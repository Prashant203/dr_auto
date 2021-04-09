package com.example.dr_auto;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.dr_auto.databinding.ActivityLoginBinding;
import com.example.dr_auto.db.APIUtils;
import com.example.dr_auto.db.User;
import com.example.dr_auto.db.UserService;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    ActivityLoginBinding binding;
    UserService userService = APIUtils.getUserService();

    public static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);


        binding.buttonVerifyPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.name.getText().toString().trim().isEmpty()) {
                    binding.name.requestFocus();
                    binding.name.setError("This field cannot be empty!!");
                    return;
                }

                if (binding.mobilenumber.getText().toString().trim().isEmpty()) {
                    binding.mobilenumber.requestFocus();
                    binding.mobilenumber.setError("Enter Correct Mobile Number!!");
                    return;
                }
                CharSequence temp_emilID = binding.emailText.getText().toString();
                if (!isValidEmail(temp_emilID)) {
                    binding.emailText.requestFocus();
                    binding.emailText.setError("Enter Correct Mail_ID!!");
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
                        Login.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                binding.progressbar.setVisibility(View.GONE);
                                binding.buttonVerifyPhone.setVisibility(View.VISIBLE);

                            }

                            @Override
                            public void onCodeSent(@NonNull String VerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                User u = new User();
                                addUser(u);

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
        });


    }

    private void addUser(User u) {
        Call<User> call = userService.addUser(u);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Login.this, "User created successfully!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}