package com.core2plus.auhda.API;

import com.core2plus.auhda.API.Responses.auhdaResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {

//    @GET("?fields=id,title,content.rendered,date")
//    Call<auhdaResponse> getPosts();

    @GET("?fields=id,title,content.rendered,date")
    Call<List<auhdaResponse>> getPosts();
}
