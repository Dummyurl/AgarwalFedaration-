package com.LeelaGroup.AgrawalFedration.medical;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.LeelaGroup.AgrawalFedration.R;

public class PhotoGallery_FullImage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery__full_image);


        Intent i = getIntent();

        int position = i.getExtras().getInt("id");
        PhotoGallary_ImageAdapter adapter = new PhotoGallary_ImageAdapter(this);
        ImageView imageView = (ImageView)findViewById(R.id.image_id_Gallery);
        imageView.setImageResource(adapter.images[position]);



    }
}
