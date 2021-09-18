package com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Rooms.RoomHolder.MainHolder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bitpolarity.spotifytestapp.DB_Handler;
import com.bitpolarity.spotifytestapp.R;
import com.bitpolarity.spotifytestapp.databinding.ActivityRoomHolderBinding;
import com.bitpolarity.spotifytestapp.databinding.RoomActionBarBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;
import com.squareup.picasso.Picasso;

public class RoomHolderActivity extends AppCompatActivity {

    ImageButton backBtn;
    ActivityRoomHolderBinding binding;
    SupporterClass supporterClass;
    TextView onlineCurrent;
    static String roomName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        super.onCreate(savedInstanceState);


        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        roomName  = getIntent().getStringExtra("room_name");

        binding = ActivityRoomHolderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        supporterClass = new ViewModelProvider(this, new SupporterViewModelFactory(roomName)).get(SupporterClass.class);


       // supporterClass.getOnlineCount();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.room_parent_container,new RoomMainFragment()).addToBackStack(null)
                .commit();

        supporterClass.sendJoiningNotif();

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supporterClass.decreaseCount();
        supporterClass.sendLeavingNotif();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        supporterClass.decreaseCount();
    }

    @Override
    protected void onResume() {
        super.onResume();
        supporterClass.updateStatus();
        supporterClass.checkIfUserDisconnected();

    }




}