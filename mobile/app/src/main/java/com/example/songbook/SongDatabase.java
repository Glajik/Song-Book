package com.example.songbook;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Song.class, FavoriteStatus.class, SongFavoriteStatus.class}, version = 13, exportSchema = true)
public abstract class SongDatabase extends RoomDatabase {
    public abstract SongDao songDao();
    public abstract FavoriteStatusDao favoriteStatusDao();
    public abstract SongFavoriteStatusDao songFavoriteStatusDao();

    private static SongDatabase instance;

    public static synchronized SongDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    SongDatabase.class, "song_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}