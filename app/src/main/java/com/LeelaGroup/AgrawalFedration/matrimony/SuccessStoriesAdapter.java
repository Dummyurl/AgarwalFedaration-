package com.LeelaGroup.AgrawalFedration.matrimony;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.LeelaGroup.AgrawalFedration.R;
import com.LeelaGroup.AgrawalFedration.matrimony.models.SussessStoriesPojo;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by USer on 26-07-2017.
 */

public class SuccessStoriesAdapter extends RecyclerView.Adapter<SuccessStoriesAdapter.MyViewHolder> {



    ArrayList<SussessStoriesPojo> storiesPojoArrayList;
    Context context;

    public SuccessStoriesAdapter(ArrayList<SussessStoriesPojo> storiesPojoArrayList, Context context) {
        this.storiesPojoArrayList = storiesPojoArrayList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.success_stories_row_layout,parent,false);
        return new MyViewHolder(view,context,storiesPojoArrayList);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        SussessStoriesPojo sussessStoriesPojo=storiesPojoArrayList.get(position);
        Glide.with(context).load(sussessStoriesPojo.getMatSucWedPic()).into(holder.wedPic);
        holder.groomName.setText(sussessStoriesPojo.getMatSucGroomName());
        holder.brideName.setText(sussessStoriesPojo.getMatSucBrideName());
        holder.date.setText(sussessStoriesPojo.getMatSucWedDate());
        holder.help.setText(sussessStoriesPojo.getMatSucAfHelp());

    }

    @Override
    public int getItemCount() {
        return storiesPojoArrayList.size();
    }

    public static final class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView brideName,groomName,date,help,viewMore;
        ImageView wedPic;
        ArrayList<SussessStoriesPojo> storiesPojos;
        Context ctx;


        public MyViewHolder(View itemView, final Context ctx, final ArrayList<SussessStoriesPojo> storiesPojos) {
            super(itemView);
            this.storiesPojos=storiesPojos;
            this.ctx=ctx;

            groomName=(TextView)itemView.findViewById(R.id.tv_goroomname);
            brideName=(TextView)itemView.findViewById(R.id.bridename);
            date=(TextView)itemView.findViewById(R.id.tv_mrrigdate);
            help=(TextView)itemView.findViewById(R.id.helpid);
            viewMore=(TextView)itemView.findViewById(R.id.tv_viewmore_succsstories);
            wedPic=(ImageView)itemView.findViewById(R.id.iv_succsstory);

            viewMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    SussessStoriesPojo sussessStoriesPojo=storiesPojos.get(position);
                    Intent intent=new Intent(ctx,SuccessStoriesFullViewActivity.class);
                    intent.putExtra("pic_groom",sussessStoriesPojo.getMatSucGroomPic());
                    intent.putExtra("pic_bride",sussessStoriesPojo.getMatSucBridePic());
                    intent.putExtra("name_groom",sussessStoriesPojo.getMatSucGroomName());
                    intent.putExtra("name_bride",sussessStoriesPojo.getMatSucBrideName());
                    intent.putExtra("mr_date",sussessStoriesPojo.getMatSucWedDate());
                    intent.putExtra("mr_loc",sussessStoriesPojo.getMatSucLocation());
                    intent.putExtra("wed_pic",sussessStoriesPojo.getMatSucWedPic());
                    intent.putExtra("help",sussessStoriesPojo.getMatSucAfHelp());
                    ctx.startActivity(intent);
                }
            });

        }
    }
}
