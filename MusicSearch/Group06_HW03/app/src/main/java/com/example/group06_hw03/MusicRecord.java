package com.example.group06_hw03;

import java.io.Serializable;
import java.util.Date;

public class MusicRecord implements Serializable {
    String Track;
    String Artist;
    double trackprice,albumprice;
    Date date;
    String genre;
    String imageUrl;
    String album;

    public MusicRecord() {


    }

    public String getTrack() {
        return Track;
    }

    public void setTrack(String track) {
        Track = track;
    }

    public String getArtist() {
        return Artist;
    }

    public void setArtist(String artist) {
        Artist = artist;
    }

    public double getTrackprice() {
        return trackprice;
    }

    public void setTrackprice(double trackprice) {
        this.trackprice = trackprice;
    }

    public double getAlbumprice() {
        return albumprice;
    }

    public void setAlbumprice(double albumprice) {
        this.albumprice = albumprice;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    @Override
    public String toString() {
        return "MusicRecord{" +
                "Track='" + Track + '\'' +
                ", Artist='" + Artist + '\'' +
                ", trackprice=" + trackprice +
                ", albumprice=" + albumprice +
                ", date=" + date +
                ", genre='" + genre + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", album='" + album + '\'' +
                '}';
    }
}
