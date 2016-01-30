package com.me.xpf.pigggeon.presenter;

import com.me.xpf.pigggeon.model.api.Shot;
import com.me.xpf.pigggeon.model.usecase.ShotUsecase;
import com.me.xpf.pigggeon.view.ShotsView;
import com.xpf.me.architect.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by pengfeixie on 16/1/29.
 */
public class ShotsPresenter extends BasePresenter<ShotsView> {

    private ShotUsecase usecase = new ShotUsecase();

    private BlockingQueue<Subscription> subscriptions = new ArrayBlockingQueue<>(2);

    public void loadShots(String shot, String sort, int page) {

        if (getView() != null) {
            Subscription subscription = usecase.getShots(shot, sort, page)
                    .finallyDo(() -> {
                        if (getView() != null) {
                            getView().progress(false);
                        }
                    })
                    .subscribe(new Subscriber<List<Shot>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            if (getView() != null) {
                                if (page == 1) {
                                    getView().showError(e.getLocalizedMessage());
                                } else {
                                    getView().showErrorBottom(e.getLocalizedMessage());
                                }
                            }
                        }

                        @Override
                        public void onNext(List<com.me.xpf.pigggeon.model.api.Shot> shots) {
                            if (getView() != null) {
                                if (page == 1) {
                                    getView().setData(shots);
                                } else {
                                    getView().setDataBottom(shots);
                                }
                            }
                        }
                    });
            try {
                subscriptions.put(subscription);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void cancel() {
        if (subscriptions.size() >= 2) {
            subscriptions.poll().unsubscribe();
        }
    }
}
