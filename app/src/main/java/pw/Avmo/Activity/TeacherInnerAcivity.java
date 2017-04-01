package pw.Avmo.Activity;

import android.annotation.TargetApi;
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
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import pw.Avmo.Adapter.AllAdapter;
import pw.Avmo.Bean.AllBean;
import pw.Avmo.Bean.TeacherInfomationBean;
import pw.Avmo.R;
import pw.Avmo.Source;


public class TeacherInnerAcivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.teacher_inner_swipetefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private Integer urlindex = 1;

    @BindView(R.id.teacher_inner_img)
    SimpleDraweeView teacherInnerImg;
    @BindView(R.id.teacher_name)
    TextView teacherName;
    @BindView(R.id.teacher_birthday)
    TextView teacherBirthday;
    @BindView(R.id.teacher_age)
    TextView teacherAge;
    @BindView(R.id.teacher_height)
    TextView teacherHeight;
    @BindView(R.id.teacher_cup)
    TextView teacherCup;
    @BindView(R.id.teacher_bust)
    TextView teacherBust;
    @BindView(R.id.teacher_waist)
    TextView teacherWaist;
    @BindView(R.id.teacher_hip)
    TextView teacherHip;
    @BindView(R.id.teacher_place_to_birth)
    TextView teacherPlaceToBirth;
    @BindView(R.id.teacher_hobbies)
    TextView teacherHobbies;
    @BindView(R.id.teacher_inner_recyclerview)
    RecyclerView teacherInnerRecyclerview;

    private String TEACHER_URL;

    private InformationHandler informationHandler;
    private RecycleViewHandler recycleViewHandler;
    private AllAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List<AllBean> beanList;

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
        ButterKnife.bind(this);
        TEACHER_URL = getIntent().getStringExtra("TEACHER_URL") + "/page/";
        String TEACHER_IMG = getIntent().getStringExtra("TEACHER_IMG");
        String TEACHER_NAME = getIntent().getStringExtra("TEACHER_NAME");
        getSupportActionBar().setTitle(TEACHER_NAME);
        teacherInnerImg.setImageURI(TEACHER_IMG);
        teacherName.setText(TEACHER_NAME);
        layoutManager = new LinearLayoutManager(this);
        teacherInnerRecyclerview.setLayoutManager(layoutManager);
        teacherInnerRecyclerview.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastItem = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                try {
                    if (lastItem + 1 == adapter.getItemCount()) {
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
                lastItem = layoutManager.findLastVisibleItemPosition();
            }
        });


        InformationThread thread = new InformationThread();
        thread.setName("get");
        thread.start();
        informationHandler = new InformationHandler();
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

                if (Objects.equals(getName(), "get")) {
                    Message InformationMessage = informationHandler.obtainMessage();
                    InformationMessage.obj = Source.getTeacherInformation(html);
                    informationHandler.sendMessage(InformationMessage);
                }
                Message recycleViewMessage = recycleViewHandler.obtainMessage();
                if (Objects.equals(getName(), "add")) {
//                    urlindex += 1;
                    List<AllBean> list = Source.getAllBean(html);
                    Log.d("InformationThread", "list.size():" + list.size());
                    if (list.size() == 0){
                        recycleViewMessage.what = 2;
                    }else {
                        recycleViewMessage.what = 1;
                        beanList.addAll(list);
                    }
                } else {
                    recycleViewMessage.what = 0;
                    beanList = Source.getAllBean(html);
                    recycleViewMessage.obj = beanList;
                }
                recycleViewHandler.sendMessage(recycleViewMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public class InformationHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            teacherInnerRecyclerview.setHasFixedSize(true);
            TeacherInfomationBean bean = (TeacherInfomationBean) msg.obj;
            teacherBirthday.setText(bean.getBirthday());
            teacherAge.setText(bean.getAge());
            teacherHeight.setText(bean.getHeight());
            teacherCup.setText(bean.getCup());
            teacherBust.setText(bean.getBust());
            teacherWaist.setText(bean.getWaist());
            teacherHip.setText(bean.getHip());
            teacherPlaceToBirth.setText(bean.getPlaceToBirth());
            teacherHobbies.setText(bean.getHobbies());
        }
    }


    public class RecycleViewHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            teacherInnerRecyclerview.setHasFixedSize(true);
            if (msg.what == 0) {
                adapter = new AllAdapter((List<AllBean>) msg.obj);
                teacherInnerRecyclerview.setAdapter(adapter);
            } else if (msg.what ==1){
//                Toast.makeText(getActivity(), "加载中请稍等", Toast.LENGTH_SHORT).show();
                adapter.notifyItemInserted(0);
            }else if (msg.what == 2){
                Toast.makeText(TeacherInnerAcivity.this, "没有啦 不要再划啦", Toast.LENGTH_SHORT).show();
            }
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}




