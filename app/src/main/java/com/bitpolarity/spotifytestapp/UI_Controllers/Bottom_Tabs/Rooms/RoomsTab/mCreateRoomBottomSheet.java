package com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Rooms.RoomsTab;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Toast;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import com.bitpolarity.spotifytestapp.DB_Handler;
import com.bitpolarity.spotifytestapp.R;
import com.bitpolarity.spotifytestapp.UI_Controllers.Bottom_Tabs.Rooms.RoomHolder.MainHolder.RoomHolderActivity;
import com.bitpolarity.spotifytestapp.databinding.ActivityRoomStarterBinding;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;


public class mCreateRoomBottomSheet extends BottomSheetDialogFragment {
    AppCompatButton startRoom;
    DB_Handler db_handler;
    TextInputEditText mRoomNameEditText;
    ActivityRoomStarterBinding binding;
    boolean photoHasSet = false;
    private Uri imgURI;
    private static final int PICK_IMAGE_REQUEST = 1;

    StorageReference mstorageRef;


    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db_handler = new DB_Handler();
        photoHasSet = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityRoomStarterBinding.inflate(inflater, container, false);

        startRoom = binding.startMainRoomBTN;
        mRoomNameEditText = binding.editTextROOM;
        mstorageRef = FirebaseStorage.getInstance().getReference(DB_Handler.getUsername());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startRoom.setOnClickListener(view1 -> {
            uploadDP(imgURI);
        });

        binding.include.cancelRoomCreation.setOnClickListener(view12 -> {
            dismiss();
        });


        binding.selectPhoto.setOnClickListener(view13 -> {
            selectPhotos();
        });
    }

    void selectPhotos() {
        showFileSelecter();
    }


    private String getFileExtension(Uri uri) {
        ContentResolver cr = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

//    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new
//            ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
//                if (result.getResultCode() == Activity.RESULT_OK ) {
//                    Intent data = result.getData();
//                }
//            });


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            photoHasSet = true;
            imgURI = data.getData();
            Log.d("BottomSHeet", "onActivityResult: URI" + imgURI.toString());
            Picasso.with(getContext()).load(imgURI).into(binding.roomDisplay);
            binding.selectPhoto.setVisibility(View.GONE);

        } else {
            photoHasSet = false;
        }
    }

    void showFileSelecter() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }

    @Override
    public void onResume() {
        super.onResume();
    }


    void uploadRoomData(String url, String roomName, int sizeKB) {



            db_handler.CreateRoom(roomName, url);

            Toast.makeText(getContext(), "Room created", Toast.LENGTH_SHORT).show();
            Log.d("Bottom", "onCreateView: " + "Room created");
            Intent i = new Intent(getContext(), RoomHolderActivity.class);
            i.putExtra("room_name", roomName);
        i.putExtra("room_profile_url",url);

            dismiss();
            startActivity(i);

    }

    void uploadDP(Uri imgURI) {

        String roomName = mRoomNameEditText.getText().toString();
        StorageReference fileRef = mstorageRef.child("ROOMS").child(roomName).child("DP").child("roomDP." + getFileExtension(imgURI));

        int sizekb = (int)fileRef.putFile(imgURI).getSnapshot().getTotalByteCount()/1024;

        if (roomName.length() > 0 && roomName.length() < 15) {

            if (imgURI != null) {

                if (sizekb <= 1024) {

                    fileRef.putFile(imgURI).addOnSuccessListener((UploadTask.TaskSnapshot taskSnapshot) -> {
                        fileRef.getDownloadUrl().addOnSuccessListener(uri -> {

                            String URL = uri.toString();
                            Toast.makeText(getContext(), "Url " + URL, Toast.LENGTH_SHORT).show();
                            uploadRoomData(URL, roomName, sizekb);

                            binding.progress.setVisibility(View.GONE);

                        }).addOnFailureListener(e -> {
                            binding.progress.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "Url Fetching failed!", Toast.LENGTH_SHORT).show();
                        });

                    })
                        .addOnFailureListener(e -> {
                                    Toast.makeText(getContext(), "DP uploading failed , retry!", Toast.LENGTH_SHORT).show();
                                }

                        ).addOnProgressListener(snapshot -> {

                            binding.progress.setVisibility(View.VISIBLE);
                            double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            binding.progress.setProgress((int) progress);

                        });

                }
                else{
                    Toast.makeText(getContext(), "Select photos lesser than 1 mb", Toast.LENGTH_SHORT).show();
                    binding.selectPhoto.setVisibility(View.VISIBLE);
                }
            }
            else {
                Toast.makeText(getContext(), "Select photo first!", Toast.LENGTH_SHORT).show();
            }
        }else {
            mRoomNameEditText.setError("Com'mon! give a dope name, make sure its less than 15 letters!");
        }

    }

    }




