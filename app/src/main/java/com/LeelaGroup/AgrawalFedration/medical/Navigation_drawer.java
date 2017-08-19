package com.LeelaGroup.AgrawalFedration.medical;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.LeelaGroup.AgrawalFedration.ModuleCustomAdapter;
import com.LeelaGroup.AgrawalFedration.R;

import java.util.ArrayList;
import java.util.Arrays;

public class Navigation_drawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    ArrayList personImages = new ArrayList<>(Arrays.asList(R.drawable.medical, R.drawable.matri,R.drawable.business,R.drawable.education,R.drawable.social));
   // ArrayList ItemNames = new ArrayList<>(Arrays.asList("Medical","Matrimony","Business","Education","Social Refurn"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        /*******************************************module**************************************************/


            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            // set a GridLayoutManager with default vertical orientation and 2 number of columns
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
            recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
            //  call the constructor of ModuleCustomAdapter to send the reference and data to Adapter
            ModuleCustomAdapter moduleCustomAdapter = new ModuleCustomAdapter(Navigation_drawer.this ,personImages);
            recyclerView.setAdapter(moduleCustomAdapter); // set the Adapter to RecyclerView

        


        /*********************************************************************************************/



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        finish();

        if (id == R.id.nav_home) {
            // Handle the camera action

        } else if (id == R.id.nav_profile) {

            Intent intent = new Intent(Navigation_drawer.this,MyProfile.class);
            startActivity(intent);


        } else if (id == R.id.nav_events) {

            Intent intent = new Intent(Navigation_drawer.this,Event.class);
            startActivity(intent);

        } else if (id == R.id.nav_gallery) {

            Intent intent = new Intent(Navigation_drawer.this,Photo_Gallery.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

            String message = "Agrawal Federation\n\nhttps://goo.gl/y471T6";
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(Intent.createChooser(share, "Share Via"));

        } else if (id == R.id.nav_aboutApp) {

        }/*else if (id == R.id.nav_logout) {
                finish();
        }*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
