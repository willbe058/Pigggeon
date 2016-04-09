package com.me.xpf.pigggeon.model.usecase;

import com.me.xpf.pigggeon.app.PigggeonApp;
import com.me.xpf.pigggeon.http.ApiDribbble;
import com.me.xpf.pigggeon.model.entity.AccessToken;
import com.xpf.me.architect.model.IModel;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by pengfeixie on 16/1/29.
 */
public class LoginUsecase implements IModel<AccessToken> {

    private String code;

    public LoginUsecase(String code) {
        this.code = code;
    }

    @Override
    public Observable<AccessToken> execute() {
        return getAccessToken(code);
    }

    private Observable<AccessToken> getAccessToken(String code) {
        return ApiDribbble.accessToken()
                .getAccessToken(PigggeonApp.CLIENT_ID, PigggeonApp.CLIENT_SECRET, code)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
