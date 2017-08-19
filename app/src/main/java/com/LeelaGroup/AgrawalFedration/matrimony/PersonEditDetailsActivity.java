package com.LeelaGroup.AgrawalFedration.matrimony;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.LeelaGroup.AgrawalFedration.MatrimonySession;
import com.LeelaGroup.AgrawalFedration.R;

import java.util.ArrayList;
import java.util.Arrays;

public class PersonEditDetailsActivity extends AppCompatActivity {


    MatrimonySession matrimonySession;
    ArrayList personImages = new ArrayList<>(Arrays.asList(R.drawable.img_basic_details, R.drawable.img_contact_deatails, R.drawable.img_social_attributee, R.drawable.img_education_details, R.drawable.img_occuption_deatils, R.drawable.img_family_details, R.drawable.img_phycical_attribt,R.drawable.img_other_details, R.drawable.img_partner_prefernce));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_edit_details);

        matrimonySession=new MatrimonySession(getApplicationContext());
        if(matrimonySession.checkLogin())
            finish();

        // get the reference of RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.etmatrirecyclerView);
        // set a GridLayoutManager with default vertical orientation and 2 number of columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        //  call the constructor of MatriCustomAdapter to send the reference and data to Adapter
        MatriEditAdapter matriEditAdapter = new MatriEditAdapter(PersonEditDetailsActivity.this,personImages);
        recyclerView.setAdapter(matriEditAdapter); // set the Adapter to RecyclerView
    }
}
