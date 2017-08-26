package com.LeelaGroup.AgrawalFedration.matrimony;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.LeelaGroup.AgrawalFedration.Business_Medical_Session;
import com.LeelaGroup.AgrawalFedration.MainActivityModules;
import com.LeelaGroup.AgrawalFedration.MatrimonySession;
import com.LeelaGroup.AgrawalFedration.R;
import com.LeelaGroup.AgrawalFedration.business.Login_Business;
import com.LeelaGroup.AgrawalFedration.matrimony.models.BasicDetailAndContactInfo;
import com.LeelaGroup.AgrawalFedration.matrimony.validation.CustomValidator;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class FormBasicDetailsActivity extends AppCompatActivity {

    boolean isImageAdded = false;
    boolean isInserted=false;
    private GestureDetector gestureDetector;
    static int hour, min;
    private static int RESULT_LOAD_IMAGE = 1;
    Toolbar toolbar;
    TextView imageTitle;
    EditText etDate, etTime, etName, etMidName, etLastName,
            etBirthPlace, etNativePlace, etCast, etSubCast, etAbtMe;
    Spinner sprMartlStatus, sprMotherTong, sprReligion, sprNoOfChild, sprChildLiveingStatus;
    ImageButton ibTimePicker, ibDatePicker;
    RadioGroup rdgrpBrideGroom, rdgrpMaleFemale;
    RadioButton brideORGroom, maleORFemale;
    Button browseImage, savenNext;
    CircleImageView personImage;
    LinearLayout LL_No_OF_Childs, LL_Child_Living;

    java.sql.Time timeValue;
    SimpleDateFormat format;
    Calendar c;
    int year, month, day;
    float dX;
    float dY;
    int lastAction;

    BasicDetailAndContactInfo basicDetailAndContactInfo;

    File imageFile;
    String mat_id;
    String  mreg_am, mreg_fname, mreg_mname, mreg_lname, mreg_birth_place, mreg_birth_time, mreg_native_place, mreg_dob, mreg_age, mreg_marital_status, mreg_gender="", mreg_no_child, mreg_child_leave_status, mreg_mother_tongue, mreg_about_me="";
    String mat_reg_religion,mat_reg_caste="",mat_reg_subcaste="";
    ProgressDialog progressDialog;

    String mediaPath;


     MatrimonySession matrimonySession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_basic_details);

        matrimonySession=new MatrimonySession(getApplication());

       /* Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        mat_id=bundle.getString("mat_id",mat_id);
*/
        init();


        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Basic Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        if(matrimonySession.checkLogin())
            finish();

        HashMap<String, String> user = matrimonySession.getUserDetails();

        String name=user.get(MatrimonySession.KEY_NAME);
        mat_id=user.get(MatrimonySession.KEY_ID);
        setupSpinners();


        /*final View dragView = findViewById(R.id.draggable_view);
        gestureDetector = new GestureDetector(this, new SingleTapConfirm());
        dragView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event)) {
                    // single tap
                    startActivity(new Intent(FormBasicDetailsActivity.this, MatrimonyActivity.class));

                    return true;
                } else {

                    // your code for move and drag

                    switch (event.getActionMasked()) {
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
        });
*/
        c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);

        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        etDate = (EditText) findViewById(R.id.frm_d_per_dob);
        etTime = (EditText) findViewById(R.id.frm_d_per_tob);

//        ibDatePicker = (ImageButton) findViewById(R.id.frm_d_per_ibdob);
//        ibTimePicker = (ImageButton) findViewById(R.id.frm_d_per_ibtob);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date

                DatePickerDialog dd = new DatePickerDialog(FormBasicDetailsActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                try {
                                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                    String dateInString = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                    Date date = formatter.parse(dateInString);

                                    etDate.setText(formatter.format(date).toString());
                                    mreg_age=getAge(year,month,day);

                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }, year, month, day);

                dd.getDatePicker().setMaxDate(System.currentTimeMillis());
                dd.show();

            }
        });



        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TimePickerDialog td = new TimePickerDialog(FormBasicDetailsActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                try {
                                    String dtStart = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
                                    format = new SimpleDateFormat("HH:mm");
                                    timeValue = new java.sql.Time(format.parse(dtStart).getTime());
                                    etTime.setText(String.valueOf(timeValue));
                                    //String amPm = hourOfDay  + ":" + minute + " " + ((hourOfDay > 12) ? "PM" : "AM");
                                    String time = hourOfDay + ":" + minute;
                                    String amPm;
                                    if (hourOfDay < 12) {
                                        amPm = "AM";

                                    } else {
                                        amPm = "PM";
                                        hour = hour - 12;
                                    }
                                    etTime.setText(time + " " + amPm);
                                } catch (Exception ex) {
                                    etTime.setText(ex.getMessage().toString());
                                }
                            }
                        },
                        hour, min,
                        DateFormat.is24HourFormat(FormBasicDetailsActivity.this)
                );
                td.show();
            }
        });

        browseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);

            }
        });


    }// onCreate()---end ---


    // calculating Age From DOB
    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

                // Get the Image from data

                if (data != null) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath = cursor.getString(columnIndex);
                     imageFile= new File(mediaPath);
                    String imageName = imageFile.getName();
                    personImage.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                    // Set the Image in ImageView for Previewing the Media
                    personImage.setVisibility(View.VISIBLE);
                    imageTitle.setVisibility(View.VISIBLE);
                    imageTitle.setText(imageName);
                    isImageAdded = true;
                    cursor.close();
                }

            } else {
                Toast.makeText(this, "You Have Not Picked Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_LONG).show();
        }

    }


    public boolean validateFirst() {


        CustomValidator validator = new CustomValidator();

        final String fanme = etName.getText().toString();
        if (!validator.isValidName(fanme)) {
            etName.requestFocus();
            etName.setError("Please Enter Valid Name");
            return false;
        }
        etName.setError(null);

        final String manme = etMidName.getText().toString();
        if (!validator.isValidName(manme)) {
            etMidName.requestFocus();
            etMidName.setError("Please Enter Valid Name");
            return false;
        }
        etMidName.setError(null);

        final String lanme = etLastName.getText().toString();
        if (!validator.isValidName(lanme)) {
            etLastName.requestFocus();
            etLastName.setError("Please Enter Valid Name");
            return false;
        }
        etLastName.setError(null);

        final String bdate = etDate.getText().toString();
        if (!validator.isEmptyField(bdate)) {
            etDate.requestFocus();
            etDate.setError("Please Select Date");
            return false;
        }
        etDate.setError(null);

        final String btime = etTime.getText().toString();
        if (!validator.isEmptyField(btime)) {
            etTime.requestFocus();
            etTime.setError("Please Select Time");
            return false;
        }
        etTime.setError(null);

        final String bplace = etBirthPlace.getText().toString();
        if (!validator.isValidName(bplace)) {
            etBirthPlace.requestFocus();
            etBirthPlace.setError("Please Select Birth Place");
            return false;
        }
        etBirthPlace.setError(null);

        final String ntvplace = etNativePlace.getText().toString();
        if (!validator.isValidName(ntvplace)) {
            etNativePlace.requestFocus();
            etNativePlace.setError("Please Select Native Place");
            return false;
        }
        etNativePlace.setError(null);

        final String mrtlstatus = sprMartlStatus.getSelectedItem().toString();
        if (!validator.isEmptyField(mrtlstatus)) {
            return false;
        }


        final String mtrtng = sprMotherTong.getSelectedItem().toString();
        if (!validator.isEmptyField(mtrtng)) {
            return false;
        }


        final String rlgn = sprReligion.getSelectedItem().toString();
        if (!validator.isEmptyField(rlgn)) {
            return false;
        }


        final String cast = etCast.getText().toString();
        if (!validator.isValidName(cast)) {
            etCast.requestFocus();
            etCast.setError("Please Select Cast");
            return false;
        }
        etCast.setError(null);

        final String subcast = etSubCast.getText().toString();
        if (!validator.isValidName(subcast)) {
            etSubCast.requestFocus();
            etSubCast.setError("Please Select SubCast");
            return false;
        }
        etCast.setError(null);


        if (!isImageAdded) {
            browseImage.requestFocus();
            Toast.makeText(this, "You have not Pick an Image", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }


    public void goToMatrimonyActivity(View v) {
        startActivity(new Intent(this, MatrimonyActivity.class));
    }

    public void goToFormContactInformation(View v) {
        if (validateFirst()&& isImageAdded) {
             getBasicData();
            Intent intent = new Intent(FormBasicDetailsActivity.this, FormContactInformationActivity.class);
            intent.putExtra("mat_id",mat_id);
            intent.putExtra("imageFile",imageFile);
            intent.putExtra("mreg_am",mreg_am);
            intent.putExtra("mreg_fname",mreg_fname);
            intent.putExtra("mreg_mname",mreg_mname);
            intent.putExtra("mreg_lname",mreg_lname);
            intent.putExtra("mreg_birth_place",mreg_birth_place);
            intent.putExtra("mreg_birth_time",mreg_birth_time);
            intent.putExtra("mreg_dob",mreg_dob);
            intent.putExtra("mreg_age",mreg_age);
            intent.putExtra("mreg_marital_status",mreg_marital_status);
            intent.putExtra("mreg_native_place",mreg_native_place);
            intent.putExtra("mreg_gender",mreg_gender);
            intent.putExtra("mreg_no_child",mreg_no_child);
            intent.putExtra("mreg_child_leave_status",mreg_child_leave_status);
            intent.putExtra("mreg_mother_tongue",mreg_mother_tongue);
            intent.putExtra("mreg_about_me",mreg_about_me);
            intent.putExtra("mat_reg_religion",mat_reg_religion);
            intent.putExtra("mat_reg_caste",mat_reg_caste);
            intent.putExtra("mat_reg_subcaste",mat_reg_subcaste);
            startActivity(intent);
            FormBasicDetailsActivity.this.finish();
            //insertBasicFields();
            //insertReligionFields();



           // startActivity(new Intent(this, FormContactInformationActivity.class));
        }


    }


    private class SingleTapConfirm extends SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    }
    public void getGenderAndBrideORGroom()
    {

        rdgrpBrideGroom = (RadioGroup) findViewById(R.id.rdogrplkgfor);
        rdgrpMaleFemale = (RadioGroup) findViewById(R.id.frm_rdogrp_gender);
        int selectedIdBrideGroom = rdgrpBrideGroom.getCheckedRadioButtonId();
        brideORGroom = (RadioButton) findViewById(selectedIdBrideGroom);
        int selectedIdMaleFemale = rdgrpMaleFemale.getCheckedRadioButtonId();
        maleORFemale = (RadioButton) findViewById(selectedIdMaleFemale);
    }

    public void init() {
        browseImage = (Button) findViewById(R.id.frm_d_per_btnbrows);
        imageTitle = (TextView) findViewById(R.id.tv_img_title);
        personImage = (CircleImageView) findViewById(R.id.iv_profile);

        etName = (EditText) findViewById(R.id.frm_d_per_fname);
        etMidName = (EditText) findViewById(R.id.frm_d_per_mname);
        etLastName = (EditText) findViewById(R.id.frm_d_per_lname);
        etDate = (EditText) findViewById(R.id.frm_d_per_dob);
        etTime = (EditText) findViewById(R.id.frm_d_per_tob);
        etBirthPlace = (EditText) findViewById(R.id.frm_d_per_brthplace);
        etNativePlace = (EditText) findViewById(R.id.frm_d_per_ntvplace);
        sprMartlStatus = (Spinner) findViewById(R.id.frm_d_per_maritalstatus);
        sprNoOfChild = (Spinner) findViewById(R.id.frm_d_per_no_of_children);
        sprChildLiveingStatus = (Spinner) findViewById(R.id.frm_d_per_children_living_status);
        sprMotherTong = (Spinner) findViewById(R.id.frm_d_per_mothertounge);
        sprReligion = (Spinner) findViewById(R.id.frm_d_per_religion);
        etCast = (EditText) findViewById(R.id.frm_d_per_cast);
        etSubCast = (EditText) findViewById(R.id.frm_d_per_subcast);
        etAbtMe = (EditText) findViewById(R.id.frm_d_per_abtme);


        LL_No_OF_Childs = (LinearLayout) findViewById(R.id.LL_No_of_Childs);
        LL_Child_Living = (LinearLayout) findViewById(R.id.LL_Child_Living);
    }

    public void getBasicData()
    {
        getGenderAndBrideORGroom();
        mreg_am=brideORGroom.getText().toString();
        mreg_fname=etName.getText().toString();
        mreg_mname=etMidName.getText().toString();
        mreg_lname=etLastName.getText().toString();
        mreg_birth_place=etBirthPlace.getText().toString();
        mreg_birth_time=etTime.getText().toString();
        mreg_native_place=etNativePlace.getText().toString();
        mreg_dob=etDate.getText().toString();
        mreg_marital_status=sprMartlStatus.getSelectedItem().toString();
        mreg_gender=maleORFemale.getText().toString();
        mreg_no_child=sprNoOfChild.getSelectedItem().toString();
        mreg_child_leave_status=sprChildLiveingStatus.getSelectedItem().toString();
        mreg_mother_tongue=sprMotherTong.getSelectedItem().toString();
        mreg_about_me=etAbtMe.getText().toString();
        mat_reg_religion=sprReligion.getSelectedItem().toString();
        mat_reg_caste=etCast.getText().toString();
        mat_reg_subcaste=etSubCast.getText().toString();

    }


    void setupSpinners() {

        sprMartlStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 2 || position == 3 || position == 6) {
                    LL_Child_Living.setVisibility(View.VISIBLE);
                    LL_No_OF_Childs.setVisibility(View.VISIBLE);
                } else if(position==0)
                {
                    //Toast.makeText(FormBasicDetailsActivity.this, "Please Select Marital Status", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    LL_Child_Living.setVisibility(View.GONE);
                    LL_No_OF_Childs.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){
                                //Toast.makeText(FormBasicDetailsActivity.this, "Please Select Marital Status", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.medical_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int res_id = item.getItemId();
        switch (res_id){
            case R.id.action_med_logout:
                matrimonySession.logoutUser();
                finish();
                break;
            case android.R.id.home:
                onBackPressed();
                finish();
                return  true;

            default:
        }

        return super.onOptionsItemSelected(item);
    }



//    public void insertReligionFields()
//    {
//        mat_reg_religion=sprReligion.getSelectedItem().toString();
//        mat_reg_caste=etCast.getText().toString();
//        mat_reg_subcaste=etSubCast.getText().toString();
//
//        ServiceMatrimony serviceMatrimony=ApiClient.getRetrofit().create(ServiceMatrimony.class);
//        Call<BasicDetailAndContactInfo> socialDetailCall=serviceMatrimony.setReligionDetails(mat_reg_religion,mat_reg_caste,mat_reg_subcaste);
//
//        socialDetailCall.enqueue(new Callback<BasicDetailAndContactInfo>() {
//            @Override
//            public void onResponse(Call<BasicDetailAndContactInfo> call, Response<BasicDetailAndContactInfo> response) {
//                Toast.makeText(FormBasicDetailsActivity.this, "success", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<BasicDetailAndContactInfo> call, Throwable t) {
//
//                Toast.makeText(FormBasicDetailsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//    }

//    public void insertBasicFields()
//    {
//        /*mat_sess, mreg_prof_pic, , , , , , , , , mreg_age, , , , , ,*/
//
//        File file=new File(mediaPath);
//        mreg_am=brideORGroom.getText().toString();
//        mreg_fname=etName.getText().toString();
//        mreg_mname=etMidName.getText().toString();
//        mreg_lname=etLastName.getText().toString();
//        mreg_birth_place=etBirthPlace.getText().toString();
//        mreg_dob=etDate.getText().toString();
//        mreg_birth_time=etTime.getText().toString();
//        mreg_native_place=etNativePlace.getText().toString();
//        mreg_marital_status=sprMartlStatus.getSelectedItem().toString();
//        mreg_mother_tongue=sprMotherTong.getSelectedItem().toString();
//        mreg_no_child=sprNoOfChild.getSelectedItem().toString();
//        mreg_child_leave_status=sprChildLiveingStatus.getSelectedItem().toString();
//        mreg_gender=maleORFemale.getText().toString();
//        mreg_about_me=etAbtMe.getText().toString();
//
//
//
//       /* religion=sprReligion.getSelectedItem().toString();
//        cast=etCast.getText().toString();
//        subcast=etSubCast.getText().toString();*/
//
//        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
//        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
//
//
//        RequestBody Matsess=RequestBody.create(MediaType.parse("text/plain"),mat_id);
//
//        RequestBody Brideorgroom=RequestBody.create(MediaType.parse("text/plain"),mreg_am);
//        RequestBody Fname=RequestBody.create(MediaType.parse("text/plain"),mreg_fname);
//        RequestBody Mname=RequestBody.create(MediaType.parse("text/plain"),mreg_mname);
//        RequestBody Lname=RequestBody.create(MediaType.parse("text/plain"),mreg_lname);
//        RequestBody BirthPlace=RequestBody.create(MediaType.parse("text/plain"),mreg_birth_place);
//        RequestBody BirthDate=RequestBody.create(MediaType.parse("text/plain"),mreg_dob);
//        RequestBody BirthTime=RequestBody.create(MediaType.parse("text/plain"),mreg_birth_time);
//        RequestBody NativePlace=RequestBody.create(MediaType.parse("text/plain"),mreg_native_place);
//        RequestBody MaritalStatus=RequestBody.create(MediaType.parse("text/plain"),mreg_marital_status);
//        RequestBody MotherToung=RequestBody.create(MediaType.parse("text/plain"),mreg_mother_tongue);
//        RequestBody NoOfChilds= RequestBody.create(MediaType.parse("text/plain"),mreg_no_child);
//        RequestBody ChildLivinStaus= RequestBody.create(MediaType.parse("text/plain"),mreg_child_leave_status);
//        RequestBody MaleFemale= RequestBody.create(MediaType.parse("text/plain"),mreg_gender);
//        RequestBody AboutMe= RequestBody.create(MediaType.parse("text/plain"),mreg_about_me);
//        RequestBody Age=RequestBody.create(MediaType.parse("text/plain"),mreg_age);
//
//
////       RequestBody Religion=RequestBody.create(MediaType.parse("text/plain"),religion);
////        RequestBody Cast=RequestBody.create(MediaType.parse("text/plain"),cast);
////        RequestBody SubCast=RequestBody.create(MediaType.parse("text/plain"),subcast);
//
//       ServiceMatrimony serviceMatrimony=ApiClient.getRetrofit().create(ServiceMatrimony.class);
//        Call<BasicDetailAndContactInfo> basicDetailCall=serviceMatrimony.setBasicDetail(Matsess,fileToUpload,Brideorgroom,Fname,Mname,Lname,BirthPlace,BirthTime,NativePlace,BirthDate,Age,MaritalStatus,MaleFemale,NoOfChilds,ChildLivinStaus,MotherToung,AboutMe);
//
//
//        basicDetailCall.enqueue(new Callback<BasicDetailAndContactInfo>() {
//            @Override
//            public void onResponse(Call<BasicDetailAndContactInfo> call, Response<BasicDetailAndContactInfo> response) {
//                Toast.makeText(FormBasicDetailsActivity.this, "Success", Toast.LENGTH_SHORT).show();
//            }
//
//
//            @Override
//            public void onFailure(Call<BasicDetailAndContactInfo> call, Throwable t) {
//
//               Toast.makeText(FormBasicDetailsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
//    }

    @Override
    public void onBackPressed()
    {
        //finish();
        Intent intent = new Intent(FormBasicDetailsActivity.this, MainActivityModules.class);
        intent.putExtra("mat_id",mat_id);
        startActivity(intent);
        FormBasicDetailsActivity.this.finish();
    }

}
