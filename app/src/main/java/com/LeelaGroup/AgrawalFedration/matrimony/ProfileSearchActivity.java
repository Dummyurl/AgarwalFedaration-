package com.LeelaGroup.AgrawalFedration.matrimony;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.LeelaGroup.AgrawalFedration.MatrimonySession;
import com.LeelaGroup.AgrawalFedration.R;
import com.LeelaGroup.AgrawalFedration.matrimony.validation.CustomValidator;

public class ProfileSearchActivity extends AppCompatActivity {
    RadioGroup rgBrdBrm;
    RadioButton rbBrideOrGroom;
    Spinner sprAgeFrom,sprAgeTo,sprHgtFromFt,sprHgtFromInch,sprHgtToFt,sprHgtToInch,
            sprRlgn,sprMrtlSts,sprMthrTong,sprCountry,sprEductn;
    CheckBox cbHoroscope,cbPhoto;
    MatrimonySession matrimonySession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_search);

        matrimonySession=new MatrimonySession(getApplicationContext());
        if(matrimonySession.checkLogin())
            finish();

    }

    public boolean validateFirst(){
        rgBrdBrm=(RadioGroup)findViewById(R.id.lkngfor_rdgrp);
        int selectedIdForBrideGroom=rgBrdBrm.getCheckedRadioButtonId();
        rbBrideOrGroom=(RadioButton)findViewById(selectedIdForBrideGroom);
        sprAgeFrom=(Spinner)findViewById(R.id.frm_srchslmt_spr_agefrmft);
        //sprAgeTo=(Spinner)findViewById(R.id.frm_srchslmt_spr_agetoft);
        //sprHgtFromFt=(Spinner)findViewById(R.id.frm_srchslmt_spr_hgtfromft);
        //sprHgtFromInch=(Spinner)findViewById(R.id.frm_srchslmt_spr_hgtfrminch);
        //sprHgtToFt=(Spinner)findViewById(R.id.frm_srchslmt_spr_hgttoft);
        //sprHgtToInch=(Spinner)findViewById(R.id.frm_srchslmt_spr_hgttoinch);
        sprRlgn=(Spinner)findViewById(R.id.frm_srchslmt_spr_relgn);
        sprMrtlSts=(Spinner)findViewById(R.id.frm_srchslmt_spr_mrtlsts);
        sprMthrTong=(Spinner)findViewById(R.id.frm_srchslmt_spr_mthrtng);
        sprCountry=(Spinner)findViewById(R.id.frm_srchslmt_spr_cntry);
        sprEductn=(Spinner)findViewById(R.id.frm_srchslmt_spr_edctn);
        cbHoroscope=(CheckBox)findViewById(R.id.frm_srchslmt_cb_hroscope);
        cbPhoto=(CheckBox)findViewById(R.id.frm_srchslmt_cb_phto);


        CustomValidator validator=new CustomValidator();


        final String agefrom=sprAgeFrom.getSelectedItem().toString();
        if(!validator.isEmptyField(agefrom)){
            return false;
        }
        final String ageto=sprAgeTo.getSelectedItem().toString();
        if(!validator.isEmptyField(ageto)){
            return false;
        }
        final String htfrmft=sprHgtFromFt.getSelectedItem().toString();
        if(!validator.isEmptyField(htfrmft)){
            return false;
        }
        final String htfrminch=sprHgtFromInch.getSelectedItem().toString();
        if(!validator.isEmptyField(htfrminch)){
            return false;
        }
        final String hgtoft=sprHgtToFt.getSelectedItem().toString();
        if(!validator.isEmptyField(hgtoft)){
            return false;
        }
        final String hgtoinch=sprHgtToInch.getSelectedItem().toString();
        if(!validator.isEmptyField(hgtoinch)){
            return false;
        }
        final String rlgn=sprRlgn.getSelectedItem().toString();
        if(!validator.isEmptyField(rlgn)){
            return false;
        }
        final String mrtlsts=sprMrtlSts.getSelectedItem().toString();
        if(!validator.isEmptyField(mrtlsts)){
            return false;
        }
        final String mthrtng=sprMthrTong.getSelectedItem().toString();
        if(!validator.isEmptyField(mthrtng)){
            return false;
        }
        final String cntry=sprCountry.getSelectedItem().toString();
        if(!validator.isEmptyField(cntry)){
            return false;
        }
        final String edctn=sprEductn.getSelectedItem().toString();
        if(!validator.isEmptyField(edctn)){
            return false;
        }
        cbPhoto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if ( isChecked ) {
                    TextView tvSerchwith=(TextView)findViewById(R.id.tv_searchwith);
                    tvSerchwith.setError(null);
                } else {
                    TextView tvSerchwith=(TextView)findViewById(R.id.tv_searchwith);
                    tvSerchwith.requestFocus();
                    tvSerchwith.setError("Please Check Both Field");
                    //cbHoroscope.setError(" preciso aceitar os Termos de Uso");
                }
            }
        });
        cbHoroscope.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if ( isChecked ) {
                    TextView tvSerchwith=(TextView)findViewById(R.id.tv_searchwith);
                    tvSerchwith.setError(null);
                } else {
                    TextView tvSerchwith=(TextView)findViewById(R.id.tv_searchwith);
                    tvSerchwith.requestFocus();
                    tvSerchwith.setError("Please Check Both Field");
                    //cbHoroscope.setError(" preciso aceitar os Termos de Uso");
                }
            }
        });
        if (!cbPhoto.isChecked()){
            TextView tvSerchwith=(TextView)findViewById(R.id.tv_searchwith);
            tvSerchwith.requestFocus();
            tvSerchwith.setError("Please Check Both Field");
            return false;
        }
        if (!cbHoroscope.isChecked()){

            TextView tvSerchwith=(TextView)findViewById(R.id.tv_searchwith);
            tvSerchwith.requestFocus();
            tvSerchwith.setError("Please Check Both Field");
            return false;
        }

        return true;
    }
    public void goToFindMatchActivity(View v){
        if (validateFirst()){

            startActivity(new Intent(this,FindMatchActivity.class));
        }
    }


}
