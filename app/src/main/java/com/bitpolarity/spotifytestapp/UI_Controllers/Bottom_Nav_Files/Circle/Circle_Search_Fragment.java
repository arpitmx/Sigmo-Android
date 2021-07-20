package com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Nav_Files.Circle;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitpolarity.spotifytestapp.R;


public class Circle_Search_Fragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_circle_search, container, false);

        // TextView tv = (TextView) v.findViewById(R.id.t1);
        //  tv.setText(getArguments().getString("msg"));

        return v;
    }

    public static Circle_Search_Fragment newInstance(String text) {

        Circle_Search_Fragment f = new Circle_Search_Fragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}