package com.naruto.didi2.activity.third;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.naruto.didi2.R;
import com.naruto.didi2.activity.test.ThreadTestActivity;
import com.naruto.didi2.adapter.AppAdapter;
import com.naruto.didi2.bean.AppCopy;
import com.naruto.didi2.util.AppUtil;
import com.naruto.didi2.util.FileUtils;
import com.naruto.didi2.util.LogUtils;
import com.naruto.didi2.util.PermissionUtils;
import com.naruto.didi2.util.ThreadPoolUtil;
import com.naruto.didi2.util.WorkThread;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class ApkCopyActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mPkgApk;
    private Button mCopyApk;
    private Button mLoadApk;
    private RecyclerView mListApk;
    private String[] per = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private List<AppCopy> list = new ArrayList<>();
    private AppAdapter mPicAdapter;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    closeProgress();
                    toast("应用复制完成");
                    break;
                case 0:
                    closeProgress();
                    toast("应用复制异常");
                    break;
                default:
                    break;
            }
        }
    };
    private ProgressDialog mProgress;
    private ArrayList<String> objects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apk_copy);
        initView();

        initRv();
        PermissionUtils.requestPermissions(this, per, new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {

                } else {
                    toast("该应用需开启存储权限");
                }

            }
        });
    }

    private void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void initData() {
        ThreadPoolUtil.getInstance().start(new WorkThread() {
            @Override
            public void runInner() {
                List<AppCopy> thirdInstallAppList = AppUtil.getInstance().getThirdInstallAppList(ApkCopyActivity.this);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list.clear();
                        list.addAll(thirdInstallAppList);
                        if (mPicAdapter != null) {
                            mPicAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });
    }

    private void initRv() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        mListApk.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        //        layoutManager.setOrientation(OrientationHelper. VERTICAL);
        //设置Adapter
        mPicAdapter = new AppAdapter(list);
        mPicAdapter.setOnItemClickListener(new AppAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, String pkg) {
                mPkgApk.setText(pkg);
            }
        });
        mListApk.setAdapter(mPicAdapter);
        //设置分隔线
        mListApk.addItemDecoration(new DividerItemDecoration(this, 1));
        //设置增加或删除条目的动画
        mListApk.setItemAnimator(new DefaultItemAnimator());
    }

    private void initView() {
        mPkgApk = (EditText) findViewById(R.id.apk_pkg);
        mCopyApk = (Button) findViewById(R.id.apk_copy);
        mCopyApk.setOnClickListener(this);
        mLoadApk = (Button) findViewById(R.id.apk_load);
        mLoadApk.setOnClickListener(this);
        mListApk = (RecyclerView) findViewById(R.id.apk_list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.apk_copy:
                // TODO 21/11/16
                String pkg = mPkgApk.getText().toString();
                if (!android.text.TextUtils.isEmpty(pkg)) {
                    showProgress();
                    ThreadPoolUtil.getInstance().start(new WorkThread() {
                        @Override
                        public void runInner() {
                            LogUtils.e("启动拷贝线程：" + Thread.currentThread().getId());
                            FileUtils.getDataFile(ApkCopyActivity.this, pkg, mHandler);
                        }
                    });
                } else {
                    toast("包名不能为空");
                }
                startActivity(new Intent(this, ThreadTestActivity.class));
                break;
            case R.id.apk_load:
                // TODO 21/11/16
                break;
            default:
                break;
        }
    }


    private void test1() {
        try {
            objects = new ArrayList<>();
            objects.add("1");
            objects.add("2");
            objects.add("3");
            configList(objects);
            LogUtils.e(objects.toString());
        } catch (Exception e) {
            LogUtils.e("" + e.toString());
        }
    }

    private void configList(List<String> list) {
        for (String a : list) {
            a = "0";
        }
    }

    private void showProgress() {
        try {
            if (mProgress == null) {
                //显示解密界面
                mProgress = new ProgressDialog(this);
            }
            if (!mProgress.isShowing()) {
                mProgress.setTitle("");
                mProgress.setMessage("copying...");
                mProgress.show();

                mProgress.setCancelable(false);
            }
        } catch (Exception e) {
            LogUtils.e("" + e.toString());
        }
    }

    private void closeProgress() {
        try {
            if (mProgress != null && mProgress.isShowing()) {
                mProgress.dismiss();
                mProgress = null;
            }
        } catch (Exception e) {
            LogUtils.e("" + e.toString());
        }

    }
}
