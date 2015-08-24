package org.ganchai.extend.rss;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jayfeng.lesscode.core.AdapterLess;
import com.jayfeng.lesscode.core.ViewLess;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import org.ganchai.R;
import org.ganchai.activity.WebViewActivity;
import org.ganchai.extend.BaseExtendActivity;
import org.ganchai.widget.RecycleItemDecoration;

import java.util.List;

public class ExtendRssActivity extends BaseExtendActivity implements View.OnClickListener {

    private String rssUrl;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter<AdapterLess.RecycleViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend_rss);

        rssUrl = getIntent().getStringExtra(BaseExtendActivity.KEY_RSS);
        String title = getIntent().getStringExtra(BaseExtendActivity.KEY_TITLE);
        if (!TextUtils.isEmpty(title)) {
            setTitle(title);
        }
        initToolbar();
        initLoadingDialog();

        recyclerView = ViewLess.$(this, R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        requestData();
    }

    private void requestData() {
        showLoadingDialog("正在加载");
        ExtendRssRequest request = new ExtendRssRequest();
        request.setUrl(rssUrl);
        getSpiceManager().execute(request, new RequestListener<ExtendRssModelList>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
                spiceException.printStackTrace();
                cancelLoadingDialog();
            }

            @Override
            public void onRequestSuccess(ExtendRssModelList extendRssModelList) {
                initListView(extendRssModelList);
                cancelLoadingDialog();
            }
        });
    }

    private void initListView(List<ExtendRssModel> data) {
        adapter = AdapterLess.$recycle(this,
                data,
                R.layout.activity_extend_rss_list_item,
                new AdapterLess.RecycleCallBack<ExtendRssModel>() {
                    @Override
                    public void onBindViewHolder(int i, AdapterLess.RecycleViewHolder recycleViewHolder, ExtendRssModel extendRssModel) {
                        // set content
                        TextView titleView = recycleViewHolder.$view(R.id.title);
                        TextView descView = recycleViewHolder.$view(R.id.desc);
                        TextView timeView = recycleViewHolder.$view(R.id.time);

                        titleView.setText(extendRssModel.getTitle());
                        descView.setText(Html.fromHtml(extendRssModel.getDescription()));
                        timeView.setText(extendRssModel.getPubDate());

                        // set listener
                        recycleViewHolder.itemView.setTag(extendRssModel);
                        recycleViewHolder.itemView.setOnClickListener(ExtendRssActivity.this);
                    }
                });
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecycleItemDecoration(ExtendRssActivity.this, RecycleItemDecoration.VERTICAL_LIST, getResources().getDrawable(R.drawable.recycleview_item_decoration)));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rss, menu);
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
            Intent intent = new Intent(ExtendRssActivity.this, WebViewActivity.class);
            intent.putExtra(WebViewActivity.KEY_URL, ((ExtendRssModel) tag).getLink());
            startActivity(intent);
        }
    }


}
