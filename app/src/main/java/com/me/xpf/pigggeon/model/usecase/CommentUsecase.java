package com.me.xpf.pigggeon.model.usecase;

import com.me.xpf.pigggeon.config.Config;
import com.me.xpf.pigggeon.http.ApiDribbble;
import com.me.xpf.pigggeon.model.api.Comment;
import com.xpf.me.architect.model.IModel;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by pengfeixie on 16/1/31.
 */
public class CommentUsecase implements IModel<List<Comment>> {


    @Override
    public Observable<List<Comment>> execute() {
        return null;
    }

    public Observable<List<Comment>> getComments(int id, int page) {
        return ApiDribbble.dribbble().comments(id, page, Config.PER_PAGE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread());
    }
}
