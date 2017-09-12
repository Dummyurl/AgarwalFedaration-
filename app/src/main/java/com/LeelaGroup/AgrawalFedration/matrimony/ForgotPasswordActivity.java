package com.LeelaGroup.AgrawalFedration.matrimony;

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
import com.LeelaGroup.AgrawalFedration.ResetPasswordPojo;
import com.LeelaGroup.AgrawalFedration.Service.ServiceMatrimony;
import com.LeelaGroup.AgrawalFedration.matrimony.validation.CustomValidator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by USer on 10-08-2017.
 */

public class ForgotPasswordActivity extends AppCompatActivity {

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
        ServiceMatrimony serviceMatrimony= ApiClient.getRetrofit().create(ServiceMatrimony.class);
        Call<ForgotPasswordPojo> call=serviceMatrimony.getOtp(getEmail);
        call.enqueue(new Callback<ForgotPasswordPojo>() {
            @Override
            public void onResponse(Call<ForgotPasswordPojo> call, Response<ForgotPasswordPojo> response) {
                ForgotPasswordPojo forgotPasswordPojo =response.body();
                if (forgotPasswordPojo.getSuccess())
                {
                    userid= forgotPasswordPojo.getUserid();
                    otp= forgotPasswordPojo.getOTP();
                    Intent intent=new Intent(new Intent(ForgotPasswordActivity.this,SendOtpActivity.class));
                    intent.putExtra("otp",otp);
                    intent.putExtra("userid",userid);
                    startActivity(intent);
                    finish();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPasswordActivity.this);
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
                Toast.makeText(ForgotPasswordActivity.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
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

//    // code for Forgot PAssword
//    private void forgotPass() {
//        AlertDialog.Builder fpalertDailog = new AlertDialog.Builder(this);
//        fpalertDailog.setTitle("Forgot Password");
//        fpalertDailog.setCancelable(false);
//        View fpview = getLayoutInflater().inflate(R.layout.mat_forgot_password_activity, null);
//        final EditText email = (EditText) fpview.findViewById(R.id.fg_email);
//        Button button_send = (Button) fpview.findViewById(R.id.fg_send);
//        Button button_cancle = (Button) fpview.findViewById(R.id.fg_cancle);
//
//        fpalertDailog.setView(fpview);
//        final AlertDialog fpdailog = fpalertDailog.create();
//        fpdailog.show();
//
//        button_cancle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fpdailog.dismiss();
//            }
//        });
//        button_send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CustomValidator validate = new CustomValidator();
//                getEmail = email.getText().toString();
//                if (validate.isValidEmail(getEmail)) {
//                    email.setError(null);
//                    fpdailog.dismiss();
//                    getOTP();
//
//                }
//                else {
//                    email.requestFocus();
//                    email.setError("Please Enter Valid Email");
//
//                }
//
//            }
//        });
//
//    }
//
//    private void getOTP() {
//        ServiceMatrimony serviceMatrimony= ApiClient.getRetrofit().create(ServiceMatrimony.class);
//        Call<ForgotPasswordPojo> call=serviceMatrimony.getOtp(getEmail);
//        call.enqueue(new Callback<ForgotPasswordPojo>() {
//            @Override
//            public void onResponse(Call<ForgotPasswordPojo> call, Response<ForgotPasswordPojo> response) {
//                ForgotPasswordPojo forgotPasswordPojo =response.body();
//                if (forgotPasswordPojo.getSuccess())
//                {
//                    userid= forgotPasswordPojo.getUserid();
//                    otp= forgotPasswordPojo.getOTP();
//
//                    matchOtp();
//
//                }
//                else {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginMatrimony.this);
//                    builder.setTitle("Message");
//                    builder.setMessage(forgotPasswordPojo.getMessage());
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    });
//                    builder.show();
//
//                    //Toast.makeText(LoginMatrimony.this, forgotPasswordPojo.getMessage(), Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ForgotPasswordPojo> call, Throwable t) {
//                Toast.makeText(LoginMatrimony.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//
//    private void matchOtp() {
//
//        AlertDialog.Builder moalertDailog = new AlertDialog.Builder(this);
//        moalertDailog.setTitle("Send OTP");
//        moalertDailog.setCancelable(false);
//        View moview = getLayoutInflater().inflate(R.layout.mat_send_otp_activity, null);
//        final EditText etOtp = (EditText) moview.findViewById(R.id.et_otp);
//        Button button_send = (Button) moview.findViewById(R.id.otp_send);
//        Button button_cancle = (Button) moview.findViewById(R.id.otp_cancle);
//        moalertDailog.setView(moview);
//        final AlertDialog modailog = moalertDailog.create();
//        modailog.show();
//
//        button_cancle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                modailog.dismiss();
//            }
//        });
//        button_send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CustomValidator validate = new CustomValidator();
//                String matchotp = etOtp.getText().toString();
//
//                if (validate.isValidPincode(matchotp)) {
//                    etOtp.setError(null);
//                    if (matchotp.equals(otp))
//                    {
//                        etOtp.setError(null);
//                        modailog.dismiss();
//                        resetPassword();
//                    }else {
//                        etOtp.requestFocus();
//                        etOtp.setError("OTP Not Match");
//                    }
//
//                }
//                else {
//                    etOtp.requestFocus();
//                    etOtp.setError("OTP Must be 6 digit Length");
//                }
//            }
//        });
//
//    }
//
//    private void resetPassword() {
//
//        AlertDialog.Builder rpalertDailog = new AlertDialog.Builder(this);
//        rpalertDailog.setTitle("Reset Password");
//        rpalertDailog.setCancelable(false);
//        View rpview = getLayoutInflater().inflate(R.layout.mat_reset_password_activity, null);
//        final EditText etpass = (EditText) rpview.findViewById(R.id.rp_pass);
//        final EditText etcnfpass = (EditText) rpview.findViewById(R.id.rp_cnfpass);
//        Button button_rpsave = (Button) rpview.findViewById(R.id.rp_save);
//        Button button_rpcancle = (Button) rpview.findViewById(R.id.rp_cancle);
//
//        rpalertDailog.setView(rpview);
//        final AlertDialog dailog = rpalertDailog.create();
//        dailog.show();
//
//        button_rpcancle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dailog.dismiss();
//            }
//        });
//
//        button_rpsave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CustomValidator validate = new CustomValidator();
//                pass = etpass.getText().toString();
//                String cnfpass = etcnfpass.getText().toString();
//
//                if (validate.isValidPassword(pass)) {
//                    etpass.setError(null);
//                    if (pass.equals(cnfpass))
//                    {
//                        etcnfpass.setError(null);
//                        dailog.dismiss();
//                        savePassword();
//                    }
//                    else {
//                        etcnfpass.requestFocus();
//                        etcnfpass.setError("Password Not Match");
//                    }
//
//                }
//                else {
//                    etpass.requestFocus();
//                    etpass.setError("Password Must Be 6 Digit Long");
//
//                }
//            }
//        });
//    }
//
//    private void savePassword() {
//        ServiceMatrimony serviceMatrimony=ApiClient.getRetrofit().create(ServiceMatrimony.class);
//        Call<ResetPasswordPojo> call=serviceMatrimony.resetPassword(userid,pass);
//        call.enqueue(new Callback<ResetPasswordPojo>() {
//            @Override
//            public void onResponse(Call<ResetPasswordPojo> call, Response<ResetPasswordPojo> response) {
//                ResetPasswordPojo passwordPojo=response.body();
//                if (passwordPojo.getSuccess())
//                {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginMatrimony.this);
//                    builder.setTitle("Message");
//                    builder.setMessage(passwordPojo.getMessage());
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    });
//                    builder.show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResetPasswordPojo> call, Throwable t) {
//                Toast.makeText(LoginMatrimony.this,"Please Check Internet Connection", Toast.LENGTH_LONG).show();
//
//            }
//        });
//
//
//    }
}
