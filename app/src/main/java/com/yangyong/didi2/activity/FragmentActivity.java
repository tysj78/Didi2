package com.yangyong.didi2.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.yangyong.didi2.R;
import com.yangyong.didi2.fragment.HomeFragment;
import com.yangyong.didi2.fragment.MyFragment;

public class FragmentActivity extends AppCompatActivity implements View.OnClickListener {

    private FragmentManager fragmentManager;
    private HomeFragment home;
    private MyFragment my;
    private FrameLayout fl_content;
    private Button bt_fg1;
    private Button bt_fg2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        initView();
        setFg();
    }

    private void setFg() {
        fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.fl_content, new MyFragment());
//        fragmentTransaction.commit();
    }

    /**
     * Fragment切换
     *
     * @param index
     */
    private void setChoiceItem(int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                if (home == null) {
                    home = new HomeFragment();
                    transaction.add(R.id.fl_content, home);
                } else {
                    transaction.show(home);
                }

                break;

            case 1:
                if (my == null) {
                    my = new MyFragment();
                    transaction.add(R.id.fl_content, my);
                } else {
                    transaction.show(my);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 隐藏片段
     *
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (home != null) {
            transaction.hide(home);
        }
        if (my != null) {
            transaction.hide(my);
        }
    }


    private void initView() {
        fl_content = (FrameLayout) findViewById(R.id.fl_content);
        bt_fg1 = (Button) findViewById(R.id.bt_fg1);
        bt_fg2 = (Button) findViewById(R.id.bt_fg2);

        bt_fg1.setOnClickListener(this);
        bt_fg2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_fg1:
                setChoiceItem(0);
                break;
            case R.id.bt_fg2:
                setChoiceItem(1);
                break;
        }
    }
}
