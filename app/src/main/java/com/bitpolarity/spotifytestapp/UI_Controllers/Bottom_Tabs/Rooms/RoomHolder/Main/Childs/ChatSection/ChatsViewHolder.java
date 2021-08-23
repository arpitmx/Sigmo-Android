package com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Rooms.RoomHolder.Main.Childs.ChatSection;


import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModel;

import com.bitpolarity.spotifytestapp.DB_Handler;
import com.bitpolarity.spotifytestapp.GetterSetterModels.ChatListModel_Multi;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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



}



