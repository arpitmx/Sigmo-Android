package com.bitpolarity.spotifytestapp.application;

import android.app.Application;

import com.aghajari.emojiview.AXEmojiManager;
import com.aghajari.emojiview.iosprovider.AXIOSEmojiProvider;
import com.bitpolarity.spotifytestapp.SongDetails;
import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.ios.IosEmojiProvider;


public class Sigmo extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        EmojiManager.install(new IosEmojiProvider());
        AXEmojiManager.install(this,new AXIOSEmojiProvider(this));

    }

    void UnregisterBR(){
        unregisterReceiver(SongDetails.receiver);
    }

}
