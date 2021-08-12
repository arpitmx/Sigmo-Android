package com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Rooms.RoomHolder.MainHolder;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bitpolarity.spotifytestapp.DB_Handler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SupporterClass extends ViewModel {

    static String roomName;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference memberRoot;
    static final String TAG = "SupporterClass";
    boolean countIncreased = false;
    boolean countDecreased = false;
    int count = 0;
    boolean isConnected = true;
    static MutableLiveData<Integer> Ocount;
    DatabaseReference connectRef;


    SupporterClass(String roomName){


        this.roomName = roomName;
        firebaseDatabase = FirebaseDatabase.getInstance();
        memberRoot = firebaseDatabase.getReference().child("Rooms").child(roomName).child("members");
        connectRef = FirebaseDatabase.getInstance().getReference(".info/connected");


        countDecreased = false;
        countIncreased = false;

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


}
