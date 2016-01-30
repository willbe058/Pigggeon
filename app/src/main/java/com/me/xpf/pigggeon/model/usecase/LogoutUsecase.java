package com.me.xpf.pigggeon.model.usecase;

import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.xpf.me.architect.app.AppData;

import java.io.File;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by pengfeixie on 16/1/30.
 */
public class LogoutUsecase {

    public Observable<Object> clearCookie() {
        return Observable.create(subscriber -> {
            CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(AppData.getContext());
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.removeSessionCookie();
            cookieManager.removeAllCookie();
            cookieSyncManager.sync();
            try {
                String pathadmob = AppData.getContext().getFilesDir().getParent() + "/app_webview";
                File dir = new File(pathadmob);
                if (dir.isDirectory()) {
                    deleteDir(dir);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            subscriber.onCompleted();
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
        }
        // The directory is now empty so delete it
        return dir != null && dir.delete();

    }

}
