package pw.Avmo.Fragment;


import android.graphics.Bitmap;
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

import org.jsoup.*;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pw.Avmo.Adapter.AllAdapter;
import pw.Avmo.bean.Allbean;
import pw.Avmo.R;
import pw.Avmo.Source;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private Integer urlindex = 1;
    private RecyclerView listView;
    private Handler handler;
    private AllAdapter allAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int TYPE = 0;
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
        Thread getimgurl = new ietindeximgThread();
        getimgurl.setName("GET URL");
        getimgurl.start();
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        listView = (RecyclerView) view.findViewById(R.id.titlelist);
        layoutManager = new LinearLayoutManager(getActivity());
        listView.setLayoutManager(layoutManager);
        listView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastItem = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (lastItem + 1 == allAdapter.getItemCount() / 2) {
                    swipeRefreshLayout.setRefreshing(true);
                    Thread getimgurl = new ietindeximgThread();
                    getimgurl.setName("add");
                    getimgurl.start();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastItem = layoutManager.findLastVisibleItemPosition();
            }
        });
        handler = new firstHandeler();
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
                allAdapter = new AllAdapter((List<Allbean>) msg.obj);
                listView.setAdapter(allAdapter);
            } else {
                allAdapter.notifyItemInserted(0);
            }
            swipeRefreshLayout.setRefreshing(false);


        }
    }


    class ietindeximgThread extends Thread {

        @Override
        public void run() {
            try {
                if (getName() == "add") {
                    urlindex += 1;
                }
//                    message.what = 1;
//                }else {
//                    message.what = 0;
//                }
                String url = "https://avmo.pw/cn/page/";
                String urls = url + urlindex.toString();
                Log.d("urls", "run: " + urls);
                Document html = Jsoup.connect(urls).get();
                Document content = Jsoup.parse(html.toString());
                Message message = handler.obtainMessage();
                if (getName() == "add") {
                    urlindex += 1;
                    message.what = 1;
                } else {
                    message.what = 0;
                }
                message.obj = getAvmoolist(content);;
                handler.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public List<Allbean> getAvmoolist(Document doc) {
        List<String> title = Source.getTitle(doc);
        List<String> fanhao = Source.getFanhao(doc);
        List<Bitmap> imgurl = Source.getImgUrl(doc);
        List<String> time = Source.getTime(doc);
        List<Allbean> allarray = new ArrayList<Allbean>();
        for (int i = 0; i < title.size(); i++) {
            Log.d("allbeab", "getAvmooblist: " + imgurl.get(i));
            Log.d("allbeab", "getAvmooblist: " + title.get(i) + i);
            Log.d("allbeab", "getAvmooblist: " + fanhao.get(i));
            Log.d("allbeab", "getAvmooblist: " + time.get(i));
            Allbean allbean = new Allbean();
            allbean.setFanhao(fanhao.get(i));
            allbean.setImgurl(imgurl.get(i));
            allbean.setTime(time.get(i));
            allbean.setTitle(title.get(i));
            allarray.add(allbean);
        }
        return allarray;
    }

}