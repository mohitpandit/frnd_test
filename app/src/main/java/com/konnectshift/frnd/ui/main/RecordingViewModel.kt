package com.konnectshift.frnd.ui.main

import androidx.lifecycle.LiveData
import com.konnectshift.frnd.data.ApiHelper
import com.konnectshift.frnd.data.AppDbHelper
import com.konnectshift.frnd.data.retrofit.ApiResponse
import com.konnectshift.frnd.model.CommonResponse
import com.konnectshift.frnd.model.RecordingObject
import java.io.File

class RecordingViewModel(var mApiHelper: ApiHelper, var mAppDbHelper: AppDbHelper) {


    fun uploadFile(file: File): LiveData<ApiResponse<CommonResponse>>? {
        return mApiHelper.uploadFile(file)
    }

    fun saveToLocal(mRecordingObject: RecordingObject, isFinished: () -> Unit) {
        mAppDbHelper.saveRecording(mRecordingObject, isFinished)
    }

    fun getRecording(): LiveData<List<RecordingObject>> {
        return mAppDbHelper.getRecordings()
    }
}