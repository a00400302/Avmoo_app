package pw.Avmo.Fragment;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.jsoup.*;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import pw.Avmo.Activity.TeacherInnerAcivity;
import pw.Avmo.Adapter.AllAdapter;
import pw.Avmo.Bean.AllBean;
import pw.Avmo.R;
import pw.Avmo.Source;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private Integer urlindex = 1;
    private List<AllBean> beanList;
    private RecyclerView listView;
    private Handler handler;
    private AllAdapter allAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager layoutManager;

    public AllFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static AllFragment newInstance(String param1, String param2) {
        return new AllFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all, container, false);
        handler = new firstHandeler();
        Thread getimgurl = new ietindeximgThread();
        getimgurl.setName("GET URL");
        getimgurl.start();
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        listView = (RecyclerView) view.findViewById(R.id.titlelist);
        layoutManager = new LinearLayoutManager(getActivity());
        listView.setLayoutManager(layoutManager);
        swipeRefreshLayout.setOnRefreshListener(this);
        int TYPE = 0;
        if (TYPE == 0) {
            swipeRefreshLayout.setRefreshing(true);
        } else {
            swipeRefreshLayout.setRefreshing(false);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        listView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastItem = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                try {
                    if (lastItem + 1 == allAdapter.getItemCount()) {
                        swipeRefreshLayout.setRefreshing(true);
                        Thread getimgurl = new ietindeximgThread();
                        getimgurl.setName("add");
                        getimgurl.start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastItem = layoutManager.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    public void onRefresh() {
        urlindex = 1;
        Thread refresh = new ietindeximgThread();
        refresh.setName("refresh");
        refresh.start();
    }


    class firstHandeler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            listView.setHasFixedSize(true);
            if (msg.what == 0) {
                allAdapter = new AllAdapter((List<AllBean>) msg.obj);
                listView.setAdapter(allAdapter);
            } else if(msg.what == 1){
//                Toast.makeText(getActivity(), "加载中请稍等", Toast.LENGTH_SHORT).show();
                allAdapter.notifyItemInserted(0);
            }else if (msg.what == 2){
                Toast.makeText(getActivity(), "没有啦 不要再划啦", Toast.LENGTH_SHORT).show();
            }
            swipeRefreshLayout.setRefreshing(false);


        }
    }


    class ietindeximgThread extends Thread {

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        public void run() {
            try {
                if (Objects.equals(getName(), "add")) {
                    urlindex += 1;
                }
                String url = "https://avmo.pw/cn/page/";
                String urls = url + urlindex.toString();
                Log.d("urls", "run: " + urls);
                Document html = Jsoup.connect(urls).ignoreHttpErrors(true).get();
                Document content = Jsoup.parse(html.toString());
                Message message = handler.obtainMessage();
                if (Objects.equals(getName(), "add")) {
//                    urlindex += 1;
                    List<AllBean> list = Source.getAllBean(html);
                    if (list.size() == 0){
                        message.what = 2;
                    }else {
                        message.what = 1;
                        beanList.addAll(list);
                    }
                } else {
                    message.what = 0;
                    beanList = Source.getAllBean(content);
                    message.obj = beanList;
                }

                handler.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}