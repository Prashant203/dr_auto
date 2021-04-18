package com.example.dr_auto;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.dr_auto.Login.Register_1;
import com.example.dr_auto.databinding.ActivityMyAcountBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyAcount extends AppCompatActivity {

    ActivityMyAcountBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_acount);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        binding.backButton.setOnClickListener(v -> onBackPressed());
        binding.myProfile.setOnClickListener(v -> startActivity(new Intent(MyAcount.this, Profile.class)));
        // binding.cars.setOnClickListener(v -> startActivity(new Intent(MyAcount.this, CarInventory.class)));
        binding.History.setOnClickListener(v -> startActivity(new Intent(MyAcount.this, OrderHistory.class)));

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
            FirebaseAuth.getInstance().signOut();

            final AlertDialog.Builder alert = new AlertDialog.Builder(MyAcount.this);
            alert.setMessage("Are you sure you want to logout?");
            alert.setPositiveButton("YES", (dialog, which) -> {
                finish();
                Intent intent = new Intent(MyAcount.this, Register_1.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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