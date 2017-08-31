package com.asuscomm.zhbee.avmoo_app.Fragment;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asuscomm.zhbee.avmoo_app.Adapter.CategoryAdapter;
import com.asuscomm.zhbee.avmoo_app.Bean.CategoryBean;
import com.asuscomm.zhbee.avmoo_app.R;
import com.asuscomm.zhbee.avmoo_app.Source;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;




/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends BlankFragment {
    private CategoryFragmentHandler handler;
    RecyclerView categoryThemeItem;
    private View view;



    public CategoryFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(int maOrManTYPE) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("maOrManTYPE",maOrManTYPE);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            maOrManTYPE = getArguments().getInt("maOrManTYPE");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category, container, false);
        categoryThemeItem = (RecyclerView) view.findViewById(R.id.category_Theme_item);


        handler = new CategoryFragmentHandler();
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        CategoryFragmentThread thread = new CategoryFragmentThread();
        thread.start();
        StaggeredGridLayoutManager layoutManager6 = new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);
        categoryThemeItem.setLayoutManager(layoutManager6);
    }

    public class CategoryFragmentThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                String url = null;
                if (maOrManTYPE == MA){
                    url = "https://www.javbus.com/genre";
                }else if (maOrManTYPE == MAN){
                    url = "https://www.javbus.com/uncensored/genre";
                }
                Document document = Jsoup.connect(url).get();
                Message message = handler.obtainMessage();
                message.obj = Source.getCategory(document);
                handler.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class CategoryFragmentHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<CategoryBean> list = (List<CategoryBean>) msg.obj;
            List<CategoryBean> themeList = null;

            if (maOrManTYPE == MA){
                themeList = list;
            }
            if ( maOrManTYPE == MAN){
                themeList = list;
            }
            categoryThemeItem.setAdapter(new CategoryAdapter(themeList));

        }
    }






}
