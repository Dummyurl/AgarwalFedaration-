package com.LeelaGroup.AgrawalFedration;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by USer on 09-09-2017.
 */

public class HomeProfile extends Fragment {
    TextView name,Email,Mobile;
    TextView t1,t2,t3;
   // TrypuneSession trypuneSession;




    public HomeProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.my_profile_home, container, false);


        Typeface font = Typeface.createFromAsset(getActivity().getAssets(),"font/fontawesome-webfont.ttf" );
        name = (TextView)view.findViewById( R.id.nameIcon);
        name.setTypeface(font);
        Email = (TextView)view.findViewById( R.id.emailIcon);
        Email.setTypeface(font);
        Mobile = (TextView)view.findViewById( R.id.mobileIcon);
        Mobile.setTypeface(font);
        t1=(TextView)view.findViewById(R.id.useremail);
        t2=(TextView)view.findViewById(R.id.usermobile);
        t3=(TextView)view.findViewById(R.id.username);

/*
        trypuneSession = new TrypuneSession(getContext());


        HashMap<String, String> user = trypuneSession.getUserDetails();
        userid = user.get(trypuneSession.KEY_ID);


        getData();*/

        return view;
    }

 /*   public void getData()
    {

        ServiceAPI service = ApiClient.getRetrofit().create(ServiceAPI.class);

        Call<ProfileDataFetchPojo> reg = service.setProfile(userid);

        reg.enqueue(new Callback<ProfileDataFetchPojo>() {
            @Override
            public void onResponse(Call<ProfileDataFetchPojo> call, Response<ProfileDataFetchPojo> response) {

                ProfileDataFetchPojo pdf = response.body();

                if (pdf.getSuccess()==true) {
                    t3.setText(pdf.getUserregName());
                    t1.setText(pdf.getUserregEmailId());
                    t2.setText(pdf.getUserregContactNo());
                }else {
                    Toast.makeText(getContext(), "Data Not Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileDataFetchPojo> call, Throwable t) {
                Toast.makeText(getContext(), "Failure", Toast.LENGTH_SHORT).show();

            }
        });


    }*/
 @Override
 public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
     super.onViewCreated(view, savedInstanceState);
     //you can set the title for your toolbar here for different fragments different titles
     getActivity().setTitle("Profile");
 }
}
