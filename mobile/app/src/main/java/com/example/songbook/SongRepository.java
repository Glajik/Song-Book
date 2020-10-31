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


        loadSongsFromWeb(application);
        allSongs = songDao.getAllSongs();
        Log.d("cs50", "allSongs from database = " + allSongs.getValue());
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

    private void loadSongsFromWeb(Application application) {
        songLoader = new SongLoader(application);
        songLoader.loadSong();
        final Thread thread = new Thread(new Runnable() {
            @Override
            public synchronized void run() {
                int i = 0;
                while ( songsFromServer.size() == 0 && i < 10000){
                    try {
                        wait(1);
                        Log.d("cs50", "waiting for data " + i);
                        i++;

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //Проверяем,  если размер массива в локальной базе данных не равен размеру массива,  полученного с сервера,
                //то всталяем с заменой массив с сервера в локальную базу данных
                if (allSongs.getValue().size() != songsFromServer.size()) {
                    Log.d("cs50",  "allSongs: " + allSongs.getValue().toString() +
                            "      songsfromserver: " + songsFromServer.toString());
                   insert(songsFromServer);
                }
                Thread.currentThread().interrupt();

            }
        });
        thread.start();
    }





    private static class InsertSongAsyncTask extends AsyncTask<List<Song>, Void, Void> {
        private SongDao songDao;
        private InsertSongAsyncTask(SongDao songDao) {
            this.songDao = songDao;
        }
        @Override
        protected Void doInBackground(List<Song>... songs) {
            songDao.insert(songs[0]);
           Log.d("cs50", "inserted " + songs[0].get(0).getTitle() + "  " + songs[0].get(1).getTitle() + "   " + songs[0].get(2).getTitle());
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