package com.LeelaGroup.AgrawalFedration.matrimony;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.LeelaGroup.AgrawalFedration.MainActivityModules;
import com.LeelaGroup.AgrawalFedration.MatrimonySession;
import com.LeelaGroup.AgrawalFedration.Network.ApiClient;
import com.LeelaGroup.AgrawalFedration.R;
import com.LeelaGroup.AgrawalFedration.Service.ServiceMatrimony;
import com.LeelaGroup.AgrawalFedration.matrimony.models.CountryStateCity;
import com.LeelaGroup.AgrawalFedration.matrimony.models.GetRegName;

import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatrimonyActivity extends AppCompatActivity {
    Toolbar toolbar;
    //CircleImageView profilepic;
    //TextView regname;
    AutoCompleteTextView city;
    Spinner lookingFor, cast;
    String mat_id = "", mat_fname = "", mat_lname = "";

    List<CountryStateCity> listCities;
    String[] cityName;
    ArrayAdapter<String> dataAdapterCity;

    Button btnFindMatch;
    String f_lookingFor, f_city, f_cast;
    //String mat_id=getIntent().getStringExtra("mat_id");
    MatrimonySession matrimonySession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrimony);
        matrimonySession = new MatrimonySession(getApplicationContext());

        init();

        if (matrimonySession.checkLogin())
            finish();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Find Match");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        btnFindMatch = (Button) findViewById(R.id.bt_findmatch);
        //  Toast.makeText(this, mat_id, Toast.LENGTH_SHORT).show();

        Intent in = getIntent();
        Bundle b = in.getExtras();
        //  mat_id = b.getString("mat_id");
        //getprofImage();
        //getFirstName();
        getcity();
        //regname.setText(mat_fname+" "+mat_lname);


        HashMap<String, String> user = matrimonySession.getUserDetails();

        String name = user.get(MatrimonySession.KEY_NAME);
        mat_id = user.get(MatrimonySession.KEY_ID);

        //regname.setText(name);
    }

    public void getFirstName() {
        ServiceMatrimony serviceMatrimony = ApiClient.getRetrofit().create(ServiceMatrimony.class);
        Call<GetRegName> call = serviceMatrimony.getRegName(mat_id);
        call.enqueue(new Callback<GetRegName>() {
            @Override
            public void onResponse(Call<GetRegName> call, Response<GetRegName> response) {
                GetRegName getNme = response.body();
                // mat_fname=getNme.getMatFname();
                // mat_lname=getNme.getMatLname();
                //  regname.setText(mat_fname+" "+mat_lname);
            }

            @Override
            public void onFailure(Call<GetRegName> call, Throwable t) {

            }
        });
    }

    public void getcity() {
        ServiceMatrimony serviceMatrimony = ApiClient.getRetrofit().create(ServiceMatrimony.class);
        Call<List<CountryStateCity>> call = serviceMatrimony.getCity();
        call.enqueue(new Callback<List<CountryStateCity>>() {
            @Override
            public void onResponse(Call<List<CountryStateCity>> call, Response<List<CountryStateCity>> response) {
                listCities = response.body();
                try {
                    cityName = new String[listCities.size()];


                    for (int i = 0; i < listCities.size(); i++) {
                        cityName[i] = listCities.get(i).getCity();
                    }
                    dataAdapterCity = new ArrayAdapter<String>(MatrimonyActivity.this, android.R.layout.simple_list_item_1, cityName);
                    city.setAdapter(dataAdapterCity);
                }
                catch (NullPointerException e) {

                }
            }

            @Override
            public void onFailure(Call<List<CountryStateCity>> call, Throwable t) {

                Toast.makeText(MatrimonyActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

/*
    public  void getFilterRecords()
    {
        ServiceMatrimony serviceMatrimony =ApiClient.getRetrofit().create(ServiceMatrimony.class);

    }*/
/*
    private void getprofImage() {
        ServiceMatrimony serviceMatrimony= ApiClient.getRetrofit().create(ServiceMatrimony.class);
        Call<LoginModel> call=serviceMatrimony.getProfilePic(mat_id);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                LoginModel loginModel=response.body();

                Glide.with(MatrimonyActivity.this).load(loginModel.getMregProfPic()).into(profilepic);
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {

            }
        });

    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int res_id = item.getItemId();
        if (res_id == R.id.action_aboutus) {

            Intent intent = new Intent(this, AboutUsActivity.class);
            intent.putExtra("mat_id", mat_id);
            startActivity(intent);
           /* Intent intent=new Intent(this,AboutUsActivity.class);
            startActivity(intent);*/
        }
       /*else if (res_id==R.id.action_profsearch){
            startActivity(new Intent(this,ProfileSearchActivity.class));
        }*/
       /* else if (res_id==R.id.action_editprofile){
            startActivity(new Intent(this,PersonEditDetailsActivity.class));
        }*/
        else if (res_id == R.id.action_myprofile) {

          /*  Intent intent=new Intent(this,MyProfileActivity.class);
            intent.putExtra("mat_id",mat_id);
            startActivity(intent);*/
            Intent intent = new Intent(this, MyCreatedProfiles.class);
            intent.putExtra("mat_id", mat_id);
            startActivity(intent);
        } else if (res_id == R.id.action_fillprofile) {
            Intent intent = new Intent(this, FormBasicDetailsActivity.class);
            intent.putExtra("mat_id", mat_id);
            startActivity(intent);

        } else if (res_id == R.id.action_succsstories) {

            startActivity(new Intent(this, SuccessStoriesActivity.class));
        } else if (res_id == R.id.action_events) {
            startActivity(new Intent(this, EventsActivity.class));
        } else if (res_id == R.id.action_contactus) {
            startActivity(new Intent(this, ContactUsActivity.class));
        } else if (res_id == R.id.action_logout) {
            matrimonySession.logoutUser();
            finish();
        } else if (res_id == android.R.id.home) {
            onBackPressed();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    /* boolean doubleBackToExitPressedOnce = false;

     @Override
     public void onBackPressed() {
         if (doubleBackToExitPressedOnce) {
             super.onBackPressed();
             return;
         }

         this.doubleBackToExitPressedOnce = true;
         Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

         new Handler().postDelayed(new Runnable() {

             @Override
             public void run() {
                 doubleBackToExitPressedOnce=false;
             }
         }, 2000);
     }
 */
    public void goToFindMatchActivity(View v) {
      /*startActivity(new Intent(this,FindMatchActivity.class));*/
        f_lookingFor = lookingFor.getSelectedItem().toString().trim();
        f_cast = cast.getSelectedItem().toString().trim();
        f_city = city.getText().toString().trim();
        Intent intent = new Intent(MatrimonyActivity.this, FindMatchActivity.class);
        intent.putExtra("lk_for", f_lookingFor);
        intent.putExtra("f_cast", f_cast);
        intent.putExtra("f_city", f_city);
        startActivity(intent);

    }

    public void goToFormBasicDetailsActivity(View v) {

        Intent intent = new Intent(getApplicationContext(), FormBasicDetailsActivity.class);

        intent.putExtra("mat_id", mat_id);
        //Toast.makeText(LoginMatrimony.this, loginModel.getMatId().toString(), Toast.LENGTH_SHORT).show();
        startActivity(intent);
        MatrimonyActivity.this.finish();
        //startActivity(new Intent(this,FormBasicDetailsActivity.class));
    }

    public void init() {
        city = (AutoCompleteTextView) findViewById(R.id.spr_locatedin);
        lookingFor = (Spinner) findViewById(R.id.spr_lookingfor);
        cast = (Spinner) findViewById(R.id.spr_cast);
//        profilepic=(CircleImageView)findViewById(R.id.profilepiuc);
//        regname=(TextView)findViewById(R.id.fname);
    }


}
