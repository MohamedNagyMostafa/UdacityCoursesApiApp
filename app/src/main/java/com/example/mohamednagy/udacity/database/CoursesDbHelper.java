package com.example.mohamednagy.udacity.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by mohamednagy on 12/1/2016.
 */
public class CoursesDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "courses.db";

    private static final int DATABASE_VERSION = 1;

    public CoursesDbHelper(Context context) {
        super(context ,DATABASE_NAME , null , DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        Log.e("tampo",CoursesContract.CoursesTable.CREATE_COURSES_TABLE);
        sqLiteDatabase.execSQL(CoursesContract.CoursesTable.CREATE_COURSES_TABLE);
        sqLiteDatabase.execSQL(CoursesContract.AffiliatesTable.CREATE_AFFILIATES_TABLE);
        sqLiteDatabase.execSQL(CoursesContract.InstructorsTable.CREATE_INSTRUCTORS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CoursesContract.CoursesTable.CREATE_COURSES_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CoursesContract.AffiliatesTable.CREATE_AFFILIATES_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CoursesContract.InstructorsTable.CREATE_INSTRUCTORS_TABLE);

        onCreate(sqLiteDatabase);
    }
}
