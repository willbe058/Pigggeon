package com.me.xpf.pigggeon.app;

import com.me.xpf.pigggeon.config.Config;
import com.me.xpf.pigggeon.utils.PreferenceUtil;
import com.xpf.me.architect.app.CommonApplication;

/**
 * Created by pengfeixie on 16/1/29.
 */
public class PigggeonApp extends CommonApplication {

    public final static String CLIENT_ID = "c72c35c6e476af62e876151b2ceac51ddea15bdaa3a3ba686892dec985545694";

    public final static String CLIENT_SECRET = "0ac6e21b171ac9413fc7a1860fd7126ad5cc683b7c6fe05b02db2b52e957edc4";

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public static String getClientAccessToken() {


        return PreferenceUtil.getPreString(Config.PRE_ACCESS_TOKEN_KEY, "");
    }

}
