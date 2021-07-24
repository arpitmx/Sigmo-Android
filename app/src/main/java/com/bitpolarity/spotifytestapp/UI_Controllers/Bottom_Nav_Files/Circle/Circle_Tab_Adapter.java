package com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Nav_Files.Circle;

import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Nav_Files.Rooms.MainRoom.Main.Childs.ChatsFrag;
import com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Nav_Files.Rooms.MainRoom.Main.Childs.MembersFrag;
import com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Nav_Files.Rooms.MainRoom.Main.Childs.SongQueueFrag;

import org.jetbrains.annotations.NotNull;

public class Circle_Tab_Adapter extends FragmentStateAdapter
{
    final Fragment friendFragment = new StatusActivity();
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
