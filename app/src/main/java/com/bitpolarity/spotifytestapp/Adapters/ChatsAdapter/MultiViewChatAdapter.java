package com.bitpolarity.spotifytestapp.Adapters.ChatsAdapter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bitpolarity.spotifytestapp.GetterSetterModels.ChatListModel_Multi;
import com.bitpolarity.spotifytestapp.R;
import com.bitpolarity.spotifytestapp.databinding.ChatMsgItemIncomingBinding;
import com.bitpolarity.spotifytestapp.databinding.ChatMsgItemIncomingSameUsrBinding;
import com.bitpolarity.spotifytestapp.databinding.ChatMsgItemOutgoingBinding;
import com.bitpolarity.spotifytestapp.databinding.ChatMsgItemUserJoinedBinding;

import java.util.ArrayList;

public class MultiViewChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ChatListModel_Multi> list;
    public static final int MESSAGE_TYPE_IN = 1;
    public static final int MESSAGE_TYPE_OUT = 2;
    public static final int MESSAGE_TYPE_OUT_SAME = 3;
    public static final int MESSAGE_TYPE_JOING_LEAVING = 4;
    StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
    ClickListner mClicklistner;




    public MultiViewChatAdapter(ArrayList<ChatListModel_Multi> list, ClickListner mClicklistner){
        this.list = list;
        this.mClicklistner = mClicklistner;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    ///////////////////////////////////////// INCOMING /////////////////////////////////////////////////////////




     class ViewHolderIncoming extends RecyclerView.ViewHolder implements View.OnClickListener{

         ChatMsgItemIncomingBinding binding_incoming;
         ClickListner clickListner;

         private boolean isRunning= false;
         private int resetInTime =500;
         private int counter=0;

        public ViewHolderIncoming(ChatMsgItemIncomingBinding binding , ClickListner clickListner) {
             super(binding.getRoot());
             this.binding_incoming = binding;
             this.clickListner = clickListner;

             itemView.setOnClickListener(this);
        }

         void bind(int position) {
            ChatListModel_Multi  messageModel = list.get(position);
             binding_incoming.usernameChatroom.setText(messageModel.getSenderName());
             binding_incoming.textMessage.setText(messageModel.getMessage());
             binding_incoming.timeTxt.setText(messageModel.getTime());

         }

         @Override
         public void onClick(View view) {

             if(isRunning)
             {
                 if(counter==1) //<-- makes sure that the callback is triggered on double click
                     clickListner.onClick(getAbsoluteAdapterPosition());
             }

             counter++;

             if(!isRunning)
             {
                 isRunning=true;
                 new Thread(() -> {
                     try {
                         Thread.sleep(resetInTime);
                         isRunning = false;
                         counter=0;
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
                 }).start();
             }
         }
     }




    ///////////////////////////////////////// INCOMING /////////////////////////////////////////////////////////











    ///////////////////////////////////////// JOINING/LEAVING ////////////////////////////////////////////////


    class ViewHolderJoining_Leaving extends RecyclerView.ViewHolder {

        ChatMsgItemUserJoinedBinding binding_joining;

        public ViewHolderJoining_Leaving(ChatMsgItemUserJoinedBinding binding) {
            super(binding.getRoot());
            this.binding_joining = binding;
        }

        void bind(int position) {
            ChatListModel_Multi  messageModel = list.get(position);
            binding_joining.timeTxt.setText(messageModel.getTime());

            if(messageModel.getMessage().equals("joined")){
            binding_joining.notificationTxt.setText(messageModel.getSenderName()+" "+messageModel.getMessage()+" the room! \uD83C\uDF7A " );
         //   binding_joining.linMain.setBackgroundResource(R.drawable.chatitembg_joined_room);
          //      binding_joining.timeTxt.setTextColor(Color.parseColor("#F390CAF9"));



            }else{
                binding_joining.notificationTxt.setText(messageModel.getSenderName()+" "+messageModel.getMessage()+" the room! \uD83D\uDEB6 " );
        //        binding_joining.linMain.setBackgroundResource(R.drawable.chatitembg_leaved_room);
          //      binding_joining.timeTxt.setTextColor(Color.parseColor("#EF9A9A"));



            }

        }
    }

    ///////////////////////////////////////// JOINING/LEAVING ////////////////////////////////////////////////












    ///////////////////////////////////////// OUTGOING ////////////////////////////////////////////////


    class ViewHolderOutgoing extends RecyclerView.ViewHolder implements View.OnClickListener{

      ChatMsgItemOutgoingBinding binding_outgoing;
         ClickListner clickListner;
        private boolean isRunning= false;
        private int resetInTime =500;
        private int counter=0;

         public ViewHolderOutgoing(ChatMsgItemOutgoingBinding binding, ClickListner clickListner) {
             super(binding.getRoot());

             this.binding_outgoing = binding;
             this.clickListner = clickListner;

             itemView.setOnClickListener(this);

         }

         void bind(int position) {
             ChatListModel_Multi  messageModel = list.get(position);

             String html = getHTMLString(messageModel.getMessage());

             if(html!=null) {
                 binding_outgoing.textMessage.setText(Html.fromHtml(html));
             }else{
                 binding_outgoing.textMessage.setText(messageModel.getMessage());
             }


             binding_outgoing.timeTxt.setText(messageModel.getTime());

         }


        @Override
        public void onClick(View view) {

            if(isRunning)
            {
                if(counter==1) //<-- makes sure that the callback is triggered on double click
                    clickListner.onClick(getAbsoluteAdapterPosition());
            }

            counter++;

            if(!isRunning)
            {
                isRunning=true;
                new Thread(() -> {
                    try {
                        Thread.sleep(resetInTime);
                        isRunning = false;
                        counter=0;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }
     }

    ///////////////////////////////////////// OUTGOING  ////////////////////////////////////////////////












    ///////////////////////////////////////// SAME USER INCOMING //////////////////////////////////////////////



     class ViewHolderIncoming_SameUsr extends RecyclerView.ViewHolder implements View.OnClickListener{

        ChatMsgItemIncomingSameUsrBinding binding_outgoing_sameusr;
        ClickListner clickListner;
         private boolean isRunning= false;
         private int resetInTime =500;
         private int counter=0;

        public ViewHolderIncoming_SameUsr(ChatMsgItemIncomingSameUsrBinding binding, ClickListner clickListner) {
            super(binding.getRoot());

            this.binding_outgoing_sameusr = binding;
            this.clickListner = clickListner;

            itemView.setOnClickListener(this);

        }

        void bind(int position) {
            ChatListModel_Multi  messageModel = list.get(position);
            binding_outgoing_sameusr.textMessage.setText(messageModel.getMessage());
            binding_outgoing_sameusr.timeTxt.setText(messageModel.getTime());

        }

         public void onClick(View view) {

             if(isRunning)
             {
                 if(counter==1) //<-- makes sure that the callback is triggered on double click
                     clickListner.onClick(getAbsoluteAdapterPosition());
             }

             counter++;

             if(!isRunning)
             {
                 isRunning=true;
                 new Thread(() -> {
                     try {
                         Thread.sleep(resetInTime);
                         isRunning = false;
                         counter=0;
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
                 }).start();
             }
         }
    }



    ///////////////////////////////////////// SAME USER INCOMING //////////////////////////////////////////////










    @Override
    public int getItemViewType(int position) {
        return list.get(position).getMessageType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       if (viewType == 1) {

           View view_incoming = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_msg_item_incoming, parent, false);
           return new ViewHolderIncoming(ChatMsgItemIncomingBinding.bind(view_incoming), mClicklistner);

       }else if(viewType == 2) {

          View view_outgoing = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_msg_item_outgoing,parent,false);
          return new ViewHolderOutgoing(ChatMsgItemOutgoingBinding.bind(view_outgoing),mClicklistner);

       }
       else if (viewType == 3){
           View view_outgoing_same_usr = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_msg_item_incoming_same_usr,parent,false);
           return new ViewHolderIncoming_SameUsr(ChatMsgItemIncomingSameUsrBinding.bind(view_outgoing_same_usr),mClicklistner);
       }
       else {
           View view_joining = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_msg_item_user_joined,parent,false);
           return new ViewHolderJoining_Leaving(ChatMsgItemUserJoinedBinding.bind(view_joining));
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
        else if(list.get(position).getMessageType() == MESSAGE_TYPE_JOING_LEAVING){
            ((ViewHolderJoining_Leaving) holder).bind(position);

        }


    }

//    public ArrayList<Integer> getBoldsIndexes(String s){
//
//        ArrayList<Integer> boldList = new ArrayList<>();
//        String[] arr = s.split("");
//
//        if (s.contains("*")) {
//            if(isBalanced(arr)){
//                for (int i = 0; i < arr.length; i++) {
//                    if (arr[i].equals("*")) {
//                        boldList.add(i+1);
//                        for (int j = i + 1; j < arr.length; j++) {
//                            if (arr[j].equals("*")) {
//                                boldList.add(j-1);
//                                i = j + 1;
//                                break;
//                            }}}}
//
//                Log.d("MultiAdapter", "getBoldsIndexes: "+boldList);
//
//                return boldList;
//                }else { return null; }
//            }
//        else{
//            return null;
//        }
//        }
//



   String getHTMLString(String s){

        String[] arr = s.split("");
        StringBuilder stringBuilder = new StringBuilder();

        if (s.contains("*")) {
            if(isBalanced(arr)){
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i].equals("*")) {
                        arr[i] = "<strong>";
                        for (int j = i + 1; j < arr.length; j++) {
                            if (arr[j].equals("*")) {
                                arr[j] = "</strong>";
                                i = j + 1;
                                break; }
                        }
                    }

                }

                for (String x : arr) {
                    stringBuilder.append(x);
                }

                return stringBuilder.toString();

            }else{
                return null;
            }


        } else {
            return null;        }

    }



    private boolean isBalanced(String[] arr){
        int count = 0;
        for (String i : arr){
            if(i.equals("*")) count++;
        }
        return count % 2 == 0;
    }





    public interface ClickListner{
        void onClick(int position);
    }

}

