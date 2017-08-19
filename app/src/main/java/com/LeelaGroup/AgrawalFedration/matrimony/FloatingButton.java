package com.LeelaGroup.AgrawalFedration.matrimony;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.support.design.widget.FloatingActionButton;

import com.LeelaGroup.AgrawalFedration.MatrimonySession;
import com.LeelaGroup.AgrawalFedration.R;


public class FloatingButton extends AppCompatActivity {

     private FloatingActionButton fab;

 MatrimonySession matrimonySession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating_button);
          matrimonySession=new MatrimonySession(getApplicationContext());

        if(matrimonySession.checkLogin())
            finish();

        FloatingActionButton fab =(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(FloatingButton.this,MatrimonyActivity.class);
                startActivity(intent);
            }
        });
    }
}
