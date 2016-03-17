package com.lixue.aibei.universaladapter.item;

import android.view.View;
import android.widget.TextView;

import com.lixue.aibei.universaladapter.R;
import com.lixue.aibei.universaladapter.demo.DemoMode;
import com.lixue.aibei.universaladapterlib.item.ExpandableApdaterItem;

/**
 * Created by Administrator on 2016/3/17.
 */
public class ExpandUserItem implements ExpandableApdaterItem<DemoMode> {
    @Override
    public int getLayoutResId() {
        return R.layout.list_item_child;
    }

    TextView tv_name;
    TextView tv_group_name;
    @Override
    public void bindViews(View root) {
        tv_name = (TextView) root.findViewById(R.id.text_child_title);
    }

    @Override
    public void setViews() {

    }

    @Override
    public void handleData(DemoMode demoMode, int postion) {
        tv_name.setText(demoMode.content);
    }

    @Override
    public int getLayoutGroupResId() {
        return R.layout.list_item_group;
    }

    @Override
    public void bindGroupViews(View root) {
        tv_group_name = (TextView) root.findViewById(R.id.text_group_title);
    }

    @Override
    public void handleGroupData(DemoMode demoMode, int position) {
        tv_group_name.setText(demoMode.content);
    }
}
