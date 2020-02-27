package com.example.hosanna.meetingsapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class AddPeopleActivity extends AppCompatActivity {

    AttendeesRepo repo;
    TextView t;

    static int buttonClickedCount = 0;
    LinkedList<EditText> list = new LinkedList<>();
    LinkedList<Button> removeButtonList = new LinkedList<>();
    LinkedList<LinearLayout> layoutList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_people);

        repo=new AttendeesRepo(this);

        Random r = new Random();
        int num = r.nextInt(100000) + 1;

        buttonClickedCount++;

        LinearLayout layout = findViewById(R.id.linLay);

        LinearLayout layoutHorizontal = new LinearLayout(AddPeopleActivity.this);
        layoutHorizontal.setOrientation(LinearLayout.HORIZONTAL);
        layoutHorizontal.setLayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                        , LinearLayout.LayoutParams.WRAP_CONTENT));
        layoutHorizontal.setId(buttonClickedCount + num);

        EditText textBox = new EditText(AddPeopleActivity.this);
        textBox.setLayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                        , LinearLayout.LayoutParams.WRAP_CONTENT));
        textBox.setHint("Add Another Attendee            ");
        textBox.setPadding(20, 20, 20, 20);
        textBox.setId(buttonClickedCount + num);

        Button remove = new Button(AddPeopleActivity.this);
        remove.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                , LinearLayout.LayoutParams.WRAP_CONTENT));
        remove.setId(buttonClickedCount + num);
        remove.setVisibility(View.GONE);

        layoutList.add(layoutHorizontal);
        list.add(textBox);
        removeButtonList.add(remove);

        layout.addView(layoutHorizontal);
        layoutHorizontal.addView(textBox);
        layoutHorizontal.addView(remove);

        Log.d("ADD", Integer.toString(layoutHorizontal.getId()));
        Log.d("ADD", Integer.toString(textBox.getId()));
        Log.d("ADD", Integer.toString(remove.getId()));

    }

    public void removeItems(View v) {

        Log.d("CLICKED", "in remove method");

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("CLICKED", "in onclicklistener");

                for (LinearLayout l : layoutList) {
                    for (EditText e : list) {
                        if (v.getId() == l.getId() && v.getId()==e.getId()) {
                            Log.d("CLICKED", Integer.toString(e.getId()));
                            Log.d("CLICKED", Integer.toString(v.getId()));
                            Log.d("CLICKED", Integer.toString(l.getId()));

                            l.removeAllViews();

                            list.remove(e);
                            removeButtonList.remove(v);
                            layoutList.remove(l);
                        }
                    }
                }
            }
        });
    }


    public void addAttendees(View view) {

        Random r = new Random();
        int num = r.nextInt(100000) + 1;

        buttonClickedCount++;

        LinearLayout layout = findViewById(R.id.linLay);

        LinearLayout layoutHorizontal = new LinearLayout(AddPeopleActivity.this);
        layoutHorizontal.setOrientation(LinearLayout.HORIZONTAL);
        layoutHorizontal.setLayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                        , LinearLayout.LayoutParams.WRAP_CONTENT));
        layoutHorizontal.setId(buttonClickedCount + num);

        EditText textBox = new EditText(AddPeopleActivity.this);
        textBox.setLayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                        , LinearLayout.LayoutParams.WRAP_CONTENT));
        textBox.setHint("Add Another Attendee            ");
        textBox.setPadding(20, 20, 20,20);
        textBox.setId(buttonClickedCount + num);

        Button remove = new Button(AddPeopleActivity.this);
        remove.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                , LinearLayout.LayoutParams.WRAP_CONTENT));
        remove.setText("Remove");
        remove.setId(buttonClickedCount + num);
        removeItems(remove);

        layoutList.add(layoutHorizontal);
        list.add(textBox);
        removeButtonList.add(remove);

        layout.addView(layoutHorizontal);
        layoutHorizontal.addView(textBox);
        layoutHorizontal.addView(remove);

        Log.d("ADD", "method in add attendees");
        Log.d("ADD", Integer.toString(layoutHorizontal.getId()));
        Log.d("ADD", Integer.toString(textBox.getId()));
        Log.d("ADD", Integer.toString(remove.getId()));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 3) {
            if (resultCode == PreviousAttendeesActivity.RESULT_OK) {
                String peopleNames = data.getStringExtra("prev_attendee_names");

                t = new TextView(AddPeopleActivity.this);
                t.setTextSize(20);
                t.setText(peopleNames);

                LinearLayout lin = findViewById(R.id.linLay);
                lin.addView(t);
            }
        }
    }

    public void addAll(View v){

        Intent i = new Intent(AddPeopleActivity.this, CreateMeetingsActivity.class);
        String names="";
        String attendeeName;

        for(EditText e : list) {

            if (list != null) {

                AttendeesModel mod = new AttendeesModel();
                attendeeName = e.getText().toString();
                mod.attendeeName = attendeeName;
                repo.insert(mod);

                names += e.getText().toString() + " ";
            }
        }

        names+=t.getText();


        i.putExtra("the_attendees",names);
        setResult(AddPeopleActivity.RESULT_OK,i);
        Log.d("CLICKED", names);

        finish();
    }

    public void addPrevious(View v){

        Intent i = new Intent(AddPeopleActivity.this, PreviousAttendeesActivity.class);
        startActivityForResult(i,3);
    }

}
