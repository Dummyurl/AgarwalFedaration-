package com.LeelaGroup.AgrawalFedration.education;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.LeelaGroup.AgrawalFedration.EducationSessionManager;
import com.LeelaGroup.AgrawalFedration.Education_Pojos.ServerResponse;
import com.LeelaGroup.AgrawalFedration.Network.ApiClient;
import com.LeelaGroup.AgrawalFedration.R;
import com.LeelaGroup.AgrawalFedration.Service.Medical.ServiceAPIEducation;

import java.io.File;
import java.io.FileNotFoundException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Education_Upload_Doc extends AppCompatActivity implements View.OnClickListener {

    static final int SELECTED_PICTURE_SSC= 1;
    static final int SELECTED_PICTURE_HSC= 5;
    static final int SELECTED_PICTURE_GRAD= 2;
    static final int SELECTED_PICTURE_POST= 3;
    static final int SELECTED_PICTURE_EXTRA= 7;
    static final int SELECTED_PICTURE_CERTIFICATE= 8;

    ProgressDialog progressDialog;

    ImageView ssc_btn_Result,hsc_btn_Result,grad_btn_Result,post_grad_btn_Result,Profile_btn_Result,Sign_btn_Result;

    Toolbar toolbar;

    String email;

    String filePath,filePath1,filePath2,filePath3,filePath5,filePath6;
    EducationSessionManager educationSessionManager;
    Button bt_ssc_bws,bt_hsc_bws,bt_ug_bws,bt_pg_bws,bt_pass_bws,bt_sign_bws;
    ImageView ssc_Result, hsc_Result,graduation_Result,post_graduate_Result,other_Certificate,other_Result;

    TextView ssc_Result_name,hsc_Result_name,graduate_text,post_graduation_text,other_activity,extra_Activity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education__upload__doc);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");

        educationSessionManager = new EducationSessionManager(getApplicationContext());

        if (educationSessionManager.checkLogin())
            finish();

        Intent intent = getIntent();

        Bundle b = intent.getExtras();

        email = b.getString("myname");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_matrimony);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Upload Documents");

        bt_ssc_bws=(Button)findViewById(R.id.browse_ssc);
        bt_hsc_bws=(Button)findViewById(R.id.browse_hsc);
        bt_ug_bws=(Button)findViewById(R.id.browse_UG);
        bt_pg_bws=(Button)findViewById(R.id.browse_PG);
        bt_pass_bws=(Button)findViewById(R.id.browse_pass);
        bt_sign_bws=(Button)findViewById(R.id.browse_sign);

        ssc_Result = (ImageView)findViewById(R.id.ssc_Result);
        hsc_Result = (ImageView)findViewById(R.id.hsc_Result);
        graduation_Result = (ImageView)findViewById(R.id.graduation_Result);
        post_graduate_Result = (ImageView)findViewById(R.id.post_graduate_Result);
        other_Certificate = (ImageView)findViewById(R.id.other_Certificate);
        other_Result = (ImageView)findViewById(R.id.other_Result);

       // ssc_btn_Result = (Button)findViewById(R.id.ssc_btn_Result);
        /*hsc_btn_Result = (Button)findViewById(R.id.hsc_btn_Result);*/
        ssc_Result_name =(TextView)findViewById(R.id.ten_ssc);
        hsc_Result_name =(TextView)findViewById(R.id.twelve_hsc);
        graduate_text =(TextView)findViewById(R.id.graduate_text);
        post_graduation_text =(TextView)findViewById(R.id.post_graduation_text);
        other_activity =(TextView)findViewById(R.id.other_activity);
        extra_Activity =(TextView)findViewById(R.id.extra_Activity);

        //upload_image = (Button) findViewById(R.id.upload_image);
        ssc_btn_Result = (ImageView) findViewById(R.id.ssc_btn_Result);
        hsc_btn_Result = (ImageView) findViewById(R.id.hsc_btn_Result);
        grad_btn_Result = (ImageView) findViewById(R.id.grad_btn_Result);
        post_grad_btn_Result = (ImageView) findViewById(R.id.post_grad_btn_Result);
        Sign_btn_Result = (ImageView) findViewById(R.id.sign_btn_Result);
        Profile_btn_Result = (ImageView) findViewById(R.id.Profile_btn_Result);

        ssc_btn_Result.setOnClickListener(this);
        Profile_btn_Result.setOnClickListener(this);
        hsc_btn_Result.setOnClickListener(this);
        grad_btn_Result.setOnClickListener(this);
        post_grad_btn_Result.setOnClickListener(this);
        Sign_btn_Result.setOnClickListener(this);


        bt_ssc_bws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.setType("*/*");
                i.setAction(i.ACTION_GET_CONTENT);
                startActivityForResult(i.createChooser(i,"select image"), SELECTED_PICTURE_SSC);
            }
        });
        bt_hsc_bws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(j, SELECTED_PICTURE_HSC);
            }
        });
        bt_ug_bws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(k, SELECTED_PICTURE_GRAD);
            }
        });
        bt_pg_bws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent l = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(l, SELECTED_PICTURE_POST);
            }
        });
        bt_pass_bws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(m, SELECTED_PICTURE_EXTRA);
            }
        });
        bt_sign_bws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(n, SELECTED_PICTURE_CERTIFICATE);
            }
        });
        /*upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  uploadMultipleFiles();

               // startActivity(new Intent(Education_Upload_Doc.this,Profile.class));
            }
        });*/

        ssc_btn_Result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sscResult();
            }
        });

        hsc_btn_Result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hscResult();
            }
        });

        Profile_btn_Result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProfileResult();

            }
        });

        grad_btn_Result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gradResult();
            }
        });

        post_grad_btn_Result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pgResult();
            }
        });

        Sign_btn_Result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sign();
            }
        });



    }



  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_education,menu);
        return true;
    }*/




    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {
            case R.id.ssc_Result:
               // Toast.makeText(Education_Upload_Doc.this, "10th Result", Toast.LENGTH_LONG).show();
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.setType("*/*");
                i.setAction(i.ACTION_GET_CONTENT);
                startActivityForResult(i.createChooser(i,"select image"), SELECTED_PICTURE_SSC);
                break;


            case R.id.hsc_Result:
               // Toast.makeText(Education_Upload_Doc.this, "12th Result", Toast.LENGTH_SHORT).show();
                Intent j = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(j, SELECTED_PICTURE_HSC);
                break;

            case R.id.graduation_Result:
               // Toast.makeText(Education_Upload_Doc.this, "Graduation Result", Toast.LENGTH_SHORT).show();
                Intent k = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(k, SELECTED_PICTURE_GRAD);
                break;

            case R.id.post_graduate_Result:
                //Toast.makeText(Education_Upload_Doc.this, "Post Graduation Result", Toast.LENGTH_SHORT).show();
                Intent l = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(l, SELECTED_PICTURE_POST);
                break;

            case R.id.other_Certificate:
               // Toast.makeText(Education_Upload_Doc.this, "Extra Activity Result", Toast.LENGTH_SHORT).show();
                Intent m = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(m, SELECTED_PICTURE_EXTRA);
                break;

            case R.id.other_Result:
               // Toast.makeText(Education_Upload_Doc.this, "Extra Activity Result", Toast.LENGTH_SHORT).show();
                Intent n = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(n, SELECTED_PICTURE_CERTIFICATE);
                break;

        }

    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECTED_PICTURE_SSC:
                if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                    Uri uri = data.getData();
                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    filePath = cursor.getString(columnIndex);
                  //  ssc_Result_name.setText(filePath);
                    cursor.close();
                    Bitmap yourSelectedImageSSC = BitmapFactory.decodeFile(filePath);

                    ssc_Result.setImageBitmap(yourSelectedImageSSC);
                    ssc_Result.setVisibility(View.VISIBLE);
                    bt_ssc_bws.setVisibility(View.GONE);
                    ssc_Result.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    ssc_Result.getAdjustViewBounds();
                }
                break;
            case SELECTED_PICTURE_HSC:
                if (resultCode == RESULT_OK && data !=  null && data.getData() != null)
                {
                    Uri uri1 = data.getData();
                    String[] projection1 = {MediaStore.Images.Media.DATA};
                    Cursor cursor1 = getContentResolver().query(uri1, projection1, null, null, null);
                    cursor1.moveToFirst();
                    int columnIndex1 = cursor1.getColumnIndex(projection1[0]);
                    filePath1 = cursor1.getString(columnIndex1);
                   // hsc_Result_name.setText(filePath1);
                    cursor1.close();
                    Bitmap yourSelectedImageHSC = BitmapFactory.decodeFile(filePath1);
                    hsc_Result.setImageBitmap(yourSelectedImageHSC);
                    hsc_Result.setVisibility(View.VISIBLE);
                    bt_hsc_bws.setVisibility(View.GONE);
                    hsc_Result.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    hsc_Result.getAdjustViewBounds();
                }
                break;


            case SELECTED_PICTURE_GRAD:
                if (resultCode == RESULT_OK && data !=  null && data.getData() != null)
                {
                    Uri uri2 = data.getData();
                    String[] projection2 = {MediaStore.Images.Media.DATA};
                    Cursor cursor2 = getContentResolver().query(uri2, projection2, null, null, null);
                    cursor2.moveToFirst();
                    int columnIndex2 = cursor2.getColumnIndex(projection2[0]);
                    filePath2 = cursor2.getString(columnIndex2);
                  //  graduate_text.setText(filePath2);
                    cursor2.close();
                    Bitmap yourSelectedImageGraduation = BitmapFactory.decodeFile(filePath2);
                    graduation_Result.setImageBitmap(yourSelectedImageGraduation);
                    graduation_Result.setVisibility(View.VISIBLE);
                    bt_ug_bws.setVisibility(View.GONE);
                    graduation_Result.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    graduation_Result.getAdjustViewBounds();
                }
                break;

            case SELECTED_PICTURE_POST:
                if (resultCode == RESULT_OK && data !=  null && data.getData() != null)
                {
                    Uri uri3 = data.getData();
                    String[] projection3 = {MediaStore.Images.Media.DATA};
                    Cursor cursor3 = getContentResolver().query(uri3, projection3, null, null, null);
                    cursor3.moveToFirst();
                    int columnIndex3 = cursor3.getColumnIndex(projection3[0]);
                    filePath3 = cursor3.getString(columnIndex3);
                  //  post_graduation_text.setText(filePath3);
                    cursor3.close();
                    Bitmap yourSelectedImageCPostGraduation= BitmapFactory.decodeFile(filePath3);
                    post_graduate_Result.setImageBitmap(yourSelectedImageCPostGraduation);
                    post_graduate_Result.setVisibility(View.VISIBLE);
                    bt_pg_bws.setVisibility(View.GONE);
                    post_graduate_Result.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    post_graduate_Result.getAdjustViewBounds();
                }
                break;

            case SELECTED_PICTURE_EXTRA:
                if (resultCode == RESULT_OK && data !=  null)
                {
                    Uri uri5 = data.getData();
                    String[] projection5 = {MediaStore.Images.Media.DATA};
                    Cursor cursor5 = getContentResolver().query(uri5, projection5, null, null, null);
                    cursor5.moveToFirst();
                    int columnIndex5 = cursor5.getColumnIndex(projection5[0]);
                    filePath5 = cursor5.getString(columnIndex5);
                   // other_activity.setText(filePath5);
                    cursor5.close();
                    Bitmap yourSelectedImageCertificate = BitmapFactory.decodeFile(filePath5);
                    other_Certificate.setImageBitmap(yourSelectedImageCertificate);
                    other_Certificate.setVisibility(View.VISIBLE);
                    bt_pass_bws.setVisibility(View.GONE);
                    other_Certificate.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    other_Certificate.getAdjustViewBounds();
                }
                break;

            case SELECTED_PICTURE_CERTIFICATE:
                if (resultCode == RESULT_OK && data !=  null)
                {
                    Uri uri6 = data.getData();
                    String[] projection6 = {MediaStore.Images.Media.DATA};
                    Cursor cursor6 = getContentResolver().query(uri6, projection6, null, null, null);
                    cursor6.moveToFirst();
                    int columnIndex6 = cursor6.getColumnIndex(projection6[0]);
                    filePath6 = cursor6.getString(columnIndex6);
                    //extra_Activity.setText(filePath6);
                    cursor6.close();
                    Bitmap yourSelectedImageExtra = BitmapFactory.decodeFile(filePath6);
                    other_Result.setImageBitmap(yourSelectedImageExtra);
                    other_Result.setVisibility(View.VISIBLE);
                    bt_sign_bws.setVisibility(View.GONE);
                    other_Result.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    other_Result.getAdjustViewBounds();
                }
                break;
        }

    }

    public void sscResult()
    {
        progressDialog.show();

        File file = null;

        if(filePath!= null && !filePath.equals("null")) {

             file = new File(filePath);

        }

        RequestBody requestBody1=null;
        if(file!= null && !file.equals("null")) {
            requestBody1 = RequestBody.create(MediaType.parse("*/*"), file);

        }
        RequestBody sess = RequestBody.create(MediaType.parse("text/plain"), email);

        MultipartBody.Part fileToUpload1 =null;
        if(file!=null && !file.equals("null")) {
            fileToUpload1 = MultipartBody.Part.createFormData("file1", file.getName(), requestBody1);

        }

        ServiceAPIEducation service = ApiClient.getRetrofit().create(ServiceAPIEducation.class);

        Call<ServerResponse> ssc = service.SSCPicture(sess,fileToUpload1);

        ssc.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse ssc = response.body();

                if (ssc != null) {
                    if (ssc.isSuccess()) {
                        Toast.makeText(getApplicationContext(), ssc.getMessage(), Toast.LENGTH_SHORT).show();

                       /* AlertDialog.Builder alert = new AlertDialog.Builder(Education_Upload_Doc.this);

                        alert.setTitle("MSG");
                        alert.setMessage(ssc.getMessage());

                        alert.show();*/

                    } else {
                        /*Toast.makeText(getApplicationContext(), ssc.getMessage(), Toast.LENGTH_SHORT).show();*/

                        AlertDialog.Builder alert = new AlertDialog.Builder(Education_Upload_Doc.this);

                        alert.setTitle("MSG");
                        alert.setMessage(ssc.getMessage());

                        alert.show();
                    }
                } else {
                    assert ssc != null;
                    Log.v("Response", ssc.toString());
                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                AlertDialog.Builder alert = new AlertDialog.Builder(Education_Upload_Doc.this);

                alert.setTitle("MSG");
                alert.setMessage("Uploading Failed...");

                alert.show();

                progressDialog.dismiss();

            }
        });
    }

    public void hscResult()
    {
        progressDialog.show();

        File file1 = null;

        if(filePath1!= null && !filePath1.equals("null")) {

            file1 = new File(filePath1);

        }

        RequestBody requestBody2=null;
        if(file1!= null && !file1.equals("null")) {
            requestBody2 = RequestBody.create(MediaType.parse("*/*"), file1);

        }
        RequestBody sess = RequestBody.create(MediaType.parse("text/plain"), email);

        MultipartBody.Part fileToUpload2 =null;

        if(file1!=null && !file1.equals("null")) {

          fileToUpload2 = MultipartBody.Part.createFormData("file2", file1.getName(), requestBody2);

        }

        ServiceAPIEducation service = ApiClient.getRetrofit().create(ServiceAPIEducation.class);

        Call<ServerResponse> hsc = service.HSCPicture(sess,fileToUpload2);

        hsc.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse hsc = response.body();

                if (hsc != null) {
                    if (hsc.isSuccess()) {
                        Toast.makeText(getApplicationContext(), hsc.getMessage(), Toast.LENGTH_SHORT).show();

                        /*AlertDialog.Builder alert = new AlertDialog.Builder(Education_Upload_Doc.this);

                        alert.setTitle("MSG");
                        alert.setMessage(hsc.getMessage());

                        alert.show();*/

                    } else {
                        /*Toast.makeText(getApplicationContext(), ssc.getMessage(), Toast.LENGTH_SHORT).show();*/

                        AlertDialog.Builder alert = new AlertDialog.Builder(Education_Upload_Doc.this);

                        alert.setTitle("MSG");
                        alert.setMessage(hsc.getMessage());

                        alert.show();
                    }
                } else {
                    assert hsc != null;
                    Log.v("Response", hsc.toString());
                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                AlertDialog.Builder alert = new AlertDialog.Builder(Education_Upload_Doc.this);

                alert.setTitle("MSG");
                alert.setMessage("Uploading Failed...");

                alert.show();

                progressDialog.dismiss();

            }
        });
    }

    public void gradResult()
    {
        progressDialog.show();

        File file2 = null;

        if(filePath2!= null && !filePath2.equals("null")) {
            file2 = new File(filePath2);
        }

        RequestBody requestBody3=null;
        if(file2!= null && !file2.equals("null")) {
          requestBody3 = RequestBody.create(MediaType.parse("*/*"), file2);
        }

        RequestBody sess = RequestBody.create(MediaType.parse("text/plain"), email);

        MultipartBody.Part fileToUpload3 =null;

        if(file2!=null && !file2.equals("null")) {

         fileToUpload3 = MultipartBody.Part.createFormData("file3", file2.getName(), requestBody3);

        }

        ServiceAPIEducation service = ApiClient.getRetrofit().create(ServiceAPIEducation.class);

        Call<ServerResponse> grad = service.GradPicture(sess,fileToUpload3);

        grad.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse grad = response.body();

                if (grad != null) {
                    if (grad.isSuccess()) {
                        Toast.makeText(getApplicationContext(), grad.getMessage(), Toast.LENGTH_SHORT).show();

                        /*AlertDialog.Builder alert = new AlertDialog.Builder(Education_Upload_Doc.this);

                        alert.setTitle("MSG");
                        alert.setMessage(grad.getMessage());

                        alert.show();*/

                    } else {
                        /*Toast.makeText(getApplicationContext(), ssc.getMessage(), Toast.LENGTH_SHORT).show();*/

                        AlertDialog.Builder alert = new AlertDialog.Builder(Education_Upload_Doc.this);

                        alert.setTitle("MSG");
                        alert.setMessage(grad.getMessage());

                        alert.show();
                    }
                } else {
                    assert grad != null;
                    Log.v("Response", grad.toString());
                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                AlertDialog.Builder alert = new AlertDialog.Builder(Education_Upload_Doc.this);

                alert.setTitle("MSG");
                alert.setMessage("Uploading Failed...");

                alert.show();

                progressDialog.dismiss();

            }
        });

    }

    public void pgResult()
    {
        progressDialog.show();

        File file3 = null;

        if(filePath3!= null && !filePath3.equals("null")) {

            file3 = new File(filePath3);

        }

        RequestBody requestBody4=null;
        if(file3!= null && !file3.equals("null")) {

            requestBody4 = RequestBody.create(MediaType.parse("*/*"), file3);

        }

        RequestBody sess = RequestBody.create(MediaType.parse("text/plain"), email);

        MultipartBody.Part fileToUpload4 =null;

        if(file3!=null && !file3.equals("null")) {

            fileToUpload4 = MultipartBody.Part.createFormData("file4", file3.getName(), requestBody4);

        }

        ServiceAPIEducation service = ApiClient.getRetrofit().create(ServiceAPIEducation.class);

        Call<ServerResponse> pg = service.PostGradPicture(sess,fileToUpload4);

        pg.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse pg = response.body();

                if (pg != null) {
                    if (pg.isSuccess()) {
                        Toast.makeText(getApplicationContext(), pg.getMessage(), Toast.LENGTH_SHORT).show();

                        /*AlertDialog.Builder alert = new AlertDialog.Builder(Education_Upload_Doc.this);

                        alert.setTitle("MSG");
                        alert.setMessage(pg.getMessage());

                        alert.show();*/

                    } else {
                        /*Toast.makeText(getApplicationContext(), ssc.getMessage(), Toast.LENGTH_SHORT).show();*/

                        AlertDialog.Builder alert = new AlertDialog.Builder(Education_Upload_Doc.this);

                        alert.setTitle("MSG");
                        alert.setMessage(pg.getMessage());

                        alert.show();
                    }
                } else {
                    assert pg != null;
                    Log.v("Response", pg.toString());
                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                AlertDialog.Builder alert = new AlertDialog.Builder(Education_Upload_Doc.this);

                alert.setTitle("MSG");
                alert.setMessage("Uploading Failed...");

                alert.show();

                progressDialog.dismiss();
            }
        });



    }

    public void ProfileResult()
    {
        progressDialog.show();

        File file4 = null;

        if(filePath5!= null && !filePath5.equals("null")) {

            file4 = new File(filePath5);

        }

        RequestBody requestBody5=null;
        if(file4!= null && !file4.equals("null")) {

            requestBody5 = RequestBody.create(MediaType.parse("*/*"), file4);
        }

        RequestBody sess = RequestBody.create(MediaType.parse("text/plain"), email);

        MultipartBody.Part fileToUpload5 =null;

        if(file4!=null && !file4.equals("null")) {

            fileToUpload5 = MultipartBody.Part.createFormData("file5", file4.getName(), requestBody5);

        }

        ServiceAPIEducation service = ApiClient.getRetrofit().create(ServiceAPIEducation.class);

        Call<ServerResponse> profile = service.ProfilePicture(sess,fileToUpload5);

        profile.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse serverResponse = response.body();

                if (serverResponse != null) {
                    if (serverResponse.isSuccess()) {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        /*AlertDialog.Builder alert = new AlertDialog.Builder(Education_Upload_Doc.this);

                        alert.setTitle("MSG");
                        alert.setMessage(serverResponse.getMessage());

                        alert.show();*/

                    } else {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    assert serverResponse != null;
                    Log.v("Response", serverResponse.toString());
                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Toast.makeText(Education_Upload_Doc.this, "unsucessfull uploading", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });


    }

    public void sign()
    {
        progressDialog.show();
        File file5 =null;

        if(filePath6!= null && !filePath6.equals("null")) {

            file5 = new File(filePath6);

        }

        RequestBody requestBody6=null;
        if(file5!= null && !file5.equals("null")) {
            requestBody6 = RequestBody.create(MediaType.parse("*/*"), file5);
        }

        RequestBody sess = RequestBody.create(MediaType.parse("text/plain"), email);

        MultipartBody.Part fileToUpload6 =null;

        if(file5!=null && !file5.equals("null")) {

            fileToUpload6 = MultipartBody.Part.createFormData("file6", file5.getName(), requestBody6);

        }

        ServiceAPIEducation service = ApiClient.getRetrofit().create(ServiceAPIEducation.class);

        Call<ServerResponse> sign = service.SignPicture(sess,fileToUpload6);

        sign.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse sign = response.body();

                if (sign != null) {
                    if (sign.isSuccess()) {
                        Toast.makeText(getApplicationContext(), sign.getMessage(), Toast.LENGTH_SHORT).show();

                        /*AlertDialog.Builder alert = new AlertDialog.Builder(Education_Upload_Doc.this);

                        alert.setTitle("MSG");
                        alert.setMessage(sign.getMessage());

                        alert.show();*/

                    } else {
                        /*Toast.makeText(getApplicationContext(), ssc.getMessage(), Toast.LENGTH_SHORT).show();*/

                        AlertDialog.Builder alert = new AlertDialog.Builder(Education_Upload_Doc.this);

                        alert.setTitle("MSG");
                        alert.setMessage(sign.getMessage());

                        alert.show();
                    }
                } else {
                    assert sign != null;
                    Log.v("Response", sign.toString());
                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                AlertDialog.Builder alert = new AlertDialog.Builder(Education_Upload_Doc.this);

                alert.setTitle("MSG");
                alert.setMessage("Uploading Failed...");

                alert.show();

                progressDialog.dismiss();

            }
        });
    }

    private void uploadMultipleFiles()
    {
        progressDialog.show();

        File file5 =null;
        File file = new File(filePath);
        File file1 = new File(filePath1);
        File file2 = new File(filePath2);
        File file3 = new File(filePath3);
        File file4 = new File(filePath5);
        if(filePath6!= null && !filePath6.equals("null")) {
            file5 = new File(filePath6);
        }
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("*/*"), file);
        RequestBody requestBody2 = RequestBody.create(MediaType.parse("*/*"), file1);
        RequestBody requestBody3 = RequestBody.create(MediaType.parse("*/*"), file2);
        RequestBody requestBody4 = RequestBody.create(MediaType.parse("*/*"), file3);
        RequestBody requestBody5 = RequestBody.create(MediaType.parse("*/*"), file4);
        RequestBody requestBody6=null;
        if(file5!= null && !file5.equals("null")) {
             requestBody6 = RequestBody.create(MediaType.parse("*/*"), file5);
        }
        RequestBody sess = RequestBody.create(MediaType.parse("text/plain"), email);
        MultipartBody.Part fileToUpload6 =null;
        MultipartBody.Part fileToUpload1 = MultipartBody.Part.createFormData("file1", file.getName(), requestBody1);
        MultipartBody.Part fileToUpload2 = MultipartBody.Part.createFormData("file2", file1.getName(), requestBody2);
        MultipartBody.Part fileToUpload3 = MultipartBody.Part.createFormData("file3", file2.getName(), requestBody3);
        MultipartBody.Part fileToUpload4 = MultipartBody.Part.createFormData("file4", file3.getName(), requestBody4);

        MultipartBody.Part fileToUpload5 = MultipartBody.Part.createFormData("file5", file4.getName(), requestBody5);
        if(file5!=null && !file5.equals("null")) {
             fileToUpload6 = MultipartBody.Part.createFormData("file6", file5.getName(), requestBody6);
        }

        ServiceAPIEducation service = ApiClient.getRetrofit().create(ServiceAPIEducation.class);

        Call<ServerResponse> call = service.uploadMulFile
                (sess,fileToUpload1, fileToUpload2 , fileToUpload3 ,fileToUpload4 , fileToUpload5 ,fileToUpload6);

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse serverResponse = response.body();

                if (serverResponse != null) {
                    if (serverResponse.isSuccess()) {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        Bundle b = new Bundle();
                        b.putString("myname", email);

                        Intent intent = new Intent(getApplicationContext(), Profile.class);
                        intent.putExtras(b);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    assert serverResponse != null;
                    Log.v("Response", serverResponse.toString());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Toast.makeText(Education_Upload_Doc.this, "unsucessfull uploading", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });
    }

    /*@Override
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

        }else if (selected_id == R.id.action_View_Form)
        {
            startActivity(new Intent(this, FeatchDetails.class));
        }else if (selected_id == R.id.education_logout)
        {
            educationSessionManager.logoutUser();
            //startActivity(new Intent(this, FeatchDetails.class));
        }

        return true;
    }
*/
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
