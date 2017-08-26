package com.LeelaGroup.AgrawalFedration.matrimony;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.LeelaGroup.AgrawalFedration.MainActivityModules;
import com.LeelaGroup.AgrawalFedration.MatrimonySession;
import com.LeelaGroup.AgrawalFedration.Network.ApiClient;
import com.LeelaGroup.AgrawalFedration.R;
import com.LeelaGroup.AgrawalFedration.Service.ServiceMatrimony;
import com.LeelaGroup.AgrawalFedration.matrimony.models.AllDetails;
import com.LeelaGroup.AgrawalFedration.matrimony.models.CountryStateCity;
import com.LeelaGroup.AgrawalFedration.matrimony.models.ImageUploadPojo;
import com.LeelaGroup.AgrawalFedration.matrimony.validation.CustomValidator;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormPartnerPreferenceActivity extends AppCompatActivity {
    private GestureDetector gestureDetector;
    Spinner sprLkgFor,sprAgeFrom,sprAgeTo,sprResdSts,sprCoplexion,sprRlgn;
    EditText etHgtfrom,etMinHt,etMaxht,getEtWgtto,etEductn,etCast;
    CheckBox cbTermsCondtn;
    TextView termAndCond;
    AutoCompleteTextView sprCountry,sprState,etCity;

    Toolbar toolbar;
    List<CountryStateCity> listCountries;
    String[] countryName;
    ArrayAdapter<String> dataAdaptercountry;

    List<CountryStateCity> listCities;
    String[] cityName;
    ArrayAdapter<String> dataAdapterCity;

    List<CountryStateCity> listStates;
    String[] stateName;
    ArrayAdapter<String> dataAdapterState;

    private ProgressDialog pDialog;

    //declare var for basic details
    String imageName="",mreg_am, mreg_fname, mreg_mname, mreg_lname, mreg_birth_place, mreg_birth_time, mreg_native_place, mreg_dob, mreg_age, mreg_marital_status, mreg_gender, mreg_no_child, mreg_child_leave_status, mreg_mother_tongue, mreg_about_me;
    String mat_reg_religion, mat_reg_caste, mat_reg_subcaste;
    File imageFile;

    //declare var for contact details
    String mreg_landline, mreg_phone, mreg_email, mreg_addr, mreg_country, mreg_state, mreg_city, mreg_pincode, mreg_resid_status;

    //declare var for social details
    String mat_reg_manglik, mat_reg_horoscope_match, mat_reg_gothra_self;

    //declare var for education details
    String mat_reg_edu;

    //declare var for occu details
    String mat_reg_occup,mat_reg_industry,mat_reg_empl,mat_reg_ipa;

    //declare var for family details
    String mat_reg_father_name,mat_reg_mother_name,mat_reg_father_occup,mat_reg_mother_occup,mat_reg_no_brother,mat_reg_no_sister,mat_mar_bro,mat_mar_sis,mat_reg_status,mreg_fam_type,mreg_fam_lpa;

    //declare var for physical details
    String mreg_phy_ht,mreg_phy_wt,mreg_bdy_type,mreg_blood_grp,mreg_complexion,mreg_handicapped,mreg_smoke,mreg_drink,mreg_diet;

    //declare var for othr details
    String mreg_come_frm,mreg_prof_create_for,mreg_hobby,mreg_interest,mreg_pwd;

    String mat_id,mat_fname="";
    String mreg_looking_for,mreg_reg_maxage,mreg_reg_minage,mreg_country_leaving,mreg_state_leaving,mreg_city_leaving,mreg_residential_status,mreg_max_ht,mreg_min_ht,mreg_groom_complexion,mreg_educ,mreg_religion,mreg_caste;

    float dX;
    float dY;
    int lastAction;

    MatrimonySession matrimonySession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_partner_preference);


        matrimonySession =new MatrimonySession(getApplicationContext());

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        init();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Partner Preference Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if(matrimonySession.checkLogin())
            finish();


        getCountries();
        getstate();
        getcity();
        catchBasicContactSocialEducationOccupationFamilyPhysicalOtherDetails();





        /*Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        mat_id=bundle.getString("mat_id",mat_id);
*/
       /* final View dragView=findViewById(R.id.draggable_view);
        gestureDetector = new GestureDetector(this, new SingleTapConfirm());
        dragView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event)) {
                    // single tap
                    startActivity(new Intent(FormPartnerPreferenceActivity.this,MatrimonyActivity.class));

                    return true;
                } else {

                    // your code for move and drag

                    switch(event.getActionMasked()){
                        case MotionEvent.ACTION_DOWN:
                            dX = view.getX() - event.getRawX();
                            dY = view.getY() - event.getRawY();
                            lastAction = MotionEvent.ACTION_DOWN;
                            break;
                        case MotionEvent.ACTION_MOVE:
                            view.setY(event.getRawY() + dY);
                            view.setX(event.getRawX() + dX);
                            lastAction = MotionEvent.ACTION_MOVE;
                            break;
                        case MotionEvent.ACTION_UP:

                            break;
                        default:
                            return true;
                    }

                }
                return false;
            }
        });*/
    }
    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    }
    public boolean validateFirst(){


        CustomValidator validator=new CustomValidator();

        final String lkgfor=sprLkgFor.getSelectedItem().toString();
        if(!validator.isEmptyField(lkgfor)){
            return false;
        }
        final String country=sprCountry.getText().toString();
        if(!validator.isValidName(country)){
            sprCountry.requestFocus();
            sprCountry.setError("Please Enter Valid Country");
            return false;
        }
        sprCountry.setError(null);

        final String state=sprState.getText().toString();
        if(!validator.isValidName(state)){
            sprState.requestFocus();
            sprState.setError("Please Enter Valid State");
            return false;
        }
        sprState.setError(null);

        final String city=etCity.getText().toString();
        if(!validator.isValidName(city)){
            etCity.requestFocus();
            etCity.setError("Please Enter Valid State");
            return false;
        }
        etCity.setError(null);

        final String height=etMinHt.getText().toString();
        if(!validator.isEmptyField(height)){
            etMinHt.requestFocus();
            etMinHt.setError("Please Enter Valid State");
            return false;
        }
        etMinHt.setError(null);


        final String weight=etMaxht.getText().toString();
        if(!validator.isEmptyField(weight)){
            etMaxht.requestFocus();
            etMaxht.setError("Please Fill This Field");
            return false;
        }
        etMaxht.setError(null);

        /*final String eduction=etEductn.getText().toString();
        if(!validator.isEmptyField(eduction)){
            etEductn.requestFocus();
            etEductn.setError("Please Fill This Field");
            return false;
        }
        etEductn.setError(null);*/


        /*final String cast=etCast.getText().toString();
        if(!validator.isValidName(cast)){
            etCast.requestFocus();
            etCast.setError("Please Fill This Field With Alphabets Only");
            return false;
        }
        etCast.setError(null);*/

        cbTermsCondtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if ( isChecked ) {
                    cbTermsCondtn.setError(null);
                } else {
                    cbTermsCondtn.requestFocus();
                    cbTermsCondtn.setError("Please Accept Terms And Conditions");
                }
            }
        });
        if (!cbTermsCondtn.isChecked()){
            termAndCond.requestFocus();
            termAndCond.setError("Please Accept Terms And Conditions");
            Toast.makeText(this, "Please Accept Terms And Conditions", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

  /*  public void setPartnerPrefDetails(){
        mreg_looking_for=sprLkgFor.getSelectedItem().toString();
        mreg_reg_between=sprAgeFrom.getSelectedItem().toString();
        mreg_country_leaving=sprCountry.getText().toString();
        mreg_state_leaving=sprState.getText().toString();
        mreg_city_leaving=etCity.getText().toString();
        mreg_residential_status=sprResdSts.getSelectedItem().toString();
        mreg_ht=getEtHgtto.getText().toString();
        mreg_wt=etWgtFrom.getText().toString();
        mreg_groom_complexion=sprCoplexion.getSelectedItem().toString();
        mreg_educ=etEductn.getText().toString();
        mreg_religion=sprRlgn.getSelectedItem().toString();
        mreg_caste=etCast.getText().toString();

        ServiceMatrimony serviceMatrimony= ApiClient.getRetrofit().create(ServiceMatrimony.class);
        Call<PartnrPrefdetails> partnrPrefdetailsCall=serviceMatrimony.setPartPrefDetails(mreg_looking_for,mreg_reg_between,mreg_country_leaving,mreg_state_leaving,mreg_city_leaving,mreg_residential_status,mreg_ht,mreg_wt,mreg_groom_complexion,mreg_educ,mreg_religion,mreg_caste,id);
        partnrPrefdetailsCall.enqueue(new Callback<PartnrPrefdetails>() {
            @Override
            public void onResponse(Call<PartnrPrefdetails> call, Response<PartnrPrefdetails> response) {
                Toast.makeText(FormPartnerPreferenceActivity.this, "success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PartnrPrefdetails> call, Throwable t) {
                Toast.makeText(FormPartnerPreferenceActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });


    }*/

    public void getcity()
    {
        ServiceMatrimony serviceMatrimony=ApiClient.getRetrofit().create(ServiceMatrimony.class);
        Call<List<CountryStateCity>> call=serviceMatrimony.getCity();
        call.enqueue(new Callback<List<CountryStateCity>>() {
            @Override
            public void onResponse(Call<List<CountryStateCity>> call, Response<List<CountryStateCity>> response) {
                listCities=response.body();
                cityName=new String[listCities.size()];
                for(int i=0;i<listCities.size();i++)
                {
                    cityName[i]=listCities.get(i).getCity();
                }
                dataAdapterCity=new ArrayAdapter<String>(FormPartnerPreferenceActivity.this,android.R.layout.simple_list_item_1,cityName);
                etCity.setAdapter(dataAdapterCity);

            }

            @Override
            public void onFailure(Call<List<CountryStateCity>> call, Throwable t) {

                Toast.makeText(FormPartnerPreferenceActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getstate()
    {
        ServiceMatrimony serviceMatrimony=ApiClient.getRetrofit().create(ServiceMatrimony.class);
        Call<List<CountryStateCity>> call=serviceMatrimony.getStates();

        call.enqueue(new Callback<List<CountryStateCity>>() {
            @Override
            public void onResponse(Call<List<CountryStateCity>> call, Response<List<CountryStateCity>> response) {
                listStates=response.body();
                stateName=new String[listStates.size()];
                for(int i=0;i<listStates.size();i++)
                {
                    stateName[i]=listStates.get(i).getState();
                }
                dataAdapterState=new ArrayAdapter<String>(FormPartnerPreferenceActivity.this,android.R.layout.simple_list_item_1,stateName);
                sprState.setAdapter(dataAdapterState);
            }

            @Override
            public void onFailure(Call<List<CountryStateCity>> call, Throwable t) {
                Toast.makeText(FormPartnerPreferenceActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
    public void getCountries()
    {
        ServiceMatrimony serviceMatrimony=ApiClient.getRetrofit().create(ServiceMatrimony.class);
        Call<List<CountryStateCity>> call=serviceMatrimony.getCountries();

        call.enqueue(new Callback<List<CountryStateCity>>() {
            @Override
            public void onResponse(Call<List<CountryStateCity>> call, Response<List<CountryStateCity>> response) {
                listCountries=response.body();
                countryName=new String[listCountries.size()];
                for (int i=0;i<listCountries.size();i++)
                {
                    countryName[i]=listCountries.get(i).getCountry();
                }
                dataAdaptercountry=new ArrayAdapter<String>(FormPartnerPreferenceActivity.this,android.R.layout.simple_list_item_1,countryName);
                sprCountry.setAdapter(dataAdaptercountry);
            }

            @Override
            public void onFailure(Call<List<CountryStateCity>> call, Throwable t) {
                Toast.makeText(FormPartnerPreferenceActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void init()
    {
        termAndCond=(TextView)findViewById(R.id.term_and_cond);

        // etHgtfrom=(EditText)findViewById(R.id.frm_prtnrpref_et_heightfrom);
        etMinHt=(EditText)findViewById(R.id.frm_prtnrpref_et_minht);
        etMaxht=(EditText)findViewById(R.id.frm_prtnrpref_et_maxht);
        //getEtWgtto=(EditText)findViewById(R.id.frm_prtnrpref_et_weightto);
        etEductn=(EditText)findViewById(R.id.frm_prtnrpref_et_education);
        etCast=(EditText)findViewById(R.id.frm_prtnrpref_spr_cast);
        sprLkgFor=(Spinner)findViewById(R.id.frm_prtnrpref_spr_lknfor);
        sprAgeFrom=(Spinner)findViewById(R.id.frm_prtnrpref_et_minagefrom);
        sprAgeTo=(Spinner)findViewById(R.id.frm_prtnrpref_et_maxagefrom);
        // sprAgeTo=(Spinner)findViewById(R.id.frm_prtnrpref_et_ageto);


        etCity=(AutoCompleteTextView)findViewById(R.id.frm_prtnrpref_spr_city);
        sprCountry=(AutoCompleteTextView)findViewById(R.id.frm_prtnrpref_spr_cntry);
        sprState=(AutoCompleteTextView)findViewById(R.id.frm_prtnrpref_spr_state);


        sprResdSts=(Spinner)findViewById(R.id.frm_prtnrpref_spr_resdntrstts);
        sprCoplexion=(Spinner)findViewById(R.id.frm_prtnrpref_spr_complexion);
        sprRlgn=(Spinner)findViewById(R.id.frm_prtnrpref_spr_religion);
        cbTermsCondtn=(CheckBox) findViewById(R.id.frm_prtnrpref_ckbx_termcondtn);
    }


    public void saveAndGoToMatrimonyActivity(View view){

        if(validateFirst()){
            setPartnerPrefDetails();
            insertImage();
            Intent intent=new Intent(FormPartnerPreferenceActivity.this,MatrimonyActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(mat_id,"mat_id");
           // intent.putExtra("mat_fname",mat_fname);
            startActivity(intent);
            this.finish();
            //startActivity(new Intent(this,MatrimonyActivity.class));
        }
    }

    public void insertAll()
    {
        ServiceMatrimony serviceMatrimony=ApiClient.getRetrofit().create(ServiceMatrimony.class);

        Call<AllDetails> call=serviceMatrimony.insertAll(mat_id,imageName,mreg_am, mreg_fname, mreg_mname, mreg_lname, mreg_birth_place, mreg_birth_time, mreg_native_place, mreg_dob, mreg_age, mreg_marital_status, mreg_gender, mreg_no_child, mreg_child_leave_status, mreg_mother_tongue, mreg_about_me,
                mat_reg_religion, mat_reg_caste, mat_reg_subcaste,
                mreg_landline, mreg_phone, mreg_email, mreg_addr, mreg_country, mreg_state, mreg_city, mreg_pincode, mreg_resid_status,
                mat_reg_manglik, mat_reg_horoscope_match, mat_reg_gothra_self,
                mat_reg_edu,
                mat_reg_occup,mat_reg_industry,mat_reg_empl,mat_reg_ipa,
                mat_reg_father_name,mat_reg_mother_name,mat_reg_father_occup,mat_reg_mother_occup,mat_reg_no_brother,mat_reg_no_sister,mat_mar_bro,mat_mar_sis,mat_reg_status,mreg_fam_type,mreg_fam_lpa,
                mreg_phy_ht,mreg_phy_wt,mreg_bdy_type,mreg_blood_grp,mreg_complexion,mreg_handicapped,mreg_smoke,mreg_drink,mreg_diet,
                mreg_come_frm,mreg_prof_create_for,mreg_hobby,mreg_interest,mreg_pwd,
                mreg_looking_for,mreg_reg_maxage,mreg_reg_minage,mreg_country_leaving,mreg_state_leaving,mreg_city_leaving,mreg_residential_status,mreg_min_ht,mreg_max_ht,mreg_groom_complexion,mreg_educ,mreg_religion,mreg_caste
        );

        call.enqueue(new Callback<AllDetails>() {
            @Override
            public void onResponse(Call<AllDetails> call, Response<AllDetails> response) {

                matrimonySession.storePid(true);

                Toast.makeText(FormPartnerPreferenceActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<AllDetails> call, Throwable t) {
                hidepDialog();
                Toast.makeText(FormPartnerPreferenceActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }
    public void insertImage()
    {
        showpDialog();
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), imageFile);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", imageFile.getName(), requestBody);
        ServiceMatrimony serviceMatrimony=ApiClient.getRetrofit().create(ServiceMatrimony.class);
        Call<ImageUploadPojo> call=serviceMatrimony.uploadImage(fileToUpload);
        call.enqueue(new Callback<ImageUploadPojo>() {
            @Override
            public void onResponse(Call<ImageUploadPojo> call, Response<ImageUploadPojo> response)
            {

                ImageUploadPojo imageUploadPojo=response.body();
                imageName=imageUploadPojo.getImageName();
                if (imageUploadPojo!=null)
                {
                    //boolean r=imageUploadPojo.getSuccess();
                    if (imageUploadPojo.getSuccess()) {

                        insertAll();
                        Toast.makeText(getApplicationContext(), imageUploadPojo.getMessage(), Toast.LENGTH_SHORT).show();

                    } else {

                         Toast.makeText(getApplicationContext(), imageUploadPojo.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //assert serverResponse != null;
                    //Log.v("Response", serverResponse.toString());

                }
                hidepDialog();

            }

            @Override
            public void onFailure(Call<ImageUploadPojo> call, Throwable t) {
                hidepDialog();

                Toast.makeText(FormPartnerPreferenceActivity.this, "failed here", Toast.LENGTH_SHORT).show();
                Toast.makeText(FormPartnerPreferenceActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void catchBasicContactSocialEducationOccupationFamilyPhysicalOtherDetails() {

        mat_id=getIntent().getStringExtra("mat_id");
        // basic details data
        imageFile = (File) getIntent().getExtras().get("imageFile");
        mreg_am = getIntent().getStringExtra("mreg_am");
        mreg_fname = getIntent().getStringExtra("mreg_fname");
        mreg_mname = getIntent().getStringExtra("mreg_mname");
        mreg_lname = getIntent().getStringExtra("mreg_lname");
        mreg_birth_place = getIntent().getStringExtra("mreg_birth_place");
        mreg_birth_time = getIntent().getStringExtra("mreg_birth_time");
        mreg_native_place = getIntent().getStringExtra("mreg_native_place");
        mreg_dob = getIntent().getStringExtra("mreg_dob");
        mreg_age = getIntent().getStringExtra("mreg_age");
        mreg_marital_status = getIntent().getStringExtra("mreg_marital_status");
        mreg_gender = getIntent().getStringExtra("mreg_gender");
        mreg_no_child = getIntent().getStringExtra("mreg_no_child");
        mreg_child_leave_status = getIntent().getStringExtra("mreg_child_leave_status");
        mreg_mother_tongue = getIntent().getStringExtra("mreg_mother_tongue");
        mreg_about_me = getIntent().getStringExtra("mreg_about_me");
        mat_reg_religion = getIntent().getStringExtra("mat_reg_religion");
        mat_reg_caste = getIntent().getStringExtra("mat_reg_caste");
        mat_reg_subcaste = getIntent().getStringExtra("mat_reg_subcaste");

        //contact details data
        mreg_landline = getIntent().getStringExtra("mreg_landline");
        mreg_phone = getIntent().getStringExtra("mreg_phone");
        mreg_email = getIntent().getStringExtra("mreg_email");
        mreg_addr = getIntent().getStringExtra("mreg_addr");
        mreg_country = getIntent().getStringExtra("mreg_country");
        mreg_state = getIntent().getStringExtra("mreg_state");
        mreg_city = getIntent().getStringExtra("mreg_city");
        mreg_pincode = getIntent().getStringExtra("mreg_pincode");
        mreg_resid_status = getIntent().getStringExtra("mreg_resid_status");

        //social attr data
        mat_reg_manglik = getIntent().getStringExtra("mat_reg_manglik");
        mat_reg_horoscope_match = getIntent().getStringExtra("mat_reg_horoscope_match");
        mat_reg_gothra_self = getIntent().getStringExtra("mat_reg_gothra_self");
        //mat_reg_gothra_mama = getIntent().getStringExtra("mat_reg_gothra_mama");

        //education info data
        mat_reg_edu = getIntent().getStringExtra("mat_reg_edu");
//        mat_reg_pg = getIntent().getStringExtra("mat_reg_pg");
//        mat_reg_docto = getIntent().getStringExtra("mat_reg_docto");
//        mat_reg_certifi = getIntent().getStringExtra("mat_reg_certifi");

        //occupation details
        mat_reg_occup = getIntent().getStringExtra("mat_reg_occup");
        mat_reg_industry = getIntent().getStringExtra("mat_reg_industry");
        mat_reg_empl = getIntent().getStringExtra("mat_reg_empl");
        mat_reg_ipa = getIntent().getStringExtra("mat_reg_ipa");

        //family info data
        mat_reg_father_name = getIntent().getStringExtra("mat_reg_father_name");
        mat_reg_mother_name = getIntent().getStringExtra("mat_reg_mother_name");
        mat_reg_father_occup = getIntent().getStringExtra("mat_reg_father_occup");
        mat_reg_mother_occup = getIntent().getStringExtra("mat_reg_mother_occup");
        mat_reg_no_brother = getIntent().getStringExtra("mat_reg_no_brother");
        mat_reg_no_sister = getIntent().getStringExtra("mat_reg_no_sister");
        mat_mar_bro = getIntent().getStringExtra("mat_mar_bro");
        mat_mar_sis = getIntent().getStringExtra("mat_mar_sis");
        mat_reg_status = getIntent().getStringExtra("mat_reg_status");
        mreg_fam_type = getIntent().getStringExtra("mreg_fam_type");
        mreg_fam_lpa = getIntent().getStringExtra("mreg_fam_lpa");

        //physical info data
        mreg_phy_ht = getIntent().getStringExtra("mreg_phy_ht");
        mreg_phy_wt = getIntent().getStringExtra("mreg_phy_wt");
        mreg_bdy_type=getIntent().getStringExtra("mreg_bdy_type");
        mreg_blood_grp = getIntent().getStringExtra("mreg_blood_grp");
        mreg_complexion = getIntent().getStringExtra("mreg_complexion");
        mreg_handicapped = getIntent().getStringExtra("mreg_handicapped");
        mreg_smoke = getIntent().getStringExtra("mreg_smoke");
        mreg_drink = getIntent().getStringExtra("mreg_drink");
        mreg_diet = getIntent().getStringExtra("mreg_diet");

        //other info data
        mreg_come_frm = getIntent().getStringExtra("mreg_come_frm");
        mreg_prof_create_for = getIntent().getStringExtra("mreg_prof_create_for");
        mreg_hobby = getIntent().getStringExtra("mreg_hobby");
        mreg_interest = getIntent().getStringExtra("mreg_interest");
        mreg_pwd = getIntent().getStringExtra("mreg_pwd");

    }
    public void setPartnerPrefDetails(){

        //catchBasicContactSocialEducationOccupationFamilyPhysicalOtherDetails();
        mreg_looking_for=sprLkgFor.getSelectedItem().toString();
        mreg_reg_minage=sprAgeFrom.getSelectedItem().toString();
        mreg_reg_maxage=sprAgeTo.getSelectedItem().toString();
        mreg_country_leaving=sprCountry.getText().toString();
        mreg_state_leaving=sprState.getText().toString();
        mreg_city_leaving=etCity.getText().toString();
        mreg_residential_status=sprResdSts.getSelectedItem().toString();
        mreg_min_ht=etMinHt.getText().toString();
        mreg_max_ht=etMaxht.getText().toString();
        mreg_groom_complexion=sprCoplexion.getSelectedItem().toString();
        mreg_educ=etEductn.getText().toString();
        mreg_religion=sprRlgn.getSelectedItem().toString();
        mreg_caste=etCast.getText().toString();

    }
    @Override
    public void onBackPressed()
    {
        //finish();
        Intent intent = new Intent(FormPartnerPreferenceActivity.this, MainActivityModules.class);
        intent.putExtra("mat_id",mat_id);
        startActivity(intent);
        FormPartnerPreferenceActivity.this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                onBackPressed();
                finish();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }




    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog !=null && pDialog.isShowing())
            pDialog.dismiss();
    }

}
