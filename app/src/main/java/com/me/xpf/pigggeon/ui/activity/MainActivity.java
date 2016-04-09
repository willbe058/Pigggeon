package com.me.xpf.pigggeon.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.cocosw.bottomsheet.BottomSheet;
import com.me.xpf.pigggeon.R;
import com.me.xpf.pigggeon.app.PigggeonApp;
import com.me.xpf.pigggeon.base.MyRequestOptions;
import com.me.xpf.pigggeon.base.activity.BaseStatusBarTintMvpActivity;
import com.me.xpf.pigggeon.config.Config;
import com.me.xpf.pigggeon.config.Constant;
import com.me.xpf.pigggeon.event.BusProvider;
import com.me.xpf.pigggeon.event.Event;
import com.me.xpf.pigggeon.model.entity.User;
import com.me.xpf.pigggeon.model.usecase.LogoutUsecase;
import com.me.xpf.pigggeon.presenter.MainPresenter;
import com.me.xpf.pigggeon.ui.fragment.ShotsFragment;
import com.me.xpf.pigggeon.utils.PreferenceUtil;
import com.me.xpf.pigggeon.utils.SettingsUtil;
import com.me.xpf.pigggeon.view.MainView;
import com.me.xpf.pigggeon.widget.HtmlTextView;
import com.me.xpf.pigggeon.widget.animation.GlideCircleTransform;
import com.umeng.fb.FeedbackAgent;
import com.umeng.fb.fragment.FeedbackFragment;
import com.xpf.me.architect.app.AppData;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.blurry.Blurry;
import rx.Subscriber;
import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

public class MainActivity extends BaseStatusBarTintMvpActivity<MainView, MainPresenter> implements MainView {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.tabs)
    TabLayout tabLayout;

    @Bind(R.id.viewpager)
    ViewPager viewPager;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private ImageView header;

    private TextView userName;

    private HtmlTextView userBio;

    private ImageView blurBack;

    @Bind(R.id.nav_view)
    NavigationView navigationView;

    private CalligraphyTypefaceSpan typefaceSpan;

    private ActionBarDrawerToggle mToggle;

    private boolean isLoaded;

    private String SHOT_TYPE_STRING = "", SORT_TYPE_STRING = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
        typefaceSpan = new CalligraphyTypefaceSpan(
                TypefaceUtils.load(AppData.getContext().getAssets(), "fonts/Comfortaa-Regular.ttf"));
        //checkToken();
        presenter.onCreate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

    private void checkToken() {
        String token;
        if (getIntent().getStringExtra("token") != null) {
            token = getIntent().getStringExtra("token");
        } else {
            token = PigggeonApp.getClientAccessToken();
        }
    }

    @Override
    protected void setupContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected void setupActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void setupViews() {
        super.setupViews();

        mToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.hello_world, R.string.hello_world);
        drawerLayout.addDrawerListener(mToggle);

        setupDrawerContent(navigationView);
        setupViewPager(viewPager);

        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(ShotsFragment.getInstance(
                PreferenceUtil.getPreString(Config.PRE_SHOT_KEY, "SHOTS"), "")
                , PreferenceUtil.getPreString(Config.PRE_SHOT_KEY, "SHOTS").toUpperCase());
        adapter.addFragment(ShotsFragment.getInstance(), "FOLLOWING");
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
    }

    private void setupDrawerContent(final NavigationView navigationView) {
        View headerView = navigationView.getHeaderView(0);
        header = ((ImageView) headerView.findViewById(R.id.header_photo));
        userName = ((TextView) headerView.findViewById(R.id.user_name_in_drawer));
        userBio = ((HtmlTextView) headerView.findViewById(R.id.bio));
        blurBack = ((ImageView) headerView.findViewById(R.id.backImg));

        switch (PreferenceUtil.getPreString(Config.PRE_SHOT_KEY, "SHOTS")) {
            case Constant.SHOTS:
                navigationView.setCheckedItem(R.id.shots);
                break;
            case Constant.TEAMS:
                navigationView.setCheckedItem(R.id.teams);
                break;
            case Constant.DEBUTS:
                navigationView.setCheckedItem(R.id.debuts);
                break;
            case Constant.PLAY_OFFS:
                navigationView.setCheckedItem(R.id.playoffs);
                break;
            case Constant.REBOUNDS:
                navigationView.setCheckedItem(R.id.rebounds);
                break;
            case Constant.ANIMATED:
                navigationView.setCheckedItem(R.id.animated);
                break;

        }
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            CharSequence text = "SHOTS";
            switch (menuItem.getItemId()) {
                case R.id.feedback:
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, FeedBackActivity.class);
                    String id = new FeedbackAgent(MainActivity.this).getDefaultConversation().getId();
                    intent.putExtra(FeedbackFragment.BUNDLE_KEY_CONVERSATION_ID, id);
                    startActivity(intent);
                    break;
                case R.id.setting:
                    startActivity(new Intent(MainActivity.this, MyPreferenceActivity.class));
                    break;
                case R.id.log_out:
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Log out?")
                            .setPositiveButton("LOG OUT", (dialog, which) -> {
                                dialog.dismiss();
                                MaterialDialog progressDialog = new MaterialDialog.Builder(MainActivity.this)
                                        .title("Log out...")
                                        .progress(true, 0)
                                        .cancelable(false)
                                        .build();
                                progressDialog.show();
                                new LogoutUsecase().clearCookie().finallyDo(progressDialog::dismiss)
                                        .subscribe(new Subscriber<Object>() {
                                            @Override
                                            public void onCompleted() {
                                                progressDialog.dismiss();
                                                PreferenceUtil.setPreBoolean(Config.PRE_IS_LOG_KEY, false);
                                                PreferenceUtil.setPreString(Config.PRE_ACCESS_TOKEN_KEY, null);
                                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                                finish();
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                Snackbar.make(drawerLayout, e.getLocalizedMessage(),
                                                        Snackbar.LENGTH_LONG)
                                                        .show();
                                            }

                                            @Override
                                            public void onNext(Object o) {

                                            }
                                        });
                            })
                            .setNegativeButton("CANCEL", (dialog, which) -> {
                                dialog.dismiss();
                            }).show();
                    drawerLayout.closeDrawers();
                    break;
                case R.id.shots:
                    SHOT_TYPE_STRING = "";
                    PreferenceUtil.setPreString(Config.PRE_SHOT_KEY, Constant.SHOTS);
                    updateShots();
                    text = "SHOTS";
                    setTabText(text, typefaceSpan);
                    menuItem.setChecked(true);

                    break;
                case R.id.teams:
                    SHOT_TYPE_STRING = Constant.TEAMS;
                    PreferenceUtil.setPreString(Config.PRE_SHOT_KEY, Constant.TEAMS);
                    updateShots();
                    text = "TEAMS";
                    setTabText(text, typefaceSpan);
                    menuItem.setChecked(true);

                    break;
                case R.id.debuts:
                    SHOT_TYPE_STRING = Constant.DEBUTS;
                    PreferenceUtil.setPreString(Config.PRE_SHOT_KEY, Constant.DEBUTS);
                    updateShots();
                    text = "DEBUTS";
                    setTabText(text, typefaceSpan);
                    menuItem.setChecked(true);

                    break;
                case R.id.playoffs:
                    SHOT_TYPE_STRING = Constant.PLAY_OFFS;
                    PreferenceUtil.setPreString(Config.PRE_SHOT_KEY, Constant.PLAY_OFFS);
                    updateShots();
                    text = "PLAYOFFS";
                    setTabText(text, typefaceSpan);
                    menuItem.setChecked(true);

                    break;
                case R.id.rebounds:
                    SHOT_TYPE_STRING = Constant.REBOUNDS;
                    PreferenceUtil.setPreString(Config.PRE_SHOT_KEY, Constant.REBOUNDS);
                    updateShots();
                    text = "REBOUNDS";
                    setTabText(text, typefaceSpan);
                    menuItem.setChecked(true);

                    break;
                case R.id.animated:
                    SHOT_TYPE_STRING = Constant.ANIMATED;
                    PreferenceUtil.setPreString(Config.PRE_SHOT_KEY, Constant.ANIMATED);
                    updateShots();
                    text = "ANIMATED";
                    setTabText(text, typefaceSpan);
                    menuItem.setChecked(true);

                    break;
            }

            drawerLayout.closeDrawers();
            return true;
        });
        loadUserAvatar();
    }

    private void updateShots() {
        BusProvider.getInstance().post(new Event.UpdateShotEvent(SHOT_TYPE_STRING, SORT_TYPE_STRING));
    }

    public void onClickHeader(View view) {
        if (isLoaded) {

        } else {
            loadUserAvatar();
        }
    }

    /**
     * load the header view, wrapper for {@link MainPresenter#loadUserAvatar()}
     */
    private void loadUserAvatar() {
        presenter.loadUserAvatar();
    }

    private void setTabText(CharSequence text, CalligraphyTypefaceSpan typefaceSpan) {
        SpannableStringBuilder s = new SpannableStringBuilder(text);
        s.setSpan(typefaceSpan, 0, text.length(), 0);
        tabLayout.getTabAt(0).setText(s);
    }

    private void handleSortButton(final MenuItem item, int which) {
        switch (which) {
            case R.id.popularity:
                SORT_TYPE_STRING = Constant.POPULARITY;
                updateShots();
                item.setIcon(R.drawable.dpball);
                break;
            case R.id.comments:
                SORT_TYPE_STRING = Constant.COMMENTS;
                updateShots();
                item.setIcon(R.drawable.dpcomments);
                break;
            case R.id.recent:
                SORT_TYPE_STRING = Constant.RECENT;
                updateShots();
                item.setIcon(R.drawable.dprecent);
                break;
            case R.id.views:
                SORT_TYPE_STRING = Constant.VIEWS;
                updateShots();
                item.setIcon(R.drawable.dpviews);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        mToggle.syncState();
        super.onPostCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.sort:
                if (SettingsUtil.isDarkMode()) {
                    new BottomSheet.Builder(this).darkTheme().title("Sort by").sheet(R.menu.bottom).listener((dialog, which) -> {
                        handleSortButton(item, which);
                    }).show();
                } else {
                    new BottomSheet.Builder(this).title("Sort by").sheet(R.menu.bottom).listener((dialog, which) -> {
                        handleSortButton(item, which);
                    }).show();
                }
                break;
            case R.id.about:
                startActivity(new Intent(this, AboutActivity.class));
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            case R.id.help:
                PreferenceUtil.setPreBoolean(Config.PRE_FROM_MAIN_KEY, true);
                startActivity(new Intent(this, TourActivity.class));
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isExit;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit) {
                this.finish();
            } else {
                Toast.makeText(this, "Press back again to exit", Toast.LENGTH_LONG).show();
                isExit = true;
                new Handler().postDelayed(() -> isExit = false, 2000);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void updateUserAvatar(User user) {
        final int avatarSize = getResources().getDimensionPixelSize(R.dimen.user);
        if (user != null) {
            isLoaded = true;
            PigggeonApp.setUser(user);
            PigggeonApp.setUserId(user.getId());
            userName.setText(user.getName());
            userBio.setText(user.getBio() != null ? user.getBio() : "");
            Glide.with(AppData.getContext())
                    .asDrawable()
                    .load(user.getAvatarUrl())
                    .apply(new MyRequestOptions()
                            .override(avatarSize, avatarSize)
                            .centerCrop(AppData.getContext())
                            .transform(AppData.getContext(), new GlideCircleTransform(AppData.getContext())))
                    .into(header);

            Glide.with(AppData.getContext())
                    .asBitmap()
                    .load(user.getAvatarUrl())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            blurBack.setImageBitmap(resource);
                            Blurry.with(AppData.getContext()).capture(blurBack).into(blurBack);
                        }
                    });
        }
    }

    @Override
    public void setError(String error) {
        Snackbar.make(drawerLayout, error, Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY", v -> {
                    loadUserAvatar();
                });
        isLoaded = false;
    }

    /**
     * main adapter for ViewPager
     */
    static class Adapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragments = new ArrayList<>();

        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }


        @Override
        public CharSequence getPageTitle(int position) {
            //we change tab font here
            CharSequence text = mFragmentTitles.get(position);
            CalligraphyTypefaceSpan typefaceSpan = new CalligraphyTypefaceSpan(
                    TypefaceUtils.load(AppData.getContext().getAssets(), "fonts/Comfortaa-Regular.ttf"));
            SpannableStringBuilder s = new SpannableStringBuilder(text);
            s.setSpan(typefaceSpan, 0, text.length(), 0);
            return s;
        }
    }


}
