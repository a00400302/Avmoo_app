package com.asuscomm.zhbee.avmoo_app.Activity;

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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.asuscomm.zhbee.avmoo_app.Adapter.HeaderBottomAdapter;
import com.asuscomm.zhbee.avmoo_app.Bean.AllBean;
import com.asuscomm.zhbee.avmoo_app.Fragment.AllFragment;
import com.asuscomm.zhbee.avmoo_app.R;
import com.asuscomm.zhbee.avmoo_app.Source;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;




public class TeacherInnerAcivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout swipeRefreshLayout;
    private Integer urlindex = 1;


    RecyclerView teacherInnerRecyclerview;

    private String TEACHER_URL;

    private RecycleViewHandler recycleViewHandler;
    private HeaderBottomAdapter adapter;
    private StaggeredGridLayoutManager layoutManager;
    private List<AllBean> beanList;
    private String teacher_name;
    private String teacher_img;




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_inner);
        teacherInnerRecyclerview = (RecyclerView) findViewById(R.id.teacher_inner_recyclerview);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.teacher_inner_swipetefreshlayout);
        TEACHER_URL = getIntent().getStringExtra("TEACHER_URL") + "/";
        teacher_img = getIntent().getStringExtra("TEACHER_IMG");
        teacher_name = getIntent().getStringExtra("TEACHER_NAME");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(teacher_name);
        teacherInnerRecyclerview.addItemDecoration(new SpacesItemDecoration(20));
        teacherInnerRecyclerview.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int[] lastItem ;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                try {
                    if (lastItem[0] + 1 == adapter.getItemCount() || lastItem[1] + 1 == adapter.getItemCount()) {
                        swipeRefreshLayout.setRefreshing(true);
                        Thread getimgurl = new InformationThread();
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




        InformationThread thread = new InformationThread();
        thread.setName("get");
        thread.start();
        recycleViewHandler = new RecycleViewHandler();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        swipeRefreshLayout.setOnRefreshListener(this);
        int TYPE = 0;
        if (TYPE == 0) {
            swipeRefreshLayout.setRefreshing(true);
        } else {
            swipeRefreshLayout.setRefreshing(false);
        }
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

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
    public void onRefresh() {
        urlindex = 1;
        Thread refresh = new InformationThread();
        refresh.setName("refresh");
        refresh.start();
    }



    public class InformationThread extends Thread {
        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        public void run() {
            super.run();
            try {
                if (Objects.equals(getName(), "add")) {
                    urlindex += 1;
                }
                String url = TEACHER_URL + urlindex.toString();
                Log.d("InformationThread", url);
                Document html = Jsoup.connect(url).ignoreHttpErrors(true).get();

                Message recycleViewMessage = recycleViewHandler.obtainMessage();
                if (Objects.equals(getName(), "add")) {
                    List<AllBean> list = Source.getAllBean(html);
                    Log.d("InformationThread", "list.size():" + list.size());
                    if (list.size() == 0) {
                        recycleViewMessage.what = 2;
                    } else {
                        recycleViewMessage.what = 1;
//                        HashMap<>  hashMap =
                        beanList.addAll(list);
                    }
                } else {
                    recycleViewMessage.what = 0;
                    beanList = Source.getAllBean(html);
                    ArrayList<String>information = Source.getTeacherInformation(html);
                    HashMap<String,Object>  hashMap = new HashMap<>();
                    hashMap.put("list",beanList);
                    hashMap.put("informations",information);
                    recycleViewMessage.obj = hashMap;
                }
                recycleViewHandler.sendMessage(recycleViewMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }



    public class RecycleViewHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            teacherInnerRecyclerview.setHasFixedSize(true);
            if (msg.what == 0) {
                HashMap<String,Object>  hashMap = (HashMap<String, Object>) msg.obj;
                ArrayList<AllBean> list = (ArrayList<AllBean>)hashMap.get("list");
                ArrayList<String>  informations = (ArrayList<String>) hashMap.get("informations");
                layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
                teacherInnerRecyclerview.setLayoutManager(layoutManager);
                adapter = new HeaderBottomAdapter(list,informations,teacher_img,TeacherInnerAcivity.this);
                teacherInnerRecyclerview.setAdapter(adapter);
            } else if (msg.what == 1) {
//                Toast.makeText(getActivity(), "加载中请稍等", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            } else if (msg.what == 2) {
                Toast.makeText(TeacherInnerAcivity.this, "没有啦 不要再划啦", Toast.LENGTH_SHORT).show();
            }
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}

