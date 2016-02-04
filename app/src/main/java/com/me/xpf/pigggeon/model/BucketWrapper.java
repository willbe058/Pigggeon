package com.me.xpf.pigggeon.model;

import com.me.xpf.pigggeon.model.api.Bucket;

/**
 * Created by pengfeixie on 16/2/4.
 */
public class BucketWrapper {

    private Bucket mBucket;

    private String mImageUrl;

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public Bucket getmBucket() {
        return mBucket;
    }

    public void setmBucket(Bucket mBucket) {
        this.mBucket = mBucket;
    }
}
