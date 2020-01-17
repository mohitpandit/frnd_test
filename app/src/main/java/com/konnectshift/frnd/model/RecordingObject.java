package com.konnectshift.frnd.model;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@Entity(tableName = "recording")
public class RecordingObject {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("media")
    @Expose
    private String media;
    @SerializedName("user_id")
    @Expose
    private int userId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        try {
            return this.id == ((RecordingObject) obj).id;
        } catch (Exception e) {
            return false;
        }
    }
}
