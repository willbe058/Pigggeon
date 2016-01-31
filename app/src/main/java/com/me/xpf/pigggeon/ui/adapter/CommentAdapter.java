package com.me.xpf.pigggeon.ui.adapter;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.me.xpf.pigggeon.R;
import com.me.xpf.pigggeon.base.adapter.BaseHeaderFooterAdapter;
import com.me.xpf.pigggeon.model.api.Comment;
import com.me.xpf.pigggeon.model.api.Shot;
import com.me.xpf.pigggeon.model.api.User;
import com.me.xpf.pigggeon.utils.SettingsUtil;
import com.me.xpf.pigggeon.widget.animation.GlideCircleTransform;
import com.xpf.me.architect.app.AppData;
import com.xpf.me.architect.recyclerview.RecyclerHolder;

import java.util.Collection;
import java.util.List;

/**
 * Created by pengfeixie on 16/1/31.
 */
public class CommentAdapter extends BaseHeaderFooterAdapter<Comment> implements View.OnClickListener {

    private int avatarSize;

    private String[] re;

    private OnAvatarClickListener listener;

    private OnCommentClickListener onCommentClickListener;

    public void setOnAvatarClickListner(OnAvatarClickListener l) {
        this.listener = l;
    }

    public void setOnCommentClickListener(OnCommentClickListener clickListener) {
        this.onCommentClickListener = clickListener;
    }

    public void setData(List<Comment> commentList) {
        this.realData = commentList;
        notifyDataSetChanged();
    }

    public void addData(int position, Comment comment) {
        this.realData.add(position, comment);
        notifyDataSetChanged();
    }

    public void addAllData(List<Comment> comments) {
        this.realData.addAll(comments);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.realData.clear();
        notifyDataSetChanged();
    }

    public void reset(List<Comment> comments) {
        this.realData.clear();
        this.realData.addAll(comments);
        notifyDataSetChanged();
    }


    public CommentAdapter(RecyclerView v, Collection<Comment> data, View header) {
        super(v, data, R.layout.item_comment, R.layout.item_footer, header);
        this.avatarSize = AppData.getContext().getResources().getDimensionPixelSize(R.dimen.user_photo);
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(mItemLayoutId, parent, false);
        if (viewType == VIEW_ITEM) {
            RecyclerHolder holder = new RecyclerHolder(root);
            holder.getView(R.id.user_photo).setOnClickListener(this);
            holder.getView(R.id.item_view).setOnClickListener(this);
            return holder;
        } else if (viewType == VIEW_FOOTER) {
            return new RecyclerHolder(LayoutInflater.from(mContext).inflate(mItemFooterId, parent, false));
        } else {
            return new RecyclerHolder(mHeader);
        }
    }

    @Override
    public void convert(RecyclerHolder holder, Comment item, int position, boolean isScrolling) {
        if (SettingsUtil.isDarkMode()) {

        }
        holder.setText(R.id.user_name_in_comment, item.getUser().getName());
        holder.setText(R.id.comment_body, item.getBody());
        re = item.getCreatedAt().split("T");
        holder.setText(R.id.create_date, re[0]);
        holder.setText(R.id.like_count, String.valueOf(item.getLikesCount()));

        Glide.with(mContext).load(item.getUser().getAvatarUrl())
                .override(avatarSize, avatarSize)
                .placeholder(R.drawable.ic_avatar_default)
                .centerCrop()
                .transform(new GlideCircleTransform(mContext))
                .into(((ImageView) holder.getView(R.id.user_photo)));

        holder.getView(R.id.user_photo).setId(R.id.user_photo);
        holder.getView(R.id.user_photo).setTag(R.id.user_photo, item.getUser());

        holder.getView(R.id.item_view).setId(R.id.item_view);
        holder.getView(R.id.item_view).setTag(R.id.item_view, item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_photo:
                if (listener != null) {
                    new Handler().postDelayed(() -> listener.onClickAvatar(v, ((User) v.getTag(R.id.user_photo))), 100);
                }
                break;
            case R.id.item_view:
                if (onCommentClickListener != null) {
                    new Handler().postDelayed(() -> onCommentClickListener.onClickComment(v, ((Comment) v.getTag(R.id.item_view))), 100);
                }
                break;
        }

    }

    public interface OnAvatarClickListener {
        void onClickAvatar(View v, User u);
    }

    public interface OnCommentClickListener {
        void onClickComment(View v, Comment comment);
    }
}
