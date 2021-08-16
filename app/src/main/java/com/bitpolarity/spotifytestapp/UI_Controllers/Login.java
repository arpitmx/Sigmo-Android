package com.bitpolarity.spotifytestapp.UI_Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bitpolarity.spotifytestapp.Services.OnClearFromRecentService;
import com.bitpolarity.spotifytestapp.R;
import com.bitpolarity.spotifytestapp.SpotifyLoginVerifierActivity;

public class Login extends AppCompatActivity {


    EditText name ;
    Button next ;
    SharedPreferences prefs;
    SharedPreferences.Editor sharedEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        name = (EditText) findViewById(R.id.username);
        next = (Button) findViewById(R.id.next);
        prefs=  getSharedPreferences("com.bitpolarity.spotifytestapp",MODE_PRIVATE);
        sharedEditor = prefs.edit();


    }


    @Override
    protected void onResume() {
        super.onResume();


        if (isItFirestTime()) {

            next.setEnabled(true);
            next.setOnClickListener(view -> {

                String username =  name.getText().toString();

                if(!username.equals("") && !prefs.getBoolean("isAuthed",false)) {

                    prefs.edit().putString("Username", username).apply();
                    startService(new Intent(getBaseContext(), OnClearFromRecentService.class));
                    startActivity(new Intent(Login.this, MainHolder.class));
                    Toast.makeText(this, "Welcome"+username, Toast.LENGTH_SHORT).show();

                }else{
                name.setError("Name can't be empty");
                    }
            });

        }

        else{
//          //  if(prefs.getBoolean("isAuthed",false)) {
//                next.setEnabled(false);
//              //  startActivity(new Intent(this, SpotifyLoginVerifierActivity.class));
//                startActivity(new Intent(this, MainHolder.class));
//                Toast.makeText(this, "Authenticate with Spotify", Toast.LENGTH_SHORT).show();
//         //   }else{
                next.setEnabled(false);
                startService(new Intent(getBaseContext(), OnClearFromRecentService.class));
                startActivity(new Intent(this, MainHolder.class));
                Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();
            }
        }



//        if(prefs.getBoolean("loggedin", true)) {
//            prefs.edit().putBoolean("firstrun", false).apply();
//        }else{
//            prefs.edit().putBoolean("firstrun", true).apply();
//        }



    @Override
    protected void onPause() {
        super.onPause();

    }


    public boolean isItFirestTime() {
        if (prefs.getBoolean("firstrun", true)) {
            sharedEditor.putBoolean("firstrun", false);
            sharedEditor.commit();
            sharedEditor.apply();
            return true;
        } else {
            return false;
        }
    }
}