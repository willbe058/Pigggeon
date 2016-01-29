package com.me.xpf.pigggeon.presenter;

import com.me.xpf.pigggeon.model.Shot;
import com.me.xpf.pigggeon.model.Sort;
import com.me.xpf.pigggeon.model.usecase.ShotUsecase;
import com.me.xpf.pigggeon.view.ShotsView;
import com.xpf.me.architect.presenter.BasePresenter;

import java.util.List;

import rx.Subscriber;

/**
 * Created by pengfeixie on 16/1/29.
 */
public class ShotsPresenter extends BasePresenter<ShotsView> {

    private ShotUsecase usecase = new ShotUsecase();

    public void loadShots(Shot shot, Sort sort, int page) {

        if (getView() != null) {
            usecase.getShots(shot, sort, page)
                    .finallyDo(() -> {
                        if (getView() != null) {
                            getView().progress(false);
                        }
                    })
                    .subscribe(new Subscriber<List<com.me.xpf.pigggeon.model.api.Shot>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            if (getView() != null) {
                                getView().showError(e.getLocalizedMessage());
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
        }
    }
}
