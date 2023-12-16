package com.example.golfhandicapapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class DatabaseCourses extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "courseDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "courses";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "courseName";
    private static final String KEY_RATING = "courseRating";
    private static final String KEY_SLOPE = "courseSlope";
    private static final String KEY_HOLES = "holes";
    private static final String KEY_PAR1 = "par1";
    private static final String KEY_PAR2 = "par2";
    private static final String KEY_PAR3 = "par3";
    private static final String KEY_PAR4 = "par4";
    private static final String KEY_PAR5 = "par5";
    private static final String KEY_PAR6 = "par6";
    private static final String KEY_PAR7 = "par7";
    private static final String KEY_PAR8 = "par8";
    private static final String KEY_PAR9 = "par9";
    private static final String KEY_PAR10 = "par10";
    private static final String KEY_PAR11 = "par11";
    private static final String KEY_PAR12 = "par12";
    private static final String KEY_PAR13 = "par13";
    private static final String KEY_PAR14 = "par14";
    private static final String KEY_PAR15 = "par15";
    private static final String KEY_PAR16 = "par16";
    private static final String KEY_PAR17 = "par17";
    private static final String KEY_PAR18 = "par18";


    // CONSTRUCT SQLITE DATABASE (COURSE DB)
    public DatabaseCourses(@Nullable Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // ESTABLISH TABLE WITH COLUMNS FOR ID#, NAME, RATING, SLOPE, HOLES, AND PAR FOR EACH HOLE
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME + " TEXT, " + KEY_RATING + " TEXT, " + KEY_SLOPE + " TEXT, " + KEY_HOLES + " TEXT, "
                + KEY_PAR1 + " TEXT, " + KEY_PAR2 + " TEXT, " + KEY_PAR3 + " TEXT, " + KEY_PAR4 + " TEXT, "
                + KEY_PAR5 + " TEXT, " + KEY_PAR6 + " TEXT, " + KEY_PAR7 + " TEXT, " + KEY_PAR8 + " TEXT, "
                + KEY_PAR9 + " TEXT, " + KEY_PAR10 + " TEXT, " + KEY_PAR11 + " TEXT, " + KEY_PAR12 + " TEXT, "
                + KEY_PAR13 + " TEXT, " + KEY_PAR14 + " TEXT, " + KEY_PAR15 + " TEXT, " + KEY_PAR16 + " TEXT, "
                + KEY_PAR17 + " TEXT, " + KEY_PAR18 + " TEXT)");
    }


    // DETERMINE IF COURSE TABLE IS PRE-EXISTING
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    // FUNCTION TO ADD A COURSE TO DB
    public void saveCourseData(
            String courseName,
            double courseRating,
            double courseSlope,
            int holes,
            int parHole1,
            int parHole2,
            int parHole3,
            int parHole4,
            int parHole5,
            int parHole6,
            int parHole7,
            int parHole8,
            int parHole9,
            int parHole10,
            int parHole11,
            int parHole12,
            int parHole13,
            int parHole14,
            int parHole15,
            int parHole16,
            int parHole17,
            int parHole18)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues dataValues = new ContentValues();
        dataValues.put(KEY_NAME, courseName);
        dataValues.put(KEY_RATING, courseRating);
        dataValues.put(KEY_SLOPE, courseSlope);
        dataValues.put(KEY_HOLES, holes);
        dataValues.put(KEY_PAR1, parHole1);
        dataValues.put(KEY_PAR2, parHole2);
        dataValues.put(KEY_PAR3, parHole3);
        dataValues.put(KEY_PAR4, parHole4);
        dataValues.put(KEY_PAR5, parHole5);
        dataValues.put(KEY_PAR6, parHole6);
        dataValues.put(KEY_PAR7, parHole7);
        dataValues.put(KEY_PAR8, parHole8);
        dataValues.put(KEY_PAR9, parHole9);
        dataValues.put(KEY_PAR10, parHole10);
        dataValues.put(KEY_PAR11, parHole11);
        dataValues.put(KEY_PAR12, parHole12);
        dataValues.put(KEY_PAR13, parHole13);
        dataValues.put(KEY_PAR14, parHole14);
        dataValues.put(KEY_PAR15, parHole15);
        dataValues.put(KEY_PAR16, parHole16);
        dataValues.put(KEY_PAR17, parHole17);
        dataValues.put(KEY_PAR18, parHole18);
        db.insert(TABLE_NAME, null, dataValues);
    }

    // FUNCTION TO GET COURSE DATA
    public ArrayList<ModalContact> getCourseData()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<ModalContact> arrayList = new ArrayList<>();

        while (cursor.moveToNext())
        {
            ModalContact modalContact = new ModalContact();

            modalContact.id = cursor.getInt(0);
            modalContact.courseName = cursor.getString(1);
            modalContact.courseRating = Double.parseDouble(cursor.getString(2));
            modalContact.courseSlope = Double.parseDouble(cursor.getString(3));
            modalContact.holes = cursor.getInt(4);
            modalContact.par1 = cursor.getInt(5);
            modalContact.par2 = cursor.getInt(6);
            modalContact.par3 = cursor.getInt(7);
            modalContact.par4 = cursor.getInt(8);
            modalContact.par5 = cursor.getInt(9);
            modalContact.par6 = cursor.getInt(10);
            modalContact.par7 = cursor.getInt(11);
            modalContact.par8 = cursor.getInt(12);
            modalContact.par9 = cursor.getInt(13);
            modalContact.par10 = cursor.getInt(14);
            modalContact.par11 = cursor.getInt(15);
            modalContact.par12 = cursor.getInt(16);
            modalContact.par13 = cursor.getInt(17);
            modalContact.par14 = cursor.getInt(18);
            modalContact.par15 = cursor.getInt(19);
            modalContact.par16 = cursor.getInt(20);
            modalContact.par17 = cursor.getInt(21);
            modalContact.par18 = cursor.getInt(22);

            arrayList.add(modalContact);
        }
        cursor.close();
        return arrayList;
    }


    // GETTING SINGLE COURSE DATA
    public ArrayList<ModalContact> getPars(String theCourse)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{"%"+theCourse+"%"}, KEY_NAME + "=?", null, null, null, null);

        ArrayList<ModalContact> arrayList2 = new ArrayList<>();

        while (cursor.moveToNext())
        {
            ModalContact modalContact = new ModalContact();
            modalContact.par1 = cursor.getInt(5);
            modalContact.par2 = cursor.getInt(6);
            modalContact.par3 = cursor.getInt(7);
            modalContact.par4 = cursor.getInt(8);
            modalContact.par5 = cursor.getInt(9);
            modalContact.par6 = cursor.getInt(10);
            modalContact.par7 = cursor.getInt(11);
            modalContact.par8 = cursor.getInt(12);
            modalContact.par9 = cursor.getInt(13);
            modalContact.par10 = cursor.getInt(14);
            modalContact.par11 = cursor.getInt(15);
            modalContact.par12 = cursor.getInt(16);
            modalContact.par13 = cursor.getInt(17);
            modalContact.par14 = cursor.getInt(18);
            modalContact.par15 = cursor.getInt(19);
            modalContact.par16 = cursor.getInt(20);
            modalContact.par17 = cursor.getInt(21);
            modalContact.par18 = cursor.getInt(22);

            arrayList2.add(modalContact);
        }
        cursor.close();
        return arrayList2;
    }

}
