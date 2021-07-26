package com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Rooms.MainRoom.MainHolder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.ImageButton;

import com.bitpolarity.spotifytestapp.R;

public class RoomHolderActivity extends AppCompatActivity {

    ImageButton backBtn;
    //current time 18:32 expected 19:34

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_holder);



//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.room_parent_container,new RoomMainFragment()).addToBackStack(null)
                .commit();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    //   void onclick(View v){
//        switch (v.findViewById()){
//            case R.id.room_back_BTN:
//
//        }
//    }
}