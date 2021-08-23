package com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Rooms.RoomHolder.Main.Childs.ChatSection;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.bitpolarity.spotifytestapp.Spotify.SpotifyViewModel;

public class ChatsViewHolderFactory implements ViewModelProvider.Factory
{

    String roomName;

    ChatsViewHolderFactory(String roomName){
        this.roomName = roomName;
    }

    public <T extends ViewModel>T create(Class<T> modelClass){
        return (T) new ChatsViewHolder(roomName);
    }



}
