package com.bitpolarity.spotifytestapp.GetterSetterModels;

public class RoomsListModel {
    String mRoomName;
    String mHostName;

    public RoomsListModel(String mRoomName, String mHostName) {
        this.mRoomName = mRoomName;
        this.mHostName = mHostName;
    }

    public String getmRoomName() {
        return mRoomName;
    }

    public void setmRoomName(String mRoomName) {
        this.mRoomName = mRoomName;
    }

    public String getmHostName() {
        return mHostName;
    }

    public void setmHostName(String mHostName) {
        this.mHostName = mHostName;
    }
}
