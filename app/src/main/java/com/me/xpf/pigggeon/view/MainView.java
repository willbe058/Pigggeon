package com.me.xpf.pigggeon.view;

import com.me.xpf.pigggeon.model.entity.User;
import com.xpf.me.architect.view.IView;

/**
 * Created by pengfeixie on 16/4/9.
 */
public interface MainView extends IView {

    void updateUserAvatar(User user);

    void setError(String error);

}
