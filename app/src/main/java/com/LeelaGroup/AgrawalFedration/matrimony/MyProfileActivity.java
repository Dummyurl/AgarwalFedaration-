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
import com.LeelaGroup.AgrawalFedration.matrimony.models.AllDetails;
import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfileActivity extends AppCompatActivity {

    CircleImageView profilepic;
    Toolbar toolbar;
TextView fname,mname,lname,dob,tob,birplc,ntvplc,mrtlsts,gndr,mtrtng,rlgn,cst,sbcst,abtme;
TextView mobno,landno,email,addr,cntry,city,state,pincode,resdsts,mnglk,hrscp,gothraself,gothramama;
TextView graduate,ocuuptn,indsry,servEmpAt,pesnlIncome;
TextView fthrName,mthrName,fthrOccu,mthrOccu,noofbro,noofsis,noofbromar,noofsismar,famsts,famtype,famincome;
TextView hght,wt,bldgrp,bdytype,complxn,diet,smoke,drink,handicap,profcreateby,hobies,intrest;
TextView lkngfor,cntrylivin,statelivin,citylivein,minage,maxage,minheight,maxheight,ppcompexn,education,pprlgn,ppcast;
String mat_id = "";

MatrimonySession matrimonySession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_matrimony);

        matrimonySession =new MatrimonySession(getApplicationContext());

        init();
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("My Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if(matrimonySession.checkLogin())
            finish();

        mat_id=getIntent().getStringExtra("mat_id");
        fetchAllInfo();

    }

    private void fetchAllInfo()
    {
        ServiceMatrimony serviceMatrimony=ApiClient.getRetrofit().create(ServiceMatrimony.class);
        Call<AllDetails> call=serviceMatrimony.getAllDetails(mat_id);

        call.enqueue(new Callback<AllDetails>() {
            @Override
            public void onResponse(Call<AllDetails> call, Response<AllDetails> response) {
                AllDetails allDetails=response.body();

                // Basic details
                try {
                    if (allDetails!=null)
                    {
                        Glide.with(MyProfileActivity.this).load(allDetails.getMregProfPic()).placeholder(R.drawable.one).into(profilepic);

                        fname.setText(allDetails.getMregFname());
                        mname.setText(allDetails.getMregMname());
                        lname.setText(allDetails.getMregLname());
                        dob.setText(allDetails.getMregDob());
                        tob.setText(allDetails.getMregBirthTime());
                        birplc.setText(allDetails.getMregBirthPlace());
                        ntvplc.setText(allDetails.getMregNativePlace());
                        mrtlsts.setText(allDetails.getMregMaritalStatus());
                        gndr.setText(allDetails.getMregGender());
                        mtrtng.setText(allDetails.getMregMotherTongue());
                        rlgn.setText(allDetails.getMatRegReligion());
                        //religion details
                        cst.setText(allDetails.getMatRegCaste());
                        sbcst.setText(allDetails.getMatRegSubcaste());
                        abtme.setText(allDetails.getMregAboutMe());

                        //contact details
                        mobno.setText(allDetails.getMregPhone());
                        landno.setText(allDetails.getMregLandline());
                        email.setText(allDetails.getMregEmail());
                        addr.setText(allDetails.getMregAddr());
                        cntry.setText(allDetails.getMregCountry());
                        city.setText(allDetails.getMregCity());
                        state.setText(allDetails.getMregState());
                        pincode.setText(allDetails.getMregPincode());
                        resdsts.setText(allDetails.getMregResidStatus());

                        //physical details
                        hght.setText(allDetails.getMregPhyHt());
                        wt.setText(allDetails.getMregPhyWt());
                        bldgrp.setText(allDetails.getMregBloodGrp());
                        bdytype.setText(allDetails.getMregBdyType());
                        complxn.setText(allDetails.getMregComplexion());
                        diet.setText(allDetails.getMregDiet());
                        smoke.setText(allDetails.getMregSmoke());
                        drink.setText(allDetails.getMregDrink());
                        handicap.setText(allDetails.getMregHandicapped());

                        //other details
                        profcreateby.setText(allDetails.getMregProfCreateFor());
                        hobies.setText(allDetails.getMregHobby());
                        intrest.setText(allDetails.getMregInterest());

                        //social details
                        mnglk.setText(allDetails.getMatRegManglik());
                        hrscp.setText(allDetails.getMatRegHoroscopeMatch());
                        gothraself.setText(allDetails.getMatRegGothraSelf());
                        //gothramama.setText(allDetails.getMatRegGothraMama());

                        //family details
                        fthrName.setText(allDetails.getMatRegFatherName());
                        mthrName.setText(allDetails.getMatRegMotherName());
                        fthrOccu.setText(allDetails.getMatRegFatherOccup());
                        mthrOccu.setText(allDetails.getMatRegMotherOccup());
                        noofbro.setText(allDetails.getMatRegNoBrother());
                        noofsis.setText(allDetails.getMatRegNoSister());
                        noofbromar.setText(allDetails.getMatMarBro());
                        noofsismar.setText(allDetails.getMatMarSis());
                        famsts.setText(allDetails.getMatRegStatus());
                        famtype.setText(allDetails.getMregFamType());
                        famincome.setText(allDetails.getMregFamStatus());

                        //education details
                        graduate.setText(allDetails.getMregEduc());
//                postgraduate.setText(allDetails.getMatRegPg());
//                docorate.setText(allDetails.getMatRegDocto());
//                crtfication.setText(allDetails.getMatRegCertifi());

                        //occupation details
                        ocuuptn.setText(allDetails.getMatRegOccup());
                        indsry.setText(allDetails.getMatRegIndustry());
                        servEmpAt.setText(allDetails.getMatRegEmpl());
                        pesnlIncome.setText(allDetails.getMatRegIpa());

                        //partner pref details
                        lkngfor.setText(allDetails.getMregLookingFor());
                        cntrylivin.setText(allDetails.getMregCountryLeaving());
                        statelivin.setText(allDetails.getMregStateLeaving());
                        citylivein.setText(allDetails.getMregCityLeaving());
                        minage.setText(allDetails.getMregRegMinage());
                        maxage.setText(allDetails.getMregRegMaxage());
                        minheight.setText(allDetails.getMregMinHt());
                        maxheight.setText(allDetails.getMregMaxHt());
                        ppcompexn.setText(allDetails.getMregGroomComplexion());
                        education.setText(allDetails.getMregEduc());
                        pprlgn.setText(allDetails.getMregReligion());
                        ppcast.setText(allDetails.getMregCaste());
                    }
                }catch (NullPointerException npe)
                {

                }


            }

            @Override
            public void onFailure(Call<AllDetails> call, Throwable t) {

                Toast.makeText(MyProfileActivity.this,"Your Profile is Not Filled", Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void init()
    {
        profilepic=(CircleImageView) findViewById(R.id.iv_profile);
        fname=(TextView)findViewById(R.id.d_per_fname);
        mname=(TextView)findViewById(R.id.d_per_mname);
        lname=(TextView)findViewById(R.id.d_per_lname);
        dob=(TextView)findViewById(R.id.d_per_dob);
        tob=(TextView)findViewById(R.id.d_per_brthtime);
        birplc=(TextView)findViewById(R.id.d_per_brthplace);
        ntvplc=(TextView)findViewById(R.id.d_per_ntvplace);
        mrtlsts=(TextView)findViewById(R.id.d_per_maritalstatus);
        gndr=(TextView)findViewById(R.id.d_per_gender);
        mtrtng=(TextView)findViewById(R.id.d_per_mothertounge);
        rlgn=(TextView)findViewById(R.id.d_per_ep_religion);
        cst=(TextView)findViewById(R.id.d_per_ep_cast);
        sbcst=(TextView)findViewById(R.id.d_per_subcast);
        abtme=(TextView)findViewById(R.id.d_per_abtme);
        mobno=(TextView)findViewById(R.id.d_per_mobno);
        landno=(TextView)findViewById(R.id.d_per_landline_no);
        email=(TextView)findViewById(R.id.d_per_email);
        addr=(TextView)findViewById(R.id.d_per_address);
        cntry=(TextView)findViewById(R.id.d_per_country);
        city=(TextView)findViewById(R.id.d_per_city);
        state=(TextView)findViewById(R.id.d_per_state);
        pincode=(TextView)findViewById(R.id.d_per_pincode);
        resdsts=(TextView)findViewById(R.id.d_per_residential_sttus);

        mnglk=(TextView)findViewById(R.id.d_per_manglik);
        hrscp=(TextView)findViewById(R.id.d_per_horoscope);
        gothraself=(TextView)findViewById(R.id.d_per_gothraself);
        //gothramama=(TextView)findViewById(R.id.d_per_gothramama);
        graduate=(TextView)findViewById(R.id.d_per_graduatein);
       /* postgraduate=(TextView)findViewById(R.id.d_per_postgrad);
        docorate=(TextView)findViewById(R.id.d_per_doctrate);
        crtfication=(TextView)findViewById(R.id.d_per_certfcion);*/

        ocuuptn=(TextView)findViewById(R.id.d_per_occupation);
        indsry=(TextView)findViewById(R.id.d_per_industry);
        servEmpAt=(TextView)findViewById(R.id.d_per_bsnsempat);
        pesnlIncome=(TextView)findViewById(R.id.d_per_psnalanulincome);

        fthrName=(TextView)findViewById(R.id.d_per_fatherfullname);
        mthrName=(TextView)findViewById(R.id.d_per_motherfullname);
        fthrOccu=(TextView)findViewById(R.id.d_per_ftheroccptn);
        mthrOccu=(TextView)findViewById(R.id.d_per_mthr_occptn);
        noofbro=(TextView)findViewById(R.id.d_per_noofbros);
        noofsis=(TextView)findViewById(R.id.d_per_sister);
        noofbromar=(TextView)findViewById(R.id.d_per_noofbrosmaried);
        noofsismar=(TextView)findViewById(R.id.d_per_noofsismarried);
        famsts=(TextView)findViewById(R.id.d_per_familysatus);
        famtype=(TextView)findViewById(R.id.d_per_familytype);
        famincome=(TextView)findViewById(R.id.d_per_familyanulincome);


        hght=(TextView)findViewById(R.id.d_per_height);
        wt=(TextView)findViewById(R.id.d_per_weight);
        bldgrp=(TextView)findViewById(R.id.d_per_bloodgrp);
        bdytype=(TextView)findViewById(R.id.d_per_bdyype);
        complxn=(TextView)findViewById(R.id.d_per_complexion);
        diet=(TextView)findViewById(R.id.d_per_dirt);
        smoke=(TextView)findViewById(R.id.d_per_smoke);
        drink=(TextView)findViewById(R.id.d_per_drink);
        handicap=(TextView)findViewById(R.id.d_per_handicap);
        profcreateby=(TextView)findViewById(R.id.d_per_profilecreatedby);
        hobies=(TextView)findViewById(R.id.d_per_ep_postgrad);
        intrest=(TextView)findViewById(R.id.d_per_intrests);

        lkngfor=(TextView)findViewById(R.id.d_per_lookngfor);
        cntrylivin=(TextView)findViewById(R.id.d_per_contrylvgin);
        statelivin=(TextView)findViewById(R.id.d_per_statelvgin);
        citylivein=(TextView)findViewById(R.id.d_per_citylvgin);
        minage=(TextView)findViewById(R.id.d_agefrm);
        maxage=(TextView)findViewById(R.id.d_ageto);

        minheight=(TextView)findViewById(R.id.d_per_minhtfrmft);
        maxheight=(TextView)findViewById(R.id.d_per_htfrmft);
        ppcompexn=(TextView)findViewById(R.id.d_per_cmplxn);
        education=(TextView)findViewById(R.id.d_per_educton);
        pprlgn=(TextView)findViewById(R.id.d_per_religion);
        ppcast=(TextView)findViewById(R.id.d_per_cast);
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
