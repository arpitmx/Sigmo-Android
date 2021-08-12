package com.bitpolarity.spotifytestapp.GetterSetterModels;

public class ChatListModel_Multi {
    String mSenderName;
    String mMsg;
    int messageType;

    public ChatListModel_Multi(String mSenderName, String msg, int messageType) {
        this.mSenderName = mSenderName;
        this.mMsg = msg;
        this.messageType = messageType;
    }

    public String getSenderName() {
        return mSenderName;
    }


    public String getMessage() {
        return mMsg;
    }

    public int getMessageType(){
        return messageType;
    }

}
