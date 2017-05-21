package com.asuscomm.zhbee.avmoo_app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.asuscomm.zhbee.avmoo_app.Activity.InnerActivity;
import com.asuscomm.zhbee.avmoo_app.Bean.AllBean;
import com.asuscomm.zhbee.avmoo_app.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bee on 2017/5/14 0014.
 */
public class HeaderBottomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<AllBean> beanList;
    private ArrayList<String> teacherInformation;
    private String teacher_img;
    private Context context;

    public HeaderBottomAdapter(List<AllBean> beanList, ArrayList<String> teacherInformation, String teacher_img, Context context) {
        this.beanList = beanList;
        this.teacherInformation = teacherInformation;
        this.teacher_img = teacher_img;
        this.context = context;
    }

    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_TYPE_HEADER;
        } else return ITEM_TYPE_CONTENT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == ITEM_TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header, parent, false);
            return new HeaderViewHolder(view);
        } else if (viewType == ITEM_TYPE_CONTENT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_all_item, parent, false);
            final ContentViewHolder holder = new ContentViewHolder(view);
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), InnerActivity.class);
                    intent.putExtra("ALLURL", beanList.get(holder.getAdapterPosition()-1).getUrl());
                    intent.putExtra("FANHAO", beanList.get(holder.getAdapterPosition()-1).getFanhao());
                    view.getContext().startActivity(intent);
                }
            });
            return holder;
        }
        return  null;
    }


    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if ( layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams && holder.getLayoutPosition() == 0){
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
            p.setFullSpan(true);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM_TYPE_HEADER) {
            if (teacherInformation.size() > 0) {
                for (String i : teacherInformation) {
                    TextView textView = new TextView(context);
                    textView.setText(i);
                    textView.setGravity(Gravity.CENTER_HORIZONTAL);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(0, 3, 0, 3);
                    ((HeaderViewHolder) holder).linearLayout.addView(textView, lp);
                }
            } else {
                TextView textView = new TextView(context);
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
                textView.setText("没有详细信息");
                ((HeaderViewHolder) holder).linearLayout.addView(textView);
            }
            ((HeaderViewHolder) holder).simpleDraweeView.setImageURI(teacher_img);
        } else if (holder instanceof ContentViewHolder) {
            Log.d("HeaderBottomAdapter", "beanList.size():" + beanList.size());
            ((ContentViewHolder) holder).fanhao.setText(beanList.get(position -1).getFanhao());
            ((ContentViewHolder) holder).title.setText(beanList.get(position-1).getTitle());
            ((ContentViewHolder) holder).img.setImageURI(beanList.get(position-1).getImgurl());
        }
    }



    @Override
    public int getItemCount() {
        return beanList.size() + 1;
    }


    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView simpleDraweeView;
        View view;
        LinearLayout linearLayout;

        HeaderViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            linearLayout = (LinearLayout) itemView.findViewById(R.id.header_layout);
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.teacher_inner_img);


        }
    }

    private class ContentViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView img;
        TextView title;
        TextView fanhao;
        View view;

        ContentViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            img = (SimpleDraweeView) itemView.findViewById(R.id.thumbnail);
            title = (TextView) itemView.findViewById(R.id.TTT);
            fanhao = (TextView) itemView.findViewById(R.id.fanhao);
        }
    }
}
