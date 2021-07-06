package com.bitpolarity.spotifytestapp;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;

import com.bitpolarity.spotifytestapp.Bottom_Nav_Files.Circle.Circle_Fragment;
import com.bitpolarity.spotifytestapp.Bottom_Nav_Files.Home_Fragment;
import com.bitpolarity.spotifytestapp.Bottom_Nav_Files.Profile_Fragment;
import com.bitpolarity.spotifytestapp.Bottom_Nav_Files.Rooms_Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.widget.Toast;


public class MainHolder extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_holder);

        bottomNavigation =  findViewById(R.id.bottom_nav);
        FragmentContainerView fragmentContainerView = findViewById(R.id.fragmentContainerView);


        bottomNavigation.setOnItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) item -> {
            switch (item.getItemId()) {
//                    case R.id.nav_home:
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.fragmentContainerView,new Home_Fragment()).commit();
//
//                       // Toast.makeText(MainHolder.this, "Home", Toast.LENGTH_SHORT).show();
//                        break;


                case R.id.nav_circle:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainerView,new Circle_Fragment()).commit();
                    //Toast.makeText(MainHolder.this, "Exercise", Toast.LENGTH_SHORT).show();
                    break;


                case R.id.nav_rooms:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainerView,new Rooms_Fragment()).commit();
                    //Toast.makeText(MainHolder.this, "Post", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.nav_profile:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainerView,new Profile_Fragment()).commit();

                  //  Toast.makeText(MainHolder.this, "Social", Toast.LENGTH_SHORT).show();
                    break;


            }
            return true;


        });

    }}

