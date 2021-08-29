package com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Circle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitpolarity.spotifytestapp.Adapters.FR_Adapter;
import com.bitpolarity.spotifytestapp.Adapters.RoomsListAdapters.RoomsListAdapter;
import com.bitpolarity.spotifytestapp.BottomSheets.mBottomSheetDialog;
import com.bitpolarity.spotifytestapp.GetterSetterModels.FR_Model;
import com.bitpolarity.spotifytestapp.GetterSetterModels.UserListModel;
import com.bitpolarity.spotifytestapp.R;
import com.bitpolarity.spotifytestapp.Adapters.CircleFriendActivityAdapter.UserListAdapter;
import com.bitpolarity.spotifytestapp.DB_Related.TempDataHolder;
import com.bitpolarity.spotifytestapp.databinding.ActivityStatusBinding;
import com.bitpolarity.spotifytestapp.databinding.FragmentCircleBinding;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static android.content.ContentValues.TAG;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.xml.transform.Result;

public class StatusActivity extends Fragment implements UserListAdapter.ULEventListner {

    final String LOG = "StatusActivity";
    RecyclerView userRecyclerView;
    RecyclerView fr_rv;
    UserListAdapter userListAdapter;
    FR_Adapter fr_adapter;
    TabLayout tabLayout;
    DatabaseReference ref;
    mBottomSheetDialog bottomSheet;

    TempDataHolder dataHolder;
    final int ONLINE = R.drawable.ongreen;
    final int OFFLINE = R.drawable.ored;
    Parcelable state;

    LinearLayoutManager layoutManager ;
    LinearLayoutManager layoutManagerFR;

    ActivityStatusBinding binding;
    static String finaltime = "";


    ////// Firebase specific




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        binding = ActivityStatusBinding.inflate(inflater,container,false);
        userRecyclerView = binding.listview;
        fr_rv = binding.frRv;

        layoutManager = new LinearLayoutManager(getContext());
        layoutManagerFR = new LinearLayoutManager(getContext());
        return binding.getRoot();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        dataHolder = new TempDataHolder();
        ref =FirebaseDatabase.getInstance().getReference().child("Users_DMode");
        binding.shimmerFrameLayout.startShimmerAnimation();



        layoutManagerFR.setOrientation(RecyclerView.HORIZONTAL);
        fr_rv.setLayoutManager(layoutManagerFR);
        fr_rv.setNestedScrollingEnabled(false);

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        userRecyclerView.setLayoutManager(layoutManager);
        userRecyclerView.setNestedScrollingEnabled(false);


        ArrayList<FR_Model> list = new ArrayList<>();
        list.add(new FR_Model(R.drawable.poster,"Teena"));
        list.add(new FR_Model(R.drawable.poster,"Teena"));
        list.add(new FR_Model(R.drawable.poster,"Teena"));
        list.add(new FR_Model(R.drawable.poster,"Teena"));
        fr_adapter = new FR_Adapter(list , getContext());
        fr_rv.setAdapter(fr_adapter);
        fr_rv.setNestedScrollingEnabled(false);
        fr_adapter.notifyDataSetChanged();


    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();

        binding.refreshLayout.setOnRefreshListener(() -> {
            loadData();
            binding.refreshLayout.setRefreshing(false);
        });
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

            bottomSheet = new mBottomSheetDialog(details,trackID);
            bottomSheet.show(getChildFragmentManager(),"ModalBottomSheet");

        }).start();

    }

    void loadData(){

        new Thread(() -> {

            Log.d(TAG, "Thread Name :" + Thread.currentThread().getName());

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {


                    //TODO : Changed positions of this .

                    int lastFirstVisiblePosition = ((LinearLayoutManager) userRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                    userRecyclerView.getLayoutManager().scrollToPosition(lastFirstVisiblePosition);
                    getMyData(snapshot);


                    //// SHIMMERS ///////////////////////////////////////////////////////////////

                    binding.shimmerFrameLayout.stopShimmerAnimation();
                    binding.shimmerFrameLayout.setVisibility(View.GONE);
                    userRecyclerView.setVisibility(View.VISIBLE);

                    //// SHIMMERS ///////////////////////////////////////////////////////////////

                }

                ///////////////////// SETTING ONCLICK LISTNER ON ELEMENTS OF RECYCLER VIEW

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), "Fail to get data.", Toast.LENGTH_SHORT).show();

                }


            });


        }).start();
    }


   void getMyData(DataSnapshot snapshot){

        userListAdapter = new UserListAdapter(getDataFaster(snapshot), StatusActivity.this, getContext());
        //userRecyclerView.setAdapter(userListAdapter);
        someMethod(userRecyclerView, userListAdapter);
        userListAdapter.notifyDataSetChanged();
    }

    List<UserListModel> getDataFaster(DataSnapshot snapshot){

        ArrayList<HashMap<String, Object>> data = recArrayList(snapshot);
        List<UserListModel> modelList = new ArrayList<>();


        int size = data.size();
        Log.d(TAG, "getDataFaster: data SIZE"+data.size());


        String[] users = new String[size]; // done
        Integer[] status = new Integer[size]; // done
        String[] songDetail = new String[size]; // done
        String[] posterURL = new String[size];  //done
        String[] isPlaying = new String[size]; //done
        String[] dateTime = new String[size];  //done
        String[] trackID = new String[size]; //done
        String[] artistName = new String[size]; //done




        Log.d(TAG, "getDataFaster: "+data);
        try {
            for (int i = 0 ; i < size ; i++) {

                JSONObject reader = new JSONObject(data.get(i));
                JSONObject SD = reader.getJSONObject("SD");

                // SONGDETAIL
                Log.d(TAG, "Songdetail : " + SD.getString("trackName") + SD.getString("artistName"));
                songDetail[i] = SD.getString("trackName") ;
                artistName[i] = SD.getString("artistName");

                // TRACKID
                Log.d(TAG, "TrackID : "+ SD.getString("trackID") );
                trackID[i] = SD.getString("trackID");

                //posterURL
                Log.d(TAG, "PosterURL : "+ SD.getString("posterURL") );
                posterURL[i] = SD.getString("posterURL");

                //Status
                Log.d(TAG, "Status : "+ data.get(i).get("STATUS") );
                int s = Integer.parseInt(String.valueOf(data.get(i).get("STATUS")));
                if (s == 1) status[i] = 1;
                else status[i] = 0;

                String now = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
                //Log.d(TAG, "Last active : "+ data.get(i).get("recKeyID") + ":" + findDifference(String.valueOf(data.get(i).get("LA")),now) );

                //isPlaying
                Log.d(TAG, "IsPlaying : "+ data.get(i).get("isPlaying") );
                isPlaying[i] = String.valueOf(data.get(i).get("isPlaying"));


                //DateTime
                Log.d(TAG, "IsPlaying : "+ data.get(i).get("LA"));
                dateTime[i] = String.valueOf(data.get(i).get("LA"));

                //Users
                Log.d(TAG, "UserName : "+ data.get(i).get("recKeyID") );
                users[i] = String.valueOf(data.get(i).get("recKeyID"));


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.d(TAG, "User array: " + Arrays.toString(users));
        Log.d(TAG, "Poster array: " + Arrays.toString(posterURL));
        Log.d(TAG, "isPlaying array : " + Arrays.toString(isPlaying));
        Log.d(TAG, "songDetail array : " + Arrays.toString(songDetail));
        Log.d(TAG, "trackID array : " + Arrays.toString(trackID));
        Log.d(TAG, "Status array: " + Arrays.toString(status));


        dataHolder.setSongDetails(songDetail);
        dataHolder.setTrackID(trackID);

        for (int i = 0; i < size; i++) {
            modelList.add(new UserListModel(dateTime[i], isPlaying[i], users[i], posterURL[i], status[i], songDetail[i],artistName[i]));
        }

        return modelList;
    }

    static String findDifference(String start_date,String end_date) {

        SimpleDateFormat sdf
                = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");



        // Try Block
        try {

            Date d1 = sdf.parse(start_date);
            Date d2 = sdf.parse(end_date);

            long difference_In_Time
                    = d2.getTime() - d1.getTime();
            long difference_In_Seconds
                    = (difference_In_Time
                    / 1000) % 60;

            long difference_In_Minutes
                    = (difference_In_Time
                    / (1000 * 60)) % 60;

            long difference_In_Hours
                    = (difference_In_Time
                    / (1000 * 60 * 60)) % 24;

            long difference_In_Years
                    = (difference_In_Time / (1000L * 60 * 60 * 24 * 365));

            long difference_In_Days
                    = (difference_In_Time
                    / (1000 * 60 * 60 * 24)) % 365;
            System.out.print(
                    "Difference "
                            + "between two dates is: ");


            long[] s = {difference_In_Years, difference_In_Days, difference_In_Hours, difference_In_Minutes, difference_In_Seconds};

            if (s[0] == 0 && s[1] == 0 && s[2] == 0 && s[3] == 0 && s[4] != 0) {
                finaltime = s[4] + " sec ago";
            } else if (s[0] == 0 && s[1] == 0 && s[2] == 0 && s[3] != 0) {
                finaltime = s[3] + " min ago";
            } else if (s[0] == 0 && s[1] == 0 && s[2] != 0) {
                finaltime = s[2] + " hour ago";
            } else if (s[0] == 0 && s[1] != 0) {
                finaltime = s[1] + " day ago";
            } else if (s[0] != 0) {
                finaltime = s[0] + " year ago";

            }


            System.out.println(
                    difference_In_Years
                            + " years, "
                            + difference_In_Days
                            + " days, "
                            + difference_In_Hours
                            + " ⌛ hours, "
                            + difference_In_Minutes
                            + " minutes, "
                            + difference_In_Seconds
                            + " seconds");


        }

        catch (ParseException e) {
            e.printStackTrace();
        }

        return finaltime;
    }

}
























































//        {
//            "SD": {
//            "albumName": "Ambitions",
//                    "posterURL": "https://i.scdn.co/image/ab67616d00001e020f94f53a1c9c60d953ffd2f2",
//                    "trackLength": "255400",
//                    "trackID": "spotify:track:57sk9X1fPLXRfkw74XNrmK",
//                    "artistName": "ONE OK ROCK",
//                    "trackName": "We Are"
//        },
//            "recKeyID": "Arpit!",
//                "STATUS": 0,
//                "isPlaying": false,
//                "LA": "⏳ 3h ago"
//        }


////////////////////////////////////////////ASYNC TASKS ////////////////////////////////////////////////











