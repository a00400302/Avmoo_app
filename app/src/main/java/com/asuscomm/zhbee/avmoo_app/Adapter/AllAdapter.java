package com.asuscomm.zhbee.avmoo_app.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.asuscomm.zhbee.avmoo_app.Activity.InnerActivity;
import com.asuscomm.zhbee.avmoo_app.Bean.AllBean;
import com.asuscomm.zhbee.avmoo_app.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;

import java.util.ArrayList;


/**
 * Created by bee on 2016/9/21.
 */
public class AllAdapter extends RecyclerView.Adapter<AllAdapter.AllViewHolder> {
    //    private  Bitmap bitmap;
    private ArrayList<AllBean> list;

    public AllAdapter(ArrayList<AllBean> list) {
        this.list = list;
    }




    @Override
    public AllViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycleview_all_item, viewGroup, false);
        final AllViewHolder holder = new AllViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), InnerActivity.class);
                intent.putExtra("ALLURL", list.get(holder.getAdapterPosition()).getUrl());
                intent.putExtra("FANHAO", list.get(holder.getAdapterPosition()).getFanhao());
                view.getContext().startActivity(intent);
            }
        });
        return holder;

    }

    @Override
    public void onBindViewHolder(AllViewHolder viewHolder, int position) {
        viewHolder.fanhao.setText(list.get(position).getFanhao());
        viewHolder.title.setText(list.get(position).getTitle());
        viewHolder.img.setImageURI(list.get(position).getImgurl());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    class AllViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView img;
        TextView title;
        TextView fanhao;
        View view;


        AllViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            img = (SimpleDraweeView) itemView.findViewById(R.id.thumbnail);
            title = (TextView) itemView.findViewById(R.id.TTT);
            fanhao = (TextView) itemView.findViewById(R.id.fanhao);
        }
    }

}