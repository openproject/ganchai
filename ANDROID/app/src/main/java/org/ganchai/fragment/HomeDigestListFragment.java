package org.ganchai.fragment;

import android.content.Intent;
import android.os.Bundle;
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
import com.jayfeng.lesscode.core.DisplayLess;
import com.jayfeng.lesscode.core.ViewLess;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import org.ganchai.R;
import org.ganchai.activity.BaseActivity;
import org.ganchai.activity.FullImageActivity;
import org.ganchai.activity.WebViewActivity;
import org.ganchai.config.Config;
import org.ganchai.config.Helper;
import org.ganchai.config.WebConfig;
import org.ganchai.model.Digest;
import org.ganchai.webservices.json.DigestListJson;
import org.ganchai.webservices.request.JsonRequest;
import org.ganchai.widget.RecycleItemDecoration;

import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class HomeDigestListFragment extends BaseFragment implements View.OnClickListener {

    public static final String KEY_DIGEST_TYPE = "key_digest_type";

    public static final int MORE_STATE_NORMAL = 0;
    public static final int MORE_STATE_LOADING = 1;
    public static final int MORE_STATE_NONE = 2;

    private PtrClassicFrameLayout ptrFrame;
    private RecyclerView recyclerView;
    private RecyclerView.ItemDecoration itemDecoration;
    private View errorView;

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter<AdapterLess.RecycleViewHolder> adapter;

    private List<Digest> recyclerData;
    private int moreState = MORE_STATE_NORMAL;
    private int type = 0;
    private int page = 1;
    private static final int size = 10;

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

        type = getArguments().getInt(KEY_DIGEST_TYPE, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home_digest_list, container, false);
        ptrFrame = ViewLess.$(rootView, R.id.fragment_rotate_header_with_listview_frame);
        recyclerView = ViewLess.$(rootView, R.id.recyclerview);
        itemDecoration = new RecycleItemDecoration(getActivity(), RecycleItemDecoration.VERTICAL_LIST, getResources().getDrawable(R.drawable.recycleview_item_decoration));

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        errorView = ViewLess.$(rootView, R.id.error);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initPtr();

        ptrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrFrame.autoRefresh();
            }
        }, 100);
    }

    // set pullto refresh view
    private void initPtr() {
        ptrFrame.setLastUpdateTimeRelateObject(this);
        ptrFrame.disableWhenHorizontalMove(true);
        ptrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                resetPage();
                requestData();
                errorView.setVisibility(View.GONE);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, recyclerView, header);
            }
        });
    }

    private void requestData() {

        JsonRequest<DigestListJson> request = new JsonRequest<>(DigestListJson.class);
        request.setUrl(WebConfig.getDigestList(page, size, type));

        ((BaseActivity) getActivity()).getSpiceManager().execute(request, new RequestListener<DigestListJson>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
                if (ptrFrame.isRefreshing()) {
                    ptrFrame.refreshComplete();
                }
                ptrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (adapter == null || adapter.getItemCount() == 0) {
                            errorView.setVisibility(View.VISIBLE);
                        }
                    }
                }, 400);
            }

            @Override
            public void onRequestSuccess(DigestListJson digestListJson) {
                if (recyclerData != null) {
                    recyclerData.clear();
                }
                if (ptrFrame.isRefreshing()) {
                    ptrFrame.refreshComplete();
                }
                // show data
                recyclerData = digestListJson.getData().getResult();
                initListView();
                errorView.setVisibility(View.GONE);
            }
        });
    }

    private void moreRequestData() {
        JsonRequest<DigestListJson> request = new JsonRequest<>(DigestListJson.class);
        request.setUrl(WebConfig.getDigestList(page + 1, size, type));

        ((BaseActivity) getActivity()).getSpiceManager().execute(request, new RequestListener<DigestListJson>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
                moreState = MORE_STATE_NORMAL;
            }

            @Override
            public void onRequestSuccess(DigestListJson digestListJson) {
                page++;
                List<Digest> moreRecyclerData = digestListJson.getData().getResult();
                moreListView(moreRecyclerData);
                if (moreRecyclerData != null && moreRecyclerData.size() < size) {
                    moreState = MORE_STATE_NONE;
                } else {
                    moreState = MORE_STATE_NORMAL;
                }
            }
        });
    }

    private void initListView() {
        adapter = AdapterLess.$recycle(getActivity(),
                recyclerData,
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

                        // set listener
                        recycleViewHolder.itemView.setTag(digest);
                        recycleViewHolder.itemView.setOnClickListener(HomeDigestListFragment.this);

                        if (i == recyclerData.size() - 1) {
                            if (moreState == MORE_STATE_NORMAL) {
                                moreRequestData();
                                moreState = MORE_STATE_LOADING;
                            }
                        }
                    }
                });
        recyclerView.setAdapter(adapter);

        recyclerView.removeItemDecoration(itemDecoration);
        recyclerView.addItemDecoration(itemDecoration);
    }

    private void moreListView(List<Digest> moreRecycleData) {
        recyclerData.addAll(moreRecycleData);
        adapter.notifyDataSetChanged();
        recyclerView.smoothScrollBy(0, DisplayLess.$dp2px(40));
    }

    private void resetPage() {
        page = 1;
        moreState = MORE_STATE_NORMAL;
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
