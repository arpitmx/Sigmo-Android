package com.bitpolarity.spotifytestapp;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class CustomAdapter extends ArrayAdapter {
    private String[] countryNames;
    private String[] poster;
    private Integer[] imageid;
    private String[] SongDetail;
    private Activity context;

    public CustomAdapter(Activity context, String[] countryNames ,String[] poster, Integer[] statusid,String[] SongDetail) {
        super(context, R.layout.list_item, countryNames);
        this.context = context;
        this.countryNames = countryNames;
        this.poster = poster;
        this.imageid = statusid;
        this.SongDetail = SongDetail;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if(convertView==null)
            row = inflater.inflate(R.layout.list_item, null, true);
        TextView textViewCountry = (TextView) row.findViewById(R.id.username);
        ImageView imageFlag = (ImageView) row.findViewById(R.id.icon);
        ImageView posterr = (ImageView) row.findViewById(R.id.artwork);
        TextView songName = row.findViewById(R.id.songname);

        textViewCountry.setText(countryNames[position]);
        imageFlag.setImageResource(imageid[position]);
        Picasso.with(context)
                .load(poster[position])
                .resize(50, 50) // here you resize your image to whatever width and height you like
                .into(posterr);

       // posterr.setImageBitmap();
        songName.setText(SongDetail[position]);
        return  row;
    }
}