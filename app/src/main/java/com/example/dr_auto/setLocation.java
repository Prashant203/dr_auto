package com.example.dr_auto;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.dr_auto.databinding.ActivitySetLocationBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class setLocation extends AppCompatActivity {

    ActivitySetLocationBinding binding;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference("Users");
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String uid = firebaseUser.getUid();

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String street = snapshot.child("street").getValue(String.class);
                String area = snapshot.child("area").getValue(String.class);
                String city = snapshot.child("city").getValue(String.class);
                String pin = snapshot.child("pin").getValue(String.class);
                String state = snapshot.child("state").getValue(String.class);

                binding.street.setText(street);
                binding.street.setSelection(binding.street.length());
                binding.area.setText(area);
                binding.city.setText(city);
                binding.state.setText(state);
                binding.pin.setText(pin);
                binding.area.setSelection(binding.area.length());
                binding.city.setSelection(binding.city.length());
                binding.state.setSelection(binding.state.length());
                binding.pin.setSelection(binding.pin.length());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_location);

        binding.grName.setText(getIntent().getStringExtra("name"));
        binding.Location.setText(getIntent().getStringExtra("address"));
        // Toast.makeText(this,getIntent().getStringExtra("contact"),Toast.LENGTH_SHORT).show();
        binding.contact2.setText(getIntent().getStringExtra("contact"));
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

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference.child(uid).child("Address").setValue(binding.street.getText().toString() + "," + binding.area.getText().toString() + "," +
                        binding.city.getText().toString() + "," + binding.state.getText().toString() + "," + binding.pin.getText().toString());

                String sourceAddress = binding.street.getText().toString() + "," + binding.area.getText().toString() + "," +
                        binding.city.getText().toString() + "," + binding.state.getText().toString() + "," + binding.pin.getText().toString();

                String destiAddress = binding.grName.getText().toString() + "," + binding.Location.getText().toString();


                System.out.println(sourceAddress + "-----------" + destiAddress);

                if (TextUtils.isEmpty(sourceAddress)) {
                    binding.Location.setError("Enter Soruce point");
                } else if (TextUtils.isEmpty(destiAddress)) {
                    binding.Location.setError("Enter Destination Point");
                } else {
                    String sendstring = "http://maps.google.com/maps?saddr=" +
                            sourceAddress +
                            "&daddr=" +
                            destiAddress;
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse(sendstring));
                    startActivity(intent);
                }

              /*  databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String address = snapshot.child("Address").getValue(String.class);

                        goToLocationFromAddress(address);

                        String lattitude = snapshot.child("latt").getValue(String.class);
                        String longitude = snapshot.child("long").getValue(String.class);
                        String grlongitude = snapshot.child("grSelectedAddressLong").getValue(String.class);
                        String grLattitude = snapshot.child("grSelectedAddressLatt").getValue(String.class);

                        System.out.println("d----"+lattitude+"---"+longitude+"--"+grLattitude+"----"+grlongitude);



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });*/
       /* Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr=" + lattitude + "," + longitude + "&daddr=" + grLattitude + "," + grlongitude));
        startActivity(intent);
        finish();*/
            }
        });

   /* public void goToLocationFromAddress(String strAddress) {
        //Create coder with Activity context - this
        Geocoder coder = new Geocoder(setLocation.this);
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
                    String longitude =String.valueOf(latLng.longitude);
                    String latitude =String.valueOf(latLng.latitude);

                    databaseReference.child(uid).child("long").setValue(longitude);
                    databaseReference.child(uid).child("latt").setValue(latitude);




                } catch (IndexOutOfBoundsException er) {
                    Toast.makeText(setLocation.this, "Location isn't available", Toast.LENGTH_SHORT).show();
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}