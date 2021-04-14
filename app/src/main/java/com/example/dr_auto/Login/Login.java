package com.example.dr_auto.Login;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
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
import com.example.dr_auto.db.APIUtils;
import com.example.dr_auto.db.User;
import com.example.dr_auto.db.UserService;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    ActivityLoginBinding binding;

    // creating a variable for our
    // Firebase Database.
    FirebaseDatabase firebaseDatabase;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;

    // creating a variable for
    // our object class
    User userInfo;


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

        // instance of our FIrebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("User");

        userInfo = new User();


        binding.buttonVerifyPhone.setOnClickListener(v -> {
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
            final AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("We will be verifying this number:");
            alert.setMessage("+91" + binding.mobilenumber.getText().toString() + "\n" + "Is this Okay?, Or would you like to change? :)");

            alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    binding.progressbar.setVisibility(View.VISIBLE);
                    binding.buttonVerifyPhone.setVisibility(View.GONE);
                    String name = binding.name.getText().toString();
                    String phone = binding.mobilenumber.getText().toString();
                    String email = binding.emailText.getText().toString();
                    loginUser();
                    addDatatoFirebase(name, phone, email);


                }
            });
            alert.setNegativeButton("EDIT", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // do nothing
                }
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

    private void addDatatoFirebase(String name, String phone, String email) {
        // below 3 lines of code is used to set
        // data in our object class.

        userInfo.setName(name);
        userInfo.setPhone(phone);
        userInfo.setEmail(email);

        // we are use add value event listener method
        // which is called with database reference.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting 
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                databaseReference.setValue(userInfo);

                // after adding this data we are showing toast message.
                Toast.makeText(Login.this, "data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(Login.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addUser(User u) {
        Call<User> call = userService.addUser(u);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NotNull Call<User> call, @NotNull Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Login.this, "User created successfully!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<User> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}