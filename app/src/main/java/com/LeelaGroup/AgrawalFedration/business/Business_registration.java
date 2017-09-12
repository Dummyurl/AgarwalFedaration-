package com.LeelaGroup.AgrawalFedration.business;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.LeelaGroup.AgrawalFedration.Business_Pojo.Business_Registration_Pojo;
import com.LeelaGroup.AgrawalFedration.Network.ApiClient;
import com.LeelaGroup.AgrawalFedration.R;
import com.LeelaGroup.AgrawalFedration.Service.Medical.Business_ServiceAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Business_registration extends AppCompatActivity implements View.OnClickListener {
    EditText name;
    EditText email;
    EditText mobile;
    EditText password;
    Button register, go_to_login;
    String user_name, user_email, user_mobile, user_password;
    private ProgressDialog pDialog;

    TextInputLayout layout_name, layout_email, layout_mobile, layout_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_registration);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Register");
        layout_name = (TextInputLayout) findViewById(R.id.layout_name);
        layout_email = (TextInputLayout) findViewById(R.id.layout_email);
        layout_mobile = (TextInputLayout) findViewById(R.id.layout_uesrname);
        layout_password = (TextInputLayout) findViewById(R.id.layout_password);

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        mobile = (EditText) findViewById(R.id.uesrname);
        password = (EditText) findViewById(R.id.password);

        register = (Button) findViewById(R.id.signup);
        go_to_login = (Button) findViewById(R.id.btn_signup);

        name.addTextChangedListener(new Business_registration.MyTextWatcher(name));
        email.addTextChangedListener(new Business_registration.MyTextWatcher(email));
        password.addTextChangedListener(new Business_registration.MyTextWatcher(password));


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (submitForm()) {
                    businessRegistration();
                }

            }
        });

        /*go_to_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Business_registration.this, Login_Business.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });*/
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                onBackPressed();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        submitForm();
    }

    private boolean submitForm() {
        if (!validateName()) {
            return false;
        }

        if (!validateEmail()) {
            return false;
        }


        if (!validatePassword()) {
            return false;
        }

        return true;

    }

    private boolean validateName() {

        if (name.getText().toString().trim().isEmpty()) {
            name.setError(getString(R.string.err_msg_name));

            requestFocus(name);

            return false;
        } else {
            layout_name.setErrorEnabled(false);
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

    private boolean validatePassword() {

        if (password.getText().toString().trim().isEmpty()) {
            password.setError(getString(R.string.err_msg_password));

            requestFocus(password);

            return false;
        } else {
            layout_password.setErrorEnabled(false);
        }
        return true;

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

                case R.id.name:
                    validateName();
                    break;

                case R.id.email:
                    validateEmail();
                    break;

                case R.id.password:
                    validatePassword();
                    break;
            }

        }
    }

    private void businessRegistration() {


        showpDialog();

        user_name = name.getText().toString();
        user_email = email.getText().toString();
        user_mobile = mobile.getText().toString();
        user_password = password.getText().toString();

        Business_ServiceAPI serviceAPI = ApiClient.getRetrofit().create(Business_ServiceAPI.class);
        Call<Business_Registration_Pojo> call = serviceAPI.business_reg(user_name, user_email, user_mobile, user_password);
        call.enqueue(new Callback<Business_Registration_Pojo>() {

            @Override
            public void onResponse(Call<Business_Registration_Pojo> call, Response<Business_Registration_Pojo> response) {
                hidepDialog();
                Business_Registration_Pojo pojo = response.body();
                if (pojo.getSuccess()) {
                    Toast.makeText(Business_registration.this, pojo.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Business_registration.this, Login_Business.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else{

                    Toast.makeText(Business_registration.this, pojo.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Business_Registration_Pojo> call, Throwable t) {
                hidepDialog();
                Toast.makeText(Business_registration.this, "Please check coonection", Toast.LENGTH_SHORT).show();

            }
        });


    }


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
