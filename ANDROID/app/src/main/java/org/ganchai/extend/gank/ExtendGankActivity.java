package org.ganchai.extend.gank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jayfeng.lesscode.core.AdapterLess;
import com.jayfeng.lesscode.core.DisplayLess;
import com.jayfeng.lesscode.core.ViewLess;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import org.ganchai.R;
import org.ganchai.activity.BaseActivity;
import org.ganchai.activity.WebViewActivity;
import org.ganchai.config.WebConfig;
import org.ganchai.extend.BaseExtendActivity;
import org.ganchai.model.Digest;
import org.ganchai.webservices.json.DigestListJson;
import org.ganchai.webservices.request.JsonRequest;
import org.ganchai.widget.RecycleItemDecoration;

import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class ExtendGankActivity extends BaseExtendActivity implements View.OnClickListener {

    public static final int MORE_STATE_NORMAL = 0;
    public static final int MORE_STATE_LOADING = 1;
    public static final int MORE_STATE_NONE = 2;

    private PtrClassicFrameLayout ptrFrame;
    private RecyclerView recyclerView;
    private View errorView;
    private RecyclerView.ItemDecoration itemDecoration;

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter<AdapterLess.RecycleViewHolder> adapter;

    private List<ExtendGankModel> recyclerData;
    private int moreState = MORE_STATE_NORMAL;
    private int page = 1;
    private static final int size = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend_gank);

        String title = getIntent().getStringExtra(BaseExtendActivity.KEY_TITLE);
        if (!TextUtils.isEmpty(title)) {
            setTitle(title);
        }

        initToolbar();
        initView();
        initPtr();

        ptrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrFrame.autoRefresh();
            }
        }, 100);
    }

    private void initView() {
        ptrFrame = ViewLess.$(this, R.id.fragment_rotate_header_with_listview_frame);
        recyclerView = ViewLess.$(this, R.id.recyclerview);
        errorView = ViewLess.$(this, R.id.error);

        itemDecoration = new RecycleItemDecoration(this, RecycleItemDecoration.VERTICAL_LIST, getResources().getDrawable(R.drawable.recycleview_item_decoration));
        recyclerView.removeItemDecoration(itemDecoration);
        recyclerView.addItemDecoration(itemDecoration);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
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
        JsonRequest<ExtendGankModelListJson> request = new JsonRequest<>(ExtendGankModelListJson.class);
        request.setUrl(Config.getGankList(page, size));
        getSpiceManager().execute(request, new RequestListener<ExtendGankModelListJson>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
                spiceException.printStackTrace();
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
            public void onRequestSuccess(ExtendGankModelListJson extendGankModelListJson) {
                if (recyclerData != null) {
                    recyclerData.clear();
                }
                if (ptrFrame.isRefreshing()) {
                    ptrFrame.refreshComplete();
                }
                // show data
                recyclerData = extendGankModelListJson.getResults();
                initListView();
                errorView.setVisibility(View.GONE);
            }
        });
    }


    private void moreRequestData() {
        JsonRequest<ExtendGankModelListJson> request = new JsonRequest<>(ExtendGankModelListJson.class);
        request.setUrl(Config.getGankList(page + 1, size));

        getSpiceManager().execute(request, new RequestListener<ExtendGankModelListJson>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
                moreState = MORE_STATE_NORMAL;
            }

            @Override
            public void onRequestSuccess(ExtendGankModelListJson extendGankModelListJson) {
                page++;
                List<ExtendGankModel> moreRecyclerData = extendGankModelListJson.getResults();
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
        adapter = AdapterLess.$recycle(this,
                recyclerData,
                R.layout.activity_extend_gank_list_item,
                new AdapterLess.RecycleCallBack<ExtendGankModel>() {
                    @Override
                    public void onBindViewHolder(int i, AdapterLess.RecycleViewHolder recycleViewHolder, ExtendGankModel extendGankModel) {
                        // set content
                        TextView descView = recycleViewHolder.$view(R.id.desc);
                        TextView whoView = recycleViewHolder.$view(R.id.who);
                        TextView timeView = recycleViewHolder.$view(R.id.time);

                        descView.setText(extendGankModel.getDesc());
                        whoView.setText(extendGankModel.getWho());
                        timeView.setText(extendGankModel.getUpdatedAt());

                        // set listener
                        recycleViewHolder.itemView.setTag(extendGankModel);
                        recycleViewHolder.itemView.setOnClickListener(ExtendGankActivity.this);

                        if (i == recyclerData.size() - 1) {
                            if (moreState == MORE_STATE_NORMAL) {
                                moreRequestData();
                                moreState = MORE_STATE_LOADING;
                            }
                        }
                    }
                });
        recyclerView.setAdapter(adapter);
    }

    private void moreListView(List<ExtendGankModel> moreRecycleData) {
        recyclerData.addAll(moreRecycleData);
        adapter.notifyDataSetChanged();
        recyclerView.smoothScrollBy(0, DisplayLess.$dp2px(40));
    }

    private void resetPage() {
        page = 1;
        moreState = MORE_STATE_NORMAL;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_extend_gank, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        if (tag != null) {
            Intent intent = new Intent(ExtendGankActivity.this, WebViewActivity.class);
            intent.putExtra(WebViewActivity.KEY_URL, ((ExtendGankModel) tag).getUrl());
            startActivity(intent);
        }
    }
}
