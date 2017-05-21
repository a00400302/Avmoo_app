package com.asuscomm.zhbee.avmoo_app.Fragment;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.asuscomm.zhbee.avmoo_app.Adapter.AllAdapter;
import com.asuscomm.zhbee.avmoo_app.Bean.AllBean;
import com.asuscomm.zhbee.avmoo_app.R;
import com.asuscomm.zhbee.avmoo_app.Source;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;




public class SreachFragment extends BlankFragment implements SwipeRefreshLayout.OnRefreshListener {
    private List<AllBean> beanList;
    private Integer urlindex = 1;
    private SwipeRefreshLayout swipeRefreshLayout;
    private StaggeredGridLayoutManager layoutManager;
    private RecyclerView listView;
    private Handler handler;

    private AllAdapter adapter;

    private String keyword;

    public SreachFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SreachFragment newInstance(int maOrManTYPE, String keyword) {
        SreachFragment fragment = new SreachFragment();
        Bundle args = new Bundle();
        args.putInt("maOrManOrOuTYPE", maOrManTYPE);
        args.putString("KEYWORD", keyword);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            maOrManTYPE = getArguments().getInt("maOrManOrOuTYPE");
            keyword = getArguments().getString("KEYWORD");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_all, container, false);
        // Inflate the layout for this fragment
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        listView = (RecyclerView) rootView.findViewById(R.id.titlelist);
        listView.addItemDecoration(new SeacherItemDecoration());
        return rootView;
    }

    private  class SeacherItemDecoration extends  RecyclerView.ItemDecoration{

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.bottom = 18;
            outRect.right = 18;
            outRect.left = 18;
            if (parent.getChildAdapterPosition(view) == 0){
                outRect.top =18;
            }

        }
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
            if (beanList == null) {
                handler = new SearchHandler();
                SearchThread thread = new SearchThread();
                thread.start();
                swipeRefreshLayout.setOnRefreshListener(SreachFragment.this);
                int TYPE = 0;
                if (0 == TYPE) {
                    swipeRefreshLayout.setRefreshing(true);
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                }
                swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
            }

        }
    }

    @Override
    public void onRefresh() {
        urlindex = 1;
        Thread refresh = new SearchThread();
        refresh.setName("refresh");
        refresh.start();
    }


    public class SearchThread extends Thread {
        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        public void run() {
            super.run();
            try {
                String url = null;
                if (Objects.equals(getName(), "add")) {
                    urlindex += 1;
                }
                if (maOrManTYPE == MA) {
                    url = "https://www.javbus3.com/search/" + keyword + "/" + urlindex + "&type=1";
                } else {
                    url = "https://www.javbus3.com/uncensored/search/" + keyword + "/" + urlindex + "&type=1";
                }

                Document document = Jsoup.connect(url).ignoreHttpErrors(true).get();


                Message message = handler.obtainMessage();
                if (Objects.equals(getName(), "add")) {
//                    urlindex += 1;
                    List<AllBean> list = Source.getAllBean(document);
                    Log.d("InformationThread", "list.size():" + list.size());
                    if (list.size() == 0) {
                        message.what = 2;
                    } else {
                        message.what = 1;
                        beanList.addAll(list);
                    }
                } else {
                    message.what = 0;
                    beanList = Source.getAllBean(document);
                    message.obj = beanList;
                }
                handler.sendMessage(message);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class SearchHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            listView.setHasFixedSize(true);
            if (msg.what == 0) {
                ArrayList<AllBean> list = (ArrayList<AllBean>) msg.obj;
                if (list.size() == 0) {
                    Toast.makeText(getContext(), "关键字  " + keyword + "  没有搜索结果", Toast.LENGTH_LONG).show();
                } else {
                    layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
                    listView.setLayoutManager(layoutManager);
                    adapter = new AllAdapter(list);
                    listView.setAdapter(adapter);
                }
            } else if (msg.what == 1) {
                adapter.notifyDataSetChanged();
            } else if (msg.what == 2) {
                Toast.makeText(getContext(), "没有啦 不要再划啦", Toast.LENGTH_SHORT).show();
            }
            swipeRefreshLayout.setRefreshing(false);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        listView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int[] lastItem ;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                try {
                    if (lastItem[0] + 1 == adapter.getItemCount()|| lastItem[1] + 1 == adapter.getItemCount()) {
                        swipeRefreshLayout.setRefreshing(true);
                        Thread getimgurl = new SearchThread();
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
                int[] ints = new int[2];
                lastItem = layoutManager.findLastVisibleItemPositions(ints);
            }
        });
    }
}
