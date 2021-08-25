package com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Rooms.RoomHolder.Main.Childs.ChatSection;


import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bitpolarity.spotifytestapp.Adapters.ChatsAdapter.MultiViewChatAdapter;
import com.bitpolarity.spotifytestapp.DB_Handler;
import com.bitpolarity.spotifytestapp.GetterSetterModels.ChatListModel_Multi;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ChatsViewHolder  extends ViewModel {


    private static final String TAG = "ChatsViewHolder" ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference msgRoot;
    String roomName, userName;
    private  final String TYPE_MSG = "1";
    private  final String TYPE_JOIN = "2";
    ArrayList temp = new ArrayList();
    ArrayList<ChatListModel_Multi> chatList;
    private MutableLiveData<ArrayList<ChatListModel_Multi>> chatListLD;

    // Responses
    private  final int success_sent = 1;
    private  final int failed_invalid_message = 0;
    private  final int failed_internal_error = 404;
    int listSize;


    ChatsViewHolder(String roomName){

       // userName = DB_Handler.getUsername();
        this.roomName = roomName;
        firebaseDatabase = FirebaseDatabase.getInstance();
        msgRoot= firebaseDatabase.getReference().child("Rooms").child(roomName).child("messages");
        chatList = new ArrayList<>();
        listSize = 0;


    }

    public int sendMessage(String msg, String username , String time ) {

        Map<String, Object> map = new HashMap<>();
        String temp_key = msgRoot.push().getKey();
        msgRoot.updateChildren(map);

        DatabaseReference in_msg = msgRoot.child(temp_key);
        Map<String, Object> map2 = new HashMap<>();

        if (filterText(msg)) {

            map2.put("msg", msg);
            map2.put("TYPE", TYPE_MSG);

            if (username != null) {

                map2.put("sender", username);
                map2.put("TIME", time);
                in_msg.updateChildren(map2);

                return success_sent;

            } else {
                return failed_internal_error;
            }

        }else{
            return failed_invalid_message;
        }

    }

        // Filter message

    private boolean filterText (String msg){

            boolean sendable = false;

            if (!msg.equals("") && msg.length() != 0) {
                if (msg.length() < 300) {
                    if (!msg.trim().isEmpty()) {
                        if (!msg.matches("[\\n\\r]+")) {
                            sendable = true;
                        }
                    }
                }
            }

            return sendable;
        }

    ArrayList<ChatListModel_Multi> getModelList(DataSnapshot dataSnapshot){

        Iterator i = dataSnapshot.getChildren().iterator();
         String userName , msg;
         String TYPE, TIME;

        while (i.hasNext()){


            TIME = String.valueOf(((DataSnapshot) i.next()).getValue());
            Log.d(TAG, " TIME "+TIME);

            TYPE = String.valueOf(((DataSnapshot) i.next()).getValue());
            Log.d(TAG, " TYPE - MSG "+TYPE);

            msg = String.valueOf(((DataSnapshot)i.next()).getValue());
            Log.d(TAG, " MSG-rec "+msg);

            userName = String.valueOf(((DataSnapshot)i.next()).getValue());
            Log.d(TAG, " USRNAME "+userName);

            if(!TYPE.equals(TYPE_JOIN)) {
                temp.add(userName);
            }

            if (TYPE.equals(TYPE_MSG)) {
                if (userName.equals(DB_Handler.getUsername())) {
                    chatList.add(new ChatListModel_Multi(userName, msg, 2,TIME));
                    Log.d(TAG, "OUTGOING: TYPE 2" + msg);

                } else {

                    if (temp.size() > 1) {

                        if (temp.get(temp.size() - 2).equals(userName)) {

                            chatList.add(new ChatListModel_Multi(userName, msg, 3,TIME));
                            Log.d(TAG, "INCOMING_SAME_USER: TYPE 3" + msg);

                        } else {
                            chatList.add(new ChatListModel_Multi(userName, msg, 1, TIME));
                            Log.d(TAG, "INCOMING: TYPE 1" + msg);
                        }

                    } else {
                        chatList.add(new ChatListModel_Multi(userName, msg, 1, TIME));
                        Log.d(TAG, "INCOMING: TYPE 1" + msg);
                    }
                }

            } else  {
                chatList.add(new ChatListModel_Multi(userName, msg, 4, TIME));
                Log.d(TAG, "JOINING/LEAVING : TYPE 4" + msg);
            }

        }


        Log.d(TAG, "TEMP "+ temp);
        listSize = chatList.size();

        return chatList;
    }


    public int getListSize() {
        return chatList.size();
    }


    // This gets into adapter
    private void setChatListLD(ArrayList<ChatListModel_Multi> chatArrayList){
        chatListLD.postValue(chatArrayList);
    }

    public LiveData<ArrayList<ChatListModel_Multi>> getChatListLD(){
        if (chatListLD==null) chatListLD = new MutableLiveData<>();
        return chatListLD;
    }


    void postMessages(){
        msgRoot.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                setChatListLD(getModelList(snapshot));

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    ArrayList<String> getBoldStrings(String s){

        s = s.trim();
        StringBuilder tBold = new StringBuilder();
        ArrayList<String> boldList = new ArrayList<>();
        String[] arr = s.split("");
        ArrayList<List> e;



        if (s.contains("*")) {
            if(isBalanced(arr)){
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i].equals("*")) {
                        for (int j = i + 1; j < arr.length; j++) {
                            if (!arr[j].equals("*")) {
                                tBold.append(arr[j]);
                            } else {
                                boldList.add(String.valueOf(tBold));
                                tBold.setLength(0);
                                i = j + 1;
                                break;
                            }
                        }
                    }

                }

                return boldList;

            }else{
                return null;
            }

        } else {
            return null;
        }
    }

   public ArrayList<Integer> getBoldsIndexes(String s){

        s = s.trim();
        ArrayList<Integer> boldList = new ArrayList<>();
        String[] arr = s.split("");

        if (s.contains("*")) {
            if(isBalanced(arr)){
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i].equals("*")) {
                        boldList.add(i+1);
                        for (int j = i + 1; j < arr.length; j++) {
                            if (arr[j].equals("*")) {
                                boldList.add(j-1);
                                i = j + 1;
                                break;
                            }}}}

                return boldList;

            }else{ return null;
            }
        }
        else{ return null;
        }
    }


    private boolean isBalanced(String[] arr){
        int count = 0;
        for (String i : arr){
            if(i.equals("*")) count++;
        }
        return count % 2 == 0;
    }

    public ArrayList<ChatListModel_Multi> getChatList(){

        if(chatList!=null){

            return  chatList;
        }
        else{
            return null;
        }
    }



}



