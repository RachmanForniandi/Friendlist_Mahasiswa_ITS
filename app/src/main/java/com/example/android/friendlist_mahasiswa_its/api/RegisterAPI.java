package com.example.android.friendlist_mahasiswa_its.api;

import com.example.android.friendlist_mahasiswa_its.model.Value;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Lenovo on 7/25/2017.
 */

public interface RegisterAPI {
    @FormUrlEncoded
    @POST("insert.php")
    Call<Value> daftar(@Field("nrp")String nrp,
                      @Field("nama")String nama,
                      @Field("jurusan")String jurusan,
                      @Field("jenis_kelamin")String jenis_kelamin);

    @GET("view.php")
    Call<Value>view();
}
