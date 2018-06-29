package com.example.matah.sligro_app;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by matah on 18/01/08.
 */

public class TabsFragment extends Fragment {
    public DownloadNews newsTask;
    public ListView listNews;
    public static int counter;
    private ArrayList<TabsFragment> transactionList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news, container, false);
        String url_request = this.getArguments().getString("url");
        ProgressBar p = (ProgressBar) view.findViewById(R.id.loader);
        listNews = (ListView) view.findViewById(R.id.listNews);
        if (Function.isNetworkAvailable(getContext())) {
            newsTask = new DownloadNews(getContext(), getActivity(),p,listNews,url_request);
            newsTask.execute();
        } else {
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }
        return view;
    }

    public ListView getDownloads(){
        return listNews;
    }
    public void getList()
    {
        counter++;
        Bundle args = new Bundle();
        try {
            transactionList.add(this);
            args.putSerializable("fragmentTabs" + counter, transactionList);
            this.setArguments(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
