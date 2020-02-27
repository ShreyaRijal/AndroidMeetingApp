package com.example.hosanna.meetingsapp;


import java.io.Serializable;

public class MeetingsModel  {


    public MeetingsModel(){

    }

    public static final String TABLE = "Meetings";

    public static final String KEY_Meeting_ID = "meeting_id";
    public static final String KEY_title = "meeting_title";
    public static final String KEY_location = "location";
    public static final String KEY_date = "date";
    public static final String KEY_time = "time";
    public static final String KEY_extra_location_info ="extra_location_info";
    public static final String KEY_attendees = "attendees";
    public static final String KEY_summary = "summary";

    int meetingID;
    String meetingTitle;
    String location;
    String date;
    String time;
    String extra_location_info;
    String attendees;
    String summary;
}
