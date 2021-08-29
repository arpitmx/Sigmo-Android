package com.bitpolarity.spotifytestapp.GetterSetterModels;

public class RoomsListModel {
    String mRoomName;
    String mHostName;
    String mTime;
    String mUrl;

    public RoomsListModel(String mRoomName, String mHostName,String mTime, String mUrl) {
        this.mRoomName = mRoomName;
        this.mHostName = mHostName;
        this.mTime = mTime;
        this.mUrl =  mUrl;
    }

    public String getmRoomName() {
        return mRoomName;
    }
    public String getmHostName() {
        return mHostName;
    }
    public String getmTime() {
        return mTime;
    }

    public String getmUrl() {
        return mUrl;
    }
}
