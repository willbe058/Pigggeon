package com.me.xpf.pigggeon.model.usecase;

import com.me.xpf.pigggeon.http.ApiDribbble;
import com.me.xpf.pigggeon.model.entity.Like;
import com.xpf.me.architect.model.IModel;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by pengfeixie on 16/2/3.
 */
public class LikeUsecase implements IModel<Like> {
    @Override
    public Observable<Like> execute() {
        return null;
    }

    public Observable<Like> isLike(int id) {
        return ApiDribbble.dribbble().isLike(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread());
    }

    public Observable<Like> like(int id) {
        return ApiDribbble.dribbble().like(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread());
    }

    public Observable<Like> unLike(int id) {
        return ApiDribbble.dribbble().unlike(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread());
    }
}
