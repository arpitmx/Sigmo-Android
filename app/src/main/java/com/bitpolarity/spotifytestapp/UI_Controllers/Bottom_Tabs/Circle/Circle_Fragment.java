package com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Circle;

import android.icu.lang.UCharacter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitpolarity.spotifytestapp.Adapters.CircleTabAdapter.Circle_Tab_Adapter;
import com.bitpolarity.spotifytestapp.databinding.FragmentCircleBinding;
import com.google.android.material.tabs.TabLayout;


public class Circle_Fragment extends Fragment {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    FragmentCircleBinding binding;
    ConstraintLayout constraintLayout;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding= FragmentCircleBinding.inflate(inflater, container, false);

        tabLayout = binding.tabLayout;
        viewPager = binding.viewpager;


        viewPager.setSaveEnabled(false);
        viewPager.setOffscreenPageLimit(1);

        final FragmentManager fm = getChildFragmentManager();
        final Circle_Tab_Adapter adapter = new Circle_Tab_Adapter(fm, getLifecycle());

        tabLayout.addTab(tabLayout.newTab().setText("Friends \uD83D\uDC4B"));
        tabLayout.addTab(tabLayout.newTab().setText("Stories ⚡"));

        viewPager.setAdapter(adapter);



        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


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

    }

    @Override
    public void onResume() {
        super.onResume();

    }

}