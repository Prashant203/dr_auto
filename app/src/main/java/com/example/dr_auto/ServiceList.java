package com.example.dr_auto;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dr_auto.Adapter.ItemArrayAdapter;
import com.example.dr_auto.databinding.ActivityServiceListBinding;
import com.example.dr_auto.db.Item;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ServiceList extends AppCompatActivity {

    RecyclerView recyclerView;
    FusedLocationProviderClient fusedLocationProviderClient;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference("Users");
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String uid;
    private ActivityServiceListBinding binding;

    {
        assert firebaseUser != null;
        uid = firebaseUser.getUid();
    }

    @Override
    protected void onStart() {
        super.onStart();

        System.out.println(uid);
        databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String Location = snapshot.child("Address").getValue(String.class);
                String name = snapshot.child("name").getValue(String.class);
                binding.name.setText(name);
                binding.Location.setText(Location);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
        binding.settings.setOnClickListener(v -> startActivity(new Intent(ServiceList.this, MyAcount.class)));


        binding.backButton.setOnClickListener(v -> onBackPressed());

        ArrayList<Item> itemList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        itemList.add(new Item("SAW Automobiles", "Medra Village Rd, Valad, Gujarat 382355"));
        itemList.add(new Item("SAW Automobiles", "Medra Village Rd, Valad, Gujarat 382355"));
        itemList.add(new Item("SAW Automobiles", "Medra Village Rd, Valad, Gujarat 382355"));
        itemList.add(new Item("SAW Automobiles", "Medra Village Rd, Valad, Gujarat 382355"));
        itemList.add(new Item("SAW Automobiles", "Medra Village Rd, Valad, Gujarat 382355"));
        ItemArrayAdapter arrayAdapter = new ItemArrayAdapter(itemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(ServiceList.this));
        recyclerView.setAdapter(arrayAdapter);


        arrayAdapter.notifyDataSetChanged();
    }

}


