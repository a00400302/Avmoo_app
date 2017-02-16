package pw.Avmo.Adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pw.Avmo.R;
import pw.Avmo.bean.Allbean;

/**
 * Created by bee on 2016/9/21.
 */
public class AllAdapter extends RecyclerView.Adapter<AllAdapter.AllViewHolder> {
    private List<Allbean> list;

    public AllAdapter(List<Allbean> list) {
        this.list = list;
    }

    @Override
    public AllViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.alllistviewitem,viewGroup,false);

        return new AllViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AllViewHolder viewHolder, int position) {
        viewHolder.fanhao.setText(list.get(position).getFanhao());
        viewHolder.img.setImageBitmap(list.get(position).getImgurl());
        viewHolder.time.setText(list.get(position).getTime());
        viewHolder.title.setText( list.get(position).getTitle());

    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    class AllViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title;
        TextView fanhao;
        TextView time;


        AllViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.thumbnail);
            title = (TextView) itemView.findViewById(R.id.TTT);
            fanhao = (TextView) itemView.findViewById(R.id.fanhao);
            time = (TextView) itemView.findViewById(R.id.TTTTime);
        }


    }
}