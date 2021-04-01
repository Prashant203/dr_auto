package com.example.dr_auto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.dr_auto.databinding.ActivitySpalsh3Binding;
import com.example.dr_auto.databinding.ActivitySplash2Binding;

public class spalsh3 extends AppCompatActivity {

    ActivitySpalsh3Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_spalsh3);
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(spalsh3.this, splash4.class));
            }
        });
    }
    @Override
    public void onBackPressed()
    {

    }
}