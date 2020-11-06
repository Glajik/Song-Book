package com.example.songbook;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

public class SongRepository {
    private SongDao songDao;
    private FavoriteStatusDao favoriteStatusDao;
    private LiveData<List<Song>> allSongs;
    public List<FavoriteStatus> allFavoriteStatuses = new ArrayList<>();
    public static List<Song> songsFromServer = new ArrayList<>();
    SongLoader songLoader;

    public SongRepository(Application application) {
        SongDatabase database = SongDatabase.getInstance(application);
        songDao = database.songDao();
        favoriteStatusDao = database.favoriteStatusDao();


        loadSongsFromWeb(application);
        allSongs = songDao.getAllSongs();
//        allFavoriteStatuses = favoriteStatusDao.getAllFavoriteStatuses();
//        Log.d("cs50", "allFavoriteStatus " + allFavoriteStatuses.get(0).songId);



    }


    public void insert(List<Song> songs) {
        new InsertSongAsyncTask(songDao).execute(songs);
    }

    public void update(Song song) {
        new UpdateAsyncTask(songDao).execute(song);
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


    public void insertFavoriteSong(FavoriteStatus favoriteStatus) {
        new InsertFavoriteSongSongAsyncTask(favoriteStatusDao).execute(favoriteStatus);
    }

    public void deleteFavoriteSong(FavoriteStatus favoriteStatus) {
        new deleteFavoriteSongAsyncTask(favoriteStatusDao).execute(favoriteStatus);
    }

    public List<FavoriteStatus> getAllFavoriteStatuses() {
        return allFavoriteStatuses;
    }



    private void loadSongsFromWeb(Application application) {
        songLoader = new SongLoader(application);
        songLoader.loadSong();
        final Thread thread = new Thread(new Runnable() {
            @Override
            public synchronized void run() {
                int i = 0;
                while (songsFromServer.size() == 0 && i < 10000) {
                    try {
                        wait(1);
                        Log.d("cs50", "waiting for data " + i);
                        i++;

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                for (int j = 0; j < songsFromServer.size(); j++) {
                    FavoriteStatus favoriteStatus = new FavoriteStatus(songsFromServer.get(j).getId(), -1);
                    allFavoriteStatuses.add(favoriteStatus);
                    Log.d("cs50", "allFavoriteStatus: " + favoriteStatus.songId + " value " + favoriteStatus.favoriteStatus);
                }
//                Проверяем,  если размер массива в локальной базе данных не равен размеру массива,  полученного с сервера,
//                то всталяем с заменой массив с сервера в локальную базу данных
                if (allSongs.getValue().size() != songsFromServer.size()) {
//                    Log.d("cs50", "allSongs: " + allSongs.getValue().toString() +
//                           "      songsfromserver: " + songsFromServer.toString());
                    deleteAllSongs();
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

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Song, Void, Void> {
        private SongDao songDao;

        private UpdateAsyncTask(SongDao songDao) {
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

    private static class InsertFavoriteSongSongAsyncTask extends AsyncTask<FavoriteStatus, Void, Void> {
    private FavoriteStatusDao favoriteStatusDao;

    private InsertFavoriteSongSongAsyncTask(FavoriteStatusDao favoriteStatusDao) {
        this.favoriteStatusDao = favoriteStatusDao;
    }

    @Override
    protected Void doInBackground(FavoriteStatus... songs) {
        favoriteStatusDao.insertFavoriteSong(songs[0]);
        Log.d("cs50", "inserted into favorite songs id  " + songs[0]);
        return null;
    }
}

    private static class deleteFavoriteSongAsyncTask extends AsyncTask<FavoriteStatus, Void, Void> {
        private FavoriteStatusDao favoriteStatusDao;

        private deleteFavoriteSongAsyncTask(FavoriteStatusDao favoriteStatusDao) {
            this.favoriteStatusDao = favoriteStatusDao;
        }

        @Override
        protected Void doInBackground(FavoriteStatus... songs) {
            favoriteStatusDao.deleteFavoriteSong(songs[0]);
            return null;
        }

    }
}