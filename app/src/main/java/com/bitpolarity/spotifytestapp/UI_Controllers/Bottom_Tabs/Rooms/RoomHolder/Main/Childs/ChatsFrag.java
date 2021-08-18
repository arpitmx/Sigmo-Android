package com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Rooms.RoomHolder.Main.Childs;

import static android.view.WindowManager.LayoutParams.ANIMATION_CHANGED;
import static android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN;
import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bitpolarity.spotifytestapp.Adapters.ChatsAdapter.ChatsAdapter;
import com.bitpolarity.spotifytestapp.Adapters.ChatsAdapter.MultiViewChatAdapter;
import com.bitpolarity.spotifytestapp.DB_Handler;
import com.bitpolarity.spotifytestapp.GetterSetterModels.ChatListModel;
import com.bitpolarity.spotifytestapp.GetterSetterModels.ChatListModel_Multi;
import com.bitpolarity.spotifytestapp.R;
import com.bitpolarity.spotifytestapp.databinding.FragmentRoomChatBinding;
import com.bitpolarity.spotifytestapp.databinding.RoomActionBarBinding;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;


public class ChatsFrag extends Fragment {

    FragmentRoomChatBinding binding;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference msgRoot, isTypingRoot;
    private String temp_key;
    MediaPlayer mpSent, mpClick ;

    RecyclerView chatRV;
    MultiViewChatAdapter adapter;
    LinearLayoutManager layoutManager;
    SwipeRefreshLayout swipeRefreshLayout;
    private String userName , msg;
    ArrayList<ChatListModel_Multi> chatList;

    final static String TAG = "ChatsFrag";
    private Parcelable recyclerViewState;

    static final int TOTAL_ELEMENT_TO_LOAD = 20;
    private final int mCurrentPage = 1;
    static boolean isTyping = false;
    ShimmerFrameLayout shimmerFrameLayout;


    long delay = 1000; // 1 seconds after user stops typing
    long last_text_edit = 0;
    Handler handler = new Handler();


    private Runnable input_finish_checker = () -> {
        if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {
                updateTyping();
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRoomChatBinding.inflate(inflater, container , false);
        firebaseDatabase = FirebaseDatabase.getInstance();

       msgRoot= firebaseDatabase.getReference().child("Rooms").child(getActivity().getIntent().getStringExtra("room_name")).child("messages");
       isTypingRoot = firebaseDatabase.getReference().child("Rooms").child(getActivity().getIntent().getStringExtra("room_name")).child("members");

       shimmerFrameLayout = binding.shimmerFrameLayoutChatfrag;
       shimmerFrameLayout.startShimmerAnimation();

       mpSent = MediaPlayer.create(getContext(), R.raw.hs_bg_message_sent2);
       mpClick = MediaPlayer.create(getContext(), R.raw.know);

      // getActivity().getWindow().setSoftInputMode(SOFT_INPUT_ADJUST_PAN);
       //swipeRefreshLayout = binding.swipeRefresh;
       //swipeRefreshLayout.setEnabled(false);

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

        //getTypingMembers();

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               if(dataSnapshot.hasChild("typingNow")){
                   String s = dataSnapshot.child("typingNow").getValue().toString();
                if(!s.equals("") && !s.equals(DB_Handler.getUsername()) ){
                    binding.roomInput.istypingTV.setVisibility(View.VISIBLE);
                    isTyping = true;
                   // binding.roomInput.istypingTV.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.slide_up));
                    binding.roomInput.istypingTV.setText(dataSnapshot.child("typingNow").getValue()+ " is typing...");
                }else{
                       // binding.roomInput.istypingTV.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_down));
                    binding.roomInput.istypingTV.setVisibility(View.INVISIBLE);
                    isTyping = false;
                }}
               else{
                   binding.roomInput.istypingTV.setText("Sara here! No messages here, send one! ;) ");
               }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };

        isTypingRoot.addValueEventListener(postListener);
        binding.roomInput.sendBtn.setOnClickListener(v -> {
            sendMessage();
        });
        loadmessages();

        binding.roomInput.msgEditBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                handler.removeCallbacks(input_finish_checker);
                isTypingRoot.child("typingNow").setValue(DB_Handler.getUsername()) ;

            }

            @Override
            public void afterTextChanged(Editable s) {


                    last_text_edit = System.currentTimeMillis();
                    handler.postDelayed(input_finish_checker, delay);

                   // isTypingRoot.child("Divyanshu").child("isTyping").setValue("false");
            }
        });

//         swipeRefreshLayout.setOnRefreshListener(() -> {
//             mCurrentPage++;
//             chatList.clear();
//             loadmessages();
//         });

    }
    void loadmessages(){

       // Query msgQue = msgRoot.limitToLast(mCurrentPage*TOTAL_ELEMENT_TO_LOAD);



        msgRoot.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    //recyclerViewState = chatRV.getLayoutManager().onSaveInstanceState();
                    //int size = getModelList(snapshot).size();

                    adapter = new MultiViewChatAdapter(getModelList(snapshot));
                    chatRV.setVisibility(View.VISIBLE);
                    chatRV.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    //chatRV.getLayoutManager().onRestoreInstanceState(recyclerViewState);

                    //swipeRefreshLayout.setRefreshing(false);
                    //chatRV.scrollToPosition(c.size()- 1);



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
            if(msg.length()<300){
             if(!msg.trim().isEmpty()){
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

        while (i.hasNext()){
            msg = String.valueOf(((DataSnapshot)i.next()).getValue());
            userName = String.valueOf(((DataSnapshot)i.next()).getValue());

            if (userName.equals(DB_Handler.getUsername())){
                chatList.add(new ChatListModel_Multi(userName,msg,2));
                Log.d(TAG, "getModelList: TYPE 1"+msg);
            }else{
                chatList.add(new ChatListModel_Multi(userName,msg,1));
                Log.d(TAG, "getModelList: TYPE 2"+msg);

            }

        }
        shimmerFrameLayout.stopShimmerAnimation();
        shimmerFrameLayout.setVisibility(View.GONE);

        return chatList;
    }

    void sendMessage(){

        binding.roomInput.sendBtn.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.pop_in));
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
            binding.roomInput.msgEditBox.setError("Message invalid!");
            binding.roomInput.sendBtn.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.pop_out));

        }

    }

    void updateTyping(){

        isTypingRoot.child("typingNow").setValue("");
    }

    void getTypingMembers(){

        isTypingRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                   Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                //Log.d(TAG, "onDataChange: Map ChatsFrag"+map.toString());

                for (String key : map.keySet()){
                    Log.d(TAG, "onDataChange: KEYS"+ key+" : "+ map.get(key));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}