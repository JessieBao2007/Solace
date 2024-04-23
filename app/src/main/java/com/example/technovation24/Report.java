package com.example.technovation24;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

    float normalizedRating=10;

    TextView text;
    LinearLayout container, journal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);


        Intent intent = getIntent();
        points = intent.getIntExtra("highestPoints", 0);


        container = findViewById(R.id.container);


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

        submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float maxRating = rating.getNumStars(); // Get the maximum rating value
                float actualRating = rating.getRating(); // Get the actual rating given by the user
                normalizedRating = (actualRating / maxRating) * 10; // Normalize the rating value between 0 and 10

                // Set points based on the normalized rating
                if (normalizedRating >= 0 && normalizedRating < 3.33) {
                    normalizedRating = 1; // Set points to 1 for bad status
                } else if (normalizedRating >= 3.33 && normalizedRating < 6.66) {
                    normalizedRating = 4; // Set points to 2 for mid status
                } else {
                    normalizedRating = 11; // Set points to 5 for good status
                }

                displayText(); // Display the text with the updated points value

                // Clear input fields and reset button backgrounds
                moodText.setText("");
                symptomText.setText("");
                activeText.setText("");
                dayText.setText("");
                moodBtn.setBackgroundResource(R.drawable.button2);
                sympBtn.setBackgroundResource(R.drawable.button2);
                activeBtn.setBackgroundResource(R.drawable.button2);
                dayBtn.setBackgroundResource(R.drawable.button2);

                // Reset mood, active, day, and symptoms variables
                mood = "";
                active = "";
                day = "";
                symptoms = "";
            }
        });




    }



    public void displayText() {
        // Create a new vertical LinearLayout to hold the text information
        LinearLayout verticalLayout = new LinearLayout(Report.this);
        verticalLayout.setOrientation(LinearLayout.VERTICAL);

        // Add date TextView
        TextView dateTextView = new TextView(Report.this);
        dateTextView.setText("Date: " + year + "/" + month + "/" + dayDate);
        dateTextView.setTextSize(18);
        dateTextView.setTextColor(Color.WHITE); // Change text color to white
        verticalLayout.addView(dateTextView);

        // Create a new horizontal LinearLayout to hold the images
        LinearLayout horizontalLayout = new LinearLayout(Report.this);
        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);

        // Create the first ImageView for mood and set its properties
        ImageView moodImageView = new ImageView(Report.this);


        if (normalizedRating < 3.33) {
            moodImageView.setImageResource(R.drawable.bad);
        } else if (normalizedRating >= 3.33 && normalizedRating < 6.66) {
            moodImageView.setImageResource(R.drawable.mid);
        } else if (normalizedRating >= 6.66){
            moodImageView.setImageResource(R.drawable.good);
        }

        LinearLayout.LayoutParams moodLayoutParams = new LinearLayout.LayoutParams(
                200, // Set width to 200dp
                200 // Set height to 200dp
        );
        moodLayoutParams.setMargins(10, 0, 0, 0); // Adjust margins as needed
        moodImageView.setLayoutParams(moodLayoutParams);
        horizontalLayout.addView(moodImageView);

        // Create the second ImageView for mood status and set its properties
        ImageView statusImageView = new ImageView(Report.this);
        if(points == 5){
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
        horizontalLayout.addView(statusImageView);

        // Add the horizontal LinearLayout with images to the main vertical LinearLayout
        verticalLayout.addView(horizontalLayout);



        // Add the text under the images
        String text = "Mood: " + mood + "\n" + "Symptoms: "+ symptoms + "\n"+"Day: "+ active + "\n" +"Highlights: " + day;
        TextView textView = new TextView(Report.this);
        textView.setText(text);
        textView.setTextSize(16);
        textView.setTextColor(Color.WHITE); // Change text color to white
        LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, // Set width to MATCH_PARENT to fill the parent
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textLayoutParams.setMargins(0, 10, 0, 0); // Adjust margins as needed
        textView.setLayoutParams(textLayoutParams);
        verticalLayout.addView(textView);

        // Add the new vertical LinearLayout to the container LinearLayout
        container.addView(verticalLayout);
    }



    public int getStatus(int athleticPoints, int calmPoints, int socialPoints, int funPoints) {
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