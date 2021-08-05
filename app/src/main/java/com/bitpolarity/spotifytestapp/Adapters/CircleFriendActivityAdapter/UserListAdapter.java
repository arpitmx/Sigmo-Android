package com.bitpolarity.spotifytestapp.Adapters.CircleFriendActivityAdapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.myViewHolder> {

    private final List<UserListModel> userModelList;
    private final ULEventListner mULEventlisnter;
    public UserListModel link;
    public Boolean playing = false;


    public UserListAdapter(List<UserListModel> categoryModelList, ULEventListner mULEventListner) {
        this.userModelList = categoryModelList;
        this.mULEventlisnter = mULEventListner;

    }

    @Override
    public void onViewAttachedToWindow(@NonNull myViewHolder holder) {
        super.onViewAttachedToWindow(holder);


    }

    @NotNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.circle_friend_activity_list_item, parent, false);
        return new myViewHolder(CircleFriendActivityListItemBinding.bind(view), mULEventlisnter);

    }


    public static class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {



        CircleFriendActivityListItemBinding binding;
        ImageView play_trackbtn;
        ULEventListner ulEventListner;

        public myViewHolder(@NonNull CircleFriendActivityListItemBinding b, ULEventListner ulEventListner) {

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



    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        link = userModelList.get(position); //Making a link to save multiple calls

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



        if (link.getIsPlaying().equals("Playing")) {
            Log.d("ISPLAYING ADAPTER", "onViewAttachedToWindow: "+ link.getIsPlaying());
            holder.binding.isPlayingg.setText("Playing");
            holder.binding.isPlayingg.setTextColor(Color.parseColor("#69DB22"));
            startAnim(holder.binding.artwork);
            playing = true;
        } else  {
            holder.binding.isPlayingg.setText("Paused");
            holder.binding.isPlayingg.setTextColor(Color.parseColor("#E53935"));
            playing = false;
        }


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    }


    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public interface ULEventListner{
        void onClick(int position);
    }



    void startAnim(CircleImageView poster){
        poster.startAnimation(AnimationUtils.loadAnimation(link.getContext(), R.anim.song_rotate));

    }

}

