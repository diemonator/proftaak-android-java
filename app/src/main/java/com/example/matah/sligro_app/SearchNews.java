package com.example.matah.sligro_app;

/**
 * Created by matah on 18/01/08.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchNews extends AsyncTask<String, Void, String> {



    public static final String KEY_AUTHOR = "author";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_URLTOIMAGE = "urlToImage";
    public static final String KEY_URL = "url";
    public static final String KEY_PUBLISHEDAT = "publishedAt";
    public ArrayList<HashMap<String, String>> dataList;
    private ProgressBar loader;
    private ListView listNews;

    private String request;

    private Context currentContext;
    private Activity currentActivity;


    public SearchNews(Context c, Activity a, ProgressBar loader, ListView listNews, String request)
    {
        currentActivity = a;
        currentContext = c;
        this.listNews = listNews;
        this.loader = loader;
        this.request = request;
        dataList = new ArrayList<HashMap<String, String>>();
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }
    protected String doInBackground(String... args) {
        String xml = "";

        String urlParameters = "";
        xml = Function.excuteGet(request, urlParameters);
        return  xml;
    }
    @Override
    protected void onPostExecute(String xml) {

        if(xml.length()>10){ // Just checking if not empty

            try {
                JSONObject jsonResponse = new JSONObject(xml);
                JSONArray jsonArray = jsonResponse.optJSONArray("articles");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();
                    if (true)
                    {
                        map.put(KEY_AUTHOR, jsonObject.optString(KEY_AUTHOR).toString());
                        map.put(KEY_TITLE, jsonObject.optString(KEY_TITLE).toString());
                        map.put(KEY_DESCRIPTION, jsonObject.optString(KEY_DESCRIPTION).toString());
                        map.put(KEY_URL, jsonObject.optString(KEY_URL).toString());
                        map.put(KEY_URLTOIMAGE, jsonObject.optString(KEY_URLTOIMAGE).toString());
                        map.put(KEY_PUBLISHEDAT, jsonObject.optString(KEY_PUBLISHEDAT).toString());
                        dataList.add(map);
                    }
                }
            } catch (JSONException e) {
                Toast.makeText(currentContext, "Unexpected error", Toast.LENGTH_SHORT).show();
            }

            ListNewsAdapter adapter = new ListNewsAdapter(currentActivity, dataList);
            listNews.setAdapter(adapter);

            listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Intent i = new Intent(currentActivity, DetailsActivity.class);
                    i.putExtra("url", dataList.get(+position).get(KEY_URL));
                    currentContext.startActivity(i);
                }
            });
            loader.setVisibility(View.GONE);
        }else{
            Toast.makeText(currentContext, "No news found", Toast.LENGTH_SHORT).show();
        }
    }
}
