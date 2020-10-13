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
    void insert(List<Song> songs);

    @Query("SELECT*FROM songs ORDER BY title")
    LiveData<List<Song>> getAllSongs();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(Song song);

    @Delete
    void delete(Song song);

    @Query("DELETE FROM songs")
    void deleteAllSongs();
}