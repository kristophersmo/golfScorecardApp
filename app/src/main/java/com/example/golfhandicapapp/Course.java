package com.example.golfhandicapapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Objects;

public class Course extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    ActionBar actionBar;  // APP HEADER
    Spinner spinner_courses;  // SPINNER DROP-DOWN MENU (COURSES)
    Button add, create, accept;  // USER OPTION AND NAVIGATION BUTTONS
    TextView showPlayers, showCourse, pars;  // OUTPUT COURSE NAME
    DatabaseCourses courseDB;  // FOR COURSE DATABASE FUNCTIONS
    ArrayList<String> activePlayers;  // ARRAYLIST TO HOLD PLAYERS IN CURRENT GAME
    ArrayList<Integer> activeHandicaps;  // ARRAYLIST TO HOLD GOLFER HANDICAPS IN CURRENT GAME
    ArrayList<String> activeCourse;  // ARRAYLIST TO HOLD COURSE IN CURRENT GAME
    ArrayList<Integer> activePars;  // ARRAYLIST TO HOLD PARS IN CURRENT GAME
    Integer courseCount = 0;  // INITIALIZE COURSE COUNT (MAX OF 1)
    String curCourse;  // STRING TO HOLD THE SELECTED COURSE NAME
    float courseRating, courseSlope;  // FLOATS TO HOLD THE SELECTED COURSE RATING AND SLOPE
    int courseHoles;  // INT TO HOLD THE NUMBER OF HOLES ON THE SELECTED COURSE
    static Course status;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        // ACTION BAR FOR NAVIGATING BACKWARDS
        actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setTitle("BACK");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // DEFINING VARIABLES FOR EACH LAYOUT ITEM
        add = findViewById(R.id.addCourse);
        create = findViewById(R.id.createCourse);
        showPlayers = findViewById(R.id.playerList);
        showCourse = findViewById(R.id.courseList);
        pars = findViewById(R.id.pars);
        spinner_courses = findViewById(R.id.courseDD);
        accept = findViewById(R.id.accept);

        // INITIALIZE LIST TO HOLD THE SELECTED (ACTIVE) COURSE
        activeCourse = new ArrayList<>();

        // DATABASE HELPER VARIABLE FOR COURSE DB
        courseDB = new DatabaseCourses(this);

        //  FOR FINISHING ACTIVITY (CALL GET INSTANCE)
        status = this;

        // THIS FUNCTION KEEPS THE COURSE DROP-DOWN MENU UPDATED USING COURSE DB
        refreshCourseMenu(courseDB);

        // RETRIEVING THE PLAYER DATA THAT WAS SELECTED (FROM PLAYERS CLASS)
        Intent intent = getIntent();
        activePlayers = intent.getStringArrayListExtra("players");
        activeHandicaps = intent.getIntegerArrayListExtra("handicaps");

        displayPlayers(activePlayers);

        // WAIT FOR USER TO SELECT A COURSE FROM THE LIST
        spinner_courses.setOnItemSelectedListener(this);


        // ON-CLICK, ADDS CURRENT SPINNER SELECTION TO THE ACTIVE COURSE DISPLAY
        // CHECKS FOR A SINGLE COURSE ADD, DENIES IF COURSE COUNT > 1
        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                curCourse = spinner_courses.getSelectedItem().toString();
                courseCount++;
                if (courseCount <= 1)
                {
                    activeCourse.add(curCourse);
                    String gameCourse = "";
                    for (int i = 0; i < activeCourse.size(); i++)
                    {
                        gameCourse += activeCourse.get(i);
                    }
                    showCourse.setText(gameCourse);
                    accept.setVisibility(View.VISIBLE);
                    storeCourseInfo(gameCourse);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "CHOOSE ONLY ONE COURSE", Toast.LENGTH_LONG).show();
                }
            }
        });


        // ON-CLICK, SENDS USER TO THE COURSE INPUT ACTIVITY FOR INPUT OF A NEW COURSE
        // PASSES PLAYER INFO SUCH THAT IT IS NOT LOST
        create.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent passPlayers = new Intent(Course.this, CourseInput.class);
                passPlayers.putExtra("players", activePlayers);
                passPlayers.putExtra("handicaps", activeHandicaps);
                startActivity(passPlayers);
            }
        });


        // ON-CLICK, ACCEPTS USER SELECTION AND STARTS THE 'START ROUND' ACTIVITY
        // PASSES THE LIST OF ACTIVE PLAYERS AND COURSE SELECTION WITH INTENT
        accept.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent passRoundInfo = new Intent(Course.this, StartRound.class);
                passRoundInfo.putExtra("players", activePlayers);
                passRoundInfo.putExtra("handicaps", activeHandicaps);
                passRoundInfo.putExtra("course", activeCourse);
                passRoundInfo.putExtra("pars", activePars);

                Bundle courseInfo = new Bundle();
                courseInfo.putFloat("rating", courseRating);
                courseInfo.putFloat("slope", courseSlope);
                courseInfo.putInt("holes", courseHoles);
                passRoundInfo.putExtras(courseInfo);
                startActivity(passRoundInfo);

                // FINISH PLAYERS AND COURSE ACTIVITIES WHEN SCORECARD IS LOADED
                Players.getInstance().finish();
                finish();
            }
        });
    }


    // THIS FUNCTION KEEPS THE COURSE DROP-DOWN MENU UPDATED WITH BOTH NEW AND EXISTING COURSES
    // USES POSITION CURSOR TO READ ALL COURSES FROM COURSES DB, THEN REFRESHES ARRAY ADAPTER
    private void refreshCourseMenu(DatabaseCourses courseDB)
    {
        Cursor cursor = courseDB.getReadableDatabase().rawQuery("select * from courses", null);
        String[] arrCourse = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int i = 0; i < arrCourse.length; i++)
        {
            arrCourse[i] = cursor.getString(1);
            cursor.moveToNext();
        }
        cursor.close();

        // APPLYING CUSTOM SPINNER MENU FROM RESOURCE
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.select_course, arrCourse);
        adapter1.setDropDownViewResource(R.layout.player_dropdown);
        spinner_courses.setAdapter(adapter1);
    }


    // THIS FUNCTION FETCHES A LIST OF PAR VALUES FROM THE SELECTED COURSE
    // USES CUSTOM TABLE SEARCH TO LOCATE courseName COLUMN WITHIN courses TABLE
    // THE CURRENT COURSE IS PASSED TO THIS METHOD AND TARGETED IN THE COURSE DB SEARCH
    // RESULTS ARE COLLECTED AND ASSIGNED TO THE PAR ARRAYLIST
    private void storeCourseInfo(String gameCourse)
    {
        String courseRowQuery = "select * from " +  "courses" +
                " where " + "courseName" + " LIKE '%' || ? || '%'";
        Cursor cursor = courseDB.getReadableDatabase().rawQuery(courseRowQuery, new String[] {gameCourse});

        // VERIFY PAR VALUES ARE PRESENT BEFORE ADDING TO ARRAYLIST
        if (cursor.getCount() == 0)
        {
            Toast.makeText(getApplicationContext(), "NO DATA FOUND", Toast.LENGTH_LONG).show();
            return;
        }

        // INITIALIZE ARRAYLIST THAT HOLDS THE PAR VALUES FOR EACH HOLE
        activePars = new ArrayList<>();

        while (cursor.moveToNext())
        {
            courseRating = cursor.getFloat(2);   // STORE THE SELECTED COURSE RATING
            courseSlope = cursor.getFloat(3);    // STORE THE SELECTED COURSE SLOPE
            courseHoles = cursor.getInt(4);      // STORE THE SELECTED COURSE NUM HOLES

            // STORE THE SELECTED COURSE PARS, SHIFT LOOP TO START AT INDEX 5
            for (int i = 5; i < courseHoles + 5; i++)
            {
                activePars.add(cursor.getInt(i));
            }
        }
        cursor.close();
        // TEST TOASTS USED DURING DEVELOPMENT
        // Toast.makeText(getApplicationContext(), activeHandicaps.toString(), Toast.LENGTH_LONG).show();
        // Toast.makeText(getApplicationContext(), activePars.toString(), Toast.LENGTH_LONG).show();
    }


    // RETRIEVING THE PLAYERS THAT WERE SELECTED (FROM PLAYERS CLASS)
    private void displayPlayers(ArrayList activePlayers)
    {
        String gamePlayers = "";
        for (int i = 0; i < activePlayers.size(); i++)
        {
            gamePlayers += activePlayers.get(i) + "\n";
        }
        showPlayers.setText(gamePlayers);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long l)
    {
        parent.getItemAtPosition(pos).toString();
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }


    // THIS FUNCTION IS USED TO FINISH THE PLAYERS ACTIVITY WHEN SCORECARD IS LOADED
    public static Course getInstance()
    {
        return status;
    }
}





