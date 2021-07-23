package com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Nav_Files.Rooms.MainRoom.MainHolder;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.bitpolarity.spotifytestapp.R;
import com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Nav_Files.Circle.Circle_Tab_Adapter;
import com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Nav_Files.Rooms.MainRoom.Room_Tab_Adapter;
import com.google.android.material.tabs.TabLayout;


public class RoomMainFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    Room_Tab_Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_room_mainholder,container , false);

        tabLayout = v.findViewById(R.id.tabLayout_Rooms);
        viewPager = v.findViewById(R.id.viewpager_Rooms);
        tabLayout.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.slide_down));


        // constraintLayout = v.findViewById(R.id.custom_action_bar_consLay);
        // constraintLayout.setElevation(0);


        tabLayout.addTab(tabLayout.newTab().setText("Queue"));
        tabLayout.addTab(tabLayout.newTab().setText("Chats"));
        tabLayout.addTab(tabLayout.newTab().setText("Members"));

        FragmentManager fm = getChildFragmentManager();
        adapter = new Room_Tab_Adapter(fm , getLifecycle());
        viewPager.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });


        return v;
    }
}