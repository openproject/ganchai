package xyz.ganchai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.api.client.repackaged.org.apache.commons.codec.Encoder;
import com.jayfeng.lesscode.core.AdapterLess;
import com.jayfeng.lesscode.core.ViewLess;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import org.apache.commons.lang3.text.translate.UnicodeEscaper;
import org.ganchai.R;
import org.ganchai.activity.BaseActivity;
import org.ganchai.activity.WebViewActivity;
import org.ganchai.extend.BaseExtendActivity;
import org.ganchai.extend.ExtendModel;
import org.ganchai.extend.html.ExtendHtmlActivity;
import org.ganchai.extend.html.ExtendHtmlModel;
import org.ganchai.extend.html.ExtendHtmlModelList;
import org.ganchai.extend.html.ExtendHtmlRequest;
import org.ganchai.extend.weixin.ExtendWeixinActivity;
import org.ganchai.extend.weixin.ExtendWeixinWebviewActivity;
import org.ganchai.widget.RecycleItemDecoration;

import java.net.URLEncoder;
import java.util.List;

public class SearchActivity extends BaseActivity implements View.OnClickListener {

    private List<ExtendModel> extendModels;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter<AdapterLess.RecycleViewHolder> adapter;

    private EditText searchEditView;
    private Button searchSubmitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xyz_activity_search);

        initToolbar();
        initLoadingDialog();

        searchEditView = ViewLess.$(this, R.id.search_edit);
        searchSubmitBtn = ViewLess.$(this, R.id.search_submit);
        recyclerView = ViewLess.$(this, R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecycleItemDecoration(SearchActivity.this, RecycleItemDecoration.VERTICAL_LIST, getResources().getDrawable(R.drawable.recycleview_item_decoration)));


        searchSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(searchEditView.getText().toString())) {
                    return;
                }

                requestData();
            }
        });
    }

    private void requestData() {
        showLoadingDialog("正在加载");
        ExtendHtmlRequest request = new ExtendHtmlRequest();
        request.setUrl(getUrl(1));

        request.setListSelectPath(".wx-rb");
        request.setTitleSelectPath(".txt-box h3");
        request.setSummarySelectPath(".sp-txt");
        request.setTimeSelectPath(".hui");
        request.setUrlSelectPath(".wx-rb");
        request.setUrlSelectAttr("href");
        request.setUrlSelectPrefix("http://weixin.sogou.com");

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
                        recycleViewHolder.itemView.setOnClickListener(SearchActivity.this);
                    }
                });
        recyclerView.setAdapter(adapter);

    }

    private String getUrl(int page) {
        String key = URLEncoder.encode(searchEditView.getText().toString().trim());
        return "http://weixin.sogou.com/weixin?query=" + key + "&type=1&page=" + page;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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

            ExtendHtmlModel extendHtmlModel = (ExtendHtmlModel) tag;

//            Intent intent = new Intent(SearchActivity.this, ExtendWeixinActivity.class);
//            intent.putExtra(BaseExtendActivity.KEY_TITLE, extendHtmlModel.getTitle());
//            intent.putExtra(BaseExtendActivity.KEY_HTML, extendHtmlModel.getUrl());

            Intent intent = new Intent(SearchActivity.this, ExtendWeixinWebviewActivity.class);
            intent.putExtra(ExtendWeixinWebviewActivity.KEY_URL, extendHtmlModel.getUrl());

            startActivity(intent);
        }
    }
}
