package com.bitpolarity.spotifytestapp;

import static com.bitpolarity.spotifytestapp.Services.OnClearFromRecentService.prefs;

import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.bitpolarity.spotifytestapp.DB_Related.fbase_bundle;
import com.bitpolarity.spotifytestapp.Singletons.TimeSystem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DB_Handler  {

    //////////////////////////////== INIT ==//////////////////////////////////////////////
    static String username;
    Date date;
    String time ;

    SimpleDateFormat formatter;
    final String LOG = "db_handler";
    String url = "";
    //Firebase
    DatabaseReference Main_ref,RoomBase_ref  ;
    final String usr_ROOT = "Users_DMode";
    final static String room_ROOT = "Rooms";
    final static String room_BASE = "RoomBase";
    DatabaseReference ROOTPATH;
    SharedPreferences pref;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    TimeSystem timeSystem;

   // static SharedPreferences prefs;

    ////////////////////////////////== INIT ==////////////////////////////////////////////////////////////////////////

    public DB_Handler(){
        formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        firebaseDatabase = FirebaseDatabase.getInstance();

        if (Main_ref == null) {

            Main_ref = firebaseDatabase.getReference();
            RoomBase_ref = firebaseDatabase.getReference().child(room_BASE);
        }
    }



    /////////////////////////////////////////-------BASIC IO--------/////////////////////////////////


    String getTime(Date date){


        String datetime = formatter.format(date);
        return datetime;

    }
    public void setStatus(int status){
        //fbase_bundle bundle = new fbase_bundle(status);
        date = new Date();
        time = getTime(date);


        Main_ref.child(usr_ROOT).child(username).child("STATUS").setValue(status);
       // ref.child(usr_ROOT).child(username).child("STATUS").onDisconnect().setValue(0);
        Main_ref.child(usr_ROOT).child(username).child("LA").setValue(time);

        if (status ==1)
        {
            Log.v(LOG, "User Status: Online");
            Log.v(LOG,"DB_STATUS > Session initiated at "+time);
        }
        else {
            Log.v(LOG, "User Status: Offline");
            Log.v(LOG, "DB_STATUS > Session ended at " + time);
        }
    }


    public void setStatusOffline(){

        date = new Date();
        time = getTime(date);

        Main_ref.child(usr_ROOT).child(username).child("STATUS").onDisconnect().setValue(0);
        Main_ref.child(usr_ROOT).child(username).child("LA").onDisconnect().setValue(time);
        Main_ref.child(usr_ROOT).child(username).child("isPlaying").onDisconnect().setValue(false);


    }


   public void setUsername(String username){
        this.username = username;
       ROOTPATH = Main_ref.child(usr_ROOT).child(username);

   }

   public static String getUsername(){
        if (username!=null) {
            return username;
        }else{
            //prefs=  getSharedPreferences("com.bitpolarity.spotifytestapp",MODE_PRIVATE);
            username = prefs.getString("Username","Error-1");
            return username;
        }
   }

    /////////////////////////////////////////-------BASIC IO--------/////////////////////////////////


    /////////////////////////////////////////------SONG DETAIL SECTION---------/////////////////////////////////


     void setSong_Details(String trackID,String posterURL, String artistName, String albumName, String trackName, String trackLength ){

         if (artistName == null) artistName = "Loading";
         else if (albumName==null) albumName = "Loading";
         else if (trackName==null) trackName = "Loading";
         else if (trackLength == null) trackLength = "0";

         fbase_bundle bundle = new fbase_bundle(trackID,posterURL,artistName.replace(",",""),albumName.replace(",",""),trackName.replace(",",""),trackLength);
         ROOTPATH.child("SD").setValue(bundle);

     }

     void setSong_PlaybackDetails(boolean isplaying){
        if (isplaying) ROOTPATH.child("isPlaying").setValue(isplaying);
        else ROOTPATH.child("isPlaying").setValue(isplaying);

     }

     public DatabaseReference getRef(int TYPE){
         DatabaseReference f = null;
        if (TYPE == 0){
           f =  Main_ref.child("Users");
        }else if (TYPE ==1){
            f =  Main_ref.child("Rooms");
        }

         return f;
     }



     ////////////////////////////////// ROOOOOOOMS


     public void CreateRoom(String mRoomName, String dpURL){

        // RoomDetails
         timeSystem = TimeSystem.getInstance();
         Main_ref.child(room_ROOT).child(mRoomName).child("roomDetails").child("roomName").setValue(mRoomName);
         RoomBase_ref.child(mRoomName).child("roomDetails").child("roomName").setValue(mRoomName);

         RoomBase_ref.child(mRoomName).child("roomDetails").child("hostDetails").child("userName").setValue(username);
         RoomBase_ref.child(mRoomName).child("roomDetails").child("isPublic").setValue("true");
         RoomBase_ref.child(mRoomName).child("roomDetails").child("size").child("total").setValue("30");
         RoomBase_ref.child(mRoomName).child("roomDetails").child("dpURL").setValue(dpURL);
         RoomBase_ref.child(mRoomName).child("roomDetails").child("startedAt").setValue(timeSystem.get_date_day_time_12h());

     }

    ////////////////////////////////// ROOOOOOOMS



}


