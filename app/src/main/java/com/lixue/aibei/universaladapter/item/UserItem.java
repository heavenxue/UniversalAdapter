package com.lixue.aibei.universaladapter.item;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lixue.aibei.universaladapter.R;
import com.lixue.aibei.universaladapter.demo.DemoMode;
import com.lixue.aibei.universaladapterlib.item.AdapterItem;

/**
 * Created by Administrator on 2016/3/15.
 */
public class UserItem implements AdapterItem<DemoMode> {
    private int mOldImageUrl;
    private int mPostion;

    @Override
    public int getLayoutResId() {
        return R.layout.list_item_user;
    }
    TextView tv;
    TextView tv1;
    TextView tv2;
    TextView tv3;
    Button btn;
    ImageView img;

    @Override
    public void bindViews(View root) {
        tv = (TextView) root.findViewById(R.id.text_userListItem_name);
        tv1 = (TextView) root.findViewById(R.id.text_userListItem_age);
        tv2 = (TextView) root.findViewById(R.id.text_userListItem_job);
        tv3 = (TextView) root.findViewById(R.id.text_userListItem_sex);
        btn = (Button) root.findViewById(R.id.button_userListItem);
        img = (ImageView) root.findViewById(R.id.image_userListItem_head);
    }

    @Override
    public void setViews() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(btn.getContext(),"pos = " + mPostion,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void handleData(DemoMode demoMode, int postion) {
        mPostion = postion;
        tv.setText(demoMode.content);
        tv1.setText(demoMode.age);
        tv2.setText(demoMode.job);
        tv3.setText(demoMode.sex);
        btn.setText(demoMode.type); // 直接操作的是vm
        int drawableId = Integer.valueOf(demoMode.imgId);
        if (mOldImageUrl == 0 && mOldImageUrl != drawableId) {
            img.setImageResource(drawableId); // load image
            mOldImageUrl = drawableId;
        }

    }
}
