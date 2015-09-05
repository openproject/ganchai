package xyz.ganchai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jayfeng.lesscode.core.AdapterLess;
import com.jayfeng.lesscode.core.ViewLess;

import org.ganchai.R;
import org.ganchai.activity.BaseActivity;
import org.ganchai.activity.WebViewActivity;
import org.ganchai.extend.BaseExtendActivity;
import org.ganchai.extend.ExtendModel;
import org.ganchai.extend.html.ExtendHtmlActivity;
import org.ganchai.extend.weixin.ExtendWeixinActivity;
import org.ganchai.widget.RecycleItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private List<ExtendModel> extendModels;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter<AdapterLess.RecycleViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xyz_activity_main);

        initToolbar();

        initExtendList();

        initView();
    }

    private void initView() {
        recyclerView = ViewLess.$(this, R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = AdapterLess.$recycle(this,
                extendModels,
                R.layout.fragment_extend_list_item,
                new AdapterLess.RecycleCallBack<ExtendModel>() {
                    @Override
                    public void onBindViewHolder(int i, AdapterLess.RecycleViewHolder recycleViewHolder, ExtendModel extendModel) {
                        // set content
                        TextView titleView = recycleViewHolder.$view(R.id.title);
                        TextView descView = recycleViewHolder.$view(R.id.desc);

                        titleView.setText(extendModel.getTitle());
                        descView.setText(extendModel.getDesc());

                        titleView.getPaint().setFakeBoldText(true);

                        // set listener
                        recycleViewHolder.itemView.setTag(extendModel);
                        recycleViewHolder.itemView.setOnClickListener(MainActivity.this);
                    }
                });
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecycleItemDecoration(this, RecycleItemDecoration.VERTICAL_LIST, getResources().getDrawable(R.drawable.recycleview_item_decoration)));
    }

    private void initExtendList() {
        extendModels = new ArrayList<>();

        ExtendModel extendModel = new ExtendModel();
        extendModel.setTitle("CPP技术网");
        extendModel.setDesc("CPP技术网");
        extendModel.setHomepage("http://weixin.sogou.com/gzhjs?cb=sogou.weixin.gzhcb&openid=oIWsFtzBk-gUGaoOA-qQohIxO0OM&eqs=9EswoYHgGD0foalG6oLiXupGvHn4gLmATvCogMn%2BNFgiSZrt4P4M8TmoKyBr5Ts8x%2Fcpp&ekv=3&page=1");
        extendModel.setHtml("http://weixin.sogou.com/gzhjs?cb=sogou.weixin.gzhcb&openid=oIWsFtzBk-gUGaoOA-qQohIxO0OM&eqs=9EswoYHgGD0foalG6oLiXupGvHn4gLmATvCogMn%2BNFgiSZrt4P4M8TmoKyBr5Ts8x%2Fcpp&ekv=3&page=1");
        extendModel.setIntentClass(ExtendWeixinActivity.class);
        extendModels.add(extendModel);

        extendModel = new ExtendModel();
        extendModel.setTitle("程序人生");
        extendModel.setDesc("分享CSDN社区中最优质的博文、最精彩的讨论、最实用的开发资源,并不定期奉送CSDN社区的各种福利.");
        extendModel.setHomepage("http://blog.csdn.net/");
        extendModel.setHtml("http://weixin.sogou.com/gzhjs?cb=sogou.weixin.gzhcb&openid=oIWsFt4wkuRLzlie2VEGsSMcZtQY&eqs=NVsnoF7g%2Fu01oQgVjZjcHujjW%2BK145LVeLCDCmoMGdexOgdNwyA2GV81AC2cYp9rDpyMx&ekv=3&page=2&t=1441367630660");
        extendModel.setIntentClass(ExtendWeixinActivity.class);
        extendModels.add(extendModel);

    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        if (tag != null) {

            Intent intent;
            ExtendModel extendModel = ((ExtendModel) tag);
            if (extendModel.getIntentClass() != null) {
                intent = new Intent(this, extendModel.getIntentClass());
                intent.putExtra(BaseExtendActivity.KEY_TITLE, extendModel.getTitle());
                intent.putExtra(BaseExtendActivity.KEY_HTML, extendModel.getHtml());

                // for html parse
                intent.putExtra(ExtendHtmlActivity.KEY_SELECT_PATH_LIST, extendModel.getListSelectPath());
                intent.putExtra(ExtendHtmlActivity.KEY_SELECT_PATH_TITLE, extendModel.getTitleSelectPath());
                intent.putExtra(ExtendHtmlActivity.KEY_SELECT_PATH_SUMMARY, extendModel.getSummarySelectPath());
                intent.putExtra(ExtendHtmlActivity.KEY_SELECT_PATH_TIME, extendModel.getTimeSelectPath());
                intent.putExtra(ExtendHtmlActivity.KEY_SELECT_PATH_URL, extendModel.getUrlSelectPath());
                intent.putExtra(ExtendHtmlActivity.KEY_SELECT_ATTR_TITLE, extendModel.getTitleSelectAttr());
                intent.putExtra(ExtendHtmlActivity.KEY_SELECT_ATTR_SUMMARY, extendModel.getSummarySelectAttr());
                intent.putExtra(ExtendHtmlActivity.KEY_SELECT_ATTR_TIME, extendModel.getTimeSelectAttr());
                intent.putExtra(ExtendHtmlActivity.KEY_SELECT_ATTR_URL, extendModel.getUrlSelectAttr());
                intent.putExtra(ExtendHtmlActivity.KEY_SELECT_PREFIX_URL, extendModel.getUrlSelectPrefix());
            } else {
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.KEY_URL, ((ExtendModel) tag).getHomepage());
            }
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.xyz_menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {

            Intent intent = new Intent(MainActivity.this, xyz.ganchai.activity.SearchActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
