package org.ganchai.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jayfeng.lesscode.core.AdapterLess;
import com.jayfeng.lesscode.core.ViewLess;

import org.ganchai.R;
import org.ganchai.activity.FullImageActivity;
import org.ganchai.config.Config;
import org.ganchai.config.Helper;
import org.ganchai.extend.ExtendModel;
import org.ganchai.model.Digest;
import org.ganchai.widget.RecycleItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MustListFragment extends BaseFragment {

    private List<Digest> data;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_must_list, container, false);
        recyclerView = ViewLess.$(rootView, R.id.recyclerview);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initListView();
    }

    private void initListView() {
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
                        View enjoyContainerView = recycleViewHolder.$view(R.id.enjoy_container);
                        TextView enjoyTextView = recycleViewHolder.$view(R.id.enjoy_text);
                        Button enjoyUrl = recycleViewHolder.$view(R.id.enjoy_url);

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

                        if (Config.isOpenEnjoyMode() && !TextUtils.isEmpty(digest.getEnjoy_image())) {

                            Helper.displayDraweeView(digest.getEnjoy_image(), enjoyImageView);

                            enjoyImageView.setTag(digest.getEnjoy_image());
                            enjoyImageView.setVisibility(View.VISIBLE);
                            enjoyImageView.setOnClickListener(imageClickListener);
                        } else {
                            enjoyImageView.setVisibility(View.GONE);
                        }

                        if (Config.isOpenEnjoyMode()
                                && !(TextUtils.isEmpty(digest.getEnjoy_text())
                                && TextUtils.isEmpty(digest.getEnjoy_url()))) {
                            enjoyContainerView.setVisibility(View.VISIBLE);
                            enjoyTextView.setText(digest.getEnjoy_text());
                        } else {
                            enjoyContainerView.setVisibility(View.GONE);
                        }
                    }
                });
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecycleItemDecoration(getActivity(), RecycleItemDecoration.VERTICAL_LIST, getResources().getDrawable(R.drawable.recycleview_item_decoration)));
    }

    private void initData() {
        data = new ArrayList<>();

        Digest digest = new Digest();
        digest.setTitle("api demo");
        digest.setSummary("官方api学习参考的最佳应用。");
        data.add(digest);

        digest = new Digest();
        digest.setTitle("library for developers android");
        digest.setSummary("集成了大量最新的android库，按门分类，并能够直接运行查看效果，居家必备。");
        data.add(digest);
    }

}
