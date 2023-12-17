package com.example.golfhandicapapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.Objects;
import com.google.android.material.textfield.TextInputLayout;

public class CourseInput extends AppCompatActivity implements OnItemSelectedListener
{
    Spinner spinner_holes;  // SPINNER DROP-DOWN MENU (NUMBER OF HOLES)
    Button cancelCourse, acceptCourse;  // TO SAVE COURSE INPUT
    TextInputLayout courseName, courseRating, courseSlope;  // FIELDS FOR COURSE INPUT
    TextView enter_par;  // SHOW INSTRUCTIONS FOR TABLE INPUT
    TableLayout courseTable;  // TABLE LAYOUT FOR COURSE INPUT
    TableRow row1, row2, row3, row4;  // TABLE ROWS (FOR COURSE INPUT)
    DatabaseCourses courseDB;  // FOR COURSE DATABASE FUNCTIONS
    ArrayList<String> activePlayers;  // ARRAYLIST TO HOLD PLAYERS IN CURRENT GAME
    ArrayList<Integer> activeHandicaps;  // ARRAYLIST TO HOLD GOLFER HANDICAPS IN CURRENT GAME
    String curHoles;  // HOLDS CURRENT VALUE, NUMBER OF HOLES ON COURSE
    EditText par_1, par_2, par_3, par_4, par_5, par_6, par_7, par_8, par_9;  // FRONT 9 PARS
    EditText par_10, par_11, par_12, par_13, par_14, par_15, par_16, par_17, par_18;  // BACK 9 PARS


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_input);

        // DEFINING VARIABLES FOR EACH LAYOUT ITEM
        row1 = findViewById(R.id.row1);
        row2 = findViewById(R.id.row2);
        row3 = findViewById(R.id.row3);
        row4 = findViewById(R.id.row4);
        courseTable = findViewById(R.id.tableLayout1);
        spinner_holes = findViewById(R.id.holesDD);
        courseName = findViewById(R.id.courseName);
        courseRating = findViewById(R.id.courseDifficulty);
        courseSlope = findViewById(R.id.courseSlope);
        par_1 = findViewById(R.id.parHole1);
        par_2 = findViewById(R.id.parHole2);
        par_3 = findViewById(R.id.parHole3);
        par_4 = findViewById(R.id.parHole4);
        par_5 = findViewById(R.id.parHole5);
        par_6 = findViewById(R.id.parHole6);
        par_7 = findViewById(R.id.parHole7);
        par_8 = findViewById(R.id.parHole8);
        par_9 = findViewById(R.id.parHole9);
        par_10 = findViewById(R.id.parHole10);
        par_11 = findViewById(R.id.parHole11);
        par_12 = findViewById(R.id.parHole12);
        par_13 = findViewById(R.id.parHole13);
        par_14 = findViewById(R.id.parHole14);
        par_15 = findViewById(R.id.parHole15);
        par_16 = findViewById(R.id.parHole16);
        par_17 = findViewById(R.id.parHole17);
        par_18 = findViewById(R.id.parHole18);
        enter_par = findViewById(R.id.instruction4);
        cancelCourse = findViewById(R.id.cancelCourse);
        acceptCourse = findViewById(R.id.acceptCourse);

        // RETRIEVING THE PLAYER DATA THAT WAS SELECTED (FROM PLAYERS CLASS)
        Intent intent = getIntent();
        activePlayers = intent.getStringArrayListExtra("players");
        activeHandicaps = intent.getIntegerArrayListExtra("handicaps");
        // Toast.makeText(getApplicationContext(), activePlayers.toString(), Toast.LENGTH_SHORT).show();

        // DATABASE HELPER VARIABLE FOR COURSE DB
        courseDB = new DatabaseCourses(this);

        // APPLYING CUSTOM SPINNER MENU FROM RESOURCE
        ArrayAdapter<CharSequence> displayHoles = ArrayAdapter.createFromResource(this,
                R.array.numberOfHoles, R.layout.select_holes);
        spinner_holes.setAdapter(displayHoles);
        spinner_holes.setOnItemSelectedListener(this);


        cancelCourse.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // PASS PLAYER INFO BACK TO COURSE SELECTION ACTIVITY
                Intent passPlayers = new Intent(CourseInput.this, Course.class);
                passPlayers.putExtra("players", activePlayers);
                passPlayers.putExtra("handicaps", activeHandicaps);
                startActivity(passPlayers);
            }
        });


        // ON-CLICK, RECEIVES COURSE INPUT DATA AND WRITES TO COURSE DB (IF ACCEPTABLE)
        // 1. GETS COURSE DATA FROM USER INPUT IN APPROPRIATE FORMAT FOR COURSE DB
        // 2. USES IF STATEMENTS TO CHECK FOR INVALID INPUT CHARACTERISTICS
        // 3. IF ALL DATA IS ACCEPTABLE, SAVE COURSE TO COURSE DB
        acceptCourse.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String name = String.valueOf(Objects.requireNonNull(courseName.getEditText()).getText());

                // FORMATTING COURSE DIFFICULTY TO ONE DECIMAL PLACE
                double rawDifficulty = Double.parseDouble(String.valueOf(Objects.requireNonNull(courseRating.getEditText()).getText()));
                double difficulty = Math.round(rawDifficulty * 10.0) / 10.0;

                // FORMATTING COURSE SLOPE TO ONE DECIMAL PLACE
                double rawSlope = Double.parseDouble(String.valueOf(Objects.requireNonNull(courseSlope.getEditText()).getText()));
                double slope = Math.round(rawSlope * 10.0) / 10.0;

                // GETTING INT VALUES FROM USER INPUT
                int holes = Integer.parseInt(curHoles);
                int par1 = Integer.parseInt(par_1.getText().toString());
                int par2 = Integer.parseInt(par_2.getText().toString());
                int par3 = Integer.parseInt(par_3.getText().toString());
                int par4 = Integer.parseInt(par_4.getText().toString());
                int par5 = Integer.parseInt(par_5.getText().toString());
                int par6 = Integer.parseInt(par_6.getText().toString());
                int par7 = Integer.parseInt(par_7.getText().toString());
                int par8 = Integer.parseInt(par_8.getText().toString());
                int par9 = Integer.parseInt(par_9.getText().toString());
                int par10 = Integer.parseInt(par_10.getText().toString());
                int par11 = Integer.parseInt(par_11.getText().toString());
                int par12 = Integer.parseInt(par_12.getText().toString());
                int par13 = Integer.parseInt(par_13.getText().toString());
                int par14 = Integer.parseInt(par_14.getText().toString());
                int par15 = Integer.parseInt(par_15.getText().toString());
                int par16 = Integer.parseInt(par_16.getText().toString());
                int par17 = Integer.parseInt(par_17.getText().toString());
                int par18 = Integer.parseInt(par_18.getText().toString());

                // BASIC VALIDATION OF COURSE INPUT REQUIREMENTS (CAN BE ADJUSTED IF NEEDED)
                if (name.matches("") ||
                        (155 < difficulty || difficulty < 55) ||
                        (155 < slope || slope < 55) ||
                        (holes == 0) ||
                        (10 < par1 || par1 < 0) ||
                        (10 < par2 || par2 < 0) ||
                        (10 < par3 || par3 < 0) ||
                        (10 < par4 || par4 < 0) ||
                        (10 < par5 || par5 < 0) ||
                        (10 < par6 || par6 < 0) ||
                        (10 < par7 || par7 < 0) ||
                        (10 < par8 || par8 < 0) ||
                        (10 < par9 || par9 < 0) ||
                        (10 < par10 || par10 < 0) ||
                        (10 < par11 || par11 < 0) ||
                        (10 < par12 || par12 < 0) ||
                        (10 < par13 || par13 < 0) ||
                        (10 < par14 || par14 < 0) ||
                        (10 < par15 || par15 < 0) ||
                        (10 < par16 || par16 < 0) ||
                        (10 < par17 || par17 < 0) ||
                        (10 < par18 || par18 < 0))
                {
                    Toast.makeText(getApplicationContext(), "INSUFFICIENT COURSE DATA", Toast.LENGTH_LONG).show();
                }
                else
                {
                    courseDB.saveCourseData(name, difficulty, slope, holes,
                            par1, par2, par3, par4, par5, par6, par7, par8, par9,
                            par10, par11, par12, par13, par14, par15, par16, par17, par18);
                    Toast.makeText(getApplicationContext(), "COURSE ADD SUCCESSFUL", Toast.LENGTH_SHORT).show();

                    // PASS PLAYER INFO BACK TO COURSE SELECTION ACTIVITY
                    Intent passPlayers = new Intent(CourseInput.this, Course.class);
                    passPlayers.putExtra("players", activePlayers);
                    passPlayers.putExtra("handicaps", activeHandicaps);
                    startActivity(passPlayers);
                }
            }
        });
    }


    // DISPLAYS THE DESIRED COURSE INPUT TABLES BASED ON THE NUMBER OF HOLES SELECTED
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long l)
    {
        parent.getItemAtPosition(pos).toString();

        curHoles = spinner_holes.getSelectedItem().toString();
        if (curHoles.matches("9"))
        {
            enter_par.setVisibility(View.VISIBLE);
            row1.setVisibility(View.VISIBLE);
            row2.setVisibility(View.VISIBLE);
            row3.setVisibility(View.INVISIBLE);
            row4.setVisibility(View.INVISIBLE);
        }
        else if (curHoles.matches("18"))
        {
            enter_par.setVisibility(View.VISIBLE);
            row1.setVisibility(View.VISIBLE);
            row2.setVisibility(View.VISIBLE);
            row3.setVisibility(View.VISIBLE);
            row4.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }
}
