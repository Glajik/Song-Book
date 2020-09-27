package com.example.songbook;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Song.class}, version = 4, exportSchema = true)
public abstract class SongDatabase extends RoomDatabase {
    public abstract SongDao songDao();
}