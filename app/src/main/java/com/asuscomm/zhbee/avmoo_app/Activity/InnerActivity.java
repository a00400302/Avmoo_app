package com.asuscomm.zhbee.avmoo_app.Activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.asuscomm.zhbee.avmoo_app.Adapter.InnerXiujyunLantuAdapter;
import com.asuscomm.zhbee.avmoo_app.Adapter.TeacherAdapter;
import com.asuscomm.zhbee.avmoo_app.Bean.InnerBean;
import com.asuscomm.zhbee.avmoo_app.Bean.TeacherBean;
import com.asuscomm.zhbee.avmoo_app.Bean.YulantuBean;
import com.asuscomm.zhbee.avmoo_app.R;
import com.asuscomm.zhbee.avmoo_app.Source;
import com.facebook.drawee.view.SimpleDraweeView;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class InnerActivity extends BaseActivity implements SwipeBackActivityBase {
    SimpleDraweeView InnerFenmian;
    TextView InnerFanhao;
    TextView InnerTitle;
    TextView InnerFaxingShang;
    TextView InnerKaifaShang;
    TextView InnerXilie;
    TextView InnerLeiBit;
    TextView InnerTime;
    TextView InnerHowLong;
    InnerHandler handler;
    keyHandler keyHandler;
    RecyclerView InnerRec;
    InnerXiujyunLantuAdapter innerXiujyunLantuAdapter;
    RecyclerView simple;
    String url;
    private SwipeBackActivityHelper mHelper;
    private Document document;
    private String fanhou;

    public InnerActivity() throws MalformedURLException {
    }

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
        setContentView(R.layout.activity_inner);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
        fanhou = getIntent().getStringExtra("FANHAO");
        getSupportActionBar().setTitle(fanhou);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        url = getIntent().getStringExtra("ALLURL");
        Log.d("InnerActivity", url);
        InnerFenmian = (SimpleDraweeView) findViewById(R.id.InnerIMG);
        InnerFenmian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(fanhou);
                Toast.makeText(InnerActivity.this, "复制番号成功", Toast.LENGTH_SHORT).show();
            }
        });
        InnerFenmian.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                keyThead keyThead = new keyThead();
                keyThead.start();
                return true;
            }
        });
        Toast.makeText(this, "                点击封面复杂番号\n长按封面  带你过发夹弯 ~~~~~~~~~", Toast.LENGTH_SHORT).show();
        InnerFanhao = (TextView) findViewById(R.id.inner_fanhao);
        InnerTitle = (TextView) findViewById(R.id.InnerTTT);
        InnerTime = (TextView) findViewById(R.id.inner_faxingshijian);
        InnerHowLong = (TextView) findViewById(R.id.inner_changdu);
        InnerFaxingShang = (TextView) findViewById(R.id.inner_faxingshang);
        InnerKaifaShang = (TextView) findViewById(R.id.inner_zhizoshang);
        InnerLeiBit = (TextView) findViewById(R.id.inner_leibie);
        InnerXilie = (TextView) findViewById(R.id.inner_xilie);

        InnerRec = (RecyclerView) findViewById(R.id.InnerRec);
        simple = (RecyclerView) findViewById(R.id.yulan_recyclerview);
        RecyclerView.LayoutManager layoutManager1 = new ScrollGridLayoutManager(this, 2);
        RecyclerView.LayoutManager layoutManager2 = new ScrollGridLayoutManager(this, 2);
        InnerRec.setLayoutManager(layoutManager1);
        InnerRec.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.right = 20;
                outRect.top = 20 ;
                outRect.left = 20;
                outRect.bottom = 20;
            }
        });
        simple.setLayoutManager(layoutManager2);
        simple.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.right = 20;
                outRect.top = 20 ;
                outRect.left = 20;
                outRect.bottom = 20;
            }
        });

        handler = new InnerHandler();
        keyHandler = new keyHandler();
        Thead thead = new Thead();
        thead.start();
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }


    class InnerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    InnerBean innerBean = (InnerBean) msg.obj;
                    InnerTitle.setText(innerBean.getTitle());
                    InnerFenmian.setImageURI(innerBean.getFenmian());
                    InnerFanhao.setText(innerBean.getFanhao());
                    InnerTime.setText(innerBean.getTime());
                    if (innerBean.getXilie().get("name") == null) {
                        InnerXilie.setText("系列:  暂无信息");
                    } else {
                        InnerXilie.setText(innerBean.getXilie().get("name"));
                    }
                    InnerLeiBit.setText(innerBean.getLeiBit());
                    InnerKaifaShang.setText(innerBean.getKaifaShang().get("name"));
                    InnerFaxingShang.setText(innerBean.getFaxingShang().get("name"));
                    InnerHowLong.setText(innerBean.getHowLong());
                    break;
                case 2:
                    TeacherAdapter adapter = new TeacherAdapter((ArrayList<TeacherBean>) msg.obj);
                    InnerRec.setAdapter(adapter);
                    break;
                case 3:
                    innerXiujyunLantuAdapter = new InnerXiujyunLantuAdapter((ArrayList<YulantuBean>) msg.obj);
                    simple.setAdapter(innerXiujyunLantuAdapter);
                    break;
                default:
                    break;
            }
        }
    }

    public class ScrollGridLayoutManager extends GridLayoutManager {
        public ScrollGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        ScrollGridLayoutManager(Context context, int spanCount) {
            super(context, spanCount);
        }

        public ScrollGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
            super(context, spanCount, orientation, reverseLayout);
        }


        @Override
        public boolean canScrollVertically() {
            return false;
        }
    }

    class keyThead extends Thread {
        @Override
        public void run() {
            super.run();
            Message message = new Message();
            message.what = 2;
            keyHandler.handleMessage(message);
        }
    }

//在线播放功能停用等待修复

//    public Message getMessage(String keyfanhou) {
//        String keyurl = "http://javbus555.com/index_movies.php?op=view&class=4&spcode=" + keyfanhou;
//        Document document = null;
//        try {
//            document = Jsoup.connect(keyurl).get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Matcher matcher = Pattern.compile(":8080/videos(.*?).m3u8").matcher(document.body().toString());
//        Message message = keyHandler.obtainMessage();
//        if (matcher.find()) {
//            message.what = 1;
//            message.obj = matcher.group();
//            return message;
//        } else {
//            keyfanhou = fanhou.replaceAll("-", "");
//            keyurl = "http://javbus555.com/index_movies.php?op=view&class=4&spcode=" + keyfanhou;
//            try {
//                document = Jsoup.connect(keyurl).get();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            matcher = Pattern.compile(":8080/videos(.*?).m3u8").matcher(document.body().toString());
//            if (matcher.find()) {
//                message.what = 1;
//                message.obj = matcher.group();
//                return message;
//            } else {
//                message.what = 2;
//                return message;
//            }
//        }
//    }


    class keyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            if (msg.what == 1) {
//                Intent intent = new Intent(InnerActivity.this, MediaSelectActivity.class);
//                intent.putExtra("keyword", fanhou);
//                intent.putExtra("key", (String) msg.obj);
//                startActivity(intent);
//            } else if (msg.what == 2) {
                Intent intent = new Intent(InnerActivity.this, MagnetActivity.class);
                intent.putExtra("keyword", fanhou);
                startActivity(intent);
//            }
        }
    }


    class Thead extends Thread {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void run() {
            try {
                document = Jsoup.connect(url).get();
                Document html = Jsoup.parse(document.toString());
                Message message1 = handler.obtainMessage();
                Message message2 = handler.obtainMessage();
                Message message3 = handler.obtainMessage();
                message1.what = 1;
                message2.what = 2;
                message3.what = 3;
                message1.obj = Source.getInnerName(html);
                message2.obj = Source.getTeacherIMG(html, 1);
                message3.obj = Source.getYulanIMG(html);
                handler.sendMessage(message1);
                handler.sendMessage(message2);
                handler.sendMessage(message3);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}
