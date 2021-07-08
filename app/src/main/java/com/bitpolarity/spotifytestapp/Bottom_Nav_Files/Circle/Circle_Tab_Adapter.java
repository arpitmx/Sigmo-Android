package com.bitpolarity.spotifytestapp.Bottom_Nav_Files.Circle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.bitpolarity.spotifytestapp.StatusActivity;

import org.jetbrains.annotations.NotNull;

public class Circle_Tab_Adapter extends FragmentStateAdapter {
    public Circle_Tab_Adapter(@NonNull @NotNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new Circle_Search_Fragment();
        }
        return new StatusActivity();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}