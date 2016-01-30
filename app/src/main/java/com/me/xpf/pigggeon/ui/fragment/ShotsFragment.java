package com.me.xpf.pigggeon.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.me.xpf.pigggeon.R;
import com.me.xpf.pigggeon.base.fragment.BaseRecyclerViewMvpFragment;
import com.me.xpf.pigggeon.event.BusProvider;
import com.me.xpf.pigggeon.event.Event;
import com.me.xpf.pigggeon.model.api.Shot;
import com.me.xpf.pigggeon.presenter.ShotsPresenter;
import com.me.xpf.pigggeon.ui.adapter.ShotsAdapter;
import com.me.xpf.pigggeon.view.ShotsView;
import com.me.xpf.pigggeon.widget.MarginDecoration;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.xpf.me.architect.app.AppData;
import com.xpf.me.architect.recyclerview.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pengfeixie on 16/1/29.
 */
public class ShotsFragment extends BaseRecyclerViewMvpFragment<ShotsView, ShotsPresenter, RecyclerView>
        implements ShotsView {

    public static final String KEY_SHOT = "key_shot";

    public static final String KEY_SORT = "key_sort";

    private SwipeRefreshLayout swipeRefreshLayout;

    private ShotsAdapter adapter;

    private List<Shot> shots = new ArrayList<>();

    private String mShot;

    private String mSort;

    public static Fragment getInstance(String shot, String sort) {
        ShotsFragment fragment = new ShotsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_SHOT, shot);
        bundle.putString(KEY_SORT, sort);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
        if (getArguments() != null) {
            mShot = (getArguments().getString(KEY_SHOT));
            mSort = (getArguments().getString(KEY_SORT));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void setupViews(View contentView) {
        swipeRefreshLayout = ((SwipeRefreshLayout) contentView.findViewById(R.id.swipe_refresh));
        swipeRefreshLayout.post(() -> swipeRefreshLayout.setOnRefreshListener(() -> {
            loadShots(1);
            updateListener.onUpdateFinished();
        }));
        swipeRefreshLayout.post(() -> swipeRefreshLayout.setColorSchemeColors(AppData.getColor(R.color.colorAccent)));

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (adapter.getItemViewType(position)) {
                    case BaseAdapter.VIEW_ITEM:
                        return 1;
                    case BaseAdapter.VIEW_FOOTER:
                        return 2;
                    default:
                        return -1;
                }
            }
        });
        adapter = new ShotsAdapter(mRecyclerView, shots);
        adapter.setOnLoadMoreListener(new BaseAdapter.OnLoadMoreListener() {
            @Override
            public void loadMore(int i) {
                loadShots(i);
            }
        });
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new MarginDecoration(AppData.getContext()));
        mRecyclerView.setAdapter(adapter);
        setOnUpdateListener(adapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(true));
        loadShots(1);
    }

    @NonNull
    @Override
    public ShotsPresenter createPresenter() {
        return new ShotsPresenter();
    }

    @Override
    public void showError(String error) {
        Snackbar.make(mRecyclerView, error, Snackbar.LENGTH_INDEFINITE).setAction("RETRY", v -> {
            loadShots(1);
            swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(true));
        }).show();
    }

    @Override
    public void showErrorBottom(String error) {
        Snackbar.make(mRecyclerView, error, Snackbar.LENGTH_INDEFINITE).setAction("RETRY", v -> {
            loadShots(1);
            swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(true));
        }).show();
        shots.remove(shots.size() - 1);
        adapter.notifyItemRemoved(shots.size());
    }

    @Override
    public void progress(boolean isShow) {
        if (isShow) {
            swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(true));
        } else {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void setData(List<Shot> list) {
        if (list.size() != 0) {
            this.shots = list;
            adapter.clearData();
            adapter.setData(shots);
            adapter.onUpdateFinished();
            mRecyclerView.smoothScrollToPosition(0);
        }
    }

    @Override
    public void setDataBottom(List<Shot> list) {
        if (list.size() != 0) {
            shots.remove(shots.size() - 1);
            adapter.notifyItemRemoved(shots.size());
            for (int i = 0; i < list.size(); i++) {
                shots.add(list.get(i));
                adapter.setData(shots);
                adapter.notifyItemInserted(shots.size());
            }
        }
    }

    /**
     * wrapper for presenter
     *
     * @param page
     */
    private void loadShots(int page) {
        presenter.cancel();
        presenter.loadShots(mShot, mSort, page);
    }

    @Subscribe
    public void onUpdateShots(Event.UpdateShotEvent event) {
        if (event != null) {
            swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(true));
            this.mShot = event.getShot();
            this.mSort = event.getSort();
            loadShots(1);
        }
    }
}
