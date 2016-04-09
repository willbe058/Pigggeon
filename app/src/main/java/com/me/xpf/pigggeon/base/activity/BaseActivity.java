package com.me.xpf.pigggeon.base.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.me.xpf.pigggeon.R;
import com.me.xpf.pigggeon.utils.SettingsUtil;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by xgo on 12/9/15.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initTheme();
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
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    protected void initTheme() {
        if (SettingsUtil.isDarkMode()) {
            setTheme(R.style.AppTheme_NoActionBar_Dark);
        } else {
            setTheme(R.style.AppTheme_NoActionBar);
        }
    }

    protected abstract void setupContentView();

    protected abstract void findViews();

    protected abstract void setupActionBar();

    protected void setupViews() {
    }

    protected abstract void tintStatusBarApi21();

    protected abstract void tintStatusBarApi19();
}
