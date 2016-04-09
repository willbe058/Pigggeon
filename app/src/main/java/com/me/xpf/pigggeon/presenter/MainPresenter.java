package com.me.xpf.pigggeon.presenter;

import android.app.Activity;
import android.content.Intent;

import com.me.xpf.pigggeon.config.Config;
import com.me.xpf.pigggeon.http.ApiDribbble;
import com.me.xpf.pigggeon.model.entity.User;
import com.me.xpf.pigggeon.model.usecase.LogoutUsecase;
import com.me.xpf.pigggeon.ui.activity.LoginActivity;
import com.me.xpf.pigggeon.utils.PreferenceUtil;
import com.me.xpf.pigggeon.view.MainView;
import com.umeng.fb.FeedbackAgent;
import com.xpf.me.architect.app.AppData;
import com.xpf.me.architect.presenter.BasePresenter;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by pengfeixie on 16/4/9.
 */
public class MainPresenter extends BasePresenter<MainView> {

    /**
     * init for sth
     */
    public void onCreate() {
        FeedbackAgent agent = new FeedbackAgent(getView().getContext() != null
                ? getView().getContext()
                : AppData.getContext());
        agent.closeAudioFeedback();
        //sync user's feedback
        agent.sync();
    }

    public void loadUserAvatar() {
        ApiDribbble.dribbble().user().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView() != null) {
                            getView().setError(e.getLocalizedMessage());
                        }
                    }

                    @Override
                    public void onNext(User user) {
                        if (getView() != null) {
                            getView().updateUserAvatar(user);
                        }
                    }
                });
    }

    public void logOut() {
        if (getView() != null) {
            getView().showProgress(true);
        }
        new LogoutUsecase().clearCookie().finallyDo(() -> getView().showProgress(false))
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {
                        if (getView() != null) {
                            PreferenceUtil.setPreBoolean(Config.PRE_IS_LOG_KEY, false);
                            PreferenceUtil.setPreString(Config.PRE_ACCESS_TOKEN_KEY, null);
                            getView().getContext().startActivity(new Intent(getView().getContext(), LoginActivity.class));
                            ((Activity) getView().getContext()).finish();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView() != null) {
                            getView().setError(e.getLocalizedMessage());
                        }
                    }

                    @Override
                    public void onNext(Object o) {

                    }
                });
    }
}
