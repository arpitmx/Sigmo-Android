package com.bitpolarity.spotifytestapp.UI_Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bitpolarity.spotifytestapp.MainHolderActivity;
import com.bitpolarity.spotifytestapp.Services.OnClearFromRecentService;
import com.bitpolarity.spotifytestapp.R;
import com.bitpolarity.spotifytestapp.TopLevelTabs.MainHolderFragment;
import com.bitpolarity.spotifytestapp.databinding.ActivityLoginBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Login extends AppCompatActivity {


    private static final String TAG ="Login" ;
    EditText name ;
    Button next ;
    int RC_SIGN_IN = 0;
    SignInButton signInButton;
    private FirebaseAuth mAuth;

    GoogleSignInClient mGoogleSignInClient;
    ActivityLoginBinding binding;

    boolean isAuthed;
    SharedPreferences prefs;
    SharedPreferences.Editor sharedEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        name = binding.username;
//        next = binding.next;
        signInButton = binding.signInbtn;
        prefs=  getSharedPreferences("com.bitpolarity.spotifytestapp",MODE_PRIVATE);
        sharedEditor = prefs.edit();
        mAuth = FirebaseAuth.getInstance();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_webclient_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton.setOnClickListener(this::onClick);



    }



    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }



    public void onClick(View v){
            switch (v.getId()){
                case R.id.signInbtn:
                    signIn();
                    break;
            }
    }

    @Override
    protected void onStart() {
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
       FirebaseUser currentUser = mAuth.getCurrentUser();

        updateUI(currentUser);


//        isAuthed = prefs.getBoolean("isAuthed",false);
//        if(!isAuthed) {
//
//
//
//            next.setEnabled(true);
//            next.setOnClickListener(view -> {
//
//                String username = name.getText().toString();
//
//                if (!username.equals("")) {
//
//                    prefs.edit().putString("Username", username).apply();
//                    startService(new Intent(getBaseContext(), OnClearFromRecentService.class));
//                    startActivity(new Intent(Login.this, MainHolderActivity.class));
//                    Toast.makeText(this, "Welcome" + username, Toast.LENGTH_SHORT).show();
//                    prefs.edit().putBoolean("isAuthed", true).apply();
//                    finish();
//
//
//                } else {
//                    name.setError("Name can't be empty");
//                }
//            });
//
//
//        }
//        else{
//
//            next.setEnabled(false);
//            startService(new Intent(getBaseContext(), OnClearFromRecentService.class));
//            startActivity(new Intent(this, MainHolderActivity.class));
//            Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();
//            finish();
//        }

    }


    @Override
    protected void onResume() {
        super.onResume();

        }


    @Override
    protected void onPause() {
        super.onPause();

    }


    private boolean isItFirestTime() {
        if (prefs.getBoolean("firstrun", true)) {
            sharedEditor.putBoolean("firstrun", false);
            sharedEditor.commit();
            sharedEditor.apply();
            return true;
        } else {
            return false;
        }
    }

    void updateUI(FirebaseUser account){

        if(account!=null){
            signInButton.setEnabled(false);
            startService(new Intent(getBaseContext(), OnClearFromRecentService.class));
            startActivity(new Intent(this, MainHolderActivity.class));
            Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            signInButton.setEnabled(true);
            Toast.makeText(this, "Sign in with your google account", Toast.LENGTH_SHORT).show();

        }

    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            // The Task returned from this call is always completed, no need to attach
//            // a listener.
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            handleSignInResult(task);
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }}


        private void firebaseAuthWithGoogle(String idToken) {
            AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithCredential:success");
                                FirebaseUser account = mAuth.getCurrentUser();

                                assert account != null;
                                prefs.edit().putString("Username", account.getDisplayName()).apply();
                                prefs.edit().putString("UDP", String.valueOf(account.getPhotoUrl())).apply();

                                  Log.w(TAG, "signin Display name" + account.getDisplayName());
                                  Log.w(TAG, "signin Display photo url" + account.getPhotoUrl());
                                  Log.w(TAG, "signin Display phone number" + account.getPhoneNumber());

                                updateUI(account);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithCredential:failure", task.getException());
                                updateUI(null);
                            }
                        }
                    });
        }



//        private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
//        try {
//            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
//            prefs.edit().putString("Username", account.getGivenName()).apply();
//            Log.w(TAG, "signIn Given Name" + account.getGivenName());
//            Log.w(TAG, "signin Family Name" + account.getFamilyName());
//            Log.w(TAG, "signin Display name" + account.getDisplayName());
//            Log.w(TAG, "signin Display photo url" + account.getPhotoUrl());
//
//            // Signed in successfully, show authenticated UI.
//            updateUI(account);
//
//        } catch (ApiException e) {
//            // The ApiException status code indicates the detailed failure reason.
//            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
//        }
//    }

}