package com.LeelaGroup.AgrawalFedration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.WindowManager;

import com.LeelaGroup.AgrawalFedration.business.Login_Business;
import com.LeelaGroup.AgrawalFedration.medical.Login_Medical;
import com.LeelaGroup.AgrawalFedration.medical.Medical_Module;
import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

import static com.LeelaGroup.AgrawalFedration.Business_Medical_Session.PREFER_NAME;

public class Splash extends AwesomeSplash {


    // Shared Preferences reference
    SharedPreferences sharedPreferences;

    // Editor reference for Shared preferences
    SharedPreferences.Editor editor;
    Context _context;
    private static final String PREFER_NAME = "AndroidExamplePref_Medical";
    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_ID = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    Business_Medical_Session business_medical_session;

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }*/
    @Override
    public void initSplash(ConfigSplash configSplash)
    {
//        sharedPreferences = getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
//        editor = sharedPreferences.edit();

       /* ActionBar actionBar = getSupportActionBar();
        actionBar.hide();*/


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            //Background Animation
        configSplash.setBackgroundColor(R.color.colorPrimaryDark);
        configSplash.setAnimCircularRevealDuration(3000);
        configSplash.setRevealFlagX(Flags.REVEAL_LEFT);
        configSplash.setRevealFlagX(Flags.REVEAL_BOTTOM);


        //LOGO
        configSplash.setLogoSplash(R.drawable.lastlogo);
        configSplash.setAnimLogoSplashDuration(5000);
        configSplash.setAnimLogoSplashTechnique(Techniques.Bounce);


        //Title
        configSplash.setTitleSplash(getString(R.string.splash_title));
        configSplash.setTitleTextColor(R.color.backgroundSplash);
        configSplash.setTitleTextSize(30f);
        //configSplash.setTitleFont("sans-serif-condensed");
        configSplash.setAnimTitleDuration(3000);
        configSplash.setAnimTitleTechnique(Techniques.FlipInX);

        business_medical_session=new Business_Medical_Session(getApplicationContext());

        goTomain();


      /*  if (business_medical_session.checkLogin())
        {
            Intent intent=new Intent(getApplicationContext(),MainActivityModules.class);
            startActivity(intent);


        }else {
            Intent intent=new Intent(getApplicationContext(),Login_Business.class);
            startActivity(intent);
            finish();
        }*/

    }

    @Override
    public void animationsFinished() {
       // startActivity(new Intent(Splash.this,Login_Business.class));

    }

    public void goTomain() {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {


                if (business_medical_session.checkLogin()) {

                    Intent loginIntent = new Intent(_context, MainActivityModules.class);
                    startActivity(loginIntent);


                } else {

                    Intent loginIntent = new Intent(Splash.this, Login_Business.class);
                    startActivity(loginIntent);

                }

            }
        }, 2000);
    }

   /* public void goTomain() {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {


                if (sharedPreferences.contains(KEY_ID)) {

                    Intent loginIntent = new Intent(_context, MainActivityModules.class);
                    startActivity(loginIntent);
                    finish();

                } else {

                    Intent loginIntent = new Intent(Splash.this, Login_Business.class);
                    startActivity(loginIntent);
                    finish();
                }

            }
        }, 2000);
    }
*/



}
