package com.lixue.aibei.universaladapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import com.lixue.aibei.universaladapter.data.ExpandDataManager;
import com.lixue.aibei.universaladapter.demo.DemoMode;
import com.lixue.aibei.universaladapter.item.ExpandUserItem;
import com.lixue.aibei.universaladapterlib.UniversalExpandaleAdapter;
import com.lixue.aibei.universaladapterlib.item.AdapterItem;
import com.lixue.aibei.universaladapterlib.item.ExpandableApdaterItem;

import java.util.List;

public class ExpandableListViewTestActivity extends AppCompatActivity {
    private ExpandableListView expandableListView;
    private UniversalExpandaleAdapter universalExpandaleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_list_view_test);
        initView();
        initData();
    }

    private void initView(){
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
    }
    private void initData(){
        final List<DemoMode> data = ExpandDataManager.loadGroupData(getBaseContext());
        List<List<DemoMode>> data1 = ExpandDataManager.loadChildData(getBaseContext());
        expandableListView.setAdapter(test01(data,data1));
        expandableListView.expandGroup(0);//默认展开第一组
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                for (int i = 0; i < universalExpandaleAdapter.getGroupCount(); i++) {
                    if (groupPosition != i) {
                        expandableListView.collapseGroup(i);
                    }
                }
            }
        });
        expandableListView.setCacheColorHint(0);
    }

    private UniversalExpandaleAdapter<DemoMode> test01(List<DemoMode> groups,List<List<DemoMode>> childs){
        universalExpandaleAdapter = new UniversalExpandaleAdapter<DemoMode>(groups,childs){

           @NonNull
           @Override
           public AdapterItem createItem(Object type) {
               return null;
           }

           @Override
           public ExpandableApdaterItem createItems(Object type) {
               return new ExpandUserItem();
           }
       };
        return universalExpandaleAdapter;
    }
}
