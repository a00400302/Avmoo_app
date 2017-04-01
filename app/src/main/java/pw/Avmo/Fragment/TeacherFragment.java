package pw.Avmo.Fragment;


import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import pw.Avmo.Adapter.TeacherAdapter;
import pw.Avmo.R;
import pw.Avmo.Source;
import pw.Avmo.Bean.TeacherBean;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeacherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeacherFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private Integer Urlindex = 1;
    private Handler Teacherhandler;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView listview;
    private List<TeacherBean> beanList;
    private int TYPE = 0;
    private GridLayoutManager layoutManager;
    private TeacherAdapter adapter;


    public TeacherFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeacherFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeacherFragment newInstance(String param1, String param2) {
        return new TeacherFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ArrayList<HashMap<String, Object>> teacherarraylist = new ArrayList<HashMap<String, Object>>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_teacher, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout2);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        listview = (RecyclerView) view.findViewById(R.id.teacherlist);
        Thread refresh = new TeacherThread();
        refresh.setName("GET URL");
        refresh.start();

        layoutManager = new GridLayoutManager(getActivity(), 2);
        listview.setLayoutManager(layoutManager);
        listview.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastItem;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastItem = layoutManager.findLastCompletelyVisibleItemPosition();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                try {
                    if (lastItem + 1 == adapter.getItemCount()) {
                        swipeRefreshLayout.setRefreshing(true);
                        Thread getimgurl = new TeacherThread();
                        getimgurl.setName("add");
                        getimgurl.start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Teacherhandler = new TeacherHandler();
        swipeRefreshLayout.setOnRefreshListener(this);
        if (TYPE == 0) {
            swipeRefreshLayout.setRefreshing(true);
        } else {
            swipeRefreshLayout.setRefreshing(false);
        }
        return view;
    }


    @Override
    public void onRefresh() {
        Urlindex = 1;
        Thread refresh = new TeacherThread();
        refresh.setName("refresh");
        refresh.start();
    }

    class TeacherHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            listview.setHasFixedSize(true);
            if (msg.what == 0) {
                adapter = new TeacherAdapter((List<TeacherBean>) msg.obj);
                listview.setAdapter(adapter);
            } else {
                adapter.notifyItemInserted(0);
//                Toast.makeText(getActivity(), "加载中请稍等", Toast.LENGTH_SHORT).show();
            }
            swipeRefreshLayout.setRefreshing(false);
        }
    }


    class TeacherThread extends Thread {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void run() {

            try {
                if (Objects.equals(getName(), "add")) {
                    Urlindex += 1;
                }
                String url = "https://avmo.pw/cn/actresses/page/";
                String urls = url + Urlindex.toString();
                Log.d("Teacher", "run:" + urls);
                Document document = Jsoup.connect(urls).get();
                Document html = Jsoup.parse(document.toString());
                Message message = Teacherhandler.obtainMessage();
                if (Objects.equals(getName(), "add")) {
                    message.what = 1;
                    beanList.addAll(getTeacherSource(html));
                } else {
                    message.what = 0;
                    beanList = getTeacherSource(html);
                    message.obj =  beanList;
                }

                Teacherhandler.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public List<TeacherBean> getTeacherSource(Document document) {
        //        List<String> imgurl = Source.getTeacherIMG(document);
//        List<TeacherBean> Teacherlist = new ArrayList<TeacherBean>();
//        for (int i = 0; i < list.size()+1; i++) {
//            Log.d("TeacherBean", "getTeacherlist: " + list.get(i));
//            TeacherBean teacherbean = new TeacherBean();
//            teacherbean.setName(Name.get(i+1));
//            teacherbean.setTeacherimg(imgurl.get(i));
//            Teacherlist.add(teacherbean);
//        }
        return Source.getTeacherIMG(document, 0);
    }

}
