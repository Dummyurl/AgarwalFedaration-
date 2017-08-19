package com.LeelaGroup.AgrawalFedration.matrimony;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.LeelaGroup.AgrawalFedration.MatrimonySession;
import com.LeelaGroup.AgrawalFedration.Network.ApiClient;
import com.LeelaGroup.AgrawalFedration.R;
import com.LeelaGroup.AgrawalFedration.Service.ServiceMatrimony;
import com.LeelaGroup.AgrawalFedration.matrimony.models.PhysicalAndOtherDetails;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtherActivity extends AppCompatActivity {
    String mat_id;
    ImageView profilepic;
    TextView pcretedby,hobiies,intrest;
MatrimonySession matrimonySession;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        matrimonySession =new MatrimonySession(getApplicationContext());

        init();

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Other Details");

        if(matrimonySession.checkLogin())
            finish();

        mat_id=getIntent().getStringExtra("mat_id");
        getOtherDetails();
    }

    private void getOtherDetails() {
        ServiceMatrimony serviceMatrimony= ApiClient.getRetrofit().create(ServiceMatrimony.class);
        Call<PhysicalAndOtherDetails> otherCall=serviceMatrimony.getPhysAndOtherDetls(mat_id);
        otherCall.enqueue(new Callback<PhysicalAndOtherDetails>() {
            @Override
            public void onResponse(Call<PhysicalAndOtherDetails> call, Response<PhysicalAndOtherDetails> response) {
                PhysicalAndOtherDetails otherDetl=response.body();

                pcretedby.setText(otherDetl.getMreg_prof_create_for());
                hobiies.setText(otherDetl.getMreg_hobby());
                intrest.setText(otherDetl.getMreg_interest());
            }

            @Override
            public void onFailure(Call<PhysicalAndOtherDetails> call, Throwable t) {

                Toast.makeText(OtherActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void init()
    {
        //profilepic=(ImageView)findViewById(R.id.d_person_other_image);
        pcretedby=(TextView)findViewById(R.id.d_per_profilecreatedby);
        hobiies=(TextView)findViewById(R.id.d_per_hobies);
        intrest=(TextView)findViewById(R.id.d_per_intrests);

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

}
