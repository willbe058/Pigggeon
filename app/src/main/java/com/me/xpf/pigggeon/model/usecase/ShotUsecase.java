package com.me.xpf.pigggeon.model.usecase;

import com.me.xpf.pigggeon.config.Config;
import com.me.xpf.pigggeon.http.ApiDribbble;
import com.me.xpf.pigggeon.model.Sort;
import com.me.xpf.pigggeon.model.api.Shot;
import com.me.xpf.pigggeon.model.api.Team;
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

    public Observable<List<Shot>> getShots(com.me.xpf.pigggeon.model.Shot shot, Sort sort, int page) {
        Observable<List<Shot>> observable = ApiDribbble.dribbble().shots(page, Config.PER_PAGE, "", "");
        switch (shot) {
            case SHOTS:
                observable = ApiDribbble.dribbble().shots(page, Config.PER_PAGE, "", "");
                break;
            case TEAMS:
                break;
            case DEBUTS:
                break;
            case REBOUNDS:
                break;
            case PLAYOFFS:
                break;
            case ANIMATED:
                break;
        }
        return observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

//    private Observable<List<Team>> getTeams() {
//    }
}
