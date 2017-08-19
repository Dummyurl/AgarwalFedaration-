package com.LeelaGroup.AgrawalFedration.medical;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.LeelaGroup.AgrawalFedration.R;

public class Photo_Gallery extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo__gallery);


        GridView gridView = (GridView)findViewById(R.id.MatrimonyImageGallery);
        gridView.setAdapter(new PhotoGallary_ImageAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent (getApplicationContext(),PhotoGallery_FullImage.class);
                i.putExtra("id",position);
                startActivity(i);


            }
        });

    }
}
