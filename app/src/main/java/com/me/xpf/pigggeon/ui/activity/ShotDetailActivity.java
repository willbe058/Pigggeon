package com.me.xpf.pigggeon.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.me.xpf.pigggeon.R;
import com.me.xpf.pigggeon.base.activity.BaseStatusBarTintMvpActivity;
import com.me.xpf.pigggeon.base.adapter.BaseHeaderFooterAdapter;
import com.me.xpf.pigggeon.event.BusProvider;
import com.me.xpf.pigggeon.model.api.Bucket;
import com.me.xpf.pigggeon.model.api.Comment;
import com.me.xpf.pigggeon.model.api.Shot;
import com.me.xpf.pigggeon.model.api.User;
import com.me.xpf.pigggeon.model.api.Userable;
import com.me.xpf.pigggeon.presenter.ShotDetailPresenter;
import com.me.xpf.pigggeon.ui.adapter.CommentAdapter;
import com.me.xpf.pigggeon.utils.SettingsUtil;
import com.me.xpf.pigggeon.view.ShotDetailView;
import com.me.xpf.pigggeon.widget.FavorLayout;
import com.me.xpf.pigggeon.widget.HtmlTextView;
import com.me.xpf.pigggeon.widget.MarginDecoration;
import com.me.xpf.pigggeon.widget.SquareImageView;
import com.me.xpf.pigggeon.widget.animation.GlideCircleTransform;
import com.xpf.me.architect.app.AppData;
import com.xpf.me.architect.fragment.MvpFragment;
import com.xpf.me.architect.recyclerview.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by pengfeixie on 16/1/31.
 */
public class ShotDetailActivity extends BaseStatusBarTintMvpActivity<ShotDetailView, ShotDetailPresenter>
        implements ShotDetailView,
        View.OnClickListener,
        CommentAdapter.OnAvatarClickListener,
        CommentAdapter.OnCommentClickListener {

    private static final String SER_SHOT = "serializable.shot";

    private static final String SER_USER = "serializable.user";

    protected MvpFragment.OnUpdateListener updateListener;

    private Shot mShot;

    private int avatarSize;

    private Userable mUser;

    private int vibrantColor;

    @Bind(R.id.toolbar_in_detail)
    Toolbar toolbar;

    @Bind(R.id.commentRecyclerView)
    RecyclerView recyclerView;

    @Bind(R.id.favor)
    FavorLayout favorLayout;

    @Bind(R.id.fab)
    FloatingActionButton likeFab;

    @Bind(R.id.progress)
    ProgressBar progressBar;

    @Bind(R.id.image)
    SquareImageView shotImage;

    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    private List<Comment> dataSet = new ArrayList<>();

    private CommentAdapter adapter;

    private GridLayoutManager layoutManager;

    public static void navigate(AppCompatActivity activity, Shot shot, Userable user) {

        Intent intent = new Intent(activity, ShotDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(SER_SHOT, shot);
        bundle.putSerializable(SER_USER, user);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    protected void setOnUpdateListener(MvpFragment.OnUpdateListener listener) {
        this.updateListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getIntent() != null) {
            mShot = (Shot) getIntent().getSerializableExtra(SER_SHOT);
            mUser = ((Userable) getIntent().getSerializableExtra(SER_USER));
            vibrantColor = getResources().getColor(R.color.colorAccent);
        } else {
            finish();
        }
        BusProvider.getInstance().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    protected void setupContentView() {
        setContentView(R.layout.activity_shot_detail);
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected void setupActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        avatarSize = getResources().getDimensionPixelSize(R.dimen.user_photo);
        likeFab.setEnabled(false);
//        checkIfLiked();
        likeFab.setOnClickListener(this);
        progressBar.setVisibility(View.VISIBLE);
        View header = LayoutInflater.from(this).inflate(R.layout.item_header, null);
        setUpHeaderView(header);
        setUpRecyclerView(recyclerView, header);
        loadComments(1);
    }

    private void setUpHeaderView(View header) {
        CardView desCard = ((CardView) header.findViewById(R.id.des));
        CardView countCard = ((CardView) header.findViewById(R.id.count));

        ImageView avatar = ((ImageView) header.findViewById(R.id.userPhoto));
//        avatar.setOnClickListener(v -> {
//            if (mShot.getUser() != null) {
//                UserProfileActivity.navigate(ShotDetailActivity.this, shot.getUser());
//                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
//            } else {
//                UserProfileActivity.navigate(ShotDetailActivity.this, user);
//                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
//            }
//
//        });
        TextView name = ((TextView) header.findViewById(R.id.userName));
        TextView createDate = ((TextView) header.findViewById(R.id.create_at));
        HtmlTextView description = ((HtmlTextView) header.findViewById(R.id.description));
        TextView tags = ((TextView) header.findViewById(R.id.tags));
        TextView commentText = ((TextView) header.findViewById(R.id.commentText));
        TextView bucketText = ((TextView) header.findViewById(R.id.bucketText));
        TextView likesText = ((TextView) header.findViewById(R.id.likesText));
        TextView reboundText = ((TextView) header.findViewById(R.id.reboundText));
        TextView attachText = ((TextView) header.findViewById(R.id.attchText));
        TextView viewText = ((TextView) header.findViewById(R.id.viewText));


        Button createBucket = ((Button) header.findViewById(R.id.create_bucket));
        createBucket.setOnClickListener(this);

        Button commentButton = ((Button) header.findViewById(R.id.create_comment));
        commentButton.setOnClickListener(this);

        if (SettingsUtil.isDarkMode()) {
            int dark = AppData.getColor(R.color.darkTextTitle);
            desCard.setCardBackgroundColor(AppData.getColor(R.color.darkCard));
            countCard.setCardBackgroundColor(AppData.getColor(R.color.darkCard));
            name.setTextColor(AppData.getColor(R.color.darkTextTitle));
            description.setTextColor(AppData.getColor(R.color.darkTextTitle));
        }

        if (mShot.getUser() != null) {
            Glide.with(AppData.getContext())
                    .load(mShot.getUser().getAvatarUrl())
                    .override(avatarSize, avatarSize)
                    .centerCrop()
                    .transform(new GlideCircleTransform(AppData.getContext()))
                    .into(avatar);
            name.setText(mShot.getUser().getName());
        } else {

            Glide.with(AppData.getContext())
                    .load(mUser.getAvatarUrl())
                    .override(avatarSize, avatarSize)
                    .centerCrop()
                    .transform(new GlideCircleTransform(AppData.getContext()))
                    .into(avatar);
            name.setText(mUser.getName());
        }

        String[] re = mShot.getCreatedAt().split("T");
        createDate.setText(re[0]);
        if (mShot.getDescription() != null && !TextUtils.isEmpty(mShot.getDescription())) {
            description.setMovementMethod(LinkMovementMethod.getInstance());
            description.setText(mShot.getDescription());

        } else {
            description.setText(getResources().getString(R.string.no_des));
        }
        if (mShot.getTags().size() != 0) {
            StringBuffer tagStr = new StringBuffer();
            int count = mShot.getTags().size();
            for (int i = 0; i < count; i++) {
                if (i == count - 1) {
                    tagStr.append(mShot.getTags().get(i));
                } else {
                    tagStr.append(mShot.getTags().get(i)).append(", ");
                }
            }
            tags.setText(tagStr);
        } else {
            tags.setText(getResources().getString(R.string.no_tag));
        }

        String COMMENTS = "%d comments";
        commentText.setText(String.format(COMMENTS, mShot.getCommentsCount()));
        String REBOUND = "%d rebounds";
        reboundText.setText(String.format(REBOUND, mShot.getReboundsCount()));
        String ATTACH = "%d attachments";
        attachText.setText(String.format(ATTACH, mShot.getAttachmentsCount()));
        String LIKES = "%d likes";
        likesText.setText(String.format(LIKES, mShot.getLikesCount()));
        String VIEWS = "%d views";
        viewText.setText(String.format(VIEWS, mShot.getViewsCount()));
        String BUCKET = "%d buckets";
        bucketText.setText(String.format(BUCKET, mShot.getBucketsCount()));
    }

    protected void setUpRecyclerView(RecyclerView recyclerView, View header) {
        layoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        adapter = new CommentAdapter(recyclerView, dataSet, header);
        adapter.setOnAvatarClickListner(this);
        adapter.setOnCommentClickListener(this);
        adapter.setOnLoadMoreListener(new BaseHeaderFooterAdapter.OnLoadMoreListener() {
            @Override
            public void loadMore(int page) {
                loadComments(page);
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new MarginDecoration(AppData.getContext()));
        recyclerView.setAdapter(adapter);
        setOnUpdateListener(adapter);
    }

    private void loadComments(int page) {
        presenter.loadComments(mShot.getId(), page);
    }

    @NonNull
    @Override
    public ShotDetailPresenter createPresenter() {
        return new ShotDetailPresenter();
    }

    @Override
    public void progress(boolean isShow) {
        if (isShow) {

        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void setCommentList(List<Comment> commentList) {
        if (commentList.size() != 0) {
            this.dataSet = commentList;
            adapter.clearData();
            adapter.setData(dataSet);
            adapter.onUpdateFinished();
            recyclerView.smoothScrollToPosition(0);
        }
    }

    @Override
    public void setCommentListBottom(List<Comment> commentListBottom) {
        if (commentListBottom.size() != 0) {
            dataSet.remove(dataSet.size() - 1);
            adapter.notifyItemRemoved(dataSet.size());
            for (int i = 0; i < commentListBottom.size(); i++) {
                dataSet.add(commentListBottom.get(i));
                adapter.setData(dataSet);
                adapter.notifyItemInserted(dataSet.size());
            }
        } else {
            dataSet.remove(dataSet.size() - 1);
            adapter.notifyItemRemoved(dataSet.size());
        }
    }

    @Override
    public void setBucketList(List<Bucket> bucketList) {

    }

    @Override
    public void showError(String error) {
        Snackbar.make(recyclerView, error, Snackbar.LENGTH_INDEFINITE).setAction("RETRY", v -> {
            presenter.loadComments(mShot.getId(), 1);
        }).show();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onClickAvatar(View v, User u) {

    }

    @Override
    public void onClickComment(View v, Comment comment) {

    }
}
