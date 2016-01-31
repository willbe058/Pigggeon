package com.me.xpf.pigggeon.presenter;

import com.me.xpf.pigggeon.model.api.Comment;
import com.me.xpf.pigggeon.model.usecase.CommentUsecase;
import com.me.xpf.pigggeon.view.ShotDetailView;
import com.xpf.me.architect.presenter.BasePresenter;

import java.util.List;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by pengfeixie on 16/1/31.
 */
public class ShotDetailPresenter extends BasePresenter<ShotDetailView> {

    private CommentUsecase commentUsecase = new CommentUsecase();

    public void loadBuckets() {

    }

    public void loadComments(int id, int page) {
        if (getView() != null) {
            commentUsecase.getComments(id, page)
                    .finallyDo(() -> {
                        if (getView() != null) {
                            getView().progress(false);
                        }
                    })
                    .subscribe(new Subscriber<List<Comment>>() {
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
                        public void onNext(List<Comment> comments) {
                            if (page == 1) {
                                getView().setCommentList(comments);
                            } else {
                                getView().setCommentListBottom(comments);
                            }
                        }
                    });
        }
    }


}
