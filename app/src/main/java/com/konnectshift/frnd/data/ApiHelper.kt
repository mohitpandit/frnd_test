package com.konnectshift.frnd.data

import androidx.lifecycle.LiveData
import com.konnectshift.frnd.data.retrofit.ApiInterface
import com.konnectshift.frnd.data.retrofit.ApiResponse
import com.konnectshift.frnd.data.retrofit.MultipartParams
import com.konnectshift.frnd.model.CommonResponse
import java.io.File
import javax.inject.Inject

class ApiHelper @Inject internal constructor(var mApiInterface: ApiInterface) {

    fun uploadFile(file: File): LiveData<ApiResponse<CommonResponse>> {
        val map = MultipartParams.Builder().addFile("media", file)
        return mApiInterface.uploadFile(map.build().map)
    }

}