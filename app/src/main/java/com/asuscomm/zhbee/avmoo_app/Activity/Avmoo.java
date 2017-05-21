package com.asuscomm.zhbee.avmoo_app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import com.asuscomm.zhbee.avmoo_app.Fragment.MaOrManFragment;
import com.asuscomm.zhbee.avmoo_app.R;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;


public class Avmoo extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_search:
                startActivity(new Intent(Avmoo.this, SearchActivity.class));
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.app_bar_avmoo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.all_tablayout);
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        ViewPager viewPager = (ViewPager) findViewById(R.id.toolbar_viewPager);

        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        MaOrManFragment teacherFragment = MaOrManFragment.newInstance(MaOrManFragment.TEACHER);
        MaOrManFragment allFragment = MaOrManFragment.newInstance(MaOrManFragment.ALL);
        MaOrManFragment categoryFragment = MaOrManFragment.newInstance(MaOrManFragment.CATEGORY);
        fragmentArrayList.add(categoryFragment);
        fragmentArrayList.add(allFragment);
        fragmentArrayList.add(teacherFragment);

        ToolbarPagerAdapter toolbarPagerAdapter = new ToolbarPagerAdapter(getSupportFragmentManager(), fragmentArrayList);
        viewPager.setAdapter(toolbarPagerAdapter);
        viewPager.setCurrentItem(1);
        tabLayout.setupWithViewPager(viewPager);
        TabLayout.Tab categorytap = tabLayout.getTabAt(0);
        TabLayout.Tab alltap = tabLayout.getTabAt(1);
        TabLayout.Tab teachertap = tabLayout.getTabAt(2);
        assert categorytap != null;
        categorytap.setIcon(R.mipmap.category);
        assert alltap != null;
        alltap.setIcon(R.mipmap.home);
        assert teachertap != null;
        teachertap.setIcon(R.mipmap.man);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    class ToolbarPagerAdapter extends FragmentPagerAdapter {


        private ArrayList<Fragment> fragmentArrayList;

        public ToolbarPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentArrayList) {
            super(fm);
            this.fragmentArrayList = fragmentArrayList;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentArrayList.get(position);
        }

        @Override
        public int getCount() {
            return 3;
        }

    }
}

