package com.bitpolarity.spotifytestapp.Adapters.ChatsAdapter;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bitpolarity.spotifytestapp.DB_Handler;
import com.bitpolarity.spotifytestapp.GetterSetterModels.ChatListModel;
import com.bitpolarity.spotifytestapp.R;
import com.bitpolarity.spotifytestapp.databinding.ChatMsgItemSendBinding;
import com.google.android.gms.common.data.DataHolder;

import java.util.ArrayList;


public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ChatsViewHolder> {

   ArrayList<ChatListModel> chatList;


    public ChatsAdapter(ArrayList<ChatListModel> chatList){
        this.chatList = chatList;
    }


    @NonNull
    @Override
    public ChatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_msg_item_send,parent,false);
        return new ChatsViewHolder(ChatMsgItemSendBinding.bind(view));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatsViewHolder holder, int position) {



//            if(chatList.get(position).getSenderName().equals(DB_Handler.getUsername())){
//                holder.binding.linearLayoutChat.setGravity(Gravity.END);
//                holder.binding.profieDisplayPictureChat.setVisibility(View.GONE);
//                //holder.binding.cardGchatMessageMe.setBackgroundColor(Color.parseColor("#0D82FF"));
//               // holder.binding.usernameChatroom.setVisibility(View.GONE);
//                holder.binding.usernameChatroom.setText(chatList.get(position).getSenderName());
//                holder.binding.textMessage.setText(chatList.get(position).getMessage());
//            }

            holder.binding.usernameChatroom.setText(chatList.get(position).getSenderName());
            holder.binding.textMessage.setText(chatList.get(position).getMessage());


    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }


    public static class ChatsViewHolder extends RecyclerView.ViewHolder {

            ChatMsgItemSendBinding binding;

        public ChatsViewHolder(ChatMsgItemSendBinding binding) {
            super(binding.getRoot());

            this.binding = binding;



        }
    }

}
