package com.me.xpf.pigggeon.ui.adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.me.xpf.pigggeon.R;
import com.me.xpf.pigggeon.base.MyRequestOptions;
import com.me.xpf.pigggeon.config.Config;
import com.me.xpf.pigggeon.model.api.Shot;
import com.me.xpf.pigggeon.ui.activity.ShotDetailActivity;
import com.me.xpf.pigggeon.utils.PreferenceUtil;
import com.me.xpf.pigggeon.utils.SettingsUtil;
import com.me.xpf.pigggeon.widget.PigggeonLoadAnimationView;
import com.me.xpf.pigggeon.widget.animation.GlideCircleTransform;
import com.xpf.me.architect.app.AppData;
import com.xpf.me.architect.recyclerview.BaseAdapter;
import com.xpf.me.architect.recyclerview.RecyclerHolder;
import com.xpf.me.architect.recyclerview.RequestManager;

import java.util.Collection;
import java.util.List;

/**
 * Created by pengfeixie on 16/1/29.
 */
public class ShotsAdapter extends BaseAdapter<Shot> {

    private int avatarSize;

    private Drawable mDefaultImageDrawable = AppData.getDrawable(R.drawable.loading);

    private MyRequestOptions myRequestOptions = new MyRequestOptions();

    public void setData(List<Shot> shotList) {
        this.realData = shotList;
        notifyDataSetChanged();
    }

    public void addAllData(List<Shot> shots) {
        this.realData.addAll(shots);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.realData.clear();
        notifyDataSetChanged();
    }

    public void reset(List<Shot> shots) {
        this.realData.clear();
        this.realData.addAll(shots);
        notifyDataSetChanged();
    }

    public ShotsAdapter(RecyclerView v, Collection<Shot> posts) {
        super(v, posts, R.layout.item_shot, R.layout.item_footer);
        this.avatarSize = mContext.getResources().getDimensionPixelSize(R.dimen.user_photo);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        if (getItemViewType(position) == VIEW_ITEM) {
            convert(holder, realData.get(position), position, isScrolling);
            holder.itemView.setOnClickListener(getOnClickListener(position));
        } else {
            ((PigggeonLoadAnimationView) holder.getView(R.id.load)).reset();
            //do nothing?
        }
    }

    @Override
    public void convert(RecyclerHolder recyclerHolder, Shot shot, int i, boolean b) {
        TextView userName = recyclerHolder.getView(R.id.user_name);
        ImageView userPhoto = recyclerHolder.getView(R.id.user_photo);
        TextView shotName = recyclerHolder.getView(R.id.shot_name);
        TextView textView = recyclerHolder.getView(R.id.textView);
        TextView textComment = recyclerHolder.getView(R.id.textComment);
        TextView textLike = recyclerHolder.getView(R.id.textLike);
        ImageView gifLogo = recyclerHolder.getView(R.id.gif_logo);
        ImageView shotPhoto = recyclerHolder.getView(R.id.shot_photo);

        if (SettingsUtil.isDarkMode()) {

        }


        if (PreferenceUtil.getPreString(Config.PRE_SHOT_KEY, "").equals("teams")) {
            userName.setText(shot.getTeam().getName());
            Glide.with(mContext)
                    .asDrawable()
                    .load(shot.getTeam().getAvatarUrl())
                    .apply(myRequestOptions.centerCrop(AppData.getContext())
                            .override(avatarSize, avatarSize)
                            .transform(AppData.getContext(), new GlideCircleTransform(AppData.getContext()))
                            .placeholder(R.drawable.ic_avatar_default))
                    .into(userPhoto);
        } else {
            userName.setText(shot.getUser().getName());
            Glide.with(mContext)
                    .asDrawable()
                    .load(shot.getUser().getAvatarUrl())
                    .apply(myRequestOptions.centerCrop(AppData.getContext())
                            .override(avatarSize, avatarSize)
                            .transform(AppData.getContext(), new GlideCircleTransform(AppData.getContext()))
                            .placeholder(R.drawable.ic_avatar_default))
                    .into(userPhoto);
        }
        shotName.setText(shot.getTitle());
        textView.setText(String.valueOf(shot.getViewsCount()));
        textComment.setText(String.valueOf(shot.getCommentsCount()));
        textLike.setText(String.valueOf(shot.getLikesCount()));

        if (shot.isGif()) {
            gifLogo.setVisibility(View.VISIBLE);
        } else {
            gifLogo.setVisibility(View.GONE);
        }
        if (recyclerHolder.mImageRequest != null) {
            recyclerHolder.mImageRequest.cancelRequest();
        }
        ImageLoader.ImageListener listener = RequestManager.getImageListener(shotPhoto, shotName, mDefaultImageDrawable, mDefaultImageDrawable, isScrolling);
        recyclerHolder.mImageRequest = RequestManager.loadImage(shot.getImages().getTeaser(), listener);

    }
}
