package pw.Avmo.Activity;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import pw.Avmo.Adapter.AllAdapter;
import pw.Avmo.Bean.AllBean;
import pw.Avmo.R;
import pw.Avmo.Source;

public class SearchActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.search_recyclerview)
    RecyclerView searchRecyclerview;
    private List<AllBean> beanList;
    private Integer urlindex = 1;
    @BindView(R.id.search_swiperefreshlayout)
    SwipeRefreshLayout searchSwiperefreshlayout;
    private SearchHandler handler;
    private String keyWord;
    private LinearLayoutManager layoutManager;
    private AllAdapter adapter;

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
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        keyWord = getIntent().getStringExtra(SearchManager.QUERY);
        getSupportActionBar().setTitle("搜索   -" + keyWord + "-   结果");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        handler = new SearchHandler();
        SearchThread thread = new SearchThread();
        thread.start();
        searchSwiperefreshlayout.setOnRefreshListener(this);
        int TYPE = 0;
        if (0 == TYPE) {
            searchSwiperefreshlayout.setRefreshing(true);
        } else {
            searchSwiperefreshlayout.setRefreshing(false);
        }
        searchSwiperefreshlayout.setColorSchemeResources(R.color.colorPrimary);


    }

    @Override
    protected void onStart() {
        super.onStart();
        layoutManager = new LinearLayoutManager(this);
        searchRecyclerview.setLayoutManager(layoutManager);
        searchRecyclerview.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastItem = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                try {
                    if (lastItem + 1 == adapter.getItemCount()) {
                        searchSwiperefreshlayout.setRefreshing(true);
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
                lastItem = layoutManager.findLastVisibleItemPosition();
            }
        });
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
                if (Objects.equals(getName(), "add")) {
                    urlindex += 1;
                }
                String url = "https://avmo.pw/cn/search/" + keyWord + "/page/" + urlindex;
                Document document = Jsoup.connect(url).ignoreHttpErrors(true).get();



                Message message = handler.obtainMessage();
                if (Objects.equals(getName(), "add")) {
//                    urlindex += 1;
                    List<AllBean> list = Source.getAllBean(document);
                    Log.d("InformationThread", "list.size():" + list.size());
                    if (list.size() == 0){
                        message.what = 2;
                    }else {
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
            searchRecyclerview.setHasFixedSize(true);
            if (msg.what == 0) {
                List<AllBean> list = (List<AllBean>) msg.obj;
                if (list.size() == 0){
                    Toast.makeText(SearchActivity.this, "关键字  " + keyWord + "  没有搜索结果", Toast.LENGTH_LONG).show();
                }else {
                    adapter = new AllAdapter(list);
                    searchRecyclerview.setAdapter(adapter);
                }
            } else if(msg.what == 1){
                adapter.notifyItemInserted(0);
            }else if (msg.what == 2){
                Toast.makeText(SearchActivity.this, "没有啦 不要再划啦", Toast.LENGTH_SHORT).show();
            }
            searchSwiperefreshlayout.setRefreshing(false);

        }
    }


}
