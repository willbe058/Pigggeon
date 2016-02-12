package com.me.xpf.pigggeon.model.usecase;

import android.util.Log;

import com.me.xpf.pigggeon.http.ApiDribbble;
import com.me.xpf.pigggeon.model.BucketWrapper;
import com.me.xpf.pigggeon.model.api.Bucket;
import com.me.xpf.pigggeon.model.api.Shot;
import com.xpf.me.architect.model.IModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by pengfeixie on 16/2/4.
 */
public class BucketsUsecase {

    private List<BucketWrapper> wrappers = new ArrayList<>();

    private List<Bucket> tempBuckets = new ArrayList<>();

    private List<String> tempUrls = new ArrayList<>();

    public Observable<List<Bucket>> execute() {
        Observable<List<Bucket>> observable = ApiDribbble.dribbble()
                .buckets().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread());

        return observable;

    }
}
