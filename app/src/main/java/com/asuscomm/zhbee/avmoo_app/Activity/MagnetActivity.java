package com.asuscomm.zhbee.avmoo_app.Activity;


import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.asuscomm.zhbee.avmoo_app.Bean.MagnetBean;
import com.asuscomm.zhbee.avmoo_app.R;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;



public class MagnetActivity extends AppCompatActivity {
    private String fanhou;
    private MagnetHandler magnetHandler;
    private RecyclerView listview;
    private TextView title;

//    Document document;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnet);
        title = (TextView) MagnetActivity.this.findViewById(R.id.magnet_title);
        if (getIntent().getIntExtra("back",0) == 0){
            Toast.makeText(this, "暂时没有在线资源 试试用磁力链接吧", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "努力加载中", Toast.LENGTH_SHORT).show();
        }
        listview = (RecyclerView) findViewById(R.id.magnet_list);
        fanhou = getIntent().getStringExtra("keyword");
        magnetHandler = new MagnetHandler();
        MagnetThread thread = new MagnetThread();
        thread.start();


        listview.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, 0, 0, 1);
            }
        });
        listview.setLayoutManager(new LinearLayoutManager(this));
    }


    class MagnetThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                Document test = Jsoup.connect("http://btdb.in/q/" + fanhou).get();
                Elements elements = test.select("li.search-ret-item");
                ArrayList<MagnetBean> list = new ArrayList<>();
                for (Element e : elements) {
                    MagnetBean bean = new MagnetBean();
                    bean.setText(e.select(" h2 >a").attr("title"));
                    bean.setSize(e.select(" div.item-meta-info > span").get(0).text());
                    bean.setTime(e.select(" div.item-meta-info > span").get(2).text());
                    bean.setUrl(e.select("li.search-ret-item > div.item-meta-info > a").attr("href"));
                    list.add(bean);
                }
                Message message = magnetHandler.obtainMessage();
                message.obj = list;
                magnetHandler.sendMessage(message);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class MagnetHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ArrayList<MagnetBean> been = (ArrayList<MagnetBean>) msg.obj;
            if (been.size() == 0) {
                Toast.makeText(MagnetActivity.this, "妈蛋! 没有找到钥匙", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                listview.setAdapter(new MagentRecycleViewAdpater(been));
                title.setVisibility(View.VISIBLE);
            }
        }
    }


    class MagentRecycleViewAdpater extends RecyclerView.Adapter<MagentRecycleViewAdpater.MagentViewHolder> {
        ArrayList<MagnetBean> list;

        public MagentRecycleViewAdpater(ArrayList<MagnetBean> list) {
            this.list = list;
        }

        @Override
        public MagentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_magnet_item, parent, false);
            MagentViewHolder holder = new MagentViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MagentViewHolder holder, final int position) {
            holder.text.setText(list.get(position).getText());
            holder.size.setText(list.get(position).getSize());
            holder.time.setText(list.get(position).getTime());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setText(list.get(position).getUrl());
                    Toast.makeText(MagnetActivity.this, "复制成功  开车~", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MagentViewHolder extends RecyclerView.ViewHolder {
            TextView text;
            TextView size;
            TextView time;

            public MagentViewHolder(View itemView) {
                super(itemView);
                text = (TextView) itemView.findViewById(R.id.magnet_text);
                size = (TextView) itemView.findViewById(R.id.magnet_size);
                time = (TextView) itemView.findViewById(R.id.magnet_time);

            }
        }
    }
}
