package com.konnectshift.frnd.ui.main

import com.konnectshift.frnd.data.ApiHelper
import com.konnectshift.frnd.data.AppDbHelper
import dagger.Module
import dagger.Provides

@Module
class RecordingActivityModule {
    @Provides
    fun provideLoginViewModel(mApiHelper: ApiHelper, mAppDbHelper: AppDbHelper): RecordingViewModel {
        return RecordingViewModel(mApiHelper, mAppDbHelper)
    }

}