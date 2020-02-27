package com.example.hosanna.meetingsapp;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;

public class PreviousAttendeesActivity extends AppCompatActivity {

    ArrayList<Integer> ids = new ArrayList<>();
    int isClicked = 0;
    ListView lv;
    ArrayList<HashMap<String, String>> attendeeList;
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("PREV", "entered oncreate");
        setContentView(R.layout.activity_previous_attendees);

        AttendeesRepo repo = new AttendeesRepo(PreviousAttendeesActivity.this);

        attendeeList = repo.getAttendeeList();

        lv= findViewById(R.id.listView);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        if (attendeeList.size() != 0) {
            adapter = new SimpleAdapter(PreviousAttendeesActivity.this, attendeeList
                    , R.layout.activity_each_attendee, new String[]{"name"}
                    , new int[]{R.id.eachAttendee});
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    int attendeeID= Integer.parseInt(attendeeList.get(position).get("id"));

                    if ((isClicked%2)==0) {
                            view.setBackgroundResource(android.R.color.darker_gray);
                            Log.d("PREV", Integer.toString(attendeeID));
                            ids.add(attendeeID);
                    } else {
                            view.setBackgroundResource(R.color.bg);
                            Log.d("PREV", Integer.toString(attendeeID));
                            if (ids.size() > 1) {
                                ids.remove(attendeeID);
                            }
                        }

                    isClicked++;
                }
            });

            lv.setAdapter(adapter);

        }
    }

    public void removePrevBtn(View view) {

        AttendeesRepo repo = new AttendeesRepo(PreviousAttendeesActivity.this);
        for (int i : ids){
            repo.delete(i);
        }

        ListView lv= findViewById(R.id.listView);

        ArrayList<HashMap<String, String>> attendeeList = repo.getAttendeeList();
        if (attendeeList.size() != 0) {
            ListAdapter adapter = new SimpleAdapter(PreviousAttendeesActivity.this, attendeeList
                    , R.layout.activity_each_attendee, new String[]{"name"}
                    , new int[]{R.id.eachAttendee});


            lv.setAdapter(adapter);
            ((SimpleAdapter) adapter).notifyDataSetChanged();

        }

    }

    public void closeActivity(View view){

        finish();
    }

    public void addPeople(View view){

        String names="";
        AttendeesRepo repo = new AttendeesRepo(PreviousAttendeesActivity.this);

        for (int i : ids){

            AttendeesModel mod = repo.getAttendeeById(i);
            names += mod.attendeeName+" ";

        }

        Intent i = new Intent(PreviousAttendeesActivity.this, AddPeopleActivity.class);
        i.putExtra("prev_attendee_names",names);
        setResult(MapsActivity.RESULT_OK,i);
        finish();

    }

}