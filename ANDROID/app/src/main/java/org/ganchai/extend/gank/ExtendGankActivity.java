package org.ganchai.extend.gank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jayfeng.lesscode.core.AdapterLess;
import com.jayfeng.lesscode.core.ViewLess;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import org.ganchai.R;
import org.ganchai.activity.BaseActivity;
import org.ganchai.activity.WebViewActivity;
import org.ganchai.config.Config;
import org.ganchai.config.Helper;
import org.ganchai.extend.BaseExtendActivity;
import org.ganchai.extend.ExtendModel;
import org.ganchai.model.Digest;
import org.ganchai.webservices.JsonRequestService;
import org.ganchai.webservices.json.DigestJson;
import org.ganchai.webservices.request.DigestListRequest;
import org.ganchai.widget.RecycleItemDecoration;

import java.util.List;

public class ExtendGankActivity extends BaseExtendActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter<AdapterLess.RecycleViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend_gank);

        setExtendSpiceManager(new SpiceManager(ExtendGankJsonRequestService.class));

        String title = getIntent().getStringExtra(BaseExtendActivity.KEY_TITLE);
        if (!TextUtils.isEmpty(title)) {
            setTitle(title);
        }
        initToolbar();

        recyclerView = ViewLess.$(this, R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        requestData();
    }

    private void requestData() {
        ExtendGankListRequest request = new ExtendGankListRequest(1, 20);
        extendSpiceManager.execute(request, new RequestListener<ExtendGankModelListJson>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
                spiceException.printStackTrace();
            }

            @Override
            public void onRequestSuccess(ExtendGankModelListJson extendGankModelListJson) {
                List<ExtendGankModel> data = extendGankModelListJson.getResults();
                initListView(data);
            }
        });
    }

    private void initListView(List<ExtendGankModel> data) {
        adapter = AdapterLess.$recycle(this,
                data,
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
                    }
                });
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecycleItemDecoration(ExtendGankActivity.this, RecycleItemDecoration.VERTICAL_LIST, getResources().getDrawable(R.drawable.recycleview_item_decoration)));

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
