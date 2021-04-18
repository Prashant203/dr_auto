package com.example.dr_auto.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.dr_auto.R;
import com.example.dr_auto.databinding.ActivityRegister1Binding;
import com.example.dr_auto.db.User;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register_1 extends AppCompatActivity {

    ActivityRegister1Binding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_1);


        binding.buttonVerifyPhone.setOnClickListener(v ->
                loginUser());


        binding.CreateNew.setOnClickListener(v -> startActivity(new Intent(Register_1.this, Login.class)));
    }

    private void loginUser() {


        if (binding.mobilenumber.getText().toString().trim().isEmpty()) {
            binding.mobilenumber.requestFocus();
            binding.mobilenumber.setError("Enter Correct Mobile Number!!");
            return;
        }
        if (binding.mobilenumber.length() != 10) {
            binding.mobilenumber.requestFocus();
            binding.mobilenumber.setError("Enter 10 Digit..!!");
            return;
        }
        binding.progressbar.setVisibility(View.VISIBLE);
        binding.buttonVerifyPhone.setVisibility(View.GONE);


        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + binding.mobilenumber.getText().toString(), 30L, TimeUnit.SECONDS,
                Register_1.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        binding.progressbar.setVisibility(View.GONE);
                        binding.buttonVerifyPhone.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onCodeSent(@NonNull String VerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

//                          User u = new User();
//                          addUser(u);

                        binding.progressbar.setVisibility(View.GONE);
                        binding.buttonVerifyPhone.setVisibility(View.VISIBLE);


                        Intent intent = new Intent(getApplicationContext(), OtpPage.class);
                        intent.putExtra("Mobile", binding.mobilenumber.getText().toString());
                        intent.putExtra("VerificationId", VerificationId);
                        startActivity(intent);

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                        binding.progressbar.setVisibility(View.GONE);
                        binding.buttonVerifyPhone.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }

    private Call<User> addUser(User u) {
        Register_1 userService = null;
        Call<User> call = userService.addUser(u);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NotNull Call<User> call, @NotNull Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Register_1.this, "User created successfully!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<User> call, @NotNull Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
        return call;
    }

    @Override
    public void onBackPressed() {

    }
}