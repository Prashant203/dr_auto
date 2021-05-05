package com.example.dr_auto.servers;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class ServiceProviderAPI {

    public static void postDataUsingVolley(Context context, String name, String area, String street, String landmark, int pincode, long contact) {
        // url to post our data
        String postUrl = "http://192.168.42.208:9192/api/service-providers/add-service-provider";
        RequestQueue requestQueue = Volley.newRequestQueue(context);


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
            Toast.makeText(context, "successfully uploaded", Toast.LENGTH_SHORT).show();
        }, Throwable::printStackTrace);

        requestQueue.add(jsonObjectRequest);

    }


}
