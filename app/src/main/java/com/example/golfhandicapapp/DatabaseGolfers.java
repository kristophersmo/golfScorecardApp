package com.example.golfhandicapapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;


public class DatabaseGolfers extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "golferDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "golfers";
    private static final String KEY_ID = "id";
    private static final String KEY_FIRSTNAME = "firstName";
    private static final String KEY_LASTNAME = "lastName";
    private static final String KEY_HANDICAP = "handicap";


    // CONSTRUCT SQLITE DATABASE (GOLFER DB)
    public DatabaseGolfers(@Nullable Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // ESTABLISH TABLE WITH COLUMNS FOR ID#, FIRSTNAME, LASTNAME, AND HANDICAP
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_FIRSTNAME + " TEXT, " + KEY_LASTNAME + " TEXT, " + KEY_HANDICAP + " TEXT)");
    }


    // DETERMINE IF GOLFER TABLE IS PRE-EXISTING
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    // FUNCTION TO ADD A GOLFER TO DB
    public void saveGolferData(
            String firstName,
            String lastName,
            int handicap)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues dataValues = new ContentValues();
        dataValues.put(KEY_FIRSTNAME, firstName);
        dataValues.put(KEY_LASTNAME, lastName);
        dataValues.put(KEY_HANDICAP, handicap);
        db.insert(TABLE_NAME, null, dataValues);
    }


    // FUNCTION TO GET GOLFER DATA
    public ArrayList<ModalContact2> getGolferData()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<ModalContact2> arrayList = new ArrayList<>();

        while (cursor.moveToNext())
        {
            ModalContact2 modalContact = new ModalContact2();
            modalContact.id = cursor.getInt(0);
            modalContact.firstName = cursor.getString(1);
            modalContact.lastName = cursor.getString(2);
            modalContact.handicap = cursor.getInt(3);
            arrayList.add(modalContact);
        }
        cursor.close();
        return arrayList;
    }

}
