package com.me.xpf.pigggeon.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xgo on 12/10/15.
 */
public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder, Bean> extends RecyclerView.Adapter<VH> {

    protected Context mContext;

    protected List<Bean> mDataList = new ArrayList<>();

    public BaseAdapter(Context context) {
        this.mContext = context;
    }

    public void resetList(List<Bean> list) {
        mDataList.clear();
        mDataList.addAll(list);
        notifyDataSetChanged();
    }

    public Bean getItemAtPosition(int position) {
        return mDataList.get(position);
    }

    public void addAll(List<Bean> list) {
        mDataList.addAll(list);
        notifyDataSetChanged();
    }

    public void addAll(int position, List<Bean> list) {
        mDataList.addAll(position, list);
        notifyDataSetChanged();
    }

    public void clear() {

        mDataList.clear();
        notifyDataSetChanged();
    }

    public void remove(int position) {
        mDataList.remove(position);
        notifyItemRemoved(position);
    }

    public void add(int position, Bean bean) {
        mDataList.add(position, bean);
        notifyItemInserted(position);
    }

    public Bean getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

}
