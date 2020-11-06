package com.example.songbook;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SongFavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavoriteSong(Song songs);

    @Query("SELECT*FROM songsFavorite ORDER BY title")
    LiveData<List<Song>> getAllFavoriteSongs();

    @Delete
    void deleteFavoriteSong(Song song);

    }