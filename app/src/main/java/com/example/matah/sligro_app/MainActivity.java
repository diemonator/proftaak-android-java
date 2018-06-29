package com.example.matah.sligro_app;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.Iconics;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.mikepenz.iconics.context.IconicsLayoutInflater2;

import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    private NotificationCompat.Builder notification;
    private static final int uniqueID = 45612;
    private static final String client_id = "12314";
    private android.support.v4.app.Fragment fragment;
    private MaterialSearchView searchView;
    public TabLayout tabs;
    public ViewPager viewpager;
    private ProgressBar proggress;
    private ListView listView;
    private Activity a;
    private ArrayList<NewsFragment> mFragment = null;
    private ArrayList<DownloadNews> mDownloadNews = null;
    private boolean fragOn = true;
    private ListNewsAdapter listOrigin;
    private boolean onetime = true;
    ListView view;
    ListNewsAdapter OriginItem;
    private NewsFragment newsFramenterino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        TextView test = (TextView) findViewById(R.id.testTV);
        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME);
//        test.setTextSize(getResources().getDimension(R.dimen.fa_size_icon_large));
//        test.setText(getString(R.string.fa_icon_search));
//        test.setTextSize(getResources().getDimension(R.dimen.fab_margin));
//        test.setText(getString(R.string.fa_icon_github_sign));
        a = this;
        FontManager.markAsIconContainer(findViewById(R.id.icons_container), iconFont);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        listView = (ListView) findViewById(R.id.listNews);
//        proggress = (ProgressBar) findViewById(R.id.loader);
//        viewPager = (ViewPager) findViewById(R.id.viewpager);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //search engine

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();

        View header = navigationView.getHeaderView(0);

        TextView txt_email_nav = (TextView) header.findViewById(R.id.nav_bar_email);
        txt_email_nav.setText(mAuth.getCurrentUser().getEmail());
        fragment = new NewsFragment();
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.content_frame, fragment);
        tx.commit();
        navigationView.setCheckedItem(R.id.nav_news);

        searchView = (MaterialSearchView) findViewById(R.id.search_view);

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (onetime) {
                    tabs = (TabLayout) findViewById(R.id.result_tabs);
                    for (int z = 0; z < tabs.getTabCount(); z++) {
                        if (tabs.getTabAt(z).isSelected()) {
                            view = ((NewsFragment) fragment).transactionFragment.get(z).listNews;
                            OriginItem = (ListNewsAdapter) view.getAdapter();
                            onetime = false;
                        }
                    }
                }

                if (newText != null && !newText.isEmpty()) {
                    try {
                        listOrigin = new ListNewsAdapter(a, new ArrayList<HashMap<String, String>>());
                        ListNewsAdapter list = new ListNewsAdapter(a, listOrigin.data);
                        int x = OriginItem.data.size();
                        //  LinearLayout v = null;
                        for (int i = 0; i < x; i++) {
                            String s = OriginItem.data.get(i).get("title");
                            if (s.contains(newText)) {
                                HashMap<String, String> holder = OriginItem.data.get(i);
                                list.data.add(holder);
                            }
                        }
                        listOrigin.setData(list.data);
                        view.setAdapter(listOrigin);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    view.setAdapter(OriginItem);
                    onetime = true;
                }
                return false;
            }
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Class fragmentClass = null;
        if (id == R.id.nav_home) {
            fragmentClass = TestsFragment.class;
        } else if (id == R.id.nav_rule_of_thumb) {
            fragmentClass = ThumbFragment.class;
        } else if (id == R.id.nav_news) {
            fragmentClass = NewsFragment.class;
        } else if (id == R.id.nav_chat) {
            Intent intent = new Intent(this, chat.class);
            startActivity(intent);
        } else if (id == R.id.nav_guide) {
            fragmentClass = GuideFragment.class;
        } else if (id == R.id.nav_logOut) {
            mAuth.signOut();
            NavUtils.navigateUpFromSameTask(this);
        }
        try {
            if (fragmentClass != null) {
                fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }


}