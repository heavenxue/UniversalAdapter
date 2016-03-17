package com.lixue.aibei.universaladapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lixue.aibei.universaladapter.data.DataManager;
import com.lixue.aibei.universaladapter.demo.DemoMode;
import com.lixue.aibei.universaladapter.item.UserItem;
import com.lixue.aibei.universaladapterlib.UniversalPagerAdapter;
import com.lixue.aibei.universaladapterlib.item.AdapterItem;

import java.util.List;

public class ViewPagerTestActivity extends AppCompatActivity {
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_test);
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
        viewPager = (ViewPager) findViewById(R.id.viewpager);
    }

    private void initData(){
        final List<DemoMode> data = DataManager.loadData(getBaseContext());
        viewPager.setAdapter(test01(data));
    }

    /**
     * 正常加载
     */
    private UniversalPagerAdapter<DemoMode> test01(List<DemoMode> data) {
        return new UniversalPagerAdapter<DemoMode>(data) {

            @Override
            public Object getItemType(DemoMode demoModel) {
                return demoModel.type;
            }

            @NonNull
            @Override
            public AdapterItem createItem(Object type) {
               return new UserItem();
            }
        };
    }

    /**
     * 懒加载
     */
    private UniversalPagerAdapter<DemoMode> test02(List<DemoMode> data) {
        return new UniversalPagerAdapter<DemoMode>(data, true) {

            @Override
            public Object getItemType(DemoMode demoModel) {
                return demoModel.type;
            }

            @NonNull
            @Override
            public AdapterItem createItem(Object type) {
                return new UserItem();
            }

        };
    }

}
