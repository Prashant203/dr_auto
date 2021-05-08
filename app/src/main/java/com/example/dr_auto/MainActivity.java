package com.example.dr_auto;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.example.dr_auto.Adapter.ViewPagerAdapter;
import com.example.dr_auto.UserProfile.MyAcount;
import com.example.dr_auto.databinding.ActivityMainBinding;
import com.example.dr_auto.db.UserAddress;
import com.example.dr_auto.services.BreakDownList;
import com.example.dr_auto.services.ServiceList;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    ActivityMainBinding binding;
    FirebaseAuth firebaseAuth;
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    private static final int REQUEST_CODE = 101;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    DatabaseReference databaseReference;
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String uid;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Name = "nameKey";
    SharedPreferences sharedpreferences;
    String text;
    LocationManager locationManager;
    LocationListener locationListener;
    private UserAddress userAddress;
    private Intent intent;

    {
        assert firebaseUser != null;
        uid = firebaseUser.getUid();
    }

    @Override
    protected void onStart() {
        super.onStart();

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        databaseReference = firebaseDatabase.getReference("Users");
        System.out.println(uid);
        databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String name = snapshot.child("name").getValue(String.class);
                SharedPreferences.Editor editor = sharedpreferences.edit();

                binding.name.setText(name);
                editor.putString(Name, binding.name.getText().toString());
                editor.apply();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        update();


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {

                updateLocation(location);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {


            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 200, locationListener);

            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            updateLocation(lastKnownLocation);

        } else {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }


    }

    private void update() {
        SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        text = sharedPreferences.getString(Name, "");
        binding.name.setText(text);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        viewPager = findViewById(R.id.viewPager);

        sliderDotspanel = findViewById(R.id.SliderDots);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);

        binding.settings.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MyAcount.class)));
        // binding.addButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CarInventory.class)));
        binding.ServiceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), ServiceList.class);
                intent.putExtra("Address", binding.Location.getText().toString());


                startActivity(intent);

            }
        });
        binding.tyresView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), ServiceList.class);
                intent.putExtra("Address", binding.Location.getText().toString());


                startActivity(intent);

            }
        });
        binding.acView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), ServiceList.class);
                intent.putExtra("Address", binding.Location.getText().toString());


                startActivity(intent);

            }
        });

        binding.cleaningView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), ServiceList.class);
                intent.putExtra("Address", binding.Location.getText().toString());


                startActivity(intent);

            }
        });

        binding.dentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), ServiceList.class);
                intent.putExtra("Address", binding.Location.getText().toString());


                startActivity(intent);

            }
        });
        binding.betteriesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), ServiceList.class);
                intent.putExtra("Address", binding.Location.getText().toString());


                startActivity(intent);

            }
        });
        binding.insuranceVIew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), ServiceList.class);
                intent.putExtra("Address", binding.Location.getText().toString());


                startActivity(intent);

            }
        });
        binding.lightsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), ServiceList.class);
                intent.putExtra("Address", binding.Location.getText().toString());


                startActivity(intent);

            }
        });
        binding.breakdownView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), BreakDownList.class);
                intent.putExtra("Address", binding.Location.getText().toString());


                startActivity(intent);

            }
        });


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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_FINE);
                criteria.setPowerRequirement(Criteria.POWER_HIGH);
                //API level 9 and up
                criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
                criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);


                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 200, locationListener);
            }
        }
    }


    public void updateLocation(Location location) {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> listAddresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);


            String address = "Could not find location :(";

            if (listAddresses != null && listAddresses.size() > 0) {

                if (listAddresses.get(0).getThoroughfare() != null) {

                    address = listAddresses.get(0).getThoroughfare() + ", ";
                    mDatabase.child(uid).child("street").setValue(address);
                }

                if (listAddresses.get(0).getLocality() != null) {

                    address += listAddresses.get(0).getLocality() + ", ";
                    mDatabase.child(uid).child("area").setValue(listAddresses.get(0).getLocality());
                }
                if (listAddresses.get(0).getSubAdminArea() != null) {

                    address += listAddresses.get(0).getSubAdminArea() + ", \n";
                    mDatabase.child(uid).child("city").setValue(listAddresses.get(0).getSubAdminArea());
                }
                if (listAddresses.get(0).getPostalCode() != null) {

                    address += listAddresses.get(0).getPostalCode() + ", ";
                    mDatabase.child(uid).child("pin").setValue(listAddresses.get(0).getPostalCode());

                }


                if (listAddresses.get(0).getAdminArea() != null) {

                    address += listAddresses.get(0).getAdminArea();
                    mDatabase.child(uid).child("state").setValue(listAddresses.get(0).getAdminArea());
                }

            }

            /*databaseReference.child(uid).child("Address").setValue(address);
            mDatabase.child(uid).child("Address").setValue(address);*/

            String latt = String.valueOf(location.getLatitude());
            String longitude = String.valueOf(location.getLongitude());

            mDatabase.child(uid).child("latt").setValue(latt);
            mDatabase.child(uid).child("long").setValue(longitude);

            binding.Location.setText(address);

            Log.i("Address", address);

        } catch (Exception e) {

            e.printStackTrace();

        }


    }

    @Override
    public void onBackPressed() {
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(getBaseContext(), "Press once again to exit!",
                    Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }

   /* private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            currentLocation = location;
            if (currentLocation != null) {
                //  Toast.makeText(getApplicationContext(), currentLocation.getLatitude() + " " + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                UserAddress add = getAddress(this, currentLocation.getLatitude(), currentLocation.getLongitude());
                binding.Location.setText(add.toString());

                *//*DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");
                mDatabase.child(uid).child("Address").setValue(add.toString());
                mDatabase.child("separateAddress").child(uid).child("street").setValue(""+add.getSubLocality());
                mDatabase.child("separateAddress").child(uid).child("area").setValue(add.getLocality());
                mDatabase.child("separateAddress").child(uid).child("city").setValue(add.getSubAdminArea());
                mDatabase.child("separateAddress").child(uid).child("state").setValue(add.getAdminArea());
                mDatabase.child("separateAddress").child(uid).child("pin").setValue(add.getZip());
*//*




            }

        });
    }

    public UserAddress getAddress(Context ctx, double lat, double lng) {
        String fullAdd = null;
        String Location;
        try {
            Geocoder geocoder = new Geocoder(ctx, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);

                String address23 = addresses.get(0).getAddressLine(0);
                String l = address.getSubLocality();
                Location = address.getLocality();
                String Location1 = address.getSubAdminArea();
                String state = address.getAdminArea();
                String zip = address.getPostalCode();
                //String Country = address.getCountryName();
                userAddress = new UserAddress(l,Location,Location1,state,zip);

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return userAddress;

    }

*/
    // must declare methods //

}