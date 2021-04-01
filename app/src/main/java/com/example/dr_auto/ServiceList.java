package com.example.dr_auto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dr_auto.databinding.ActivityServiceListBinding;

import java.util.ArrayList;

public class ServiceList extends AppCompatActivity {

    RecyclerView recyclerView;
   private ActivityServiceListBinding binding;
   Button addB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_service_list);
        recyclerView = findViewById(R.id.rvData);
        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ServiceList.this, MyAcount.class));
            }
        });
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ServiceList.this, CarInventory.class));
            }
        });


        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             onBackPressed();
            }
        });

        ArrayList<Item> itemList = new ArrayList<Item>();
        itemList.add(new Item("SAW Automobiles","₹ 3,299","Every 5000 km/3 Months","Car Service","Takes 4 Hrs"));
        itemList.add(new Item("SAW Automobiles","₹ 3,299","Every 5000 km/3 Months","Car Service","Takes 4 Hrs"));
        itemList.add(new Item("SAW Automobiles","₹ 3,299","Every 5000 km/3 Months","Car Service","Takes 4 Hrs"));
        itemList.add(new Item("SAW Automobiles","₹ 3,299","Every 5000 km/3 Months","Car Service","Takes 4 Hrs"));
        itemList.add(new Item("SAW Automobiles","₹ 3,299","Every 5000 km/3 Months","Car Service","Takes 4 Hrs"));
        itemList.add(new Item("SAW Automobiles","₹ 3,299","Every 5000 km/3 Months","Car Service","Takes 4 Hrs"));
        itemList.add(new Item("SAW Automobiles","₹ 3,299","Every 5000 km/3 Months","Car Service","Takes 4 Hrs"));
        itemList.add(new Item("SAW Automobiles","₹ 3,299","Every 5000 km/3 Months","Car Service","Takes 4 Hrs"));
        itemList.add(new Item("SAW Automobiles","₹ 3,299","Every 5000 km/3 Months","Car Service","Takes 4 Hrs"));
        itemList.add(new Item("SAW Automobiles","₹ 3,299","Every 5000 km/3 Months","Car Service","Takes 4 Hrs"));
        itemList.add(new Item("SAW Automobiles","₹ 3,299","Every 5000 km/3 Months","Car Service","Takes 4 Hrs"));
        itemList.add(new Item("SAW Automobiles","₹ 3,299","Every 5000 km/3 Months","Car Service","Takes 4 Hrs"));
        itemList.add(new Item("SAW Automobiles","₹ 3,299","Every 5000 km/3 Months","Car Service","Takes 4 Hrs"));
        itemList.add(new Item("SAW Automobiles","₹ 3,299","Every 5000 km/3 Months","Car Service","Takes 4 Hrs"));
        itemList.add(new Item("SAW Automobiles","₹ 3,299","Every 5000 km/3 Months","Car Service","Takes 4 Hrs"));
        itemList.add(new Item("SAW Automobiles","₹ 3,299","Every 5000 km/3 Months","Car Service","Takes 4 Hrs"));

        ItemArrayAdapter arrayAdapter=new ItemArrayAdapter(itemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(ServiceList.this));
        recyclerView.setAdapter(arrayAdapter);



    }
}