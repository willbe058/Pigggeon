package com.me.xpf.pigggeon.base.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by xgo on 12/9/15.
 */
public abstract class BaseFragment extends Fragment {

    protected String getSelfTag() {
        return BaseFragment.this.getClass().getSimpleName();
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
