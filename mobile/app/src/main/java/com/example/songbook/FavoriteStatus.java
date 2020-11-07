package com.example.songbook;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

//foreignKeys = @ForeignKey(entity = Song.class, parentColumns = "id", childColumns = "id")

@Entity(tableName = "favoriteStatuses" , foreignKeys = @ForeignKey(entity = Song.class, parentColumns = "id", childColumns = "id"))
public class FavoriteStatus{
    @PrimaryKey()
    int id;
    int favoriteStatus;

    FavoriteStatus(int id, int favoriteStatus){
        this.id = id;
        this.favoriteStatus = favoriteStatus;
    }
    public int getSongId() {
        return id;
    }

    public int getFavoriteStatus() {
        return favoriteStatus;
    }

    public void setSongId(int songId) {
        this.id = songId;
    }

    public void setFavoriteStatus(int favoriteStatus) {
        this.favoriteStatus = favoriteStatus;
    }



}