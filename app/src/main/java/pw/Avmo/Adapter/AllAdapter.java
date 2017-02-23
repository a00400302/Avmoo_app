package pw.Avmo.Adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pw.Avmo.InnerActivity;
import pw.Avmo.R;
import pw.Avmo.bean.Allbean;

/**
 * Created by bee on 2016/9/21.
 */
public class AllAdapter extends RecyclerView.Adapter<AllAdapter.AllViewHolder> {
    Handler handler;
    private List<Allbean> list;

    public AllAdapter(List<Allbean> list) {
        this.list = list;
    }

    @Override
    public AllViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.alllistviewitem,viewGroup,false);
        final AllViewHolder holder = new AllViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), InnerActivity.class);
                intent.putExtra("ALLURL",list.get(holder.getAdapterPosition()).getUrl());
                v.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(AllViewHolder viewHolder, int position) {
        viewHolder.fanhao.setText(list.get(position).getFanhao());
        viewHolder.time.setText(list.get(position).getTime());
        viewHolder.title.setText( list.get(position).getTitle());
        //        viewHolder.img.setImageBitmap(list.get(position).getImgurl());
        handler = new handler(viewHolder);
        Thread thread = new thread(viewHolder,position);
        thread.start();

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
        View view;


        AllViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            img = (ImageView) itemView.findViewById(R.id.thumbnail);
            title = (TextView) itemView.findViewById(R.id.TTT);
            fanhao = (TextView) itemView.findViewById(R.id.fanhao);
            time = (TextView) itemView.findViewById(R.id.TTTTime);
        }


    }
    private class handler extends Handler{
        AllViewHolder holder;

        handler(AllViewHolder holder) {
            this.holder = holder;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            holder.img.setImageBitmap((Bitmap) msg.obj);
        }
    }

    private class thread extends Thread{
        AllViewHolder holder;
        int position;

        thread(AllViewHolder holder, int position) {
            this.holder = holder;
            this.position = position;
        }

        @Override
        public void run() {
            super.run();
            URL url = null;
            try {
                url = new URL(list.get(position).getImgurl());
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                InputStream is = connection.getInputStream();
                Message message = new Message();
                message.obj = BitmapFactory.decodeStream(is);
                handler.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}