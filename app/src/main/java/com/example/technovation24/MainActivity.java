package com.example.technovation24;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button welcomeBtn = findViewById(R.id.welcomeBtn);
        welcomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open other view; create intent
                Intent aboutIntent = new Intent(MainActivity.this, HomeActivity.class);
                // start activity
                startActivity(aboutIntent);
            }
        });

    }
}