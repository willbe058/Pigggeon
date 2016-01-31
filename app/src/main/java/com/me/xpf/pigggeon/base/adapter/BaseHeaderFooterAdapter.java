package com.me.xpf.pigggeon.base.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.me.xpf.pigggeon.R;
import com.me.xpf.pigggeon.widget.PigggeonLoadAnimationView;
import com.xpf.me.architect.fragment.MvpFragment;
import com.xpf.me.architect.recyclerview.RecyclerHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by pengfeixie on 16/1/31.
 */
public abstract class BaseHeaderFooterAdapter<T> extends RecyclerView.Adapter<RecyclerHolder> implements MvpFragment.OnUpdateListener {

    public static final int VIEW_ITEM = 0;

    public static final int VIEW_FOOTER = 1;

    public static final int VIEW_HEADER = 2;

    protected List<T> realData;

    protected final int mItemLayoutId;

    protected int mItemFooterId;

    protected View mHeader;

    protected boolean isScrolling;

    protected Context mContext;

    protected OnItemClickListener listener;

    protected OnLoadMoreListener loadMoreListener;

    protected boolean hasFooter = false;

    private RecyclerView recyclerView;

    public interface OnItemClickListener {
        void onItemClick(View view, Object data, int position);
    }

    public interface OnLoadMoreListener {

        void loadMore(int page);

    }

    public BaseHeaderFooterAdapter(final RecyclerView v, Collection<T> data, int itemLayoutId, int footerId, View header) {
        if (data == null) {
            realData = new ArrayList<>();
        } else if (data instanceof List) {
            realData = (List<T>) data;
        } else {
            realData = new ArrayList<>(data);
        }
        mItemLayoutId = itemLayoutId;
        mItemFooterId = footerId;
        mHeader = header;
        mContext = v.getContext();
        hasFooter = true;
        EndlessScrollListener scrollListener = new EndlessScrollListener();
        recyclerView = v;
        recyclerView.addOnScrollListener(scrollListener);
    }

    public boolean isHeader(int position) {
        return position == 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeader(position)) {
            return VIEW_HEADER;
        } else if (realData.get(position) != null) {
            return VIEW_ITEM;
        } else {
            return VIEW_FOOTER;
        }
    }

    public abstract void convert(RecyclerHolder holder, T item, int position, boolean isScrolling);

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(mItemLayoutId, parent, false);
        if (viewType == VIEW_ITEM) {
            return new RecyclerHolder(root);
        } else if (viewType == VIEW_FOOTER) {
            return new RecyclerHolder(LayoutInflater.from(mContext).inflate(mItemFooterId, parent, false));
        } else {
            return new RecyclerHolder(mHeader);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        if (isHeader(position)) {
            return;
        }

        if (getItemViewType(position) == VIEW_ITEM) {
            convert(holder, realData.get(position), position, isScrolling);
            holder.itemView.setOnClickListener(getOnClickListener(position));
        } else {
            ((PigggeonLoadAnimationView) holder.getView(R.id.load)).reset();
            //do nothing?
        }

    }

    public void setOnItemClickListener(OnItemClickListener l) {
        listener = l;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener l) {
        this.loadMoreListener = l;
    }

    public View.OnClickListener getOnClickListener(final int position) {
        return v -> {
            if (listener != null && v != null) {
                listener.onItemClick(v, realData.get(position), position);
            }
        };
    }


    @Override
    public int getItemCount() {
        return realData.size();
    }

    public BaseHeaderFooterAdapter<T> refresh(Collection<T> datas) {
        if (datas == null) {
            realData = new ArrayList<>();
        } else if (datas instanceof List) {
            realData = (List<T>) datas;
        } else {
            realData = new ArrayList<>(datas);
        }
        return this;
    }

    public class EndlessScrollListener extends RecyclerView.OnScrollListener {
        protected int previousTotal = 0; // The total number of items in the dataset after the last load
        protected boolean loading = true; // True if we are still waiting for the last set of data to load.
        protected boolean canLoad = true;
        protected int visibleThreshold = 1; // The minimum amount of items to have below your current scroll position before loading more.
        protected int firstVisibleItem, visibleItemCount, totalItemCount;
        protected int current_page = 1;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            isScrolling = !(newState == RecyclerView.SCROLL_STATE_IDLE);
            if (!isScrolling) {
                notifyDataSetChanged();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            visibleItemCount = recyclerView.getChildCount();
            totalItemCount = recyclerView.getLayoutManager().getItemCount();
            firstVisibleItem = ((GridLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            if (loading) {
                if (totalItemCount > previousTotal + 1) {
                    loading = false;
                    previousTotal = totalItemCount;
                }
            }
            Log.i("loading", String.valueOf(loading));
            if (canLoad && !loading && (firstVisibleItem + visibleItemCount) >= (totalItemCount - visibleThreshold)) {
                // End has been reached
                // Do something
                current_page++;
                onLoadMore(current_page);
                loading = true;
            }
        }

        public void onLoadMore(int page) {
            realData.add(null);
            notifyItemInserted(realData.size());
            loadMoreListener.loadMore(page);
        }
    }

    @Override
    public void onUpdateFinished() {
        recyclerView.clearOnScrollListeners();
        recyclerView.addOnScrollListener(new EndlessScrollListener());
    }

}
