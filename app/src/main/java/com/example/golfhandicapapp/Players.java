package com.example.golfhandicapapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Objects;
import static java.lang.Integer.parseInt;

public class Players extends AppCompatActivity implements OnItemSelectedListener
{
    ActionBar actionBar;  // APP HEADER
    Spinner spinner_players;  // SPINNER DROP-DOWN MENU (PLAYERS)
    Button add, create, save, next;  // USER OPTION AND NAVIGATION BUTTONS
    EditText firstName, lastName, handicap;  // FIELDS FOR PLAYER INPUT
    TextView showAllPlayers;  // OUTPUT LIST OF GOLFERS
    DatabaseGolfers golferDB;  // FOR GOLFER DATABASE FUNCTIONS
    ArrayList<String> activePlayers;  // ARRAYLIST TO HOLD PLAYERS IN CURRENT GAME
    ArrayList<Integer> activeHandicaps;  // ARRAYLIST TO HOLD GOLFER HANDICAPS IN CURRENT GAME
    Integer playerCount = 0;  // INITIALIZE PLAYER COUNT (MAX OF 4)
    static Players status;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        // ACTION BAR FOR NAVIGATING BACKWARDS
        actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setTitle("BACK");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // DEFINING VARIABLES FOR EACH LAYOUT ITEM
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        handicap = findViewById(R.id.handicap);
        add = findViewById(R.id.addGolfer);
        create = findViewById(R.id.createGolfer);
        save = findViewById(R.id.saveToList);
        showAllPlayers = findViewById(R.id.playerList);
        spinner_players = findViewById(R.id.playerDD);
        next = findViewById(R.id.goToCourse);

        // INITIALIZE LIST TO HOLD SELECTED GOLFERS
        activePlayers = new ArrayList<>();
        // INITIALIZE LIST TO HOLD SELECTED GOLFER HANDICAPS
        activeHandicaps = new ArrayList<>();

        // DATABASE HELPER VARIABLE FOR GOLFERS DB
        golferDB = new DatabaseGolfers(this);

        //  FOR FINISHING ACTIVITY (CALL GET INSTANCE)
        status = this;

        // KEEP PLAYER DROP-DOWN MENU UPDATED USING PLAYERS DB
        refreshPlayerMenu(golferDB);

        // WAIT FOR USER TO SELECT A PLAYER FROM THE LIST
        spinner_players.setOnItemSelectedListener(this);


        // ON-CLICK, GET THE SELECTED PLAYER'S HANDICAP AND ADD PLAYER TO ROSTER
        // 1. INCREMENT THE PLAYER COUNT AND ASSIGN PLAYER TO STRING curPlayer
        // 2. USE THIS CURRENT PLAYER STRING TO SEARCH THE GOLFER DB
        // 3. AFTER FINDING THE PLAYER, GET THE HANDICAP AND ADD IT TO THE HANDICAP ARRAYLIST
        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                playerCount++;
                String curPlayer = spinner_players.getSelectedItem().toString();

                String handicapQuery = "select * from " +  "golfers" +
                        " where " + "firstName || ' ' || lastName" + " LIKE '%' || ? || '%'";
                Cursor cursor = golferDB.getReadableDatabase().rawQuery(handicapQuery, new String[] {curPlayer});

                while (cursor.moveToNext())
                {
                    activeHandicaps.add(cursor.getInt(3));  // STORE HANDICAP VALUE
                }
                cursor.close();

                // CONDITIONALLY ADD SELECTED PLAYER TO ACTIVE PLAYERS ROSTER
                // CHECKS THE NUMBER OF PLAYERS ADDED, DENIES IF PLAYER COUNT > MAX
                if (playerCount <= 4)
                {
                    activePlayers.add(curPlayer);
                    String gamePlayers = "";
                    for (int i = 0; i < activePlayers.size(); i++)
                    {
                        gamePlayers += activePlayers.get(i) + "\n";
                    }
                    showAllPlayers.setText(gamePlayers);
                    if (!gamePlayers.matches(""))
                    {
                        next.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "MAX PLAYERS REACHED", Toast.LENGTH_LONG).show();
                }
            }
        });


        // ON-CLICK, DISPLAY FIELDS FOR NEW GOLFER INPUT
        create.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                firstName.setVisibility(View.VISIBLE);
                lastName.setVisibility(View.VISIBLE);
                handicap.setVisibility(View.VISIBLE);
                save.setVisibility(View.VISIBLE);
            }
        });


        // ON-CLICK, SAVE NEW GOLFER TO GOLFER DB
        // 1. STORE STRINGS ENTERED IN GOLFER INPUT FIELDS
        // 2. VERIFY NON-NULL INPUT REQUIREMENTS BEFORE WRITING TO GOLFER DB
        // 3. IF OK, CONVERT HANDICAP STRING TO INT AND CALL saveGolferData() IN GOLFER DATABASE
        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String fName = firstName.getText().toString();
                String lName = lastName.getText().toString();
                String hCap = handicap.getText().toString();

                if (fName.matches("") || lName.matches("") || hCap.matches(""))
                {
                    Toast.makeText(getApplicationContext(), "PLEASE COMPLETE ALL FIELDS", Toast.LENGTH_LONG).show();
                }
                else
                {
                    int golferHandicap = parseInt(hCap);
                    golferDB.saveGolferData(fName, lName, golferHandicap);  // WRITE TO GOLFER DB
                    Toast.makeText(getApplicationContext(), "PLAYER ADD SUCCESSFUL", Toast.LENGTH_SHORT).show();

                    refreshPlayerMenu(golferDB);  // UPDATE SPINNER DROP-DOWN MENU

                    firstName.setVisibility(View.INVISIBLE);  // RESET & HIDE NEW PLAYER INPUT FIELDS
                    lastName.setVisibility(View.INVISIBLE);
                    handicap.setVisibility(View.INVISIBLE);
                    save.setVisibility(View.INVISIBLE);
                    firstName.setText("");
                    lastName.setText("");
                    handicap.setText("");
                }
                spinner_players.setSelection(0);  // RESET THE PLAYER DROP-DOWN MENU
            }
        });


        // ON-CLICK, ACCEPT ALL PLAYER SELECTIONS, SEND DATA TO NEXT ACTIVITY (COURSE.CLASS)
        next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent sendPlayers = new Intent(Players.this, Course.class);
                sendPlayers.putExtra("players", activePlayers);
                sendPlayers.putExtra("handicaps", activeHandicaps);
                startActivity(sendPlayers);
                //finish();
            }
        });
    }


    // THIS FUNCTION KEEPS THE PLAYER DROP-DOWN MENU UPDATED WITH BOTH NEW AND EXISTING GOLFERS
    // USES POSITION CURSOR TO READ ALL GOLFERS FROM GOLFERS DB, THEN REFRESHES ARRAY ADAPTER
    private void refreshPlayerMenu(DatabaseGolfers golferDB)
    {
        Cursor cursor = golferDB.getReadableDatabase().rawQuery("select * from golfers", null);
        String[] arrGolfer = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int i = 0; i < arrGolfer.length; i++)
        {
            arrGolfer[i] = cursor.getString(1) + " " + cursor.getString(2);
            cursor.moveToNext();
        }
        cursor.close();

        ArrayAdapter<String> displayPlayers = new ArrayAdapter<>(this, R.layout.select_golfer, arrGolfer);
        // APPLYING CUSTOM SPINNER MENU FROM RESOURCE
        displayPlayers.setDropDownViewResource(R.layout.player_dropdown);
        spinner_players.setAdapter(displayPlayers);
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
    public static Players getInstance()
    {
        return status;
    }
}





