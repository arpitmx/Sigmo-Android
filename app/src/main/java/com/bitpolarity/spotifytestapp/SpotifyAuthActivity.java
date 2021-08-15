package com.bitpolarity.spotifytestapp;

import static com.bitpolarity.spotifytestapp.Services.OnClearFromRecentService.editor;
import static com.bitpolarity.spotifytestapp.Services.OnClearFromRecentService.prefs;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;


public class SpotifyAuthActivity extends AppCompatActivity {

    private final String CLIENT_ID= "84b37e8b82e2466c9f69a2e41b100476";
    private static final int REQUEST_CODE = 1337;
    private static final String REDIRECT_URI = "http://localhost:8888/callback";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotify_auth);



        AuthorizationRequest.Builder builder = new AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI);
        builder.setScopes(new String[]{"streaming"});
        AuthorizationRequest request = builder.build();
        AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request);

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, intent);

            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    // Handle successful response
                    // startActivity(new Intent(SpotifyAuthAcitivity.this,MainActivity.class));
                    Toast.makeText(this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                    editor.putBoolean("isAuthed",true);
                    break;

                // Auth flow returned an error
                case ERROR:
                    Toast.makeText(SpotifyAuthActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    editor.putBoolean("isAuthed",false);

                    break;

                // Most likely auth flow was cancelled
                default:
                    // Handle other cases
                    Toast.makeText(this, "Already logged in", Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}