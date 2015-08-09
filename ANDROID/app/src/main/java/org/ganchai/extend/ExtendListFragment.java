package org.ganchai.extend;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jayfeng.lesscode.core.AdapterLess;
import com.jayfeng.lesscode.core.ViewLess;

import org.ganchai.R;
import org.ganchai.activity.WebViewActivity;
import org.ganchai.extend.gank.ExtendGankActivity;
import org.ganchai.fragment.BaseFragment;
import org.ganchai.widget.RecycleItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ExtendListFragment extends BaseFragment implements View.OnClickListener {

    private List<ExtendModel> extendModels;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter<AdapterLess.RecycleViewHolder> adapter;

    public ExtendListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initExtendList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_extend_list, container, false);
        recyclerView = ViewLess.$(rootView, R.id.recyclerview);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initListView();
    }

    private void initListView() {
        adapter = AdapterLess.$recycle(getActivity(),
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
                        recycleViewHolder.itemView.setOnClickListener(ExtendListFragment.this);
                    }
                });
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecycleItemDecoration(getActivity(), RecycleItemDecoration.VERTICAL_LIST, getResources().getDrawable(R.drawable.recycleview_item_decoration)));
    }

    private void initExtendList() {
        extendModels = new ArrayList<>();

        ExtendModel extendModel = new ExtendModel();
        extendModel.setTitle("干货集中营");
        extendModel.setDesc("每日分享妹子图 和 技术干货，还有供大家中午休息的休闲视频。");
        extendModel.setHomepage("http://gank.io/");
        extendModel.setIntentClass(ExtendGankActivity.class);
        extendModels.add(extendModel);
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        if (tag != null) {

            Intent intent;
            ExtendModel extendModel = ((ExtendModel) tag);
            if (extendModel.getIntentClass() != null) {
                intent = new Intent(getActivity(), extendModel.getIntentClass());
                intent.putExtra(BaseExtendActivity.KEY_TITLE, extendModel.getTitle());
            } else {
                intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra(WebViewActivity.KEY_URL, ((ExtendModel) tag).getHomepage());
            }
            startActivity(intent);
        }
    }
}
