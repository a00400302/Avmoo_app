package pw.Avmo.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pw.Avmo.R;
import pw.Avmo.bean.InnerTeacherBean;

/**
 * Created by Administrator on 2017/2/15.
 */

public class InnerTecherAdapter extends RecyclerView.Adapter<InnerTecherAdapter.viewhodler> {
    private List<InnerTeacherBean> list;

    public InnerTecherAdapter(List<InnerTeacherBean> list) {
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
        viewhodler.TeacherTTT.setText(list.get(position).getTeachername());
        viewhodler.TeacherIMG.setImageBitmap(list.get(position).getTeacherIMG());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
