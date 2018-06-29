package com.example.matah.sligro_app;


import android.animation.TypeConverter;
import android.annotation.SuppressLint;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.Transaction;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {

    public final String[] NEWS_SOURCE = {"hacker-news", "bbc-news", "cnn","abc-news","google-news"}; ///
    public final String[] API_KEY = {"fbaad9f5814347a1b68e3e77634f8dfb", "fbaad9f5814347a1b68e3e77634f8dfb", "48972ae970154e9f948cd2b10ce9120d"}; // ### YOUE NEWS API HERE ### ///

    public final String[] NEWS_URLS_MAIN = {"https://newsapi.org/v2/top-headlines?sources=", "https://newsapi.org/v2/everything?sources="};
    public final String NEWS_URLS_KEYS = "&apiKey=";

    public ViewPager viewPager;
    public TabLayout tabs;

    public Fragment fragment;
    public Adapter adapter;
    public View view;
    public ArrayList<TabsFragment> transactionFragment ;
    public NewsFragment() {

    }

    public Adapter getAdapter() {
        return adapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);
        transactionFragment = new ArrayList<>();
        // Inflate the layout for this fragment
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        // Set Tabs inside Toolbar
        tabs = (TabLayout) view.findViewById(R.id.result_tabs);
        tabs.setupWithViewPager(viewPager);
        for (int i = 0; i < tabs.getTabCount(); i++) {
            TabLayout.Tab tab = tabs.getTabAt(i);
            tab.setText(adapter.getPageTitle(i));
        }


        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new Adapter(getChildFragmentManager());

        for (int i = 0; i < NEWS_SOURCE.length; i++) {
            String url = NEWS_URLS_MAIN[0] + NEWS_SOURCE[i] + NEWS_URLS_KEYS + API_KEY[0];
            Bundle args = new Bundle();
            args.putString("url", url);
            fragment = new TabsFragment();
            adapter.addFragment(fragment, NEWS_SOURCE[i]);
            fragment.setArguments(args);
            transactionFragment.add((TabsFragment) fragment);
        }
        viewPager.setAdapter(adapter);
    }

    public static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }


        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        public List<Fragment> getListFragment() {
            return mFragmentList;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }
}
