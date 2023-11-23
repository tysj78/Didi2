package com.naruto.didi2.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.naruto.didi2.R;
import com.naruto.didi2.bean.MnInfo;
import com.naruto.didi2.util.EmulatorCheckUtil;
import com.naruto.didi2.util.MnCheck;

import java.io.File;

public class MmCheckActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mncheck;
    private TextView result;
    private TextView result_content;
    private Button newcheck;
    private TextView new_result;
    private static String[] known_device_ids;

    private static String[] known_files;

    private static String[] known_imsi_ids = { "310260000000000" };

    private static String[] known_numbers;

    private static String[] known_pipes;

    private static String[] known_qemu_drivers;
    static  {
        known_device_ids = new String[] { "000000000000000" };
        known_numbers = new String[] {
                "15555215554", "15555215556", "15555215558", "15555215560", "15555215562", "15555215564", "15555215566", "15555215568", "15555215570", "15555215572",
                "15555215574", "15555215576", "15555215578", "15555215580", "15555215582", "15555215584" };
        known_files = new String[] { "/system/lib/libc_malloc_debug_qemu.so", "/sys/qemu_trace", "/system/bin/qemu-props" };
        known_qemu_drivers = new String[] { "goldfish" };
        known_pipes = new String[] { "/dev/socket/qemud", "/dev/qemu_pipe" };
//        signMd5Str = null;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mm_check);
        initView();
    }

    private void initView() {
        mncheck = (Button) findViewById(R.id.mncheck);
        result = (TextView) findViewById(R.id.result);
        result_content = (TextView) findViewById(R.id.result_content);

        mncheck.setOnClickListener(this);
        newcheck = (Button) findViewById(R.id.newcheck);
        newcheck.setOnClickListener(this);
        new_result = (TextView) findViewById(R.id.new_result);
        new_result.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mncheck:
                MnInfo b = MnCheck.checkEmulator(this);
                if (b == null) {
                    result.setText("检测模拟器出错");
                    return;
                }
                if (b.isM) {
                    result.setText("当前设备为模拟器");
                } else {
                    result.setText("当前设备为真机");
                }
                result_content.setText(b.infos);
                break;
            case R.id.newcheck:
                boolean b1 = EmulatorCheckUtil.getSingleInstance().checkIsRunningInEmulator(this);
                if (b1) {
                    new_result.setText("新版检测结果：当前设备为模拟器");
                } else {
                    new_result.setText("新版检测结果：当前设备为真机");
                }
                break;
        }
    }

    public static boolean checkEmulator(Context paramContext) {
        return (CheckEmulatorBuild() || CheckImsiIDS(paramContext) || CheckDeviceIDS(paramContext) || CheckPhoneNumber(paramContext) || CheckEmulatorFiles() || checkQEmuDriverFile() || checkPipes());
    }

    private static boolean CheckEmulatorBuild() {
        String str1 = Build.BOARD;
        String str2 = Build.BOOTLOADER;
        String str3 = Build.BRAND;
        String str4 = Build.DEVICE;
        String str5 = Build.HARDWARE;
        String str6 = Build.MODEL;
        String str7 = Build.PRODUCT;
        String str8 = Build.SERIAL;
        if (str1 == "unknown" || str2 == "unknown" || str3 == "generic" || str4 == "generic" || str6 == "sdk" || str7 == "sdk" || str5 == "goldfish" || str8.equals("unknown") || str3.toLowerCase().equals("android") || str6.toLowerCase().contains("sdk")) {
            Log.v("Result:", "Find Emulator by EmulatorBuild!");
            return true;
        }
        if (str3.toLowerCase().equals("android") || str6.toLowerCase().contains("sdk")) {
            Log.v("Result:", "Find Emulator by EmulatorBuild!");
            return true;
        }
        Log.v("Result:", "Not Find Emulator by EmulatorBuild!");
        return false;
    }

    private static boolean CheckImsiIDS(Context paramContext) {
        boolean bool = false;
        TelephonyManager telephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
        if (ActivityCompat.checkSelfPermission(paramContext, "android.permission.READ_PHONE_STATE") != 0) {
            String str = telephonyManager.getSubscriberId();
            String[] arrayOfString = known_imsi_ids;
            int i = arrayOfString.length;
            for (byte b = 0; b < i; b++) {
                if (arrayOfString[b].equalsIgnoreCase(str)) {
                    Log.v("Result:", "Find imsi ids: 310260000000000!");
                    return true;
                }
            }
        } else {
            return bool;
        }
        Log.v("Result:", "Not Find imsi ids: 310260000000000!");
        return false;
    }

    private static boolean CheckDeviceIDS(Context paramContext) {
        boolean bool = false;
        TelephonyManager telephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
        if (ActivityCompat.checkSelfPermission(paramContext, "android.permission.READ_PHONE_STATE") != 0) {
            String str = telephonyManager.getDeviceId();
            String[] arrayOfString = known_device_ids;
            int i = arrayOfString.length;
            for (byte b = 0; b < i; b++) {
                if (arrayOfString[b].equalsIgnoreCase(str)) {
                    Log.v("Result:", "Find ids: 000000000000000!");
                    return true;
                }
            }
        } else {
            return bool;
        }
        Log.v("Result:", "Not Find ids: 000000000000000!");
        return false;
    }

    private static boolean CheckPhoneNumber(Context paramContext) {
        byte b = 0;
        TelephonyManager telephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
        boolean i = false;
        if (ActivityCompat.checkSelfPermission(paramContext, "android.permission.READ_SMS") != 0) {
            i = false;
            if (ActivityCompat.checkSelfPermission(paramContext, "android.permission.READ_PHONE_STATE") != 0) {
                String str = telephonyManager.getLine1Number();
                String[] arrayOfString = known_numbers;
                int j = arrayOfString.length;
                for (byte b1 = 0; b1 < j; b1++) {
                    if (arrayOfString[b1].equalsIgnoreCase(str)) {
                        Log.v("Result:", "Find PhoneNumber!");
                        return true;
                    }
                }
            } else {
                return i;
            }
        } else {
            return i;
        }
        Log.v("Result:", "Not Find PhoneNumber!");
        return false;
    }

    private static boolean CheckEmulatorFiles() {
        for (byte b = 0; b < known_files.length; b++) {
            if ((new File(known_files[b])).exists()) {
                Log.v("Result:", "Find Emulator Files!");
                return true;
            }
        }
        Log.v("Result:", "Not Find Emulator Files!");
        return false;
    }

    private static boolean checkQEmuDriverFile() { // Byte code:
        //   0: new java/io/File
        //   3: dup
        //   4: ldc_w '/proc/tty/drivers'
        //   7: invokespecial <init> : (Ljava/lang/String;)V
        //   10: astore_3
        //   11: aload_3
        //   12: invokevirtual exists : ()Z
        //   15: ifeq -> 110
        //   18: aload_3
        //   19: invokevirtual canRead : ()Z
        //   22: ifeq -> 110
        //   25: sipush #1024
        //   28: newarray byte
        //   30: astore_2
        //   31: new java/io/FileInputStream
        //   34: dup
        //   35: aload_3
        //   36: invokespecial <init> : (Ljava/io/File;)V
        //   39: astore_3
        //   40: aload_3
        //   41: aload_2
        //   42: invokevirtual read : ([B)I
        //   45: pop
        //   46: aload_3
        //   47: invokevirtual close : ()V
        //   50: new java/lang/String
        //   53: dup
        //   54: aload_2
        //   55: invokespecial <init> : ([B)V
        //   58: astore_2
        //   59: getstatic com/emm/sandboxsdk/audit/CheckItems.known_qemu_drivers : [Ljava/lang/String;
        //   62: astore_3
        //   63: aload_3
        //   64: arraylength
        //   65: istore_1
        //   66: iconst_0
        //   67: istore_0
        //   68: iload_0
        //   69: iload_1
        //   70: if_icmpge -> 110
        //   73: aload_2
        //   74: aload_3
        //   75: iload_0
        //   76: aaload
        //   77: invokevirtual indexOf : (Ljava/lang/String;)I
        //   80: iconst_m1
        //   81: if_icmpeq -> 103
        //   84: ldc 'Result:'
        //   86: ldc_w 'Find know_qemu_drivers!'
        //   89: invokestatic i : (Ljava/lang/String;Ljava/lang/String;)I
        //   92: pop
        //   93: iconst_1
        //   94: ireturn
        //   95: astore_3
        //   96: aload_3
        //   97: invokevirtual printStackTrace : ()V
        //   100: goto -> 50
        //   103: iload_0
        //   104: iconst_1
        //   105: iadd
        //   106: istore_0
        //   107: goto -> 68
        //   110: ldc 'Result:'
        //   112: ldc_w 'Not Find known_qemu_drivers!'
        //   115: invokestatic i : (Ljava/lang/String;Ljava/lang/String;)I
        //   118: pop
        //   119: iconst_0
        //   120: ireturn
        // Exception table:
        //   from	to	target	type
        //   31	50	95	java/lang/Exception
        return false;
        }

        private static boolean checkPipes() {
            for (byte b = 0; b < known_pipes.length; b++) {
                if ((new File(known_pipes[b])).exists()) {
                    Log.v("Result:", "Find pipes!");
                    return true;
                }
            }
            Log.i("Result:", "Not Find pipes!");
            return false;
        }
}
