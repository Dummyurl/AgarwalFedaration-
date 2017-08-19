package com.LeelaGroup.AgrawalFedration.medical;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.LeelaGroup.AgrawalFedration.R;

/**
 * Created by Adwait on 09/06/2017.
 */

public class PhotoGallary_ImageAdapter extends BaseAdapter {


    private Context context;

    public Integer[] images={
            R.drawable.images1,R.drawable.images2,R.drawable.images3,R.drawable.images4,R.drawable.images5,
            R.drawable.images6,R.drawable.images7,R.drawable.images8
    };

    public PhotoGallary_ImageAdapter(Context c){
        context = c;
    }
    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return images[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(images[position]);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(new GridView.LayoutParams(350,370));

        return imageView;
    }
}
