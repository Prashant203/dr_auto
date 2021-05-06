package com.example.dr_auto.Login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.dr_auto.R;
import com.example.dr_auto.databinding.ActivityRegister1Binding;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class Register_1 extends AppCompatActivity {

    ActivityRegister1Binding binding;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_1);
        String uid = getIntent().getStringExtra("uid");
        System.out.println("---------------" + uid);
        databaseReference = firebaseDatabase.getReference("Users");
        System.out.println();
        databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String phone = snapshot.child("phone").getValue(String.class);
                binding.phoneNumber.setText(phone.substring(4));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        binding.buttonVerifyPhone.setOnClickListener(v ->
                loginUser());


        binding.CreateNew.setOnClickListener(v -> startActivity(new Intent(Register_1.this, Login.class)));
    }

    private void loginUser() {

        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + binding.phoneNumber.getText().toString(), 30L, TimeUnit.SECONDS,
                Register_1.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onCodeSent(@NonNull String VerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

//
                        Intent intent = new Intent(getApplicationContext(), OTP2.class);
                        intent.putExtra("Mobile", binding.phoneNumber.getText().toString());
                        intent.putExtra("VerificationId", VerificationId);
                        startActivity(intent);

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {


                        binding.buttonVerifyPhone.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }

}