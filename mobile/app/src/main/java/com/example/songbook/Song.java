package com.example.songbook;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.OnConflictStrategy;
import androidx.room.PrimaryKey;

@Entity(tableName = "songs", indices = {@Index("id"), @Index(value = "title")})

public class Song {
    @PrimaryKey()
    private int id;
    private String title;
    private String text;
    private String description;
    private String created_at;
    private String updated_at;
    private  String  language;
    @ColumnInfo(defaultValue = "0")
    private int favStatus = 0;





    Song(int id, String title, String text, String description, String created_at,
         String updated_at, String language){
        this.id = id;
        this.title = title;
        this.text = text;
        this.description = description;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.language = language;


    }

    public String getText(){return text;}

    public int getId(){
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getCreated_at() {return created_at;}

    public String getUpdated_at() {return updated_at;}

    public String getLanguage() {
        return language;
    }
    public int getFavStatus() {
        return favStatus;
    }
    public void setFavStatus(int favStatus) {
        this.favStatus = favStatus;
    }






}

