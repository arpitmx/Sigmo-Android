package com.bitpolarity.spotifytestapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static android.content.ContentValues.TAG;

public class StatusActivity extends AppCompatActivity {

    final String LOG = "StatusActivity";
    Set<String> cache_friends;
    ImageView imageView;
    DB_Handler db_handler;
    String requestURL = "https://embed.spotify.com/oembed/?url=";
    SharedPreferences sharedPreferences;
    String arl;
    ListView listView;
    TempDataHolder tempDataHolder = new TempDataHolder();
    ShimmerFrameLayout shimmerFrameLayout;
    ////// Firebase specific


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        shimmerFrameLayout = findViewById(R.id.shimmerFrameLayout);
        shimmerFrameLayout.startShimmerAnimation();
        cache_friends = new HashSet<String>();
        final ListView lv = (ListView) findViewById(R.id.listview);
        final int ONLINE = R.drawable.ongreen;
        final int OFFLINE = R.drawable.ored;
        imageView = findViewById(R.id.icon);
        DatabaseReference ref;
        listView = findViewById(R.id.listview);


        ref =FirebaseDatabase.getInstance().getReference().child("Users");


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Map<String, Object> map = (Map<String, Object>) snapshot.getValue();

                Log.d(TAG, "Value is: " + map);
                assert map != null;
                Log.d(TAG, "Value is: " + map.keySet());

                Set keys = map.keySet();
                int size = keys.size();
                String metaData[] = new String[size];
                ArrayList<String[]> metaList = new ArrayList<>();

                ////////////////////////////// GETTING SONG META DATA ////////////////////////////////////////////////////////////


                for(Object key: keys){

                    Log.d(TAG, "onDataChange: "+key + ": " + map.get(key));
                    String meta = map.get(key).toString();

                    String[] purge = {"{", "}", "=", "SD","albumName","trackLength","trackID","artistName","trackName", "STATUS","LA"};
                    String result = meta;
                    for (String s : purge) {
                        result = result.replace(s, "");
                    }
                     metaData = result.split(",");
                    metaList.add(metaData);
                    Log.d(TAG, "onDataChange: "+Arrays.toString(metaData));


                }


                //////////////////////////////////  GETTING SONG META DATA //////////////////////////////////////////////

                String[] users = (String[]) keys.toArray(new String[size]);
                Log.d(TAG, "onDataChange: "+Arrays.toString(users));
                Integer[] status = new Integer[size];
                String[] songDetail = new String[size];
                String[] poster = new String[size];

                sharedPreferences= getSharedPreferences("com.bitpolarity.spotifytestapp",MODE_PRIVATE);
                ArrayList<String> e = new ArrayList<>();




                for (int i = 0 ; i < size ; i ++) {

                    if (Integer.parseInt(metaList.get(i)[5].trim())==1) {
                    status[i] = ONLINE;
                    }else{
                        status[i] = OFFLINE;
                    }

                    songDetail[i] = metaList.get(i)[4]+"-"+metaList.get(i)[3];
                    String url = metaList.get(i)[2].trim();
                    poster[i] = url;
                    Log.d(TAG, "Arturl: "+url);

                }

               // requestURL+metaList.get(0)[2].trim();




                CustomAdapter customAdapter = new CustomAdapter(StatusActivity.this, users,poster,status,songDetail);
                lv.setAdapter(customAdapter);

                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);










            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(StatusActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });










        //cache_friends.add(Arrays.toString(people));
        //prefs.edit().putStringSet("friends", cache_friends).apply();






    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //setStatus(0);
        //Log.v(LOG, "Activity status  : destroyed");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(LOG, "Status : Background");

    }

    public List<String> getTextFromWeb(String urlString)
    {
        URLConnection feedUrl;
        List<String> placeAddress = new ArrayList<>();

        try
        {
            feedUrl = new URL(urlString).openConnection();
            InputStream is = feedUrl.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = null;

            while ((line = reader.readLine()) != null) // read line by line

            {
                placeAddress.add(line); // add line to list
            }
            is.close(); // close input stream

            return placeAddress; // return whatever you need
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }



 void  setDetails (String url){


        new Thread(new Runnable()
        {
            public void run()
            {

                List<String> addressList = getTextFromWeb(url); // format your URL
                //Log.d(LOG, String.valueOf(addressList));
                String url = addressList.get(0).split(",")[8].replace("\"thumbnail_url\":","").replace("\"","");
                tempDataHolder.setArtUrl(url);
                Log.d(LOG, url);
        }

    }).start();

  }
}