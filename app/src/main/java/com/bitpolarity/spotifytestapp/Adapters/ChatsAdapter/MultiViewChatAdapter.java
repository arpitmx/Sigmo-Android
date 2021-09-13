package com.bitpolarity.spotifytestapp.Adapters.ChatsAdapter;

import android.graphics.Typeface;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bitpolarity.spotifytestapp.GetterSetterModels.MessageModelHolder;
import com.bitpolarity.spotifytestapp.R;
import com.bitpolarity.spotifytestapp.databinding.ChatMsgItemIncomingBinding;
import com.bitpolarity.spotifytestapp.databinding.ChatMsgItemIncomingSameUsrBinding;
import com.bitpolarity.spotifytestapp.databinding.ChatMsgItemOutgoingBinding;
import com.bitpolarity.spotifytestapp.databinding.ChatMsgItemUserJoinedBinding;
import com.bitpolarity.spotifytestapp.databinding.ChatMsgOutgoingReferenceItemOutgoingBinding;

import java.util.ArrayList;

public class MultiViewChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<MessageModelHolder> list;
    public static final int MESSAGE_TYPE_IN = 1;

    //////////////////////////////////////////////////////

    public static final int MESSAGE_TYPE_OUT = 2;
    public static final int MESSAGE_TYPE_OUT_SAME_USER_MIDDLE_ITEM = 6;
    public static final int MESSAGE_TYPE_OUT_SAME_USER_END_ITEM = 7;


    //////////////////////////////////////////////////////////////////

    public static final int MESSAGE_TYPE_IN_SAME = 3;
    public static final int MESSAGE_TYPE_JOING_LEAVING = 4;
    public static final int MESSAGE_TYPE_REFFERED_MSG = 5;

    int lastPosition = 500;
    StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
    ClickListner mClicklistner;


    public MultiViewChatAdapter() {
    }

    public void initiateAdapter(ClickListner mClicklistner) {
        this.mClicklistner = mClicklistner;
    }

    public void setModelList(ArrayList<MessageModelHolder> list) {
        this.list = list;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    //////////////////////////////////////////////////////////// VIEWHOLDERS /////////////////////////////////////////////////////////






    ///////////////////////////////////////// INCOMING  /////////////////////////////////////////////////////////


    class ViewHolderIncoming extends RecyclerView.ViewHolder implements View.OnClickListener {

        ChatMsgItemIncomingBinding binding_incoming;
        ClickListner clickListner;

        private boolean isRunning = false;
        private final int resetInTime = 500;
        private int counter = 0;

        public ViewHolderIncoming(ChatMsgItemIncomingBinding binding, ClickListner clickListner) {
            super(binding.getRoot());
            this.binding_incoming = binding;
            this.clickListner = clickListner;
            itemView.setOnClickListener(this);
        }

        void bind(int position) {
            MessageModelHolder messageModelHolder = list.get(position);
            binding_incoming.usernameChatroom.setText(messageModelHolder.getSenderName());
            binding_incoming.textMessage.setText(messageModelHolder.getMessage());
            binding_incoming.timeTxt.setText(messageModelHolder.getTime());

        }



        @Override
        public void onClick(View view) {

            if (isRunning) {
                if (counter == 1) //<-- makes sure that the callback is triggered on double click
                    clickListner.onClick(getAbsoluteAdapterPosition());

            }

            counter++;

            if (!isRunning) {
                isRunning = true;
                new Thread(() -> {
                    try {
                        Thread.sleep(resetInTime);
                        isRunning = false;
                        counter = 0;
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
            MessageModelHolder messageModelHolder = list.get(position);
            binding_joining.timeTxt.setText(messageModelHolder.getTime());

            if (messageModelHolder.getMessage().equals("joined")) {
                binding_joining.notificationTxt.setText(messageModelHolder.getSenderName() + " " + messageModelHolder.getMessage() + " the room! \uD83C\uDF7A ");
                //   binding_joining.linMain.setBackgroundResource(R.drawable.chatitembg_joined_room);
                //      binding_joining.timeTxt.setTextColor(Color.parseColor("#F390CAF9"));


            } else {
                binding_joining.notificationTxt.setText(messageModelHolder.getSenderName() + " " + messageModelHolder.getMessage() + " the room! \uD83D\uDEB6 ");
                //        binding_joining.linMain.setBackgroundResource(R.drawable.chatitembg_leaved_room);
                //      binding_joining.timeTxt.setTextColor(Color.parseColor("#EF9A9A"));
            }

        }
    }

    ///////////////////////////////////////// JOINING/LEAVING ////////////////////////////////////////////////








    ///////////////////////////////////////// OUTGOING ////////////////////////////////////////////////



    class ViewHolderOutgoing extends RecyclerView.ViewHolder implements View.OnClickListener {

        ChatMsgItemOutgoingBinding binding_outgoing;
        ClickListner clickListner;
        private boolean isRunning = false;
        private final int resetInTime = 500;
        private int counter = 0;
        private final int msgType ;

        public ViewHolderOutgoing(ChatMsgItemOutgoingBinding binding, ClickListner clickListner, int msgType ) {
            super(binding.getRoot());

            this.binding_outgoing = binding;
            this.clickListner = clickListner;
            itemView.setOnClickListener(this);
            this.msgType = msgType;

        }



        void bind(int position) {
            MessageModelHolder messageModelHolder = list.get(position);

            if(msgType==2) {
                binding_outgoing.layoutGchatContainerMe.setBackgroundResource(R.drawable.outgoing_messg_bubble);
            }else if(msgType == 6){
                binding_outgoing.layoutGchatContainerMe.setBackgroundResource(R.drawable.chatitembg_outgoing_middle_item);
            }else{
                binding_outgoing.layoutGchatContainerMe.setBackgroundResource(R.drawable.chatitembg_outgoing_end_item);
            }

            if(messageModelHolder.getMessage().length()<26){
            //Single Line

                binding_outgoing.secondaryMultiline.setVisibility(View.GONE);
                binding_outgoing.secondarySinlgeline.setVisibility(View.VISIBLE);
            binding_outgoing.spaceView.setVisibility(View.VISIBLE);

            binding_outgoing.textMessage.setText(messageModelHolder.getMessage());
            binding_outgoing.timeTxt2.setText(messageModelHolder.getTime());
        }
            else{

                //Multi Line

                binding_outgoing.secondaryMultiline.setVisibility(View.VISIBLE);
                binding_outgoing.secondarySinlgeline.setVisibility(View.GONE);
                binding_outgoing.spaceView.setVisibility(View.GONE);


                binding_outgoing.textMessage.setText(messageModelHolder.getMessage());
                binding_outgoing.timeTxt.setText(messageModelHolder.getTime());

            }
        }


        //             String html = getHTMLString(messageModel.getMessage());
//
//             if(html!=null) {
//                 binding_outgoing.textMessage.setText(Html.fromHtml(html));
//             }else{
//             }


        @Override
        public void onClick(View view) {

            if (isRunning) {
                if (counter == 1) //<-- makes sure that the callback is triggered on double click
                    clickListner.onClick(getAbsoluteAdapterPosition());
            }

            counter++;

            if (!isRunning) {
                isRunning = true;
                new Thread(() -> {
                    try {
                        Thread.sleep(resetInTime);
                        isRunning = false;
                        counter = 0;
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

        ChatMsgItemIncomingSameUsrBinding binding_incoming_sameusr;
        ClickListner clickListner;
         private boolean isRunning= false;
         private int resetInTime =500;
         private int counter=0;

        public ViewHolderIncoming_SameUsr(ChatMsgItemIncomingSameUsrBinding binding, ClickListner clickListner) {
            super(binding.getRoot());

            this.binding_incoming_sameusr = binding;
            this.clickListner = clickListner;

            itemView.setOnClickListener(this);

        }
         public void clearAnimation()
         {
             binding_incoming_sameusr.getRoot().clearAnimation();
         }

        void bind(int position) {
            MessageModelHolder messageModelHolder = list.get(position);
            binding_incoming_sameusr.textMessage.setText(messageModelHolder.getMessage());
            binding_incoming_sameusr.timeTxt.setText(messageModelHolder.getTime());

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





    ///////////////////////////////////////// REFERRED MESSG //////////////////////////////////////////////////////

    class ViewHolderRefferedMessage extends RecyclerView.ViewHolder implements View.OnClickListener{

        ChatMsgOutgoingReferenceItemOutgoingBinding referenceItemBinding;
        ClickListner clickListner;
        private boolean isRunning= false;
        private final int resetInTime =500;
        private int counter=0;

        public ViewHolderRefferedMessage(ChatMsgOutgoingReferenceItemOutgoingBinding binding, ClickListner clickListner) {

            super(binding.getRoot());
            this.referenceItemBinding = binding;
            this.clickListner = clickListner;
            itemView.setOnClickListener(this);

        }

        void bind(int position) {
            MessageModelHolder messageModelHolder = list.get(position);

//             String html = getHTMLString(messageModel.getMessage());
//
//             if(html!=null) {
//                 binding_outgoing.textMessage.setText(Html.fromHtml(html));
//             }else{
//             }


            referenceItemBinding.textMessage.setText(messageModelHolder.getMessage());
            referenceItemBinding.timeTxt.setText(messageModelHolder.getTime());
            referenceItemBinding.referenceText.setText(list.get(Integer.parseInt(messageModelHolder.getRefPos())).getMessage());
            referenceItemBinding.referenceToUsername.setText(list.get(Integer.parseInt(messageModelHolder.getRefPos())).getSenderName());

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


    ///////////////////////////////////////// REFERRED MESSG //////////////////////////////////////////////////////







    //////////////////////////////////////////////////////  VIEWHOLDERS  /////////////////////////////////////////////////////////////////////////////////////


    @Override
    public int getItemViewType(int position) {
        return list.get(position).getMessageType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


     switch (viewType){
         case 1:

             View view_incoming = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_msg_item_incoming, parent, false);
                     return new ViewHolderIncoming(ChatMsgItemIncomingBinding.bind(view_incoming), mClicklistner);

         case 2:
             View view_outgoing = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_msg_item_outgoing,parent,false);
             return new ViewHolderOutgoing(ChatMsgItemOutgoingBinding.bind(view_outgoing),mClicklistner, viewType);


         case 3:
             View view_outgoing_same_usr = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_msg_item_incoming_same_usr,parent,false);
             return new ViewHolderIncoming_SameUsr(ChatMsgItemIncomingSameUsrBinding.bind(view_outgoing_same_usr),mClicklistner);

         case 5:
             View view_ref_outgoing = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_msg_outgoing_reference_item_outgoing,parent,false);
             return new ViewHolderRefferedMessage(ChatMsgOutgoingReferenceItemOutgoingBinding.bind(view_ref_outgoing), mClicklistner);


         case 6:
             View view_outgoing_same_middle = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_msg_item_outgoing,parent,false);
             return new ViewHolderOutgoing(ChatMsgItemOutgoingBinding.bind(view_outgoing_same_middle),mClicklistner, viewType);

         case 7:
             View view_outgoing_same_end = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_msg_item_outgoing,parent,false);
             return new ViewHolderOutgoing(ChatMsgItemOutgoingBinding.bind(view_outgoing_same_end),mClicklistner, viewType);

         default:
             View view_joining = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_msg_item_user_joined,parent,false);
             return new ViewHolderJoining_Leaving(ChatMsgItemUserJoinedBinding.bind(view_joining));


     }

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {


        switch (list.get(position).getMessageType()){

            case MESSAGE_TYPE_IN  :

                     ((ViewHolderIncoming) holder).bind(position);
                     setAnimation(holder.itemView,position,R.anim.pop_in_slide);

                break;

            case MESSAGE_TYPE_OUT :

                    ((ViewHolderOutgoing) holder).bind(position);
                    setAnimation(holder.itemView, position, R.anim.pop_in_slide);


                break;

            case MESSAGE_TYPE_IN_SAME:
                ((ViewHolderIncoming_SameUsr) holder).bind(position);
                setAnimation(holder.itemView,position,R.anim.pop_in_slide);


                break;

            case MESSAGE_TYPE_JOING_LEAVING:
                ((ViewHolderJoining_Leaving) holder).bind(position);
                setAnimation(holder.itemView,position,R.anim.fade_in);
                break;

            case MESSAGE_TYPE_REFFERED_MSG:
                ((ViewHolderRefferedMessage) holder).bind(position);
                setAnimation(holder.itemView,position,R.anim.popin_right_to_left);

                break;

            case MESSAGE_TYPE_OUT_SAME_USER_MIDDLE_ITEM:
                ((ViewHolderOutgoing) holder).bind(position);
                setAnimation(holder.itemView,position,R.anim.popin_right_to_left);
                break;

            case MESSAGE_TYPE_OUT_SAME_USER_END_ITEM:
                ((ViewHolderOutgoing) holder).bind(position);
                setAnimation(holder.itemView,position,R.anim.popin_right_to_left);
                break;

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

//
//
//   String getHTMLString(String s){
//
//        String[] arr = s.split("");
//        StringBuilder stringBuilder = new StringBuilder();
//
//        if (s.contains("*")) {
//            if(isBalanced(arr)){
//                for (int i = 0; i < arr.length; i++) {
//                    if (arr[i].equals("*")) {
//                        arr[i] = "<strong>";
//                        for (int j = i + 1; j < arr.length; j++) {
//                            if (arr[j].equals("*")) {
//                                arr[j] = "</strong>";
//                                i = j + 1;
//                                break; }
//                        }
//                    }
//
//                }
//
//                for (String x : arr) {
//                    stringBuilder.append(x);
//                }
//
//                return stringBuilder.toString();
//
//            }else{
//                return null;
//            }
//
//
//        } else {
//            return null;        }
//
//    }
//
//
//
//    private boolean isBalanced(String[] arr){
//        int count = 0;
//        for (String i : arr){
//            if(i.equals("*")) count++;
//        }
//        return count % 2 == 0;
//    }
//
//



    public interface ClickListner{
        void onClick(int position);
    }

    private void setAnimation(View viewToAnimate, int position, int anim)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position < lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), R.anim.pop_in_slide);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(final RecyclerView.ViewHolder holder)
    {
            holder.itemView.clearAnimation();
    }


}

