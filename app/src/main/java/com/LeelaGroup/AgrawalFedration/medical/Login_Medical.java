package com.LeelaGroup.AgrawalFedration.medical;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.LeelaGroup.AgrawalFedration.Business_Medical_Session;
import com.LeelaGroup.AgrawalFedration.ForgotPasswordPojo;
import com.LeelaGroup.AgrawalFedration.Medical_Pojos.Medical_Registration;
import com.LeelaGroup.AgrawalFedration.Medical_Session;
import com.LeelaGroup.AgrawalFedration.Network.ApiClient;
import com.LeelaGroup.AgrawalFedration.R;
import com.LeelaGroup.AgrawalFedration.ResetPasswordPojo;
import com.LeelaGroup.AgrawalFedration.Service.Medical.MedicalServiceAPI;
import com.LeelaGroup.AgrawalFedration.Service.ServiceMatrimony;
import com.LeelaGroup.AgrawalFedration.matrimony.LoginMatrimony;
import com.LeelaGroup.AgrawalFedration.matrimony.validation.CustomValidator;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Login_Medical extends AppCompatActivity {

  /*  EditText email, password;
    TextInputLayout layout_email, layout_password;
    Button login;
    TextView signUP,forgotPass;
    private ProgressDialog pDialog;
    Medical_Registration mr = new Medical_Registration();
    Intent intent;
    Business_Medical_Session medical__session;
    String getEmail="",userid="",otp="",pass="";

    AlertDialog alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        medical__session = new Business_Medical_Session(getApplicationContext());

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        login = (Button) findViewById(R.id.login);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        signUP = (TextView) findViewById(R.id.newuser);

        layout_email = (TextInputLayout) findViewById(R.id.layout_email);
        layout_password = (TextInputLayout) findViewById(R.id.layout_password);
        forgotPass=(TextView)findViewById(R.id.forgot);
        email.addTextChangedListener(new MyTextWatcher(email));
        password.addTextChangedListener(new MyTextWatcher(password));


        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (submitForm()) {
                    login();
                }


            }
        });

        signUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Medical.this, Medical_Sign_UP.class));
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPass();
            }
        });

    }

    private void login() {

        showpDialog();
        MedicalServiceAPI service = ApiClient.getRetrofit().create(MedicalServiceAPI.class);

        String getEmail = email.getText().toString();
        String getPass = password.getText().toString();

        mr.setUser_email(getEmail);
        mr.setUser_pwd(getPass);

        Call<Medical_Registration> call = service.getLogin(mr.getUser_email(), mr.getUser_pwd());
        call.enqueue(new Callback<Medical_Registration>() {
            @Override
            public void onResponse(Call<Medical_Registration> call, Response<Medical_Registration> response) {
                hidepDialog();
                Medical_Registration res = response.body();
                if (res.getSuccess()) {
                    Toast.makeText(Login_Medical.this, res.getMessage(), Toast.LENGTH_SHORT).show();
                    String responseUserId = res.getUser_id();
                    String responseUserEmail = res.getUser_email();
                    medical__session.createUserLoginSession(responseUserId, responseUserEmail);
                    intent = new Intent(getApplicationContext(), Medical_Module.class);
                    *//*intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*//*
                    startActivity(intent);
                    finish();
                } else {

                    //Toast.makeText(Login_Medical.this, res.getMessage(), Toast.LENGTH_SHORT).show();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Login_Medical.this);

                    builder.setMessage(res.getMessage())
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    alert.dismiss();
                                }
                            });
                    alert = builder.create();
                    alert.show();

                }
            }

            @Override
            public void onFailure(Call<Medical_Registration> call, Throwable t) {

                Toast.makeText(Login_Medical.this, t.getMessage(), Toast.LENGTH_LONG).show();
                hidepDialog();
            }
        });

    }


    private boolean submitForm() {
        if (!validateEmail()) {
            return false;
        }

        if (!validatePassword()) {
            return false;
        }
        return true;
    }

    private boolean validatePassword() {

        if (password.getText().toString().trim().isEmpty()) {
            layout_password.setError(getString(R.string.err_msg_password));

            return false;
        } else {

            layout_password.setErrorEnabled(false);
        }
        return true;

    }

    private boolean validateEmail() {
        String email1 = email.getText().toString().trim();

        if (email1.isEmpty() || !isValidEmail(email1)) {

            layout_email.setError(getString(R.string.err_msg_email));
            requestFocus(email);

            return false;
        } else {

            layout_email.setErrorEnabled(false);
        }

        return true;
    }

    private boolean isValidEmail(String email1) {

        return !TextUtils.isEmpty(email1) && android.util.Patterns.EMAIL_ADDRESS.matcher(email1).matches();
    }


    private void requestFocus(View view) {

        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;


        public MyTextWatcher(View view) {

            this.view = view;

        }

        @Override
        public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence s, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

            switch (view.getId()) {

                case R.id.email:
                    validateEmail();
                    break;

                case R.id.password:
                    validatePassword();
                    break;
            }


        }
    }


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }



    // code for Forgot PAssword
    private void forgotPass() {
        AlertDialog.Builder fpalertDailog = new AlertDialog.Builder(this);
        fpalertDailog.setTitle("Forgot Password");
        fpalertDailog.setCancelable(false);
        View fpview = getLayoutInflater().inflate(R.layout.forgot_password, null);
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
        MedicalServiceAPI medicalServiceAPI=ApiClient.getRetrofit().create(MedicalServiceAPI.class);
        Call<ForgotPasswordPojo> call=medicalServiceAPI.getOtp(getEmail);
        call.enqueue(new Callback<ForgotPasswordPojo>() {
            @Override
            public void onResponse(Call<ForgotPasswordPojo> call, Response<ForgotPasswordPojo> response) {
                ForgotPasswordPojo forgotPasswordPojo =response.body();
                if (forgotPasswordPojo.getSuccess())
                {
                    userid= forgotPasswordPojo.getUserid();
                    otp= forgotPasswordPojo.getOTP();

                    matchOtp();

                }else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Login_Medical.this);
                    builder.setTitle("Message");
                    builder.setMessage(forgotPasswordPojo.getMessage());
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                    //Toast.makeText(Login_Medical.this, forgotPasswordPojo.getMessage(), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordPojo> call, Throwable t) {
                Toast.makeText(Login_Medical.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void matchOtp() {

        AlertDialog.Builder moalertDailog = new AlertDialog.Builder(this);
        moalertDailog.setTitle("Send OTP");
        moalertDailog.setCancelable(false);
        View moview = getLayoutInflater().inflate(R.layout.send_otp_layout, null);
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
        View rpview = getLayoutInflater().inflate(R.layout.reset_password_layout, null);
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
        MedicalServiceAPI medicalServiceAPI=ApiClient.getRetrofit().create(MedicalServiceAPI.class);
        Call<ResetPasswordPojo> call=medicalServiceAPI.resetPassword(userid,pass);
        call.enqueue(new Callback<ResetPasswordPojo>() {
            @Override
            public void onResponse(Call<ResetPasswordPojo> call, Response<ResetPasswordPojo> response) {
                ResetPasswordPojo passwordPojo=response.body();
                if (passwordPojo.getSuccess())
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Login_Medical.this);
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
                Toast.makeText(Login_Medical.this,"Please Check Internet Connection", Toast.LENGTH_LONG).show();

            }
        });


    }

*/
}
