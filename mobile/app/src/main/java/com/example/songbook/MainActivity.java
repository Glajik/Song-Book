package com.example.songbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity //implements SearchView.OnQueryTextListener
 {

    private RecyclerView recyclerView;
    private SongAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public static SongDatabase songDatabase;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });


        return true;
    }







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        adapter = new SongAdapter(getApplicationContext());
        layoutManager = new LinearLayoutManager( this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);



// развертываем базу данных
        songDatabase = Room.databaseBuilder(getApplicationContext(), SongDatabase.class, "songsDb" )
                .allowMainThreadQueries()
                .build();

        adapter.reload();
    }

     @Override
     protected void onResume() {
         super.onResume();
         adapter.reload();

     }


}
