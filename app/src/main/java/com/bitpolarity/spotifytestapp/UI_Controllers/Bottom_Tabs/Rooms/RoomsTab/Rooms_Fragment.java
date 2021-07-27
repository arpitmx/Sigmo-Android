package com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Rooms.RoomsTab;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bitpolarity.spotifytestapp.Adapters.RoomsListAdapters.RoomsListAdapter;
import com.bitpolarity.spotifytestapp.DB_Handler;
import com.bitpolarity.spotifytestapp.GetterSetterModels.RoomsListModel;
import com.bitpolarity.spotifytestapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.content.ContentValues.TAG;


public class Rooms_Fragment extends Fragment implements RoomsListAdapter.ULEventListner_Room {
    AppCompatButton startRoomBTN;
    DB_Handler db_handler ;
    DatabaseReference mref;
    final String TAG = "Rooms_Fragment";
    LinearLayoutManager lm;
    RecyclerView mRoomRV;
    RoomsListAdapter listAdapter;
    FirebaseDatabase firebaseDatabase;


    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db_handler = new DB_Handler();
        mref = FirebaseDatabase.getInstance().getReference().child("Rooms");






    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_rooms, container, false);
        startRoomBTN =v.findViewById(R.id.startroomBTN);



        lm = new LinearLayoutManager(getContext());
        mRoomRV = v.findViewById(R.id.rooms_listview);
        List<RoomsListModel> modelList = new ArrayList<>();



        lm.setOrientation(LinearLayoutManager.VERTICAL);
        mRoomRV.setLayoutManager(lm);
        mRoomRV.setNestedScrollingEnabled(false);


       // Log.d(TAG, "onDataChange: "+modelList);
//        modelList.add(new RoomsListModel("TestRoom0","@arpitmaurya"));
//        Log.d(TAG, "Rooms: "+modelList);
//                listAdapter = new RoomsListAdapter(modelList, Rooms_Fragment.this);
//


   mref.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                //Iterator<? extends DataSnapshot> i = snapshot.getChildren().iterator();
                Map<String, Object> map1 = (Map<String, Object>) snapshot.getValue();

//                while (i.hasNext()){
//                     modelList.add(new RoomsListModel(((DataSnapshot) i.next()).getKey(),"@arpitmaurya"));
//                     Log.d(TAG, "DS: "+ (DataSnapshot) ((DataSnapshot) i.next()).getValue());
//                     Log.d(TAG, "DSValue: "+ ((DataSnapshot) i.next()).getValue());
//
//                 }

                assert map1 != null;
                Set<String> keys = map1.keySet();

                for (String key : keys) {
                    Log.d(TAG, "onDataChange: " + key + ": " + map1);
                    modelList.add(new RoomsListModel(key,"@arpitmaurya"));

                }

//                Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
//                assert map != null;
//                Log.d(TAG, "Value is: " + map.keySet());
//                Set<String> keys = map.keySet();





                modelList.add(new RoomsListModel("TestRoom0","@arpitmaurya"));


                Log.d(TAG, "onDataChange: "+modelList);
                listAdapter = new RoomsListAdapter(modelList, Rooms_Fragment.this::onClick);
                mRoomRV.setAdapter(listAdapter);
                listAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.d(TAG, "onCancelled: "+error);

            }

        });



        startRoomBTN.setOnClickListener(view -> {

            mCreateRoomBottomSheet bottomSheet = new mCreateRoomBottomSheet();
            bottomSheet.show(getChildFragmentManager(), "Room_Bottomsheet up");

                  });


        return v;
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.child_fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onClick(int position) {

    }

}