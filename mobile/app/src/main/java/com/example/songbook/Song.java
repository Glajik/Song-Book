package com.example.songbook;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "songs", indices = {@Index("id"), @Index(value = "title", unique = true)})
public class Song {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String text;
    private String description;




    Song(int id, String title, String text, String description){
        this.id = id;
        this.title = title;
        this.text = text;
        this.description = description;

    }

    public String getText(){

        return text;
    }

    public int getId(){
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }


}

