package com.LeelaGroup.AgrawalFedration.medical;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.LeelaGroup.AgrawalFedration.Medical_Pojos.Medical;
import com.LeelaGroup.AgrawalFedration.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by hp on 08-Jul-17.
 */

public class TopSearchAdapter extends RecyclerView.Adapter<TopSearchAdapter.MyViewHilderTop> {

    private List<Medical> list;
    private Context context;

    public TopSearchAdapter(List<Medical> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHilderTop onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_for_top_search,parent,false);
        return new MyViewHilderTop(view);
    }

    @Override
    public void onBindViewHolder(MyViewHilderTop holder, int position) {

        Glide.with(context).load(list.get(position).getMed_logo()).into(holder.imageView);
        holder.Service_name.setText(list.get(position).getBusinessName1());
        holder.Service_contact.setText(list.get(position).getBusinessContact1());
        holder.Service_Address.setText(list.get(position).getBusinessAddress1());

    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public static  final class MyViewHilderTop extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView Service_name,Service_contact,Service_Address;

        public MyViewHilderTop(View itemView) {
            super(itemView);

            imageView=(ImageView) itemView.findViewById(R.id.imageView);
            Service_name=(TextView) itemView.findViewById(R.id.Service_name);
            Service_contact=(TextView) itemView.findViewById(R.id.Service_contact);
            Service_Address=(TextView) itemView.findViewById(R.id.Service_Address);
        }
    }
}
