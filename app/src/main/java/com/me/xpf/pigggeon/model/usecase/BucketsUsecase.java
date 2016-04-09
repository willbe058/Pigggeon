package com.me.xpf.pigggeon.model.usecase;

import com.me.xpf.pigggeon.http.ApiDribbble;
import com.me.xpf.pigggeon.model.entity.Bucket;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by pengfeixie on 16/2/4.
 */
public class BucketsUsecase {

    public Observable<List<Bucket>> execute() {

        return ApiDribbble.dribbble()
                .buckets().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread());
    }
}
