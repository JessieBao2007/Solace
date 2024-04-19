package com.example.technovation24;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Report extends AppCompatActivity {


    String mood, active, day, symptoms;
    Button moodBtn, activeBtn, dayBtn, sympBtn,submit;

    int points;

    int year;
    int month;
    int dayDate;

    float moodRate;

    TextView text;
    LinearLayout container, journal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        container = findViewById(R.id.container);

        Calming calmingInstance = new Calming();

        points = calmingInstance.getStatus();


        //date stuff
        Calendar calendar = Calendar.getInstance();
         year = calendar.get(Calendar.YEAR);
         month = calendar.get(Calendar.MONTH) + 1;
         dayDate = calendar.get(Calendar.DAY_OF_MONTH);


        RatingBar rating = findViewById(R.id.moodRate);


        EditText moodText = findViewById(R.id.moodText);
        moodBtn = findViewById(R.id.submitMood);
        moodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mood = moodText.getText().toString();
                moodBtn.setClickable(false);
                moodBtn.setBackgroundResource(R.drawable.white);
                moodBtn.setTextColor(Color.BLACK);
            }
        });


        EditText symptomText = findViewById(R.id.sympText);
        sympBtn = findViewById(R.id.submitSymp);
        sympBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                symptoms = symptomText.getText().toString();
                sympBtn.setClickable(false);
                sympBtn.setBackgroundResource(R.drawable.white);
                sympBtn.setTextColor(Color.BLACK);
            }
        });

        EditText activeText = findViewById(R.id.activeText);
        activeBtn = findViewById(R.id.submitActive);
        activeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                active = activeText.getText().toString();
                activeBtn.setBackgroundResource(R.drawable.white);
                activeBtn.setTextColor(Color.BLACK);

            }
        });

        EditText dayText = findViewById(R.id.dayText);
        dayBtn = findViewById(R.id.submitDay);
        dayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day = dayText.getText().toString();
                dayBtn.setBackgroundResource(R.drawable.white);
                dayBtn.setTextColor(Color.BLACK);
            }
        });

        submit=findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayText();
                moodRate = 5 - rating.getRating();
                moodText.setText("");
                symptomText.setText("");
                activeText.setText("");
                dayText.setText("");

                moodBtn.setBackgroundResource(R.drawable.button2);
                sympBtn.setBackgroundResource(R.drawable.button2);
                activeBtn.setBackgroundResource(R.drawable.button2);
                dayBtn.setBackgroundResource(R.drawable.button2);


                mood = "";
                active = "";
                day = "";
                symptoms = "";
            }
        });


    }



    public void displayText() {
        // Create a new LinearLayout to hold the images and text
        LinearLayout itemLayout = new LinearLayout(Report.this);
        itemLayout.setOrientation(LinearLayout.HORIZONTAL);

        // Add date TextView
        TextView dateTextView = new TextView(Report.this);
        dateTextView.setText("Date: " + year + "/" + month + "/" + dayDate);
        dateTextView.setTextSize(18);
        dateTextView.setTextColor(Color.WHITE); // Change text color to white
        itemLayout.addView(dateTextView);

        // Create the first ImageView for mood and set its properties
        ImageView moodImageView = new ImageView(Report.this);
        if(moodRate < 2.0){
            moodImageView.setImageResource(R.drawable.bad);
        } else if(moodRate >= 2.0 && moodRate <= 4.0){
            moodImageView.setImageResource(R.drawable.mid);
        } else {
            moodImageView.setImageResource(R.drawable.good);
        }
        LinearLayout.LayoutParams moodLayoutParams = new LinearLayout.LayoutParams(
                200, // Set width to 200dp
                200 // Set height to 200dp
        );
        moodLayoutParams.setMargins(10, 0, 0, 0); // Adjust margins as needed
        moodImageView.setLayoutParams(moodLayoutParams);
        itemLayout.addView(moodImageView);

        // Create the second ImageView for mood status and set its properties
        ImageView statusImageView = new ImageView(Report.this);
        if(points == 0){
            statusImageView.setImageResource(R.drawable.buff);
        } else if(points == 1){
            statusImageView.setImageResource(R.drawable.calmest);
        } else if(points == 2){
            statusImageView.setImageResource(R.drawable.supers);
        } else if(points == 3){
            statusImageView.setImageResource(R.drawable.yapper);
        } else {
            statusImageView.setImageResource(R.drawable.bad);
        }
        LinearLayout.LayoutParams statusLayoutParams = new LinearLayout.LayoutParams(
                200, // Set width to 200dp
                200 // Set height to 200dp
        );
        statusLayoutParams.setMargins(10, 0, 0, 0); // Adjust margins as needed
        statusImageView.setLayoutParams(statusLayoutParams);
        itemLayout.addView(statusImageView);

        // Add the new LinearLayout to the container LinearLayout
        container.addView(itemLayout);


    }




}