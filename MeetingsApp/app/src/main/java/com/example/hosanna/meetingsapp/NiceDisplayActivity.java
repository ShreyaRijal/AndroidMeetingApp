package com.example.hosanna.meetingsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class NiceDisplayActivity extends AppCompatActivity {

    int id;
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_nice_display);

        TextView title = findViewById(R.id.titleDisplay);
        TextView loc = findViewById(R.id.locationDisplay);
        TextView date = findViewById(R.id.dateDisplay);
        TextView time = findViewById(R.id.timeDisplay);
        TextView attendees = findViewById(R.id.attendeesDisplay);
        TextView extra_info = findViewById(R.id.extraLocDisplay);
        TextView summary = findViewById(R.id.summaryDisplay);

        Intent i = getIntent();
        MeetingsRepo repo = new MeetingsRepo(NiceDisplayActivity.this);

        id = Integer.parseInt(i.getStringExtra("nice_id"));
        MeetingsModel mod = repo.getMeetingById(id);

        //MeetingsModel mod =(MeetingsModel)i.getSerializableExtra("meeting");

        title.setText(mod.meetingTitle);
        loc.setText(mod.location);
        date.setText(mod.date);
        time.setText(mod.time);
        attendees.setText(mod.attendees);
        extra_info.setText(mod.extra_location_info);
        summary.setText(mod.summary);

    }

    public void removeMeeting(View view){

        MeetingsRepo repo = new MeetingsRepo(NiceDisplayActivity.this);
        repo.delete(id);

        finish();
    }

    public void updateMeeting(View view){

        Intent i = new Intent(NiceDisplayActivity.this, CreateMeetingsActivity.class);
        i.putExtra("display_Id", Integer.toString(id));
        startActivity(i);
    }
}
