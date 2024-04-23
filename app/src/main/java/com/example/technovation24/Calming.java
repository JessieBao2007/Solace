package com.example.technovation24;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class Calming extends AppCompatActivity {

    int athleticPoints=0;
    int calmPoints=0;
    int socialPoints=0;
    int funPoints=0;

    public int points = 4;

    LinearLayout run, weight, yoga, prayer, breathe,layout1,layout2,layout3,layout4;

    Button exerciseBtn, cont;

    CheckBox clean, tv, wash, art, social1, social2, social3, social4;

    boolean exercisePress = false;
    TextView tester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calming);

        ImageView homeBtn = findViewById(R.id.homeBtn);

        run = findViewById(R.id.runButton);
        weight = findViewById(R.id.weightButton);
        yoga = findViewById(R.id.yogaButton);
        prayer = findViewById(R.id.prayerButton);
        breathe = findViewById(R.id.breatheButton);

        clean = findViewById(R.id.roomButton);
        tv = findViewById(R.id.TVButton);
        wash = findViewById(R.id.washButton);
        art = findViewById(R.id.artButton);

        social1 = findViewById(R.id.social1);
        social2 = findViewById(R.id.social2);
        social3 = findViewById(R.id.social3);
        social4 = findViewById(R.id.social4);

        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);
        layout3 = findViewById(R.id.layout3);
        layout4 = findViewById(R.id.layout4);

        cont = findViewById(R.id.cont);

        tester = findViewById(R.id.exerciseText);
        exerciseBtn = findViewById(R.id.lockButton);

        Spinner exerciseSpinner = findViewById(R.id.exerciseSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.exercisechoices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exerciseSpinner.setAdapter(adapter);



        exerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedExercise = exerciseSpinner.getSelectedItem().toString();
                switch (selectedExercise) {
                    case "less than 10 minutes":
                        athleticPoints += 10;
                        break;
                    case "10 to 19 minutes":
                        athleticPoints += 20;
                        break;
                    case "20 to 29 minutes":
                        athleticPoints+=30;
                        break;
                    case "30 to 39 minutes":
                        athleticPoints+=40;
                        break;
                    case "40 to 49 minutes":
                        athleticPoints+=50;
                        break;
                    case "50 to 59 minutes":
                        athleticPoints+=60;
                        break;
                    case "greater than 1 hour":
                        athleticPoints+=70;
                        break;

                }
                exerciseBtn.setVisibility(View.GONE);
            }
        });



        run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!exercisePress) {
                    exercisePress = true;
                    athleticPoints += 40;
                    funPoints+=20;
                    run.setClickable(false);
                    run.setBackgroundResource(R.drawable.white);
                }
            }
        });

        weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!exercisePress) {
                    exercisePress = true;
                    athleticPoints += 40;
                    funPoints+=20;
                    weight.setClickable(false);
                    weight.setBackgroundResource(R.drawable.white);
                }
            }
        });

        yoga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!exercisePress) {
                    exercisePress = true;
                    athleticPoints += 40;
                    calmPoints+=20;
                    funPoints+=20;
                    yoga.setClickable(false);
                    yoga.setBackgroundResource(R.drawable.white);
                }
            }
        });

        prayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calmPoints += 50;
                socialPoints+=30;
                prayer.setVisibility(View.GONE);
            }
        });

        breathe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calmPoints += 50;
                athleticPoints+=10;
                breathe.setVisibility(View.GONE);
            }
        });


        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funPoints += 40;
                calmPoints+=10;
                clean.setClickable(false);
                clean.setBackgroundResource(R.drawable.white);

            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funPoints += 50;
                calmPoints+=15;
                athleticPoints-=20;
                socialPoints+=10;
                tv.setClickable(false);
                tv.setBackgroundResource(R.drawable.white);

            }
        });

        wash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funPoints += 40;
                calmPoints+=10;
                wash.setClickable(false);
                wash.setBackgroundResource(R.drawable.white);

            }
        });

        art.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funPoints += 50;
                calmPoints+=30;
                art.setClickable(false);
                art.setBackgroundResource(R.drawable.white);

            }
        });



        social1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socialPoints += 70;
                funPoints+=10;
                calmPoints+=20;
                social1.setClickable(false);
                layout1.setBackgroundResource(R.drawable.white);

            }
        });


        social2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socialPoints += 60;
                funPoints+=30;
                social2.setClickable(false);
                layout2.setBackgroundResource(R.drawable.white);

            }
        });



        social3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socialPoints += 30;
                funPoints+=20;
                calmPoints+=20;
                social3.setClickable(false);
                layout3.setBackgroundResource(R.drawable.white);

            }
        });


        social4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socialPoints += 50;
                social4.setClickable(false);
                layout4.setBackgroundResource(R.drawable.white);

            }
        });



        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open other view; create intent
                Intent aboutIntent = new Intent(Calming.this, HomeActivity.class);
                // start activity
                startActivity(aboutIntent);
            }
        });

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int highestPoints = getStatus(); // Get the highest points using the getStatus() method
                Intent aboutIntent = new Intent(Calming.this, Report.class);
                aboutIntent.putExtra("highestPoints", highestPoints); // Pass the highest points to the Report activity
                startActivity(aboutIntent);
            }
        });

    }


    public int getStatus() {
        int maxPoints = Math.max(Math.max(athleticPoints, calmPoints), Math.max(funPoints, socialPoints));

        if (athleticPoints == maxPoints && athleticPoints > 0) {
            return 5;
        } else if (calmPoints == maxPoints && calmPoints > 0) {
            return 1;
        } else if (funPoints == maxPoints && funPoints > 0) {
            return 2;
        } else if (socialPoints == maxPoints && socialPoints > 0) {
            return 3;
        } else {
            return 4;
        }
    }


}