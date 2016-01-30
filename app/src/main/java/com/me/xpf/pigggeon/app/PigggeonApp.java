package com.me.xpf.pigggeon.app;

import com.me.xpf.pigggeon.R;
import com.me.xpf.pigggeon.config.Config;
import com.me.xpf.pigggeon.model.api.User;
import com.me.xpf.pigggeon.utils.PreferenceUtil;
import com.xpf.me.architect.app.CommonApplication;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by pengfeixie on 16/1/29.
 */
public class PigggeonApp extends CommonApplication {

    public final static String CLIENT_ID = "c72c35c6e476af62e876151b2ceac51ddea15bdaa3a3ba686892dec985545694";

    public final static String CLIENT_SECRET = "0ac6e21b171ac9413fc7a1860fd7126ad5cc683b7c6fe05b02db2b52e957edc4";

    private static int userId;

    private static User me;

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Comfortaa-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

    public static String getClientAccessToken() {

        return PreferenceUtil.getPreString(Config.PRE_ACCESS_TOKEN_KEY, "");
    }

    public static void setUserId(int id) {
        userId = id;
    }

    public static int getUserId() {
        return userId;
    }

    public static void setUser(User user) {
        me = user;
    }

    public static User getUser() {
        return me;
    }

    public static boolean isMyself(int id) {
        return id == userId;
    }

}
