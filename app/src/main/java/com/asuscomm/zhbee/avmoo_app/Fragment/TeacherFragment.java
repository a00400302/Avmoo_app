package com.asuscomm.zhbee.avmoo_app.Fragment;


import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.asuscomm.zhbee.avmoo_app.Adapter.TeacherAdapter;
import com.asuscomm.zhbee.avmoo_app.Bean.TeacherBean;
import com.asuscomm.zhbee.avmoo_app.R;
import com.asuscomm.zhbee.avmoo_app.Source;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.List;
import java.util.Objects;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeacherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeacherFragment extends BlankFragment implements SwipeRefreshLayout.OnRefreshListener {
    private Integer Urlindex = 1;
    private Handler Teacherhandler;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView listview;
    private List<TeacherBean> beanList;
    private int TYPE = 0;
    private StaggeredGridLayoutManager layoutManager;
    private TeacherAdapter adapter;


    public TeacherFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static TeacherFragment newInstance(int maOrManTYPE) {
        TeacherFragment fragment = new TeacherFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("maOrManTYPE", maOrManTYPE);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getArguments() != null) {
            maOrManTYPE = getArguments().getInt("maOrManTYPE");
        }
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_teacher, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout2);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        listview = (RecyclerView) rootView.findViewById(R.id.teacherlist);
        listview.addItemDecoration(new TeacherFragmentItemDecoration());
        return rootView;
    }


    private  class TeacherFragmentItemDecoration extends  RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.bottom = 20;
            outRect.right = 20;
            outRect.left = 20;
            outRect.top =20 ;
        }
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
            if (beanList == null) {
                Teacherhandler = new TeacherHandler();
                Thread refresh = new TeacherThread();
                refresh.setName("GET URL");
                refresh.start();
                swipeRefreshLayout.setOnRefreshListener(this);
                if (TYPE == 0) {
                    swipeRefreshLayout.setRefreshing(true);
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        }
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
                layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
                listview.setLayoutManager(layoutManager);
                adapter = new TeacherAdapter((List<TeacherBean>) msg.obj);
                listview.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
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
                String url = null;

                if (maOrManTYPE == MA) {
                    url = "https://www.javbus.com/actresses/";
                } else if (maOrManTYPE == MAN) {
                    url = "https://www.javbus.com/uncensored/actresses/";
                }


                String urls = url + Urlindex.toString();
                Log.d("Teacher", "run:" + urls);
                Document document = Jsoup.connect(urls).get();
                Message message = Teacherhandler.obtainMessage();
                if (Objects.equals(getName(), "add")) {
                    message.what = 1;
                    beanList.addAll(Source.getTeacherIMG(document, 0));
                } else {
                    message.what = 0;
                    beanList = Source.getTeacherIMG(document, 0);
                    message.obj = beanList;
                }

                Teacherhandler.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        listview.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int[] lastItem;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int[] ints = new int[2];
                lastItem = layoutManager.findLastVisibleItemPositions(ints);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                try {
                    if (lastItem[0] + 1 == adapter.getItemCount()|| lastItem[1] + 1 == adapter.getItemCount()) {
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
    }
}
