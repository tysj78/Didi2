package com.naruto.didi2.activity.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.naruto.didi2.R;
import com.naruto.didi2.bean.DPoint;
import com.naruto.didi2.util.AppUtil;
import com.naruto.didi2.util.LogUtils;

public class ScrollActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
        initView();
    }

    private void initView() {
        bt_object = (Button) findViewById(R.id.bt_object);

        bt_object.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_object:
                String right = AppUtil.getInstance().right(116.405285, 39.904989, 100);
                String top = AppUtil.getInstance().top(116.405285, 39.904989, 50);
                LogUtils.e("平移后位置：" + right + "=" + top);
                DPoint diagonal = AppUtil.getInstance().diagonal(116.405285, 39.904989, 100, 50);
                LogUtils.e("平移后位置：" + diagonal.toString());
                break;
        }
    }

    //经 纵 == 纬 横
    void calculate(int jin, int wei, int width, int height) {
        //计算出对角线点坐标
    }
}
