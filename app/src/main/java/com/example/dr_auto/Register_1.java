package com.example.dr_auto;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.dr_auto.databinding.ActivityRegister1Binding;

public class Register_1 extends AppCompatActivity {

    ActivityRegister1Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_register_1);


        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                CharSequence temp_emilID=binding.emailText.getText().toString();
                if(!isValidEmail(temp_emilID))
                {
                    binding.emailText.requestFocus();
                    binding.emailText.setError("Enter Correct Mail_ID ..!!");

                }
                else
                {
                    startActivity(new Intent(Register_1.this,MainActivity.class));

                }
        }});

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