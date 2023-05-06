package com.example.individualproject;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginAndRegister extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_and_register);
        Button registerButton = (Button) findViewById(R.id.button1);

        // Checking the state and enabling Login Button.
        if (getIntent().hasExtra("state")) {
            if (getIntent().getStringExtra("state").equals("success")) {
                registerButton.setEnabled(true);
            } else {
                registerButton.setEnabled(false);
            }
        } else {
            registerButton.setEnabled(false);
        }
    }

    public void goToRegisterView(View view) {
        Intent intent = new Intent(this, RegisterView.class);
        // starting a new activity.
        startActivity(intent);
    }
}