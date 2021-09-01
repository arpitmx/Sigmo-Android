package com.bitpolarity.spotifytestapp.GetterSetterModels;

public class MessageModelHolder {

    String mSenderName;
    String mMsg;
    int messageType;
    String time;
    String refPos;

    public MessageModelHolder(String mSenderName, String msg, int messageType, String time) {
        this.mSenderName = mSenderName;
        this.mMsg = msg;
        this.messageType = messageType;
        this.time = time;
    }


    public MessageModelHolder(String mSenderName, String msg, int messageType, String time , String refPos){
        this.mSenderName = mSenderName;
        this.mMsg = msg;
        this.messageType = messageType;
        this.time = time;
        this.refPos = refPos;
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

    public String getRefPos(){
        return refPos ;
    }


}
