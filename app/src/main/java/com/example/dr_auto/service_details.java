package com.example.dr_auto;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.dr_auto.UserProfile.MyAcount;
import com.example.dr_auto.databinding.ActivityServiceDetailsBinding;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;

public class service_details extends AppCompatActivity {

    ActivityServiceDetailsBinding binding;

    boolean opened;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference ndatabaseReference = firebaseDatabase.getReference("Users");
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String Uid = firebaseUser.getUid();


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_service_details);

        binding.grName.setText(getIntent().getStringExtra("name"));
        binding.Location.setText(getIntent().getStringExtra("address"));

        // Toast.makeText(this,getIntent().getStringExtra("contact"),Toast.LENGTH_SHORT).show();
        binding.contact2.setText(getIntent().getStringExtra("contact"));

        ndatabaseReference.child(Uid).child("grSelectedAddress").setValue(binding.Location.getText().toString());

        String grAddress = binding.Location.getText().toString();
        String grname = binding.grName.getText().toString();

      //  goToLocationFromAddress(grname + "," + grAddress);


        binding.contact2.setOnClickListener(v -> {

            final AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Do you want to call this Garage ?");
            alert.setMessage(binding.contact2.getText().toString() + "\n" + " Or would you like to cancel?");

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

        binding.backButton.setOnClickListener(v -> onBackPressed());
        binding.settings.setOnClickListener(v -> startActivity(new Intent(service_details.this, MyAcount.class)));

        binding.sureBoxRelative.setVisibility(View.INVISIBLE);
        binding.pickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "This service is not Available yet!", Toast.LENGTH_SHORT).show();
            }

        });
        binding.drop.setOnClickListener(v -> {
            if (!opened) {
                binding.sureBoxRelative.setVisibility(View.VISIBLE);
                TranslateAnimation animate = new TranslateAnimation(
                        0,
                        0,
                        binding.sureBoxRelative.getHeight(),
                        0);
                animate.setDuration(500);
                animate.setFillAfter(true);
                binding.sureBoxRelative.startAnimation(animate);
            } else {
                binding.sureBoxRelative.setVisibility(View.INVISIBLE);
                TranslateAnimation animate = new TranslateAnimation(
                        0,
                        0,
                        0,
                        binding.sureBoxRelative.getHeight());
                animate.setDuration(500);
                animate.setFillAfter(true);
                binding.sureBoxRelative.startAnimation(animate);


            }
            opened = !opened;
        });
        binding.Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(service_details.this, setLocation.class);
                intent.putExtra("name", binding.grName.getText().toString());
                intent.putExtra("address", binding.Location.getText().toString());
                intent.putExtra("contact", binding.contact2.getText().toString());
                startActivity(intent);

            }
        });
        binding.no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.sureBoxRelative.setVisibility(View.INVISIBLE);
                TranslateAnimation animate = new TranslateAnimation(
                        0,
                        0,
                        0,
                        binding.sureBoxRelative.getHeight());
                animate.setDuration(500);
                animate.setFillAfter(true);
                binding.sureBoxRelative.startAnimation(animate);
                binding.sureBoxRelative.clearAnimation();
                opened = !opened;
            }
        });
    }

    public void goToLocationFromAddress(String strAddress) {
        //Create coder with Activity context - this
        Geocoder coder = new Geocoder(this);
        List<Address> address;

        try {
            //Get latLng from String
            address = coder.getFromLocationName(strAddress, 5);

            //check for null
            if (address != null) {

                //Lets take first possibility from the all possibilities.
                try {
                    Address location = address.get(0);
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    String longitude = String.valueOf(latLng.longitude);
                    String latitude = String.valueOf(latLng.latitude);

                    ndatabaseReference.child(Uid).child("grSelectedAddressLong").setValue(longitude);
                    ndatabaseReference.child(Uid).child("grSelectedAddressLatt").setValue(latitude);


                } catch (IndexOutOfBoundsException er) {
                    Toast.makeText(this, "Location isn't available", Toast.LENGTH_SHORT).show();
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}