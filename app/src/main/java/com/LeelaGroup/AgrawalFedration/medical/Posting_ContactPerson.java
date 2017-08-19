package com.LeelaGroup.AgrawalFedration.medical;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.LeelaGroup.AgrawalFedration.Medical_Pojos.Medical;
import com.LeelaGroup.AgrawalFedration.Medical_Session;
import com.LeelaGroup.AgrawalFedration.Network.ApiClient;
import com.LeelaGroup.AgrawalFedration.R;
import com.LeelaGroup.AgrawalFedration.Service.Medical.MedicalServiceAPI;
import com.LeelaGroup.AgrawalFedration.matrimony.validation.CustomValidator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Posting_ContactPerson extends AppCompatActivity {

    TextInputLayout personName,personNumber,personDesig,personMail;
    EditText contactPersonName,contactPersonNumber,contactPersonDesig,contactPersonMail;
    Button Save ;
    String contact_Person_Name,contact_Person_Number,contact_Person_Desig,contact_Person_Mail,ContactDetail;
    RadioGroup radioGroup;
    RadioButton selectedContact;
    Medical medical;
    ProgressDialog progressDialog;
    Medical_Session medical__session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting__contact_person);

        medical__session =new Medical_Session(getApplicationContext());
        if(medical__session.checkLogin())
            finish();

        personName = (TextInputLayout) findViewById(R.id.layout_personName);
        personNumber = (TextInputLayout) findViewById(R.id.layout_personNumber);
        personDesig = (TextInputLayout) findViewById(R.id.layout_personDesig);
        personMail = (TextInputLayout) findViewById(R.id.layout_personMail);


        contactPersonName = (EditText) findViewById(R.id.contactperson_name);
        contactPersonNumber = (EditText) findViewById(R.id.contactperson_number);
        contactPersonDesig = (EditText) findViewById(R.id.contactperson_designation);
        contactPersonMail = (EditText) findViewById(R.id.contactperson_email);
       // Save = (Button) findViewById(R.id.saveContactPerson);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroupDetail);

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Inserting");

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (validateFirst()) {
                    ContactPerson();
                    Toast.makeText(Posting_ContactPerson.this, "Successfully submited", Toast.LENGTH_LONG).show();

                }
            }
        });
    }



    private boolean validateFirst() {

        CustomValidator validator = new CustomValidator();

        final String panme = contactPersonName.getText().toString();
        if (!validator.isValidName(panme)) {
            contactPersonName.requestFocus();
            contactPersonName.setError("Please Enter Valid Name");
            return false;
        }
        contactPersonName.setError(null);

        final String Mobile = contactPersonNumber.getText().toString();
        if (!validator.isValidMobile(Mobile)) {
            contactPersonNumber.requestFocus();
            contactPersonNumber.setError("Please Enter Valid Mobile no.");
            return false;
        }
        contactPersonNumber.setError(null);

        final String Designation = contactPersonDesig.getText().toString();
        if (!validator.isEmptyField(Designation)) {
            contactPersonDesig.requestFocus();
            contactPersonDesig.setError("Please Enter Designation");
            return false;
        }
        contactPersonDesig.setError(null);

        final String Email = contactPersonMail.getText().toString();
        if (!validator.isValidEmail(Email)) {
            contactPersonMail.requestFocus();
            contactPersonMail.setError("Please Enter Valid Email ID");
            return false;
        }
        contactPersonMail.setError(null);

        return true;
    }

    private void ContactPerson() {
        progressDialog.show();

        int selectedContactId = radioGroup.getCheckedRadioButtonId();
        selectedContact = (RadioButton) findViewById(selectedContactId);

        contact_Person_Name = contactPersonName.getText().toString();
        contact_Person_Number = contactPersonNumber.getText().toString();
        contact_Person_Desig= contactPersonDesig.getText().toString();
        contact_Person_Mail= contactPersonMail.getText().toString();
        ContactDetail = selectedContact.getText().toString();

        MedicalServiceAPI getResponse = ApiClient.getRetrofit().create(MedicalServiceAPI.class);
        Call<Medical> call = getResponse.ContactPerson(contact_Person_Name,contact_Person_Number,contact_Person_Mail,contact_Person_Desig,ContactDetail);

        call.enqueue(new Callback<Medical>() {
            @Override
            public void onResponse(Call<Medical> call, Response<Medical> response) {
                medical = response.body();

                if (medical != null) {
//                    if (medical.getSuccess()) {
//                        Toast.makeText(getApplicationContext(), medical.getMessage(), Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getApplicationContext(), medical.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
                    Toast.makeText(getApplicationContext(), "inserted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Posting_ContactPerson.this,Medical_Module.class));
                } else {
                    assert medical != null;
                    Log.v("Response", medical.toString());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Medical> call, Throwable t) {

            }
        });
    }

}

