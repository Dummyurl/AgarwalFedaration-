package com.LeelaGroup.AgrawalFedration.business;

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

import com.LeelaGroup.AgrawalFedration.ForgotPasswordAll;
import com.LeelaGroup.AgrawalFedration.ForgotPasswordPojo;
import com.LeelaGroup.AgrawalFedration.Business_Pojo.BusinessLoginResponsePOJO;
import com.LeelaGroup.AgrawalFedration.Home;
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
                Intent intent=new Intent(Login_Business.this, ForgotPasswordAll.class);
                startActivity(intent);
                finish();
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
                    //Toast.makeText(Login_Business.this, pojo.getMessage(), Toast.LENGTH_SHORT).show();
                    String business_reg_id = pojo.getUserId();
                    String business_reg_email = pojo.getUserEmail();
                    String fname=pojo.getUserFname();
                    //String islogin="true";
                    business_Medical_session.createUserLoginSession(business_reg_id,business_reg_email,fname);

                    if (business_Medical_session.isIdStore())
                    {
                        Intent intent = new Intent(getApplicationContext(), Home.class);
                        //Intent intent = new Intent(getApplicationContext(), MainActivityModules.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }


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
  /*  public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }*/
//    public void startSecond(View v){startActivity(new Intent(Login_Medical.this,Education.class));}
}
