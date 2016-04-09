package com.me.xpf.pigggeon.model.usecase;

import com.me.xpf.pigggeon.config.Config;
import com.me.xpf.pigggeon.http.ApiDribbble;
import com.me.xpf.pigggeon.model.entity.Comment;
import com.xpf.me.architect.model.IModel;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by pengfeixie on 16/1/31.
 */
public class CommentUsecase implements IModel<List<Comment>> {

    private int mId, mPage;

    public CommentUsecase(int id, int page) {
        this.mId = id;
        this.mPage = page;
    }

    @Override
    public Observable<List<Comment>> execute() {
        return getComments(mId, mPage);
    }

    private Observable<List<Comment>> getComments(int id, int page) {
        return ApiDribbble.dribbble().comments(id, page, Config.PER_PAGE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread());
    }
}
