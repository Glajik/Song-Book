package com.example.songbook;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    private List<Song> songs = new ArrayList<>();
    private List<Song> filtered = new ArrayList<>();
    private OnItemClickListener listener;

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
                //Log.d("cs50", "songs size is " + filteredSong.size());
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Song item : songs) {
                    if (item.getText().toLowerCase().contains(filterPattern)) {
                        filteredSong.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredSong;
            results.count = filteredSong.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            filtered = (List<Song>) results.values;

            notifyDataSetChanged();
        }
    }

    public class SongViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout containerView;
        public TextView textView;
        public Button favoriteSong;

        SongViewHolder(final View itemView) {
            super(itemView);
            containerView = itemView.findViewById(R.id.song_row);
            textView = itemView.findViewById(R.id.song_row_text_view);
            favoriteSong = itemView.findViewById(R.id.liked_button);


            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Song current = (Song) containerView.getTag();
                    Intent intent = new Intent(v.getContext(), SongActivity.class);
                    intent.putExtra(SongActivity.EXTRA_ID, current.getId());
                    intent.putExtra(SongActivity.EXTRA_TITLE, current.getTitle());
                    intent.putExtra(SongActivity.EXTRA_TEXT, current.getText());
                    intent.putExtra(SongActivity.EXTRA_DESCRIPTION, current.getDescription());
                    //мой добавленный Toast
                    String message = "Good choice";
                    Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();
                    v.getContext().startActivity(intent);
                }
            });

        favoriteSong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if(listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(songs.get(position));
                    }
                }
            });
        }
        public void setColorFavoriteSongButton(Song current){
            int favoriteStatus = current.getFavStatus();
            if( favoriteStatus== 0 ){
                favoriteSong.setBackgroundResource(R.drawable.ic_shadow_thumb_up_24);
                Log.d("cs50", " song current " + current.getTitle() + " , favoriteStatus " + favoriteStatus);
            }else {
                favoriteSong.setBackgroundResource(R.drawable.ic_baseline_thumb_up_24);
                Log.d("cs50", " song current " + current.getTitle() + " , favoriteStatus " + favoriteStatus);

            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick (Song song);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }



    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_row, parent, false);
        return new SongViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song current = filtered.get(position);
        holder.textView.setText(current.getTitle());
        //Log.d("cs50", "filtered size is " + filtered.size());
        holder.containerView.setTag(current);
        holder.setColorFavoriteSongButton(current);


    }

    @Override
    public int getItemCount() {
       // Log.d("cs50", "filtered size is " + filtered.size());
        return filtered.size();

    }

    public void reload(List<Song> songs) {
        this.songs = songs;
        getFilter().filter("");
       // Log.d("cs50", "reload = " + songs.size());
        notifyDataSetChanged();
    }

}

