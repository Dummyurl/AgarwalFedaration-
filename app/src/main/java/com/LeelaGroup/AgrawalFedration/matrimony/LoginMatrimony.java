package com.LeelaGroup.AgrawalFedration.matrimony;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.LeelaGroup.AgrawalFedration.FontManager;
import com.LeelaGroup.AgrawalFedration.ForgotPasswordPojo;
import com.LeelaGroup.AgrawalFedration.MainActivityModules;
import com.LeelaGroup.AgrawalFedration.MatrimonySession;
import com.LeelaGroup.AgrawalFedration.Network.ApiClient;
import com.LeelaGroup.AgrawalFedration.R;
import com.LeelaGroup.AgrawalFedration.ResetPasswordPojo;
import com.LeelaGroup.AgrawalFedration.Service.ServiceMatrimony;
import com.LeelaGroup.AgrawalFedration.matrimony.models.LoginModel;
import com.LeelaGroup.AgrawalFedration.matrimony.validation.CustomValidator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginMatrimony extends AppCompatActivity {

    TextView newregister,frogotPass;
    EditText email,password;
    ProgressBar progress;
    Button login;
    Toolbar toolbar;
    SharedPreferences preferences;
    private ProgressDialog pDialog;
    String mat_id="",mat_fname="",mat_lname="";
    String getEmail="",userid="",otp="",pass="";
    String mat_email,mat_pwd;
    MatrimonySession matrimonySession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_matrimony);

       matrimonySession=new MatrimonySession(getApplicationContext());

        login =(Button)findViewById(R.id.button);
        email =(EditText)findViewById(R.id.emailphone);
        password = (EditText)findViewById(R.id.logpass);
        frogotPass=(TextView)findViewById(R.id.mat_forgot_pass);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);




        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFirst())
                {
                    setLogin();
                    /*Intent intent = new Intent(getApplicationContext(), MatrimonyActivity.class);
                    startActivity(intent);*/

                }
            }
        });

        newregister = (TextView)findViewById(R.id.newregister);
        newregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginMatrimony.this,MatrimonyRegistration.class);

                startActivity(intent);
                finish();
            }
        });

        frogotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(LoginMatrimony.this,ForgotPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);


    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent=new Intent(LoginMatrimony.this,MainActivityModules.class);
        startActivity(intent);
        finish();
        return true;
    }

    public void setLogin()
    {
        showpDialog();
        mat_email= email.getText().toString();
       // Toast.makeText(this, mat_email, Toast.LENGTH_SHORT).show();
        mat_pwd= password.getText().toString();
        //Toast.makeText(this, mat_pwd, Toast.LENGTH_SHORT).show();
        ServiceMatrimony serviceMatrimony= ApiClient.getRetrofit().create(ServiceMatrimony.class);
        Call<LoginModel> loginCall=serviceMatrimony.setLoginDetails(mat_email,mat_pwd);

        loginCall.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
               hidepDialog();
               LoginModel loginModel=response.body();


                if (loginModel.getSuccess())
                {
                    
                    Toast.makeText(LoginMatrimony.this, loginModel.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MatrimonyActivity.class);
                    mat_id=loginModel.getMatId();
                    mat_fname=loginModel.getMatFname();
                    mat_lname=loginModel.getMatLname();
                    //intent.putExtra("mat_id",mat_id);
                    matrimonySession.createUserLoginSession(mat_id,mat_fname+" "+mat_lname);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   // intent.putExtra("mat_fname",mat_fname);
                    Toast.makeText(LoginMatrimony.this, loginModel.getMatId(), Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    email.setText("");
                    password.setText("");
                    email.requestFocus();
                    finish();
                }
                Toast.makeText(LoginMatrimony.this, loginModel.getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                hidepDialog();
                Toast.makeText(LoginMatrimony.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
    private boolean validateFirst() {

        CustomValidator validator = new CustomValidator();

        final String Email = email.getText().toString();
        if (!validator.isValidEmail(Email)) {
            email.requestFocus();
            email.setError("Please Enter Valid Email");
            return false;
        }
        email.setError(null);

        final String pass = password.getText().toString();
        if (!validator.isValidPassword(pass)) {
            password.requestFocus();
            password.setError("Please Enter Valid Password");
            return false;
        }
            return true;
    }


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }



   /* // code for Forgot PAssword
    private void forgotPass() {
        AlertDialog.Builder fpalertDailog = new AlertDialog.Builder(this);
        fpalertDailog.setTitle("Forgot Password");
        fpalertDailog.setCancelable(false);
        View fpview = getLayoutInflater().inflate(R.layout.mat_forgot_password_activity, null);
        final EditText email = (EditText) fpview.findViewById(R.id.fg_email);
        Button button_send = (Button) fpview.findViewById(R.id.fg_send);
        Button button_cancle = (Button) fpview.findViewById(R.id.fg_cancle);

        fpalertDailog.setView(fpview);
        final AlertDialog fpdailog = fpalertDailog.create();
        fpdailog.show();

        button_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fpdailog.dismiss();
            }
        });
        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomValidator validate = new CustomValidator();
                getEmail = email.getText().toString();
                if (validate.isValidEmail(getEmail)) {
                    email.setError(null);
                    fpdailog.dismiss();
                    getOTP();

                }
                else {
                    email.requestFocus();
                    email.setError("Please Enter Valid Email");

                }

            }
        });

    }

    private void getOTP() {
        ServiceMatrimony serviceMatrimony=ApiClient.getRetrofit().create(ServiceMatrimony.class);
        Call<ForgotPasswordPojo> call=serviceMatrimony.getOtp(getEmail);
        call.enqueue(new Callback<ForgotPasswordPojo>() {
            @Override
            public void onResponse(Call<ForgotPasswordPojo> call, Response<ForgotPasswordPojo> response) {
                ForgotPasswordPojo forgotPasswordPojo =response.body();
                if (forgotPasswordPojo.getSuccess())
                {
                    userid= forgotPasswordPojo.getUserid();
                    otp= forgotPasswordPojo.getOTP();

                    matchOtp();

                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginMatrimony.this);
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
                Toast.makeText(LoginMatrimony.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void matchOtp() {

        AlertDialog.Builder moalertDailog = new AlertDialog.Builder(this);
        moalertDailog.setTitle("Send OTP");
        moalertDailog.setCancelable(false);
        View moview = getLayoutInflater().inflate(R.layout.mat_send_otp_activity, null);
        final EditText etOtp = (EditText) moview.findViewById(R.id.et_otp);
        Button button_send = (Button) moview.findViewById(R.id.otp_send);
        Button button_cancle = (Button) moview.findViewById(R.id.otp_cancle);
        moalertDailog.setView(moview);
        final AlertDialog modailog = moalertDailog.create();
        modailog.show();

        button_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modailog.dismiss();
            }
        });
        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomValidator validate = new CustomValidator();
                String matchotp = etOtp.getText().toString();

                if (validate.isValidPincode(matchotp)) {
                    etOtp.setError(null);
                    if (matchotp.equals(otp))
                    {
                        etOtp.setError(null);
                        modailog.dismiss();
                        resetPassword();
                    }else {
                        etOtp.requestFocus();
                        etOtp.setError("OTP Not Match");
                    }

                }
                else {
                    etOtp.requestFocus();
                    etOtp.setError("OTP Must be 6 digit Length");
                }
            }
        });

    }

    private void resetPassword() {

        AlertDialog.Builder rpalertDailog = new AlertDialog.Builder(this);
        rpalertDailog.setTitle("Reset Password");
        rpalertDailog.setCancelable(false);
        View rpview = getLayoutInflater().inflate(R.layout.mat_reset_password_activity, null);
        final EditText etpass = (EditText) rpview.findViewById(R.id.rp_pass);
        final EditText etcnfpass = (EditText) rpview.findViewById(R.id.rp_cnfpass);
        Button button_rpsave = (Button) rpview.findViewById(R.id.rp_save);
        Button button_rpcancle = (Button) rpview.findViewById(R.id.rp_cancle);

        rpalertDailog.setView(rpview);
        final AlertDialog dailog = rpalertDailog.create();
        dailog.show();

        button_rpcancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dailog.dismiss();
            }
        });

        button_rpsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomValidator validate = new CustomValidator();
                pass = etpass.getText().toString();
                String cnfpass = etcnfpass.getText().toString();

                if (validate.isValidPassword(pass)) {
                    etpass.setError(null);
                    if (pass.equals(cnfpass))
                    {
                        etcnfpass.setError(null);
                        dailog.dismiss();
                        savePassword();
                    }
                    else {
                        etcnfpass.requestFocus();
                        etcnfpass.setError("Password Not Match");
                    }

                }
                else {
                    etpass.requestFocus();
                    etpass.setError("Password Must Be 6 Digit Long");

                }
            }
        });
    }

    private void savePassword() {
        ServiceMatrimony serviceMatrimony=ApiClient.getRetrofit().create(ServiceMatrimony.class);
        Call<ResetPasswordPojo> call=serviceMatrimony.resetPassword(userid,pass);
        call.enqueue(new Callback<ResetPasswordPojo>() {
            @Override
            public void onResponse(Call<ResetPasswordPojo> call, Response<ResetPasswordPojo> response) {
                ResetPasswordPojo passwordPojo=response.body();
                if (passwordPojo.getSuccess())
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginMatrimony.this);
                    builder.setTitle("Message");
                    builder.setMessage(passwordPojo.getMessage());
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }
            }

            @Override
            public void onFailure(Call<ResetPasswordPojo> call, Throwable t) {
                Toast.makeText(LoginMatrimony.this,"Please Check Internet Connection", Toast.LENGTH_LONG).show();

            }
        });


    }
*/
}
