package com.LeelaGroup.AgrawalFedration;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.LeelaGroup.AgrawalFedration.Business_Pojo.ProfileImageFetchPojo;
import com.LeelaGroup.AgrawalFedration.Business_Pojo.ProfileImagePojo;
import com.LeelaGroup.AgrawalFedration.Network.ApiClient;
import com.LeelaGroup.AgrawalFedration.Service.Medical.Business_ServiceAPI;
import com.LeelaGroup.AgrawalFedration.notification.UserNotification;
import com.bumptech.glide.Glide;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String token;
    Business_Medical_Session business_medical_session;
    CircleImageView profilePic;
    TextView username,useremail;
    public static final int PROFILE_PICTURE = 1;

    String profilePath,uid,uname,uemail;
    File imageFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        business_medical_session = new Business_Medical_Session(getApplicationContext());

        if (business_medical_session.checkLogin())
            finish();


        token= FirebaseInstanceId.getInstance().getToken();
       Toast.makeText(Home.this, token, Toast.LENGTH_SHORT).show();
        storeId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
       /* profilePic=(CircleImageView)findViewById(R.id.profilePic);
        username=(TextView)*/
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

       NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View view = navigationView.inflateHeaderView(R.layout.nav_header_main_drawer);
        profilePic = (CircleImageView) view.findViewById(R.id.profilePic);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                // Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "selectProfile"), PROFILE_PICTURE);
            }
        });

        username = (TextView) view.findViewById(R.id.user_name);
        useremail = (TextView) view.findViewById(R.id.user_email);

       // NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //add this line to display menu1 when the activity is loaded
        displaySelectedScreen(R.id.nav_home);

        HashMap<String, String> user = business_medical_session.getUserDetails();

        //String name = user.get(Business_Medical_Session.KEY_NAME);
        uid = user.get(Business_Medical_Session.KEY_ID);
        uemail = user.get(Business_Medical_Session.KEY_Email);
        uname = user.get(Business_Medical_Session.KEY_Name);

        username.setText(uname);
        useremail.setText(uemail);
        fetchProfileImage();
    }

    private void storeId() {
        if(business_medical_session.isIdStore()){
            HashMap<String, String> user = business_medical_session.getUserDetails();
            String id=user.get(Business_Medical_Session.KEY_ID);

             Business_ServiceAPI business_serviceAPI = ApiClient.getRetrofit().create(Business_ServiceAPI.class);
                Call<NotificationPojo> notificationPojoCall = business_serviceAPI.updateToken(id,token);
                notificationPojoCall.enqueue(new Callback<NotificationPojo>() {
                    @Override
                    public void onResponse(Call<NotificationPojo> call, Response<NotificationPojo> response) {
                        NotificationPojo notificationPojo=response.body();
                       Toast.makeText(Home.this, notificationPojo.getResponse(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<NotificationPojo> call, Throwable t) {

                    }
                });

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_home:
                HomePageModule homePageModule = new HomePageModule();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.relative_layout_for_fragment, homePageModule, homePageModule.getTag()).commit();
                //setTitle("Loan Inquiry");
                break;

            case R.id.nav_profile:
                HomeProfile homeProfile = new HomeProfile();
                FragmentManager manager1 = getSupportFragmentManager();
                manager1.beginTransaction().replace(R.id.relative_layout_for_fragment, homeProfile, homeProfile.getTag()).commit();
                break;
            case R.id.nav_notif:

               /* UserNotification userNotification = new UserNotification();
                FragmentManager manager3 = getSupportFragmentManager();
                manager3.beginTransaction().replace(R.id.relative_layout_for_fragment, userNotification, userNotification.getTag()).commit();*/
                Intent intent=new Intent(Home.this,UserNotification.class);
                startActivity(intent);
                break;
            case R.id.nav_about:

                AboutUs aboutUs = new AboutUs();
                FragmentManager managerAboutUs = getSupportFragmentManager();
                managerAboutUs.beginTransaction().replace(R.id.relative_layout_for_fragment, aboutUs).commit();
                break;

            case R.id.nav_logout:
                business_medical_session.logoutUser();
                finish();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //calling the method displayselectedscreen and passing the id of selected menu
        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PROFILE_PICTURE && resultCode == RESULT_OK && null != data)
        {
            super.onActivityResult(requestCode, resultCode, data);

            Uri uri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor;
            if(Build.VERSION.SDK_INT >19)
            {

                // Will return "image:x*"
                String wholeID = DocumentsContract.getDocumentId(uri);
                // Split at colon, use second item in the array
                String id = wholeID.split(":")[1];
                // where id is equal to
                String sel = MediaStore.Images.Media._ID + "=?";

                cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        filePathColumn, sel, new String[]{ id }, null);
            }
            else
            {
                cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
            }
            profilePath = null;
            try
            {
                int column_index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                profilePath = cursor.getString(column_index);
                File aimageFile=new File(profilePath);
                imageFile=new Compressor(this).compressToFile(aimageFile);
                //String imageName = imageFile.getName();
                cursor.close();
                //Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                Glide.with(this).load(uri).into(profilePic);
                setProfileImage();
            }
            catch(NullPointerException e) {

            } /*catch (FileNotFoundException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           }*/ catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setProfileImage()
    {

        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), imageFile);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", imageFile.getName(), requestBody);
        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), uid);
        Business_ServiceAPI service = ApiClient.getRetrofit().create(Business_ServiceAPI.class);
        Call<ProfileImagePojo> profilePicture = service.setProfileImage(fileToUpload,id);


        profilePicture.enqueue(new Callback<ProfileImagePojo>() {
            @Override
            public void onResponse(Call<ProfileImagePojo> call, Response<ProfileImagePojo> response) {
                ProfileImagePojo pictureProfile = response.body();
                String imagePath=pictureProfile.getImageName();
                Glide.with(Home.this).load(imagePath).into(profilePic);
            }

            @Override
            public void onFailure(Call<ProfileImagePojo> call, Throwable t) {

            }
        });
    }
    public void fetchProfileImage()
    {

        Business_ServiceAPI serviceAPI=ApiClient.getRetrofit().create(Business_ServiceAPI.class);
        Call<ProfileImageFetchPojo> call=serviceAPI.getProfilePic(uid);
        call.enqueue(new Callback<ProfileImageFetchPojo>() {
            @Override
            public void onResponse(Call<ProfileImageFetchPojo> call, Response<ProfileImageFetchPojo> response) {
                ProfileImageFetchPojo picture = response.body();
                String img = picture.getPicture();
                Glide.with(Home.this).load(img).into(profilePic);
            }

            @Override
            public void onFailure(Call<ProfileImageFetchPojo> call, Throwable t) {

                Toast.makeText(Home.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
