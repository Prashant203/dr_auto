package com.example.dr_auto;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.example.dr_auto.Adapter.ViewPagerAdapter;
import com.example.dr_auto.databinding.ActivityMainBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    ActivityMainBinding binding;
    FirebaseAuth firebaseAuth;

    private static final int REQUEST_CODE = 101;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    DatabaseReference databaseReference;
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String uid;

    {
        assert firebaseUser != null;
        uid = firebaseUser.getUid();
    }

    @Override
    protected void onStart() {
        super.onStart();


        databaseReference = firebaseDatabase.getReference("Users");
        System.out.println(uid);
        databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String name = snapshot.child("name").getValue(String.class);
                binding.name.setText(name);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLocation();


        viewPager = findViewById(R.id.viewPager);

        sliderDotspanel = findViewById(R.id.SliderDots);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);

        binding.settings.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MyAcount.class)));
        // binding.addButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CarInventory.class)));
        binding.cardView.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ServiceList.class)));


        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.deactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.deactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            currentLocation = location;
            //  Toast.makeText(getApplicationContext(), currentLocation.getLatitude() + " " + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
            String add = getAddress(this, currentLocation.getLatitude(), currentLocation.getLongitude());
            binding.Location.setText(add);

            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(uid);
            mDatabase.child("Address").setValue(add);


        });
    }

    public String getAddress(Context ctx, double lat, double lng) {
        String fullAdd = null;
        String Location;
        try {
            Geocoder geocoder = new Geocoder(ctx, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);


                String l = address.getSubLocality();
                Location = address.getLocality();
                String Location1 = address.getSubAdminArea();
                String state = address.getAdminArea();
                String zip = address.getPostalCode();
                //String Country = address.getCountryName();
                if (l != null) {
                    fullAdd = l + ", " + Location + ", " + Location1 + ", \n" + state + ", " + zip;
                } else {
                    fullAdd = Location + ", " + Location1 + ", \n" + state + ", " + zip;
                }

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return fullAdd;

    }


    // must declare methods //

  /*  public void onStart(){
        mGoogleApiClient.connect();
        super.onStart();
        if(mGoogleApiClient.isConnected()){
            fetchLocation();
        }
    }
    public void onStop(){
        mGoogleApiClient.disconnect();
        stopLocationUpdate();
        super.onStop();
    }
    public void onPause(){
        mGoogleApiClient.disconnect();
        stopLocationUpdate();
        super.onPause();

    }
    public void onResume(){
        mGoogleApiClient.connect();
        super.onResume();
        if(mGoogleApiClient.isConnected()){
            fetchLocation();
        }
    }

    protected  void stopLocationUpdate(){
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (LocationListener) this);
    }

    // Must Declare LocatonListener Methods //
    public void onLocationChanged(Location location){
        if(location!=null){
            lat = location.getLatitude();
            lng = location.getLongitude();
        }
    }*/

}