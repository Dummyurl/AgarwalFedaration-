package com.LeelaGroup.AgrawalFedration.medical;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.LeelaGroup.AgrawalFedration.medical.Event_Contact;
import com.LeelaGroup.AgrawalFedration.R;

import java.util.ArrayList;

/**
 * Created by Adwait on 17/05/2017.
 */

public class Event_Adapter extends RecyclerView.Adapter<Event_Adapter.EventViewHolder>  {

    ArrayList<Event_Contact> contacts = new ArrayList<Event_Contact>();
    Context ctx;

    public Event_Adapter(ArrayList<Event_Contact> contacts, Context ctx) {
        this.contacts = contacts;
        this.ctx = ctx;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_row_layout, parent, false);
        EventViewHolder contactViewHolder = new EventViewHolder(view, ctx, contacts);
        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        Event_Contact con = contacts.get(position);
        holder.EventImage.setImageResource(con.getE_image());
        holder.date.setText(con.getE_date());
        holder.day.setText(con.getE_day());
        holder.time.setText(con.getE_time());
        holder.location.setText(con.getE_location());
        holder.entryCharges.setText(con.getE_entry_charge());
        holder.title.setText(con.getE_title());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView EventImage;
        TextView title,date, day,time,location,entryCharges;
        ArrayList<Event_Contact> contacts;
        Context ctx;

        public EventViewHolder(View itemView, Context ctx,ArrayList<Event_Contact> contacts) {
            super(itemView);
            this.contacts = contacts;
            this.ctx = ctx;

            EventImage = (ImageView)itemView.findViewById(R.id.imageViewEvent);
            title = (TextView)itemView.findViewById(R.id.eventTitle);
            date = (TextView) itemView.findViewById(R.id.event_date);
            day = (TextView) itemView.findViewById(R.id.event_day);
            time = (TextView) itemView.findViewById(R.id.event_time);
            location = (TextView) itemView.findViewById(R.id.event_location);
            entryCharges =(TextView) itemView.findViewById(R.id.event_entrycharge);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
