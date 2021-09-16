package com.bitpolarity.spotifytestapp.Adapters.CircleTabAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.bitpolarity.spotifytestapp.TopLevelTabs.MainHolderFragment;
import com.bitpolarity.spotifytestapp.TopLevelTabs.Profile_Fragment;

public class TopLevelTabAdapter extends FragmentStateAdapter
{
    final Fragment profile_fragment = new Profile_Fragment();
    final Fragment mainHolderFragment = new MainHolderFragment();

    public TopLevelTabAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {

            return  profile_fragment;
        }
        return mainHolderFragment;
    }

    @Override
    public int getItemCount() {
        return 2 ;
    }


}
