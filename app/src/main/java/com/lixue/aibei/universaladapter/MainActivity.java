package com.lixue.aibei.universaladapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initView();
    }

    private void initView() {
        findViewById(R.id.list_view_btn).setOnClickListener(this);
        findViewById(R.id.rcv_btn).setOnClickListener(this);
        findViewById(R.id.viewpager_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Class clz = null;
        switch (v.getId()) {
            case R.id.list_view_btn:
                clz = ListViewTestActivity.class;
                break;
            case R.id.rcv_btn:
                clz = RcvTestActivity.class;
                break;
            case R.id.viewpager_btn:
                clz = ViewPagerTestActivity.class;
                break;
        }
        startActivity(new Intent(this, clz));
    }
}
