package pw.Avmo.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pw.Avmo.R;
import pw.Avmo.bean.Teacherbean;

/**
 * Created by bee on 2016/9/28.
 */

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.Viewhodler> {
    private List<Teacherbean> list;

    public TeacherAdapter(List<Teacherbean> list) {
        this.list = list;
    }


    @Override
    public Viewhodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_teacher_itme,parent,false);
        return new Viewhodler(view);
    }

    @Override
    public void onBindViewHolder(Viewhodler viewhodler, int position) {
        viewhodler.TeacherTTT.setText(list.get(position).getName());
//        viewhodler.TeacherIMG.setImageBitmap(list.get(position).getTeacherimg());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class Viewhodler extends RecyclerView.ViewHolder {
        TextView TeacherTTT;
//        ImageView TeacherIMG;

        Viewhodler(View itemView) {
            super(itemView);
//            TeacherIMG = (ImageView) itemView.findViewById(R.id.TeacherIMG);
            TeacherTTT = (TextView) itemView.findViewById(R.id.TeacherTTT);
        }
    }
}
