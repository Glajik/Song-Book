package com.example.songbook;

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

import java.util.ArrayList;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> implements Filterable {

    private List<SongFavoriteStatus> songFavoriteStatuses = new ArrayList<>();
    private List<SongFavoriteStatus> filtered = new ArrayList<>();
    private OnItemClickListener listener;



    @Override
    public Filter getFilter() {

        return new SongFilter();
    }

    private class SongFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<SongFavoriteStatus> filteredSong = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredSong.addAll(songFavoriteStatuses);
                //Log.d("cs50", "songs size is " + filteredSong.size());
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (SongFavoriteStatus item : songFavoriteStatuses) {
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

            filtered = (List<SongFavoriteStatus>) results.values;

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
                    SongFavoriteStatus current = (SongFavoriteStatus) containerView.getTag();
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
                        int songId = songFavoriteStatuses.get(position).getId();
                        listener.onItemClick(songId);

                        favoriteSong.setBackgroundResource(R.drawable.ic_baseline_thumb_up_24);
                    }

                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick (int songId);
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
        SongFavoriteStatus current = filtered.get(position);
        holder.textView.setText(current.getTitle());
        Log.d("cs50", "filtered size is " + filtered.size());
        holder.containerView.setTag(current);

    }

    @Override
    public int getItemCount() {
       // Log.d("cs50", "filtered size is " + filtered.size());
        return filtered.size();

    }

    public void reload(List<SongFavoriteStatus> songFavoriteStatuses) {
        this.songFavoriteStatuses = songFavoriteStatuses;
        getFilter().filter("");
        Log.d("cs50", "reload = " + this.songFavoriteStatuses.size());
        notifyDataSetChanged();
    }

}

