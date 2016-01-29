package com.me.xpf.pigggeon.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.me.xpf.pigggeon.R;
import com.me.xpf.pigggeon.config.Config;
import com.me.xpf.pigggeon.model.api.Shot;
import com.me.xpf.pigggeon.utils.PreferenceUtil;
import com.me.xpf.pigggeon.utils.SettingsUtil;
import com.xpf.me.architect.recyclerview.BaseAdapter;
import com.xpf.me.architect.recyclerview.RecyclerHolder;

import java.util.Collection;
import java.util.List;

/**
 * Created by pengfeixie on 16/1/29.
 */
public class ShotsAdapter extends BaseAdapter<Shot> {

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
    }

    @Override
    public void convert(RecyclerHolder recyclerHolder, Shot shot, int i, boolean b) {
        if (SettingsUtil.isDarkMode()) {

        }

        if (PreferenceUtil.getPreString(Config.PRE_SHOT_KEY, "").equals("teams")) {
            recyclerHolder.setText(R.id.user_name, shot.getTeam().getName());
        } else {
            recyclerHolder.setText(R.id.user_name, shot.getUser().getName());
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


    }
}
