package com.example.matah.sligro_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import static android.provider.AlarmClock.EXTRA_MESSAGE;


/**
 * A simple {@link Fragment} subclass.
 */
public class GuideFragment extends Fragment {
    private ArrayList<Guide> guides;

    CustomAdapter customAdapter;
    public GuideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide, null);
        GridView gridview = (GridView) view.findViewById(R.id.gridView);
        customAdapter = new CustomAdapter(getContext());
        guides = customAdapter.getList();
        gridview.setAdapter(customAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent = new Intent(getActivity(), GuideOverviewActivity.class);
                intent.putExtra("guide", guides.get(position));
                startActivity(intent);
            }
        });

        return view;
    }

}
