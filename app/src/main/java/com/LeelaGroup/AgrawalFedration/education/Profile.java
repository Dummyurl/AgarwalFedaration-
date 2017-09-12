package com.LeelaGroup.AgrawalFedration.education;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.LeelaGroup.AgrawalFedration.EducationSessionManager;
import com.LeelaGroup.AgrawalFedration.Education_Pojos.PersonalDetailPojo;
import com.LeelaGroup.AgrawalFedration.MatrimonySession;
import com.LeelaGroup.AgrawalFedration.Network.ApiClient;
import com.LeelaGroup.AgrawalFedration.R;
import com.LeelaGroup.AgrawalFedration.Service.Medical.ServiceAPIEducation;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity {

    Toolbar toolbar;

    String email,sess;

    EducationSessionManager educationSessionManager;

    TextView d_fname,d_per_lname,d_per_dob,d_per_fathername,d_per_Mothername,d_per_gender,d_per_state1,d_per_city1,d_per_address1,d_per_pincode1;

    Typeface font;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        educationSessionManager = new EducationSessionManager(getApplicationContext());

        if (educationSessionManager.checkLogin())
        finish();


        font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );

        Intent intent = getIntent();

        Bundle b = intent.getExtras();



        toolbar = (Toolbar)findViewById(R.id.toolbar_matrimony);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Profile");

        getAwesome();

        d_fname=(TextView)findViewById(R.id.d_fname);
        d_per_lname=(TextView)findViewById(R.id.d_per_lname);
        d_per_dob=(TextView)findViewById(R.id.d_per_dob);
        d_per_fathername=(TextView)findViewById(R.id.d_per_fathername);
        d_per_Mothername=(TextView)findViewById(R.id.d_per_Mothername);
        d_per_gender=(TextView)findViewById(R.id.d_per_gender);
        d_per_state1=(TextView)findViewById(R.id.d_per_state1);
        d_per_city1=(TextView)findViewById(R.id.d_per_city1);
        d_per_address1=(TextView)findViewById(R.id.d_per_address1);
        d_per_pincode1=(TextView)findViewById(R.id.d_per_pincode1);


        HashMap<String, String> user = educationSessionManager.getUserDetails();

        email=user.get(EducationSessionManager.KEY_EMAIL);


       // PersonalDetailPojo
       // Toast.makeText(this, email, Toast.LENGTH_LONG).show();
        fetch();
    }

    private void getAwesome() {

        TextView fname = (TextView) findViewById( R.id.fname);
        fname.setTypeface(font);

        TextView per_lname = (TextView) findViewById( R.id.per_lname);
        per_lname.setTypeface(font);

        TextView per_dob = (TextView) findViewById( R.id.per_dob);
        per_dob.setTypeface(font);

        TextView per_fathername = (TextView) findViewById( R.id.per_fathername);
        per_fathername.setTypeface(font);

        TextView per_Mothername = (TextView) findViewById( R.id.per_Mothername);
        per_Mothername.setTypeface(font);

        TextView per_gender = (TextView) findViewById( R.id.per_gender);
        per_gender.setTypeface(font);

        TextView country1 = (TextView) findViewById( R.id.country1);
        country1.setTypeface(font);

        TextView state1 = (TextView) findViewById( R.id.state1);
        state1.setTypeface(font);

        TextView city1 = (TextView) findViewById( R.id.city1);
        city1.setTypeface(font);

        TextView address1 = (TextView) findViewById( R.id.address1);
        address1.setTypeface(font);

        TextView pincode1 = (TextView) findViewById( R.id.pincode1);
        pincode1.setTypeface(font);
    }

    private void fetch() {
        ServiceAPIEducation service = ApiClient.getRetrofit().create(ServiceAPIEducation.class);

        Call<PersonalDetailPojo> pdf = service.getPersonalDeTail(email);

        pdf.enqueue(new Callback<PersonalDetailPojo>() {
            @Override
            public void onResponse(Call<PersonalDetailPojo> call, Response<PersonalDetailPojo> response) {

                try {
                    PersonalDetailPojo pdf= response.body();
                    d_fname.setText(pdf.getPd_fname());
                    d_per_lname.setText(pdf.getPd_lname());
                    d_per_dob.setText(pdf.getPd_dob());
                    d_per_fathername.setText(pdf.getPd_father_name());
                    d_per_Mothername.setText(pdf.getPd_mother_name());
                    d_per_gender.setText(pdf.getPd_gender());
                    d_per_state1.setText(pdf.getPd_state());
                    d_per_city1.setText(pdf.getPd_city());
                    d_per_address1.setText(pdf.getPd_addr());
                    d_per_pincode1.setText(pdf.getPd_pincode());
                } catch (Exception e) {

                }

                //Toast.makeText(Profile.this, "Successfully Featch", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<PersonalDetailPojo> call, Throwable t) {

                Toast.makeText(Profile.this, "Unsuccessfully Featch", Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_education,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int selected_id = item.getItemId();

        if (selected_id == R.id.action_marksheet)
        {
            Bundle b = new Bundle();
            b.putString("myname", email);

            Intent intent = new Intent(getApplicationContext(), Education_Upload_Doc.class);
            intent.putExtras(b);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
//            startActivity(new Intent(this, Education_Upload_Doc.class));
        }else if (selected_id == R.id.action_View_Form)
        {
            Bundle b = new Bundle();
            b.putString("myname", email);

            Intent intent = new Intent(getApplicationContext(), FeatchDetails.class);
            intent.putExtras(b);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            //startActivity(new Intent(this, FeatchDetails.class));
        }else if (selected_id == R.id.education_logout)
        {
            educationSessionManager.logoutUser();
            finish();

            /*Intent intent = new Intent(Profile.this,login_education.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);*/

            Profile.this.finish();
        }else if (selected_id == R.id.action_update_marksheet)
        {
            Bundle b = new Bundle();
            b.putString("myname", email);

            Intent intent = new Intent(getApplicationContext(), Document.class);
            intent.putExtras(b);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else if (selected_id== R.id.scholarships)
        {
            Bundle b = new Bundle();
            b.putString("myname", email);

            Intent intent = new Intent(getApplicationContext(), Education_Scholarships.class);
            intent.putExtras(b);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else if(selected_id==android.R.id.home){
            onBackPressed();
            finish();
        }


        return true;
    }
}
