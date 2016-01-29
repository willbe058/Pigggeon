package com.me.xpf.pigggeon.http;

import com.me.xpf.pigggeon.app.PigggeonApp;
import com.me.xpf.pigggeon.config.Constant;
import com.me.xpf.pigggeon.model.api.AccessToken;
import com.me.xpf.pigggeon.model.api.Shot;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.util.List;

import retrofit.CallAdapter;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by pengfeixie on 16/1/29.
 */
public class ApiDribbble implements Constant {

    private static OkHttpClient okHttpClient = new OkHttpClient();

    //the retrofit instance for access token
    private static Retrofit accessRetrofit = new Retrofit.Builder()
            .baseUrl(BASE_AUTH_RUL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();

    private static AccessTokenService accessTokenService = accessRetrofit.create(AccessTokenService.class);

    public static AccessTokenService accessToken() {
        return accessTokenService;
    }

    public interface AccessTokenService {

        @POST("token")
        Observable<AccessToken> getAccessToken(
                @Query("client_id") String clientId,
                @Query("client_secret") String secret,
                @Query("code") String code);
    }

    /**
     * all the api call happens here
     *
     * @return
     */
    public static DribbbleService dribbble() {

        okHttpClient.networkInterceptors().add(chain -> {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("Cache-Control", "no-cache")
                    .addHeader("Authorization", PigggeonApp.getClientAccessToken())
                    .build();
            return chain.proceed(request);
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(DribbbleService.class);
    }

    public interface DribbbleService {

        @GET("shots")
        Observable<List<Shot>> shots(
                @Query(PAGE) int page,
                @Query(PER_PAGE) int perPage,
                @Query(LIST) String list,
                @Query(SORT) String sortBy);
    }

}
