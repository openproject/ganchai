package org.ganchai.fragment;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.jayfeng.lesscode.core.AdapterLess;
import com.jayfeng.lesscode.core.DisplayLess;
import com.jayfeng.lesscode.core.ViewLess;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import org.ganchai.R;
import org.ganchai.activity.BaseActivity;
import org.ganchai.activity.FullImageActivity;
import org.ganchai.activity.WebViewActivity;
import org.ganchai.config.Helper;
import org.ganchai.model.Digest;
import org.ganchai.webservices.json.DigestJson;
import org.ganchai.webservices.request.DigestListRequest;
import org.ganchai.widget.RecycleItemDecoration;

import java.util.List;

public class HomeDigestListFragment extends BaseFragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter<AdapterLess.RecycleViewHolder> adapter;

    private View.OnClickListener imageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String tag = (String) v.getTag();
            Intent intent = new Intent(getActivity(), FullImageActivity.class);
            intent.putExtra("url", tag);
            startActivity(intent);
        }
    };

    public HomeDigestListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home_digest_list, container, false);
        recyclerView = ViewLess.$(rootView, R.id.recyclerview);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        requestData();
    }

    private void requestData() {
        DigestListRequest request = new DigestListRequest();
        ((BaseActivity) getActivity()).getSpiceManager().execute(request, new RequestListener<DigestJson>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {

            }

            @Override
            public void onRequestSuccess(DigestJson digestJson) {
                List<Digest> data = digestJson.getData().getResult();
                initListView(data);
            }
        });
    }

    private void initListView(List<Digest> data) {
        adapter = AdapterLess.$recycle(getActivity(),
                data,
                R.layout.fragment_home_digest_list_item,
                new AdapterLess.RecycleCallBack<Digest>() {
                    @Override
                    public void onBindViewHolder(int i, AdapterLess.RecycleViewHolder recycleViewHolder, Digest digest) {
                        // set content
                        TextView titleView = recycleViewHolder.$view(R.id.title);
                        TextView summaryView = recycleViewHolder.$view(R.id.summary);
                        SimpleDraweeView thumbnailView = recycleViewHolder.$view(R.id.thumbnail);
                        SimpleDraweeView enjoyImageView = recycleViewHolder.$view(R.id.enjoy_image);

                        titleView.setText(digest.getTitle());
                        summaryView.setText(digest.getSummary());

                        titleView.getPaint().setFakeBoldText(true);

                        if (!TextUtils.isEmpty(digest.getThumbnail())) {

                            Helper.displayDraweeView(digest.getThumbnail(), thumbnailView);

                            thumbnailView.setTag(digest.getThumbnail());
                            thumbnailView.setVisibility(View.VISIBLE);
                            thumbnailView.setOnClickListener(imageClickListener);
                        } else {
                            thumbnailView.setVisibility(View.GONE);
                        }

                        if (!TextUtils.isEmpty(digest.getEnjoy_image())) {

                            Helper.displayDraweeView(digest.getEnjoy_image(), enjoyImageView);

                            enjoyImageView.setTag(digest.getEnjoy_image());
                            enjoyImageView.setVisibility(View.VISIBLE);
                            enjoyImageView.setOnClickListener(imageClickListener);
                        } else {
                            enjoyImageView.setVisibility(View.GONE);
                        }

                        // set listener
                        recycleViewHolder.itemView.setTag(digest);
                        recycleViewHolder.itemView.setOnClickListener(HomeDigestListFragment.this);
                    }
                });
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecycleItemDecoration(getActivity(), RecycleItemDecoration.VERTICAL_LIST, getResources().getDrawable(R.drawable.recycleview_item_decoration)));
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        if (tag != null) {
            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            intent.putExtra(WebViewActivity.KEY_URL, ((Digest) tag).getSource());
            startActivity(intent);
        }
    }
}
