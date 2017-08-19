package com.LeelaGroup.AgrawalFedration.business;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.LeelaGroup.AgrawalFedration.Business_Pojo.BusinessCardPojo;
import com.LeelaGroup.AgrawalFedration.Business_Pojo.BusinessGetSet;
import com.LeelaGroup.AgrawalFedration.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USer on 15-07-2017.
 */

public class BusinessModuleClickAdapter extends RecyclerView.Adapter<BusinessModuleClickAdapter.ModuleViewHodler> {


    private List<BusinessCardPojo> arrayList;
    private Context context;

    public BusinessModuleClickAdapter(List<BusinessCardPojo> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public ModuleViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.business_card_row, parent, false);
        return new ModuleViewHodler(layoutView);
    }

    @Override
    public void onBindViewHolder(ModuleViewHodler holder, int position) {

        holder.tv_card_add_name.setText(arrayList.get(position).getName());
        holder.tv_card_contact_no.setText(arrayList.get(position).getMobile());
        holder.tv_card_email.setText(arrayList.get(position).getEmail());
        holder.tv_card_location.setText(arrayList.get(position).getCityName());
        Glide.with(context).load(arrayList.get(position).getLogo()).into(holder.img_card);

    }

    @Override
    public int getItemCount()
    {
        return arrayList.size();
    }

    public static final class ModuleViewHodler extends RecyclerView.ViewHolder {
        TextView tv_card_add_name, tv_card_contact_no, tv_card_email, tv_card_location;
        ImageView img_card;

        public ModuleViewHodler(View itemView) {
            super(itemView);

            img_card = (ImageView) itemView.findViewById(R.id.img_card);
            tv_card_add_name = (TextView) itemView.findViewById(R.id.tv_card_add_name);
            tv_card_contact_no = (TextView) itemView.findViewById(R.id.tv_card_contact_no);
            tv_card_email = (TextView) itemView.findViewById(R.id.tv_card_email);
            tv_card_location = (TextView) itemView.findViewById(R.id.tv_card_location);

        }
    }

    public void setFilter(List<BusinessCardPojo> newList){
        arrayList=new ArrayList<>();
        arrayList.addAll(newList);
        notifyDataSetChanged();
    }
}
