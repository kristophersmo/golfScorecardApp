package com.example.golfhandicapapp;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;
import java.util.ArrayList;
import android.annotation.SuppressLint;
import static java.lang.Math.round;
import static java.lang.String.valueOf;
import android.widget.Toast;

public class StartRound extends AppCompatActivity
{
    TextView gameCourse, gameRatingSlope;  // OUTPUT TEXT FOR COURSE SELECTION, RATING, AND SLOPE
    TextView player1, player2, player3, player4;  // OUTPUT GOLFERS
    TextView allPars;  // HOLDS THE TOTAL SUM OF ALL PARS
    TextView totalP1Score, totalP2Score, totalP3Score, totalP4Score;  // TOTAL SCORE FOR EACH PLAYER
    TextView courseHCap1, courseHCap2, courseHCap3, courseHCap4;  // HOLDS THE COURSE HANDICAP PER PLAYER
    Button getScores, finishGame;  // BUTTON TO COMPLETE THE SCORECARD AND FINISH THE GAME
    ArrayList<String> activePlayers;  // ARRAYLIST TO HOLD GOLFERS IN CURRENT GAME
    ArrayList<String> activeCourse;  // ARRAYLIST TO HOLD COURSE IN CURRENT GAME
    ArrayList<Integer> activeHandicaps;  // ARRAYLIST TO HOLD GOLFER HANDICAPS IN CURRENT GAME
    ArrayList<Integer> activePars;  // ARRAYLIST TO HOLD PARS IN CURRENT GAME
    float courseRating, courseSlope;  // FLOATS TO HOLD THE RATING AND SLOPE IN CURRENT GAME
    int courseHoles;    // INT TO HOLD THE NUMBER OF COURSE HOLES
    int finalScoreP1 = 0;   // INITIALIZE INT TO HOLDS THE FINAL SCORE OF PLAYER 1
    int finalScoreP2 = 0;   // INITIALIZE INT TO HOLDS THE FINAL SCORE OF PLAYER 2
    int finalScoreP3 = 0;   // INITIALIZE INT TO HOLDS THE FINAL SCORE OF PLAYER 3
    int finalScoreP4 = 0;   // INITIALIZE INT TO HOLDS THE FINAL SCORE OF PLAYER 4
    DatabaseCourses courseDB;  // FOR COURSE DATABASE FUNCTIONS
    DatabaseGolfers golferDB;  // FOR GOLFER DATABASE FUNCTIONS


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_round);

        // DEFINING VARIABLES FOR EACH LAYOUT ITEM
        gameCourse = findViewById(R.id.gameCourse);
        gameRatingSlope = findViewById(R.id.gameRatingSlope);
        player1 = findViewById(R.id.showPlayer1);
        player2 = findViewById(R.id.showPlayer2);
        player3 = findViewById(R.id.showPlayer3);
        player4 = findViewById(R.id.showPlayer4);
        finishGame = findViewById(R.id.finish);
        getScores = findViewById(R.id.getScores);
        allPars = findViewById(R.id.totalPar);
        courseHCap1 = findViewById(R.id.courseHCap1);
        courseHCap2 = findViewById(R.id.courseHCap2);
        courseHCap3 = findViewById(R.id.courseHCap3);
        courseHCap4 = findViewById(R.id.courseHCap4);

        // DATABASE HELPER VARIABLE FOR COURSE DB
        courseDB = new DatabaseCourses(this);
        // DATABASE HELPER VARIABLE FOR GOLFERS DB
        golferDB = new DatabaseGolfers(this);

        // RETRIEVING THE PLAYER AND COURSE DATA THAT WAS SELECTED IN PREVIOUS ACTIVITIES
        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        activePlayers = intent.getStringArrayListExtra("players");
        activeHandicaps = intent.getIntegerArrayListExtra("handicaps");
        activeCourse = intent.getStringArrayListExtra("course");
        activePars = intent.getIntegerArrayListExtra("pars");
        courseRating = bundle.getFloat("rating");
        courseSlope = bundle.getFloat("slope");
        courseHoles = bundle.getInt("holes");

        // PREPARING THE SCORECARD FOR PLAY
        fillPlayers();
        fillCourse();
        fillRatingSlope();
        int totalParValue = 0;
        fillPars(totalParValue);
        calcHandicaps(totalParValue);
        // USER THEN ADDS A SCORE FOR EACH PLAYER/HOLE AND SELECTS 'FINISH' WHEN COMPLETE


        // TEST TOASTS (ENSURING THAT ALL DATA IS ACCURATE/PRESENT)
        // Toast.makeText(getApplicationContext(), activePlayers.toString(), Toast.LENGTH_SHORT).show();
        // Toast.makeText(getApplicationContext(), activeHandicaps.toString(), Toast.LENGTH_SHORT).show();
        // Toast.makeText(getApplicationContext(), activeCourse.toString(), Toast.LENGTH_SHORT).show();
        // Toast.makeText(getApplicationContext(), activePars.toString(), Toast.LENGTH_SHORT).show();


        // ON-CLICK, ONLY TALLY THE SCORECARD RESULTS
        getScores.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                tallyResults();
            }
        });


        // ON-CLICK, TALLY SCORECARD RESULTS AND FINISH THE GAME
        finishGame.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                tallyResults();

                Intent finishGame = new Intent(StartRound.this, CompleteGame.class);
                finishGame.putExtra("players", activePlayers);
                finishGame.putExtra("handicaps", activeHandicaps);

                Bundle scoring = new Bundle();
                scoring.putInt("scoreP1", finalScoreP1);
                scoring.putInt("scoreP2", finalScoreP2);
                scoring.putInt("scoreP3", finalScoreP3);
                scoring.putInt("scoreP4", finalScoreP4);
                finishGame.putExtras(scoring);
                startActivity(finishGame);
            }
        });
    }


    // RETRIEVING THE PLAYERS THAT WERE SELECTED (FROM PLAYERS CLASS)
    // DISPLAY ONLY THE ACTIVE PLAYERS, HIDE ANY PLAYER VACANCIES
    @SuppressLint("SetTextI18n")
    private void fillPlayers()
    {
        if (activePlayers.size() == 1)
        {
            player1.setText("A:  " + activePlayers.get(0));
            player2.setVisibility(View.INVISIBLE);
            player3.setVisibility(View.INVISIBLE);
            player4.setVisibility(View.INVISIBLE);
        }
        else if (activePlayers.size() == 2)
        {
            player1.setText("A:  " + activePlayers.get(0));
            player2.setText("B:  " + activePlayers.get(1));
            player3.setVisibility(View.INVISIBLE);
            player4.setVisibility(View.INVISIBLE);
        }
        else if (activePlayers.size() == 3)
        {
            player1.setText("A:  " + activePlayers.get(0));
            player2.setText("B:  " + activePlayers.get(1));
            player3.setText("C:  " + activePlayers.get(2));
            player4.setVisibility(View.INVISIBLE);
        }
        else if (activePlayers.size() == 4)
        {
            player1.setText("A:  " + activePlayers.get(0));
            player2.setText("B:  " + activePlayers.get(1));
            player3.setText("C:  " + activePlayers.get(2));
            player4.setText("D:  " + activePlayers.get(3));
        }
    }


    // RETRIEVING THE COURSE THAT WAS SELECTED (FROM COURSE CLASS)
    private void fillCourse()
    {
        String theCourse = activeCourse.get(0);
        gameCourse.setText(theCourse);
    }


    // RETRIEVING THE COURSE RATING AND SLOPE (FOR USER REFERENCE)
    // DISPLAYING ON A SINGLE LINE TO SAVE SPACE
    @SuppressLint("SetTextI18n")
    private void fillRatingSlope()
    {
        gameRatingSlope.setText("RATING: " + courseRating + "     " + "SLOPE: " + courseSlope);
    }


    // RETRIEVING THE PARS ASSOCIATED WITH THE SELECTED COURSE (FROM COURSE CLASS)
    // 1. CREATE INT ARRAY OF SIZE 'NUMBER OF PAR INPUT FIELDS'
    // 2. INSIDE LOOP, CREATE TEMP TEXTVIEW TO REPRESENT THE CURRENT PAR FIELD
    // 3. INSIDE LOOP, ADD ASSOCIATED VALUE FROM ARRAYLIST PARS TO THE CURRENT FIELD
    // 4. INSIDE LOOP, ADD CURRENT PAR TO CUMULATIVE TOTAL PARS
    // 5. DISPLAY TOTAL AT END OF LOOP
    private void fillPars(int totalParValue)
    {
        int[] parArray = new int[] {R.id.parHole1, R.id.parHole2, R.id.parHole3, R.id.parHole4,
                R.id.parHole5, R.id.parHole6, R.id.parHole7, R.id.parHole8, R.id.parHole9,
                R.id.parHole10, R.id.parHole11, R.id.parHole12, R.id.parHole13, R.id.parHole14,
                R.id.parHole15, R.id.parHole16, R.id.parHole17, R.id.parHole18};

        for(int i = 0; i < courseHoles; i++)
        {
            TextView cur_parValue = findViewById(parArray[i]);
            cur_parValue.setText(String.format(String.valueOf(activePars.get(i))));
            totalParValue += activePars.get(i);
        }
        allPars.setText(valueOf(totalParValue));
    }


    // FUNCTION TO FIND THE CALCULATED COURSE HANDICAP FOR EACH PLAYER
    // 1. CREATE INT ARRAY OF SIZE 'NUMBER OF COURSE HANDICAP FIELDS' (ONE PER PLAYER)
    // 2. USE THE SIZE OF 'ACTIVE HANDICAPS' TO LOOP EXACTLY THROUGH PLAYER COUNT ONLY
    // 3. INSIDE LOOP, CREATE TEMP TEXTVIEW TO REPRESENT THE CURRENT HANDICAP FIELD
    // 4. INSIDE LOOP, PULL CURRENT HANDICAP VALUE AND CALCULATE THE COURSE HANDICAP
    // 5. LOOPS ONCE FOR EACH ACTIVE PLAYER

    // COURSE HANDICAP = [Handicap Index * (courseSlope / 113)] + (courseRating - Par)  *TO NEAREST WHOLE NUMBER*
    @SuppressLint("SetTextI18n")
    private void calcHandicaps(int totalParValue)
    {
        float slopeCalc = courseSlope / 113;

        int[] handicapArray = new int[] {R.id.courseHCap1, R.id.courseHCap2, R.id.courseHCap3, R.id.courseHCap4};

        for (int i = 0; i < activeHandicaps.size(); i++)
        {
            int courseHandicap;
            TextView cur_handicapValue = findViewById(handicapArray[i]);
            courseHandicap = round((float) activeHandicaps.get(i) * slopeCalc + (courseRating - totalParValue));
            cur_handicapValue.setText(valueOf(courseHandicap));
        }
    }


    // FUNCTION TO TALLY THE FINAL SCORING RESULTS OF THE GAME
    // 1. CREATE AN INT ARRAY OF SIZE 'SCORING FIELDS' (ONE ARRAY FOR EACH PLAYER COLUMN)
    // 2. INSIDE LOOP, CREATE TEMP TEXTVIEW TO REPRESENT THE CURRENT SCORE FOR THAT PLAYER
    // 3. LOOP THROUGH SCORING COLUMN VALUES AND ADD TO CUMULATIVE SCORE (ADD SCORES IN COLUMN)
    private void tallyResults()
    {
        finalScoreP1 = 0;  // RESET FINAL SCORE VALUES BEFORE CALCULATING
        finalScoreP2 = 0;
        finalScoreP3 = 0;
        finalScoreP4 = 0;

        totalP1Score = findViewById(R.id.totalScore1);  // DEFINE LAYOUT ITEMS
        totalP2Score = findViewById(R.id.totalScore2);
        totalP3Score = findViewById(R.id.totalScore3);
        totalP4Score = findViewById(R.id.totalScore4);

        // PLAYER 1 SCORING
        int[] scoreP1Array = new int[] {R.id.score1_p1, R.id.score2_p1, R.id.score3_p1, R.id.score4_p1,
                R.id.score5_p1, R.id.score6_p1, R.id.score7_p1, R.id.score8_p1, R.id.score9_p1,
                R.id.score10_p1, R.id.score11_p1, R.id.score12_p1, R.id.score13_p1, R.id.score14_p1,
                R.id.score15_p1, R.id.score16_p1, R.id.score17_p1, R.id.score18_p1};

        for(int i = 0; i < scoreP1Array.length; i++)
        {
            TextView cur_scoreValue = findViewById(scoreP1Array[i]);
            if (!cur_scoreValue.getText().toString().equals(""))
            {
                int current_score = Integer.parseInt(cur_scoreValue.getText().toString());
                finalScoreP1 += Integer.parseInt(String.valueOf(current_score));
            }
        }   totalP1Score.setText(String.valueOf(finalScoreP1));

        // PLAYER 2 SCORING
        int[] scoreP2Array = new int[] {R.id.score1_p2, R.id.score2_p2, R.id.score3_p2, R.id.score4_p2,
                R.id.score5_p2, R.id.score6_p2, R.id.score7_p2, R.id.score8_p2, R.id.score9_p2,
                R.id.score10_p2, R.id.score11_p2, R.id.score12_p2, R.id.score13_p2, R.id.score14_p2,
                R.id.score15_p2, R.id.score16_p2, R.id.score17_p2, R.id.score18_p2};

        for(int i = 0; i < scoreP2Array.length; i++)
        {
            TextView cur_scoreValue = findViewById(scoreP2Array[i]);
            if (!cur_scoreValue.getText().toString().equals(""))
            {
                int current_score = Integer.parseInt(cur_scoreValue.getText().toString());
                finalScoreP2 += Integer.parseInt(String.valueOf(current_score));
            }
        }   totalP2Score.setText(String.valueOf(finalScoreP2));

        // PLAYER 3 SCORING
        int[] scoreP3Array = new int[] {R.id.score1_p3, R.id.score2_p3, R.id.score3_p3, R.id.score4_p3,
                R.id.score5_p3, R.id.score6_p3, R.id.score7_p3, R.id.score8_p3, R.id.score9_p3,
                R.id.score10_p3, R.id.score11_p3, R.id.score12_p3, R.id.score13_p3, R.id.score14_p3,
                R.id.score15_p3, R.id.score16_p3, R.id.score17_p3, R.id.score18_p3};

        for(int i = 0; i < scoreP3Array.length; i++)
        {
            TextView cur_scoreValue = findViewById(scoreP3Array[i]);
            if (!cur_scoreValue.getText().toString().equals(""))
            {
                int current_score = Integer.parseInt(cur_scoreValue.getText().toString());
                finalScoreP3 += Integer.parseInt(String.valueOf(current_score));
            }
        }   totalP3Score.setText(String.valueOf(finalScoreP3));

        // PLAYER 4 SCORING
        int[] scoreP4Array = new int[] {R.id.score1_p4, R.id.score2_p4, R.id.score3_p4, R.id.score4_p4,
                R.id.score5_p4, R.id.score6_p4, R.id.score7_p4, R.id.score8_p4, R.id.score9_p4,
                R.id.score10_p4, R.id.score11_p4, R.id.score12_p4, R.id.score13_p4, R.id.score14_p4,
                R.id.score15_p4, R.id.score16_p4, R.id.score17_p4, R.id.score18_p4};

        for(int i = 0; i < scoreP4Array.length; i++)
        {
            TextView cur_scoreValue = findViewById(scoreP4Array[i]);
            if (!cur_scoreValue.getText().toString().equals(""))
            {
                int current_score = Integer.parseInt(cur_scoreValue.getText().toString());
                finalScoreP4 += Integer.parseInt(String.valueOf(current_score));
            }
        }   totalP4Score.setText(String.valueOf(finalScoreP4));
    }

}





