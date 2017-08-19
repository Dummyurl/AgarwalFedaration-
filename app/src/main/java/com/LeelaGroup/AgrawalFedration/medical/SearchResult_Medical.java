package com.LeelaGroup.AgrawalFedration.medical;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.LeelaGroup.AgrawalFedration.Business_Medical_Session;
import com.LeelaGroup.AgrawalFedration.Medical_Session;
import com.LeelaGroup.AgrawalFedration.R;
import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchResult_Medical extends AppCompatActivity {

    TextView textViewDetail, textViewAdress, textViewTiming, textViewContact, tvWebsite, tvPersonName,
            tvPersonMob, tvDesignation, textView_Email,textViewheading;
    String close_time,open_time,open_close,circleImage;
    Business_Medical_Session medical__session;
    CircleImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result__medical);


        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar_matrimony);
        setSupportActionBar(toolbar);
        setTitle("Advertiser Details");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        medical__session =new Business_Medical_Session(getApplicationContext());

        circleImageView=(CircleImageView)findViewById(R.id.img_card) ;
        textViewheading = (TextView) findViewById(R.id.heading);
        textViewDetail = (TextView) findViewById(R.id.textViewDetail);
        textViewAdress = (TextView) findViewById(R.id.textViewAdress);
        textViewTiming = (TextView) findViewById(R.id.textViewTimin);
        textViewContact = (TextView) findViewById(R.id.textViewContact);
        tvWebsite = (TextView) findViewById(R.id.tvWebsite);
        tvPersonName = (TextView) findViewById(R.id.tvPersonName);
        tvPersonMob = (TextView) findViewById(R.id.tvPersonMob);
        tvDesignation = (TextView) findViewById(R.id.tvDesignation);
        textView_Email = (TextView) findViewById(R.id.textView_Email);



        open_time=getIntent().getStringExtra("med_open_time");
        close_time=getIntent().getStringExtra("med_open_time");
        open_close=open_time+ "-"+ close_time;
        circleImage=getIntent().getStringExtra("med_image");



        Glide.with(this).load(circleImage).into(circleImageView);
        textViewDetail.setText(getIntent().getStringExtra("med_about"));
        textViewAdress.setText(getIntent().getStringExtra("med_addr"));
        textViewTiming.setText(open_close);
        textViewContact.setText(getIntent().getStringExtra("med_phone"));
        tvWebsite.setText(getIntent().getStringExtra("med_website"));
        tvPersonName.setText(getIntent().getStringExtra("med_cont_name"));
        tvPersonMob.setText(getIntent().getStringExtra("med_cont_phone"));
        tvDesignation.setText(getIntent().getStringExtra("med_cont_desig"));
        textView_Email.setText(getIntent().getStringExtra("med_cont_email"));

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:

                onBackPressed();
                finish();

                return  true;
        }

        return super.onOptionsItemSelected(item);
    }
}
