package com.me.xpf.pigggeon.http;

import com.me.xpf.pigggeon.config.Constant;
import com.me.xpf.pigggeon.model.api.AccessToken;
import com.me.xpf.pigggeon.model.api.Shot;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by pengfeixie on 16/1/29.
 */
public class ApiDribbble implements Constant {

    private static OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();

    //the retrofit instance for access token
    private static Retrofit accessRetrofit = new Retrofit.Builder()
            .baseUrl(BASE_AUTH_RUL)
            .addConverterFactory(retrofit2.GsonConverterFactory.create())
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
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient.addNetworkInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .header("Authorization", "Bearer dc9c44800ea3c310d0dbd6ac0f94271e95cf408844e05874f10f1ac5c3e13744")
                    .header("Cache-Control", "no-cache")
                    .build();
            return chain.proceed(request);
        })
                .addInterceptor(interceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.build())
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
