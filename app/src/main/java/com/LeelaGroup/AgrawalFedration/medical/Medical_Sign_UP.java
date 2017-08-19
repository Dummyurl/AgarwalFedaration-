package com.LeelaGroup.AgrawalFedration.medical;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.LeelaGroup.AgrawalFedration.Medical_Pojos.Medical_Registration;
import com.LeelaGroup.AgrawalFedration.Network.ApiClient;
import com.LeelaGroup.AgrawalFedration.R;
import com.LeelaGroup.AgrawalFedration.Service.Medical.MedicalServiceAPI;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Medical_Sign_UP extends AppCompatActivity implements View.OnClickListener {

    EditText name;
    EditText email;
    EditText mobile;
    EditText password;
    Button register, go_to_login;
    String user_name, user_email, user_mobile, user_password;
    Medical_Registration registration;
    private ProgressDialog pDialog;

    TextInputLayout layout_name, layout_email, layout_mobile, layout_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical__sign__up);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

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

        name.addTextChangedListener(new MyTextWatcher(name));
        email.addTextChangedListener(new MyTextWatcher(email));
        password.addTextChangedListener(new MyTextWatcher(password));


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (submitForm()) {
                    MedicalRegistration();
                }

            }
        });

       /* go_to_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Medical_Sign_UP.this, Login_Medical.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });*/
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
            String name1=name.getText().toString().trim();
        if (name1.isEmpty() && name1==null ) {
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

    private void MedicalRegistration() {


        showpDialog();

        user_name = name.getText().toString();
        user_email = email.getText().toString();
        user_mobile = mobile.getText().toString();
        user_password = password.getText().toString();

        registration = new Medical_Registration();

        registration.setUser_fname(user_name);
        registration.setUser_email(user_email);
        registration.setUser_phone(user_mobile);
        registration.setUser_pwd(user_password);

        MedicalServiceAPI service = ApiClient.getRetrofit().create(MedicalServiceAPI.class);

        Call<Medical_Registration> mr = service.medicalRegistion(registration.getUser_fname(), registration.getUser_email()
                , registration.getUser_phone(), registration.getUser_pwd());

        mr.enqueue(new Callback<Medical_Registration>() {
            @Override
            public void onResponse(Call<Medical_Registration> call, Response<Medical_Registration> response) {
                registration = response.body();
                hidepDialog();
                if (registration.getSuccess()) {
                    Toast.makeText(Medical_Sign_UP.this, registration.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Medical_Sign_UP.this, Login_Medical.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                Toast.makeText(Medical_Sign_UP.this, registration.getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Medical_Registration> call, Throwable t) {
                Toast.makeText(Medical_Sign_UP.this, "please check connection", Toast.LENGTH_SHORT).show();
                hidepDialog();
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
