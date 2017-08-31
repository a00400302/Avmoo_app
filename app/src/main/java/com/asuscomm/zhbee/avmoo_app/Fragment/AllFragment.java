package com.asuscomm.zhbee.avmoo_app.Fragment;


import android.annotation.TargetApi;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
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
import org.jsoup.*;
import org.jsoup.nodes.Document;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllFragment extends BlankFragment implements SwipeRefreshLayout.OnRefreshListener {
    private Integer urlindex = 1;
    private ArrayList<AllBean> beanList;
    private RecyclerView listView;
    private Handler handler;
    private AllAdapter allAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private StaggeredGridLayoutManager layoutManager;


    public AllFragment() {
    }




    public static AllFragment newInstance(int maOrManOrOuTYPE) {
        AllFragment fragment = new AllFragment();
        Bundle args = new Bundle();

        args.putInt("maOrManOrOuTYPE", maOrManOrOuTYPE);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getArguments() != null) {
            maOrManTYPE = getArguments().getInt("maOrManOrOuTYPE");
        }
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_all, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        listView = (RecyclerView) rootView.findViewById(R.id.titlelist);
        SpacesItemDecoration decoration = new SpacesItemDecoration(18);
        listView.addItemDecoration(decoration);




        return rootView;
    }


    private class SpacesItemDecoration extends RecyclerView.ItemDecoration{
        private  int space;

        SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left=space;
            outRect.right=space;
            outRect.bottom=space;
            if (parent.getChildAdapterPosition(view)==0){
                outRect.top =space;
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        listView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int[] lastItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                try {
                    if (lastItem[0] + 1 == allAdapter.getItemCount() || lastItem[1] + 1 == allAdapter.getItemCount()) {
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
                int[] f = new int[2];
                lastItem = layoutManager.findLastVisibleItemPositions(f);

            }
        });
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible){
            if (beanList == null){
                handler = new firstHandeler();
                Thread getimgurl = new ietindeximgThread();
                getimgurl.setName("GET URL");
                getimgurl.start();
                swipeRefreshLayout.setOnRefreshListener(this);
                int TYPE = 0;
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
        urlindex = 1;
        Thread refresh = new ietindeximgThread();

        refresh.setName("refresh");
        refresh.start();
    }

//            new Handler().postDelayed(new Runnable() {
//        @Override
//        public void run() {
//            swipeRefreshLayout.setRefreshing(false);
//        }
//    },3000);

    private class firstHandeler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            listView.setHasFixedSize(true);
            if (msg.what == 0) {
                layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                listView.setLayoutManager(layoutManager);
                allAdapter = new AllAdapter((ArrayList<AllBean>) msg.obj);
                listView.setAdapter(allAdapter);
            } else if(msg.what == 1){
//                Toast.makeText(getActivity(), "加载中请稍等", Toast.LENGTH_SHORT).show();
//                allAdapter.notifyItemInserted(0);
                allAdapter.notifyDataSetChanged();
            }else if (msg.what == 2){
                Toast.makeText(getActivity(), "没有啦 不要再划啦", Toast.LENGTH_SHORT).show();
            }
            swipeRefreshLayout.setRefreshing(false);


        }
    }


    private class ietindeximgThread extends Thread {

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        public void run() {
            try {
                if (Objects.equals(getName(), "add")) {
                    urlindex += 1;
                }
                String url = null;
                if (maOrManTYPE == MA){
                    url = "https://www.javbus.com/page/";
                }
                if (maOrManTYPE == MAN){
                    url = "https://www.javbus.com/uncensored/page/";
                }
                if (maOrManTYPE == OU){
                    url = "https://www.javbus.xyz/page/";
                }
                String urls = url + urlindex.toString();
                Log.d("urls", "run: " + urls);
                Document html = Jsoup.connect(urls).ignoreHttpErrors(true).get();
                Document content = Jsoup.parse(html.toString());
                Message message = handler.obtainMessage();
                if (Objects.equals(getName(), "add")) {
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