package com.me.xpf.pigggeon.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.me.xpf.pigggeon.R;
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

import java.util.Collection;
import java.util.List;

/**
 * Created by pengfeixie on 16/1/29.
 */
public class ShotsAdapter extends BaseAdapter<Shot> {

    private int avatarSize;

    private ShotDetailActivity.TEST test = new ShotDetailActivity.TEST();

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
        if (SettingsUtil.isDarkMode()) {

        }

        if (PreferenceUtil.getPreString(Config.PRE_SHOT_KEY, "").equals("teams")) {
            recyclerHolder.setText(R.id.user_name, shot.getTeam().getName());
            Glide.with(mContext)
                    .load(shot.getTeam().getAvatarUrl())
                    .apply(test.centerCrop(AppData.getContext())
                            .override(avatarSize, avatarSize)
                            .transform(AppData.getContext(), new GlideCircleTransform(AppData.getContext()))
                            .placeholder(R.drawable.ic_avatar_default))
                    .into(((ImageView) recyclerHolder.getView(R.id.user_photo)));
        } else {
            recyclerHolder.setText(R.id.user_name, shot.getUser().getName());
            Glide.with(mContext)
                    .load(shot.getUser().getAvatarUrl())
                    .apply(test.centerCrop(AppData.getContext())
                            .override(avatarSize, avatarSize)
                            .transform(AppData.getContext(), new GlideCircleTransform(AppData.getContext()))
                            .placeholder(R.drawable.ic_avatar_default))
                    .into(((ImageView) recyclerHolder.getView(R.id.user_photo)));
        }
        recyclerHolder.setText(R.id.shot_name, shot.getTitle());
        recyclerHolder.setText(R.id.textView, String.valueOf(shot.getViewsCount()));
        recyclerHolder.setText(R.id.textComment, String.valueOf(shot.getCommentsCount()));
        recyclerHolder.setText(R.id.textLike, String.valueOf(shot.getLikesCount()));

        if (shot.isGif()) {
            recyclerHolder.getView(R.id.gif_logo).setVisibility(View.VISIBLE);
        } else {
            recyclerHolder.getView(R.id.gif_logo).setVisibility(View.GONE);
        }
        Glide.with(mContext)
                .load(shot.getImages().getTeaser())
                .apply(test.centerCrop(AppData.getContext())
                        .placeholder(R.drawable.loading))
                .into(((ImageView) recyclerHolder.getView(R.id.shot_photo)));
    }
}
