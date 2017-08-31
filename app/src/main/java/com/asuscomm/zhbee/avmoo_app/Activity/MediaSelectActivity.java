package com.asuscomm.zhbee.avmoo_app.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.asuscomm.zhbee.avmoo_app.R;

public class MediaSelectActivity extends AppCompatActivity {


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_select);
        final String keyword = getIntent().getStringExtra("keyword");
        Button selectButton = (Button) findViewById(R.id.select_button);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaSelectActivity.this, MagnetActivity.class);
                intent.putExtra("keyword", keyword);
                intent.putExtra("back", 1);
                startActivity(intent);
                finish();
            }
        });
        String[] strings = new String[]{
                "http://59.120.24.228",
                "http://60.251.90.246",
                "http://59.124.108.74",
                "http://60.251.108.82",
                "http://210.59.195.242",
                "http://60.251.2.50",
                "http://61.218.20.194",
                "http://59.120.38.82",
                "http://59.120.24.229",
                "http://60.251.90.245",
                "http://59.120.128.98",
                "http://60.251.108.182",
                "http://59.124.16.109",
                "http://61.218.1.151",
                "http://60.250.179.229",
                "http://60.251.90.246"};
        RecyclerView selectList = (RecyclerView) findViewById(R.id.Select_list);
        selectList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, 0, 0, 1);
            }
        });
        selectList.setAdapter(new SelectAdapter(strings));
        selectList.setLayoutManager(new LinearLayoutManager(MediaSelectActivity.this));


    }


    class SelectAdapter extends RecyclerView.Adapter<SelectAdapter.SelectViewHolder> {
        String[] strings;

        public SelectAdapter(String[] strings) {
            this.strings = strings;
        }

        @Override
        public SelectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recucleview_select_item, parent, false);
            return new SelectViewHolder(view);
        }

        @Override
        public void onBindViewHolder(SelectViewHolder holder, final int position) {
            holder.textView.setText(strings[position]);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent openVideo = new Intent(Intent.ACTION_VIEW);
                    openVideo.setDataAndType(Uri.parse(strings[position] + getIntent().getStringExtra("key")), "video/*");
                    startActivity(openVideo);
                }
            });
        }

        @Override
        public int getItemCount() {
            return strings.length;
        }

        class SelectViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            public SelectViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.select_text);
            }
        }
    }
}
