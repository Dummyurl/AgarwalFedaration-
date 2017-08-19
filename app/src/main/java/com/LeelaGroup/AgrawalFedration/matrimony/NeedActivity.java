package com.LeelaGroup.AgrawalFedration.matrimony;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.LeelaGroup.AgrawalFedration.MatrimonySession;
import com.LeelaGroup.AgrawalFedration.R;

public class NeedActivity extends AppCompatActivity {

    String mat_id;

    MatrimonySession matrimonySession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need);

        matrimonySession =new MatrimonySession(getApplicationContext());

        mat_id=getIntent().getStringExtra("mat_id");

        if(matrimonySession.checkLogin())
            finish();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        // enabling action bar app icon and behaving it as toggle button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NeedActivity.this,ConceptActivity.class));
            }
        });
    }
    public void  goToConcept(View view){
        Intent intent=new Intent(NeedActivity.this,ConceptActivity.class);
        intent.putExtra("ma_id",mat_id);
        startActivity(intent);
        finish();

    }
    public void  doFinish(View view){
        Intent intent=new Intent(NeedActivity.this,AboutUsActivity.class);
        intent.putExtra("ma_id",mat_id);
        startActivity(intent);
        finish();

    }
}
