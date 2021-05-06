package com.example.dr_auto.UserProfile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.dr_auto.Login.Register_1;
import com.example.dr_auto.OrderHistory;
import com.example.dr_auto.R;
import com.example.dr_auto.databinding.ActivityMyAcountBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyAcount extends AppCompatActivity {

    ActivityMyAcountBinding binding;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    String uid = firebaseUser.getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_acount);

        databaseReference = firebaseDatabase.getReference("Users");
        System.out.println(uid);
        databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String phone = snapshot.child("phone").getValue(String.class);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        binding.backButton.setOnClickListener(v -> onBackPressed());
        binding.myProfile.setOnClickListener(v -> startActivity(new Intent(MyAcount.this, Profile.class)));
        // binding.cars.setOnClickListener(v -> startActivity(new Intent(MyAcount.this, CarInventory.class)));
        binding.History.setOnClickListener(v -> startActivity(new Intent(MyAcount.this, OrderHistory.class)));

        binding.cars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyAcount.this, Garages.class));
            }
        });
        binding.Share.setOnClickListener(v -> {

            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Your body here";
            String shareSub = "Your subject here";
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share using"));
        });

        binding.Logout.setOnClickListener(v -> {


            final AlertDialog.Builder alert = new AlertDialog.Builder(MyAcount.this);
            alert.setMessage("Are you sure you want to logout?");
            alert.setPositiveButton("YES", (dialog, which) -> {
                finish();
                Intent intent = new Intent(MyAcount.this, Register_1.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("uid", uid);
                System.out.println(uid);
                FirebaseAuth.getInstance().signOut();
                startActivity(intent);
            });
            alert.setNegativeButton("NO", (dialog, which) -> {
                // do nothing
            });

            AlertDialog alertDialog = alert.create();
            alertDialog.show();

        });
    }
}