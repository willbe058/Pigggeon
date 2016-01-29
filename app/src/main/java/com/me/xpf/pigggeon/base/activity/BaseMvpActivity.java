package com.me.xpf.pigggeon.base.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import com.xpf.me.architect.activity.MvpActivity;
import com.xpf.me.architect.presenter.BasePresenter;
import com.xpf.me.architect.view.IView;

import butterknife.ButterKnife;

/**
 * Created by xgo on 12/9/15.
 */
public abstract class BaseMvpActivity<V extends IView, P extends BasePresenter<V>> extends MvpActivity<V, P> {

    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupContentView();
        findViews();

        setupActionBar();
        setupViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this); // avoid mem leak
        // TODO: clear view's listeners
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT == 19) {
            tintStatusBarApi19();
        } else if (Build.VERSION.SDK_INT >= 21) {
            tintStatusBarApi21();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    //  @Override public boolean onOptionsItemSelected(MenuItem item) {
//    if (item.getItemId() == android.R.id.home) {
//      this.finish();
//      return true;
//    }
//    return false;
//  }
    protected abstract void setupContentView();

    protected abstract void findViews();

    protected abstract void setupActionBar();

    protected void setupViews() {
    }

    protected abstract void tintStatusBarApi21();

    protected abstract void tintStatusBarApi19();
}
