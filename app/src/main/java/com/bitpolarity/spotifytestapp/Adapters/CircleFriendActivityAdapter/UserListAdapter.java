package com.bitpolarity.spotifytestapp.Adapters.CircleFriendActivityAdapter;

import static com.bitpolarity.spotifytestapp.R.drawable.dark_four_side_borders_post_active;
import static com.bitpolarity.spotifytestapp.R.drawable.dark_four_side_borders_post_inactive;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

import com.bitpolarity.spotifytestapp.GetterSetterModels.UserListModel;
import com.bitpolarity.spotifytestapp.R;
import com.bitpolarity.spotifytestapp.UI_Controllers.MainHolder;
import com.bitpolarity.spotifytestapp.databinding.CircleFriendActivityListItemBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.myViewHolder> {

    private final List<UserListModel> userModelList;
    private final ULEventListner mULEventlisnter;
    public UserListModel link;
    public Boolean playing = false;
    private int lastPosition = -1;
    static final float MINIMUM = 25;
    Context context;

    public UserListAdapter(List<UserListModel> categoryModelList, ULEventListner mULEventListner, Context context) {
        this.userModelList = categoryModelList;
        this.mULEventlisnter = mULEventListner;
        this.context = context;

    }

    @Override
    public void onViewAttachedToWindow(@NonNull myViewHolder holder) {
        super.onViewAttachedToWindow(holder);

        //holder.binding.artwork.startAnimation(AnimationUtils.loadAnimation(link.getContext(), R.anim.song_rotate));
    }

    @NotNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.circle_friend_activity_list_item, parent, false);
        return new myViewHolder(CircleFriendActivityListItemBinding.bind(view), mULEventlisnter);

    }

    public static class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CircleFriendActivityListItemBinding binding;
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
            ulEventListner.onClick(getAbsoluteAdapterPosition());
        }
    }


    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        link = userModelList.get(position); //Making a link to save multiple calls

        ////////////Setting Values /////////////////////////////////////////////

        holder.binding.username.setText(link.getUsername());
        holder.binding.songname.setText("\uD83C\uDFB5 "+link.getSongDetail());
        holder.binding.artistName.setText("\uD83D\uDCBF "+link.getArtistName());
        holder.binding.lastActive.setText(link.getDatetime());

        // Poster /////////////////////////////////////////////////////////////////

        Glide.with(context)
                .load(link.getPoster())
                .apply(new RequestOptions().override(80, 80))
                .into(holder.binding.artwork);

       // setAnimation(holder.binding.artwork, position);

        Glide.with(context)
                .load("https://picsum.photos/100/100")
                .apply(new RequestOptions().override(50, 50))
                .into(holder.binding.profileImage);

        //"https://i.scdn.co/image/ab6775700000ee855ffdafb1d7fb1eb34622f04f"


        if(link.getStatus() == 1){
            holder.binding.getRoot().setBackgroundResource(dark_four_side_borders_post_active);
            holder.binding.onlineStatus.setImageResource(R.drawable.ongreen);

            if (link.getIsPlaying().equals("true")) {
            Log.d("ISPLAYING ADAPTER", "onViewAttachedToWindow: "+ link.getIsPlaying());
            holder.binding.isPlayingg.setText("\uD83C\uDFA7");
            startAnim(holder.binding.artwork);
            playing = true;

        } else  {
            holder.binding.isPlayingg.setText("⏸");
            holder.binding.isPlayingg.setTextColor(Color.parseColor("#E53935"));
            playing = false;
        }
        }else{
            //holder.binding.isPlayingg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
            holder.binding.onlineStatus.setImageResource(R.drawable.ored);
            holder.binding.isPlayingg.setText("\uD83D\uDCA4");
            holder.binding.getRoot().setBackgroundResource(dark_four_side_borders_post_inactive);

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
        poster.startAnimation(AnimationUtils.loadAnimation(context, R.anim.song_rotate));
    }

//    private void setAnimation(View viewToAnimate, int position)
//    {
//        // If the bound view wasn't previously displayed on screen, it's animated
//        if (position > lastPosition)
//        {
//            Animation animation = AnimationUtils.loadAnimation(link.getContext(), R.anim.rotate_fadein);
//            viewToAnimate.startAnimation(animation);
//            lastPosition = position;
//        }
//    }


    void setCircleItemBG(String url, LinearLayout view){


        // Bitmap bitmap = BitmapFactory.decodeResource(getResources(), drawableFromUrl("https://i.scdn.co/image/ab67616d00001e028155c99a241d4c57b2c3f88d"));

        Bitmap b  = getBitmapFromURL(url);
        Palette.from(b).maximumColorCount(12).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                // Get the "vibrant" color swatch based on the bitmap
                Palette.Swatch vibrant = palette.getVibrantSwatch();
                if (vibrant != null) {
                view.setBackgroundColor(vibrant.getRgb());

                }
            }
        });
    }

    public static Bitmap getBitmapFromURL(String src) {
        new Thread(() -> {

        }).start();
        try {
            URL url = new URL(src);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }






}

