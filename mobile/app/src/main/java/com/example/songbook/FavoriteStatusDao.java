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
    //@Query("INSERT INTO  favoriteStatuses  (id, favoriteStatus)  VALUES (:id, 0) WHERE ")
    @Query("UPDATE favoriteStatuses  SET id =:id ")
    void insertFavoriteSong(int id);

    @Query("SELECT * FROM favoriteStatuses")
    List<FavoriteStatus> getAllFavoriteStatuses();


    @Delete
    void deleteFavoriteSong(FavoriteStatus favoriteStatus);

    @Update
    void updateFavoriteStatus (FavoriteStatus favoriteStatus);



}