package com.asuscomm.zhbee.avmoo_app.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.asuscomm.zhbee.avmoo_app.R;

import java.util.ArrayList;




public class MaOrManFragment extends BlankFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final int CATEGORY = 0;
    public static final int ALL = 1;
    public static final int TEACHER = 2;
    public static final int SEARCH = 3;


    // TODO: Rename and change types of parameters
    private int TYPE;
    private String keyword;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    // TODO: Rename and change types and number of parameters
    public static MaOrManFragment newInstance(int TYPE, String keyword) {
        MaOrManFragment fragment = new MaOrManFragment();
        Bundle args = new Bundle();
        args.putInt("TYPE", TYPE);
        args.putString("keyword", keyword);
        fragment.setArguments(args);
        return fragment;
    }

    public static MaOrManFragment newInstance(int TYPE) {
        MaOrManFragment fragment = new MaOrManFragment();
        Bundle args = new Bundle();
        args.putInt("TYPE", TYPE);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            TYPE = getArguments().getInt("TYPE");
            if (TYPE == SEARCH) {
                keyword = getArguments().getString("keyword");
            }
        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_ma_or_man, container, false);
            viewPager = (ViewPager) rootView.findViewById(R.id.ma_or_man_viewpager);
            tabLayout = (TabLayout) rootView.findViewById(R.id.ma_or_man_tablayout);
        }

        return rootView;
    }


    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
            if (tabLayout.getTabCount() == 0){
                if (TYPE == ALL) {
                    ArrayList<Fragment> fragments = new ArrayList<>();
                    AllFragment maAllFragment = AllFragment.newInstance(AllFragment.MA);
                    AllFragment manAllFragment = AllFragment.newInstance(AllFragment.MAN);
                    AllFragment ouAllFragment = AllFragment.newInstance(AllFragment.OU);
                    fragments.add(maAllFragment);
                    fragments.add(manAllFragment);
                    fragments.add(ouAllFragment);
                    tabLayout.addTab(tabLayout.newTab().setText("有码"));
                    tabLayout.addTab(tabLayout.newTab().setText("无码"));
                    tabLayout.addTab(tabLayout.newTab().setText("欧美"));
                    viewPager.setAdapter(new MaOrManViewPagerAdpater(getChildFragmentManager(), fragments, ALL));
                    tabLayout.setupWithViewPager(viewPager);
                    TabLayout.Tab ma = tabLayout.getTabAt(0);
                    TabLayout.Tab man = tabLayout.getTabAt(1);
                    TabLayout.Tab ou = tabLayout.getTabAt(2);
                    assert ma != null;
                    ma.setText("有码");
                    assert man != null;
                    man.setText("无码");
                    assert ou != null;
                    ou.setText("欧美");
                }
                if (TYPE == CATEGORY) {
                    ArrayList<Fragment> fragments = new ArrayList<>();
                    CategoryFragment maCategoryFragment = CategoryFragment.newInstance(CategoryFragment.MA);
                    CategoryFragment manCategoryFragment = CategoryFragment.newInstance(CategoryFragment.MAN);
                    fragments.add(maCategoryFragment);
                    fragments.add(manCategoryFragment);
                    tabLayout.addTab(tabLayout.newTab());
                    tabLayout.addTab(tabLayout.newTab());
                    viewPager.setAdapter(new MaOrManViewPagerAdpater(getChildFragmentManager(), fragments, CATEGORY));
                    viewPager.setCurrentItem(1);
                    tabLayout.setupWithViewPager(viewPager);
                    TabLayout.Tab ma = tabLayout.getTabAt(0);
                    TabLayout.Tab man = tabLayout.getTabAt(1);
                    assert ma != null;
                    ma.setText("有码");
                    assert man != null;
                    man.setText("无码");


                }
                if (TYPE == TEACHER) {
                    ArrayList<Fragment> fragments = new ArrayList<>();
                    TeacherFragment maTeacherFragment = TeacherFragment.newInstance(TeacherFragment.MA);
                    TeacherFragment manTeacherFragment = TeacherFragment.newInstance(TeacherFragment.MAN);
                    fragments.add(maTeacherFragment);
                    fragments.add(manTeacherFragment);
                    tabLayout.addTab(tabLayout.newTab().setText("有码"));
                    tabLayout.addTab(tabLayout.newTab().setText("无码"));
                    viewPager.setAdapter(new MaOrManViewPagerAdpater(getChildFragmentManager(), fragments, TEACHER));
                    tabLayout.setupWithViewPager(viewPager);
                    TabLayout.Tab ma = tabLayout.getTabAt(0);
                    TabLayout.Tab man = tabLayout.getTabAt(1);
                    assert ma != null;
                    ma.setText("有码");
                    assert man != null;
                    man.setText("无码");
                }
                if (TYPE == SEARCH) {
                    ArrayList<Fragment> fragments = new ArrayList<>();
                    SreachFragment maSreachFragment = SreachFragment.newInstance(TeacherFragment.MA, keyword);
                    SreachFragment manSreachFragment = SreachFragment.newInstance(TeacherFragment.MAN, keyword);
                    fragments.add(maSreachFragment);
                    fragments.add(manSreachFragment);
                    tabLayout.addTab(tabLayout.newTab().setText("有码"));
                    tabLayout.addTab(tabLayout.newTab().setText("无码"));
                    viewPager.setAdapter(new MaOrManViewPagerAdpater(getChildFragmentManager(), fragments, TEACHER));
                    tabLayout.setupWithViewPager(viewPager);
                    TabLayout.Tab ma = tabLayout.getTabAt(0);
                    TabLayout.Tab man = tabLayout.getTabAt(1);
                    assert ma != null;
                    ma.setText("有码");
                    assert man != null;
                    man.setText("无码");
                }
            }
        }
    }


    class MaOrManViewPagerAdpater extends FragmentPagerAdapter {
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
        }

        ArrayList<Fragment> fragments;
        int TYPE;


        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }


        public MaOrManViewPagerAdpater(FragmentManager fm, ArrayList<Fragment> fragments, int TYPE) {
            super(fm);
            this.fragments = fragments;
            this.TYPE = TYPE;
        }


        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            if (TYPE == ALL) {
                return 3;
            } else if (TYPE == TEACHER) {
                return 2;
            } else if (TYPE == CATEGORY) {
                return 2;
            }
            return 2;
        }
    }

//    on

}
