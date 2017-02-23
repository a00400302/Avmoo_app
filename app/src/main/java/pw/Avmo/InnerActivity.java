package pw.Avmo;

import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import pw.Avmo.Adapter.InnerXiujyunLantuAdapter;

import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import pw.Avmo.Adapter.InnerTecherAdapter;
import pw.Avmo.bean.InnerBean;
import pw.Avmo.bean.Teacherbean;
import pw.Avmo.bean.YulantuBean;


public class InnerActivity extends AppCompatActivity {
    ImageView InnerFenmian;
    TextView InnerFanhao;
    TextView InnerTitle;
    TextView InnerFaxingShang;
    TextView InnerKaifaShang;
    TextView InnerXilie;
    TextView InnerLeiBit;
    TextView InnerTime;
    TextView InnerDaoyan;
    TextView InnerHowLong;
    InnerHandler handler;
    RecyclerView InnerRec;
    InnerXiujyunLantuAdapter innerXiujyunLantuAdapter;
    RecyclerView simple;

    String url;

    public InnerActivity() throws MalformedURLException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra("ALLURL");
        setContentView(R.layout.activity_inner);
        InnerFenmian = (ImageView) findViewById(R.id.InnerIMG);
        InnerFanhao = (TextView) findViewById(R.id.InnerFanhao);
        InnerTitle = (TextView) findViewById(R.id.InnerTTT);
        InnerTime = (TextView) findViewById(R.id.InnerFXSJ);
        InnerHowLong = (TextView) findViewById(R.id.InnerYPCD);
        InnerFaxingShang = (TextView) findViewById(R.id.InnerFXS);
        InnerKaifaShang = (TextView) findViewById(R.id.InnerZZS);
        InnerLeiBit = (TextView) findViewById(R.id.InnerLB);
        InnerXilie = (TextView) findViewById(R.id.InnerXL);
        InnerDaoyan = (TextView) findViewById(R.id.InnerDY);

        InnerRec = (RecyclerView) findViewById(R.id.InnerRec);
        simple = (RecyclerView) findViewById(R.id.simple);
        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(this, 2);
        RecyclerView.LayoutManager layoutManager2 = new GridLayoutManager(this, 2);
        InnerRec.setLayoutManager(layoutManager1);
        simple.setLayoutManager(layoutManager2);

        handler = new InnerHandler();
        Thead thead = new Thead();
        thead.start();
    }


    class InnerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    InnerBean innerBean = (InnerBean) msg.obj;
                    InnerDaoyan.setText(innerBean.getDaoyan());
                    InnerFenmian.setImageBitmap(innerBean.getFenmian());
                    InnerLeiBit.setText(innerBean.getLeiBit());
                    InnerXilie.setText(innerBean.getXilie());
                    InnerKaifaShang.setText(innerBean.getKaifaShang());
                    InnerFanhao.setText(innerBean.getFanhao());
                    InnerTitle.setText(innerBean.getTitle());
                    InnerFaxingShang.setText(innerBean.getFaxingShang());
                    InnerTime.setText(innerBean.getTime());
                    InnerHowLong.setText(innerBean.getHowLong());
                    break;
                case 2:
                    (List<Teacherbean>) msg.obj
                    InnerTecherAdapter adapter = new InnerTecherAdapter();
                    InnerRec.setAdapter(adapter);
                    break;
                case 3:

                    innerXiujyunLantuAdapter = new InnerXiujyunLantuAdapter((List<YulantuBean>) msg.obj);
                    simple.setAdapter(innerXiujyunLantuAdapter);
                    Thread thread = new thread();
                    thread.setName("xiao");
                    break;
                default:
                    break;
            }
        }
    }

    private class thread extends Thread {
        List<YulantuBean> asdf;


        @Override
        public void run() {
            super.run();
            if (getName() == "xiao") {
                try {
                    for (YulantuBean i : asdf) {
                        URL url = new URL(i.getXiaoyulantu());
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        InputStream is = connection.getInputStream();
                        Message message = new Message();
                        message.obj = BitmapFactory.decodeStream(is);
                        handler.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }}

        class Thead extends Thread {
            @Override
            public void run() {
                try {
                    Log.d("Inner", "run:" + url);
                    Document document = Jsoup.connect(url).get();
                    Document html = Jsoup.parse(document.toString());
                    Message message1 = handler.obtainMessage();
                    Message message2 = handler.obtainMessage();
                    Message message3 = handler.obtainMessage();
                    message1.what = 1;
                    message2.what = 2;
                    message3.what = 3;
                    message1.obj = Source.getInnerName(html);
                    message2.obj = Source.getTeacherIMG(html);
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





