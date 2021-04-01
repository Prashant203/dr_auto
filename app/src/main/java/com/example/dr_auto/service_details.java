package com.example.dr_auto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.dr_auto.databinding.ActivityServiceDetailsBinding;

public class service_details extends AppCompatActivity {

    ActivityServiceDetailsBinding binding;

    boolean opened;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_service_details);

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onBackPressed();
            }
        });
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(service_details.this,CarInventory.class));

            }
        });
        
        binding.sureBoxRelative.setVisibility(View.INVISIBLE);
        binding.pickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "This service is not Available yet!", Toast.LENGTH_SHORT).show();
            }

        });
        binding.drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!opened){
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
            }

        });
        binding.Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(service_details.this,setLocation.class));

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
}