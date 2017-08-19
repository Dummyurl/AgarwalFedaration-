package com.LeelaGroup.AgrawalFedration.matrimony;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.LeelaGroup.AgrawalFedration.MatrimonySession;
import com.LeelaGroup.AgrawalFedration.Network.ApiClient;
import com.LeelaGroup.AgrawalFedration.R;
import com.LeelaGroup.AgrawalFedration.Service.ServiceMatrimony;
import com.LeelaGroup.AgrawalFedration.matrimony.models.BasicDetailAndContactInfo;
import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BasicDetailsActivity extends AppCompatActivity {
     MatrimonySession matrimonySession;
Toolbar toolbar;
    String mat_id;
    CircleImageView profilepic;
    TextView fname, mname, lname, dob, tob, birplc, ntvplc, mrtlsts, noofchild,chdlivsts,gndr, mtrtng, rlgn, cst, sbcst, abtme;
    String mat_sess, mreg_prof_pic, mreg_am, mreg_fname, mreg_mname, mreg_lname, mreg_birth_place, mreg_birth_time, mreg_native_place, mreg_dob, mreg_age, mreg_marital_status, mreg_gender, mreg_no_child, mreg_child_leave_status, mreg_mother_tongue, mreg_about_me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_details);
        matrimonySession=new MatrimonySession(getApplicationContext());
        toolbar=(Toolbar)findViewById(R.id.toolbar_matrimony);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Basic Details");


        mat_id = getIntent().getStringExtra("mat_id");
        init();



        if(matrimonySession.checkLogin())
            finish();

        getBasicDetails();
    }

    public void getBasicDetails() {
        ServiceMatrimony serviceMatrimony = ApiClient.getRetrofit().create(ServiceMatrimony.class);
        Call<BasicDetailAndContactInfo> getBasicCall=serviceMatrimony.getBasicDetailsAndContact(mat_id);
        getBasicCall.enqueue(new Callback<BasicDetailAndContactInfo>() {
            @Override
            public void onResponse(Call<BasicDetailAndContactInfo> call, Response<BasicDetailAndContactInfo> response) {
                BasicDetailAndContactInfo basicDetailAndContactInfo=response.body();
               try {

                   Glide.with(BasicDetailsActivity.this).load(basicDetailAndContactInfo.
                           getMreg_prof_pic()).into(profilepic);
               }catch (Exception e)
               {

               }
                fname.setText(basicDetailAndContactInfo.getMreg_fname());
                mname.setText(basicDetailAndContactInfo.getMreg_mname());
                lname.setText(basicDetailAndContactInfo.getMreg_lname());
                dob.setText(basicDetailAndContactInfo.getMreg_dob());
                tob.setText(basicDetailAndContactInfo.getMreg_birth_time());
                birplc.setText(basicDetailAndContactInfo.getMreg_birth_place());
                ntvplc.setText(basicDetailAndContactInfo.getMreg_native_place());
                mrtlsts.setText(basicDetailAndContactInfo.getMreg_marital_status());
                noofchild.setText(basicDetailAndContactInfo.getMreg_no_child());
                chdlivsts.setText(basicDetailAndContactInfo.getMreg_child_leave_status());
                gndr.setText(basicDetailAndContactInfo.getMreg_gender());
                mtrtng.setText(basicDetailAndContactInfo.getMreg_mother_tongue());
                rlgn.setText(basicDetailAndContactInfo.getMat_reg_religion());
                cst.setText(basicDetailAndContactInfo.getMat_reg_caste());
                sbcst.setText(basicDetailAndContactInfo.getMat_reg_subcaste());
                abtme.setText(basicDetailAndContactInfo.getMreg_about_me());

            }

            @Override
            public void onFailure(Call<BasicDetailAndContactInfo> call, Throwable t) {

                Toast.makeText(BasicDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void init() {
        profilepic = (CircleImageView) findViewById(R.id.d_person_image);
        fname = (TextView) findViewById(R.id.d_per_fname);
        mname = (TextView) findViewById(R.id.d_per_mname);
        lname = (TextView) findViewById(R.id.d_per_lname);
        dob = (TextView) findViewById(R.id.d_per_dob);
        tob = (TextView) findViewById(R.id.d_per_brthtime);
        birplc = (TextView) findViewById(R.id.d_per_brthplace);
        ntvplc = (TextView) findViewById(R.id.d_per_ntvplace);
        mrtlsts = (TextView) findViewById(R.id.d_per_maritalstatus);
        noofchild = (TextView) findViewById(R.id.frm_d_per_no_of_children);
        chdlivsts = (TextView) findViewById(R.id.frm_d_per_children_living_status);
        gndr = (TextView) findViewById(R.id.d_per_gender);
        mtrtng = (TextView) findViewById(R.id.d_per_mothertounge);
        rlgn = (TextView) findViewById(R.id.d_per_religion);
        cst = (TextView) findViewById(R.id.d_per_cast);
        sbcst = (TextView) findViewById(R.id.d_per_subcast);
        abtme = (TextView) findViewById(R.id.d_per_abtme);

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
