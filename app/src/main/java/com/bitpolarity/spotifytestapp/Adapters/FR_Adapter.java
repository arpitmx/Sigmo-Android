package com.bitpolarity.spotifytestapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bitpolarity.spotifytestapp.GetterSetterModels.FR_Model;
import com.bitpolarity.spotifytestapp.R;

import java.util.ArrayList;

public class FR_Adapter extends RecyclerView.Adapter<FR_Adapter.MyViewHolder> {
    public ArrayList<FR_Model> childModelArrayList;
    Context cxt;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView heroImage;
        public TextView movieName;

        public MyViewHolder(View itemView) {
            super(itemView);
            heroImage = itemView.findViewById(R.id.profile_pic);
            movieName = itemView.findViewById(R.id.profile_n);
        }
    }

    public FR_Adapter(ArrayList<FR_Model> arrayList, Context mContext) {
        this.cxt = mContext;
        this.childModelArrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_recommendation_itemview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        FR_Model currentItem = childModelArrayList.get(position);
        holder.heroImage.setImageResource(R.drawable.poster);
        holder.movieName.setText("Teena");

    }

    @Override
    public int getItemCount() {
        return childModelArrayList.size();
    }
}