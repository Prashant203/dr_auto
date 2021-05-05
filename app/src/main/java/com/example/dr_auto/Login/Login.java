package com.example.dr_auto.Login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dr_auto.R;
import com.example.dr_auto.databinding.ActivityLoginBinding;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {


    ActivityLoginBinding binding;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

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
                binding.name.setSelection(binding.name.length());
                binding.name.setError("This field cannot be empty!!");
                return;
            }

            if (binding.mobilenumber.getText().toString().trim().isEmpty()) {
                binding.mobilenumber.requestFocus();
                binding.mobilenumber.setSelection(binding.mobilenumber.length());
                binding.mobilenumber.setError("Enter Correct Mobile Number!!");
                return;
            }
            CharSequence temp_emilID = binding.emailText.getText().toString();
            if (!isValidEmail(temp_emilID)) {
                binding.emailText.requestFocus();
                binding.emailText.setSelection(binding.emailText.length());
                binding.emailText.setError("Enter Correct Mail_ID!!");
                return;
            }
            if (binding.mobilenumber.length() != 10) {
                binding.mobilenumber.requestFocus();
                binding.mobilenumber.setSelection(binding.mobilenumber.length());
                binding.mobilenumber.setError("Enter 10 Digit..!!");
                return;
            }




          /*  databaseReference.child(phone).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                    userInfo = dataSnapshot.getValue(User.class);

                    phoneNoFromDB = dataSnapshot.child("phone").getValue(String.class);
                    nameNoFromDB = dataSnapshot.child("name").getValue(String.class);
                    emailFromDB = dataSnapshot.child("email").getValue(String.class);
                    String uIDFromDB = dataSnapshot.child("email").getValue(String.class);

                    Log.d(TAG, uIDFromDB+" / "+"User name: " + userInfo.getName() + ", email " + userInfo.getEmail() + "Phone " + userInfo.getPhone());
                }

                @Override
                public void onCancelled(@NotNull DatabaseError error) {
// Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });

*/
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

    private void postDataUsingVolley(String name, String email, String phone) {
        // url to post our data
        String url = "http://192.168.42.208:9192/createUser";


        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(Login.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // inside on response method we are
                // hiding our progress bar
                // and setting data to edit text as empty

                binding.name.setText("");
                binding.emailText.setText("");
                binding.mobilenumber.setText("");

                // on below line we are displaying a success toast message.
                Toast.makeText(Login.this, "Data added to API", Toast.LENGTH_SHORT).show();
                try {
                    // on below line we are passing our response
                    // to json object to extract data from it.
                    JSONObject respObj = new JSONObject(response);

                    // below are the strings which we
                    // extract from our json object.
                    String name = respObj.getString("userName");
                    String email = respObj.getString("userEmail");
                    String phone = respObj.getString("userPhone");


                    // on below line we are setting this string s to our text view.
                    //  responseTV.setText("Name : " + name + "\n" + "Job : " + job);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(Login.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("userName", name);
                params.put("userEmail", email);
                params.put("userPhone", phone);

                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
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
                        intent.putExtra("Name", binding.name.getText().toString());
                        intent.putExtra("Email", binding.emailText.getText().toString());
                        intent.putExtra("VerificationId", VerificationId);
                        // postDataUsingVolley(binding.name.getText().toString(), binding.emailText.getText().toString(),binding.mobilenumber.getText().toString() );

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