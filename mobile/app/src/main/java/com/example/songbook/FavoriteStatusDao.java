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
public interface FavoriteStatusDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertFavoriteSong(int id, int favoriteStatus);

    @Query("SELECT*FROM favoriteStatuses")
    List<FavoriteStatus> getAllFavoriteStatuses();


//    @Delete
//    void deleteFavoriteSong(int id);

}