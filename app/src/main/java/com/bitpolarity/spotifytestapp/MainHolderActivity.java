package com.bitpolarity.spotifytestapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Circle.TabManagerFragment;

public class MainHolderActivity extends AppCompatActivity {

     FragmentManager fm ;
     Fragment fragment ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level_main_holder);

        fm = getSupportFragmentManager();
        fragment = new TabManagerFragment();
        fm.beginTransaction().replace(R.id.fragmentcontainer_toplevel_tabholder, fragment).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
}