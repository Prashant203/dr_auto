package com.example.dr_auto.UserProfile;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.dr_auto.R;
import com.example.dr_auto.databinding.ActivityGaragesBinding;
import com.example.dr_auto.servers.ServiceProviderAPI;

public class Garages extends AppCompatActivity {

    ActivityGaragesBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_garages);


        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Name = binding.name.getText().toString();
                String area = binding.area.getText().toString();
                String Street = binding.street.getText().toString();
                String Landmark = binding.landmark.getText().toString();
                int pin = Integer.parseInt(binding.pin.getText().toString());
                long contact = Long.parseLong(binding.contact.getText().toString());

                ServiceProviderAPI.postDataUsingVolley(Garages.this, Name, Street, area, Landmark, pin, contact);

            }
        });

    }




}