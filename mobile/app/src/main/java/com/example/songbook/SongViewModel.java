package com.example.songbook;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class SongViewModel extends AndroidViewModel {

    private SongRepository repository;
    private LiveData<List<Song>> allSongs;

    public SongViewModel(@NonNull Application application) {
        super(application);
        repository = new SongRepository(application);
        allSongs = repository.getAllSongs();
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
    public void deleteAllNotes() {
        repository.deleteAllSongs();
    }
    public LiveData<List<Song>> getAllSongs() {
        return allSongs;
    }
}
