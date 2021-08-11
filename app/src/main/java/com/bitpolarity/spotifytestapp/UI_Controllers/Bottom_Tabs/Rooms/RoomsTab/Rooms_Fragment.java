package com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Rooms.RoomsTab;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

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
import com.bitpolarity.spotifytestapp.GetterSetterModels.UserListModel;
import com.bitpolarity.spotifytestapp.R;
import com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Rooms.RoomHolder.MainHolder.RoomHolderActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
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
    static List<RoomsListModel> modelList;


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
        startRoomBTN = v.findViewById(R.id.startroomBTN);


        lm = new LinearLayoutManager(getContext());
        mRoomRV = v.findViewById(R.id.rooms_listview);
        modelList = new ArrayList<>();


        lm.setOrientation(LinearLayoutManager.VERTICAL);
        mRoomRV.setLayoutManager(lm);
        mRoomRV.setNestedScrollingEnabled(false);


        // Log.d(TAG, "onDataChange: "+modelList);
//        modelList.add(new RoomsListModel("TestRoom0","@arpitmaurya"));
//        Log.d(TAG, "Rooms: "+modelList);
//                listAdapter = new RoomsListAdapter(modelList, Rooms_Fragment.this);
//
        mRoomRV.hasFixedSize();


        mref.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                Map<String, Object> map1 = (Map<String, Object>) snapshot.getValue();


                Log.d(TAG, "onDataChange Map : " + map1);

                //assert map1 != null;
                Set<String> keys = map1.keySet();

                if (modelList != null) {
                    modelList.clear();
                }

                for (String key : keys) {

                    String hostname;
                    DatabaseReference hostref = mref.child(key).child("roomDetails").child("hostDetails").child("userName");

                    modelList.add(new RoomsListModel(key, "Host"));
                    Log.d(TAG, "onDataChange key : " + key);
                    Log.d(TAG, "onDataChange Value : " + map1.get(key));

                }


                Log.d(TAG, "onDataChange ModelList: " + modelList);


                listAdapter = new RoomsListAdapter(modelList, Rooms_Fragment.this);
                mRoomRV.setAdapter(listAdapter);
                listAdapter.notifyDataSetChanged();


            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.d(TAG, "onCancelled: " + error);

            }

        });




        startRoomBTN.setOnClickListener(view -> {

            mCreateRoomBottomSheet bottomSheet = new mCreateRoomBottomSheet();
            bottomSheet.show(getChildFragmentManager(), "Room_Bottomsheet up");

                  });


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        mRoomRV.addOnScrollListener(new RoomsListAdapter.MyRecyclerScroll() {
            @Override
            public void show() {
                startRoomBTN.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
            }

            @Override
            public void hide() {
                startRoomBTN.animate().translationY(startRoomBTN.getHeight() +30).setInterpolator(new AccelerateInterpolator(2)).start();
            }
        });
    }



    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.child_fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onClick(int position) {
        Intent i = new Intent(getContext(), RoomHolderActivity.class);
        i.putExtra("room_name",modelList.get(position).getmRoomName());
        startActivity(i);


    }


    public ArrayList<HashMap<String, Object>> recArrayList(DataSnapshot snapshot){
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        if (snapshot == null){
            return list;
        }

        // This is awesome! You don't have to know the data structure of the database.
        Object fieldsObj = new Object();

        HashMap<String, Object> fldObj;

        for (DataSnapshot shot : snapshot.getChildren()){

            try{

                fldObj = (HashMap<String, Object>)shot.getValue(fieldsObj.getClass());

            }catch (Exception ex){

                Log.d(TAG, "recArrayList: "+ Arrays.toString(ex.getStackTrace()));

                continue;
            }
            // Include the primary key of this Firebase data record. Named it 'recKeyID'
            fldObj.put("recKeyID", shot.getKey());

            list.add(fldObj);
        }

        return list;
    }


    List<RoomsListModel> getDataFaster(DataSnapshot snapshot){

        ArrayList<HashMap<String, Object>> data = recArrayList(snapshot);
        List<RoomsListModel> modelList = new ArrayList<>();


        int size = data.size();
        Log.d(TAG, "getDataFaster: data SIZE"+data.size());
        Log.d(TAG, "getDataFaster: "+data);


        try {

            for (int i = 0 ; i < size ; i++) {

                JSONObject reader = new JSONObject(data.get(i));
                JSONObject SD = reader.getJSONObject("roomDetails").getJSONObject("hostDetails");

                //Users
                Log.d(TAG, "UserName : "+ data.get(i).get("recKeyID") );
                //users[i] = String.valueOf(data.get(i).get("recKeyID"));
                modelList.add(new RoomsListModel( String.valueOf(data.get(i).get("recKeyID")),SD.getString("userName")));


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



        return modelList;
    }


}