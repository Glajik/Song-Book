package com.example.songbook;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.Arrays;
import java.util.List;

public class SongViewModel extends AndroidViewModel {

    public SongRepository repository;
    private LiveData<List<Song>> allSongs;
   List<FavoriteStatus>  allFavoriteStatuses;


    public SongViewModel(@NonNull Application application) {
        super(application);
        repository = new SongRepository(application);
        allSongs = repository.getAllSongs();
        allFavoriteStatuses = repository.getAllFavoriteStatuses();


        Log.d("cs50", "SongViewModel allSongs = " + allSongs.getValue());
    }

    public void insert(List<Song> songs) {
        repository.insert(songs);
    }
    public void update(Song song) {
        repository.update(song);
    }
    public void delete(Song song) {
        repository.delete(song);
    }
    public void deleteAllSongs() {
        repository.deleteAllSongs();
    }
    public LiveData<List<Song>> getAllSongs() {
        return allSongs;
    }

    public List<FavoriteStatus> getAllFavoriteStatuses() {
        return allFavoriteStatuses;
    }
    public void insertFavoriteSong(int songId, int favoriteStatus) {
        repository.insertFavoriteSong(songId, favoriteStatus);
    }
//    public void deleteFavoriteSong(int songId) {
//        repository.deleteFavoriteSong(songId);
//    }

    public void  updateFavoriteList(int songId) {

            if( ){

                Log.d("cs50", "song has been disliked");
            } else {
                ;
                Log.d("cs50", "song has been  added to liked");
            }
        //}
    }
}
