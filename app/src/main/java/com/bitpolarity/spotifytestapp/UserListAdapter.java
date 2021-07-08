package com.bitpolarity.spotifytestapp;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {


   private final List<UserListModel> userModelList;
   private final ULEventListner mULEventlisnter;


    public UserListAdapter(List<UserListModel> categoryModelList, ULEventListner mULEventListner) {

        this.userModelList = categoryModelList;
        this.mULEventlisnter = mULEventListner;


    }

    @NotNull
    @Override

    public UserListAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view,mULEventlisnter);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.ViewHolder holder, int position) {

        UserListModel link = userModelList.get(position); //Making a link to save multiple calls

        String userName = link.getUsername();
        Log.d("Adapter LOG", "onBindViewHolder: "+ userName);
        String songName = link.getSongDetail();
        Log.d("Adapter LOG", "onBindViewHolder: "+ songName);
        Integer online_status = link.getImageid();
        Log.d("Adapter LOG", "onBindViewHolder: "+ online_status);


        ////////////Setting Values /////////////////////////////////////////////

        holder.textViewUsername.setText(userName);
        holder.songName.setText(songName);
        holder.online_status.setImageResource(online_status);
        holder.last_acive.setText(link.getDatetime());



        // Poster /////////////////////////////////////////////////////////////////


        Glide.with(userModelList.get(position).getContext())
                .load(link.getPoster())
                .apply(new RequestOptions().override(180, 180))
               .into(holder.posterr);



        // Equilizer///////////////////////////////////////////////////////////

        Glide.with(userModelList.get(position).getContext())
                .load(R.drawable.eq)
                .apply(new RequestOptions().override(110, 110))
                .into(holder.equilizer_iv);


        // Setting Playing and Paused TVs and making equilizer visible and gone ////////////////////////////////////////////

        if (link.getIsPlaying().equals("Playing")) {
            holder.isPlayingTV.setText(link.getIsPlaying());
            holder.equilizer_iv.setVisibility(View.VISIBLE);
            holder.posterr.startAnimation(AnimationUtils.loadAnimation(link.getContext(), R.anim.song_rotate));
        }
        else
            {
                holder.isPlayingTV.setText("Paused");
                holder.isPlayingTV.setTextColor(Color.parseColor("#E53935"));
                holder.equilizer_iv.setVisibility(View.GONE);
        }

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////



 //       ImageView posterr = (ImageView) row.findViewById(R.id.artwork);
//        TextView songName = row.findViewById(R.id.songname);
//        TextView isPlaying_TV = row.findViewById(R.id.isPlayingg);
//       ImageView view = row.findViewById(R.id.view2);



    }


    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewUsername ;
       ImageView online_status;
       CircleImageView posterr;
       TextView songName;
       TextView isPlayingTV;
       ImageView equilizer_iv;
       TextView last_acive;

       ULEventListner ulEventListner;

        public ViewHolder(@NonNull View itemView, ULEventListner ulEventListner) {
            super(itemView);

            this.ulEventListner = ulEventListner;
            textViewUsername = (TextView) itemView.findViewById(R.id.username);
            online_status = (ImageView) itemView.findViewById(R.id.online_status);
            posterr =  itemView.findViewById(R.id.artwork);
            songName =(TextView) itemView.findViewById(R.id.songname);
            isPlayingTV = (TextView) itemView.findViewById(R.id.isPlayingg);
            equilizer_iv =(ImageView) itemView.findViewById(R.id.view2);
            last_acive = (TextView) itemView.findViewById(R.id.last_active);
            itemView.setOnClickListener(this);





        }


        @Override
        public void onClick(View view) {
            ulEventListner.onClick(getAdapterPosition());
        }
    }

    interface ULEventListner{
        void onClick(int position);
    }



//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//       animation = AnimationUtils.loadAnimation(getContext(),R.anim.slidein_left_to_right);
//        View row=convertView;
//        LayoutInflater inflater = context.getLayoutInflater();
//
//
//
//        row = inflater.inflate(R.layout.list_item, null, true);
//
//        TextView textViewCountry = (TextView) row.findViewById(R.id.username);
//        ImageView imageFlag = (ImageView) row.findViewById(R.id.online_status);
//        ImageView posterr = (ImageView) row.findViewById(R.id.artwork);
//        TextView songName = row.findViewById(R.id.songname);
//        TextView isPlaying_TV = row.findViewById(R.id.isPlayingg);
//       ImageView view = row.findViewById(R.id.view2);
//
////        Glide.with(context)
////                .load(R.drawable.eq)
////                //,https://i.gifer.com/KNGq.gif
////                .into((ImageView) view);
//        Glide.with(context)
//                .load(R.drawable.eq)
//                //,https://i.gifer.com/KNGq.gif
//                .into((ImageView) view);
//
//
//
//
//        if(convertView==null)
//
//
//        textViewCountry.setText(countryNames[position]);
//
//        imageFlag.setImageResource(imageid[position]);
//
//
//        if (isPlaying[position].equals("Playing")) {
//            isPlaying_TV.setText(isPlaying[position]);
//            view.setVisibility(View.VISIBLE);
//        }else{
//            isPlaying_TV.setText(isPlaying[position]);
//            isPlaying_TV.setTextColor(Color.parseColor("#E53935"));
//            view.setVisibility(View.GONE);
//        }
//
//        Glide.with(context)
//                .load(poster[position])
//                .placeholder(R.drawable.eq)// here you resize your image to whatever width and height you like
//                .into(posterr);
//
//
//
//
//        songName.setText(SongDetail[position]);
//        return  row;
//    }
}