package com.example.matah.sligro_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class GuideOverviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_overview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        Guide guide = (Guide) intent.getSerializableExtra("guide");
        TextView Title = (TextView) findViewById(R.id.titleGuide);
        TextView Content = (TextView) findViewById(R.id.contentGuide);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(guide.getImg());
        imageView.getLayoutParams().height = 200;
        imageView.getLayoutParams().width = 200;
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Title.setText(guide.getTitle());
        Content.setText(guide.getContent());
    }
}
