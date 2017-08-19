package com.LeelaGroup.AgrawalFedration.education;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.LeelaGroup.AgrawalFedration.medical.Login_Medical;
import com.LeelaGroup.AgrawalFedration.R;


public class ViewFlipperEducation extends AppCompatActivity {

    Button button3,button6;


    private ViewFlipper simpleViewFlipper;
    int[] images = {R.drawable.education, R.drawable.image_1, R.drawable.image_2, R.drawable.image_3,R.drawable.image_4,R.drawable.image_5};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flipper_education);



        simpleViewFlipper = (ViewFlipper) findViewById(R.id.flipperEducation); // get the reference of ViewFlipper

        // loop for creating ImageView's
        for (int i = 0; i < images.length; i++) {
            // create the object of ImageView
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(images[i]); // set image in ImageView
            simpleViewFlipper.addView(imageView); // add the created ImageView in ViewFlipper
        }
        // Declare in and out animations and load them using AnimationUtils class
        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        // set the animation type's to ViewFlipper
        simpleViewFlipper.setInAnimation(in);
        simpleViewFlipper.setOutAnimation(out);
        // set interval time for flipping between views
        simpleViewFlipper.setFlipInterval(3000);
        // set auto start for flipping between views
        simpleViewFlipper.setAutoStart(true);

        button3 = (Button)findViewById(R.id.button3);
        button6 = (Button)findViewById(R.id.button6);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewFlipperEducation.this,Login_Medical.class));
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewFlipperEducation.this,Signup.class));
            }
        });
    }

//    public void login(View v){startActivity(new Intent(ViewFlipperEducation.this,Login_Medical.class));}
//
//    public void signup(View v){startActivity(new Intent(ViewFlipperEducation.this,Signup.class));}
}
