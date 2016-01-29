package com.me.xpf.pigggeon.view;

import com.me.xpf.pigggeon.model.api.Shot;
import com.xpf.me.architect.view.IView;

import java.util.List;

/**
 * Created by pengfeixie on 16/1/29.
 */
public interface ShotsView extends IView {

    void showError(String error);

    void showErrorBottom(String error);

    void progress(boolean isShow);

    void setData(List<Shot> shots);

    void setDataBottom(List<Shot> shots);

}
