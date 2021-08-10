package com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Rooms.RoomHolder.Main.Childs;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bitpolarity.spotifytestapp.Adapters.ChatsAdapter.ChatsAdapter;
import com.bitpolarity.spotifytestapp.Adapters.ChatsAdapter.MultiViewChatAdapter;
import com.bitpolarity.spotifytestapp.DB_Handler;
import com.bitpolarity.spotifytestapp.GetterSetterModels.ChatListModel;
import com.bitpolarity.spotifytestapp.R;
import com.bitpolarity.spotifytestapp.databinding.FragmentRoomChatBinding;
import com.bitpolarity.spotifytestapp.databinding.RoomActionBarBinding;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;


public class ChatsFrag extends Fragment {

    FragmentRoomChatBinding binding;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference msgRoot;
    private String temp_key;
    MediaPlayer mpSent, mpClick ;

    RecyclerView chatRV;
    ChatsAdapter adapter;
    LinearLayoutManager layoutManager;

    private String userName , msg;
    ArrayList<ChatListModel> chatList;


    RoomActionBarBinding actionBarBinding;
    private Parcelable recyclerViewState;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRoomChatBinding.inflate(inflater, container , false);
        firebaseDatabase = FirebaseDatabase.getInstance();

       msgRoot= firebaseDatabase.getReference().child("Rooms").child(getActivity().getIntent().getStringExtra("room_name")).child("messages");

       mpSent = MediaPlayer.create(getContext(), R.raw.hs_bg_message_sent2);
       mpClick = MediaPlayer.create(getContext(), R.raw.know);

       chatRV = binding.chatsLayout;
       layoutManager = new LinearLayoutManager(getContext());
       chatList = new ArrayList<>();

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layoutManager.setOrientation(RecyclerView.VERTICAL);
        layoutManager.setStackFromEnd(true);
        chatRV.hasFixedSize();
        chatRV.setLayoutManager(layoutManager);
        chatRV.setNestedScrollingEnabled(false);






        binding.roomInput.sendBtn.setOnClickListener(v -> {
           // mpClick.start();
            String msg = binding.roomInput.msgEditBox.getText().toString();
            String usrname = DB_Handler.getUsername();

            Map<String, Object> map = new HashMap<>();
            temp_key = msgRoot.push().getKey();
            msgRoot.updateChildren(map);

            DatabaseReference in_msg = msgRoot.child(temp_key);
            Map<String, Object> map2 = new HashMap<>();



            if (filterText(msg)){

                map2.put("msg",msg);
                if (usrname!=null) {
                    map2.put("sender", usrname);
                    in_msg.updateChildren(map2);

                    Toast.makeText(getContext(),"Sent",Toast.LENGTH_SHORT).show();
                    binding.roomInput.msgEditBox.setText("");
                    mpSent.start();

                }else{
                    binding.roomInput.msgEditBox.setError("Internal error , restart sigmo!");
                }
            }
            else{
                binding.roomInput.msgEditBox.setError("Message can't be empty!");
            }
        });



        msgRoot.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                recyclerViewState = chatRV.getLayoutManager().onSaveInstanceState();

                ArrayList<ChatListModel> c = new ArrayList<>();
                c = getModelList(snapshot);
                adapter = new ChatsAdapter(c);
               // multiViewChatAdapter = new MultiViewChatAdapter(c);


            chatRV.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            chatRV.getLayoutManager().onRestoreInstanceState(recyclerViewState);
            chatRV.scrollToPosition(c.size()- 1);

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


    boolean filterText(String msg){

        boolean sendable = false;

        if(!msg.equals("") && msg.length()!=0){
             if(!msg.trim().isEmpty()){
                 if (!msg.matches("[\\n\\r]+")) {
                     sendable = true;
                 }
             }
        }

        return sendable;
    }






    ArrayList<ChatListModel> getModelList(DataSnapshot dataSnapshot){

        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()){
            msg = String.valueOf(((DataSnapshot)i.next()).getValue());
            userName = String.valueOf(((DataSnapshot)i.next()).getValue());
            chatList.add(new ChatListModel(userName,msg));
        }

        return chatList;
    }
}