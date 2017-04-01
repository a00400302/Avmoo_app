package pw.Avmo.Adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pw.Avmo.Activity.CategoryActivity;
import pw.Avmo.Activity.InnerActivity;
import pw.Avmo.R;
import pw.Avmo.Bean.CategoryBean;

/**
 * Created by Administrator on 2017/3/29 0029.
 */

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
