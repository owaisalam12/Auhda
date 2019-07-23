package com.core2plus.auhda.API;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

      private static final String BASE_URL="http://192.168.10.7/woophp/API.php/";

     //private static final String BASE_URL="http://www.mocky.io/v2/";

    //singleton instance
    private static RetrofitClient mInstance;

    private Retrofit retrofit;
    OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();
    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    //synchronized method to get the singleton instance of retrofitclient class
    public static synchronized RetrofitClient getInstance() { //it is synchronized because we want single instance only.
        if (mInstance == null) {
            mInstance = new RetrofitClient();

        }
        return mInstance;
    }

    //method to get Api
    public API getApi() {
        return retrofit.create(API.class);
    }
}
