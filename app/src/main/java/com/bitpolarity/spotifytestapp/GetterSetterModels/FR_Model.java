package com.bitpolarity.spotifytestapp.GetterSetterModels;

public class FR_Model {
    static int profile_pic;
    static String name;

    public int getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(int profile_pic) {
        FR_Model.profile_pic = profile_pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public FR_Model(int profile_pic, String name){
        FR_Model.profile_pic = profile_pic;
        FR_Model.name = name;
    }
}
