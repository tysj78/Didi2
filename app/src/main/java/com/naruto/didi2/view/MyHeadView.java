package com.naruto.didi2.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jwenfeng.library.pulltorefresh.view.HeadView;

import com.naruto.didi2.R;
import com.naruto.didi2.util.AppUtil;

/**
 * xxx class
 *
 * @author yangyong
 * @date 2020/11/16/0016
 */

public class MyHeadView extends FrameLayout implements HeadView {

    private TextView tv;
    private ImageView arrow;
    private ProgressBar progressBar;

    private String lastTime = "";
    private TextView time;

    public MyHeadView(@NonNull Context context) {
        this(context, null);
    }

    public MyHeadView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyHeadView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public void begin() {

    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.myheader, null);
        addView(view);
        tv = (TextView) view.findViewById(R.id.header_tv);
        arrow = (ImageView) view.findViewById(R.id.header_arrow);
        progressBar = (ProgressBar) view.findViewById(R.id.header_progress);

        time = (TextView) view.findViewById(R.id.tv_time);
    }

    @Override
    public void progress(float progress, float all) {
        float s = progress / all;
        if (s >= 0.9f) {
            arrow.setRotation(180);
        } else {
            arrow.setRotation(0);
        }
        if (progress >= all) {
            tv.setText("松开刷新");
            time.setText("最近更新:" + lastTime);
        } else {
            tv.setText("下拉加载");
            time.setText("最近更新:" + lastTime);
        }
    }

    @Override
    public void finishing(float progress, float all) {
    }

    @Override
    public void loading() {
        arrow.setVisibility(GONE);
        progressBar.setVisibility(VISIBLE);
        tv.setText("刷新中...");

        lastTime = AppUtil.getInstance().getCurrentDate();
    }

    @Override
    public void normal() {
        arrow.setVisibility(VISIBLE);
        progressBar.setVisibility(GONE);
        tv.setText("下拉刷新");
    }

    @Override
    public View getView() {
        return this;
    }
}
