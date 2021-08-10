package com.bitpolarity.spotifytestapp.Adapters.ChatsAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bitpolarity.spotifytestapp.DB_Handler;
import com.bitpolarity.spotifytestapp.GetterSetterModels.ChatListModel;
import com.bitpolarity.spotifytestapp.R;
import com.bitpolarity.spotifytestapp.databinding.ChatMsgItemIncomingBinding;
import com.bitpolarity.spotifytestapp.databinding.ChatMsgItemOutgoingBinding;

import java.util.ArrayList;

public class MultiViewChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ChatListModel> list;

    public MultiViewChatAdapter(ArrayList<ChatListModel> list){
        this.list = list;
    }


    @Override
    public int getItemCount() {
        return 0;
    }


     class ViewHolderIncoming extends RecyclerView.ViewHolder {

         ChatMsgItemIncomingBinding binding_incoming;

        public ViewHolderIncoming(ChatMsgItemIncomingBinding binding) {
             super(binding.getRoot());
         this.binding_incoming = binding;
        }


     }

     class ViewHolderOutgoing extends RecyclerView.ViewHolder{

      ChatMsgItemOutgoingBinding binding_outgoing;

         public ViewHolderOutgoing(ChatMsgItemOutgoingBinding binding) {
             super(binding.getRoot());

             this.binding_outgoing = binding;
         }

     }


    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return position % 2 * 2;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       if (viewType == 0) {
           View view_incoming = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_msg_item_incoming, parent, false);
           return new ViewHolderIncoming(ChatMsgItemIncomingBinding.bind(view_incoming));
       }else{
          View view_outgoing = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_msg_item_outgoing,parent,false);
          return new ViewHolderOutgoing(ChatMsgItemOutgoingBinding.bind(view_outgoing));

       }
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        ViewHolderIncoming holderin = (ViewHolderIncoming) holder;
        ViewHolderOutgoing holderout = (ViewHolderOutgoing) holder;



        if(list.get(position).getSenderName().equals(DB_Handler.getUsername())){

            holderin.binding_incoming.textMessage.setText(list.get(position).getMessage());
            holderin.binding_incoming.usernameChatroom.setText(list.get(position).getSenderName());

        }

        else{
            holderout.binding_outgoing.textMessage.setText(list.get(position).getMessage());
        }

//        switch (holder.getItemViewType()) {
//            case 0:
//                ViewHolderIncoming viewHolder0 = (ViewHolderIncoming) holder;
//
//                break;
//
//            case 2:
//               ViewHolderOutgoing viewHolder2 = (ViewHolderOutgoing) holder;
//
//                break;
//        }
    }

}

