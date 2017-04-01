package pw.Avmo.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import pw.Avmo.R;
import pw.Avmo.Bean.YulantuBean;

/**
 * Created by Administrator on 2017/2/15.
 */

public class InnerXiujyunLantuAdapter extends  RecyclerView.Adapter<InnerXiujyunLantuAdapter.viewholder> {
    private List<YulantuBean> list;

    public InnerXiujyunLantuAdapter(List<YulantuBean> list) {
        this.list = list;
    }

    @Override
    public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_yulan_item,parent,false);
        return  new viewholder(view);
    }

    @Override
    public void onBindViewHolder(viewholder holder, int position) {
        holder.imageView.setImageURI(list.get(position).getXiaoyulantu());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewholder extends RecyclerView.ViewHolder{
        SimpleDraweeView imageView;
        viewholder(View itemView) {
            super(itemView);
            imageView = (SimpleDraweeView) itemView.findViewById(R.id.yulan);
        }
    }




}
