package com.LeelaGroup.AgrawalFedration.education;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.LeelaGroup.AgrawalFedration.R;


public class BestOfLuck extends AppCompatActivity {

    Button Exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_of_luck);

        Exit=(Button)findViewById(R.id.Exit);

        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }
    public void StartNext(View v){startActivity(new Intent(BestOfLuck.this,Registration.class));}
}
