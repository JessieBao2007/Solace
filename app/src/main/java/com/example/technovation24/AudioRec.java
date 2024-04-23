package com.example.technovation24;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AudioRec extends AppCompatActivity {

    private MediaPlayer songPlayer;

    TextView output;
    Button jingle;
    boolean player = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_rec);

        jingle = findViewById(R.id.jingle);
        songPlayer = MediaPlayer.create(this, R.raw.calming);

        ImageView homeBtn = findViewById(R.id.homeBtn);
        output = findViewById(R.id.textOutput);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open other view; create intent
                Intent aboutIntent = new Intent(AudioRec.this, HomeActivity.class);
                // start activity
                startActivity(aboutIntent);
            }
        });


        jingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!player){
                    songPlayer.start();
                    jingle.setText("Stop Jingle");
                    player = true;
                }else{
                    songPlayer.pause();
                    jingle.setText("Play Jingle");
                    player = false;
                }
            }
        });
    }

    public void speak(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Start Speaking");
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100 && resultCode == RESULT_OK){
            output.setText(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (songPlayer != null) {
            songPlayer.release();
        }
    }

}

