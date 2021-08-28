package com.bitpolarity.spotifytestapp.GetterSetterModels;

public class RoomsListModel {
    String mRoomName;
    String mHostName;
    String mTime;

    public RoomsListModel(String mRoomName, String mHostName,String mTime) {
        this.mRoomName = mRoomName;
        this.mHostName = mHostName;
        this.mTime = mTime;
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

}
