package com.example.songbook;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;



@Entity(tableName = "favoriteStatuses", foreignKeys = @ForeignKey(entity = Song.class, parentColumns = "id", childColumns = "songId"))
public class FavoriteStatus{
    @PrimaryKey()
    int songId;
    int favoriteStatus;

    FavoriteStatus(int songId, int favoriteStatus){
        this.songId = songId;
        this.favoriteStatus = favoriteStatus;
    }
    public int getSongId() {
        return songId;
    }

    public int getFavoriteStatus() {
        return favoriteStatus;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public void setFavoriteStatus(int favoriteStatus) {
        this.favoriteStatus = favoriteStatus;
    }



}