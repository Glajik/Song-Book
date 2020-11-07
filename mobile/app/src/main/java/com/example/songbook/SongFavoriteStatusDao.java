package com.example.songbook;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SongFavoriteStatusDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSongFavoriteStatus(SongFavoriteStatus songFavoriteStatus);

    @Query("SELECT * FROM songsFavoriteStatus ORDER BY title")
    LiveData<List<SongFavoriteStatus>> getAllSongsFavoriteStatus();

    @Query("INSERT INTO songsFavoriteStatus SELECT * FROM songs, (SELECT favoriteStatus from favoriteStatuses) ")
    void fillSongFavoriteStatus();

}
