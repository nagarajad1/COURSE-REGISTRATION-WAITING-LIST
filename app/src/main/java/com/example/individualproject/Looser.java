package com.example.individualproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class Looser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looser);

        // Reading Score
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String score = sharedPreferences.getString("score", "");

        TextView textView1 = (TextView) findViewById(R.id.looserscore);
        textView1.setText("$ " + score);
    }
}