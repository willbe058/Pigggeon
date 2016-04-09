package com.me.xpf.pigggeon.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
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
import com.me.xpf.pigggeon.base.activity.BaseStatusBarTintActivity;
import com.me.xpf.pigggeon.config.Config;
import com.me.xpf.pigggeon.config.Constant;
import com.me.xpf.pigggeon.event.BusProvider;
import com.me.xpf.pigggeon.event.Event;
import com.me.xpf.pigggeon.http.ApiDribbble;
import com.me.xpf.pigggeon.model.entity.User;
import com.me.xpf.pigggeon.model.usecase.LogoutUsecase;
import com.me.xpf.pigggeon.ui.fragment.ShotsFragment;
import com.me.xpf.pigggeon.utils.PreferenceUtil;
import com.me.xpf.pigggeon.utils.SettingsUtil;
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
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

public class MainActivity extends BaseStatusBarTintActivity {

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

    private View headerView;

    private ImageView blurBack;

    @Bind(R.id.nav_view)
    NavigationView navigationView;

    private CalligraphyTypefaceSpan typefaceSpan;

    private String token;

    private ActionBarDrawerToggle mToggle;

    private boolean isLoaded;

    private String mShot = "", mSort = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
        typefaceSpan = new CalligraphyTypefaceSpan(
                TypefaceUtils.load(AppData.getContext().getAssets(), "fonts/Comfortaa-Regular.ttf"));
        checkToken();
        FeedbackAgent agent = new FeedbackAgent(this);
        agent.closeAudioFeedback();
        //sync user's feedback
        agent.sync();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

    private void checkToken() {
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
        drawerLayout.setDrawerListener(mToggle);

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
        headerView = navigationView.getHeaderView(0);
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
                                new LogoutUsecase()
                                        .clearCookie()
                                        .finallyDo(progressDialog::dismiss)
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
                    mShot = "";
                    PreferenceUtil.setPreString(Config.PRE_SHOT_KEY, Constant.SHOTS);
                    updateShots();
                    text = "SHOTS";
                    setTabText(text, typefaceSpan);
                    menuItem.setChecked(true);

                    break;
                case R.id.teams:
                    mShot = Constant.TEAMS;
                    PreferenceUtil.setPreString(Config.PRE_SHOT_KEY, Constant.TEAMS);
                    updateShots();
                    text = "TEAMS";
                    setTabText(text, typefaceSpan);
                    menuItem.setChecked(true);

                    break;
                case R.id.debuts:
                    mShot = Constant.DEBUTS;
                    PreferenceUtil.setPreString(Config.PRE_SHOT_KEY, Constant.DEBUTS);
                    updateShots();
                    text = "DEBUTS";
                    setTabText(text, typefaceSpan);
                    menuItem.setChecked(true);

                    break;
                case R.id.playoffs:
                    mShot = Constant.PLAY_OFFS;
                    PreferenceUtil.setPreString(Config.PRE_SHOT_KEY, Constant.PLAY_OFFS);
                    updateShots();
                    text = "PLAYOFFS";
                    setTabText(text, typefaceSpan);
                    menuItem.setChecked(true);

                    break;
                case R.id.rebounds:
                    mShot = Constant.REBOUNDS;
                    PreferenceUtil.setPreString(Config.PRE_SHOT_KEY, Constant.REBOUNDS);
                    updateShots();
                    text = "REBOUNDS";
                    setTabText(text, typefaceSpan);
                    menuItem.setChecked(true);

                    break;
                case R.id.animated:
                    mShot = Constant.ANIMATED;
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
        BusProvider.getInstance().post(new Event.UpdateShotEvent(mShot, mSort));
    }

    public void onClickHeader(View view) {
        if (isLoaded) {

        } else {
            loadUserAvatar();
        }
    }

    /**
     * load the header view
     */
    private void loadUserAvatar() {
        final int avatarSize = getResources().getDimensionPixelSize(R.dimen.user);
        ApiDribbble.dribbble().user().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Snackbar.make(drawerLayout, e.getLocalizedMessage(), Snackbar.LENGTH_INDEFINITE)
                                .setAction("RETRY", v -> {
                                    loadUserAvatar();
                                });
                        isLoaded = false;
                    }

                    @Override
                    public void onNext(User user) {
                        if (user != null) {
                            isLoaded = true;
                            PigggeonApp.setUser(user);
                            PigggeonApp.setUserId(user.getId());
                            userName.setText(user.getName());
                            userBio.setText(user.getBio() != null ? user.getBio() : "");
                            Glide.with(AppData.getContext())
                                    .asDrawable()
                                    .load(user.getAvatarUrl())
                                    .apply(new ShotDetailActivity.MyRequestOptions()
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
                });
    }

    private void setTabText(CharSequence text, CalligraphyTypefaceSpan typefaceSpan) {
        SpannableStringBuilder s = new SpannableStringBuilder(text);
        s.setSpan(typefaceSpan, 0, text.length(), 0);
        tabLayout.getTabAt(0).setText(s);
    }

    private void handleSortButton(final MenuItem item, int which) {
        switch (which) {
            case R.id.popularity:
                mSort = Constant.POPULARITY;
                updateShots();
                item.setIcon(R.drawable.dpball);
                break;
            case R.id.comments:
                mSort = Constant.COMMENTS;
                updateShots();
                item.setIcon(R.drawable.dpcomments);
                break;
            case R.id.recent:
                mSort = Constant.RECENT;
                updateShots();
                item.setIcon(R.drawable.dprecent);
                break;
            case R.id.views:
                mSort = Constant.VIEWS;
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
            CharSequence text = mFragmentTitles.get(position);
            CalligraphyTypefaceSpan typefaceSpan = new CalligraphyTypefaceSpan(
                    TypefaceUtils.load(AppData.getContext().getAssets(), "fonts/Comfortaa-Regular.ttf"));
            SpannableStringBuilder s = new SpannableStringBuilder(text);
            s.setSpan(typefaceSpan, 0, text.length(), 0);
            return s;
        }
    }


}
