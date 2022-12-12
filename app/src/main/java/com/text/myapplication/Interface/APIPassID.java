package com.text.myapplication.Interface;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIPassID {
    String BASE_URL="http://192.168.137.1:8686/api/india/getsubdistrict/118";

    @GET("getState")
    Call<String> getState();

    @GET("getDistrict/{id}")
    Call<String> getDistrict(@Path("id")int id);

    @GET("getSubDistrict/{id}")
    Call<String> getSubDistrict(@Path("id")int id);

}
