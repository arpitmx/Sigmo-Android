package com.bitpolarity.spotifytestapp.UI_Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bitpolarity.spotifytestapp.Services.OnClearFromRecentService;
import com.bitpolarity.spotifytestapp.R;

public class Login extends AppCompatActivity {


    EditText name ;
    Button next ;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        name = (EditText) findViewById(R.id.username);
        next = (Button) findViewById(R.id.next);

        prefs=  getSharedPreferences("com.bitpolarity.spotifytestapp",MODE_PRIVATE);

    }


    @Override
    protected void onResume() {
        super.onResume();


        if (prefs.getBoolean("firstrun", true)) {

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   prefs.edit().putString("Username", name.getText().toString()).apply();

                    startService(new Intent(getBaseContext(), OnClearFromRecentService.class));
                    startActivity(new Intent(Login.this , MainHolder.class));

                }
            });

        }
        else{

            startActivity(new Intent(this , MainHolder.class));

        }
            prefs.edit().putBoolean("firstrun", false).apply();

        }

    @Override
    protected void onPause() {
        super.onPause();

    }
}