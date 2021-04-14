package com.example.dr_auto.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.dr_auto.Login.Login;
import com.example.dr_auto.R;
import com.example.dr_auto.databinding.ActivitySplash4Binding;

public class splash4 extends AppCompatActivity {

    ActivitySplash4Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash4);
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(splash4.this, Login.class));
            }
        });
    }
    @Override
    public void onBackPressed()
    {

    }
}