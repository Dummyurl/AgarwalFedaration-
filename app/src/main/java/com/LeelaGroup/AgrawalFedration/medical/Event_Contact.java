package com.LeelaGroup.AgrawalFedration.medical;

/**
 * Created by Adwait on 17/05/2017.
 */

public class Event_Contact {

    private int e_image;
    private String e_title,e_date,e_day,e_time,e_location,e_entry_charge;

    public Event_Contact(int e_image,String e_title,String e_date,String e_day,String e_time,String e_location,String e_entry_charge){
        this.setE_image(e_image);
        this.setE_title(e_title);
        this.setE_date(e_date);
        this.setE_day(e_day);
        this.setE_time(e_time);
        this.setE_location(e_location);
        this.setE_entry_charge(e_entry_charge);
    }

    public int getE_image() {
        return e_image;
    }

    public void setE_image(int e_image) {
        this.e_image = e_image;
    }

    public String getE_title() {
        return e_title;
    }

    public void setE_title(String e_title) {
        this.e_title = e_title;
    }

    public String getE_date() {
        return e_date;
    }

    public void setE_date(String e_date) {
        this.e_date = e_date;
    }

    public String getE_day() {
        return e_day;
    }

    public void setE_day(String e_day) {
        this.e_day = e_day;
    }

    public String getE_time() {
        return e_time;
    }

    public void setE_time(String e_time) {
        this.e_time = e_time;
    }

    public String getE_location() {
        return e_location;
    }

    public void setE_location(String e_location) {
        this.e_location = e_location;
    }

    public String getE_entry_charge() {
        return e_entry_charge;
    }

    public void setE_entry_charge(String e_entry_charge) {
        this.e_entry_charge = e_entry_charge;
    }
}
