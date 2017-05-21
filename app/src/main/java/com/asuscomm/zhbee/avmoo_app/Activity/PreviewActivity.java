package com.asuscomm.zhbee.avmoo_app.Activity;


import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.asuscomm.zhbee.avmoo_app.Bean.YulantuBean;
import com.asuscomm.zhbee.avmoo_app.R;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;



public class PreviewActivity extends AppCompatActivity {

    private int location;
    private TextView yulanId;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        finish();
        return  true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_preview);
//        getSupportActionBar().hide();

        Bundle bundle = getIntent().getExtras();
        final ArrayList<YulantuBean> list = (ArrayList<YulantuBean>) bundle.getSerializable("DAURL");
        location = getIntent().getIntExtra("LOCATION",0);
        Log.d("PreviewActivity", "location:" + location);

        TextView yulanLength = (TextView) findViewById(R.id.yulan_length);
        String length = String.valueOf(list.size());
        yulanLength.setText(length);

        yulanId = (TextView) findViewById(R.id.yulan_id);
        yulanId.setText(String.valueOf(location +1));

        final ViewPager pager = (ViewPager) findViewById(R.id.yulan_pager);
        final ArrayList<View> views = new ArrayList<>();
        for (int i = 0; i < list.size(); i++){
            SimpleDraweeView simpleDraweeView = new SimpleDraweeView(this);
//            simpleDraweeView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            simpleDraweeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            simpleDraweeView.setImageURI(list.get(i).getDayulantu());
            GenericDraweeHierarchy genericDraweeHierarchy = new GenericDraweeHierarchyBuilder(simpleDraweeView.getResources())
                    .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER).build();
            simpleDraweeView.setHierarchy(genericDraweeHierarchy);


            views.add(simpleDraweeView);
        }

        PagerAdapter pagerAdapter = new PagerAdapter() {


            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
//                return super.instantiateItem(container, position);
                container.addView(views.get(position));

                return views.get(position);
            }



            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
//                super.destroyItem(container, position, object);
                container.removeView(views.get(position));
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        };

        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(location);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                yulanId.setText(String.valueOf(position+1));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
