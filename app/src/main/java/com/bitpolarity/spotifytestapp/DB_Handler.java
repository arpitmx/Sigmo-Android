package com.bitpolarity.spotifytestapp;

import static com.bitpolarity.spotifytestapp.Services.OnClearFromRecentService.prefs;

import android.content.SharedPreferences;
import android.util.Log;

import com.bitpolarity.spotifytestapp.DB_Related.fbase_bundle;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DB_Handler {

    //////////////////////////////== INIT ==//////////////////////////////////////////////
    static String username;
    Date date;
    String time ;

    SimpleDateFormat formatter;
    final String LOG = "db_handler";

    //Firebase
    DatabaseReference ref;
    final String usr_ROOT = "Users";
    final String room_ROOT = "Rooms";
    DatabaseReference ROOTPATH;
    SharedPreferences pref;

   // static SharedPreferences prefs;

    ////////////////////////////////== INIT ==////////////////////////////////////////////////////////////////////////

    public DB_Handler(){
        formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        if (ref == null) {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            ref = firebaseDatabase.getReference();

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


        ref.child(usr_ROOT).child(username).child("STATUS").setValue(status);
       // ref.child(usr_ROOT).child(username).child("STATUS").onDisconnect().setValue(0);
        ref.child(usr_ROOT).child(username).child("LA").setValue(time);

        if (status ==1)
        {
            Log.v(LOG, "User Status: Online");
            Log.v(LOG,"DB_STATUS > Session initiated at "+time);
        }
        else{
         Log.v(LOG, "User Status: Offline");
         Log.v(LOG,"DB_STATUS > Session ended at "+time);
        }





    }

   public void setUsername(String username){
        this.username = username;
       ROOTPATH =ref.child(usr_ROOT).child(username);

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
           f =  ref.child("Users");
        }else if (TYPE ==1){
            f =  ref.child("Rooms");
        }

         return f;
     }



     ////////////////////////////////// ROOOOOOOMS
     public void CreateRoom(String mRoomName){


        // RoomDetails

         ref.child(room_ROOT).child(mRoomName).child("roomDetails").child("roomName").setValue(mRoomName);
         ref.child(room_ROOT).child(mRoomName).child("roomDetails").child("hostDetails").child("userName").setValue(username);
        ref.child(room_ROOT).child(mRoomName).child("roomDetails").child("isPublic").setValue("true");
        ref.child(room_ROOT).child(mRoomName).child("roomDetails").child("size").child("total").setValue("30");
        date = new Date();
        ref.child(room_ROOT).child(mRoomName).child("roomDetails").child("startedAt").setValue(getTime(date));


        //Member Structure



     }

    ////////////////////////////////// ROOOOOOOMS



}


