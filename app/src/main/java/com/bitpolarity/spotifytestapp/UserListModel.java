package com.bitpolarity.spotifytestapp;

import android.app.Activity;
import android.content.Context;

public class UserListModel {

    private String username;
    private String poster;
    private Integer imageid;
    private String SongDetail;
    public Context context;
    private String isPlaying;
    private String datetime;


    public String getUsername() {
        return username;
    }

    public String getPoster() {
        return poster;
    }

    public Integer getImageid() {
        return imageid;
    }


    public String getSongDetail() {
        return SongDetail;
    }



    public Context getContext() {
        return this.context;
    }


    public String getIsPlaying() {
        return this.isPlaying;
    }

    public String getDatetime(){
        return  this.datetime;
    }


    public UserListModel(Context context , String datetime, String isPlaying, String username, String poster, Integer statusid, String SongDetail) {

        this.context = context;
        this.username = username;
        this.isPlaying = isPlaying;
        this.poster = poster;
        this.imageid = statusid;
        this.SongDetail = SongDetail;
        this.datetime = datetime;

    }




}
