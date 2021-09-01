package com.bitpolarity.spotifytestapp.Adapters.RoomsListAdapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.bitpolarity.spotifytestapp.GetterSetterModels.RoomsListModel;
import com.bitpolarity.spotifytestapp.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class RoomsListAdapter extends RecyclerView.Adapter<RoomsListAdapter.ViewHolder> {


    private final List<RoomsListModel> userModelList;
    private final ULEventListner_Room mULEventlisnter;
    static final float MINIMUM = 20;
    int lastPosition = -1;
    Context context;

    public RoomsListAdapter(List<RoomsListModel> categoryModelList, ULEventListner_Room mULEventListner, Context context) {

        this.userModelList = categoryModelList;
        this.mULEventlisnter =mULEventListner;
        this.context = context;
    }

    @Override
    public RoomsListAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.public_rooms_item,parent,false);
        return new ViewHolder(view, mULEventlisnter);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        RoomsListModel link = userModelList.get(position); //Making a link to save multiple calls

        ////////////Setting Values /////////////////////////////////////////////

        holder.mRoomName.setText(link.getmRoomName());
        holder.mHostName.setText("Hosted by "+link.getmHostName());


        Glide.with(context)
                .load(link.getmUrl())
                .apply(new RequestOptions().override(80,80))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                       holder.progressBar.setVisibility(View.GONE);
                        holder.room_display_item.setAnimation(AnimationUtils.loadAnimation(context,R.anim.pop_in));
                        holder.room_display_item.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .into(holder.room_display_item);

//
//        Glide.with(context)
//                .load(link.getmUrl())
//                .apply(new RequestOptions().override(80, 80))
//                .into(holder.room_display_item);


        setAnimation(holder.itemView,position);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);


    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mRoomName, mHostName , mTime;
        CircleImageView room_display_item;
        ULEventListner_Room ulEventListner;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView, ULEventListner_Room ulEventListner) {
            super(itemView);

            this.ulEventListner = ulEventListner;
            mRoomName = (TextView) itemView.findViewById(R.id.mRoomName);
            mHostName = (TextView) itemView.findViewById(R.id.mHostName);
            room_display_item = itemView.findViewById(R.id.room_display_item);
            progressBar  = itemView.findViewById(R.id.progress_room_item);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ulEventListner.onClick(getAbsoluteAdapterPosition());
        }
    }

    public interface ULEventListner_Room{
        void onClick(int position);
    }


    public abstract static class MyRecyclerScroll extends RecyclerView.OnScrollListener {

        int scrollDist = 0;
        boolean isVisible = true;



        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);


            if (isVisible && scrollDist > MINIMUM) {
                hide();
                scrollDist = 0;
                isVisible = false;
            }
            else if (!isVisible && scrollDist < -MINIMUM) {
                show();
                scrollDist = 0;
                isVisible = true;
            }

            if ((isVisible && dy > 0) || (!isVisible && dy < 0)) {
                scrollDist += dy;
            }

        }

        public abstract void show();
        public abstract void hide();
    }




        private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), R.anim.fade_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

}