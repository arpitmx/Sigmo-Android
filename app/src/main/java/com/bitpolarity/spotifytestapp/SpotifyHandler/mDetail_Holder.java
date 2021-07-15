package com.bitpolarity.spotifytestapp.SpotifyHandler;

public class mDetail_Holder {

    String Poster_url;
    String Song_Title;
    String Song_Artist;
    Boolean isPlaying;
    private static mDetail_Holder instance = null;


    public static mDetail_Holder getInstance() {
        if (instance == null) {
            instance = new mDetail_Holder();
        }
        return instance;
    }

    public String getPoster_url() {
        return this.Poster_url;
    }

    public void setPoster_url(String poster_url) {
        this.Poster_url = poster_url;
    }

    public String getSong_Title() {
        return this.Song_Title;
    }

    public void setSong_Title(String song_Title) {
        this.Song_Title = song_Title;
    }

    public String getSong_Artist() {
        return this.Song_Artist;
    }

    public void setSong_Artist(String song_Artist) {
        this.Song_Artist = song_Artist;
    }

    public Boolean getPlaying() {
        return isPlaying;
    }

    public void setPlaying(Boolean playing) {
        isPlaying = playing;
    }


}
