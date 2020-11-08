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

    @Query("INSERT OR REPLACE INTO songs (id, title, description, text, created_at, updated_at, language)" +
            "  VALUES (:id, :title, :description, :text, :created_at, :updated_at, :language) ")
    long insert(int id, String title, String description, String text, String created_at,
                String updated_at, String language);

    @Query("SELECT*FROM songs ORDER BY title")
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