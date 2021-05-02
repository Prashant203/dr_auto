package com.example.dr_auto;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.dr_auto.databinding.ActivityProfileBinding;
import com.example.dr_auto.db.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    ActivityProfileBinding binding;

    private static final String TAG = "dataBase";
    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;
    FirebaseUser user;
    String uid;
    User userInfo;
    String phoneNoFromDB;
    String nameNoFromDB, emailFromDB;
    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        String uid = firebaseUser.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        System.out.println(uid);
        databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String name = snapshot.child("name").getValue(String.class);
                String email = snapshot.child("email").getValue(String.class);
                String phone = snapshot.child("phone").getValue(String.class);

                binding.name.setText(name);
                binding.emailText.setText(email);
                binding.mobileNo.setText(phone);
                binding.emailText.setEnabled(false);
                binding.mobileNo.setEnabled(false);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);


        //  binding.mobileNo.setText("+91 " + getIntent().getStringExtra("Mobile"));

        // when iv is clicked
        /*binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });*/
        binding.backButton.setOnClickListener(v -> onBackPressed());
        binding.saveButton.setOnClickListener(v -> {
            String name = binding.name.getText().toString();

            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            assert firebaseUser != null;
            String uid = firebaseUser.getUid();
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(uid);
            mDatabase.child("name").setValue(name);

            Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();

        });


    }

    @SuppressLint("IntentReset")
    private void selectImage() {
        final CharSequence[] items = {"Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
        builder.setTitle("Add Image");
        builder.setItems(items, (dialog, which) -> {

            if (items[which].equals("Gallery")) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);

            } else if (items[which].equals("Cancel")) {
                dialog.dismiss();
            }

        });
        builder.show();

    }

  /*  @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                binding.profileImage.setImageURI(selectedImageUri);
            }
        }
    }*/
}