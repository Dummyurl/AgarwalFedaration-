package com.LeelaGroup.AgrawalFedration.medical;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.LeelaGroup.AgrawalFedration.R;

import java.util.ArrayList;

public class Event extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Event_Contact> list = new ArrayList<Event_Contact>();
    int[] image_id = {R.drawable.event_fab, R.drawable.event_fab, R.drawable.event_fab};
    String[] title,date,day,time,location,entryCharge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        title = getResources().getStringArray(R.array.titleEvent);
        date = getResources().getStringArray(R.array.event_date);
        day = getResources().getStringArray(R.array.event_day);
        time = getResources().getStringArray(R.array.event_time);
        location = getResources().getStringArray(R.array.event_location);
        entryCharge = getResources().getStringArray(R.array.event_entryCharge);

        int count = 0;
        for (String Title : title) {
            Event_Contact contact = new Event_Contact(image_id[count],Title,date[count],day[count],
                    time[count],location[count],entryCharge[count]);
            count++;
            list.add(contact);
        }
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new Event_Adapter(list, this);
        recyclerView.setAdapter(adapter);

    }
}
