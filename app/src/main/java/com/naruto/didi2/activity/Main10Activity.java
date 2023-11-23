package com.naruto.didi2.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.naruto.didi2.R;
import com.naruto.didi2.util.LogUtils;
import com.naruto.didi2.util.Rom;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;


public class Main10Activity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTest1;
    private TextView mTest2;
    private TextView mTest3;
    private TextView mTest4;
    private TextView mTest5;
    private Button mRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main10);
        initView();


    }

    private void initView() {
        mTest1 = (TextView) findViewById(R.id.test1);
        mTest2 = (TextView) findViewById(R.id.test2);
        mTest3 = (TextView) findViewById(R.id.test3);
        mTest4 = (TextView) findViewById(R.id.test4);
        mTest5 = (TextView) findViewById(R.id.test5);
        mRead = (Button) findViewById(R.id.read);
        mRead.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.read:
                // TODO 22/11/09
//                getratio();//屏幕分辨率 ok
                getrtio1();//屏幕实际分辨率
                getsysName();//系统名称 ok
//                getcpu();//cpu使用率
                sysInfo();
                getcpuname();//cpu型号
                getcpuabi();//cpu架构 ok
                break;
            default:
                break;
        }
    }

    private String getratio() {
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            LogUtils.e("displayMetrics.widthPixels = " + (displayMetrics.widthPixels) + ", displayMetrics.heightPixels = " + (displayMetrics.heightPixels));
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
        return "";
    }

    private String getrtio1() {
        try {
            WindowManager windowManager = getWindow().getWindowManager();
            DisplayMetrics metrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getRealMetrics(metrics);
//屏幕实际宽度（像素个数）
            int width = metrics.widthPixels;
//屏幕实际高度（像素个数）
            int height = metrics.heightPixels;
            LogUtils.e("[width:" + width + "==height:" + height+"]");
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
        return "";
    }

    private String getsysName() {
        String name = "";
        try {
//            name = OSUtils.getRomType().name();
            name = Rom.getName();
            LogUtils.e("系统名称：" + name);
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }

        return name;
    }

    private String getcpuname() {
        String cpuName = "";
        try {
            cpuName = getCpuName();
            LogUtils.e("cpu名称：" + cpuName);
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }

        return cpuName;
    }

    public static String getCpuName() {
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        String cpuName = "";

        try {
            FileReader fileReader = new FileReader(str1);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((str2 = bufferedReader.readLine()) != null) {
                if (TextUtils.isEmpty(str2)) {
                    continue;
                }
                String[] arrayOfString = str2.split(":\\s+", 2);
                if (TextUtils.equals(arrayOfString[0].trim(), "Hardware")) {
                    cpuName = arrayOfString[1];
                    break;
                }
            }

            bufferedReader.close();
            fileReader.close();
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
        return cpuName;
    }

    private String getcpuabi() {
        String abistr = "";
        try {
            String[] abis = new String[]{};
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                abis = Build.SUPPORTED_ABIS;
            } else {
                abis = new String[]{Build.CPU_ABI, Build.CPU_ABI2};
            }
            abistr = Arrays.toString(abis);
            LogUtils.e("CPU架构: " + abistr);
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }

        return abistr;
    }


//    private float getCpuDataForO() {
//        java.lang.Process process = null;
//        try {
//            //调用shell 执行 top -n 1
//            process = Runtime.getRuntime().exec("top -n 1");
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String line;
//            int cpuIndex = -1;
//            while ((line = reader.readLine()) != null) {
//                line = line.trim();
//                if (TextUtils.isEmpty(line)) {
//                    continue;
//                }
//                int tempIndex = getCPUIndex(line);
//                if (tempIndex != -1) {
//                    cpuIndex = tempIndex;
//                    continue;
//                }
//                if (line.startsWith(String.valueOf(android.os.Process.myPid()))) {
//                    if (cpuIndex == -1) {
//                        continue;
//                    }
//                    String[] param = line.split("\s+");
//                    if (param.length <= cpuIndex) {
//                        continue;
//                    }
//                    String cpu = param[cpuIndex];
//                    if (cpu.endsWith("%")) {
//                        cpu = cpu.substring(0, cpu.lastIndexOf("%"));
//                    }
//                    float rate = Float.parseFloat(cpu) / Runtime.getRuntime().availableProcessors();
//                    return rate;
//                }
//            }
//        } catch (IOException e) {
//            //...
//        } finally {
//            //...
//        }
//        return 0;
//    }


    private String getcpu() {
        float processCpuRate = 0;
        try {
            processCpuRate = getProcessCpuRate();
            LogUtils.e("cpu:" + processCpuRate);
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
        return String.valueOf(processCpuRate);
    }

    public static float getProcessCpuRate() {

        float totalCpuTime1 = getTotalCpuTime();
        float processCpuTime1 = getAppCpuTime();
        try {
            Thread.sleep(360);
        } catch (Exception e) {
        }

        float totalCpuTime2 = getTotalCpuTime();
        float processCpuTime2 = getAppCpuTime();

        float cpuRate = 100 * (processCpuTime2 - processCpuTime1)
                / (totalCpuTime2 - totalCpuTime1);

        return cpuRate;
    }

    public static long getTotalCpuTime() {   // 获取系统总CPU使用时间
        String[] cpuInfos = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("/proc/stat")), 1000);
            String load = reader.readLine();
            LogUtils.e("load:" + load);
            reader.close();
            cpuInfos = load.split(" ");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        long totalCpu = Long.parseLong(cpuInfos[2])
                + Long.parseLong(cpuInfos[3]) + Long.parseLong(cpuInfos[4])
                + Long.parseLong(cpuInfos[6]) + Long.parseLong(cpuInfos[5])
                + Long.parseLong(cpuInfos[7]) + Long.parseLong(cpuInfos[8]);
        return totalCpu;
    }

    public static long getAppCpuTime() {   // 获取应用占用的CPU时间
        String[] cpuInfos = null;
        try {
            int pid = android.os.Process.myPid();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("/proc/" + pid + "/stat")), 1000);
            String load = reader.readLine();
            reader.close();
            cpuInfos = load.split(" ");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        long appCpuTime = Long.parseLong(cpuInfos[13])
                + Long.parseLong(cpuInfos[14]) + Long.parseLong(cpuInfos[15])
                + Long.parseLong(cpuInfos[16]);
        return appCpuTime;
    }

    private void sysInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 查询某一个进程的信息
//                    List<String> cmds = new ArrayList<String>();
//                    cmds.add("top");
//                    cmds.add("-n");
//                    cmds.add("1");
//                    if (Build.VERSION.SDK_INT >= 26) {
//                        cmds.add("-p");
//                        cmds.add(android.os.Process.myPid() + "");
//                    }
//                    ProcessBuilder pb = new ProcessBuilder(cmds);
//                    Process p = pb.start();

                    Process p = Runtime.getRuntime().exec("top -n 3");

                    BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    p.exitValue();
                    br.close();

                    LogUtils.e("Sys : " + sb.toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    LogUtils.e("Exception : " + ex.toString());
                }
            }
        }).start();

    }


}
