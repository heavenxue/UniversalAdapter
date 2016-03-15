package com.lixue.aibei.universaladapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.lixue.aibei.universaladapter.data.DataManager;
import com.lixue.aibei.universaladapter.demo.DemoMode;
import com.lixue.aibei.universaladapter.item.UserItem;
import com.lixue.aibei.universaladapterlib.UniversalAdapter;
import com.lixue.aibei.universaladapterlib.item.AdapterItem;

import java.util.List;

public class MainActivity extends AppCompatActivity{

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
        initData();
    }

    private void initView(){
        listview = (ListView) findViewById(R.id.listview);
    }

    private void initData(){
        final List<DemoMode> data = DataManager.loadData(getBaseContext());
        listview.setAdapter(test01(data));
    }

    /**
     * CommonPagerAdapter的类型和item的类型是一致的
     * 这里的都是{@link DemoMode}
     * <p/>
     * 一种类型的type
     */
    private UniversalAdapter<DemoMode> test01(List<DemoMode> data) {
        return new UniversalAdapter<DemoMode>(data) {

            @NonNull
            @Override
            public AdapterItem createItem(Object type) {
                // 如果就一种，那么直接return一种类型的item即可。
                return new UserItem();
            }
        };
    }
}
