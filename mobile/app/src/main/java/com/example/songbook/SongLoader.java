package com.example.songbook;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SongLoader {

    public SongLoader(Context context) {
        requestQueue = Volley.newRequestQueue(context);

    }

    public List<Song> songs = new ArrayList<>();
    private RequestQueue requestQueue;


    public synchronized void loadSong() {

        String url = "https://song-book-289222.ew.r.appspot.com/api/songs";
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("songs");

                   //Log.d("cs50", "response is: " + response.toString());
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject result = results.getJSONObject(i);
                        Song songTemp = new Song(result.getInt("id"), result.getString("title"),
                                result.getString("text"), result.getString("description"),
                                result.getString("created_at"), result.getString("updated_at"), result.getString("language"));
                         Log.d("cs50", "songTemp:" + songs.size());
                         songs.add(songTemp);


                    }
                    //передаем статической переменной репозитория список песен
                    SongRepository.songsFromServer = songs;
                    Log.d("cs50", " Songrepository.songsfromserver = " + SongRepository.songsFromServer.size());





                } catch (JSONException e) {
                    Log.e("cs50", "Json error", e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("cs50", "Song list error");
                Log.e("cs50", error.toString());
            }
        });

        requestQueue.add(request);
       }

}
