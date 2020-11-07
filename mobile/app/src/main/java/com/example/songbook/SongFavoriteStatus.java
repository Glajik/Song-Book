package com.example.songbook;

import androidx.room.Entity;
import androidx.room.Index;

@Entity(tableName = "songsFavoriteStatus", indices = {@Index("id"), @Index(value = "title"), @Index("favoriteStatus")})
public class SongFavoriteStatus extends Song{
    int favoriteStatus;

    public SongFavoriteStatus(int id, String title, String text, String description, String created_at,
                              String updated_at, String language, int favoriteStatus) {
        super(id, title, text, description, created_at, updated_at, language);
        this.favoriteStatus = favoriteStatus;
    }

    public void setFavoriteStatus(int favoriteStatus) {
        this.favoriteStatus = favoriteStatus;
    }

    public int getFavoriteStatus() {
        return favoriteStatus;
    }
}
