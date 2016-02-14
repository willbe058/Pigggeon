package com.me.xpf.pigggeon.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.me.xpf.pigggeon.R;
import com.me.xpf.pigggeon.base.MyRequestOptions;
import com.me.xpf.pigggeon.model.BucketWrapper;
import com.me.xpf.pigggeon.ui.activity.ShotDetailActivity;
import com.me.xpf.pigggeon.utils.SettingsUtil;
import com.xpf.me.architect.app.AppData;
import com.xpf.me.architect.recyclerview.BaseAdapter;
import com.xpf.me.architect.recyclerview.RecyclerHolder;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by pengfeixie on 16/2/4.
 */
public class BucketAdapter extends BaseAdapter<BucketWrapper> implements View.OnClickListener {

    private OnBucketClickListener listener;
    private Set<Integer> positionSet = new HashSet<>();

    private MyRequestOptions myRequestOptions = new MyRequestOptions();

    public BucketAdapter(RecyclerView v, Collection<BucketWrapper> data) {
        super(v, data, R.layout.item_bucket, R.layout.item_footer);
    }

    @Override
    public void convert(RecyclerHolder recyclerHolder, BucketWrapper bucketWrapper, int position, boolean b) {
        if (bucketWrapper.getmImageUrl() != null) {
            Glide.with(mContext)
                    .asDrawable()
                    .load(bucketWrapper.getmImageUrl())
                    .apply(myRequestOptions.placeholder(R.drawable.loading))
                    .into(((ImageView) recyclerHolder.getView(R.id.img_bucket)));
        } else {
            Glide.with(mContext)
                    .asDrawable()
                    .load(R.drawable.noshot)
                    .apply(myRequestOptions.placeholder(R.drawable.loading))
                    .into(((ImageView) recyclerHolder.getView(R.id.img_bucket)));
        }

        recyclerHolder.getView(R.id.check).setTag(position);
        if (positionSet.contains(position)) {
            ((CheckBox) recyclerHolder.getView(R.id.check)).setChecked(true);
        } else {
            ((CheckBox) recyclerHolder.getView(R.id.check)).setChecked(false);
        }

        if (SettingsUtil.isDarkMode()) {
            ((TextView) recyclerHolder.getView(R.id.bucket_name)).setTextColor(AppData.getColor(R.color.darkTextTitle));
        }
        ((TextView) recyclerHolder.getView(R.id.bucket_name)).setText(bucketWrapper.getmBucket().getName());
        String shotCounts = "%d shots";
        ((TextView) recyclerHolder.getView(R.id.bucketCount)).setText(String.format(shotCounts, bucketWrapper.getmBucket().getShotsCount()));
        recyclerHolder.itemView.setTag(bucketWrapper);
        ((CheckBox) recyclerHolder.getView(R.id.check)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    positionSet.add(((Integer) buttonView.getTag()));
                    bucketWrapper.getmBucket().setIsChecked(positionSet.contains(buttonView.getTag()));

                } else {
                    positionSet.remove((buttonView.getTag()));
                    bucketWrapper.getmBucket().setIsChecked(positionSet.contains(buttonView.getTag()));
                }
            }
        });

        recyclerHolder.itemView.setOnClickListener(v -> {
            //never do it again! the checkbox find by id isn't the same one, so onCheckedChangeListener didn't work
            //CheckBox cb = ((CheckBox) v.findViewById(R.id.check));

            //this is the right way to do
            (((CheckBox) recyclerHolder.getView(R.id.check))).setChecked(!((CheckBox) recyclerHolder.getView(R.id.check)).isChecked());
        });
    }

    public List<BucketWrapper> getBuckets() {
        return realData;
    }

    @Override
    public void onClick(View v) {

    }

    public interface OnBucketClickListener {

        void onBucketClick(View view, BucketWrapper viewModel);

    }
}
