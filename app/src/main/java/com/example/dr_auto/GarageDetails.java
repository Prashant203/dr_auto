package com.example.dr_auto;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.dr_auto.UserProfile.MyAcount;
import com.example.dr_auto.databinding.ActivityGarageDetailsBinding;

public class GarageDetails extends AppCompatActivity {
    ActivityGarageDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_garage_details);

        binding.grName.setText(getIntent().getStringExtra("name"));
        binding.address.setText(getIntent().getStringExtra("address"));

        // Toast.makeText(this,getIntent().getStringExtra("contact"),Toast.LENGTH_SHORT).show();
        binding.phone.setText(getIntent().getStringExtra("contact"));


        binding.callButton.setOnClickListener(v -> {

            final AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Do you want to call this Garage ?");
            alert.setMessage(binding.phone.getText().toString() + "\n" + " Or would you like to cancel?");

            alert.setPositiveButton("YES", (dialog, which) -> {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + getIntent().getStringExtra("contact")));
                startActivity(intent);

            });
            alert.setNegativeButton("NO", (dialog, which) -> {
                // do nothing
            });

            AlertDialog alertDialog = alert.create();
            alertDialog.show();
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GarageDetails.this, MyAcount.class));
            }
        });


    }
}