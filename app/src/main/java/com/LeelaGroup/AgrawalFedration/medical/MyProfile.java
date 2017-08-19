package com.LeelaGroup.AgrawalFedration.medical;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.LeelaGroup.AgrawalFedration.R;
import com.LeelaGroup.AgrawalFedration.matrimony.validation.CustomValidator;

public class MyProfile extends AppCompatActivity implements View.OnClickListener {

    static final int SELECTED_PICTURE = 1;
    static final int SELECTED_PICTURE_PROFILE = 5;
    private ImageView cover, profile,EditProfile;
    Button save;
    EditText Email,Name,Contact,Address,Country,State,City;
    RadioButton Male,Female;
    RadioGroup Gender;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        cover = (ImageView) findViewById(R.id.Cover);
        profile = (ImageView) findViewById(R.id.profile);
        EditProfile = (ImageView) findViewById(R.id.EditProfile);

        save = (Button) findViewById(R.id.btnSaveProfile);
       // save.setOnClickListener(this);
        cover.setOnClickListener(this);
        profile.setOnClickListener(this);

        Name=(EditText)findViewById(R.id.profile_Name);
        Email=(EditText)findViewById(R.id.profile_Email);
        Contact=(EditText)findViewById(R.id.profile_Contact);
        Address=(EditText)findViewById(R.id.profile_addr);
        Country=(EditText)findViewById(R.id.profile_country);
        State=(EditText)findViewById(R.id.profile_state);
        City=(EditText)findViewById(R.id.profile_city);

        Gender=(RadioGroup)findViewById(R.id.profile_radioGroup);

        Male=(RadioButton)findViewById(R.id.profile_male);
        Female=(RadioButton)findViewById(R.id.profile_female);

        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (validateFirst())
                {
                    //Toast.makeText(MyProfile.this, "Profile updated..", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MyProfile.this,MyProfile.class));
                }
            }
        });

    }



    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.Cover:
                Toast.makeText(MyProfile.this, "Cover", Toast.LENGTH_LONG).show();
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, SELECTED_PICTURE);
                break;
            case R.id.profile:
                Toast.makeText(MyProfile.this, "Profile", Toast.LENGTH_SHORT).show();
                Intent j = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(j, SELECTED_PICTURE_PROFILE);
                break;
            case R.id.btnSaveProfile:
                /*cover.setImageResource(R.drawable.ic_email_black_24dp);
                profile.setImageResource(R.drawable.cover);*/
                break;
        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case SELECTED_PICTURE:
                if (resultCode == RESULT_OK)
                {
                    Uri uri = data.getData();
                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();
                    Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
                    cover.setImageBitmap(yourSelectedImage);
                    cover.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
                break;
            case SELECTED_PICTURE_PROFILE:
                if (resultCode == RESULT_OK)
                {
                    Uri uri1 = data.getData();
                    String[] projection1 = {MediaStore.Images.Media.DATA};
                    Cursor cursor1 = getContentResolver().query(uri1, projection1, null, null, null);
                    cursor1.moveToFirst();
                    int columnIndex1 = cursor1.getColumnIndex(projection1[0]);
                    String filePath1 = cursor1.getString(columnIndex1);
                    cursor1.close();
                    Bitmap yourSelectedImage1 = BitmapFactory.decodeFile(filePath1);
                    profile.setImageBitmap(yourSelectedImage1);
                    profile.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
                break;
        }

    }


    public boolean validateFirst()
    {


        CustomValidator validator=new CustomValidator();

        final String name=Name.getText().toString();
        if(!validator.isValidName(name))
        {
            Name.requestFocus();
            Name.setError("Please enter valid Name");
            return false;
        }
        Name.setError(null);

        final String mob=Contact.getText().toString();
        if(!validator.isValidMobile(mob)){
            Contact.requestFocus();
            Contact.setError("Please Enter Valid Mobile Number");
            return false;
        }
        Contact.setError(null);
        /*final String landline=etLandline.getText().toString();
        if(!validator.isValidNumber(landline)){
            etLandline.requestFocus();
            etLandline.setError("Please Enter Valid Number");
            return false;
        }*/
        final String email=Email.getText().toString();
        if(!validator.isValidEmail(email)){
            Email.requestFocus();
            Email.setError("Please Enter Valid Email");
            return false;
        }
        Email.setError(null);

        final String addr=Address.getText().toString();
        if(!validator.isEmptyField(addr)){
            Address.requestFocus();
            Address.setError("Address Should Not Empty");
            return false;
        }
        Address.setError(null);

        final String country=Country.getText().toString();
        if(!validator.isValidName(country))
        {
            Country.requestFocus();
            Country.setError("Please Enter Valid Country Name");
            return false;
        }
        Country.setError(null);

        final String state=State.getText().toString();
        if(!validator.isValidName(state)){
            State.requestFocus();
            State.setError("Please Enter Valid State Name");
            return false;
        }
        State.setError(null);

        final String city=City.getText().toString();
        if(!validator.isValidName(city)){
            City.requestFocus();
            City.setError("Please Enter Valid City Name");
            return false;
        }
        City.setError(null);

       /* final String ressts=sprResdStats.getSelectedItem().toString();
        if(!validator.isEmptyField(ressts)){

            return false;
        }*/

        return true;
    }
}



