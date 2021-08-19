package com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Rooms.RoomHolder.MainHolder;

import static com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Rooms.RoomHolder.MainHolder.RoomHolderActivity.roomName;

import android.content.Intent;
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
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.bitpolarity.spotifytestapp.R;
import com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Rooms.RoomHolder.Main.Childs.ChatsFrag;
import com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Rooms.RoomHolder.Room_Tab_Adapter;
import com.bitpolarity.spotifytestapp.UI_Controllers.MainHolder;
import com.bitpolarity.spotifytestapp.databinding.FragmentRoomMainholderBinding;
import com.bitpolarity.spotifytestapp.databinding.FragmentRoomsBinding;
import com.bitpolarity.spotifytestapp.databinding.RoomActionBarBinding;
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
    DatabaseReference memberRoot;
    final static String TAG = "RoomHolderActivity";
    ConstraintLayout constraintLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRoomMainholderBinding.inflate(inflater, container, false);
        binding.roomActionBar.roomTitleBar.setText(roomName);

        firebaseDatabase = FirebaseDatabase.getInstance();
        memberRoot = firebaseDatabase.getReference().child("Rooms").child(roomName).child("members");

//
//        tabLayout = binding.tabLayoutRooms;
//        viewPager = binding.viewpagerRooms;
//        tabLayout.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.slide_down));

        binding.roomActionBar.roomBackBTN.setOnClickListener(view -> getActivity().onBackPressed());



//         constraintLayout =  binding.roomActionBar.customActionBarConsLay;
//         constraintLayout.setElevation(0);

//
//        tabLayout.addTab(tabLayout.newTab().setText("Queue"));
//        tabLayout.addTab(tabLayout.newTab().setText("Chats"));
//        tabLayout.addTab(tabLayout.newTab().setText("Members"));
//
//        FragmentManager fm = getChildFragmentManager();
//        adapter = new Room_Tab_Adapter(fm , getLifecycle());
//        viewPager.setAdapter(adapter);
//        viewPager.setCurrentItem(1);
//
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//            }
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//            }
//        });
//
//        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                tabLayout.selectTab(tabLayout.getTabAt(position));
//            }
//        });

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