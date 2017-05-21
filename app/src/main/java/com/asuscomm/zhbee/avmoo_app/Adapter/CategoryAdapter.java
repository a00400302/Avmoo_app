package com.asuscomm.zhbee.avmoo_app.Adapter;

/**
 * Created by bee on 2017/5/14 0014.
 */
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.asuscomm.zhbee.avmoo_app.Activity.CategoryActivity;
import com.asuscomm.zhbee.avmoo_app.Bean.CategoryBean;
import com.asuscomm.zhbee.avmoo_app.R;

import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<CategoryBean> list;

    public CategoryAdapter(List<CategoryBean> list) {
        this.list = list;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_category_item, parent, false);
        final CategoryViewHolder holder = new CategoryViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CategoryActivity.class);
                intent.putExtra("ALLURL",list.get(holder.getAdapterPosition()).getUrl());
                intent.putExtra("NAME",list.get(holder.getAdapterPosition()).getName());
                view.getContext().startActivity(intent);
            }
        });
        return holder;
    }



    @Override
    public void onBindViewHolder(final CategoryViewHolder holder, int position) {
        holder.textView.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        CategoryViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.category_item);
        }
    }


}

