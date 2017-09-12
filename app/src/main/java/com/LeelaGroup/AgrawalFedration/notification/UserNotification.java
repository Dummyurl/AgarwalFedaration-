package com.LeelaGroup.AgrawalFedration.notification;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.LeelaGroup.AgrawalFedration.Business_Pojo.My_Add_Card_Pojo;
import com.LeelaGroup.AgrawalFedration.R;
import com.LeelaGroup.AgrawalFedration.business.Business_My_Add;
import com.LeelaGroup.AgrawalFedration.business.Business_My_Add_Adapter;

import java.util.ArrayList;

/**
 * Created by USer on 09-09-2017.
 */

public class UserNotification extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    NotificationAdapter adapter;
    ArrayList<NotificationPojo> arrayList;



    public UserNotification() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.notification_activity, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NotificationAdapter(arrayList, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        return view;
    }

}
