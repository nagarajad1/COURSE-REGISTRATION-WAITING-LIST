package com.example.individualproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SecondQuestion extends AppCompatActivity {
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_question);

        RadioGroup radiogroup1 = (RadioGroup) findViewById(R.id.radiogroup1);
        radiogroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //                Applying color backgrounds to radios
                for (int i = 0; i < group.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) group.getChildAt(i);
                    if (radioButton.getId() == checkedId) {
                        radioButton.setBackgroundColor(getResources().getColor(R.color.millionairelite, getTheme()));
                    } else {
                        radioButton.setBackgroundColor(getResources().getColor(R.color.white, getTheme()));
                    }

                }
            }
        });

        Button button1 = (Button) findViewById(R.id.button1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedOptionId = radiogroup1.getCheckedRadioButtonId();
                if (selectedOptionId != -1) {
                    RadioButton selectedOption = findViewById(selectedOptionId);
                    String selectedOptionText = selectedOption.getText().toString();
                    //                    Checking the answers
                    if (selectedOptionText.equals("Android Studio")) {
                        int duration = Toast.LENGTH_SHORT;
                        Toast.makeText(SecondQuestion.this, "This is the CORRECT answer You Earned $ 1000", duration).show();
                        Intent intent = new Intent(SecondQuestion.this, ThirdQuestion.class);
                        startActivity(intent);
                    } else {
                        int duration = Toast.LENGTH_SHORT;
                        Toast.makeText(SecondQuestion.this, "Not Correct", duration).show();
                        Intent intent = new Intent(SecondQuestion.this, Looser.class);
                        startActivity(intent);
                    }

                }
            }
        });
    }
}