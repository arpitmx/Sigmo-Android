package com.bitpolarity.spotifytestapp.GetterSetterModels;

import android.content.Context;

public class UserListModel {

    private String username;
    private String poster;
    private Integer status;
    private String SongDetail;
    private String isPlaying;
    private String datetime;
    private String artistName;


    public String getArtistName() {
        return artistName;
    }

    public String getUsername() {
        return username;
    }

    public String getPoster() {
        return poster;
    }

    public Integer getStatus() {
        return status;
    }


    public String getSongDetail() {
        return SongDetail;
    }

    public String getIsPlaying() {
        return isPlaying;
    }

    public String getDatetime(){
        return  this.datetime;
    }


    public UserListModel( String datetime, String isPlaying, String username, String poster, Integer status, String SongDetail, String artistName) {


        this.username = username;
       this.isPlaying = isPlaying;
        this.poster = poster;
        this.status = status;
        this.SongDetail = SongDetail;
        this.datetime = datetime;
        this.artistName = artistName;

    }




}
