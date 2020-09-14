package com.example.songbook;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> implements Filterable {


    private List<Song> filtered = new ArrayList<>();


    @Override
    public Filter getFilter() {

        return new SongFilter();
    }

    private class SongFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Song> filteredSong = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredSong.addAll(songs);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Song item : songs) {
                    if (item.getText().toLowerCase().contains(filterPattern)) {
                        filteredSong.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredSong; // you need to create this variable!
            results.count = filteredSong.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            filtered = (List<Song>) results.values;

            notifyDataSetChanged();                   // и заменить в OnBind и getItemCount
        }
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout containerView;
        public TextView textView;

        SongViewHolder(View view) {
            super(view);
            containerView = view.findViewById(R.id.song_row);
            textView = view.findViewById(R.id.song_row_text_view);

            containerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Song current = (Song) containerView.getTag();
                    Intent intent = new Intent(v.getContext(), SongActivity.class);
                    intent.putExtra("id", current.getId());
                    intent.putExtra("title", current.getTitle());
                    intent.putExtra("text", current.getText());
                    intent.putExtra("description", current.getDescription());


                    //мой добавленный Toast
                    String message = "Good choice";
                    Toast.makeText(v.getContext(), message, Toast.LENGTH_LONG).show();


                    v.getContext().startActivity(intent);


                }
            });


        }

    }

    private List<Song> songs = new ArrayList<>();
    private List<Song> songsDb = new ArrayList<>();
    private RequestQueue requestQueue;


    SongAdapter(Context context) {
        requestQueue = Volley.newRequestQueue(context);

        filtered = songsDb;

        loadSong();  // подумать,  может стоит перенести этот метод в onCreate или даже в mainActivity
        // возможно,  нужно сделать проверку,  если базы еще нет,  то сначала запустить load
        songsDb = songs;




    }
    public void loadSong() {

        String url = "https://song-book-289222.ew.r.appspot.com/api/songs/1";
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                try {

                        Song songTemp = new Song(result.getInt("id"), result.getString("title"),
                                result.getString("text"), result.getString("description"));
                        songs.add(songTemp);
                        MainActivity.songDatabase.songDao().create(songTemp);



                    notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.e("cs50", "Json error", e);
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("cs50", "Song list error");
            }
        });
        requestQueue.add(request);
    }

//    public void loadSong() {
//
//        String url = "https://song-book-289222.ew.r.appspot.com/api/songs";
//        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    JSONArray results = response.getJSONArray("songs");
//                    for (int i = 0; i < results.length(); i++) {
//                        JSONObject result = results.getJSONObject(i);
//                        Song songTemp = new Song(result.getInt("id"), result.getString("title"),
//                                result.getString("text"), result.getString("description"));
//                        songs.add(songTemp);
//                        MainActivity.songDatabase.songDao().create(songTemp);
//
//
//                    }
//                    notifyDataSetChanged();
//                } catch (JSONException e) {
//                    Log.e("cs50", "Json error", e);
//                }
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("cs50", "Song list error");
//            }
//        });
//        requestQueue.add(request);
//    }


    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_row, parent, false);
        return new SongViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {


        Song current = filtered.get(position);


        holder.textView.setText(current.getTitle());
        // потом добавить попробовать сюда еще через append текст песни,  чтобы поиск по словам песни тоже был.
        holder.containerView.setTag(current);

    }

    @Override
    public int getItemCount() {
        return filtered.size();


    }

    public void reload() {
        songsDb = MainActivity.songDatabase.songDao().getAllsongs();
        notifyDataSetChanged();
    }

}

