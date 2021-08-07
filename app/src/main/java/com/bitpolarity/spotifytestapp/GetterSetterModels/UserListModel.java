package com.bitpolarity.spotifytestapp.GetterSetterModels;

import android.content.Context;

public class UserListModel {

    private String username;
    private String poster;
    private Integer status;
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

    public Integer getStatus() {
        return status;
    }


    public String getSongDetail() {
        return SongDetail;
    }



    public Context getContext() {
        return this.context;
    }


    public String getIsPlaying() {
        return isPlaying;
    }

    public String getDatetime(){
        return  this.datetime;
    }


    public UserListModel(Context context , String datetime, String isPlaying, String username, String poster, Integer status, String SongDetail) {

        this.context = context;
        this.username = username;
       this.isPlaying = isPlaying;
        this.poster = poster;
        this.status = status;
        this.SongDetail = SongDetail;
        this.datetime = datetime;

    }




}
