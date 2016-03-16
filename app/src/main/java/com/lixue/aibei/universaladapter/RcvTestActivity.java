package com.lixue.aibei.universaladapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lixue.aibei.universaladapter.data.DataManager;
import com.lixue.aibei.universaladapter.demo.DemoMode;
import com.lixue.aibei.universaladapter.item.UserItem;
import com.lixue.aibei.universaladapterlib.UniversalRcvAdapter;
import com.lixue.aibei.universaladapterlib.item.AdapterItem;
import com.lixue.aibei.universaladapterlib.util.IAdapter;

import java.util.List;

public class RcvTestActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rcv_test);
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
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    private void initData(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setRecycleChildrenOnDetach(true);
        recyclerView.setLayoutManager(layoutManager);

        // 放一个默认数据
        recyclerView.setAdapter(getAdapter(null));

        // 现在得到数据
        final List<DemoMode> data = DataManager.loadData(getBaseContext());
        ((IAdapter) recyclerView.getAdapter()).setData(data); // 设置新的数据
        recyclerView.getAdapter().notifyDataSetChanged(); // 通知数据刷新
    }

    /**
     * CommonAdapter的类型和item的类型是一致的
     * 这里的都是{@link com.lixue.aibei.universaladapter.demo.DemoMode}
     * <p/>
     * 多种类型的type
     */
    private UniversalRcvAdapter<DemoMode> getAdapter(List<DemoMode> data) {
        return new UniversalRcvAdapter<DemoMode>(data) {

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
