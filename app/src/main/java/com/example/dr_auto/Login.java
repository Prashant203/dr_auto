package com.example.dr_auto;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.dr_auto.databinding.ActivityLoginBinding;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {

    ActivityLoginBinding binding;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        binding.buttonVerifyPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (binding.mobilenumber.getText().toString().trim().isEmpty()) {
                    binding.mobilenumber.requestFocus();
                    binding.mobilenumber.setError("Enter Correct Mobile Number ..!!");
                    return;
                }
                CharSequence temp_emilID=binding.emailText.getText().toString();
                if(!isValidEmail(temp_emilID))
                {
                    binding.emailText.requestFocus();
                    binding.emailText.setError("Enter Correct Mail_ID ..!!");
                    return;
                }
                if (binding.mobilenumber.length() != 10) {
                    binding.mobilenumber.requestFocus();
                    binding.mobilenumber.setError("Enter 10 Digit..!!");
                    return;
                }
                binding.progressbar.setVisibility(View.VISIBLE);
                binding.buttonVerifyPhone.setVisibility(View.GONE);


                String str = binding.name.getText().toString();
                String str1 = binding.emailText.getText().toString();

                PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + binding.mobilenumber.getText().toString(), 30L, TimeUnit.SECONDS,
                        Login.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                binding.progressbar.setVisibility(View.GONE);
                                binding.buttonVerifyPhone.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onCodeSent(@NonNull String VerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                binding.progressbar.setVisibility(View.GONE);
                                binding.buttonVerifyPhone.setVisibility(View.VISIBLE);

                                Intent intent = new Intent(getApplicationContext(), OtpPage.class);
                                intent.putExtra("Mobile", binding.mobilenumber.getText().toString());
                                intent.putExtra("Name", str);
                                intent.putExtra("Email", str1);
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
    @Override
    public void onBackPressed()
    {

    }
    public static boolean isValidEmail(CharSequence target)
    {
        if (TextUtils.isEmpty(target))
        {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}