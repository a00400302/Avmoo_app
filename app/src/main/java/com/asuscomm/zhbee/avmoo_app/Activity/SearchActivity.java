package com.asuscomm.zhbee.avmoo_app.Activity;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import com.asuscomm.zhbee.avmoo_app.Fragment.MaOrManFragment;
import com.asuscomm.zhbee.avmoo_app.R;


public class SearchActivity extends BaseActivity {
    private String keyWord;
    private SharedPreferences sharedPreferences;
    private SearchView searchView;
    private LinearLayout layout;
    private FrameLayout frameLayout;
    private ArrayList<String> lll;
    private HistoryAdpater adpater;
    private MaOrManFragment maOrManFragment;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_search, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_item_search);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setIconifiedByDefault(true);
        searchView.setIconified(false);
        searchView.setFocusable(true);
        searchView.requestFocusFromTouch();
        searchView.setQueryHint("嘀~   学生卡");
//        if (searchView.isIconified()) {
//
//        }
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frameLayout.setVisibility(View.GONE);
                layout.setVisibility(View.VISIBLE);
//                adpater.notifyItemChanged(0);
                if (maOrManFragment != null) {
                    getSupportFragmentManager().beginTransaction().remove(maOrManFragment).commit();
                }
                getSupportActionBar().setTitle("搜索");
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onQueryTextSubmit(String query) {
                frameLayout.setVisibility(View.VISIBLE);
                layout.setVisibility(View.GONE);
                keyWord = query;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String ifkeyword = sharedPreferences.getString(keyWord, "");
                if (!Objects.equals(ifkeyword, keyWord)) {
                    editor.putString(keyWord, keyWord);
                    lll.add(keyWord);
                    Collections.reverse(lll);
                    adpater.notifyItemChanged(0);
                    editor.apply();
                }


                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                searchView.clearFocus();
                searchView.onActionViewCollapsed();
                getSupportActionBar().setTitle("搜索   -" + keyWord + "-   结果");
                maOrManFragment = MaOrManFragment.newInstance(MaOrManFragment.SEARCH, keyWord);
                transaction.add(R.id.search_layout, maOrManFragment).commit();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("搜索");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        frameLayout = (FrameLayout) findViewById(R.id.search_layout);
        layout = (LinearLayout) findViewById(R.id.history_layout);
        sharedPreferences = getSharedPreferences("history", MODE_PRIVATE);
        Map<String, ?> all = sharedPreferences.getAll();
        Object[] keys = all.keySet().toArray();
        lll = new ArrayList<String>();

        for (int i = 0; i < keys.length; i++) {
            lll.add((String) all.get(keys[i]));
        }
        final RecyclerView listView = (RecyclerView) findViewById(R.id.history_list);
        adpater = new HistoryAdpater(lll);
        listView.setAdapter(adpater);

        listView.setLayoutManager(new GridLayoutManager(this, 3));

        Button button = (Button) findViewById(R.id.clean_history_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.edit().clear().apply();
                lll.clear();
                adpater.notifyDataSetChanged();
            }
        });


    }


    class HistoryAdpater extends RecyclerView.Adapter<HistoryAdpater.HistoryViewHolder> {
        ArrayList<String> list;


        public HistoryAdpater(ArrayList<String> lll) {
            this.list = lll;
        }

        @Override
        public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_history_item, parent, false);
            return new HistoryViewHolder(view);
        }

        @Override
        public void onBindViewHolder(HistoryViewHolder holder, final int position) {
            if (list.size() != 0) {
                holder.t.setText(list.get(position));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        frameLayout.setVisibility(View.VISIBLE);
                        layout.setVisibility(View.GONE);
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        searchView.clearFocus();
                        searchView.onActionViewCollapsed();
                        getSupportActionBar().setTitle("搜索   -" + list.get(position) + "-   结果");
                        MaOrManFragment fragment = MaOrManFragment.newInstance(MaOrManFragment.SEARCH, list.get(position));
                        transaction.add(R.id.search_layout, fragment).commit();
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class HistoryViewHolder extends RecyclerView.ViewHolder {

            private final TextView t;

            public HistoryViewHolder(View itemView) {
                super(itemView);
                t = (TextView) itemView.findViewById(R.id.history_text);
            }
        }
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


}
