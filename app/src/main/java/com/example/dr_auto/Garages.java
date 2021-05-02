package com.example.dr_auto;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.dr_auto.databinding.ActivityGaragesBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class Garages extends AppCompatActivity {

    ActivityGaragesBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_garages);


        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Name = binding.name.getText().toString();
                String area = binding.area.getText().toString();
                String Street = binding.street.getText().toString();
                String Landmark = binding.landmark.getText().toString();
                int pin = Integer.parseInt(binding.pin.getText().toString());
                long contact = Long.parseLong(binding.contact.getText().toString());

                postDataUsingVolley(Name, Street, area, Landmark, pin, contact);

            }
        });

    }

    private void postDataUsingVolley(String name, String area, String street, String landmark, int pincode, long contact) {
        // url to post our data
        String postUrl = "http://192.168.42.208:9192/api/service-providers/add-service-provider";
        RequestQueue requestQueue = Volley.newRequestQueue(Garages.this);

        Random random = new Random();
        int grID = random.nextInt(2000);

        JSONObject postData = new JSONObject();
        try {
            postData.put("name", name);
            postData.put("street", street);
            postData.put("area", area);
            postData.put("landmark", landmark);
            postData.put("pincode", pincode);
            postData.put("contact", contact);
            postData.put("garageId", grID);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, response -> {
            System.out.println(response);
            Toast.makeText(getApplicationContext(), "successfully uploaded", Toast.LENGTH_SHORT).show();
        }, Throwable::printStackTrace);

        requestQueue.add(jsonObjectRequest);

    }


}