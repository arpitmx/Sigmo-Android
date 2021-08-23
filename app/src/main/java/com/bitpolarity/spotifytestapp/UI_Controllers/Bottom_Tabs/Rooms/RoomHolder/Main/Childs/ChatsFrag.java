package com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Rooms.RoomHolder.Main.Childs;


import android.graphics.Color;
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
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.aghajari.emojiview.AXEmojiManager;
import com.aghajari.emojiview.view.AXEmojiPopup;
import com.aghajari.emojiview.view.AXSingleEmojiView;
import com.bitpolarity.spotifytestapp.Adapters.ChatsAdapter.MultiViewChatAdapter;
import com.bitpolarity.spotifytestapp.DB_Handler;
import com.bitpolarity.spotifytestapp.GetterSetterModels.ChatListModel_Multi;
import com.bitpolarity.spotifytestapp.LinearLayoutManagers.SigmoLinearLayoutManager;
import com.bitpolarity.spotifytestapp.R;
import com.bitpolarity.spotifytestapp.RecyclerScrollManager;
import com.bitpolarity.spotifytestapp.Singletons.TimeSystem;
import com.bitpolarity.spotifytestapp.databinding.FragmentRoomChatBinding;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



public class ChatsFrag extends Fragment  {

    FragmentRoomChatBinding binding;
    FirebaseDatabase firebaseDatabase;
   public static DatabaseReference msgRoot;
    static DatabaseReference isTypingRoot;
    static DatabaseReference bgRoot;
    private String temp_key;
    MediaPlayer mpSent, mpClick ;

    RecyclerView chatRV;
    MultiViewChatAdapter adapter;
    LinearLayoutManager layoutManager;
    SigmoLinearLayoutManager speedyLinearLayoutManager;
    SwipeRefreshLayout swipeRefreshLayout;
    private String userName , msg;
    private String TYPE, TIME;
    ArrayList<ChatListModel_Multi> chatList;
    public static DatabaseReference in_msg;
    final static String TAG = "ChatsFrag";
    private Parcelable recyclerViewState;
    TimeSystem timeSystem;

    static final int TOTAL_ELEMENT_TO_LOAD = 20;
    private final int mCurrentPage = 1;
    static boolean isTyping = false;
    ShimmerFrameLayout shimmerFrameLayout;
    List<String> temp = new ArrayList();




    long delay = 500; // 1 seconds after user stops typing
    long last_text_edit = 0;
    Handler handler = new Handler();
    int listSize= 0;





    private static final String TYPE_MSG = "1";
    private static final String TYPE_JOIN = "2";
    private static final int TYPE_LEFT = R.dimen.TYPE_LEFT;
    private int lastFirstVisiblePosition;


    private Runnable input_finish_checker = () -> {
        if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {
                updateTyping();
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRoomChatBinding.inflate(inflater, container , false);
        firebaseDatabase = FirebaseDatabase.getInstance();
        timeSystem = TimeSystem.getInstance();

       msgRoot= firebaseDatabase.getReference().child("Rooms").child(getActivity().getIntent().getStringExtra("room_name")).child("messages");
       bgRoot = firebaseDatabase.getReference().child("roomBG");
       isTypingRoot = firebaseDatabase.getReference().child("Rooms").child(getActivity().getIntent().getStringExtra("room_name")).child("members");



       shimmerFrameLayout = binding.shimmerFrameLayoutChatfrag;
       shimmerFrameLayout.startShimmerAnimation();

       mpSent = MediaPlayer.create(getContext(), R.raw.hs_bg_message_sent2);
       mpClick = MediaPlayer.create(getContext(), R.raw.know);

      //getActivity().getWindow().setSoftInputMode(SOFT_INPUT_ADJUST_PAN);
       //swipeRefreshLayout = binding.swipeRefresh;
       //swipeRefreshLayout.setEnabled(false);

       chatRV = binding.chatsLayout;
       //layoutManager = new LinearLayoutManager(getContext());
       speedyLinearLayoutManager = new SigmoLinearLayoutManager(getContext());
       chatList = new ArrayList<>();



       return binding.getRoot();

    }







    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



//        layoutManager.setOrientation(RecyclerView.VERTICAL);
//        layoutManager.setStackFromEnd(true);


        initOnScroll_UP_JUMP_TO_TOP();


        speedyLinearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        speedyLinearLayoutManager.setStackFromEnd(true);

        chatRV.hasFixedSize();
        chatRV.setLayoutManager(speedyLinearLayoutManager);
        chatRV.setNestedScrollingEnabled(false);
        loadmessages();

        binding.roomInput.sendBtn.setOnClickListener(v -> {
            sendMessage();
        });



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


    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();

        ///////////////////////////////////////////////////////Emoji

        initEmojiBoard();

        //////////////////////////////////////////////////////Emoji


        binding.jumpToEndFAB.setOnClickListener(view -> {
            onClickFab();
        });

        chatRV.addOnScrollListener(new RecyclerScrollManager.FabScroll() {

            @Override
            public void show() {
                binding.jumpToEndFAB.setVisibility(View.VISIBLE);
                binding.jumpToEndFAB.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.pop_in));
                binding.jumpToEndFAB.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
            }

            @Override
            public void hide() {
                //binding.jumpToEndFAB.setVisibility(View.VISIBLE);
               // binding.jumpToEndFAB.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
                binding.jumpToEndFAB.animate().translationY(binding.jumpToEndFAB.getHeight() +30).setInterpolator(new AccelerateInterpolator(2)).start();
            }
        });


        try {
            bgRoot.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String url = snapshot.child("url").getValue().toString();
                    setChatWall(url);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch (Exception e ){
            Log.d(TAG, "onResume: Error setting chat wallpaper");
        }



    }

    private void initEmojiBoard() {

        //final EmojiPopup emojiPopup = EmojiPopup.Builder.fromRootView(binding.getRoot()).build(binding.roomInput.msgEditBox);

        AXSingleEmojiView emojiView = new AXSingleEmojiView(getContext());
        emojiView.setEditText(binding.roomInput.msgEditBox);

        AXEmojiPopup emojiPopup = new AXEmojiPopup(emojiView);
        setTheme();
        binding.roomInput.til.setStartIconOnClickListener(view -> {
            emojiPopup.toggle();

            binding.roomInput.msgEditBox.setOnClickListener(view1 -> emojiPopup.dismiss());

        });


    }

    void setTheme(){

        AXEmojiManager.getEmojiViewTheme().setFooterEnabled(true);
        AXEmojiManager.getEmojiViewTheme().setSelectionColor(Color.TRANSPARENT);
        AXEmojiManager.getEmojiViewTheme().setSelectedColor(0xff82ADD9);
        AXEmojiManager.getEmojiViewTheme().setFooterSelectedItemColor(0xff82ADD9);
        AXEmojiManager.getEmojiViewTheme().setBackgroundColor(0xFF1E2632);
        AXEmojiManager.getEmojiViewTheme().setCategoryColor(0xFF232D3A);
        AXEmojiManager.getEmojiViewTheme().setFooterBackgroundColor(0xFF232D3A);
        AXEmojiManager.getEmojiViewTheme().setVariantPopupBackgroundColor(0xFF232D3A);
        AXEmojiManager.getEmojiViewTheme().setVariantDividerEnabled(false);
        AXEmojiManager.getEmojiViewTheme().setDividerColor(0xFF1B242D);
        AXEmojiManager.getEmojiViewTheme().setDefaultColor(0xFF677382);
        AXEmojiManager.getEmojiViewTheme().setTitleColor(0xFF677382);
        AXEmojiManager.getEmojiViewTheme().setAlwaysShowDivider(true);

    }

    void onClickFab(){
        speedyLinearLayoutManager.smoothScrollToPosition(chatRV, (RecyclerView.State) recyclerViewState,listSize-1);
        //chatRV.smoothScrollToPosition(listSize-1);
        binding.jumpToEndFAB.animate().translationY(binding.jumpToEndFAB.getHeight() +30).setInterpolator(new AccelerateInterpolator(5)).start();
        RecyclerScrollManager.FabScroll.setScrollDist();

    }

    void handleEmojiView(){



//        if(!layout.isShowing()) {
//            layout.show();
//            layout.setVisibility(View.VISIBLE);
//
//            binding.roomInput.msgEditBox.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    layout.hideAndOpenKeyboard();
//                    layout.toggle();
//                    layout.setVisibility(View.GONE);
//
//
//                }
//            });
//
//        }
//
//        else {
//            //layout.setVisibility(View.GONE);
//            layout.hideAndOpenKeyboard();
//            //layout.dismiss();
//        }

    }

    void setChatWall(String url){

        Glide.with(getContext())
                .load(url)
                .into(binding.roomBackgroundWallpaper);
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

                    if(binding.jumpToEndFAB.getVisibility()==View.VISIBLE) {
                       // binding.jumpToEndFAB.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_out));
                        binding.jumpToEndFAB.setVisibility(View.GONE);
                    }

                    //chatRV.getLayoutManager().onRestoreInstanceState(recyclerViewState);

                    //swipeRefreshLayout.setRefreshing(false);
                    //chatRV.scrollToPosition(listSize- 1);



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

        shimmerFrameLayout.stopShimmerAnimation();
        shimmerFrameLayout.setVisibility(View.GONE);
        listSize = chatList.size();

        return chatList;
    }

    void sendMessage(){

        binding.roomInput.sendBtn.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.pop_in));
        String msg = binding.roomInput.msgEditBox.getText().toString();
        String usrname = DB_Handler.getUsername();
        String time = timeSystem.getTime_format_12h();


        Map<String, Object> map = new HashMap<>();
        String temp_key = msgRoot.push().getKey();
        msgRoot.updateChildren(map);

        DatabaseReference in_msg = msgRoot.child(temp_key);
        Map<String, Object> map2 = new HashMap<>();


        if (filterText(msg)){

            map2.put("msg",msg);
            map2.put("TYPE",TYPE_MSG);

            if (usrname!=null) {
                map2.put("sender", usrname);
                map2.put("TIME",time);
                in_msg.updateChildren(map2);
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



    private void initOnScroll_UP_JUMP_TO_TOP() {

        chatRV.addOnScrollListener(new RecyclerScrollManager.MiniplayerScroll() {

            //Move Miniplayer up and show jump to top tab

            @Override
            public void show() {
                binding.miniPlayerRoom.jumpToTop.setVisibility(View.VISIBLE);
                binding.miniPlayerRoom.jumpToTop.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.pop_in_jump_to_top));
                binding.miniPlayerRoom.getRoot().animate().translationY(-binding.miniPlayerRoom.getRoot().getHeight()).setInterpolator(new AccelerateInterpolator(2)).start();


            }



            //show Miniplayer up and hide jump to top tab


            @Override
            public void hide() {
                binding.miniPlayerRoom.jumpToTop.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.fade_out));
                binding.miniPlayerRoom.jumpToTop.setVisibility(View.GONE);
                binding.miniPlayerRoom.getRoot().animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();

            }
        });


        binding.miniPlayerRoom.jumpToTop.setOnClickListener(view -> {
            chatRV.smoothScrollToPosition(0);
            binding.miniPlayerRoom.getRoot().animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
            binding.miniPlayerRoom.jumpToTop.setVisibility(View.GONE);
            RecyclerScrollManager.MiniplayerScroll.setScrollDist();
        });




    }




}