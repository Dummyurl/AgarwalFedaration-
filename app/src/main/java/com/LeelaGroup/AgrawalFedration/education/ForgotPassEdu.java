package com.LeelaGroup.AgrawalFedration.education;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.LeelaGroup.AgrawalFedration.ForgotPasswordPojo;
import com.LeelaGroup.AgrawalFedration.Network.ApiClient;
import com.LeelaGroup.AgrawalFedration.R;
import com.LeelaGroup.AgrawalFedration.Service.Medical.ServiceAPIEducation;
import com.LeelaGroup.AgrawalFedration.Service.ServiceMatrimony;
import com.LeelaGroup.AgrawalFedration.matrimony.SendOtpActivity;
import com.LeelaGroup.AgrawalFedration.matrimony.validation.CustomValidator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by USer on 29-08-2017.
 */

public class ForgotPassEdu extends AppCompatActivity {
    Button sendOtp;
    EditText etEmail;
    Toolbar toolbar;
    String getOtp="",userid="",otp="";
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mat_forgot_password_activity);

        toolbar  = (Toolbar) findViewById(R.id.toolbar);
        sendOtp=(Button)findViewById(R.id.fg_send);
        etEmail=(EditText)findViewById(R.id.fg_email);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Forgot Password");

        sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFirst())
                {
                    getOTP();

                }
            }
        });

    }



    private void getOTP() {
        String getEmail=etEmail.getText().toString();
        ServiceAPIEducation serviceAPIEducation= ApiClient.getRetrofit().create(ServiceAPIEducation.class);
        Call<ForgotPasswordPojo> call=serviceAPIEducation.getEduOtp(getEmail);
        call.enqueue(new Callback<ForgotPasswordPojo>() {
            @Override
            public void onResponse(Call<ForgotPasswordPojo> call, Response<ForgotPasswordPojo> response) {
                ForgotPasswordPojo forgotPasswordPojo =response.body();
                if (forgotPasswordPojo.getSuccess())
                {
                    userid= forgotPasswordPojo.getUserid();
                    otp= forgotPasswordPojo.getOTP();
                    Intent intent=new Intent(new Intent(ForgotPassEdu.this,SendOtpEdu.class));
                    intent.putExtra("otp",otp);
                    intent.putExtra("userid",userid);
                    startActivity(intent);
                    finish();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPassEdu.this);
                    builder.setTitle("Message");
                    builder.setMessage(forgotPasswordPojo.getMessage());
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();

                    //Toast.makeText(LoginMatrimony.this, forgotPasswordPojo.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordPojo> call, Throwable t) {
                Toast.makeText(ForgotPassEdu.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
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


    public boolean validateFirst(){
        etEmail.setError(null);
        CustomValidator validator=new CustomValidator();
        final String email=etEmail.getText().toString();
        if(!validator.isValidEmail(email)){
            etEmail.requestFocus();
            etEmail.setError("Enter Registered Email");
            return false;
        }
        etEmail.setError(null);

        return true;
    }
}
