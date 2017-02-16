package pw.Avmo.Fragment;


import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

import pw.Avmo.Adapter.InnerTecherAdapter;
import pw.Avmo.Adapter.InnerXiujyunLantuAdapter;
import pw.Avmo.Adapter.TeacherAdapter;
import pw.Avmo.R;
import pw.Avmo.Source;
import pw.Avmo.bean.InnerBean;
import pw.Avmo.bean.InnerTeacherBean;
import pw.Avmo.bean.Teacherbean;
import pw.Avmo.bean.YulantuBean;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InnerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InnerFragment extends Fragment {
    ImageView InnerFenmian;
    TextView InnerFanhao;
    TextView  InnerTitle;
    TextView  InnerFaxingShang;
    TextView  InnerKaifaShang;
    TextView  InnerXilie;
    TextView InnerLeiBit;
    TextView  InnerTime;
    TextView   InnerDaoyan;
    TextView  InnerHowLong;
    InnerHandler handler;
    RecyclerView InnerRec;
    InnerXiujyunLantuAdapter innerXiujyunLantuAdapter;
    RecyclerView simple;
    public InnerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InnerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InnerFragment newInstance(String param1, String param2) {
        InnerFragment fragment = new InnerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inner, container, false);
        InnerFenmian = (ImageView) view.findViewById(R.id.InnerIMG);
        InnerFanhao = (TextView) view.findViewById(R.id.InnerFanhao);
        InnerTitle = (TextView) view.findViewById(R.id.InnerTTT);
        InnerTime = (TextView) view.findViewById(R.id.InnerFXSJ);
        InnerHowLong = (TextView) view.findViewById(R.id.InnerYPCD);
        InnerFaxingShang =  (TextView) view.findViewById(R.id.InnerFXS);
        InnerKaifaShang = (TextView) view.findViewById(R.id.InnerZZS);
        InnerLeiBit =  (TextView) view.findViewById(R.id.InnerLB);
        InnerXilie = (TextView) view.findViewById(R.id.InnerXL);
        InnerDaoyan = (TextView) view.findViewById(R.id.InnerDY);

        InnerRec = (RecyclerView) view.findViewById(R.id.InnerRec);
        simple  = (RecyclerView) view.findViewById(R.id.simple);
        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(getActivity(),2);
        RecyclerView.LayoutManager layoutManager2 = new GridLayoutManager(getActivity(),2);
        InnerRec.setLayoutManager(layoutManager1);
        simple.setLayoutManager(layoutManager2);

        handler = new InnerHandler();
        Thead  thead = new Thead();
        thead.start();



        return view;
    }





    class  InnerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    InnerBean  innerBean = (InnerBean) msg.obj;
                    InnerDaoyan.setText(innerBean.getDaoyan());
                    InnerFenmian.setImageBitmap(innerBean.getFenmian());
                    InnerLeiBit.setText(innerBean.getLeiBit());
                    InnerXilie.setText(innerBean.getXilie());
                    InnerKaifaShang.setText(innerBean.getKaifaShang());
                    InnerFanhao.setText(innerBean.getFanhao());
                    InnerTitle.setText(innerBean.getTitle());
                    InnerFaxingShang.setText(innerBean.getFaxingShang());
                    InnerTime.setText(innerBean.getTime());
                    InnerHowLong.setText(innerBean.getHowLong());
                    break;
                case 2:
                    InnerTecherAdapter adapter = new InnerTecherAdapter((List<InnerTeacherBean>) msg.obj);
                    InnerRec.setAdapter(adapter);
                    break;
                case 3:
                    innerXiujyunLantuAdapter = new InnerXiujyunLantuAdapter((List<YulantuBean>) msg.obj);
                    simple.setAdapter(innerXiujyunLantuAdapter);
                    break;
                default:break;
            }
        }
    }

    class Thead extends Thread {
        @Override
        public void run() {
            try {
                String url = "https://avmo.pw/cn/movie/5sw2";
                Log.d("Inner", "run:"  +  url);
                Document document = Jsoup.connect(url).get();
                Document html = Jsoup.parse(document.toString());
                Message message1 = handler.obtainMessage();
                Message message2 = handler.obtainMessage();
                Message message3 = handler.obtainMessage();
                message1.what = 1;
                message2.what = 2 ;
                message3.what = 3;
                message1.obj = Source.getInnerName(html);
                message2.obj = Source.getTeacherIMG(html);
                message3.obj = Source.getYulanIMG(html);
                handler.sendMessage(message1);
                handler.sendMessage(message2);
                handler.sendMessage(message3);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




//    public  static InnerBean getInnerBeanList(URL){
//
//    }

}
