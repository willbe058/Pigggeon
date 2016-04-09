package com.me.xpf.pigggeon.model.usecase;

import com.me.xpf.pigggeon.config.Config;
import com.me.xpf.pigggeon.http.ApiDribbble;
import com.me.xpf.pigggeon.model.entity.Shot;
import com.xpf.me.architect.model.IModel;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by pengfeixie on 16/1/29.
 */
public class ShotUsecase implements IModel<List<Shot>> {



    @Override
    public Observable<List<Shot>> execute() {
        return null;
    }

    public Observable<List<Shot>> getShots(String shot, String sort, int page) {
        Observable<List<Shot>> observable = ApiDribbble.dribbble().shots(page, Config.PER_PAGE, shot, sort);
        return observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Shot>> getFollowings(int page) {
        return ApiDribbble.dribbble().followings(page, Config.PER_PAGE)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
