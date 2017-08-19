package com.LeelaGroup.AgrawalFedration.matrimony;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.LeelaGroup.AgrawalFedration.MatrimonySession;
import com.LeelaGroup.AgrawalFedration.Network.ApiClient;
import com.LeelaGroup.AgrawalFedration.R;
import com.LeelaGroup.AgrawalFedration.Service.ServiceMatrimony;
import com.LeelaGroup.AgrawalFedration.matrimony.models.PhysicalAndOtherDetails;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhysicalAttributeActivity extends AppCompatActivity {

    String mat_id;
    TextView mreg_phy_ht,mreg_phy_wt,mreg_bdy_type,mreg_blood_grp,mreg_complexion,
            mreg_handicapped,mreg_smoke,mreg_drink,mreg_diet;

    MatrimonySession matrimonySession;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_attribute);

      matrimonySession=new MatrimonySession(getApplicationContext());

        init();

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Physical Attributes");

        if(matrimonySession.checkLogin())
            finish();

        mat_id=getIntent().getStringExtra("mat_id");
        getPhysicalDeyails();

    }

    private void getPhysicalDeyails() {
        ServiceMatrimony serviceMatrimony= ApiClient.getRetrofit().create(ServiceMatrimony.class);
        Call<PhysicalAndOtherDetails> phyclDtlCall=serviceMatrimony.getPhysAndOtherDetls(mat_id);
        phyclDtlCall.enqueue(new Callback<PhysicalAndOtherDetails>() {
            @Override
            public void onResponse(Call<PhysicalAndOtherDetails> call, Response<PhysicalAndOtherDetails> response) {
                PhysicalAndOtherDetails phycaldetl=response.body();
                mreg_phy_ht.setText(phycaldetl.getMreg_phy_ht());
                mreg_phy_wt.setText(phycaldetl.getMreg_phy_wt());
                mreg_bdy_type.setText(phycaldetl.getMreg_bdy_type());
                mreg_blood_grp.setText(phycaldetl.getMreg_blood_grp());
                mreg_complexion.setText(phycaldetl.getMreg_complexion());
                mreg_smoke.setText(phycaldetl.getMreg_smoke());
                mreg_drink.setText(phycaldetl.getMreg_drink());
                mreg_diet.setText(phycaldetl.getMreg_diet());
                mreg_handicapped.setText(phycaldetl.getMreg_handicapped());
            }

            @Override
            public void onFailure(Call<PhysicalAndOtherDetails> call, Throwable t) {
                Toast.makeText(PhysicalAttributeActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void init() {
        mreg_phy_ht=(TextView)findViewById(R.id.d_per_height);
        mreg_phy_wt=(TextView)findViewById(R.id.d_per_weight);
        mreg_bdy_type=(TextView)findViewById(R.id.d_per_bdyype);
        mreg_blood_grp=(TextView)findViewById(R.id.d_per_bloodgrp);
        mreg_complexion=(TextView)findViewById(R.id.d_per_complexion);
        mreg_diet=(TextView)findViewById(R.id.d_per_dirt);
        mreg_smoke=(TextView)findViewById(R.id.d_per_smoke);
        mreg_drink=(TextView)findViewById(R.id.d_per_drink);
        mreg_handicapped=(TextView)findViewById(R.id.d_per_handicap);

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
