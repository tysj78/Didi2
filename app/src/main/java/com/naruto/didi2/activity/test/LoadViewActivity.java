package com.naruto.didi2.activity.test;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.naruto.didi2.R;
import com.naruto.didi2.util.LogUtils;

public class LoadViewActivity extends AppCompatActivity {

    private ScrollView scrollView;
    private LinearLayout contentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_view);
        initView();
        loadFileContent();
    }

    private void loadFileContent() {
        scrollView = new ScrollView(this);
        contentContainer.addView(scrollView);
        EditText view = new EditText(this);
        scrollView.addView(view);
//        String path = getIntent().getStringExtra("FILE");
//        delFile = getIntent().getIntExtra("isBurnAfterRead", 0);

//        infoFileName = getIntent().getStringExtra("infoFileName");//add by gxb 20200402

        view.setText(getContent());
        view.setTextColor(Color.parseColor("#343434"));
//        view.setBackgroundResource(R.drawable.);
        view.setFilters(new InputFilter[]{new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                return source.length() < 1 ? dest.subSequence(dstart, dend) : "";
            }
        }});
//        realFile = new File(path);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //todu 这里写你要在界面加载完成后执行的操作。
                LogUtils.e("文档加载完成");
                if (scrollView != null) {
                    scrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });

    }

    private String getContent() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 100000; i++) {
            builder.append("吾儿王腾有大地之姿");
        }
        return builder.toString();
    }


    private void initView() {
        contentContainer = (LinearLayout) findViewById(R.id.loadview);
    }
}
