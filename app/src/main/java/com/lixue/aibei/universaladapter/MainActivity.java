package com.lixue.aibei.universaladapter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.lixue.aibei.universaladapter.bean.Game;
import com.lixue.aibei.universaladapter.bean.User;
import com.lixue.aibei.universaladapterlib.OnLoadMoreListener;
import com.lixue.aibei.universaladapterlib.UniversalAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnLoadMoreListener{
    private int nextStart;
    private int size = 20;

    private ListView listview;
    private UniversalAdapter adapter;

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
        if(adapter != null){
            listview.setAdapter(adapter);
        }else{
            loadData();
        }
    }
    private void loadData(){
        new AsyncTask<String, String, List<Object>>(){

            @Override
            protected List<Object> doInBackground(String... params) {
                int index = 0;
                List<Object> dataList = new ArrayList<Object>(size);
                boolean userStatus = true;
                boolean gameStatus = true;
                while (index < size){
                    if(index % 2 == 0){
                        User user = new User();
                        user.headResId = R.mipmap.ic_launcher;
                        user.name = "王大卫"+(index+nextStart+1);
                        user.sex = userStatus?"男":"女";
                        user.age = ""+(index+nextStart+1);
                        user.job = "实施工程师";
                        user.monthly = ""+9000+index+nextStart+1;
                        dataList.add(user);
                        userStatus = !userStatus;
                    }else{
                        Game game = new Game();
                        game.iconResId = R.mipmap.ic_launcher;
                        game.name = "英雄联盟"+(index+nextStart+1);
                        game.like = gameStatus?"不喜欢":"喜欢";
                        dataList.add(game);
                        gameStatus = !gameStatus;
                    }
                    index++;
                }
                if(nextStart != 0){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return dataList;
            }

            @Override
            protected void onPostExecute(List<Object> objects) {
                nextStart += size;
                if(adapter == null){
                    adapter = new UniversalAdapter(objects);
//                    adapter.addItemFactory(new UserListItemFactory(getActivity().getBaseContext()));
//                    adapter.addItemFactory(new GameListItemFactory(getActivity().getBaseContext()));
//                    if(nextStart < 100){
//                        adapter.setEnableLoadMore(new LoadMoreListItemFactory(MainActivity.this));
//                    }
                    listview.setAdapter(adapter);
                }else{
                    adapter.loadMoreFinished();
                    if(nextStart == 100){
                        adapter.loadMoreEnd();
                    }
                    adapter.append(objects);
                }
            }
        }.execute("");
    }

    @Override
    public void onLoadMore(UniversalAdapter adapter) {

    }
}
