package com.konnectshift.frnd.data.retrofit;

import androidx.lifecycle.LiveData;

import com.konnectshift.frnd.model.CommonResponse;

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;


public interface ApiInterface {


    @Multipart
    @POST("api/uploadMedia")
    LiveData<ApiResponse<CommonResponse>> uploadFile(@PartMap HashMap<String, RequestBody> map);

}