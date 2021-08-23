package com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Rooms.RoomHolder.MainHolder;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.bitpolarity.spotifytestapp.DB_Handler;
import com.bitpolarity.spotifytestapp.Singletons.TimeSystem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class SupporterClass extends ViewModel {

    static String roomName;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference memberRoot;
    static final String TAG = "SupporterClass";
    boolean countIncreased = false;
    boolean countDecreased = false;
    int count = 0;
    TimeSystem timeSystem;
    DatabaseReference connectRef,msgRoot;


    SupporterClass(String roomName){


        this.roomName = roomName;
        firebaseDatabase = FirebaseDatabase.getInstance();
        memberRoot = firebaseDatabase.getReference().child("Rooms").child(roomName).child("members");
        connectRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        timeSystem = TimeSystem.getInstance();
        msgRoot= firebaseDatabase.getReference().child("Rooms").child(roomName).child("messages");


    }

           void increaseCount(){


               memberRoot.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        memberRoot.child("allmembers").child(DB_Handler.getUsername()).child("isTyping").setValue("false");
                        countIncreased = true;
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d(TAG, "onCancelled: Error");
                    }
                });

    }


            void checkIfUserDisconnected(){



                memberRoot.child("allmembers").child(DB_Handler.getUsername()).onDisconnect().removeValue();

//                DatabaseReference inMsg = getMap();
//                Map<String, Object> map2 = new HashMap<>();
//
//                map2.put("msg","left");
//                map2.put("sender", DB_Handler.getUsername());
//                map2.put("TYPE", "2" );
//
//               inMsg.onDisconnect().updateChildren(map2);

            }


            void decreaseCount(){
                memberRoot.child("allmembers").child(DB_Handler.getUsername()).removeValue();
                countDecreased = true;
            }





            void getOnlineCount(){


                memberRoot.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        count = Integer.parseInt(snapshot.child("currentOnline").getValue().toString());
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d(TAG, "onCancelled: Error");
                    }
                });


            }



            void updateStatus(){

                connectRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        boolean connected = snapshot.getValue(Boolean.class);

                        if (connected) {
                            increaseCount();


                        } else {
                            decreaseCount();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        System.err.println("Listener was cancelled");
                    }
                });


            }


            void sendJoiningNotif(){

                Map<String, Object> map = new HashMap<>();
                String temp_key = msgRoot.push().getKey();
                msgRoot.updateChildren(map);
                String time = timeSystem.getTime_format_12h();


                DatabaseReference in_msg = msgRoot.child(temp_key);
                Map<String, Object> map2 = new HashMap<>();


                map2.put("msg","joined");
                map2.put("sender", DB_Handler.getUsername());
                map2.put("TYPE", "2");
                map2.put("TIME", time);


                    in_msg.updateChildren(map2);


            }


    void sendLeavingNotif(){

        Map<String, Object> map = new HashMap<>();
        String temp_key = msgRoot.push().getKey();
        msgRoot.updateChildren(map);
        String time = timeSystem.getTime_format_12h();

        DatabaseReference in_msg = msgRoot.child(temp_key);
        Map<String, Object> map2 = new HashMap<>();


        map2.put("msg","left");
        map2.put("sender", DB_Handler.getUsername());
        map2.put("TYPE", "2" );
        map2.put("TIME", time);


        in_msg.updateChildren(map2);

    }


    DatabaseReference getMap(){

        Map<String, Object> map = new HashMap<>();
        String temp_key = msgRoot.push().getKey();
        msgRoot.updateChildren(map);

        return msgRoot.child(temp_key);
    }


}
