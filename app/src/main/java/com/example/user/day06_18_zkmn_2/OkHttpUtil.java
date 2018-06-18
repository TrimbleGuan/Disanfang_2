package com.example.user.day06_18_zkmn_2;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpUtil {

    private final Handler mhandler;
    private final OkHttpClient mokHttpClient;
    private static OkHttpUtil mokHttpUtil;

    private OkHttpUtil(){
        mhandler = new Handler(Looper.getMainLooper());
        mokHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.MILLISECONDS)
                .writeTimeout(5000,TimeUnit.MILLISECONDS)
                .readTimeout(5000,TimeUnit.MILLISECONDS)
                .build();
    }
    public static OkHttpUtil getInteace(){
        if(mokHttpUtil==null){
            synchronized (OkHttpUtil.class){
                if(mokHttpUtil==null){
                    mokHttpUtil = new OkHttpUtil();
                }
            }
        }
        return mokHttpUtil;
    }
    public void doGet(String url, final OkCallBack okCallBack){
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        Call call = mokHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                if(okCallBack!=null){
                    //
                    mhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            okCallBack.onFailure(e);
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                mhandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(response!=null &&response.isSuccessful()){
                            try {
                                String json = response.body().string();
                                okCallBack.onResponse(json);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if(response!=null){
                            okCallBack.onFailure(new Exception("网络异常"));
                        }
                    }
                });
            }
        });
    }
    public void doPost(String url, Map<String, String> map, final OkCallBack okCallback) {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : map.keySet()) {
            builder.add(key, map.get(key));
        }
        FormBody formBody = builder.build();
        Request request = new Request.Builder()
                .post(formBody)
                .url(url)
                .build();
        final Call call = mokHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                if (okCallback != null) {
                    //切换到主线程
                    mhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            okCallback.onFailure(e);
                        }
                    });
                }
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                mhandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (response != null && response.isSuccessful()) {
                                String json = response.body().string();
                                if (okCallback != null) {
                                    okCallback.onResponse(json);
                                    return;
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (okCallback != null) {
                            okCallback.onFailure(new Exception("网络异常"));
                        }
                    }
                });
            }
        });
    }
    public interface OkCallBack{
        void onFailure(Exception e);
        void onResponse(String json);
    }
}
