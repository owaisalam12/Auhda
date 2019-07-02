package com.core2plus.auhda.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    // private static final String BASE_URL = "https://raw.githubusercontent.com/hasancse91/Android-CardView-RecyclerView/master/Related_Data/";
//   private static final String BASE_URL="http://192.168.137.1/food/API/API.php/";
   private static final String BASE_URL="http://192.168.137.1/auhda/wp-json/wp/v2/posts/";

    // private static final String BASE_URL="http://www.mocky.io/";

    //singleton instance
    private static RetrofitClient mInstance;

    private Retrofit retrofit;

    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
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
