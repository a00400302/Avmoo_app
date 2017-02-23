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
import pw.Avmo.bean.Teacherbean;

/**
 * Created by Administrator on 2017/2/15.
 */

public class InnerTecherAdapter extends RecyclerView.Adapter<InnerTecherAdapter.viewhodler> {
    private Handler handler;
    private List<Teacherbean> list;

    public InnerTecherAdapter(List<Teacherbean> list) {
        this.list = list;
    }

    class viewhodler extends RecyclerView.ViewHolder{
        ImageView TeacherIMG;
        TextView TeacherTTT;
        public viewhodler(View itemView) {
            super(itemView);
            TeacherIMG = (ImageView) itemView.findViewById(R.id.TeacherIMG);
            TeacherTTT = (TextView) itemView.findViewById(R.id.TeacherTTT);
        }
    }
    @Override
    public viewhodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_teacher_itme,parent,false);
        return new viewhodler(view);
    }

    @Override
    public void onBindViewHolder(viewhodler viewhodler, int position) {
        viewhodler.TeacherTTT.setText(list.get(position).getName());
        handler = new handler(viewhodler);
        Thread thread = new thread(viewhodler,position);
        thread.start();

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    private class handler extends Handler{
        viewhodler holder;

        handler(viewhodler holder) {
            this.holder = holder;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            holder.TeacherIMG.setImageBitmap((Bitmap) msg.obj);
        }
    }

    private class thread extends  Thread {
        viewhodler viewhodler;
        int position;

        thread(InnerTecherAdapter.viewhodler viewhodler, int position) {
            this.viewhodler = viewhodler;
            this.position = position;
        }

        @Override
        public void run() {
            super.run();
            try {
                URL url = new URL(list.get(position).getTeacherimg());
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                InputStream is = connection.getInputStream();
                Message message = new Message();
                message.obj = BitmapFactory.decodeStream(is) ;
                handler.sendMessage(message);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
