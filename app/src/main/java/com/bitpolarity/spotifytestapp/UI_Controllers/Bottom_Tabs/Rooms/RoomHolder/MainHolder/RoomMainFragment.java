package com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Rooms.RoomHolder.MainHolder;

import static com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Rooms.RoomHolder.MainHolder.RoomHolderActivity.roomName;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bitpolarity.spotifytestapp.DB_Handler;
import com.bitpolarity.spotifytestapp.R;
import com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Rooms.RoomHolder.Main.Childs.ChatSection.ChatsFrag;
import com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Rooms.RoomHolder.Room_Tab_Adapter;
import com.bitpolarity.spotifytestapp.databinding.FragmentRoomMainholderBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;


public class RoomMainFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    Room_Tab_Adapter adapter;
    FragmentRoomMainholderBinding binding;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference memberRoot, isTypingRoot;
    final static String TAG = "RoomHolderActivity";
    ConstraintLayout constraintLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRoomMainholderBinding.inflate(inflater, container, false);
        binding.roomActionBar.roomTitleBar.setText(roomName);
        firebaseDatabase = FirebaseDatabase.getInstance();

        isTypingRoot = firebaseDatabase.getReference().child("Rooms").child(getActivity().getIntent().getStringExtra("room_name")).child("members");
        memberRoot = firebaseDatabase.getReference().child("Rooms").child(roomName).child("members");

        binding.roomActionBar.roomBackBTN.setOnClickListener(view -> getActivity().onBackPressed());

        //getTypingMembers();

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild("typingNow")){
                    String s = dataSnapshot.child("typingNow").getValue().toString();
                    if(!s.equals("") && !s.equals(DB_Handler.getUsername()) ){
                        binding.roomActionBar.istypingTV.setVisibility(View.VISIBLE);
                        // binding.roomInput.istypingTV.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.slide_up));
                        binding.roomActionBar.istypingTV.setText(dataSnapshot.child("typingNow").getValue()+ " is typing...");
                        binding.roomActionBar.totalOnlineTv.setVisibility(View.GONE);

                    }else{
                        // binding.roomInput.istypingTV.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_down));
                        binding.roomActionBar.istypingTV.setVisibility(View.GONE);
                        binding.roomActionBar.totalOnlineTv.setVisibility(View.VISIBLE);

                    }}
                else{
                    Toast.makeText(getContext(), "No messages , send one! ;)", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };

        isTypingRoot.addValueEventListener(postListener);









        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        memberRoot.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("allmembers")) {
                    Map<String, Object> map = (Map<String, Object>) snapshot.child("allmembers").getValue();
                    binding.roomActionBar.totalOnlineTv.setText(map.size()+" online");
                }
                else{
                    binding.roomActionBar.totalOnlineTv.setText("Loading");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled: Error");
            }
        });


        FragmentManager fragmentManager = getChildFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.child_chat_frameLayout,new ChatsFrag()).addToBackStack(null)
                .commit();

    }
}