package com.bitpolarity.spotifytestapp.Adapters.CircleTabAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Circle.Circle_Search_Fragment;
import com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Circle.StatusFragment;

public class Circle_Tab_Adapter extends FragmentStateAdapter
{
    final Fragment friendFragment = new StatusFragment();
    final Fragment circleSearchFragment = new Circle_Search_Fragment();

    public Circle_Tab_Adapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return circleSearchFragment;
        }
        return friendFragment;
    }

    @Override
    public int getItemCount() {
        return 2 ;
    }


}
