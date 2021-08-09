package com.bitpolarity.spotifytestapp.Adapters.RoomsListAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bitpolarity.spotifytestapp.GetterSetterModels.RoomsListModel;
import com.bitpolarity.spotifytestapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class RoomsListAdapter extends RecyclerView.Adapter<RoomsListAdapter.ViewHolder> {


    private final List<RoomsListModel> userModelList;
    private final ULEventListner_Room mULEventlisnter;
    Animation animation;
    static final float MINIMUM = 20;
    int lastPosition = -1;



    public RoomsListAdapter(List<RoomsListModel> categoryModelList, ULEventListner_Room mULEventListner) {

        this.userModelList = categoryModelList;
        this.mULEventlisnter =mULEventListner;
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
        holder.mHostName.setText(link.getmHostName());
        setAnimation(holder.itemView,position);




        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView mRoomName, mHostName ;
        ULEventListner_Room ulEventListner;

        public ViewHolder(@NonNull View itemView, ULEventListner_Room ulEventListner) {
            super(itemView);


            this.ulEventListner = ulEventListner;
            mRoomName = (TextView) itemView.findViewById(R.id.mRoomName);
            mHostName = (TextView) itemView.findViewById(R.id.mHostName);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            ulEventListner.onClick(getAdapterPosition());
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