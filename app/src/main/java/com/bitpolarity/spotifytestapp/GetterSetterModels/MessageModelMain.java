package com.bitpolarity.spotifytestapp.GetterSetterModels;

import com.google.gson.annotations.SerializedName;

public class MessageModelMain {

   @SerializedName("TIME")
   private String TIME;
   @SerializedName("TYPE")
   private String TYPE;
    @SerializedName("msg")
   private String msg;
    @SerializedName("sender")
   private String sender;
    @SerializedName("REFPOS")
   private String REFPOS;


    MessageModelMain(){
    }

    public String getTIME() {
        return TIME;
    }

    public String getTYPE() {
        return TYPE;
    }

    public String getMsg() { return msg; }

    public String getSender() {
        return sender;
    }

    public String getRefpos(){ return REFPOS; }
}
