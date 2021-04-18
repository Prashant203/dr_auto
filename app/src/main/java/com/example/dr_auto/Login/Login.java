package com.example.dr_auto.Login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.dr_auto.R;
import com.example.dr_auto.databinding.ActivityLoginBinding;
import com.example.dr_auto.db.User;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {

    private static final String TAG = "dataBase";
    ActivityLoginBinding binding;

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;

    User userInfo;
    String phoneNoFromDB;
    String nameNoFromDB;

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


        binding.buttonVerifyPhone.setOnClickListener(v -> {

            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("Users");


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

            String uID = databaseReference.push().getKey();
            String name = binding.name.getText().toString();
            String phone = binding.mobilenumber.getText().toString();
            String email = binding.emailText.getText().toString();
            userInfo = new User(name, email, phone);
            databaseReference.child(phone).setValue(userInfo);

            databaseReference.child(phone).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                    userInfo = dataSnapshot.getValue(User.class);

                    phoneNoFromDB = dataSnapshot.child("phone").getValue(String.class);
                    nameNoFromDB = dataSnapshot.child("name").getValue(String.class);
                    Log.d(TAG, "User name: " + userInfo.getName() + ", email " + userInfo.getEmail() + "Phone " + userInfo.getPhone());
                }

                @Override
                public void onCancelled(@NotNull DatabaseError error) {
// Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });


            final AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("We will be verifying this number:");
            alert.setMessage("+91" + binding.mobilenumber.getText().toString() + "\n" + "Is this Okay?, Or would you like to change? :)");

            alert.setPositiveButton("YES", (dialog, which) -> {
                binding.progressbar.setVisibility(View.VISIBLE);
                binding.buttonVerifyPhone.setVisibility(View.GONE);

                loginUser();

            });
            alert.setNegativeButton("EDIT", (dialog, which) -> {
                // do nothing
            });

            AlertDialog alertDialog = alert.create();
            alertDialog.show();


        });

    }

    private void loginUser() {
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
                        intent.putExtra("Mobile", phoneNoFromDB);
                        intent.putExtra("Name", nameNoFromDB);
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

    @Override
    public void onBackPressed() {

    }
}