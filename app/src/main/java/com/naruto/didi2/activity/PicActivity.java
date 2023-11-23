package com.naruto.didi2.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.naruto.didi2.R;
import com.naruto.didi2.adapter.PicAdapter;
import com.naruto.didi2.bean.MeiTu;
import com.naruto.didi2.broadcast.NteWorkChangeReceive;
import com.naruto.didi2.service.MyService;
import com.naruto.didi2.util.AesUtils;
import com.naruto.didi2.util.AppUtil;
import com.naruto.didi2.util.LogUtils;
import com.naruto.didi2.util.OkHttpUtil;
import com.naruto.didi2.util.PermissionUtils;
import com.naruto.didi2.util.SpUtils;
import com.naruto.didi2.view.MyHeadView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.functions.Consumer;

public class PicActivity extends BaseActivity implements View.OnClickListener, OkHttpUtil.DataCallBack {

    private RecyclerView rv_pic;
    private PicAdapter mPicAdapter;
    private Button bt_go;
    private Button bt_query;
    private Button bt_add;
    private Button bt_delete;
    private Button bt_clear;
    //    private String url = "http://quan.suning.com/getSysTime.do";
//    private String url = "http://api.tianapi.com/meinv/index";
//    private String url = "http://39.105.99.227/api/Images/0";
    private String testurl = "https://www.huceo.com/post/481.html";
    private String testurl1 = "https://m.ugirl.com/meinvtupian";
    private String queryUrl = "http://api.tianapi.com/txapi/htmlpic/index";
    private String meituUrl = "https://m.tupianzj.com/meinv/mm/meituwang/";
    Object object;
    private String[] per = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private Toast toast;
    //    private List<MeiTuBean.NewslistBean> list = new ArrayList<>();
    private List<MeiTu.NewslistBean> list = new ArrayList<>();
    private PullToRefreshLayout prl_content;
    private LinearLayoutManager layoutManager;
    private EditText et_index;
    private Button bt_update;
    private NteWorkChangeReceive nteWorkChangeReceive;
    private ImageView img;
    private Button bt_load;
    private int mPage = 1;
    private String question = "CDJYZVEo7o7CaZW5Qx+89UxPI6eIwNwbqxDSoSe1BtDOsgEEXGiNfbAt4B31VvwQ";
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic);
        mContext = this;
        initView();
        initRv();

        PermissionUtils.requestPermissions(this, per, new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean){
                    initData();
                }else {
                    toast("该应用需开启存储权限");
                }

            }
        });
        initEvent();
//        initReceiver();
    }

    private void initReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        nteWorkChangeReceive = new NteWorkChangeReceive();
        registerReceiver(nteWorkChangeReceive, intentFilter);
    }

    private void initEvent() {
        MyHeadView myHeadView = new MyHeadView(this);
        prl_content.setHeaderView(myHeadView);
        prl_content.setCanRefresh(false);
        prl_content.setCanLoadMore(false);
        prl_content.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
//                mPage=1;
                initData();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        // 结束刷新
//                        prl_content.finishRefresh();
//                        //修改数据
//                        if (mPicAdapter != null) {
//                            mPicAdapter.notifyDataSetChanged();
//                        }
//                    }
//                }, 2000);
            }

            @Override
            public void loadMore() {
//                mPage++;
                initData();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        // 结束加载更多
//                        prl_content.finishLoadMore();
//                        //修改数据
//                        List<String> list = AppUtil.getInstance().getList();
//                        for (int i = 0; i < 20; i++) {
//                            list.add("recyclerview:" + i);
//                        }
//                        if (mPicAdapter != null) {
//                            mPicAdapter.notifyDataSetChanged();
//                            int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
//                            layoutManager.scrollToPosition(lastVisibleItemPosition + 5);
//                        }
//                    }
//                }, 2000);
            }
        });
    }

    /*private void initData(final int page) {
//        list = AppUtil.getInstance().getList();
        HashMap<String, String> params = new HashMap<>();
        params.put("key", "e3d610dde0076bbc53d1421b12cfee35");
        params.put("page", page + "");
        OkHttpUtil.getInstance().doPost(url, params, new OkHttpUtil.DataCallBack() {
            @Override
            public void onSuccess(String s) {
                try {
                    LogUtils.e("返回数据：" + s);
                    Gson gson = new Gson();
                    MeiTuBean meiTuBean = gson.fromJson(s, MeiTuBean.class);
                    int code = meiTuBean.getCode();
                    if (code == 200) {
                        List<MeiTuBean.NewslistBean> newslist = meiTuBean.getNewslist();
                        if (page == 1) {
                            list.clear();
                            if (prl_content != null) {
                                prl_content.finishRefresh();
                            }
                        } else {
                            if (prl_content != null) {
                                prl_content.finishLoadMore();
                            }
                        }
                        list.addAll(newslist);
                        if (mPicAdapter != null) {
                            mPicAdapter.notifyDataSetChanged();
                        }
                    } else {
                        LogUtils.e("获取数据失败:" + code + "==" + meiTuBean.getMsg());
                    }
                } catch (Exception e) {
                    LogUtils.e("Exception: " + e.toString());
                }
            }

            @Override
            public void onFailure(String f) {
                LogUtils.e(f);
            }
        });
        LogUtils.e("当前数据量：" + list.size());
    }*/

    private void initData() {
        long longValue = SpUtils.getLongValue(this, SpUtils.RUNCOUNT);
        if (longValue >= 10) {
            AppUtil.getInstance().toast("运行次数已达上限，请联系管理员开通vip");
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        String decrypt = AesUtils.decrypt(question);
        params.put("key", decrypt);
        params.put("url", testurl1);
//        params.put("page", "0");
        OkHttpUtil.getInstance().doPost(queryUrl, params, new OkHttpUtil.DataCallBack() {
            @Override
            public void onSuccess(String s) {
                try {
                    LogUtils.e("返回数据：" + s.length() + s);
                    Gson gson = new Gson();
                    MeiTu meiTuBean = gson.fromJson(s, MeiTu.class);
                    int code = meiTuBean.getCode();
                    if (code == 200) {
                        long longValue = SpUtils.getLongValue(mContext, SpUtils.RUNCOUNT);
                        longValue++;
                        SpUtils.saveLongValue(mContext, SpUtils.RUNCOUNT, longValue);
                        List<MeiTu.NewslistBean> newslist = meiTuBean.getNewslist();
//                        if (page == 1) {
//                            list.clear();
//                            if (prl_content != null) {
//                                prl_content.finishRefresh();
//                            }
//                        } else {
//                            if (prl_content != null) {
//                                prl_content.finishLoadMore();
//                            }
//                        }
                        list.clear();
                        list.addAll(newslist);
                        if (mPicAdapter != null) {
                            mPicAdapter.notifyDataSetChanged();
                        }
                    } else {
                        LogUtils.e("获取数据失败:" + code + "==" + meiTuBean.getMsg());
                    }
                } catch (Exception e) {
                    LogUtils.exception(e);
                }
            }

            @Override
            public void onFailure(String f) {
                LogUtils.e(f);
            }
        });
//        LogUtils.e("当前数据量：" + list.size());
    }

//    private void initData() {
//        long longValue = SpUtils.getLongValue(this, SpUtils.RUNCOUNT);
//        if (longValue >= 5) {
//            AppUtil.getInstance().toast("运行次数已达上限，请联系管理员开通vip");
//            return;
//        }
//        HashMap<String, String> params = new HashMap<>();
//        String decrypt = AesUtils.decrypt(question);
//        params.put("key", decrypt);
//        params.put("url", meituUrl);
//        OkHttpUtil.getInstance().doPost(queryUrl, params, new OkHttpUtil.DataCallBack() {
//            @Override
//            public void onSuccess(String s) {
//                try {
//                    LogUtils.e("返回数据：" + s.length()+s);
//                    Gson gson = new Gson();
//                    MeiTu meiTuBean = gson.fromJson(s, MeiTu.class);
//                    int code = meiTuBean.getCode();
//                    if (code == 200) {
//                        long longValue = SpUtils.getLongValue(mContext, SpUtils.RUNCOUNT);
//                        longValue++;
//                        SpUtils.saveLongValue(mContext, SpUtils.RUNCOUNT, longValue);
//                        List<MeiTu.NewslistBean> newslist = meiTuBean.getNewslist();
////                        if (page == 1) {
////                            list.clear();
////                            if (prl_content != null) {
////                                prl_content.finishRefresh();
////                            }
////                        } else {
////                            if (prl_content != null) {
////                                prl_content.finishLoadMore();
////                            }
////                        }
//                        list.clear();
//                        list.addAll(newslist);
//                        if (mPicAdapter != null) {
//                            LogUtils.e("更新列表：");
//                            mPicAdapter.notifyDataSetChanged();
//                        }
//                    } else {
//                        LogUtils.e("获取数据失败:" + code + "==" + meiTuBean.getMsg());
//                    }
//                } catch (Exception e) {
//                    LogUtils.e("Exception: " + e.toString());
//                }
//            }
//
//            @Override
//            public void onFailure(String f) {
//                LogUtils.e(f);
//            }
//        });
////        LogUtils.e("当前数据量：" + list.size());
//    }

    private void initRv() {
        layoutManager = new LinearLayoutManager(this);
//设置布局管理器
        rv_pic.setLayoutManager(layoutManager);
//设置为垂直布局，这也是默认的
//        layoutManager.setOrientation(OrientationHelper. VERTICAL);
//设置Adapter
        mPicAdapter = new PicAdapter(list);
        rv_pic.setAdapter(mPicAdapter);
        //设置分隔线
        rv_pic.addItemDecoration(new DividerItemDecoration(this, 1));
//设置增加或删除条目的动画
        rv_pic.setItemAnimator(new DefaultItemAnimator());
    }

    private void initView() {
        rv_pic = (RecyclerView) findViewById(R.id.rv_pic);
        bt_go = (Button) findViewById(R.id.bt_go);
        bt_go.setOnClickListener(this);
        bt_query = (Button) findViewById(R.id.bt_query);
        bt_query.setOnClickListener(this);
        bt_add = (Button) findViewById(R.id.bt_add);
        bt_add.setOnClickListener(this);
        bt_delete = (Button) findViewById(R.id.bt_delete);
        bt_delete.setOnClickListener(this);
        bt_clear = (Button) findViewById(R.id.bt_clear);
        bt_clear.setOnClickListener(this);
        prl_content = (PullToRefreshLayout) findViewById(R.id.prl_content);
        prl_content.setOnClickListener(this);
        et_index = (EditText) findViewById(R.id.et_index);
        et_index.setOnClickListener(this);
        bt_update = (Button) findViewById(R.id.bt_update);
        bt_update.setOnClickListener(this);
        img = (ImageView) findViewById(R.id.img);
        img.setOnClickListener(this);
        bt_load = (Button) findViewById(R.id.bt_load);
        bt_load.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_go:

                break;
            case R.id.bt_query:
                AppUtil.getInstance().callPhone(this, "10086");
                break;
            case R.id.bt_add:
//                SpUtils.saveStringValue(this, SpUtils.RUNCOUNT,"5");
                stopService(new Intent(this, MyService.class));
                break;
            case R.id.bt_delete:
//                AppUtil.getInstance().clearAppData(this);
                SpUtils.saveStringValue(this, SpUtils.RUNCOUNT, "6");
                break;
            case R.id.bt_clear:
                SpUtils.clear(this);
                AppUtil.getInstance().clearAppData(this);
                break;
            case R.id.bt_update:
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                String index = et_index.getText().toString();
                int po = Integer.parseInt(index);
                mPicAdapter.upView(rv_pic, po, firstVisibleItemPosition);
                break;
            case R.id.bt_load:
//                Glide.with(this).load("http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg").into(img);
//                startActivity(new Intent(this, TuActivity.class));
                uploadP();
                break;
        }
    }

    private void reqPer() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        PermissionUtils.requestPermissionsOnThread((android.support.v4.app.FragmentActivity) AppUtil.getInstance().getActivity(), per, new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                LogUtils.e("同意了权限");
                            }
                        });
                    }
                }
        ).start();
    }

    private void test2() {
//        Toast.makeText(PicActivity.this, "1", Toast.LENGTH_SHORT).show();
//
//        Toast.makeText(PicActivity.this, "3", Toast.LENGTH_SHORT).show();
//
//        Toast.makeText(PicActivity.this, "2", Toast.LENGTH_SHORT).show();

    }

    private void test() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        toast("toast1");
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                        }
                    }
                }
        ).start();
//        new Thread(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        SystemClock.sleep(500);
//                        toast("toast2");
//                    }
//                }
//        ).start();
//        new Thread(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        SystemClock.sleep(1000);
//                        toast("toast3");
//                    }
//                }
//        ).start();
    }

    void toast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(PicActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onSuccess(String s) {
        LogUtils.e("获取到网络数据：" + s);
    }

    @Override
    public void onFailure(String f) {
        LogUtils.e("获取网络数据异常：" + f);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(nteWorkChangeReceive);
    }

    private void uploadP() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        OkHttpUtil.getInstance().uploadFile();
                    }
                }
        ).start();
    }
}
