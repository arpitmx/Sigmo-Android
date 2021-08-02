package com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Circle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitpolarity.spotifytestapp.BottomSheets.mBottomSheetDialog;
import com.bitpolarity.spotifytestapp.GetterSetterModels.UserListModel;
import com.bitpolarity.spotifytestapp.R;
import com.bitpolarity.spotifytestapp.Adapters.CircleFriendActivityAdapter.UserListAdapter;
import com.bitpolarity.spotifytestapp.DB_Related.TempDataHolder;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.content.ContentValues.TAG;

public class StatusActivity extends Fragment implements UserListAdapter.ULEventListner {

    final String LOG = "StatusActivity";
    ImageView imageView;
    ShimmerFrameLayout shimmerFrameLayout;
    TextView isPlayingTV ;
    RecyclerView userRecyclerView;
    UserListAdapter userListAdapter;
    DatabaseReference ref;
    TempDataHolder dataHolder;
    final int ONLINE = R.drawable.ongreen;
    final int OFFLINE = R.drawable.ored;
    Parcelable state;
    LinearLayoutManager layoutManager ;




    ////// Firebase specific




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
       // super.onCreate(savedInstanceState);

        View v = inflater.inflate(R.layout.activity_status,container,false);

        isPlayingTV = (TextView) v.findViewById(R.id.isPlayingg);

        userRecyclerView = v.findViewById(R.id.listview);
        shimmerFrameLayout = v.findViewById(R.id.shimmerFrameLayout);
        shimmerFrameLayout.startShimmerAnimation();
        isPlayingTV = v.findViewById(R.id.isPlayingg);
        imageView = v.findViewById(R.id.online_status);
        dataHolder = new TempDataHolder();

        layoutManager = new LinearLayoutManager(getContext());


        ref =FirebaseDatabase.getInstance().getReference().child("Users");

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        userRecyclerView.setLayoutManager(layoutManager);
        userRecyclerView.setNestedScrollingEnabled(false);


//       new Thread(() ->
//            })).start();



                return v;
    }


    @Override
    public void onResume() {
        super.onResume();


        new Thread(() -> ref.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Map<String, Object> map1 = (Map<String, Object>) snapshot.getValue();
                Log.d(TAG, "HASSSHH:" + Arrays.toString(recArrayList(snapshot).get(0).get("SD").toString().replace("{", "").replace("}", "").split(",")));

                assert map1 != null;
                Log.d(TAG, "Value is: " + map1.keySet());

                Set<String> keys = map1.keySet();
                int size = keys.size();
                String metaData[];
                ArrayList<String[]> metaList = new ArrayList<>();

                //TODO : Changed positions of this .

                int lastFirstVisiblePosition = ((LinearLayoutManager)userRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                userRecyclerView.getLayoutManager().scrollToPosition(lastFirstVisiblePosition);

                ////////////////////////////// GETTING SONG META DATA ////////////////////////////////////////////////////////////


                for (String key : keys) {
                    Log.d(TAG, "onDataChange: " + key + ": " + map1);
                    String meta = map1.get(key).toString();

                    String[] purge = {"{", "}", "=", "SD", "albumName", "isPlaying", "trackLength", "posterURL", "trackID", "artistName", "trackName", "STATUS", "LA"};
                    String result = meta;


                    for (String s : purge) {
                        result = result.replace(s, "");
                    }
                    metaData = result.split(",");
                    metaList.add(metaData);
                    Log.d(TAG, "metaList: " + Arrays.toString(metaList.get(0)));
                }

                Map<String, Object> map = new HashMap<>();

                for (int i = 0; i < size; i++) {

                    map.put("albumName" + i, metaList.get(i)[0]);
                    map.put("trackLength" + i, metaList.get(i)[2]);
                    map.put("isPlaying" + i, metaList.get(i)[7]);
                    map.put("trackID" + i, metaList.get(i)[3]);
                    map.put("artistName" + i, metaList.get(i)[4]);
                    map.put("trackName" + i, metaList.get(i)[5]);
                    map.put("STATUS" + i, metaList.get(i)[6]);
                    map.put("LA" + i, metaList.get(i)[8]);
                    map.put("posterURL" + i, metaList.get(i)[1]);

                }

                Log.d(TAG, "HASH MAP: " + map);

                //////////////////////////////////  GETTING SONG META DATA //////////////////////////////////////////////



                String[] users = (String[]) keys.toArray(new String[size]);
                Log.d(TAG, "onDataChange: " + Arrays.toString(users));
                Integer[] status = new Integer[size];
                String[] songDetail = new String[size];
                String[] posterURL = new String[size];
                String[] isPlaying = new String[size];
                String[] dateTime = new String[size];
                String[] trackID = new String[size];


                for (int i = 0; i < size; i++) {

                    Log.d(TAG, "Online Status : " + map.get("STATUS" + i));
                    if (Integer.parseInt(map.get("STATUS" + i).toString().trim()) == 1) {
                        status[i] = ONLINE;
                    } else {
                        status[i] = OFFLINE;
                    }

                    if (map.get("isPlaying" + i).toString().trim().equals("true")) {
                        isPlaying[i] = "Playing";
                    } else {
                        isPlaying[i] = "Paused";

                    }

                    songDetail[i] = map.get("trackName" + i) + "-" + map.get("artistName" + i);
                    trackID[i] = String.valueOf(map.get("trackID" + i)).trim();
                    String url = String.valueOf(map.get("posterURL" + i)).trim();

                    posterURL[i] = url;

                    dateTime[i] = String.valueOf(map.get("LA" + i));


                }

                Log.d(TAG, "Poster array: " + Arrays.toString(posterURL));
                Log.d(TAG, "isPlaying array : " + Arrays.toString(isPlaying));
                Log.d(TAG, "songDetail array : " + Arrays.toString(songDetail));
                Log.d(TAG, "trackID array : " + Arrays.toString(trackID));


                ////////////////////////// SETTING DATA TO ADAPTER




                dataHolder.setSongDetails(songDetail);
                dataHolder.setTrackID(trackID);
                // dataHolder.setTrackID(son);
                List<UserListModel> modelList = new ArrayList<>();

                for (int i = 0; i < size; i++) {
                    modelList.add(new UserListModel(getContext(), dateTime[i], isPlaying[i], users[i], posterURL[i], status[i], songDetail[i]));
                }
                userListAdapter = new UserListAdapter(modelList, StatusActivity.this);
                //userRecyclerView.setAdapter(userListAdapter);
                someMethod(userRecyclerView, userListAdapter);
                userListAdapter.notifyDataSetChanged();
                ////////////////////////// SETTING DATA TO ADAPTER

                //// SHIMMERS ///////////////////////////////////////////////////////////////

                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                userRecyclerView.setVisibility(View.VISIBLE);
            }

            //// SHIMMERS ///////////////////////////////////////////////////////////////


            ///////////////////// SETTING ONCLICK LISTNER ON ELEMENTS OF RECYCLER VIEW
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(getContext(), "Fail to get data.", Toast.LENGTH_SHORT).show();
            }


        })).start();



    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //setStatus(0);
        //Log.v(LOG, "Activity status  : destroyed");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v(LOG, "Status : Background");
        state = layoutManager.onSaveInstanceState();


    }

    private void someMethod(RecyclerView rv, UserListAdapter adapter) {
        rv.setAdapter(adapter);
        layoutManager.onRestoreInstanceState(state);
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


    @Override
    public void onClick(int position) {

        new Thread(() -> {


            String details = dataHolder.getSongDetails()[position];
            String trackID = dataHolder.getTrackID()[position];


            Log.d(TAG, "onClick: POS:"+position+"  Details : "+details);
            Log.d(TAG, "onClick: POS:"+position+"  Details : "+trackID);

            mBottomSheetDialog bottomSheet = new mBottomSheetDialog(details,trackID);
            bottomSheet.show(getChildFragmentManager(),"ModalBottomSheet");
        }).start();




    }
}