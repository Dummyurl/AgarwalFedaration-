package com.LeelaGroup.AgrawalFedration;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.LeelaGroup.AgrawalFedration.Network.ApiClient;
import com.LeelaGroup.AgrawalFedration.Service.Medical.Business_ServiceAPI;
import com.LeelaGroup.AgrawalFedration.Service.Medical.ServiceAPIEducation;
import com.LeelaGroup.AgrawalFedration.business.Login_Business;

import com.LeelaGroup.AgrawalFedration.matrimony.LoginMatrimony;
import com.LeelaGroup.AgrawalFedration.matrimony.validation.CustomValidator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by USer on 11-08-2017.
 */

public class ResetPasswordAll extends AppCompatActivity {
    Button savePss;
    EditText pass;
    EditText cnfPass;
    Toolbar toolbar;
    String  userid = "", epass="" ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mat_reset_password_activity);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        pass=(EditText)findViewById(R.id.rp_pass);
        cnfPass=(EditText)findViewById(R.id.rp_cnfpass);

        savePss=(Button)findViewById(R.id.rp_save) ;

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Forgot Password");

        savePss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFirst())
                {
                    cnfPass.setError(null);
                    if (pass.getText().toString().equals(cnfPass.getText().toString()))
                    {
                        savePassword();

                    }else
                    {
                        cnfPass.requestFocus();
                        cnfPass.setError("Password Not Matched");
                    }
                }

            }
        });

    }

    public boolean validateFirst() {
        pass.setError(null);
        CustomValidator validator = new CustomValidator();
        String epass = pass.getText().toString();
        if (!validator.isValidPassword(epass)) {
            pass.requestFocus();
            pass.setError("Password Must Be At Least 6 Digit Long");
            return false;
        }
        pass.setError(null);

        return true;
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

    public void getId()
    {
        userid=getIntent().getStringExtra("userid");
        epass=pass.getText().toString();
    }


    private void savePassword() {
        getId();
        Business_ServiceAPI business_serviceAPI= ApiClient.getRetrofit().create(Business_ServiceAPI.class);
        Call<ResetPasswordPojo> call=business_serviceAPI.resetPassword(userid,epass);
        call.enqueue(new Callback<ResetPasswordPojo>() {
            @Override
            public void onResponse(Call<ResetPasswordPojo> call, Response<ResetPasswordPojo> response) {
                ResetPasswordPojo passwordPojo=response.body();
               if (passwordPojo.getSuccess())
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ResetPasswordAll.this);
                    builder.setTitle("Message");
                    builder.setMessage(passwordPojo.getMessage());
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            Intent intent =new Intent(ResetPasswordAll.this,Login_Business.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    builder.show();
                }


            }

            @Override
            public void onFailure(Call<ResetPasswordPojo> call, Throwable t) {
                Toast.makeText(ResetPasswordAll.this,"Please Check Internet Connection", Toast.LENGTH_LONG).show();

            }
        });


    }


}
