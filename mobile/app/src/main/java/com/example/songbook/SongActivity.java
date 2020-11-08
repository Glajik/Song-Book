package com.example.songbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class SongActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.example.songbook.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.example.songbook.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.example.songbook.EXTRA_DESCRIPTION";
    public static final String EXTRA_TEXT = "com.example.songbook.EXTRA_TEXT";
    private TextView titleTextView;
    private TextView descriptionTextView;
    private TextView textTextView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);


        titleTextView = findViewById(R.id.title);
        descriptionTextView = findViewById(R.id.description);
        textTextView = findViewById(R.id.text);

        titleTextView.setText(getIntent().getStringExtra(EXTRA_TITLE));
        descriptionTextView.setText(getIntent().getStringExtra(EXTRA_DESCRIPTION));
        textTextView.setText(getIntent().getStringExtra(EXTRA_TEXT));

    }
}