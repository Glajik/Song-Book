package com.example.songbook;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "songsFavorite", indices = {@Index("id"), @Index(value = "title")})
public class SongFavorite  extends Song{


     SongFavorite(int id, String title, String text, String description, String created_at, String updated_at, String language) {
        super(id, title, text, description, created_at, updated_at, language);
    }

}

