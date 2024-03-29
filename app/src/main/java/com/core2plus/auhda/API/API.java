package com.core2plus.auhda.API;

import com.core2plus.auhda.API.Responses.auhdaResponse;
import com.core2plus.auhda.API.Responses.jobsResponse;
import com.core2plus.auhda.API.Responses.orderResponse;
import com.core2plus.auhda.API.Responses.productResponse;
import com.core2plus.auhda.API.Responses.variationsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
public interface API {

//    @GET("?fields=id,title,content.rendered,date")
//    Call<auhdaResponse> getPosts();

    //@GET("?fields=id,title,content.rendered,date")
//    @GET("5d1f24b03100003e74ebeafa")
//    Call<List<auhdaResponse>> getPosts();
//
//    @GET("5d204e08300000520dbb0ae0")
//    Call<List<jobsResponse>> getJobs();

    @GET("?fx=getPosts")
    Call<List<auhdaResponse>> getPosts();

    @GET("?fx=getJobs")
    Call<List<jobsResponse>> getJobs();


//       @FormUrlEncoded //with localhost images
//      @POST("5d2d95862e00006100c57e9a")
//      Call<productResponse> getProducts(@Field("query") String query);


//       @FormUrlEncoded //with auhda.com images
//      @POST("5d2ec01e340000550064d222")
//      Call<productResponse> getProducts(@Field("query") String query);


    @FormUrlEncoded
    @POST("?fx=getProducts")
    Call<productResponse> getProducts(@Field("query") String query);


    @FormUrlEncoded
    @POST("?fx=getProductVariations")
    Call<variationsResponse> getProductVariations(@Field("product_id") int product_id);

    @FormUrlEncoded
    @POST("?fx=createOrder")
    Call<orderResponse> createOrder(@Field("first_name") String first_name,
                                    @Field("last_name") String last_name,
                                    @Field("email") String email,
                                    @Field("phone") String phone,
                                    @Field("payment_method") String payment_method,
                                    @Field("product_id") int product_id,
                                    @Field("quantity") int quantity
    );


    @FormUrlEncoded
    @POST("?fx=signUpUser")
    Call<orderResponse> signUpUser(@Field("email") String email,@Field("user_pass") String user_pass);


    @FormUrlEncoded
    @POST("?fx=signInUser")
    Call<orderResponse> signInUser(@Field("email") String email,@Field("user_pass") String user_pass);

}
