package com.valdioveliu.valdio.audioplayer;


import java.io.Serializable;

/**
 * Created by Valdio Veliu on 16-07-18.
 */
public class Audio implements Serializable {

    private String id;
    private String data;
    private String title;
    private String album;
    private String artist;
    private float duration;

    public Audio(String id,String data, String title, String album, String artist, float duration) {

        this.id = id;
        this.data = data;
        this.title = title;
        this.album = album;
        this.artist = artist;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }


}
