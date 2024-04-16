package com.example.technovation24;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button cameraBtn = findViewById(R.id.cameraBtn);
        Button audioBtn = findViewById(R.id.audioBtn);
        Button calmBtn = findViewById(R.id.calmBtn);
        Button calendarBtn = findViewById(R.id.calendarBtn);

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open other view; create intent
                Intent aboutIntent = new Intent(HomeActivity.this, CameraRec.class);
                // start activity
                startActivity(aboutIntent);
            }
        });

        audioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open other view; create intent
                Intent aboutIntent = new Intent(HomeActivity.this, AudioRec.class);
                // start activity
                startActivity(aboutIntent);
            }
        });
        calmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open other view; create intent
                Intent aboutIntent = new Intent(HomeActivity.this, Calming.class);
                // start activity
                startActivity(aboutIntent);
            }
        });
        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open other view; create intent
                Intent aboutIntent = new Intent(HomeActivity.this, Calendar.class);
                // start activity
                startActivity(aboutIntent);
            }
        });
    }
}