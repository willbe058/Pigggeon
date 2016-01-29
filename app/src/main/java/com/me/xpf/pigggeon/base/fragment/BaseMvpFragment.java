package com.me.xpf.pigggeon.base.fragment;

import com.xpf.me.architect.fragment.MvpFragment;
import com.xpf.me.architect.presenter.BasePresenter;
import com.xpf.me.architect.view.IView;

/**
 * Created by xgo on 12/9/15.
 */
public abstract class BaseMvpFragment<V extends IView, P extends BasePresenter<V>> extends MvpFragment<V, P> {

    protected String getSelfTag() {
        return BaseMvpFragment.this.getClass().getSimpleName();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
