package com.LeelaGroup.AgrawalFedration.matrimony;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.LeelaGroup.AgrawalFedration.MatrimonySession;
import com.LeelaGroup.AgrawalFedration.R;
import com.LeelaGroup.AgrawalFedration.matrimony.validation.CustomValidator;

import java.io.File;

public class FormSocialAttributeActivity extends AppCompatActivity {

    public static final int RESULT_LOAD_IMAGE = 1;
    public static final int FILE_SELECT_CODE = 0;

    Toolbar toolbar;
    String mat_id;

    //declare var for basic details
    String mreg_am, mreg_fname, mreg_mname, mreg_lname, mreg_birth_place, mreg_birth_time, mreg_native_place, mreg_dob, mreg_age, mreg_marital_status, mreg_gender, mreg_no_child, mreg_child_leave_status, mreg_mother_tongue, mreg_about_me;
    String mat_reg_religion, mat_reg_caste, mat_reg_subcaste;
    File imageFile;

    //declare var for contact details
    String mreg_landline, mreg_phone, mreg_email, mreg_addr, mreg_country, mreg_state, mreg_city, mreg_pincode, mreg_resid_status;


//    Button bn_browsephoto, bn_browsebiodata;
//    ImageView iv_biodata, iv_potrait_photo;
//    TextView titleportimg, title_biodata;
    private GestureDetector gestureDetector;
    EditText etHoroscope, etGothraSelf, etGothraMama;
    RadioGroup rgManglik;
    RadioButton rbYesOrNo;

   String mat_reg_manglik, mat_reg_horoscope_match, mat_reg_gothra_self;

    float dX;
    float dY;
    int lastAction;

    MatrimonySession matrimonySession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_social_attribute);

        matrimonySession =new MatrimonySession(getApplicationContext());

        init();


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Social Attributes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        if(matrimonySession.checkLogin())
            finish();

        catchBasicContactDetails();

       /* final View dragView = findViewById(R.id.draggable_view);
        gestureDetector = new GestureDetector(this, new SingleTapConfirm());
        dragView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event)) {
                    // single tap
                    startActivity(new Intent(FormSocialAttributeActivity.this, MatrimonyActivity.class));

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
        });*/


//        bn_browsephoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(
//                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(i, RESULT_LOAD_IMAGE);
//
//            }
//        });
//        bn_browsebiodata.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("*/*");
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//
//                try
//                {
//                    startActivityForResult(
//                            Intent.createChooser(intent, "Select a File to Upload"),
//                            FILE_SELECT_CODE);
//                }
//                catch (android.content.ActivityNotFoundException ex)
//                {
//                    // Potentially direct the user to the Market with a Dialog
//                    Toast.makeText(FormSocialAttributeActivity.this, "Please Install File Manager.",
//                            Toast.LENGTH_LONG).show();
//                }
//            }
//        });

    }//  end onCreate()

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//        switch (requestCode)
//        {
//            case FILE_SELECT_CODE:
//                if (resultCode == RESULT_OK)
//                {
//                    // Get the Uri of the selected file
//                    Uri uri = data.getData();
//                    Toast.makeText(this, "File Uri: " + uri.toString().toString(), Toast.LENGTH_SHORT).show();
//                    //Log.d(TAG, "File Uri: " + uri.toString());
//                    // Get the path
//                    String path = null;
//
//                    String txtPattern = ".txt";
//                    String jpgPattern = ".jpg";
//                    String pngPattern = ".png";
//                    String bmpPattern = ".bmp";
//                    String pdfPattern = ".pdf";
//                    String docPattern = ".doc";
//                    String docxPattern = ".docx";
//                    String docmPattern = ".docm";
//
//                    try
//                    {
//                        path = FileUtils.getPath(this, uri);
//                        File file = new File(path);
//                        String fileName=file.getName();
//
//                        try {
//                            MimeTypeMap.getFileExtensionFromUrl(file.toURL().toString());
//                            if (file==null)
//                            {
//                                Toast.makeText(this, "Please Select a file..", Toast.LENGTH_SHORT).show();
//                            }
//                            else if (file.getName().endsWith(pdfPattern.toLowerCase()))
//                            {
//                                iv_biodata.setImageResource(R.drawable.pdf_icon);
//                                iv_biodata.setVisibility(View.VISIBLE);
//                            }else if (file.getName().endsWith(txtPattern.toLowerCase()))
//                            {
//                                iv_biodata.setImageResource(R.drawable.text_icon);
//                                iv_biodata.setVisibility(View.VISIBLE);
//                            }else if (file.getName().endsWith(docPattern.toLowerCase())||file.getName().endsWith(docxPattern.toLowerCase
//                                    ())||file.getName().endsWith(docmPattern.toLowerCase()))
//                            {
//                                iv_biodata.setImageResource(R.drawable.word_icon);
//                                iv_biodata.setVisibility(View.VISIBLE);
//                            }
//                            else if (file.getName().endsWith(bmpPattern.toLowerCase())||file.getName().endsWith(pngPattern.toLowerCase
//                                    ())||file.getName().endsWith(jpgPattern.toLowerCase()))
//                            {
//                                    Uri selectedImage = data.getData();
//                                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
//                                    Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
//                                    cursor.moveToFirst();
//                                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                                    picturePath = cursor.getString(columnIndex);
//
//                                    iv_biodata.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//                                    iv_biodata.setVisibility(View.VISIBLE);
//                                    cursor.close();
//                            }
//                        } catch (MalformedURLException e) {
//                            e.printStackTrace();
//                        }
//
//                        title_biodata.setText(fileName);
//                        title_biodata.setVisibility(View.VISIBLE);
//                    }
//                    catch (URISyntaxException e)
//                    {
//                        e.printStackTrace();
//                    }
//                    Toast.makeText(this, "File Path: " + path.toString(), Toast.LENGTH_SHORT).show();
//
//                }
//                break;
//
//
//            case RESULT_LOAD_IMAGE:
//                try {
//
//                    // When an Image is picked
//                    if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
//
//                        // Get the Image from data
//                        Uri selectedImage = data.getData();
//                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//                        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//                        assert cursor != null;
//                        cursor.moveToFirst();
//
//                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                         mediaPath = cursor.getString(columnIndex);
//                        File file= new File(mediaPath);
//                        String imageName=file.getName();
//                        iv_potrait_photo.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
//                        // Set the Image in ImageView for Previewing the Media
//                        iv_potrait_photo.setVisibility(View.VISIBLE);
//                        titleportimg.setVisibility(View.VISIBLE);
//                        titleportimg.setText(imageName);
//                        cursor.close();
//
//                    } else {
//                        Toast.makeText(this, "You Have Not Picked Image", Toast.LENGTH_LONG).show();
//                    }
//                } catch (Exception e) {
//                    Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_LONG).show();
//                }
//                break;
//        }
//
//        super.onActivityResult(requestCode, resultCode, data);
//
//    }
//    private static class FileUtils
//    {
//        public static String getPath(Context context, Uri uri) throws URISyntaxException
//        {
//            if ("content".equalsIgnoreCase(uri.getScheme()))
//            {
//                String[] projection = {"_data"};
//                Cursor cursor = null;
//
//                try
//                {
//                    cursor = context.getContentResolver().query(uri, projection, null, null, null);
//                    assert cursor != null;
//                    int column_index = cursor.getColumnIndexOrThrow("_data");
//                    if (cursor.moveToFirst())
//                    {
//                        return cursor.getString(column_index);
//                    }
//                }
//                catch (Exception e)
//                {
//                    // Eat it
//                    e.printStackTrace();
//                }
//            }
//            else
//            if ("file".equalsIgnoreCase(uri.getScheme()))
//            {
//                return uri.getPath();
//            }
//
//            return null;
//        }
//
//    }


    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    }

    public boolean validateFirst() {

        CustomValidator validator = new CustomValidator();

        final String horosope = etHoroscope.getText().toString();
        if (!validator.isValidName(horosope)) {
            etHoroscope.requestFocus();
            etHoroscope.setError("Please Enter Proper Horoscope");
            return false;
        }
        etHoroscope.setError(null);

        final String gothraself = etGothraSelf.getText().toString();
        if (!validator.isValidName(gothraself)) {
            etGothraSelf.requestFocus();
            etGothraSelf.setError("Please Enter Proper Gothra");
            return false;
        }
        etGothraSelf.setError(null);

       /* final String gothramama = etGothraMama.getText().toString();
        if (!validator.isValidName(gothramama)) {
            etGothraMama.requestFocus();
            etGothraMama.setError("Please Enter Proper Gothra");
            return false;
        }
        etGothraMama.setError(null);*/

        return true;
    }

    public void goToFormContactInformation(View v) {
        startActivity(new Intent(this, FormContactInformationActivity.class));
    }

    public void goToFormEducationDetails(View v) {
        if (validateFirst()) {

            getSocialData();
            Intent intent = new Intent(FormSocialAttributeActivity.this, FormEducationDetailsActivity.class);
            //basic detail
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

            //contact details
            intent.putExtra("mreg_landline",mreg_landline);
            intent.putExtra("mreg_phone",mreg_phone);
            intent.putExtra("mreg_email",mreg_email);
            intent.putExtra("mreg_addr",mreg_addr);
            intent.putExtra("mreg_country",mreg_country);
            intent.putExtra("mreg_state",mreg_state);
            intent.putExtra("mreg_city",mreg_city);
            intent.putExtra("mreg_pincode",mreg_pincode);
            intent.putExtra("mreg_resid_status",mreg_resid_status);

            //social attribute details
            intent.putExtra("mat_reg_manglik",mat_reg_manglik);
            intent.putExtra("mat_reg_horoscope_match",mat_reg_horoscope_match);
            intent.putExtra("mat_reg_gothra_self",mat_reg_gothra_self);
            //intent.putExtra("mat_reg_gothra_mama",mat_reg_gothra_mama);

            startActivity(intent);
            this.finish();
            //setSocialattributeDetails();
           /* Intent intent = new Intent(getApplicationContext(), FormEducationDetailsActivity.class);
            intent.putExtra("mat_id", id);
            startActivity(intent);*/
            // startActivity(new Intent(this,FormEducationDetailsActivity.class));
        }

    }

    public void catchBasicContactDetails() {

        // basic details data
        mat_id=getIntent().getStringExtra("mat_id");
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

    }

    public void getManglik()
    {
        rgManglik = (RadioGroup) findViewById(R.id.frm_soclattr_rdogrp_mnglik);
        int selectedRgId = rgManglik.getCheckedRadioButtonId();
        rbYesOrNo = (RadioButton) findViewById(selectedRgId);
    }
    public void getSocialData()
    {
        getManglik();
       // catchBasicContactDetails();
        mat_reg_manglik = rbYesOrNo.getText().toString();
        mat_reg_horoscope_match = etHoroscope.getText().toString();
        mat_reg_gothra_self = etGothraSelf.getText().toString();
        //mat_reg_gothra_mama = etGothraMama.getText().toString();
    }


    //    private void setSocialattributeDetails() {
//
//        File file = new File(mediaPath);
//        File file1 = new File(picturePath);
//
//        // Parsing any Media type file
//        RequestBody requestBody1 = RequestBody.create(MediaType.parse("*/*"), file);
//        RequestBody requestBody2 = RequestBody.create(MediaType.parse("*/*"), file1);
//
//        imageToUpload= MultipartBody.Part.createFormData("file1", file.getName(), requestBody1);
//        fileToUpload = MultipartBody.Part.createFormData("file2", file1.getName(), requestBody2);
//
//        String Id=id;
//        Toast.makeText(this, Id, Toast.LENGTH_LONG).show();
//        mat_reg_manglik=rbYesOrNo.getText().toString();
//        mat_reg_horoscope_match=etHoroscope.getText().toString();
//        mat_reg_gothra_self=etGothraSelf.getText().toString();
//        mat_reg_gothra_mama=etGothraMama.getText().toString();
//
//        RequestBody Manglik = RequestBody.create(MediaType.parse("plain/text"), mat_reg_manglik);
//        RequestBody Horoscope = RequestBody.create(MediaType.parse("plain/text"), mat_reg_horoscope_match);
//        RequestBody GothraSelf = RequestBody.create(MediaType.parse("plain/text"), mat_reg_gothra_self);
//        RequestBody  GothraMama= RequestBody.create(MediaType.parse("plain/text"), mat_reg_gothra_mama);
//        RequestBody  fkId= RequestBody.create(MediaType.parse("plain/text"), id);
//
//
//        ServiceMatrimony serviceMatrimony= ApiClient.getRetrofit().create(ServiceMatrimony.class);
//        Call<SocialAndFamilyDetails> socialDetailsCall=serviceMatrimony.setSocialDetails(imageToUpload,fileToUpload,Manglik,Horoscope,GothraSelf,GothraMama,fkId);
//
//        socialDetailsCall.enqueue(new Callback<SocialAndFamilyDetails>() {
//            @Override
//            public void onResponse(Call<SocialAndFamilyDetails> call, Response<SocialAndFamilyDetails> response) {
//
//                Toast.makeText(FormSocialAttributeActivity.this, "Success", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<SocialAndFamilyDetails> call, Throwable t) {
//                Toast.makeText(FormSocialAttributeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }
    private void init() {
        etHoroscope = (EditText) findViewById(R.id.frm_soclattr_et_horoscope);
        etGothraSelf = (EditText) findViewById(R.id.frm_soclattr_et_gothra_self);
        //etGothraMama = (EditText) findViewById(R.id.frm_soclattr_et_gothra_mama);


        //bn_browsephoto = (Button) findViewById(R.id.frm_soclattr_btn_brws);
        //bn_browsebiodata = (Button) findViewById(R.id.frm_soclattr_btn_uldbiodta);
        //iv_biodata = (ImageView) findViewById(R.id.iv_biodata);
        //iv_potrait_photo = (ImageView) findViewById(R.id.iv_pt_image);
        //titleportimg = (TextView) findViewById(R.id.tv_ptimg_title);
        //title_biodata = (TextView) findViewById(R.id.tv_biodata_title);

    }
    @Override
    public void onBackPressed()
    {
        //finish();
        Intent intent = new Intent(FormSocialAttributeActivity.this, MatrimonyActivity.class);
        intent.putExtra("mat_id",mat_id);
        startActivity(intent);
        FormSocialAttributeActivity.this.finish();
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





}
