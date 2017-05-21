package com.asuscomm.zhbee.avmoo_app.Adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.asuscomm.zhbee.avmoo_app.Activity.PreviewActivity;
import com.asuscomm.zhbee.avmoo_app.Bean.YulantuBean;
import com.asuscomm.zhbee.avmoo_app.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * Created by bee on 2017/5/14 0014.
 */
public class InnerXiujyunLantuAdapter extends  RecyclerView.Adapter<InnerXiujyunLantuAdapter.InnerYulanViewHolder> {
    private ArrayList<YulantuBean> list;

    public InnerXiujyunLantuAdapter(ArrayList<YulantuBean> list) {
        this.list = list;
    }

    @Override
    public InnerYulanViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_yulan_item,parent,false);
        final InnerYulanViewHolder innerYulanViewHolder = new InnerYulanViewHolder(view);
        innerYulanViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent.getContext(), PreviewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("DAURL",list);
                intent.putExtras(bundle);
                intent.putExtra("LOCATION",innerYulanViewHolder.getAdapterPosition());
                view.getContext().startActivity(intent);
                Log.d("InnerXiujyunLantuAdapte", "innerYulanViewHolder.getAdapterPosition():" + innerYulanViewHolder.getAdapterPosition());
            }
        });

        return  innerYulanViewHolder;
    }

    @Override
    public void onBindViewHolder(InnerYulanViewHolder holder, int position) {
        holder.imageView.setImageURI(list.get(position).getXiaoyulantu());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class InnerYulanViewHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView imageView;
        InnerYulanViewHolder(View itemView) {
            super(itemView);
            imageView = (SimpleDraweeView) itemView.findViewById(R.id.yulan_view);
        }
    }




}
