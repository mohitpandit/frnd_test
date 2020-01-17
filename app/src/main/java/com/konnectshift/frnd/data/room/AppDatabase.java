package com.konnectshift.frnd.data.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.konnectshift.frnd.model.RecordingObject;


@Database(entities = {RecordingObject.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RecordingDao userDao();
}
