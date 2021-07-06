package com.bitpolarity.spotifytestapp.Bottom_Nav_Files;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentContainerView;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import androidx.annotation.NonNull;

import com.bitpolarity.spotifytestapp.Bottom_Nav_Files.Circle.Circle_Fragment;
import com.bitpolarity.spotifytestapp.Bottom_Nav_Files.Home_Fragment;
import com.bitpolarity.spotifytestapp.Bottom_Nav_Files.Profile_Fragment;
import com.bitpolarity.spotifytestapp.Bottom_Nav_Files.Rooms_Fragment;
import com.bitpolarity.spotifytestapp.R;
import com.bitpolarity.spotifytestapp.mMiniPlayer;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainHolder extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    mMiniPlayer miniPlayer;
    TextView sigmoTV ;
    ImageView bitpSymbol;
    ImageButton imgbtn;
    CircleImageView c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_holder);

        imgbtn = findViewById(R.id.imageButton);
        sigmoTV = findViewById(R.id.sigmoTitleBar);
        bitpSymbol = findViewById(R.id.bitpSymbl);
        c = (CircleImageView) findViewById(R.id.cir);

        bitpSymbol.setAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate));
        sigmoTV.setAnimation(AnimationUtils.loadAnimation(this , R.anim.fade_in));
        sigmoTV.setAnimation(AnimationUtils.loadAnimation(this , R.anim.slidein_left_to_right));

        bottomNavigation =  findViewById(R.id.bottom_nav);
        FragmentContainerView fragmentContainerView = findViewById(R.id.fragmentContainerView);

        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.open();

            }
        });


        RelativeLayout bottomSheetParentLayout = findViewById(R.id.mini_lin) ;
       BottomSheetBehavior mBottomSheetBehaviour;



        //miniPlayer = new mMiniPlayer();

        //miniPlayer.show(getSupportFragmentManager(),"Mini Player");





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

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

