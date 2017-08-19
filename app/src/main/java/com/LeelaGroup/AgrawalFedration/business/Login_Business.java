package com.LeelaGroup.AgrawalFedration.business;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.LeelaGroup.AgrawalFedration.ForgotPasswordPojo;
import com.LeelaGroup.AgrawalFedration.Business_Pojo.BusinessLoginResponsePOJO;
import com.LeelaGroup.AgrawalFedration.MainActivityModules;
import com.LeelaGroup.AgrawalFedration.ResetPasswordPojo;
import com.LeelaGroup.AgrawalFedration.Business_Medical_Session;
import com.LeelaGroup.AgrawalFedration.Network.ApiClient;
import com.LeelaGroup.AgrawalFedration.R;
import com.LeelaGroup.AgrawalFedration.Service.Medical.Business_ServiceAPI;
import com.LeelaGroup.AgrawalFedration.matrimony.validation.CustomValidator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Login_Business extends AppCompatActivity {

    EditText email, password;
    TextInputLayout layout_email, layout_password;
    Button login;
    private ProgressDialog pDialog;
    Intent intent;
    Business_Medical_Session business_Medical_session;
    TextView signup, forgotPass;
    boolean isLogin;
    String getEmail = "";
    String userid = "", otp = "";
    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        business_Medical_session=new Business_Medical_Session(this);
        setContentView(R.layout.activity_login);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        login = (Button) findViewById(R.id.login);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        signup = (TextView) findViewById(R.id.newuser);
        forgotPass = (TextView) findViewById(R.id.forgot);

        layout_email = (TextInputLayout) findViewById(R.id.layout_email);
        layout_password = (TextInputLayout) findViewById(R.id.layout_password);

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

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Business.this, Business_registration.class));
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

        String getEmail = email.getText().toString();
        String getPass = password.getText().toString();

        Business_ServiceAPI serviceAPI = ApiClient.getRetrofit().create(Business_ServiceAPI.class);
        Call<BusinessLoginResponsePOJO> call = serviceAPI.LoginUser(getEmail, getPass);
        call.enqueue(new Callback<BusinessLoginResponsePOJO>() {
            @Override
            public void onResponse(Call<BusinessLoginResponsePOJO> call, Response<BusinessLoginResponsePOJO> response) {
                BusinessLoginResponsePOJO pojo = response.body();
                hidepDialog();
                if (pojo.getSuccess()) {
                    Toast.makeText(Login_Business.this, pojo.getMessage(), Toast.LENGTH_SHORT).show();
                    String business_reg_id = pojo.getUserId();
                    String business_reg_email = pojo.getUserEmail();
                    //String islogin="true";
                    business_Medical_session.createUserLoginSession(business_reg_id,business_reg_email);
                    Intent intent = new Intent(getApplicationContext(), MainActivityModules.class);
                    //Intent intent = new Intent(getApplicationContext(), MainActivityModules.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
                Toast.makeText(Login_Business.this, pojo.getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<BusinessLoginResponsePOJO> call, Throwable t) {
                hidepDialog();
                Toast.makeText(Login_Business.this, "Please Check Connection", Toast.LENGTH_SHORT).show();
            }
        });


    }

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

                } else {
                    email.requestFocus();
                    email.setError("Please Enter Valid Email");

                }

            }
        });

    }

    private void getOTP() {
        Business_ServiceAPI business_serviceAPI = ApiClient.getRetrofit().create(Business_ServiceAPI.class);
        Call<ForgotPasswordPojo> call = business_serviceAPI.getOtp(getEmail);
        call.enqueue(new Callback<ForgotPasswordPojo>() {
            @Override
            public void onResponse(Call<ForgotPasswordPojo> call, Response<ForgotPasswordPojo> response) {
                ForgotPasswordPojo forgotPasswordPojo = response.body();
                if (forgotPasswordPojo.getSuccess()) {
                    userid = forgotPasswordPojo.getUserid();
                    otp = forgotPasswordPojo.getOTP();
                    matchOtp();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Login_Business.this);
                    builder.setTitle("Message");
                    builder.setMessage(forgotPasswordPojo.getMessage());
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                    //Toast.makeText(Login_Business.this, forgotPasswordPojo.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordPojo> call, Throwable t) {
                Toast.makeText(Login_Business.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
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
                    if (matchotp.equals(otp)) {
                        etOtp.setError(null);
                        modailog.dismiss();
                        resetPassword();
                    } else {
                        etOtp.requestFocus();
                        etOtp.setError("OTP Not Match");
                    }

                } else {
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
                    if (pass.equals(cnfpass)) {
                        etcnfpass.setError(null);
                        dailog.dismiss();
                        savePassword();
                    } else {
                        etcnfpass.requestFocus();
                        etcnfpass.setError("Password Not Match");
                    }

                } else {
                    etpass.requestFocus();
                    etpass.setError("Password Must Be 6 Digit Long");

                }
            }
        });
    }

    private void savePassword() {
        Business_ServiceAPI business_serviceAPI = ApiClient.getRetrofit().create(Business_ServiceAPI.class);
        Call<ResetPasswordPojo> call = business_serviceAPI.resetPassword(userid, pass);
        call.enqueue(new Callback<ResetPasswordPojo>() {
            @Override
            public void onResponse(Call<ResetPasswordPojo> call, Response<ResetPasswordPojo> response) {
                ResetPasswordPojo passwordPojo = response.body();
                if (passwordPojo.getSuccess()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Login_Business.this);
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
                Toast.makeText(Login_Business.this, "Please Check Internet Connection", Toast.LENGTH_LONG).show();

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
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }
//    public void startSecond(View v){startActivity(new Intent(Login_Medical.this,Education.class));}
}
