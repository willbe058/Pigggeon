package com.me.xpf.pigggeon.view;

import com.me.xpf.pigggeon.model.entity.BucketWrapper;
import com.me.xpf.pigggeon.model.entity.Comment;
import com.xpf.me.architect.view.IView;

import java.util.List;

/**
 * Created by pengfeixie on 16/1/31.
 */
public interface ShotDetailView extends IView {

    void progress(boolean isShow);

    void setCommentList(List<Comment> commentList);

    void setCommentListBottom(List<Comment> commentListBottom);

    void setBucketList(List<BucketWrapper> bucketList);

    void showError(String error);
}
