package com.example.hosanna.meetingsapp;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // load the layout
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();
        MeetingsRepo repo = new MeetingsRepo(MainActivity.this);

        final ArrayList<HashMap<String, String>> meetingList = repo.getMeetingList();

        ListView lv = findViewById(R.id.listViewMain);

            ListAdapter adapter = new SimpleAdapter(MainActivity.this, meetingList
                    , R.layout.activity_each_attendee, new String[]{"title"}
                    , new int[]{R.id.eachAttendee});

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String temp_id = meetingList.get(position).get("id");

                    Intent i = new Intent(MainActivity.this,NiceDisplayActivity.class);
                    // i.putExtra("meeting", mod);
                    i.putExtra("nice_id", temp_id);
                    startActivity(i);

                }
            });
            lv.setAdapter(adapter);


    }

    public void createMeeting(View view){

        Intent i = new Intent(this,CreateMeetingsActivity.class);
        startActivity(i);
    }

}
