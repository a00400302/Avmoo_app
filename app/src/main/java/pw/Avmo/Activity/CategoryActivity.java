package pw.Avmo.Activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import pw.Avmo.Adapter.AllAdapter;
import pw.Avmo.R;
import pw.Avmo.Source;
import pw.Avmo.Bean.AllBean;

public class CategoryActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private Integer urlindex = 1;
    private RecyclerView listView;
    private Handler handler;
    private AllAdapter allAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
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
        getSupportActionBar().setTitle(titlename);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        handler = new firstHandeler();
        Thread getimgurl = new ietindeximgThread();
        getimgurl.setName("GET URL");
        getimgurl.start();
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        listView = (RecyclerView) findViewById(R.id.category_list);
//        LinearLayoutManager layoutManager =
        listView.setLayoutManager(new LinearLayoutManager(this));
//        listView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            int lastItem = 0;
//
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (lastItem + 1 == allAdapter.getItemCount() / 2) {
//                    swipeRefreshLayout.setRefreshing(true);
//                    Thread getimgurl = new ietindeximgThread();
//                    getimgurl.setName("add");
//                    getimgurl.start();
//                }
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                lastItem = layoutManager.findLastVisibleItemPosition();
//            }
//        });

        swipeRefreshLayout.setOnRefreshListener(this);
        if (TYPE == 0) {
            swipeRefreshLayout.setRefreshing(true);
        } else {
            swipeRefreshLayout.setRefreshing(false);
        }
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
            } else {
                allAdapter.notifyItemInserted(0);
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
//                    message.what = 1;
//                }else {
//                    message.what = 0;
//                }
                String url = getIntent().getStringExtra("ALLURL") + "/page/";
                String urls = url + urlindex.toString();
                Log.d("urls", "run: " + urls);
                Document html = Jsoup.connect(urls).get();
                Document content = Jsoup.parse(html.toString());
                Message message = handler.obtainMessage();
                if (Objects.equals(getName(), "add")) {
//                    urlindex += 1;
                    message.what = 1;
                } else {
                    message.what = 0;
                }
                message.obj = Source.getAllBean(content);
                handler.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}