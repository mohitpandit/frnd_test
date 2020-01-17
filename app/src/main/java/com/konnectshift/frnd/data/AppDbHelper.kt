package com.konnectshift.frnd.data

import androidx.lifecycle.LiveData
import com.konnectshift.frnd.data.room.AppDatabase
import com.konnectshift.frnd.model.RecordingObject
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDbHelper @Inject constructor(private val mAppDatabase: AppDatabase) {


    fun getRecordings(): LiveData<List<RecordingObject>> {
        return mAppDatabase.userDao().loadAll()
    }

    fun saveRecording(rec: RecordingObject, finished: () -> Unit) {
        val d = Observable.fromCallable {
            mAppDatabase.userDao().insertItem(rec)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            finished()
        }, { throwable ->
            throwable.printStackTrace()
        })
    }
}