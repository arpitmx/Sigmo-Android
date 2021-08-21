package com.bitpolarity.spotifytestapp.Adapters.ChatsAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bitpolarity.spotifytestapp.GetterSetterModels.ChatListModel_Multi;
import com.bitpolarity.spotifytestapp.R;
import com.bitpolarity.spotifytestapp.databinding.ChatMsgItemIncomingBinding;
import com.bitpolarity.spotifytestapp.databinding.ChatMsgItemIncomingSameUsrBinding;
import com.bitpolarity.spotifytestapp.databinding.ChatMsgItemOutgoingBinding;

import java.util.ArrayList;

public class MultiViewChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ChatListModel_Multi> list;
    public static final int MESSAGE_TYPE_IN = 1;
    public static final int MESSAGE_TYPE_OUT = 2;
    public static final int MESSAGE_TYPE_OUT_SAME = 3;



    public MultiViewChatAdapter(ArrayList<ChatListModel_Multi> list){
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


     class ViewHolderIncoming extends RecyclerView.ViewHolder {

         ChatMsgItemIncomingBinding binding_incoming;

        public ViewHolderIncoming(ChatMsgItemIncomingBinding binding) {
             super(binding.getRoot());
         this.binding_incoming = binding;
        }

         void bind(int position) {
            ChatListModel_Multi  messageModel = list.get(position);
             binding_incoming.usernameChatroom.setText(messageModel.getSenderName());
             binding_incoming.textMessage.setText(messageModel.getMessage());

         }
     }

     class ViewHolderOutgoing extends RecyclerView.ViewHolder{

      ChatMsgItemOutgoingBinding binding_outgoing;

         public ViewHolderOutgoing(ChatMsgItemOutgoingBinding binding) {
             super(binding.getRoot());

             this.binding_outgoing = binding;
         }

         void bind(int position) {
             ChatListModel_Multi  messageModel = list.get(position);
             binding_outgoing.textMessage.setText(messageModel.getMessage());
         }
     }


    class ViewHolderIncoming_SameUsr extends RecyclerView.ViewHolder{

        ChatMsgItemIncomingSameUsrBinding binding_outgoing_sameusr;

        public ViewHolderIncoming_SameUsr(ChatMsgItemIncomingSameUsrBinding binding) {
            super(binding.getRoot());

            this.binding_outgoing_sameusr = binding;
        }

        void bind(int position) {
            ChatListModel_Multi  messageModel = list.get(position);
            binding_outgoing_sameusr.textMessage.setText(messageModel.getMessage());
        }
    }

     @Override
    public int getItemViewType(int position) {
        return list.get(position).getMessageType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       if (viewType == 1) {

           View view_incoming = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_msg_item_incoming, parent, false);
           return new ViewHolderIncoming(ChatMsgItemIncomingBinding.bind(view_incoming));

       }else if(viewType == 2) {

          View view_outgoing = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_msg_item_outgoing,parent,false);
          return new ViewHolderOutgoing(ChatMsgItemOutgoingBinding.bind(view_outgoing));

       }
       else{
           View view_outgoing_same_usr = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_msg_item_incoming_same_usr,parent,false);
           return new ViewHolderIncoming_SameUsr(ChatMsgItemIncomingSameUsrBinding.bind(view_outgoing_same_usr));
       }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (list.get(position).getMessageType() == MESSAGE_TYPE_IN) {
            ((ViewHolderIncoming) holder).bind(position);

        } else if (list.get(position).getMessageType() == MESSAGE_TYPE_OUT) {
            ((ViewHolderOutgoing) holder).bind(position);
        }
        else if(list.get(position).getMessageType() == MESSAGE_TYPE_OUT_SAME) {
            ((ViewHolderIncoming_SameUsr) holder).bind(position);
        }


    }

}

