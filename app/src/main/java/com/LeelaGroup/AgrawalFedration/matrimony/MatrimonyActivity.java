package com.LeelaGroup.AgrawalFedration.matrimony;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
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
import com.LeelaGroup.AgrawalFedration.matrimony.models.SussessStoriesPojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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

    AutoScrollViewPager viewPager;
    CustumSwipeAdapter adapter;
    ImageView btnPrev,btnNext;

    ArrayList<SussessStoriesPojo> arrayList;




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

        btnPrev = (ImageView) findViewById(R.id.btn_preev);
        btnNext = (ImageView) findViewById(R.id.btn_next);
        viewPager=(AutoScrollViewPager)findViewById(R.id.view_pager);
       /* viewPager.startAutoScroll();
        viewPager.setInterval(3000);
        viewPager.setCycle(true);
        viewPager.setStopScrollWhenTouch(true);
        adapter=new CustumSwipeAdapter(this);
        viewPager.setAdapter(adapter);*/
        /*After setting the adapter use the timer */


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

        getSuccessDetails();

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(getItem(-1), true); //getItem(-1) for previous
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(getItem(+1), true); //getItem(-1) for previous
            }
        });


    }




    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
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

    private void getSuccessDetails() {

        ServiceMatrimony serviceMatrimony= ApiClient.getRetrofit().create(ServiceMatrimony.class);
        Call<List<SussessStoriesPojo>> call=serviceMatrimony.getSuccessStories();
        call.enqueue(new Callback<List<SussessStoriesPojo>>() {
            @Override
            public void onResponse(Call<List<SussessStoriesPojo>> call, Response<List<SussessStoriesPojo>> response) {
                arrayList=(ArrayList<SussessStoriesPojo>) response.body();
                //adapter=new CustumSwipeAdapter(arrayList,MatrimonyActivity.this);
                viewPager.startAutoScroll();
                viewPager.setInterval(3000);
                viewPager.setCycle(true);
                viewPager.setStopScrollWhenTouch(true);
                adapter=new CustumSwipeAdapter(arrayList,MatrimonyActivity.this);
                viewPager.setAdapter(adapter);
                viewPager.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<SussessStoriesPojo>> call, Throwable t) {
                Toast.makeText(MatrimonyActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();            }
        });
    }

}
