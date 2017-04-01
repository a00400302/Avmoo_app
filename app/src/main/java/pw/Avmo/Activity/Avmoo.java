package pw.Avmo.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.drawee.backends.pipeline.Fresco;

import pw.Avmo.Fragment.AboutFragment;
import pw.Avmo.Fragment.AllFragment;
import pw.Avmo.Fragment.CategoryFragment;
import pw.Avmo.Fragment.FavoritesFragment;
import pw.Avmo.Fragment.TeacherFragment;
import pw.Avmo.R;

public class Avmoo extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FragmentManager fragmentManager;
    private TeacherFragment teacherFragment = new TeacherFragment();
    private CategoryFragment categoryFragment = new CategoryFragment();
    private AllFragment allFragment = new AllFragment();
    private AboutFragment aboutFragment = new AboutFragment();


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_search,menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.toolbar_search).getActionView();
        searchView.setQueryHint("嘀~  学生卡");
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return  true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.app_bar_avmoo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);





        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.FragmentInner, allFragment);
        fragmentTransaction.add(R.id.FragmentInner, teacherFragment);
        fragmentTransaction.add(R.id.FragmentInner, categoryFragment);
        fragmentTransaction.hide(categoryFragment).hide(teacherFragment).commit();

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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_all) {
            fragmentManager.beginTransaction()
                    .hide(categoryFragment)
                    .hide(teacherFragment)
                    .show(allFragment)
                    .commit();
        } else if (id == R.id.nav_teacher) {
                fragmentManager.beginTransaction()
                        .hide(categoryFragment)
                        .hide(allFragment)
                        .show(teacherFragment)
                        .commit();

        } else if (id == R.id.nav_category) {
                fragmentManager.beginTransaction()
                        .hide(allFragment)
                        .hide(teacherFragment)
                        .show(categoryFragment)
                        .commit();

        } else if (id == R.id.nav_share)

        {
        } else if (id == R.id.nav_about)

        {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        //super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //super.onRestoreInstanceState(savedInstanceState);
    }
}
