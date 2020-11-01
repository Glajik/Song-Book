package com.example.songbook;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Song.class}, version = 6, exportSchema = true)
public abstract class SongDatabase extends RoomDatabase {
    public abstract SongDao songDao();

    private static SongDatabase instance;

    public static synchronized SongDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    SongDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}