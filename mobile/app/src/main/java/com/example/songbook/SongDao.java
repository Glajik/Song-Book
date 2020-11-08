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
public interface SongDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Song> songs);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertSongWithStatus(Song song);

    @Query("SELECT*FROM songs ORDER BY favStatus DESC, title")
    LiveData<List<Song>> getAllSongs();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Song songs);

     @Delete
    void delete(Song song);

    @Query("DELETE FROM songs")
    void deleteAllSongs();

//    @Query("SELECT favStatus FROM songs WHERE id = :id")
//    void getFavStatus(int id);
}