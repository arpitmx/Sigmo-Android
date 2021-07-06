package com.bitpolarity.spotifytestapp.Bottom_Nav_Files.Circle;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitpolarity.spotifytestapp.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class Circle_Fragment extends Fragment {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    Circle_Tab_Adapter adapter;
    ConstraintLayout constraintLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_circle,container , false);

        tabLayout = v.findViewById(R.id.tabLayout);
        viewPager = v.findViewById(R.id.viewpager);
       // constraintLayout = v.findViewById(R.id.custom_action_bar_consLay);
       // constraintLayout.setElevation(0);


        tabLayout.addTab(tabLayout.newTab().setText("Search"));
        tabLayout.addTab(tabLayout.newTab().setText("Friends"));

        FragmentManager fm = getChildFragmentManager();
        adapter = new Circle_Tab_Adapter(fm , getLifecycle());
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