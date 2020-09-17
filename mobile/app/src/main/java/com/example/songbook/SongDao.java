package com.example.songbook;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface SongDao {
    @Insert
    void insert(Song song);

    @Query("SELECT*FROM songs ORDER BY title")
    List<Song> getAllsongs();

    @Query("UPDATE songs SET title = :title, text = :text, description = :description ")
    void save(String title, String text, String description);

    @Query("DELETE FROM songs WHERE id= :id")
    void delete(int id);

    @Query("DELETE FROM songs")
    void deleteAll();
}