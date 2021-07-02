package com.bitpolarity.spotifytestapp;


import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;


public class CustomAdapter extends ArrayAdapter {
    private String[] countryNames;
    private String[] poster;
    private Integer[] imageid;
    private String[] SongDetail;
    private Activity context;
    private String[] isPlaying;
    Animation animation;

    public CustomAdapter(Activity context,String[] isPlaying, String[] countryNames ,String[] poster, Integer[] statusid,String[] SongDetail) {
        super(context, R.layout.list_item, countryNames);
        this.context = context;
        this.countryNames = countryNames;
        this.poster = poster;
        this.imageid = statusid;
        this.SongDetail = SongDetail;
        this.isPlaying = isPlaying;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       animation = AnimationUtils.loadAnimation(getContext(),R.anim.slidein_left_to_right);
        View row=convertView;
        LayoutInflater inflater = context.getLayoutInflater();



        row = inflater.inflate(R.layout.list_item, null, true);

        TextView textViewCountry = (TextView) row.findViewById(R.id.username);
        ImageView imageFlag = (ImageView) row.findViewById(R.id.online_status);
        ImageView posterr = (ImageView) row.findViewById(R.id.artwork);
        TextView songName = row.findViewById(R.id.songname);
        TextView isPlaying_TV = row.findViewById(R.id.isPlayingg);
       ImageView view = row.findViewById(R.id.view2);

//        Glide.with(context)
//                .load(R.drawable.eq)
//                //,https://i.gifer.com/KNGq.gif
//                .into((ImageView) view);
        Glide.with(context)
                .load(R.drawable.eq)
                //,https://i.gifer.com/KNGq.gif
                .into((ImageView) view);




        if(convertView==null)


        textViewCountry.setText(countryNames[position]);

        imageFlag.setImageResource(imageid[position]);


        if (isPlaying[position].equals("Playing")) {
            isPlaying_TV.setText(isPlaying[position]);
            view.setVisibility(View.VISIBLE);
        }else{
            isPlaying_TV.setText(isPlaying[position]);
            isPlaying_TV.setTextColor(Color.parseColor("#E53935"));
            view.setVisibility(View.GONE);
        }

        Picasso.with(context)
                .load(poster[position])
                .resize(120, 120) // here you resize your image to whatever width and height you like
                .into(posterr);


        songName.setText(SongDetail[position]);
        return  row;
    }
}