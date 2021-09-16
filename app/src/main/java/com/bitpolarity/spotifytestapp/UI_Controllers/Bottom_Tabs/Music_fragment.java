package com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitpolarity.spotifytestapp.R;


public class Music_fragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_posts, container, false);

        // TextView tv = (TextView) v.findViewById(R.id.t1);
        //  tv.setText(getArguments().getString("msg"));

        return v;
    }


}