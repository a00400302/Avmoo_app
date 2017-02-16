package pw.Avmo.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pw.Avmo.R;
import pw.Avmo.bean.YulantuBean;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_yulan,parent,false);
        return  new viewholder(view);
    }

    @Override
    public void onBindViewHolder(viewholder holder, int position) {
        holder.imageView.setImageBitmap(list.get(position).getXiaoyulantu());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewholder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public viewholder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.yulan);
        }
    }
}
