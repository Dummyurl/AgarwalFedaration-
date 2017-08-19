package com.LeelaGroup.AgrawalFedration.Social_Refurn;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.LeelaGroup.AgrawalFedration.Business_Medical_Session;
import com.LeelaGroup.AgrawalFedration.MainActivityModules;
import com.LeelaGroup.AgrawalFedration.R;
import com.LeelaGroup.AgrawalFedration.business.Login_Business;
import com.LeelaGroup.AgrawalFedration.medical.SplashScreenActivity;

/**
 * Created by Admin on 8/8/2017.
 */

public class SplashScreen extends Activity {
    private static final String TAG = SplashScreenActivity.class.getSimpleName();
    private ImageView backgroundImage;
    public static final String MyPREFERENCES = "MyPrefs" ;
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    public static final String KEY_ID = "name";

    public static final String KEY_EMAIL = "email";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splashscreen);
        goTomain();


    }






    private void goTomain() {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {


                Business_Medical_Session business_medical_session=new Business_Medical_Session(getApplicationContext());

                if (!business_medical_session.checkLogin()) {

                    Intent loginIntent = new Intent(SplashScreen.this, MainActivityModules.class);
                    startActivity(loginIntent);
                    finish();

                } else {

                    Intent loginIntent = new Intent(SplashScreen.this, Login_Business.class);
                    startActivity(loginIntent);
                    finish();
                }

            }
        }, 2000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e(TAG, "onActivityResult");


    }
}
