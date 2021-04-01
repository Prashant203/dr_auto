package com.example.dr_auto;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.dr_auto.databinding.ActivityOtpPageBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class OtpPage extends AppCompatActivity {
    ActivityOtpPageBinding binding;


    EditText otp_textbox_one, otp_textbox_two, otp_textbox_three, otp_textbox_four, otp_textbox_five, otp_textbox_six;
    private String varificationId;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_otp_page);
        final TextView textView = findViewById(R.id.resend);

        new CountDownTimer(30000, 1000) {

            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                textView.setText("00:"+millisUntilFinished / 1000);
            }

            public void onFinish() {
                binding.resendButton.setVisibility(View.VISIBLE);
            }
        }.start();

        binding.mob.setText("+91 "+getIntent().getStringExtra("Mobile"));

        otp_textbox_one = findViewById(R.id.etDigit1);
        otp_textbox_two = findViewById(R.id.etDigit2);
        otp_textbox_three = findViewById(R.id.etDigit3);
        otp_textbox_four = findViewById(R.id.etDigit4);
        otp_textbox_five = findViewById(R.id.etDigit5);
        otp_textbox_six = findViewById(R.id.etDigit6);

        EditText[] edit = {otp_textbox_one, otp_textbox_two, otp_textbox_three, otp_textbox_four, otp_textbox_five, otp_textbox_six};

        otp_textbox_one.addTextChangedListener(new GenericTextWatcher(otp_textbox_one, edit));
        otp_textbox_two.addTextChangedListener(new GenericTextWatcher(otp_textbox_two, edit));
        otp_textbox_three.addTextChangedListener(new GenericTextWatcher(otp_textbox_three, edit));
        otp_textbox_four.addTextChangedListener(new GenericTextWatcher(otp_textbox_four, edit));
        otp_textbox_five.addTextChangedListener(new GenericTextWatcher(otp_textbox_five, edit));
        otp_textbox_six.addTextChangedListener(new GenericTextWatcher(otp_textbox_six, edit));





        varificationId = getIntent().getStringExtra("VerificationId");
        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.etDigit1.getText().toString().isEmpty()
                        || binding.etDigit2.getText().toString().isEmpty()
                        || binding.etDigit3.getText().toString().isEmpty()
                        || binding.etDigit4.getText().toString().isEmpty()
                        || binding.etDigit5.getText().toString().isEmpty()
                        || binding.etDigit6.getText().toString().isEmpty()
                ) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid OTP", Toast.LENGTH_SHORT).show();
                    return;
                }
                String code = binding.etDigit1.getText().toString() +
                        binding.etDigit2.getText().toString() +
                        binding.etDigit3.getText().toString() +
                        binding.etDigit4.getText().toString() +
                        binding.etDigit5.getText().toString() +
                        binding.etDigit6.getText().toString();

                if (varificationId != null) {
                    binding.progressbar.setVisibility(View.VISIBLE);
                    binding.submitButton.setVisibility(View.INVISIBLE);
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(varificationId, code);
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            binding.progressbar.setVisibility(View.INVISIBLE);
                            binding.submitButton.setVisibility(View.VISIBLE);
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            } else {
                                Toast.makeText(getApplicationContext(), "The OTP entered was invalid", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });

        binding.wrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OtpPage.this,Login.class));
            }
        });



        binding.resendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + getIntent().getStringExtra("Mobile"), 30L, TimeUnit.SECONDS,
                        OtpPage.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onCodeSent(@NonNull String newVerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                binding.resendButton.setVisibility(View.INVISIBLE);
                                textView.setText("00:30");
                                new CountDownTimer(30000, 1000) {

                                    @SuppressLint("SetTextI18n")
                                    public void onTick(long millisUntilFinished) {
                                        textView.setText("00:"+millisUntilFinished / 1000);
                                    }

                                    public void onFinish() {
                                        binding.resendButton.setVisibility(View.VISIBLE);
                                    }
                                }.start();

                                varificationId = newVerificationId;
                                Toast.makeText(getApplicationContext(), "OTP Send Again Successfully", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

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
}