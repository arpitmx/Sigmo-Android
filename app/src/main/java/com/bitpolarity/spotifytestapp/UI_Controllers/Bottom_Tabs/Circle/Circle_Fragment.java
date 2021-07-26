package com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Circle;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.bitpolarity.spotifytestapp.R;
import com.google.android.material.tabs.TabLayout;


public class Circle_Fragment extends Fragment {

    TabLayout tabLayout;
    ViewPager2 viewPager;

    ConstraintLayout constraintLayout;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_circle,container , false);

        tabLayout = v.findViewById(R.id.tabLayout);
        viewPager = v.findViewById(R.id.viewpager);
        tabLayout.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.slide_down));

        viewPager.setSaveEnabled(false);
        viewPager.setOffscreenPageLimit(1);

        final FragmentManager fm = getChildFragmentManager();
        final Circle_Tab_Adapter adapter = new Circle_Tab_Adapter(fm, getLifecycle());

        tabLayout.addTab(tabLayout.newTab().setText("Friends"));
        tabLayout.addTab(tabLayout.newTab().setText("Status"));

        viewPager.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(),true);
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

    @Override
    public void onResume() {
        super.onResume();

    }

}