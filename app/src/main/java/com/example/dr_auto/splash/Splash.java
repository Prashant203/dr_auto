package com.example.dr_auto.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dr_auto.Login.Login;
import com.example.dr_auto.MainActivity;
import com.example.dr_auto.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Splash extends AppCompatActivity {

    long SPLASH_TIME_OUT = 3000;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
         final ProgressBar progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.INVISIBLE);

        firebaseAuth = FirebaseAuth.getInstance();


        progressBar.postDelayed(new Runnable() {
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        }, 2000);
    }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null){
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    Intent intent=new Intent(Splash.this, MainActivity.class);
                    Splash.this.startActivity(intent);
                    finish();

                }
            }, SPLASH_TIME_OUT);

        }else {
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    startActivity(new Intent(Splash.this, Login.class));

                    finish();

                }
            }, SPLASH_TIME_OUT);
        }
    }
}