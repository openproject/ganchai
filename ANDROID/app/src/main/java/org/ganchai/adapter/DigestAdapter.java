package org.ganchai.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ganchai.R;
import org.ganchai.model.Digest;

import java.util.List;

public class DigestAdapter extends RecyclerView.Adapter<DigestAdapter.ViewHolder> implements View.OnClickListener {

    private List<Digest> dataSet;
    private OnRecyclerViewItemClickListener onItemClickListener = null;

    public DigestAdapter(List<Digest> dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_home_digest_list_item, viewGroup, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.getTextView().setText(dataSet.get(position).getTitle());
        viewHolder.itemView.setTag(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.title);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            onItemClickListener.onClick(v,(Digest)v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public static interface OnRecyclerViewItemClickListener {
        void onClick(View view , Digest data);
    }
}
