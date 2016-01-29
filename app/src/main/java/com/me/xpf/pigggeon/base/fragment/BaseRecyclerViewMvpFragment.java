package com.me.xpf.pigggeon.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.me.xpf.pigggeon.R;
import com.xpf.me.architect.presenter.BasePresenter;
import com.xpf.me.architect.view.IView;

import butterknife.ButterKnife;

/**
 * Created by xgo on 12/9/15.
 */
public abstract class BaseRecyclerViewMvpFragment<V extends IView, P extends BasePresenter<V>, RV extends RecyclerView> extends BaseMvpFragment<V, P> {

    protected View mContentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContentView = inflater.inflate(getLayoutId(), null);
        mRecyclerView = findRecyclerView(mContentView);
        setupViews(mContentView);
        return mContentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this); // avoid mem leak
        // TODO: clear view's listeners and drawables
    }

    protected abstract int getLayoutId();


    protected RV mRecyclerView;

    public RV getRecyclerView() {
        return mRecyclerView;
    }

    @SuppressWarnings("unchecked")
    protected RV findRecyclerView(View contentView) {
        return (RV) contentView.findViewById(R.id.recyclerView);
    }

    protected abstract void setupViews(View contentView);
}
