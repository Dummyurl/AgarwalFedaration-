package com.LeelaGroup.AgrawalFedration.medical;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.LeelaGroup.AgrawalFedration.R;

/**
 * Created by Adwait on 11/05/2017.
 */


public class SecondFragment extends Fragment {

    GridView simpleGrid;
    int logos[] = { R.drawable.hospital,R.drawable.hospital1,R.drawable.hospital2,R.drawable.hospital3,
            R.drawable.hospital4};
//            R.drawable.hospital2, R.drawable.hospital3, R.drawable.hospital4,
//            R.drawable.hospital5, R.drawable.hospital6, R.drawable.hospital7, R.drawable.hospital8};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        simpleGrid = (GridView) view.findViewById(R.id.simpleGridView); // init GridView
        // Create an object of CustomAdapter and set Adapter to GirdView
        CustomAdapterTabGrid customAdapter = new CustomAdapterTabGrid(getActivity().getApplicationContext(), logos);
        simpleGrid.setAdapter(customAdapter);
        // implement setOnItemClickListener event on GridView
        simpleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // set an Intent to Another Activity
                Intent intent = new Intent(getActivity(), SearchResult_Medical.class);
                intent.putExtra("image", logos[position]); // put image data in Intent
                startActivity(intent); // start Intent
            }
        });

        return view;
    }
}

