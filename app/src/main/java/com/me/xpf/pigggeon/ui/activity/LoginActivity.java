package com.me.xpf.pigggeon.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatDialog;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.me.xpf.pigggeon.R;
import com.me.xpf.pigggeon.app.PigggeonApp;
import com.me.xpf.pigggeon.base.activity.BaseStatusBarTintActivity;
import com.me.xpf.pigggeon.config.Config;
import com.me.xpf.pigggeon.config.Constant;
import com.me.xpf.pigggeon.model.api.AccessToken;
import com.me.xpf.pigggeon.model.usecase.LoginUsecase;
import com.me.xpf.pigggeon.utils.PreferenceUtil;
import com.xpf.me.architect.app.AppData;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * Created by pengfeixie on 16/1/29.
 */
public class LoginActivity extends BaseStatusBarTintActivity implements Constant {

    @Bind(R.id.login)
    Button loginBtn;

    private WebView webView;

    private static final String CODE = "?code=";

    private static final String QUERY_CLIENT_ID = "?client_id=";

    private static final String QUERY_SCOPE = "&scope=public+write+comment";

    private AppCompatDialog dialog;

    @Override
    protected void initTheme() {
        //do nothing
    }

    @Override
    protected void setupContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected void setupActionBar() {

    }

    @Override
    protected void setupViews() {
        super.setupViews();
        if (PreferenceUtil.getPreBoolean(Config.PRE_IS_LOG_KEY, false)) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            finish();
        }

        loginBtn.setOnClickListener(v -> {
            dialog = new AppCompatDialog(v.getContext());
            dialog.setContentView(R.layout.webview);
            webView = ((WebView) dialog.findViewById(R.id.webView));
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setSaveFormData(false);
            settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

            //the dribbble auth api to get the code
            webView.loadUrl(AUTH_URL + QUERY_CLIENT_ID + PigggeonApp.CLIENT_ID + QUERY_SCOPE);
            webView.setWebViewClient(new PigggeonWebViewClient());
            dialog.show();
            dialog.setCancelable(true);
        });
    }

    private class PigggeonWebViewClient extends WebViewClient {

        boolean authComplete = false;

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (url.contains(CODE) && !authComplete) {
                webView.stopLoading();
                Uri uri = Uri.parse(url);
                authComplete = true;
                dialog.dismiss();

                final MaterialDialog progressDialog = new MaterialDialog.Builder(view.getContext())
                        .progressIndeterminateStyle(true)
                        .title("Contacting Dribbble...")
                        .cancelable(false)
                        .build();
                progressDialog.show();

                new LoginUsecase()
                        .getAccessToken(getCode(uri))
                        .finallyDo(progressDialog::dismiss)
                        .subscribe(new Subscriber<AccessToken>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(AppData.getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onNext(AccessToken accessToken) {
                                if (accessToken.getAccessToken() != null) {
                                    PreferenceUtil.setPreString(Config.PRE_ACCESS_TOKEN_KEY, "Bearer " + accessToken.getAccessToken());
                                    PreferenceUtil.setPreBoolean(Config.PRE_IS_LOG_KEY, true);
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("token", "Bearer " + accessToken.getAccessToken());
                                    startActivity(intent);
                                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "some thing is wrong during auth", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

            } else if (url.contains("error=access_denied")) {
                authComplete = true;
                Toast.makeText(getApplicationContext(), "Error during auth", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else {
                Log.i("unknown", url);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {

        }
    }

    private String getCode(Uri uri) {

        return uri.getQueryParameter("code");
    }
}
