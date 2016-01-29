package com.me.xpf.pigggeon.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.xpf.pigggeon.R;
import com.me.xpf.pigggeon.app.PigggeonApp;
import com.me.xpf.pigggeon.base.activity.BaseStatusBarTintActivity;
import com.me.xpf.pigggeon.model.Shot;
import com.me.xpf.pigggeon.model.Sort;
import com.me.xpf.pigggeon.ui.fragment.ShotsFragment;
import com.me.xpf.pigggeon.widget.HtmlTextView;
import com.umeng.fb.FeedbackAgent;
import com.umeng.fb.fragment.FeedbackFragment;
import com.xpf.me.architect.app.AppData;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
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

    static ImageView blurBack;

    @Bind(R.id.nav_view)
    NavigationView navigationView;

    private CalligraphyTypefaceSpan typefaceSpan;

    private String token;

    private ActionBarDrawerToggle mToggle;

    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        typefaceSpan = new CalligraphyTypefaceSpan(
                TypefaceUtils.load(AppData.getContext().getAssets(), "fonts/Comfortaa-Regular.ttf"));
        checkToken();
        FeedbackAgent agent = new FeedbackAgent(this);
        agent.closeAudioFeedback();
        //sync user's feedback
        agent.sync();
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
        blurBack = ((ImageView) findViewById(R.id.backImg));
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
        adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(ShotsFragment.getInstance("", ""), "SHOTS");
        adapter.addFragment(new Fragment(), "FOLLOWING");
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
    }

    private void setupDrawerContent(final NavigationView navigationView) {
        headerView = navigationView.getHeaderView(0);
        header = ((ImageView) headerView.findViewById(R.id.header_photo));
        userName = ((TextView) headerView.findViewById(R.id.user_name_in_drawer));
        userBio = ((HtmlTextView) headerView.findViewById(R.id.bio));

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            CharSequence text = "SHOTS";
            switch (menuItem.getItemId()) {


            }

            menuItem.setChecked(true);
            drawerLayout.closeDrawers();
            return true;
        });
//        loadUserAvatar();
    }

    private void setTabText(CharSequence text, CalligraphyTypefaceSpan typefaceSpan) {
        SpannableStringBuilder s = new SpannableStringBuilder(text);
        s.setSpan(typefaceSpan, 0, text.length(), 0);
        tabLayout.getTabAt(0).setText(s);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
