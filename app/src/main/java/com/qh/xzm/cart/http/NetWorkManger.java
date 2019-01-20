package com.qh.xzm.cart.http;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetWorkManger {

    private static NetWorkManger mInstance;

    private static final String BASE_URL = "http://120.27.23.105/";
    private Retrofit retrofit;

    //单例
    public static NetWorkManger getInstance() {
        if (mInstance == null) {
            mInstance = new NetWorkManger();
        }
        return mInstance;
    }

    private NetWorkManger() {
        init();
    }

    private void init() {

        //初始化必要对象和参数
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

        // 初始化Retrofit
        retrofit = new Retrofit.Builder()
                //添加自定义的okHttp
                .client(client)
                //设置BaseUrl
                .baseUrl(BASE_URL)
                //Rxjava处理回调的数据
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //Gson转化器
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

    //把接口的注解翻译为OKhttp请求
    public <T> T create(final Class<T> service) {
        return retrofit.create(service);
    }


}
