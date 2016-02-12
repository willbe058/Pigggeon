package com.me.xpf.pigggeon.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.me.xpf.pigggeon.R;
import com.me.xpf.pigggeon.model.BucketWrapper;
import com.me.xpf.pigggeon.model.api.Bucket;
import com.me.xpf.pigggeon.ui.activity.ShotDetailActivity;
import com.me.xpf.pigggeon.widget.PigggeonLoadAnimationView;
import com.xpf.me.architect.app.AppData;
import com.xpf.me.architect.recyclerview.RequestManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by pengfeixie on 16/2/12.
 */
public class NewBucketAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private String shotCounts = "%d shots";
    private Context mContext;
    private List<BucketWrapper> mBuckets;
    private OnBucketClickListener listener;
    private Set<Integer> positionSet = new HashSet<>();

    private final int VIEW_ITEM = 1;
    private final int VIEW_PRO = 0;

    private ShotDetailActivity.MyRequestOptions myRequestOptions = new ShotDetailActivity.MyRequestOptions();

    public NewBucketAdapter(Context context, List<BucketWrapper> buckets) {
        this.mContext = context;
        this.mBuckets = buckets;

    }

    public void setOnBucketClickListener(OnBucketClickListener clickListener) {
        this.listener = clickListener;
    }

    public void setData(List<BucketWrapper> buckets) {
        this.mBuckets = buckets;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_bucket, parent, false);
            vh = new BucketViewHolder(view);
        } else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_footer, parent, false);

            vh = new ProgressViewHolder(v);
        }

        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof BucketViewHolder) {
            final BucketWrapper wrapper = mBuckets.get(position);
            if (wrapper.getmImageUrl() != null) {
                Glide.with(mContext)
                        .asDrawable()
                        .load(wrapper.getmImageUrl())
                        .apply(myRequestOptions.placeholder(R.drawable.loading))
                        .into(((BucketViewHolder) holder).bucketImg);
            } else {
                Glide.with(mContext)
                        .asDrawable()
                        .load(R.drawable.noshot)
                        .into(((BucketViewHolder) holder).bucketImg);
            }


            ((BucketViewHolder) holder).checkBox.setTag(position);

            if (positionSet.contains(position)) {
                ((BucketViewHolder) holder).checkBox.setChecked(true);
            } else {
                ((BucketViewHolder) holder).checkBox.setChecked(false);
            }

            if (PreferenceManager.getDefaultSharedPreferences(mContext).getString(mContext.getResources().getString(R.string.theme_type), "light").equals("dark")) {
                ((BucketViewHolder) holder).bucketName.setTextColor(mContext.getResources().getColor(R.color.darkTextTitle));
            }

            ((BucketViewHolder) holder).bucketName.setText(wrapper.getmBucket().getName());
            ((BucketViewHolder) holder).shotCount.setText(String.format(shotCounts, wrapper.getmBucket().getShotsCount()));
            holder.itemView.setTag(wrapper.getmBucket());
            ((BucketViewHolder) holder).checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Log.i("bucket t", String.valueOf(isChecked));
                        Log.i("bucket t", String.valueOf(positionSet.contains(buttonView.getTag())));
                        positionSet.add(((Integer) buttonView.getTag()));
                        wrapper.getmBucket().setIsChecked(positionSet.contains(buttonView.getTag()));

                    } else {
                        Log.i("bucket false", String.valueOf(!isChecked));
                        Log.i("bucket false", String.valueOf(!positionSet.contains(buttonView.getTag())));

                        positionSet.remove((buttonView.getTag()));
                        wrapper.getmBucket().setIsChecked(positionSet.contains(buttonView.getTag()));
                    }
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //never do it again! the checkbox find by id isn't the same one, so onCheckedChangeListener didn't work
//                CheckBox cb = ((CheckBox) v.findViewById(R.id.check));

                    //this is the right way to do
                    ((BucketViewHolder) holder).checkBox.setChecked(!((BucketViewHolder) holder).checkBox.isChecked());
                }
            });
        } else {
            ((ProgressViewHolder) holder).progressBar.reset();
        }


    }

    public List<BucketWrapper> getBuckets() {
        return mBuckets;
    }

    @Override
    public int getItemViewType(int position) {
        return mBuckets.get(position) != null ? VIEW_ITEM : VIEW_PRO;
    }

    @Override
    public int getItemCount() {
        return mBuckets.size();
    }

    @Override
    public void onClick(final View v) {

        if (listener != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    listener.onBucketClick(v, (Bucket) v.getTag());
                }
            }, 200);
        }
    }


    public interface OnBucketClickListener {

        void onBucketClick(View view, Bucket viewModel);

    }

    public class BucketViewHolder extends RecyclerView.ViewHolder {

        public ImageView bucketImg;
        public TextView bucketName, shotCount;
        public CheckBox checkBox;

        public BucketViewHolder(View itemView) {
            super(itemView);
            bucketImg = ((ImageView) itemView.findViewById(R.id.img_bucket));
            bucketName = ((TextView) itemView.findViewById(R.id.bucket_name));
            shotCount = ((TextView) itemView.findViewById(R.id.bucketCount));
            checkBox = ((CheckBox) itemView.findViewById(R.id.check));
            checkBox.setClickable(false);


        }
    }

    public class ProgressViewHolder extends RecyclerView.ViewHolder {

        public PigggeonLoadAnimationView progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (PigggeonLoadAnimationView) v.findViewById(R.id.load);
        }
    }
}
