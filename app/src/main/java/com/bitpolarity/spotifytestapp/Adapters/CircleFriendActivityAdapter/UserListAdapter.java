package com.bitpolarity.spotifytestapp.Adapters.CircleFriendActivityAdapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bitpolarity.spotifytestapp.GetterSetterModels.UserListModel;
import com.bitpolarity.spotifytestapp.R;
import com.bitpolarity.spotifytestapp.databinding.CircleFriendActivityListItemBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {


    private final List<UserListModel> userModelList;
    private final ULEventListner mULEventlisnter;
    Animation animation;
    CircleFriendActivityListItemBinding binding;



    public UserListAdapter(List<UserListModel> categoryModelList, ULEventListner mULEventListner) {

        this.userModelList = categoryModelList;
        this.mULEventlisnter = mULEventListner;
    }


    @NotNull
    @Override

    public UserListAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.circle_friend_activity_list_item,parent,false);
        return new ViewHolder(CircleFriendActivityListItemBinding.bind(view), mULEventlisnter);

    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.ViewHolder holder, int position) {

        UserListModel link = userModelList.get(position); //Making a link to save multiple calls

        ////////////Setting Values /////////////////////////////////////////////


        holder.binding.username.setText(link.getUsername());
        holder.binding.songname.setText(link.getSongDetail());
        holder.binding.onlineStatus.setImageResource(link.getImageid());
        holder.binding.lastActive.setText(link.getDatetime());

        // Poster /////////////////////////////////////////////////////////////////

        Glide.with(userModelList.get(position).getContext())
                .load(link.getPoster())
                .apply(new RequestOptions().override(80, 80))
                .into(holder.binding.artwork);


        Glide.with(userModelList.get(position).getContext())
                .load("https://picsum.photos/100/100")
                .apply(new RequestOptions().override(50, 50))
                .into(holder.binding.profileImage);

        //"https://i.scdn.co/image/ab6775700000ee855ffdafb1d7fb1eb34622f04f"


        // Equilizer///////////////////////////////////////////////////////////

//        // Setting Playing and Paused TVs and making equilizer visible and gone ////////////////////////////////////////////
//
        if (link.getIsPlaying().equals("Playing")) {
            holder.binding.isPlayingg.setText(link.getIsPlaying());
           holder.binding.artwork.startAnimation(AnimationUtils.loadAnimation(link.getContext(), R.anim.song_rotate));
        }
        else
        {
            holder.binding.isPlayingg.setTextColor(Color.parseColor("#E53935"));
        }

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    }




    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {



        CircleFriendActivityListItemBinding binding;
        ImageView play_trackbtn;
        ULEventListner ulEventListner;

        public ViewHolder(@NonNull CircleFriendActivityListItemBinding b, ULEventListner ulEventListner) {

            super(b.getRoot());
            binding = b;
            this.ulEventListner = ulEventListner;
            //play_trackbtn = binding.playTrack;
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