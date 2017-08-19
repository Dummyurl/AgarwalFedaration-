package com.LeelaGroup.AgrawalFedration.Social_Refurn;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.LeelaGroup.AgrawalFedration.R;

import java.util.ArrayList;
import java.util.List;

import in.goodiebag.carouselpicker.CarouselPicker;

public class Social_Refurn extends AppCompatActivity {

    CarouselPicker imageCarousel;
    TextView tvSelected,socialText,leela_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social__refurn);
        imageCarousel = (CarouselPicker) findViewById(R.id.imageCarousel);
     //   tvSelected = (TextView)findViewById(R.id.tvSelectedItem);
        socialText = (TextView)findViewById(R.id.social_heading);
        leela_info = (TextView)findViewById(R.id.leela_info);
        List<CarouselPicker.PickerItem> imageItems = new ArrayList<>();
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.logo_leela));
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.rajtilak_logo));
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.interlink_logo));
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.lawyer_logo1));
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.tours_logo));
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.m2_logo));
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.psac_logo));
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.job_logo));
        CarouselPicker.CarouselViewAdapter imageAdapter = new CarouselPicker.CarouselViewAdapter(this, imageItems, 0);
        imageCarousel.setAdapter(imageAdapter);



       /* CarouselPicker.CarouselViewAdapter mixAdapter = new CarouselPicker.CarouselViewAdapter(this, mixItems, 0);
        mixCarousel.setAdapter(mixAdapter);*/

        imageCarousel.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               // tvSelected.setText("Selected item in image carousel is  : "+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
