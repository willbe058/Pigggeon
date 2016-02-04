package com.me.xpf.pigggeon.presenter;

import android.util.Log;

import com.me.xpf.pigggeon.http.ApiDribbble;
import com.me.xpf.pigggeon.model.BucketWrapper;
import com.me.xpf.pigggeon.model.api.Bucket;
import com.me.xpf.pigggeon.model.api.Comment;
import com.me.xpf.pigggeon.model.api.Shot;
import com.me.xpf.pigggeon.model.usecase.BucketsUsecase;
import com.me.xpf.pigggeon.model.usecase.CommentUsecase;
import com.me.xpf.pigggeon.view.ShotDetailView;
import com.xpf.me.architect.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by pengfeixie on 16/1/31.
 */
public class ShotDetailPresenter extends BasePresenter<ShotDetailView> {

    private CommentUsecase commentUsecase = new CommentUsecase();

    private BucketsUsecase bucketsUsecase = new BucketsUsecase();

    private List<BucketWrapper> wrappers = new ArrayList<>();

    private List<Bucket> tempBuckets = new ArrayList<>();

    private List<String> tempUrls = new ArrayList<>();

    public void loadBuckets() {
        if (getView() != null) {
            getView().progress(true);
        }
        bucketsUsecase.execute().flatMap(buckets -> {
            this.tempBuckets = buckets;
            return Observable.from(buckets);
        })
                .concatMap(bucket -> ApiDribbble.dribbble().getBucketImage(bucket.getId(), 1))
                .concatMap(shots -> {
                    if (shots.size() == 0) {
                        tempUrls.add(null);
                    } else {
                        tempUrls.add(shots.get(0).getImages().getTeaser());
                    }
                    return Observable.from(shots);
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Shot>() {
                    @Override
                    public void onCompleted() {
                        Log.i("buceks", tempBuckets.get(0).getName());
                        Log.i("urls", Arrays.toString(tempUrls.toArray()));
                        for (int i = 0; i < tempBuckets.size(); i++) {
                            BucketWrapper wrapper = new BucketWrapper();
                            wrapper.setmBucket(tempBuckets.get(i));
                            wrapper.setmImageUrl(tempUrls.get(i));
                            wrappers.add(wrapper);
                        }
                        if (getView() != null) {
                            getView().progress(false);
                            getView().setBucketList(wrappers);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(Shot shot) {
                        Log.i("shot", shot.getId() + "");
                    }
                });
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
