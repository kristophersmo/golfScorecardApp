package com.example.golfhandicapapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import android.annotation.SuppressLint;

public class CompleteGame extends AppCompatActivity
{
    TextView scoreP1, scoreP2, scoreP3, scoreP4;  // SCORING RESULTS PER PLAYER
    Button discardResults, keepResults;  // BUTTONS FOR HANDLING RESULTS
    ArrayList<String> activePlayers;  // ARRAYLIST TO HOLD PLAYERS IN CURRENT GAME
    ArrayList<Integer> activeHandicaps;  // ARRAYLIST TO HOLD GOLFER HANDICAPS IN CURRENT GAME
    int finalScoreP1, finalScoreP2, finalScoreP3, finalScoreP4;
    DatabaseCourses courseDB;  // FOR COURSE DATABASE FUNCTIONS
    DatabaseGolfers golferDB;  // FOR GOLFER DATABASE FUNCTIONS

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_game);

        // DEFINING END GAME BUTTONS
        discardResults = findViewById(R.id.killResults);
        keepResults = findViewById(R.id.saveResults);

        // DATABASE HELPER VARIABLE FOR COURSE DB
        courseDB = new DatabaseCourses(this);
        // DATABASE HELPER VARIABLE FOR GOLFERS DB
        golferDB = new DatabaseGolfers(this);

        // RETRIEVING THE PLAYER SCORING DATA THAT WAS DETERMINED IN PREVIOUS ACTIVITY
        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;

        activePlayers = intent.getStringArrayListExtra("players");
        activeHandicaps = intent.getIntegerArrayListExtra("handicaps");
        finalScoreP1 = bundle.getInt("scoreP1");
        finalScoreP2 = bundle.getInt("scoreP2");
        finalScoreP3 = bundle.getInt("scoreP3");
        finalScoreP4 = bundle.getInt("scoreP4");

        int[] playerArray = new int[] {R.id.nameP1, R.id.nameP2, R.id.nameP3, R.id.nameP4};
        for (int i = 0; i < activePlayers.size(); i++)
        {
            String getPlayer = activePlayers.get(i);
            TextView cur_player = findViewById(playerArray[i]);
            cur_player.setText(getPlayer);
        }

        // APPLYING THE PLAYER SCORING DATA THAT WAS DETERMINED IN PREVIOUS ACTIVITY
        scoreP1 = findViewById(R.id.scoreP1);
        scoreP2 = findViewById(R.id.scoreP2);
        scoreP3 = findViewById(R.id.scoreP3);
        scoreP4 = findViewById(R.id.scoreP4);

        scoreP1.setText("FINAL SCORE: " + String.format(String.valueOf(finalScoreP1)));
        scoreP2.setText("FINAL SCORE: " + String.format(String.valueOf(finalScoreP2)));
        scoreP3.setText("FINAL SCORE: " + String.format(String.valueOf(finalScoreP3)));
        scoreP4.setText("FINAL SCORE: " + String.format(String.valueOf(finalScoreP4)));


        this.discardResults.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });


        this.keepResults.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });
    }
}
