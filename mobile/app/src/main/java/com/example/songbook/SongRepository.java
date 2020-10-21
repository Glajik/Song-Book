package com.example.songbook;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

public class SongRepository {
    private SongDao songDao;
    private LiveData<List<Song>> allSongs;
    public static List<Song> songsFromServer = new ArrayList<>();
    SongLoader songLoader;

    public SongRepository(Application application)  {
        SongDatabase database = SongDatabase.getInstance(application);
        songDao = database.songDao();


        songLoader = new SongLoader(application);
        songLoader.loadSong();
        Thread thread = new Thread(new Runnable() {
            @Override
            public synchronized void run() {
                while ( songsFromServer.size() == 0){
                    try {
                        wait(1);
                        Log.d("cs50", "waiting for data");

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                insert(songsFromServer);
            }
        });
        thread.start();

        allSongs = songDao.getAllSongs();
        Log.d("cs50", "songs from DB " + allSongs.getValue());
    }


    public void insert(List<Song> songs) {
        new InsertSongAsyncTask(songDao).execute(songs);
    }
    public void update(Song song) {
        new UpdateSongAsyncTask(songDao).execute(song);
    }
    public void delete(Song song) {
        new DeleteSongAsyncTask(songDao).execute(song);
    }
    public void deleteAllSongs() {
        new DeleteAllSongsAsyncTask(songDao).execute();
    }
    public LiveData<List<Song>> getAllSongs() {
        return allSongs;
    }





    private static class InsertSongAsyncTask extends AsyncTask<List<Song>, Void, Void> {
        private SongDao songDao;
        private InsertSongAsyncTask(SongDao songDao) {
            this.songDao = songDao;
        }
        @Override
        protected Void doInBackground(List<Song>... songs) {
            songDao.insert(songs[0]);
            Log.d("cs50", "inserted " + songs[0].get(0).getTitle());
            return null;
        }
    }
    private static class UpdateSongAsyncTask extends AsyncTask<Song, Void, Void> {
        private SongDao songDao;
        private UpdateSongAsyncTask(SongDao songDao) {
            this.songDao = songDao;
        }
        @Override
        protected Void doInBackground(Song... songs) {
            songDao.update(songs[0]);
            return null;
        }
    }
    private static class DeleteSongAsyncTask extends AsyncTask<Song, Void, Void> {
        private SongDao songDao;
        private DeleteSongAsyncTask(SongDao songDao) {
            this.songDao = songDao;
        }
        @Override
        protected Void doInBackground(Song... songs) {
            songDao.delete(songs[0]);
            return null;
        }
    }
    private static class DeleteAllSongsAsyncTask extends AsyncTask<Void, Void, Void> {
        private SongDao songDao;
        private DeleteAllSongsAsyncTask(SongDao songDao) {
            this.songDao = songDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            songDao.deleteAllSongs();
            return null;
        }
    }
}