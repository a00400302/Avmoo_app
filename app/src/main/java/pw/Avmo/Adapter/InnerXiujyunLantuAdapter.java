package pw.Avmo.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import pw.Avmo.R;
import pw.Avmo.bean.YulantuBean;

/**
 * Created by Administrator on 2017/2/15.
 */

public class InnerXiujyunLantuAdapter extends  RecyclerView.Adapter<InnerXiujyunLantuAdapter.viewholder> {
    private List<YulantuBean> list;
    private List<Bitmap> bitmapList;

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
        holder.imageView.setImageBitmap(bitmapList.get(position));
    }


    @Override
    public int getItemCount() {
        return bitmapList.size();
    }

    class viewholder extends RecyclerView.ViewHolder{
        ImageView imageView;
        viewholder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.yulan);
        }
    }




}
