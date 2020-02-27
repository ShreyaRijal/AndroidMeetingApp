package com.example.hosanna.meetingsapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class UpdateDisplayActivity extends AppCompatActivity{


    TextView dateHolder;
    DatePickerDialog.OnDateSetListener dateListener;
    TextView timeHolder;
    TimePickerDialog.OnTimeSetListener timeListener;
    MeetingsModel mod = new MeetingsModel();
    EditText title;
    EditText extra_info;
    EditText summary;
    int display_id;
    TextView loc;
    TextView people;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // load the layout
        setContentView(R.layout.activity_update_display);

        MeetingsRepo repo = new MeetingsRepo(UpdateDisplayActivity.this);
        Intent intent = getIntent();
        display_id = Integer.parseInt(intent.getStringExtra("display_Id"));
        mod = repo.getMeetingById(display_id);

        title = findViewById(R.id.meetingTitleUpdate);
        title.setText(mod.meetingTitle);

        extra_info = findViewById(R.id.extraLocationInfoUpdate);
        extra_info.setText(mod.extra_location_info);

        summary = findViewById(R.id.summaryUpdate);
        summary.setText(mod.summary);

        summary.setImeOptions(EditorInfo.IME_ACTION_DONE);
        summary.setRawInputType(InputType.TYPE_CLASS_TEXT);

        dateHolder = findViewById(R.id.dateViewUpdate);
        dateHolder.setText(mod.date);

        loc.setText(mod.location);
        people.setText(mod.attendees);

        dateHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int y = c.get(Calendar.YEAR);
                int m = c.get((Calendar.MONTH));
                int d = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        com.example.hosanna.meetingsapp.UpdateDisplayActivity.this,
                        android.R.style.Theme_DeviceDefault,
                        dateListener,
                        y, m, d);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
                datePickerDialog.show();
            }
        });

        dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;

                String date = dayOfMonth + "/" + month + "/" + year;

                dateHolder.setText(date);

                mod.date = date;
            }
        };

        timeHolder = findViewById(R.id.timeViewUpdate);
        timeHolder.setText(mod.time);

        timeHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int h = c.get(Calendar.HOUR_OF_DAY);
                int min = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        com.example.hosanna.meetingsapp.UpdateDisplayActivity.this, timeListener, h,
                        min, android.text.format.DateFormat.is24HourFormat(com.example.hosanna.meetingsapp.UpdateDisplayActivity.this));
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
                timePickerDialog.show();
            }
        });

        timeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                String time = hourOfDay + ":" + String.format("%02d", minute);

                timeHolder.setText(time);

                mod.time = time;
            }
        };

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        loc = findViewById(R.id.locationViewUpdate);
        people = findViewById(R.id.attendeesUpdate);

        Log.d("ENTERED", Integer.toString(requestCode));


        if (requestCode == 1) {
            if (resultCode == MapsActivity.RESULT_OK) {
                String place = data.getStringExtra("place_name");
                loc.setText(place);
                mod.location = place;
            }
            if (resultCode == MapsActivity.RESULT_CANCELED) {
                loc.setText(loc.getText().toString());
            }
        }

        if (requestCode == 2) {
            if (resultCode == AddPeopleActivity.RESULT_OK) {
                Log.d("ENTERED", "Some random msg");
                String peopleNames = data.getStringExtra("the_attendees");

                people.setText(peopleNames);

                mod.attendees = peopleNames;
            }
        }

    }

    public void chooseLocation(View view) {

        Intent i = new Intent(this, MapsActivity.class);
        startActivityForResult(i, 1);
    }

    public void addPeople(View view) {

        Intent i = new Intent(this, AddPeopleActivity.class);
        startActivityForResult(i, 2);
    }

    public void Update(View view) {

        MeetingsRepo repo = new MeetingsRepo(com.example.hosanna.meetingsapp.UpdateDisplayActivity.this);

        mod.meetingTitle = title.getText().toString();
        mod.extra_location_info = extra_info.getText().toString();
        mod.summary = summary.getText().toString();

        repo.update(mod);
        Intent intent = new Intent(com.example.hosanna.meetingsapp.UpdateDisplayActivity.this, MainActivity.class);
        startActivity(intent);

    }

}


