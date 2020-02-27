package com.example.hosanna.meetingsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class MeetingsRepo {

    private DBHelper dbHelper;

    public MeetingsRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(MeetingsModel meeting) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MeetingsModel.KEY_title, meeting.meetingTitle);
        values.put(MeetingsModel.KEY_location, meeting.location);
        values.put(MeetingsModel.KEY_date, meeting.date);
        values.put(MeetingsModel.KEY_time, meeting.time);
        values.put(MeetingsModel.KEY_attendees, meeting.attendees);
        values.put(MeetingsModel.KEY_extra_location_info, meeting.extra_location_info);
        values.put(MeetingsModel.KEY_summary, meeting.summary);

        // Inserting Row
        long meetings_Id = db.insert(MeetingsModel.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) meetings_Id;
    }

    public void delete(int meetings_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(MeetingsModel.TABLE, MeetingsModel.KEY_Meeting_ID + "= ?", new String[]{String.valueOf(meetings_Id)});
        db.close(); // Closing database connection
    }

    public void update(MeetingsModel meeting) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MeetingsModel.KEY_title, meeting.meetingTitle);
        values.put(MeetingsModel.KEY_location, meeting.location);
        values.put(MeetingsModel.KEY_date, meeting.date);
        values.put(MeetingsModel.KEY_time, meeting.time);
        values.put(MeetingsModel.KEY_attendees, meeting.attendees);
        values.put(MeetingsModel.KEY_extra_location_info, meeting.extra_location_info);
        values.put(MeetingsModel.KEY_summary, meeting.summary);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(MeetingsModel.TABLE, values, MeetingsModel.KEY_Meeting_ID + "= ?",
                new String[]{String.valueOf(meeting.meetingID)});
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>> getMeetingList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                MeetingsModel.KEY_Meeting_ID + "," +
                MeetingsModel.KEY_title + "," +
                MeetingsModel.KEY_location +"," +
                MeetingsModel.KEY_date +"," +
                MeetingsModel.KEY_time +"," +
                MeetingsModel.KEY_attendees +"," +
                MeetingsModel.KEY_extra_location_info +"," +
                MeetingsModel.KEY_summary +
                " FROM " + MeetingsModel.TABLE;

        ArrayList<HashMap<String, String>> meetingList = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> meeting = new HashMap<>();
                meeting.put("id", cursor.getString(cursor.getColumnIndex(MeetingsModel.KEY_Meeting_ID)));
                meeting.put("title", cursor.getString(cursor.getColumnIndex(MeetingsModel.KEY_title)));
//                meeting.put("location", cursor.getString(cursor.getColumnIndex(MeetingsModel.KEY_location)));
//                meeting.put("date", cursor.getString(cursor.getColumnIndex(MeetingsModel.KEY_date)));
//                meeting.put("time", cursor.getString(cursor.getColumnIndex(MeetingsModel.KEY_time)));
//                meeting.put("attendees", cursor.getString(cursor.getColumnIndex(MeetingsModel.KEY_attendees)));
//                meeting.put("extraLocationInfo", cursor.getString(cursor.getColumnIndex(MeetingsModel.KEY_extra_location_info)));
//                meeting.put("summary", cursor.getString(cursor.getColumnIndex(MeetingsModel.KEY_summary)));

                meetingList.add(meeting);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return meetingList;

    }

    public MeetingsModel getMeetingById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                MeetingsModel.KEY_Meeting_ID + "," +
                MeetingsModel.KEY_title + "," +
                MeetingsModel.KEY_location +"," +
                MeetingsModel.KEY_date +"," +
                MeetingsModel.KEY_time +"," +
                MeetingsModel.KEY_attendees +"," +
                MeetingsModel.KEY_extra_location_info +"," +
                MeetingsModel.KEY_summary +
                " FROM " + MeetingsModel.TABLE
                + " WHERE " +
                MeetingsModel.KEY_Meeting_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        MeetingsModel meeting = new MeetingsModel();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                meeting.meetingID =cursor.getInt(cursor.getColumnIndex(MeetingsModel.KEY_Meeting_ID));
                meeting.meetingTitle =cursor.getString(cursor.getColumnIndex(MeetingsModel.KEY_title));
                meeting.location =cursor.getString(cursor.getColumnIndex(MeetingsModel.KEY_location));
                meeting.date =cursor.getString(cursor.getColumnIndex(MeetingsModel.KEY_date));
                meeting.time =cursor.getString(cursor.getColumnIndex(MeetingsModel.KEY_time));
                meeting.attendees =cursor.getString(cursor.getColumnIndex(MeetingsModel.KEY_attendees));
                meeting.extra_location_info =cursor.getString(cursor.getColumnIndex(MeetingsModel.KEY_extra_location_info));
                meeting.summary =cursor.getString(cursor.getColumnIndex(MeetingsModel.KEY_summary));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return meeting;
    }
}
