package org.ganchai.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jayfeng.lesscode.core.AdapterLess;
import com.jayfeng.lesscode.core.ViewLess;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import org.ganchai.R;
import org.ganchai.activity.BaseActivity;
import org.ganchai.adapter.DigestAdapter;
import org.ganchai.model.Digest;
import org.ganchai.webservices.json.DigestJson;
import org.ganchai.webservices.request.DigestListRequest;

import java.util.List;

public class HomeDigestListFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private DigestAdapter adapter;

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
        adapter = new DigestAdapter(data);
        recyclerView.setAdapter(adapter);
    }
}
