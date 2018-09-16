package com.bwie.week3.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bwie.week3.R;
import com.bwie.week3.bean.MainBean;

import java.util.List;

/**
 * Created by _ヽ陌路离殇ゞ on 2018/9/15.
 */

public class SubAdapter extends RecyclerView.Adapter<SubAdapter.SubViewHodel>{

    List<MainBean.Pois> list;
    Context context;
    public SubAdapter(List<MainBean.Pois> pois, Context context) {
        this.list=pois;
        this.context=context;
    }

    @NonNull
    @Override
    public SubViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        SubViewHodel subViewHodel = new SubViewHodel(inflate);
        return subViewHodel;
    }

    @Override
    public void onBindViewHolder(@NonNull SubViewHodel holder, int position) {
        holder.te_name.setText(list.get(position).getName());
        holder.phone.setText(list.get(position).getDistance());
        List<MainBean.Pois.Photo> photos = list.get(position).getPhotos();
        if(photos!=null&&photos.size()>0){
            Glide.with(context).load(photos.get(0).getUrl()).into(holder.icon_i);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SubViewHodel extends RecyclerView.ViewHolder {

        private  TextView te_name;
        private  TextView phone;
        private  ImageView icon_i;

        public SubViewHodel(View itemView) {
            super(itemView);
            te_name = itemView.findViewById(R.id.te_name);
            phone = itemView.findViewById(R.id.phone);
            icon_i = itemView.findViewById(R.id.icon_i);
        }
    }
}
