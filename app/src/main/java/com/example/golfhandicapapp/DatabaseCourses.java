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
    private static final String KEY_DIFF1 = "diff1";
    private static final String KEY_DIFF2 = "diff2";
    private static final String KEY_DIFF3 = "diff3";
    private static final String KEY_DIFF4 = "diff4";
    private static final String KEY_DIFF5 = "diff5";
    private static final String KEY_DIFF6 = "diff6";
    private static final String KEY_DIFF7 = "diff7";
    private static final String KEY_DIFF8 = "diff8";
    private static final String KEY_DIFF9 = "diff9";
    private static final String KEY_DIFF10 = "diff10";
    private static final String KEY_DIFF11 = "diff11";
    private static final String KEY_DIFF12 = "diff12";
    private static final String KEY_DIFF13 = "diff13";
    private static final String KEY_DIFF14 = "diff14";
    private static final String KEY_DIFF15 = "diff15";
    private static final String KEY_DIFF16 = "diff16";
    private static final String KEY_DIFF17 = "diff17";
    private static final String KEY_DIFF18 = "diff18";


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
                + KEY_PAR17 + " TEXT, " + KEY_PAR18 + " TEXT, " + KEY_DIFF1 + " TEXT, " + KEY_DIFF2 + " TEXT, "
                + KEY_DIFF3 + " TEXT, " + KEY_DIFF4 + " TEXT, " + KEY_DIFF5 + " TEXT, " + KEY_DIFF6 + " TEXT, "
                + KEY_DIFF7 + " TEXT, " + KEY_DIFF8 + " TEXT, " + KEY_DIFF9 + " TEXT, " + KEY_DIFF10 + " TEXT, "
                + KEY_DIFF11 + " TEXT, " + KEY_DIFF12 + " TEXT, " + KEY_DIFF13 + " TEXT, " + KEY_DIFF14 + " TEXT, "
                + KEY_DIFF15 + " TEXT, " + KEY_DIFF16 + " TEXT, " + KEY_DIFF17 + " TEXT, " + KEY_DIFF18 + " TEXT)");
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
            int parHole18,
            int difHole1,
            int difHole2,
            int difHole3,
            int difHole4,
            int difHole5,
            int difHole6,
            int difHole7,
            int difHole8,
            int difHole9,
            int difHole10,
            int difHole11,
            int difHole12,
            int difHole13,
            int difHole14,
            int difHole15,
            int difHole16,
            int difHole17,
            int difHole18)
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
        dataValues.put(KEY_DIFF1, difHole1);
        dataValues.put(KEY_DIFF2, difHole2);
        dataValues.put(KEY_DIFF3, difHole3);
        dataValues.put(KEY_DIFF4, difHole4);
        dataValues.put(KEY_DIFF5, difHole5);
        dataValues.put(KEY_DIFF6, difHole6);
        dataValues.put(KEY_DIFF7, difHole7);
        dataValues.put(KEY_DIFF8, difHole8);
        dataValues.put(KEY_DIFF9, difHole9);
        dataValues.put(KEY_DIFF10, difHole10);
        dataValues.put(KEY_DIFF11, difHole11);
        dataValues.put(KEY_DIFF12, difHole12);
        dataValues.put(KEY_DIFF13, difHole13);
        dataValues.put(KEY_DIFF14, difHole14);
        dataValues.put(KEY_DIFF15, difHole15);
        dataValues.put(KEY_DIFF16, difHole16);
        dataValues.put(KEY_DIFF17, difHole17);
        dataValues.put(KEY_DIFF18, difHole18);
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
            modalContact.diff1 = cursor.getInt(23);
            modalContact.diff2 = cursor.getInt(24);
            modalContact.diff3 = cursor.getInt(25);
            modalContact.diff4 = cursor.getInt(26);
            modalContact.diff5 = cursor.getInt(27);
            modalContact.diff6 = cursor.getInt(28);
            modalContact.diff7 = cursor.getInt(29);
            modalContact.diff8 = cursor.getInt(30);
            modalContact.diff9 = cursor.getInt(31);
            modalContact.diff10 = cursor.getInt(32);
            modalContact.diff11 = cursor.getInt(33);
            modalContact.diff12 = cursor.getInt(34);
            modalContact.diff13 = cursor.getInt(35);
            modalContact.diff14 = cursor.getInt(36);
            modalContact.diff15 = cursor.getInt(37);
            modalContact.diff16 = cursor.getInt(38);
            modalContact.diff17 = cursor.getInt(39);
            modalContact.diff18 = cursor.getInt(40);

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
            modalContact.diff1 = cursor.getInt(23);
            modalContact.diff2 = cursor.getInt(24);
            modalContact.diff3 = cursor.getInt(25);
            modalContact.diff4 = cursor.getInt(26);
            modalContact.diff5 = cursor.getInt(27);
            modalContact.diff6 = cursor.getInt(28);
            modalContact.diff7 = cursor.getInt(29);
            modalContact.diff8 = cursor.getInt(30);
            modalContact.diff9 = cursor.getInt(31);
            modalContact.diff10 = cursor.getInt(32);
            modalContact.diff11 = cursor.getInt(33);
            modalContact.diff12 = cursor.getInt(34);
            modalContact.diff13 = cursor.getInt(35);
            modalContact.diff14 = cursor.getInt(36);
            modalContact.diff15 = cursor.getInt(37);
            modalContact.diff16 = cursor.getInt(38);
            modalContact.diff17 = cursor.getInt(39);
            modalContact.diff18 = cursor.getInt(40);

            arrayList2.add(modalContact);
        }
        cursor.close();
        return arrayList2;
    }

}
