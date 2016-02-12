package com.me.xpf.pigggeon.helper;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.me.xpf.pigggeon.http.ApiDribbble;
import com.me.xpf.pigggeon.model.BucketWrapper;
import com.me.xpf.pigggeon.model.api.Bucket;
import com.me.xpf.pigggeon.model.api.Shot;
import com.me.xpf.pigggeon.model.usecase.BucketsUsecase;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by pengfeixie on 16/2/12.
 */
public class BucketManager {

    private static volatile BucketManager singleton;

    private static Map<Integer, String> urlMap;

    private static List<BucketWrapper> bucketWrappers;

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;

    private static final int MAX_POOL_SIZE = CPU_COUNT * 2 + 1;

    private static final int KEEP_ALIVE = 1;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {

        private final AtomicInteger mCount = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {

            return new Thread(r, "Bucket #" + mCount.getAndIncrement());
        }
    };

    private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingDeque<>(128);

    public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAX_POOL_SIZE,
            KEEP_ALIVE,
            TimeUnit.SECONDS,
            sPoolWorkQueue,
            sThreadFactory);


    private static BucketHandler mHandler;

    static class BucketHandler extends Handler {

        private WeakReference<OnBucketLoadCallback> callbackWeakReference;

        public BucketHandler(OnBucketLoadCallback callback) {
            this.callbackWeakReference = new WeakReference<>(callback);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (callbackWeakReference.get() != null) {
                    callbackWeakReference.get().onSuccess(bucketWrappers);
                }
            }
        }
    }

    private BucketManager() {

    }

    public static BucketManager getInstance() {
        if (singleton == null) {
            synchronized (BucketManager.class) {
                if (singleton == null) {
                    singleton = new BucketManager();
                }
            }
        }
        return singleton;
    }

    public void loadBuckets(OnBucketLoadCallback callback) {

        new BucketsUsecase().execute().subscribe(new Subscriber<List<Bucket>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                callback.onFail(e.getLocalizedMessage());
            }

            @Override
            public void onNext(List<Bucket> buckets) {
                final CountDownLatch doneLatch = new CountDownLatch(buckets.size());
                final CountDownLatch startLatch = new CountDownLatch(1);
                mHandler = new BucketHandler(callback);
                bucketWrappers = new ArrayList<>();
                urlMap = new HashMap<>();
                Runnable runnable = () -> {
                    for (int i = 0; i < buckets.size(); i++) {

                        BucketWrapper wrapper = new BucketWrapper();
                        wrapper.setId(i);
                        wrapper.setmBucket(buckets.get(i));
                        bucketWrappers.add(wrapper);
                        final int finalI = i;
                        Runnable getImageTask = () -> ApiDribbble.dribbble().getBucketImage(buckets.get(finalI).getId(), 1)
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<List<Shot>>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        callback.onFail(e.getLocalizedMessage());
                                    }

                                    @Override
                                    public void onNext(List<Shot> shots) {
                                        try {
                                            startLatch.await();
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        } finally {
                                            if (shots.size() != 0) {
                                                urlMap.put(finalI, shots.get(0).getImages().getTeaser());
                                            } else {
                                                urlMap.put(finalI, null);
                                            }
                                            doneLatch.countDown();
                                        }

                                    }
                                });

                        THREAD_POOL_EXECUTOR.execute(getImageTask);
                    }
                    startLatch.countDown();
                    try {
                        doneLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        for (int i = 0; i < bucketWrappers.size(); i++) {
                            //we could assume that bucketWrappers' size now is the same as buckets
                            BucketWrapper wrapper = bucketWrappers.get(i);
                            wrapper.setmImageUrl(urlMap.get(i));
                            Log.i("buckets!!!", wrapper.getId() + "#" + wrapper.getmImageUrl());
                        }
                        mHandler.sendEmptyMessage(1);
                    }
                };
                THREAD_POOL_EXECUTOR.execute(runnable);
            }
        });
    }

    public interface OnBucketLoadCallback {

        void onSuccess(List<BucketWrapper> bucketWrappers);

        void onFail(String message);
    }


}