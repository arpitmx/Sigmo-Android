package com.bitpolarity.spotifytestapp.Adapters;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.bitpolarity.spotifytestapp.GetterSetterModels.UserListModel;
import com.bitpolarity.spotifytestapp.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.jetbrains.annotations.NotNull;

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
        return new ViewHolder(view, mULEventlisnter);
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
        holder.play_trackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(link.context, "Play Track clicked", Toast.LENGTH_SHORT).show();
            }
        });


        // Poster /////////////////////////////////////////////////////////////////

        Glide.with(userModelList.get(position).getContext())
                .load(link.getPoster())
                .apply(new RequestOptions().override(100, 100))
                .into(holder.posterr);


        Glide.with(userModelList.get(position).getContext())
                .load("https://i.scdn.co/image/ab6775700000ee855ffdafb1d7fb1eb34622f04f")
                .apply(new RequestOptions().override(50, 50))
                .into(holder.user_profile_dp);

        //"https://i.scdn.co/image/ab6775700000ee855ffdafb1d7fb1eb34622f04f"







        // Equilizer///////////////////////////////////////////////////////////

        Glide.with(userModelList.get(position).getContext())
                .load(R.drawable.eq)
                .apply(new RequestOptions().override(110, 110))
                .into(holder.equilizer_iv);


        // Setting Playing and Paused TVs and making equilizer visible and gone ////////////////////////////////////////////

        if (link.getIsPlaying().equals("Playing")) {
            holder.isPlayingTV.setText(link.getIsPlaying());
            holder.equilizer_iv.setVisibility(View.VISIBLE);
           // holder.posterr.startAnimation(AnimationUtils.loadAnimation(link.getContext(), R.anim.song_rotate));
        }
        else
        {
            holder.isPlayingTV.setText("Paused");
            holder.isPlayingTV.setTextColor(Color.parseColor("#E53935"));
            holder.equilizer_iv.setVisibility(View.GONE);
        }

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    }




    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView textViewUsername ;
        ImageView online_status;
        CircleImageView posterr;
        TextView songName;
        TextView isPlayingTV;
        ImageView equilizer_iv;
        TextView last_acive;
        ImageView user_profile_dp;
        ImageView play_trackbtn;


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
            user_profile_dp = itemView.findViewById(R.id.profile_image);
            play_trackbtn = itemView.findViewById(R.id.play_track);

            itemView.setOnClickListener(this);


        }


        @Override
        public void onClick(View view) {
            ulEventListner.onClick(getAdapterPosition());
        }
    }

    public interface ULEventListner{
        void onClick(int position);
    }



}