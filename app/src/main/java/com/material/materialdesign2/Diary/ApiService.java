package com.material.materialdesign2.Diary;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    public static final String API_URL = "http://13.209.35.60/api/";


    @Multipart
    @POST("upload")
    Call<ResponseBody> postUpload(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file
            );

}
