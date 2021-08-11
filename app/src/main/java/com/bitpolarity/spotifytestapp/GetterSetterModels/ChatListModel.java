package com.bitpolarity.spotifytestapp.GetterSetterModels;

public class ChatListModel {
    String mSenderName;
    String mMsg;

    public ChatListModel(String mSenderName, String msg) {
        this.mSenderName = mSenderName;
        this.mMsg = msg;
    }

    public String getSenderName() {
        return mSenderName;
    }


    public String getMessage() {
        return mMsg;
    }

}
