package com.asuscomm.zhbee.avmoo_app.Activity;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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

public class CategoryActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private Integer urlindex = 1;
    private RecyclerView listView;
    private List<AllBean> beanList;
    private Handler handler;
    private AllAdapter allAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private StaggeredGridLayoutManager layoutManager;
    private int TYPE = 0;


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
        setContentView(R.layout.activity_category);
        String titlename = getIntent().getStringExtra("NAME");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(titlename);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        handler = new firstHandeler();
        Thread getimgurl = new CategoryThread();
        getimgurl.setName("GET URL");
        getimgurl.start();
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.category_swiperefreshlayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        listView = (RecyclerView) findViewById(R.id.category_list);
        swipeRefreshLayout.setOnRefreshListener(this);
        int TYPE = 0;
        if (TYPE == 0) {
            swipeRefreshLayout.setRefreshing(true);
        } else {
            swipeRefreshLayout.setRefreshing(false);
        }

        listView.addItemDecoration(new SpacesItemDecoration(18));
        listView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int[] lastItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                try {
                    if (lastItem[0]+ 1 == allAdapter.getItemCount()||lastItem[1]+ 1 == allAdapter.getItemCount()) {
                        swipeRefreshLayout.setRefreshing(true);
                        Thread getimgurl = new CategoryThread();
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
        Thread refresh = new CategoryThread();
        refresh.setName("refresh");
        refresh.start();
    }



    class firstHandeler extends Handler {
        public void handleMessage(Message msg) {
            listView.setHasFixedSize(true);
            if (msg.what == 0) {
                layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
                listView.setLayoutManager(layoutManager);
                allAdapter = new AllAdapter((ArrayList<AllBean>) msg.obj);
                listView.setAdapter(allAdapter);
            } else if(msg.what == 1){
                allAdapter.notifyDataSetChanged();
            }else if (msg.what == 2){
                Toast.makeText(CategoryActivity.this, "没有啦 不要再划啦", Toast.LENGTH_SHORT).show();
            }
            swipeRefreshLayout.setRefreshing(false);


        }
    }


    class CategoryThread extends Thread {


        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        public void run() {
            try {
                if (Objects.equals(getName(), "add")) {
                    urlindex += 1;
                }
                String url = getIntent().getStringExtra("ALLURL") + "/";
                String urls = url + urlindex.toString();
                Log.d("urls", "run: " + urls);
                Document html = Jsoup.connect(urls).get();
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
