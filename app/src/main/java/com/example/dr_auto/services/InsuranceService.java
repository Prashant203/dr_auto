package com.example.dr_auto.services;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.dr_auto.Adapter.ItemArrayAdapter;
import com.example.dr_auto.GarageDetails;
import com.example.dr_auto.R;
import com.example.dr_auto.UserProfile.MyAcount;
import com.example.dr_auto.databinding.ActivityServiceListBinding;
import com.example.dr_auto.db.Item;
import com.example.dr_auto.db.Pin;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InsuranceService extends AppCompatActivity implements ItemArrayAdapter.ServiceProviderListener {

    private final Pin pin1 = new Pin();
    RecyclerView recyclerView;
    FusedLocationProviderClient fusedLocationProviderClient;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference("Users");
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String uid;
    ItemArrayAdapter arrayAdapter;
    ArrayList<Item> itemList;
    double latitude, longitude;
    Location currentLocation, destination;
    private ActivityServiceListBinding binding;

    {
        assert firebaseUser != null;
        uid = firebaseUser.getUid();
    }

    @Override
    protected void onStart() {
        super.onStart();

        String url = "http://192.168.42.208:9192/api/service-providers/get-service-providers/";


        getServiceProviders(url);

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

        binding.Location.setText(getIntent().getStringExtra("Address"));
        binding.Location.setText(getIntent().getStringExtra("Address"));
        goToLocationFromAddress(binding.Location.getText().toString());
        System.out.println("Home" + latitude + "-----------" + longitude);
        double currentLat = latitude;
        double currentLong = longitude;
        currentLocation = new Location("locationA");
        currentLocation.setLatitude(currentLat);
        currentLocation.setLongitude(currentLong);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_service_list);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        recyclerView = findViewById(R.id.rvData);
        /*binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ServiceList.this, MyAcount.class));
            }
        });*/
        binding.settings.setOnClickListener(v -> startActivity(new Intent(InsuranceService.this, MyAcount.class)));


        binding.backButton.setOnClickListener(v -> onBackPressed());

        itemList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


       /* itemList.add(new Item("SAW Automobiles", "Medra Village Rd, Valad, Gujarat 382355"));
        itemList.add(new Item("SAW Automobiles", "Medra Village Rd, Valad, Gujarat 382355"));
        itemList.add(new Item("SAW Automobiles", "Medra Village Rd, Valad, Gujarat 382355"));
        itemList.add(new Item("SAW Automobiles", "Medra Village Rd, Valad, Gujarat 382355"));
        itemList.add(new Item("SAW Automobiles", "Medra Village Rd, Valad, Gujarat 382355"));*/


        DatabaseReference ndatabaseReference = firebaseDatabase.getReference("Users");
        ndatabaseReference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String pin = (Objects.requireNonNull(snapshot.child("pin").getValue())).toString();
                String getSubAdminArea = (Objects.requireNonNull(snapshot.child("city").getValue())).toString();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void getServiceProviders(String url) {
        //String url = "http://192.168.42.208:9192/api/service-providers/get-service-provider/"+city+"/"+pin;

        itemList.clear();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    //textView.setText("Response: " + response.toString());
                    JSONArray garages = null;
                    try {
                        garages = response.getJSONArray("obj");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println(response);
                    for (int i = 0; i < garages.length(); i++) {
                        // creating a new json object and
                        // getting each object from our json array.
                        try {
                            // we are getting each json object.
                            JSONObject responseObj = garages.getJSONObject(i);

                            String name = responseObj.getString("name");
                            String street = responseObj.getString("street");
                            String landmark = responseObj.getString("landmark");
                            String area = responseObj.getString("area");
                            int pincode = responseObj.getInt("pincode");
                            long contact = responseObj.getLong("contact");
                            int garageId = responseObj.getInt("garageId");

                            goToLocationFromAddress(binding.Location.getText().toString());
                            // System.out.println("Home" + latitude + "-----------" + longitude);
                            double currentLat = latitude;
                            double currentLong = longitude;
                            Location currentLocation = new Location("locationA");
                            currentLocation.setLatitude(currentLat);
                            currentLocation.setLongitude(currentLong);


                            goToLocationFromAddress(name + "," + street + "," + landmark + "," + area + "," + pincode);
                            double newLat = latitude;
                            double newLong = longitude;
                            destination = new Location("locationB");
                            destination.setLatitude(newLat);
                            destination.setLongitude(newLong);
                            double distance = currentLocation.distanceTo(destination);

                            //      System.out.println("distance--" + i + "-----" + String.format("%.2f", distance / 1000) + "km");
                            @SuppressLint("DefaultLocale")
                            String dist = String.format("%.2f", distance / 1000);
                            itemList.add(new Item(garageId, name, street, area, landmark, pincode, contact, dist));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if (itemList.size() > 0) {
                        binding.progressbar.setVisibility(View.GONE);
                        itemList.sort((o1, o2) -> o1.getFormat().compareTo(o2.getFormat()));

                        arrayAdapter = new ItemArrayAdapter(itemList, InsuranceService.this);
                        recyclerView.setLayoutManager(new LinearLayoutManager(InsuranceService.this));
                        recyclerView.setAdapter(arrayAdapter);
                        arrayAdapter.notifyDataSetChanged();
                        System.out.println(itemList);
                    }

                }, error -> {
                    // TODO: Handle error

                });
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }


    @Override
    public void onProviderClick(int postion) {
        System.out.println(itemList.get(postion));
        Intent intent = new Intent(getApplicationContext(), GarageDetails.class);
        intent.putExtra("name", itemList.get(postion).getName());
        intent.putExtra("address", itemList.get(postion).getStreet() + ", " + itemList.get(postion).getArea() + ", " + itemList.get(postion).getLandmark() + ", " +
                itemList.get(postion).getPincode());
        intent.putExtra("contact", "+91" + itemList.get(postion).getContact());

        startActivity(intent);


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
                    longitude = latLng.longitude;
                    latitude = latLng.latitude;


                   /* ndatabaseReference.child(Uid).child("grSelectedAddressLong").setValue(longitude);
                    ndatabaseReference.child(Uid).child("grSelectedAddressLatt").setValue(latitude);
*/

                } catch (IndexOutOfBoundsException er) {
                    Toast.makeText(this, "Location isn't available", Toast.LENGTH_SHORT).show();
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


