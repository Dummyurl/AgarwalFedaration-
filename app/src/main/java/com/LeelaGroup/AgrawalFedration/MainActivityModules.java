package com.LeelaGroup.AgrawalFedration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.LeelaGroup.AgrawalFedration.business.Login_Business;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivityModules extends AppCompatActivity {

    ModuleCustomAdapter moduleCustomAdapter;
    ArrayList personImages = new ArrayList<>(Arrays.asList(R.drawable.medical, R.drawable.matri, R.drawable.bus,
            R.drawable.edu, R.drawable.socialrefurn));



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_modules);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // set a GridLayoutManager with default vertical orientation and 2 number of columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        //  call the constructor of ModuleCustomAdapter to send the reference and data to Adapter
        moduleCustomAdapter= new ModuleCustomAdapter(MainActivityModules.this, personImages);
        recyclerView.setAdapter(moduleCustomAdapter); // set the Adapter to RecyclerView

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.medical_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int res_id = item.getItemId();
        switch (res_id){
            case R.id.action_med_logout:

                Business_Medical_Session business_medical_session = new Business_Medical_Session(getApplicationContext());
                MatrimonySession matrimonySession=new MatrimonySession(getApplicationContext());
                EducationSessionManager educationSessionManager=new EducationSessionManager(getApplicationContext());
                business_medical_session.logoutUser();
                matrimonySession.logoutFromMain();
                educationSessionManager.logoutFromMain();
                finish();
                break;
            case android.R.id.home:
                 onBackPressed();

                 finish();
                 return  true;

            default:
        }

        return super.onOptionsItemSelected(item);
    }
}

