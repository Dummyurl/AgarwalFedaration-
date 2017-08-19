package com.LeelaGroup.AgrawalFedration.education;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.LeelaGroup.AgrawalFedration.EducationSessionManager;
import com.LeelaGroup.AgrawalFedration.Education_Pojos.CETDetailPojo;
import com.LeelaGroup.AgrawalFedration.Education_Pojos.EducationDetailPojo;
import com.LeelaGroup.AgrawalFedration.Education_Pojos.ExamSubjectPojo;
import com.LeelaGroup.AgrawalFedration.Education_Pojos.FamilyDetailPojo;
import com.LeelaGroup.AgrawalFedration.Education_Pojos.FeatchPojo;
import com.LeelaGroup.AgrawalFedration.Education_Pojos.ImageFeatcPojo;
import com.LeelaGroup.AgrawalFedration.Education_Pojos.OtherDetailPojo;
import com.LeelaGroup.AgrawalFedration.Education_Pojos.PersonalDetailPojo;
import com.LeelaGroup.AgrawalFedration.Education_Pojos.RefferencePojo;
import com.LeelaGroup.AgrawalFedration.Network.ApiClient;
import com.LeelaGroup.AgrawalFedration.R;
import com.LeelaGroup.AgrawalFedration.Service.Medical.ServiceAPIEducation;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeatchDetails extends AppCompatActivity {
    Toolbar toolbar;

    String email;

    EducationSessionManager educationSessionManager;

    CircleImageView iv_profile;


    TextView d_per_fname, d_per_lname, d_per_dob, d_per_fathername, d_per_Mothername, d_per_gender,
            per_country1, d_per_state1, d_per_city1, d_per_address1, d_per_pincode1;
    TextView d_per_ftheroccptn, d_per_Father_MobileNo1, d_per_Father_Telephone, d_per_Designation, d_per_Anual_Income1,
            d_per_PAN1, d_per_Mother_Occupation1, d_per_Mother_Telephone1, d_per_MobileNo1, d_per_Religion2;
    TextView d_per_SSC_Board, d_per_SSC_SchoolName, d_per_SSC_graduatein, d_per_Year, d_per_SSC_Percent,
            d_per_SSC_Rank1;
    TextView d_per_HSC_Board, d_per_HSC_SchoolName, d_per_graduatein, d_per_HSC_Year,
            d_per_HSC_Percent, d_per_HSC_Rank1;
    TextView d_per_Grad_Board, d_per_Grad_SchoolName, d_per_grad_Roll_No,
            d_per_Grad_Year, d_per_Grad_Percent, d_per_Grad_Rank1, d_per_Grad_Final_Year;
    TextView d_per_Post_Grad_Board, d_per_Post_Grad_SchoolName, d_per_Grad_Roll_No, d_per_Post_Grad_Year, d_per_Post_Grad_Percent,
            d_per_Post_Grad_Rank1, d_per_Post_Grad_Final_Year;
    TextView d_per_Facilty_from_AF, d_per_Know_About_CET1, d_per_Language_CET, d_per_Examination_Center;

    TextView d_exam_name, d_per_ExS_Year, d_exam_lang, d_per_ES_Exam_Center, d_per_ES_Exam_Language,
            d_state_exam_name, state_lang_name, state_exam_center, state_sec_lang, d_per_ES_exam_Center, d_sec_ES_year;
    TextView other_activity, other_level, other_year, other_Position;

    TextView d_Ref_Name1, d_Ref_Address1, d_Ref_City1, d_Ref_Mobile1, d_Ref_Telephone1, d_Ref_Name2, d_Ref_Address2,
            d_Ref_City2, d_Ref_Telephone2, d_Ref_Mobile2;

    TextView ssc_marksheet_value, hsc_marksheet_value, graduation_marksheet_value, pg_marksheet_value, sign_value;

    FeatchPojo pdf = new FeatchPojo();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_featch_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_matrimony);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Featch Details");

        Intent intent = getIntent();

        Bundle b = intent.getExtras();

        email = b.getString("myname");

        educationSessionManager = new EducationSessionManager(getApplicationContext());

        if (educationSessionManager.checkLogin())
            finish();


        /*toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        iv_profile = (CircleImageView) findViewById(R.id.iv_profile);

                    /* Personal Details*/
        d_per_fname = (TextView) findViewById(R.id.d_per_fname);
        d_per_lname = (TextView) findViewById(R.id.d_per_lname);
        d_per_dob = (TextView) findViewById(R.id.d_per_dob);
        d_per_fathername = (TextView) findViewById(R.id.d_per_fathername);
        d_per_Mothername = (TextView) findViewById(R.id.d_per_Mothername);
        d_per_gender = (TextView) findViewById(R.id.d_per_gender);
        per_country1 = (TextView) findViewById(R.id.per_country1);
        d_per_state1 = (TextView) findViewById(R.id.d_per_state1);
        d_per_city1 = (TextView) findViewById(R.id.d_per_city1);
        d_per_address1 = (TextView) findViewById(R.id.d_per_address1);
        d_per_pincode1 = (TextView) findViewById(R.id.d_per_pincode1);


                     /*  Family Details*/
        d_per_ftheroccptn = (TextView) findViewById(R.id.d_per_ftheroccptn);
        d_per_Father_MobileNo1 = (TextView) findViewById(R.id.d_per_Father_MobileNo1);
        d_per_Father_Telephone = (TextView) findViewById(R.id.d_per_Father_Telephone);
        d_per_Designation = (TextView) findViewById(R.id.d_per_Designation);
        //d_per_Anual_Income1 = (TextView)findViewById(R.id.d_per_Anual_Income1);
        d_per_PAN1 = (TextView) findViewById(R.id.d_per_PAN1);
        d_per_Mother_Telephone1 = (TextView) findViewById(R.id.d_per_Mother_Telephone1);
        d_per_Mother_Occupation1 = (TextView) findViewById(R.id.d_per_Mother_Occupation1);
        d_per_MobileNo1 = (TextView) findViewById(R.id.d_per_MobileNo1);
        d_per_Religion2 = (TextView) findViewById(R.id.d_per_Religion2);


                 /* SSC  Education Details*/
        d_per_SSC_Board = (TextView) findViewById(R.id.d_per_SSC_Board);
        d_per_SSC_SchoolName = (TextView) findViewById(R.id.d_per_SSC_SchoolName);
        d_per_SSC_graduatein = (TextView) findViewById(R.id.d_per_SSC_graduatein);
        d_per_Year = (TextView) findViewById(R.id.d_per_Year);
        d_per_SSC_Percent = (TextView) findViewById(R.id.d_per_SSC_Percent);
        d_per_SSC_Rank1 = (TextView) findViewById(R.id.d_per_SSC_Rank1);

                /*HSC Education Details*/

        d_per_HSC_Board = (TextView) findViewById(R.id.d_per_HSC_Board);
        d_per_HSC_SchoolName = (TextView) findViewById(R.id.d_per_HSC_SchoolName);
        d_per_graduatein = (TextView) findViewById(R.id.d_per_graduatein);
        d_per_HSC_Year = (TextView) findViewById(R.id.d_per_HSC_Year);
        d_per_HSC_Percent = (TextView) findViewById(R.id.d_per_HSC_Percent);
        d_per_HSC_Rank1 = (TextView) findViewById(R.id.d_per_HSC_Rank1);

                 /*Graduation Details*/

        d_per_Grad_Board = (TextView) findViewById(R.id.d_per_Grad_Board);
        d_per_Grad_SchoolName = (TextView) findViewById(R.id.d_per_Grad_SchoolName);
        d_per_grad_Roll_No = (TextView) findViewById(R.id.d_per_grad_Roll_No);
        d_per_Grad_Year = (TextView) findViewById(R.id.d_per_Grad_Year);
        d_per_Grad_Percent = (TextView) findViewById(R.id.d_per_Grad_Percent);
        d_per_Grad_Rank1 = (TextView) findViewById(R.id.d_per_Grad_Rank1);
        d_per_Grad_Final_Year = (TextView) findViewById(R.id.d_per_Grad_Final_Year);


                    /*PostGraduation Details*/

        d_per_Post_Grad_Board = (TextView) findViewById(R.id.d_per_Post_Grad_Board);
        d_per_Post_Grad_SchoolName = (TextView) findViewById(R.id.d_per_Post_Grad_SchoolName);
        d_per_Grad_Roll_No = (TextView) findViewById(R.id.d_per_Grad_Roll_No);
        d_per_Post_Grad_Year = (TextView) findViewById(R.id.d_per_Post_Grad_Year);
        d_per_Post_Grad_Percent = (TextView) findViewById(R.id.d_per_Post_Grad_Percent);
        d_per_Post_Grad_Rank1 = (TextView) findViewById(R.id.d_per_Post_Grad_Rank1);
        d_per_Post_Grad_Final_Year = (TextView) findViewById(R.id.d_per_Post_Grad_Final_Year);

                   /* CET Details*/

        d_per_Facilty_from_AF = (TextView) findViewById(R.id.d_per_Facilty_from_AF);
        // d_per_Know_About_CET1 = (TextView)findViewById(R.id.d_per_Know_About_CET1);
        d_per_Language_CET = (TextView) findViewById(R.id.d_per_Language_CET);
        d_per_Examination_Center = (TextView) findViewById(R.id.d_per_Examination_Center);


                    /*Appear Exam Details*/

        d_exam_name = (TextView) findViewById(R.id.d_exam_name);
        d_per_ExS_Year = (TextView) findViewById(R.id.d_per_ExS_Year);
        d_exam_lang = (TextView) findViewById(R.id.d_exam_lang);
        d_per_ES_Exam_Center = (TextView) findViewById(R.id.d_per_ES_Exam_Center);
        d_per_ES_Exam_Language = (TextView) findViewById(R.id.d_per_ES_Exam_Language);
        d_state_exam_name = (TextView) findViewById(R.id.d_state_exam_name);
        // state_lang_name = (TextView)findViewById(R.id.state_lang_name);
        state_exam_center = (TextView) findViewById(R.id.state_exam_center);
        //state_sec_lang = (TextView)findViewById(R.id.state_sec_lang);
        d_sec_ES_year = (TextView) findViewById(R.id.d_sec_ES_year);


                            /* Other Details*/


        other_activity = (TextView) findViewById(R.id.other_activity);
        other_level = (TextView) findViewById(R.id.other_level);
        other_year = (TextView) findViewById(R.id.other_year);
        other_Position = (TextView) findViewById(R.id.other_Position);


                           /*Refrence Details*/

        d_Ref_Name1 = (TextView) findViewById(R.id.d_Ref_Name1);
        d_Ref_Address1 = (TextView) findViewById(R.id.d_Ref_Address1);
        d_Ref_City1 = (TextView) findViewById(R.id.d_Ref_City1);
        d_Ref_Mobile1 = (TextView) findViewById(R.id.d_Ref_Mobile1);
        d_Ref_Telephone1 = (TextView) findViewById(R.id.d_Ref_Telephone1);
        d_Ref_Name2 = (TextView) findViewById(R.id.d_Ref_Name2);
        d_Ref_Address2 = (TextView) findViewById(R.id.d_Ref_Address2);
        d_Ref_City2 = (TextView) findViewById(R.id.d_Ref_City2);
        d_Ref_Telephone2 = (TextView) findViewById(R.id.d_Ref_Telephone2);
        d_Ref_Mobile2 = (TextView) findViewById(R.id.d_per_Ref_Mobile2);


                            /*Image Upload Document*/

       /* ssc_marksheet_value=(TextView)findViewById(R.id.ssc_marksheet_value);
        hsc_marksheet_value=(TextView)findViewById(R.id.hsc_marksheet_value);
        graduation_marksheet_value=(TextView)findViewById(R.id.graduation_marksheet_value);
        pg_marksheet_value=(TextView)findViewById(R.id.pg_marksheet_value);
        sign_value=(TextView)findViewById(R.id.sign_value);*/


        featchImage();
        fetchPersonalDetail();
        fetchFamilyDetail();
        fetchEducationDetail();
        fetchCetDetail();
        fetchExamSubjectDetail();
        fetchOtherDetail();
        fetchRefferenceDetail();


    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_education, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int selected_id = item.getItemId();

        if (selected_id == R.id.action_marksheet) {
            Bundle b = new Bundle();
            b.putString("myname", email);

            Intent intent = new Intent(getApplicationContext(), Education_Upload_Doc.class);
            intent.putExtras(b);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
//            startActivity(new Intent(this, Education_Upload_Doc.class));
        } else if (selected_id == R.id.action_View_Form) {
            startActivity(new Intent(this, FeatchDetails.class));
        } else if (selected_id == R.id.education_logout) {
            educationSessionManager.logoutUser();

        } else if (selected_id == android.R.id.home) {
            onBackPressed();
            finish();
        }


        return super.onOptionsItemSelected(item);
    }
*/
    public void featchImage() {
        ServiceAPIEducation service = ApiClient.getRetrofit().create(ServiceAPIEducation.class);

        Call<ImageFeatcPojo> ifp = service.getImageDeTail(email);

        ifp.enqueue(new Callback<ImageFeatcPojo>() {
            @Override
            public void onResponse(Call<ImageFeatcPojo> call, Response<ImageFeatcPojo> response) {
                ImageFeatcPojo ifp = response.body();

                String img = ifp.getEdProfOtherCert();

                Glide.with(FeatchDetails.this).load(ifp.getEdProfOtherCert()).into(iv_profile);

               /* ssc_marksheet_value.setText(ifp.getEdProfSscMarksheet());
                hsc_marksheet_value.setText(ifp.getEdProfHscMarksheet());
                graduation_marksheet_value.setText(ifp.getEdProfGdMarksheet());
                pg_marksheet_value.setText(ifp.getEdProfPgMarksheet());
                sign_value.setText(ifp.getEdProfScanSign());*/

            }

            @Override
            public void onFailure(Call<ImageFeatcPojo> call, Throwable t) {

                AlertDialog.Builder alert = new AlertDialog.Builder(FeatchDetails.this);

                alert.setTitle("Error");
                alert.setMessage(t.getMessage());

                alert.show();

                //  Toast.makeText(FeatchDetails.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void fetchPersonalDetail() {

        ServiceAPIEducation service = ApiClient.getRetrofit().create(ServiceAPIEducation.class);

        Call<PersonalDetailPojo> pdf = service.getPersonalDeTail(email);

        pdf.enqueue(new Callback<PersonalDetailPojo>() {
            @Override
            public void onResponse(Call<PersonalDetailPojo> call, Response<PersonalDetailPojo> response) {

                PersonalDetailPojo pdf = response.body();
                d_per_fname.setText(pdf.getPd_fname());
                d_per_lname.setText(pdf.getPd_lname());
                d_per_dob.setText(pdf.getPd_dob());
                d_per_fathername.setText(pdf.getPd_father_name());
                d_per_Mothername.setText(pdf.getPd_mother_name());
                d_per_gender.setText(pdf.getPd_gender());
                d_per_state1.setText(pdf.getPd_state());
                d_per_city1.setText(pdf.getPd_city());
                d_per_address1.setText(pdf.getPd_addr());
                d_per_pincode1.setText(pdf.getPd_pincode());

                Toast.makeText(FeatchDetails.this, "Successfully Featch", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<PersonalDetailPojo> call, Throwable t) {

                Toast.makeText(FeatchDetails.this, "Unsuccessfully Featch", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void fetchFamilyDetail() {

        ServiceAPIEducation service = ApiClient.getRetrofit().create(ServiceAPIEducation.class);

        Call<FamilyDetailPojo> fdf = service.getFamilyDeTail(email);

        fdf.enqueue(new Callback<FamilyDetailPojo>() {
            @Override
            public void onResponse(Call<FamilyDetailPojo> call, Response<FamilyDetailPojo> response) {

                FamilyDetailPojo fdf = response.body();

                d_per_ftheroccptn.setText(fdf.getFd_fathr_occup());
                d_per_Father_MobileNo1.setText(fdf.getFd_fathr_mob());
                d_per_Father_Telephone.setText(fdf.getFd_fathr_telephone());
                d_per_Designation.setText(fdf.getFd_fathr_desig());
                // d_per_Anual_Income1.setText(fdf.getFd_fathr_income());
                d_per_PAN1.setText(fdf.getFd_fathr_pan());
                d_per_Mother_Telephone1.setText(fdf.getFa_mothr_telephone());
                d_per_Mother_Occupation1.setText(fdf.getFa_mothr_occup());
                d_per_MobileNo1.setText(fdf.getFa_mothr_mob());
                d_per_Religion2.setText(fdf.getFa_mothr_religion());

                Toast.makeText(FeatchDetails.this, "Successfully Featch", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<FamilyDetailPojo> call, Throwable t) {

                Toast.makeText(FeatchDetails.this, "Unsuccessfully Featch", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void fetchEducationDetail() {
        ServiceAPIEducation service = ApiClient.getRetrofit().create(ServiceAPIEducation.class);

        Call<EducationDetailPojo> edf = service.getEducationDeTail(email);

        edf.enqueue(new Callback<EducationDetailPojo>() {
            @Override
            public void onResponse(Call<EducationDetailPojo> call, Response<EducationDetailPojo> response) {

                EducationDetailPojo edf = response.body();

                d_per_SSC_Board.setText(edf.getEd_ssc_board());
                d_per_SSC_SchoolName.setText(edf.getEd_ssc_name());
                d_per_SSC_graduatein.setText(edf.getEd_ssc_roll());
                d_per_Year.setText(edf.getEd_ssc_year());
                d_per_SSC_Percent.setText(edf.getEd_ssc_percent());
                d_per_SSC_Rank1.setText(edf.getEd_ssc_rank());

                /*HSC Education Details*/

                d_per_HSC_Board.setText(edf.getEd_hsc_board());
                d_per_HSC_SchoolName.setText(edf.getEd_hsc_name());
                d_per_graduatein.setText(edf.getEd_hsc_roll());
                d_per_HSC_Year.setText(edf.getEd_hsc_year());
                d_per_HSC_Percent.setText(edf.getEd_hsc_percent());
                d_per_HSC_Rank1.setText(edf.getEd_hsc_rank());

                 /*Graduation Details*/

                d_per_Grad_Board.setText(edf.getEd_gd_university());
                d_per_Grad_SchoolName.setText(edf.getEd_gd_inst());
                d_per_grad_Roll_No.setText(edf.getEd_gd_degree_name());
                d_per_Grad_Year.setText(edf.getEd_gd_year());
                d_per_Grad_Percent.setText(edf.getEd_gd_percent());
                d_per_Grad_Rank1.setText(edf.getEd_gd_rank());
                d_per_Grad_Final_Year.setText(edf.getEd_gd_appear_final());


                    /*PostGraduation Details*/

                d_per_Post_Grad_Board.setText(edf.getEd_pg_university());
                d_per_Post_Grad_SchoolName.setText(edf.getEd_pg_inst());
                d_per_Grad_Roll_No.setText(edf.getEd_pg_degree_name());
                d_per_Post_Grad_Year.setText(edf.getEd_pg_year());
                d_per_Post_Grad_Percent.setText(edf.getEd_pg_percent());
                d_per_Post_Grad_Rank1.setText(edf.getEd_pg_rank());
                d_per_Post_Grad_Final_Year.setText(edf.getEd_pg_menu());


            }

            @Override
            public void onFailure(Call<EducationDetailPojo> call, Throwable t) {

            }
        });


    }

    public void fetchCetDetail() {

        ServiceAPIEducation service = ApiClient.getRetrofit().create(ServiceAPIEducation.class);

        Call<CETDetailPojo> cdf = service.getCETDeTail(email);

        cdf.enqueue(new Callback<CETDetailPojo>() {
            @Override
            public void onResponse(Call<CETDetailPojo> call, Response<CETDetailPojo> response) {

                CETDetailPojo cdf = response.body();

                d_per_Facilty_from_AF.setText(cdf.getCet_get_facility());
//                d_per_Know_About_CET1.setText(cdf.getCd_cm_from());
                d_per_Language_CET.setText(cdf.getCd_lang());
                d_per_Examination_Center.setText(cdf.getCd_exam_center());
            }

            @Override
            public void onFailure(Call<CETDetailPojo> call, Throwable t) {

            }
        });

    }

    public void fetchExamSubjectDetail() {

        ServiceAPIEducation service = ApiClient.getRetrofit().create(ServiceAPIEducation.class);

        Call<ExamSubjectPojo> esdf = service.getExamSubjectDeTail(email);

        esdf.enqueue(new Callback<ExamSubjectPojo>() {
            @Override
            public void onResponse(Call<ExamSubjectPojo> call, Response<ExamSubjectPojo> response) {

                ExamSubjectPojo esdf = response.body();

                d_exam_name.setText(esdf.getEs_upsc_exam());
                d_per_ExS_Year.setText(esdf.getEs_upsc_prel_year());
                d_exam_lang.setText(esdf.getEs_upsc_lang());
                d_per_ES_Exam_Center.setText(esdf.getEs_state_prel_state());
                d_per_ES_Exam_Language.setText(esdf.getEs_upsc_lang());
                d_state_exam_name.setText(esdf.getEs_state_exam());
                // state_lang_name.setText(esdf.getEs_state_lang());
                state_exam_center.setText(esdf.getEs_state_prel_state());
                //  state_sec_lang.setText(esdf.getEs_state_lang());
                d_sec_ES_year.setText(esdf.getEs_state_prel_year());
            }

            @Override
            public void onFailure(Call<ExamSubjectPojo> call, Throwable t) {

            }
        });


    }

    public void fetchOtherDetail() {

        ServiceAPIEducation service = ApiClient.getRetrofit().create(ServiceAPIEducation.class);

        Call<OtherDetailPojo> odf = service.getOtherDeTail(email);

        odf.enqueue(new Callback<OtherDetailPojo>() {
            @Override
            public void onResponse(Call<OtherDetailPojo> call, Response<OtherDetailPojo> response) {

                OtherDetailPojo odf = response.body();

                other_activity.setText(odf.getOther_activity());
                other_level.setText(odf.getOther_national_level());
                other_year.setText(odf.getOther_year());
                other_Position.setText(odf.getOther_pos());
            }

            @Override
            public void onFailure(Call<OtherDetailPojo> call, Throwable t) {

            }
        });

    }

    public void fetchRefferenceDetail() {

        ServiceAPIEducation service = ApiClient.getRetrofit().create(ServiceAPIEducation.class);

        Call<RefferencePojo> rdf = service.getRefferenceDeTail(email);

        rdf.enqueue(new Callback<RefferencePojo>() {
            @Override
            public void onResponse(Call<RefferencePojo> call, Response<RefferencePojo> response) {

                RefferencePojo rdf = response.body();

                d_Ref_Name1.setText(rdf.getRef1_name());
                d_Ref_Address1.setText(rdf.getRef1_addr());
                d_Ref_City1.setText(rdf.getRef1_city());
                d_Ref_Mobile1.setText(rdf.getRef1_mobile());
                d_Ref_Telephone1.setText(rdf.getRef1_telephone());
                d_Ref_Name2.setText(rdf.getRef2_name());
                d_Ref_Address2.setText(rdf.getRef2_addr());
                d_Ref_City2.setText(rdf.getRef2_city());
                d_Ref_Telephone2.setText(rdf.getRef2_telephone());
                d_Ref_Mobile2.setText(rdf.getRef2_mobile());
            }

            @Override
            public void onFailure(Call<RefferencePojo> call, Throwable t) {

            }
        });


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
