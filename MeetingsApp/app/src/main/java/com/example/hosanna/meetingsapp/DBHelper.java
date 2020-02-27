package com.example.hosanna.meetingsapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;

class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 7;

    private static final String DATABASE_NAME = "People.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_ATTENDEE = "CREATE TABLE " + AttendeesModel.TABLE + "("
                + AttendeesModel.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + AttendeesModel.KEY_name + " TEXT)";

        db.execSQL(CREATE_TABLE_ATTENDEE);

        String CREATE_TABLE_MEETING = " CREATE TABLE " + MeetingsModel.TABLE + "("
                +MeetingsModel.KEY_Meeting_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"
                +MeetingsModel.KEY_title + " TEXT, "
                +MeetingsModel.KEY_location+ " TEXT, "
                +MeetingsModel.KEY_time+ " TEXT, "
                +MeetingsModel.KEY_date+ " TEXT, "
                +MeetingsModel.KEY_attendees+ " TEXT, "
                +MeetingsModel.KEY_extra_location_info+ " TEXT, "
                +MeetingsModel.KEY_summary+ " TEXT)";

        db.execSQL(CREATE_TABLE_MEETING);

    }
        @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed, all data will be gone!!!
            db.execSQL("DROP TABLE IF EXISTS " + AttendeesModel.TABLE);

            db.execSQL("DROP TABLE IF EXISTS " + MeetingsModel.TABLE);

            // Create tables again
            onCreate(db);
    }
}
