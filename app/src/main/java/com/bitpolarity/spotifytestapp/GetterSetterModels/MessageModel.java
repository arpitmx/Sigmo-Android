package com.bitpolarity.spotifytestapp.GetterSetterModels;

public class MessageModel {

    String mSenderName;
    String mMsg;
    int messageType;
    String time;

    public MessageModel(String mSenderName, String msg, int messageType, String time) {
        this.mSenderName = mSenderName;
        this.mMsg = msg;
        this.messageType = messageType;
        this.time = time;
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

    public String getTime(){
        return time;
    }


}
