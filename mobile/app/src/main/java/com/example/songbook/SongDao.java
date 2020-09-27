package com.example.songbook;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface SongDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insert(List<Song> song);

    @Query("SELECT*FROM songs ORDER BY title")
    List<Song> getAllsongs();

//    @Query("UPDATE songs SET title = :title, text = :text, description = :description, " +
//            "created_at = :created_at, updated_at = :updated_at, lang_id = :lang_id" WHERE id = :id)
//    void update(int id, String title, String text, String description, String created_at, String updated_at, int lang_id);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(List<Song> song);

    @Query("DELETE FROM songs WHERE id= :id")
    void delete(int id);

    @Query("DELETE FROM songs")
    void deleteAll();
}