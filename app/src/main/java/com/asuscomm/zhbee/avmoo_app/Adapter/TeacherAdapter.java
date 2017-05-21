package com.asuscomm.zhbee.avmoo_app.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.asuscomm.zhbee.avmoo_app.Activity.TeacherInnerAcivity;
import com.asuscomm.zhbee.avmoo_app.Bean.TeacherBean;
import com.asuscomm.zhbee.avmoo_app.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by bee on 2017/5/14 0014.
 */
public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.Viewhodler> {
    private List<TeacherBean> list;

    public TeacherAdapter(List<TeacherBean> list) {
        this.list = list;
    }


    @Override
    public Viewhodler onCreateViewHolder(ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_teacher_inner_itme,parent,false);
        final Viewhodler viewhodler =  new Viewhodler(view);
        viewhodler.TeacherIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), TeacherInnerAcivity.class);
                intent.putExtra("TEACHER_URL",list.get(viewhodler.getLayoutPosition()).getURL());
                intent.putExtra("TEACHER_IMG",list.get(viewhodler.getLayoutPosition()).getTeacherimg());
                intent.putExtra("TEACHER_NAME",list.get(viewhodler.getLayoutPosition()).getName());
                view.getContext().startActivity(intent);
            }
        });
        return  viewhodler;
    }

    @Override
    public void onBindViewHolder(Viewhodler viewhodler, int position) {
        viewhodler.TeacherTTT.setText(list.get(position).getName());
        viewhodler.TeacherIMG.setImageURI(list.get(position).getTeacherimg());


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class Viewhodler extends RecyclerView.ViewHolder {
        TextView TeacherTTT;
        SimpleDraweeView TeacherIMG;

        Viewhodler(View itemView) {
            super(itemView);
            TeacherIMG = (SimpleDraweeView) itemView.findViewById(R.id.TeacherIMG);
            TeacherTTT = (TextView) itemView.findViewById(R.id.TeacherTTT);
        }
    }
}

