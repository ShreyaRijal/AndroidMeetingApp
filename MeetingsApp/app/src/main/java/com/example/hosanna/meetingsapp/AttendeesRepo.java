package com.example.hosanna.meetingsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class AttendeesRepo {

    private DBHelper dbHelper;

    public AttendeesRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(AttendeesModel Attendee) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AttendeesModel.KEY_name, Attendee.attendeeName);

        // Inserting Row
        long attendee_Id = db.insert(AttendeesModel.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) attendee_Id;
    }

    public void delete(int attendee_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(AttendeesModel.TABLE, AttendeesModel.KEY_ID + "= ?", new String[]{String.valueOf(attendee_Id)});
        db.close(); // Closing database connection
    }

    public void update(AttendeesModel attendee) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AttendeesModel.KEY_name, attendee.attendeeName);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(AttendeesModel.TABLE, values, AttendeesModel.KEY_ID + "= ?",
                new String[]{String.valueOf(attendee.attendeeID)});
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>> getAttendeeList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                AttendeesModel.KEY_ID + "," +
                AttendeesModel.KEY_name +
                " FROM " + AttendeesModel.TABLE;

        ArrayList<HashMap<String, String>> attendeeList = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> attendee = new HashMap<>();
                attendee.put("id", cursor.getString(cursor.getColumnIndex(AttendeesModel.KEY_ID)));
                attendee.put("name", cursor.getString(cursor.getColumnIndex(AttendeesModel.KEY_name)));
                attendeeList.add(attendee);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return attendeeList;

    }

    public AttendeesModel getAttendeeById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                AttendeesModel.KEY_ID + "," +
                AttendeesModel.KEY_name +
                " FROM " + AttendeesModel.TABLE
                + " WHERE " +
                AttendeesModel.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        AttendeesModel attendee = new AttendeesModel();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                attendee.attendeeID =cursor.getInt(cursor.getColumnIndex(AttendeesModel.KEY_ID));
                attendee.attendeeName =cursor.getString(cursor.getColumnIndex(AttendeesModel.KEY_name));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return attendee;
    }

}

