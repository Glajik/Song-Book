package com.example.songbook;

import androidx.room.Entity;
import androidx.room.Index;
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
    private int is_liked = -1;
    //private  int lang_id;





    Song(int id, String title, String text, String description, String created_at,
         String updated_at){
        this.id = id;
        this.title = title;
        this.text = text;
        this.description = description;
        this.created_at = created_at;
        this.updated_at = updated_at;
        //this.lang_id = lang_id;

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

    public int getIs_liked() {
        return is_liked;
    }
    public void setIs_liked(int is_liked) {
        this.is_liked = is_liked;
    }

    //public int getLang_id() {return lang_id;}

}

