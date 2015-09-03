package org.ganchai.extend.html;

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

public class ExtendHtmlActivity extends BaseExtendActivity implements View.OnClickListener {

    public static final String KEY_SELECT_PATH_LIST = "key_select_path_list";
    public static final String KEY_SELECT_PATH_TITLE = "key_select_path_title";
    public static final String KEY_SELECT_PATH_SUMMARY = "key_select_path_summary";
    public static final String KEY_SELECT_PATH_TIME = "key_select_path_time";
    public static final String KEY_SELECT_PATH_URL = "key_select_path_url";
    public static final String KEY_SELECT_ATTR_TITLE = "key_select_attr_title";
    public static final String KEY_SELECT_ATTR_SUMMARY = "key_select_attr_summary";
    public static final String KEY_SELECT_ATTR_TIME = "key_select_attr_time";
    public static final String KEY_SELECT_ATTR_URL = "key_select_attr_url";
    public static final String KEY_SELECT_PREFIX_URL = "key_select_prefix_url";

    private String htmlUrl;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter<AdapterLess.RecycleViewHolder> adapter;

    private String listSelectPath;
    private String titleSelectPath;
    private String summarySelectPath;
    private String timeSelectPath;
    private String urlSelectPath;
    private String titleSelectAttr;
    private String summarySelectAttr;
    private String timeSelectAttr;
    private String urlSelectAttr;
    private String urlSelectPrefix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend_rss);

        htmlUrl = getIntent().getStringExtra(BaseExtendActivity.KEY_HTML);
        listSelectPath = getIntent().getStringExtra(KEY_SELECT_PATH_LIST);
        titleSelectPath = getIntent().getStringExtra(KEY_SELECT_PATH_TITLE);
        summarySelectPath = getIntent().getStringExtra(KEY_SELECT_PATH_SUMMARY);
        timeSelectPath = getIntent().getStringExtra(KEY_SELECT_PATH_TIME);
        urlSelectPath = getIntent().getStringExtra(KEY_SELECT_PATH_URL);
        titleSelectAttr = getIntent().getStringExtra(KEY_SELECT_ATTR_TITLE);
        summarySelectAttr = getIntent().getStringExtra(KEY_SELECT_ATTR_SUMMARY);
        timeSelectAttr = getIntent().getStringExtra(KEY_SELECT_ATTR_TIME);
        urlSelectAttr = getIntent().getStringExtra(KEY_SELECT_ATTR_URL);
        urlSelectPrefix = getIntent().getStringExtra(KEY_SELECT_PREFIX_URL);

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
        ExtendHtmlRequest request = new ExtendHtmlRequest();
        request.setUrl(htmlUrl);
        request.setListSelectPath(listSelectPath);
        request.setTitleSelectPath(titleSelectPath);
        request.setSummarySelectPath(summarySelectPath);
        request.setTimeSelectPath(timeSelectPath);
        request.setUrlSelectPath(urlSelectPath);
        request.setTitleSelectAttr(titleSelectAttr);
        request.setSummarySelectAttr(summarySelectAttr);
        request.setTimeSelectAttr(timeSelectAttr);
        request.setUrlSelectAttr(urlSelectAttr);
        request.setUrlSelectPrefix(urlSelectPrefix);
        getSpiceManager().execute(request, new RequestListener<ExtendHtmlModelList>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
                spiceException.printStackTrace();
                cancelLoadingDialog();
            }

            @Override
            public void onRequestSuccess(ExtendHtmlModelList extendHtmlModelList) {
                initListView(extendHtmlModelList);
                cancelLoadingDialog();
            }
        });
    }

    private void initListView(List<ExtendHtmlModel> data) {
        adapter = AdapterLess.$recycle(this,
                data,
                R.layout.activity_extend_rss_list_item,
                new AdapterLess.RecycleCallBack<ExtendHtmlModel>() {
                    @Override
                    public void onBindViewHolder(int i, AdapterLess.RecycleViewHolder recycleViewHolder, ExtendHtmlModel extendHtmlModel) {
                        // set content
                        TextView titleView = recycleViewHolder.$view(R.id.title);
                        TextView descView = recycleViewHolder.$view(R.id.desc);
                        TextView timeView = recycleViewHolder.$view(R.id.time);

                        titleView.setText(extendHtmlModel.getTitle());
                        descView.setText(Html.fromHtml(extendHtmlModel.getSummary()));
                        timeView.setText(extendHtmlModel.getTime());

                        // set listener
                        recycleViewHolder.itemView.setTag(extendHtmlModel);
                        recycleViewHolder.itemView.setOnClickListener(ExtendHtmlActivity.this);
                    }
                });
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecycleItemDecoration(ExtendHtmlActivity.this, RecycleItemDecoration.VERTICAL_LIST, getResources().getDrawable(R.drawable.recycleview_item_decoration)));

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
            Intent intent = new Intent(ExtendHtmlActivity.this, WebViewActivity.class);
            intent.putExtra(WebViewActivity.KEY_URL, ((ExtendHtmlModel) tag).getUrl());
            startActivity(intent);
        }
    }


}
