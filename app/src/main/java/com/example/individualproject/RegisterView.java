package com.example.individualproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class RegisterView extends AppCompatActivity {

    Button bRegisterButton;
    EditText etFirstName, etFamilyName, etEmail, etPassword, etDateofBirth;
    boolean isAllFieldsChecked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_view);

        // register buttons with their proper IDs.
        bRegisterButton = findViewById(R.id.registerButton);

        // register all the EditText fields with their IDs.
        etFirstName = findViewById(R.id.firstName);
        etFamilyName = findViewById(R.id.familyName);
        etDateofBirth = findViewById(R.id.dateOfBirth);
        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);

        // handle the Register button click
        bRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // store the returned value of the dedicated function which checks
                // whether the entered data is valid or if any fields are left blank.
                isAllFieldsChecked = CheckAllFields();

                // the boolean variable turns to be true then
                // only the user must be proceed to the LoginAndRegister Activity
                if (isAllFieldsChecked) {
                    Intent intent = new Intent(RegisterView.this, LoginAndRegister.class);
                    intent.putExtra("state", "success");
                    startActivity(intent);
                }
            }
        });
    }

    // Validation for all Register fields
    // Returns booleans for non valid fields

    private boolean CheckAllFields() {
        if (etFirstName.length() == 0) {
            etFirstName.setError("This field is required");
            return false;
        } else if (etFirstName.length() < 3 ^ etFirstName.length() > 30) {
            etFirstName.setError("First Name must be minimum 3 characters and Maximum 30 characters ");
            return false;
        }

        if (etFamilyName.length() == 0) {
            etFamilyName.setError("This field is required");
            return false;
        }

        if (etDateofBirth.length() == 0 ^ !(etDateofBirth.getText().toString().matches("^[0-9]{2}/[0-9]{2}/[0-9]{4}$")))
        {
            etDateofBirth.setError("This field is required");
            return false;
        }

        if (etEmail.length() == 0 ^ !(Patterns.EMAIL_ADDRESS.matcher(etEmail.getText()).matches())) {
            etEmail.setError("Email is required and Valid");
            return false;
        }

        if (etPassword.length() == 0) {
            etPassword.setError("Password is required");
            return false;
        }

        // after all validation return true.
        return true;
    }
}