package com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Nav_Files.Rooms.MainRoom;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Nav_Files.Rooms.MainRoom.Main.Childs.ChatsFrag;
import com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Nav_Files.Rooms.MainRoom.Main.Childs.MembersFrag;
import com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Nav_Files.Rooms.MainRoom.Main.Childs.SongQueueFrag;

import org.jetbrains.annotations.NotNull;

public class Room_Tab_Adapter extends FragmentStateAdapter {
    public Room_Tab_Adapter(@NonNull @NotNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new SongQueueFrag();
        } else if (position == 2) {
            return new MembersFrag();

        } else return new ChatsFrag();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
