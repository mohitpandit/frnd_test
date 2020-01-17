package com.konnectshift.frnd.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.konnectshift.frnd.model.RecordingObject

@Dao
interface RecordingDao {
    @Query("SELECT * FROM recording order By id DESC")
    fun loadAll(): LiveData<List<RecordingObject>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(rec: RecordingObject)
}