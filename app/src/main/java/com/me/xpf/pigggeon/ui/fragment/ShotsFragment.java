package com.me.xpf.pigggeon.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.me.xpf.pigggeon.R;
import com.me.xpf.pigggeon.base.fragment.BaseRecyclerViewMvpFragment;
import com.me.xpf.pigggeon.model.Sort;
import com.me.xpf.pigggeon.model.api.Shot;
import com.me.xpf.pigggeon.presenter.ShotsPresenter;
import com.me.xpf.pigggeon.ui.adapter.ShotsAdapter;
import com.me.xpf.pigggeon.view.ShotsView;
import com.me.xpf.pigggeon.widget.MarginDecoration;
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

    private com.me.xpf.pigggeon.model.Shot mShot;

    private Sort mSort;

    public static Fragment getInstance(com.me.xpf.pigggeon.model.Shot shot, Sort sort) {
        ShotsFragment fragment = new ShotsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_SHOT, shot);
        bundle.putSerializable(KEY_SORT, sort);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mShot = ((com.me.xpf.pigggeon.model.Shot) getArguments().getSerializable(KEY_SHOT));
            mSort = ((Sort) getArguments().getSerializable(KEY_SORT));
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void setupViews(View contentView) {
        swipeRefreshLayout = ((SwipeRefreshLayout) contentView.findViewById(R.id.swipe_refresh));
        swipeRefreshLayout.post(() -> swipeRefreshLayout.setOnRefreshListener(() -> {
            presenter.loadShots(mShot, mSort, 1);
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
                presenter.loadShots(mShot, mSort, i);
            }
        });
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new MarginDecoration(AppData.getContext()));
        mRecyclerView.setAdapter(adapter);
        setOnUpdateListener(adapter);
    }

    @NonNull
    @Override
    public ShotsPresenter createPresenter() {
        return new ShotsPresenter();
    }

    @Override
    public void showError(String error) {
        Snackbar.make(mRecyclerView, error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void progress(boolean isShow) {
        if (isShow) {
            swipeRefreshLayout.setRefreshing(true);
        } else {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void setData(List<Shot> list) {
        if (shots.size() != 0) {
            this.shots = list;
            adapter.setData(shots);
        }
    }

    @Override
    public void setDataBottom(List<Shot> list) {
        if (shots.size() != 0) {
            shots.remove(shots.size() - 1);
            adapter.notifyItemRemoved(shots.size());
            for (int i = 0; i < list.size(); i++) {
                shots.add(list.get(i));
                adapter.setData(shots);
                adapter.notifyItemInserted(shots.size());
            }
        }
    }
}
