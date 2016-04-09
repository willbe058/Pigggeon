package com.me.xpf.pigggeon.presenter;

import android.widget.Toast;

import com.me.xpf.pigggeon.helper.BucketManager;
import com.me.xpf.pigggeon.model.entity.BucketWrapper;
import com.me.xpf.pigggeon.model.entity.Comment;
import com.me.xpf.pigggeon.model.usecase.CommentUsecase;
import com.me.xpf.pigggeon.view.ShotDetailView;
import com.xpf.me.architect.app.AppData;
import com.xpf.me.architect.model.IModel;
import com.xpf.me.architect.presenter.BasePresenter;

import java.util.List;

import rx.Subscriber;

/**
 * Created by pengfeixie on 16/1/31.
 */
public class ShotDetailPresenter extends BasePresenter<ShotDetailView> {

    private IModel<List<Comment>> commentUsecase;


    public void loadBuckets() {
        if (getView() != null) {
            getView().progress(true);
        }
        BucketManager.getInstance().loadBuckets(new BucketManager.OnBucketLoadCallback() {
            @Override
            public void onSuccess(List<BucketWrapper> bucketWrappers) {
                if (getView() != null) {
                    getView().progress(false);
                    getView().setBucketList(bucketWrappers);
                }
            }

            @Override
            public void onFail(String message) {
                if (getView() != null) {
                    getView().progress(false);
                    Toast.makeText(AppData.getContext(), message, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void loadComments(int id, int page) {
        commentUsecase = new CommentUsecase(id, page);
        if (getView() != null) {
            commentUsecase.execute()
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
