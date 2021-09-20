package com.bitpolarity.spotifytestapp.Singletons;

import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadingSystems {

    private static UploadingSystems instance = null;

    public static UploadingSystems getInstance(){
        if(instance == null){
            instance = new UploadingSystems();
        }
        return instance;
    }



    }


