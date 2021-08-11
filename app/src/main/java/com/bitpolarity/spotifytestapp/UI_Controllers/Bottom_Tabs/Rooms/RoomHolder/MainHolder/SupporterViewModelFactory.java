package com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Rooms.RoomHolder.MainHolder;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SupporterViewModelFactory implements ViewModelProvider.Factory {

    String roomName;
    SupporterViewModelFactory(String roomName){
        this.roomName = roomName;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == SupporterClass.class) {
            return (T) new SupporterClass(roomName);
        }
        return null;
    }
}
