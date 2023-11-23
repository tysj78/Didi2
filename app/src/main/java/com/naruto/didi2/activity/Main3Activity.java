package com.naruto.didi2.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.naruto.didi2.R;
import com.naruto.didi2.constant.Constants;
import com.naruto.didi2.util.LogUtils;

import java.lang.reflect.Method;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main3Activity extends BaseActivity {

    private static final String TAG = "yy";
    private TextView main3_content;
    private TextView main3_ch;
    private TextView main3_zlj;
    private final int[] abcde = {0x67452301, 0xefcdab89, 0x98badcfe,
            0x10325476, 0xc3d2e1f0};
    private int[] tmpData = new int[80];
    private int[] digestInt = new int[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        initView();
        //8.0动态权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int checkPermission = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 1); //后面的1为请求码
            } else {
                Log.e(Constants.TAG, "imei: " + getPhoneIMEI());
            }
        }
        String pseudoId = getPseudoId();

        main3_zlj.setText("cpu指令集：" + Build.CPU_ABI);
        Log.e(Constants.TAG, "pseudoId: " + pseudoId);

        String phoneIMEI = getPhoneIMEI();
        Log.e(Constants.TAG, "imei: " + phoneIMEI);

        String imeiOnly = getImeiOnly(this, 0);
        String imeiOnly1 = getImeiOnly(this, 1);
        Log.e(Constants.TAG, "imei-new1: " + imeiOnly);

        Log.e(Constants.TAG, "imei-new2: " + imeiOnly1);

        String meidOnly = getMeidOnly(this, 0);
        String meidOnly1 = getMeidOnly(this, 0);
        Log.e(Constants.TAG, "meid-new1: " + meidOnly);

        Log.e(Constants.TAG, "meid-new2: " + meidOnly1);


//        String serial = Build.SERIAL;
//
//        byte[] bytes = "ffffffffabb20833ffffffffef05ac4a".getBytes();
//        String digestOfString = getDigestOfString(bytes);
//        Log.e(TAG, "转换后的udid: "+digestOfString );

        boolean imei = isIMEI("357695696645247");
        Log.e(TAG, "imei检测: " + imei);
        String sdk = Build.VERSION.SDK;
        main3_content.setText("系统版本: " + sdk);

    }


    @SuppressLint("MissingPermission")
    private String getPhoneIMEI() {
        try {
            TelephonyManager tm = (TelephonyManager) getSystemService(Service.TELEPHONY_SERVICE);
            return tm.getDeviceId();
        } catch (Exception e) {
            LogUtils.e("" + e.toString());
        }
        return "获取异常";
    }


    public String getImeiOnly(Context context, int slotId) {
        String imei = "";
//Android 6.0 以后需要获取动态权限 检查权限
//        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            return imei;
//        }
        try {
            TelephonyManager manager = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            if (manager != null) {
                // android 8 即以后建议用getImei 方法获取 不会获取到MEID
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Method method = manager.getClass().getMethod("getImei", int.class);
                    imei = (String) method.invoke(manager, slotId);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//5.0的系统如果想获取MEID/IMEI1/IMEI2 ----framework层提供了两个属性值“ril.cdma.meid"和“ril.gsm.imei"获取
                    imei = getSystemPropertyByReflect("ril.gsm.imei");
//如果获取不到 就调用 getDeviceId 方法获取
                } else {//5.0以下获取imei/meid只能通过 getDeviceId 方法去取
                }
            }

        } catch (Exception e) {
        }
        if (TextUtils.isEmpty(imei)) {
            LogUtils.e("getImei获取为空，通过getDeviceId获取");
            String imeiOrMeid = getDeviceId(context, slotId);
//长度15 的是imei 14的是meid
            if (!TextUtils.isEmpty(imeiOrMeid) && imeiOrMeid.length() >= 15) {
                imei = imeiOrMeid;
            }
        }
        return imei;
    }

    public String getMeidOnly(Context context, int slotId) {
        String meid = "";
//Android 6.0 以后需要获取动态权限 检查权限
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return meid;
        }
        try {
            TelephonyManager manager = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            if (manager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {// android 8 即以后建议用getMeid 方法获取 不会获取到Imei
                    Method method = manager.getClass().getMethod("getMeid", int.class);
                    meid = (String) method.invoke(manager, slotId);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//5.0的系统如果想获取MEID/IMEI1/IMEI2 ----framework层提供了两个属性值“ril.cdma.meid"和“ril.gsm.imei"获取
                    meid = getSystemPropertyByReflect("ril.cdma.meid");
//如果获取不到 就调用 getDeviceId 方法获取
                } else {//5.0以下获取imei/meid只能通过 getDeviceId 方法去取
                }
            }
        } catch (Exception e) {
        }
        if (TextUtils.isEmpty(meid)) {
            LogUtils.e("获取meid为空");
            String imeiOrMeid = getDeviceId(context, slotId);
//长度15 的是imei 14的是meid
            if (imeiOrMeid.length() == 14) {
                meid = imeiOrMeid;
            }
        }
        return meid;

    }


    public static String getDeviceId(Context context) {
        String imei = "";
//Android 6.0 以后需要获取动态权限 检查权限
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return imei;
        }
// 1. 尝试通过系统api获取imei
        imei = getDeviceIdFromSystemApi(context);
        if (TextUtils.isEmpty(imei)) {
            imei = getDeviceIdByReflect(context);
        }
        return imei;
    }

    public static String getDeviceId(Context context, int slotId) {
        String imei = "";
// 1. 尝试通过系统api获取imei
        imei = getDeviceIdFromSystemApi(context, slotId);
        if (TextUtils.isEmpty(imei)) {
            imei = getDeviceIdByReflect(context, slotId);
        }
        return imei;
    }

    public static String getDeviceIdByReflect(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= 21) {
                Method simMethod = TelephonyManager.class.getDeclaredMethod("getDefaultSim");
                Object sim = simMethod.invoke(tm);
                Method method = TelephonyManager.class.getDeclaredMethod("getDeviceId", int.class);
                return method.invoke(tm, sim).toString();
            } else {
                Class clazz = Class.forName("com.android.internal.telephony.IPhoneSubInfo");
                Method subInfoMethod = TelephonyManager.class.getDeclaredMethod("getSubscriberInfo");
                subInfoMethod.setAccessible(true);
                Object subInfo = subInfoMethod.invoke(tm);
                Method method = clazz.getDeclaredMethod("getDeviceId");
                return method.invoke(subInfo).toString();
            }
        } catch (Throwable e) {

        }
        return "";
    }

    public static String getDeviceIdByReflect(Context context, int slotId) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            Method method = tm.getClass().getMethod("getDeviceId", int.class);
            return method.invoke(tm, slotId).toString();
        } catch (Throwable e) {
        }
        return "";
    }


    public static String getDeviceIdFromSystemApi(Context context, int slotId) {
        String imei = "";
        try {
            TelephonyManager telephonyManager =
                    (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager != null) {
                imei = telephonyManager.getDeviceId(slotId);
            }
        } catch (Throwable e) {
        }
        return imei;
    }

    public static String getDeviceIdFromSystemApi(Context context) {
        String imei = "";
        try {
            TelephonyManager telephonyManager =
                    (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager != null) {
                imei = telephonyManager.getDeviceId();
            }
        } catch (Throwable e) {
        }
        return imei;
    }

    @SuppressLint("PrivateApi")
    private static String getSystemPropertyByReflect(String key) {
        try {
            Class clz = Class.forName("android.os.SystemProperties");
            Method getMethod = clz.getMethod("get", String.class, String.class);
            return (String) getMethod.invoke(clz, key, "");
        } catch (Exception e) {

        }
        return "";
    }

    public String getPseudoId() {

        String m_szDevIDShort = "35" +
                //主板
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +
                //cpu指令集    设置参数
                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +
                //显示屏参数     主机
                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +
                //修订版本列表    硬件制造商
                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +
                //版本   手机制造商
                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +
                //描述build的标签   builder的类型
                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +
                //用户
                Build.USER.length() % 10; //13 位
        Log.e(Constants.TAG, "主板: " + Build.BOARD);
        Log.e(Constants.TAG, "cpu指令集: " + Build.CPU_ABI);
        Log.e(Constants.TAG, "设置参数: " + Build.DEVICE);
        Log.e(Constants.TAG, "显示屏参数: " + Build.DISPLAY);
        Log.e(Constants.TAG, "主机: " + Build.HOST);
        Log.e(Constants.TAG, "修订版本列表: " + Build.ID);
        Log.e(Constants.TAG, "硬件制造商: " + Build.MANUFACTURER);
        Log.e(Constants.TAG, "版本: " + Build.MODEL);
        Log.e(Constants.TAG, "手机制造商: " + Build.PRODUCT);
        Log.e(Constants.TAG, "描述build的标签: " + Build.TAGS);
        Log.e(Constants.TAG, "builder的类型: " + Build.TYPE);
        Log.e(Constants.TAG, "用户: " + Build.USER);
        String serial = "";
        try {
            //            serial = android.os.Build.class.getField("SERIAL").get(null).toString();
            serial = Build.SERIAL;
            main3_ch.setText(serial);
            //API>=9 使用serial号
            Log.e(Constants.TAG, "m_szDevIDShort: " + m_szDevIDShort);
            Log.e(Constants.TAG, "m_szDevIDShort_hash: " + m_szDevIDShort.hashCode());
            Log.e(Constants.TAG, "serial: " + serial);
            Log.e(Constants.TAG, "serial_hash: " + serial.hashCode());
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            Log.e(Constants.TAG, "获取失败: " + exception.getMessage());
            //serial需要一个初始化
            serial = "serial"; // 随便一个初始化
        }
        //使用硬件信息拼凑出来的15位号码
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

    private void initView() {
        main3_content = (TextView) findViewById(R.id.main3_content);
        main3_ch = (TextView) findViewById(R.id.main3_ch);
        main3_zlj = (TextView) findViewById(R.id.main3_zlj);
    }

    public String getDigestOfString(byte[] byteData) {
        return byteArrayToHexString(getDigestOfBytes(byteData));
    }

    // 计算sha-1摘要，返回相应的字节数组
    public byte[] getDigestOfBytes(byte[] byteData) {
        process_input_bytes(byteData);
        byte[] digest = new byte[20];
        for (int i = 0; i < digestInt.length; i++) {
            intToByteArray(digestInt[i], digest, i * 4);
        }
        return digest;
    }

    // 整数转换为4字节数组
    private void intToByteArray(int intValue, byte[] byteData, int i) {
        byteData[i] = (byte) (intValue >>> 24);
        byteData[i + 1] = (byte) (intValue >>> 16);
        byteData[i + 2] = (byte) (intValue >>> 8);
        byteData[i + 3] = (byte) intValue;
    }

    // 计算sha-1摘要
    private int process_input_bytes(byte[] bytedata) {
        // 初试化常量
        System.arraycopy(abcde, 0, digestInt, 0, abcde.length);
        // 格式化输入字节数组，补10及长度数据
        byte[] newbyte = byteArrayFormatData(bytedata);
        // 获取数据摘要计算的数据单元个数
        int MCount = newbyte.length / 64;
        // 循环对每个数据单元进行摘要计算
        for (int pos = 0; pos < MCount; pos++) {
            // 将每个单元的数据转换成16个整型数据，并保存到tmpData的前16个数组元素中
            for (int j = 0; j < 16; j++) {
                tmpData[j] = byteArrayToInt(newbyte, (pos * 64) + (j * 4));
            }
            // 摘要计算函数
            encrypt();
        }
        return 20;
    }

    // 4字节数组转换为整数
    private int byteArrayToInt(byte[] bytedata, int i) {
        return ((bytedata[i] & 0xff) << 24) | ((bytedata[i + 1] & 0xff) << 16)
                | ((bytedata[i + 2] & 0xff) << 8) | (bytedata[i + 3] & 0xff);
    }


    // 将字节数组转换为十六进制字符串
    private static String byteArrayToHexString(byte[] bytearray) {
        String strDigest = "";
        for (int i = 0; i < bytearray.length; i++) {
            strDigest += byteToHexString(bytearray[i]);
        }
        return strDigest;
    }


    // 格式化输入字节数组格式
    private byte[] byteArrayFormatData(byte[] bytedata) {
        // 补0数量
        int zeros = 0;
        // 补位后总位数
        int size = 0;
        // 原始数据长度
        int n = bytedata.length;
        // 模64后的剩余位数
        int m = n % 64;
        // 计算添加0的个数以及添加10后的总长度
        if (m < 56) {
            zeros = 55 - m;
            size = n - m + 64;
        } else if (m == 56) {
            zeros = 63;
            size = n + 8 + 64;
        } else {
            zeros = 63 - m + 56;
            size = (n + 64) - m + 64;
        }
        // 补位后生成的新数组内容
        byte[] newbyte = new byte[size];
        // 复制数组的前面部分
        System.arraycopy(bytedata, 0, newbyte, 0, n);
        // 获得数组Append数据元素的位置
        int l = n;
        // 补1操作
        newbyte[l++] = (byte) 0x80;
        // 补0操作
        for (int i = 0; i < zeros; i++) {
            newbyte[l++] = (byte) 0x00;
        }
        // 计算数据长度，补数据长度位共8字节，长整型
        long N = (long) n * 8;
        byte h8 = (byte) (N & 0xFF);
        byte h7 = (byte) ((N >> 8) & 0xFF);
        byte h6 = (byte) ((N >> 16) & 0xFF);
        byte h5 = (byte) ((N >> 24) & 0xFF);
        byte h4 = (byte) ((N >> 32) & 0xFF);
        byte h3 = (byte) ((N >> 40) & 0xFF);
        byte h2 = (byte) ((N >> 48) & 0xFF);
        byte h1 = (byte) (N >> 56);
        newbyte[l++] = h1;
        newbyte[l++] = h2;
        newbyte[l++] = h3;
        newbyte[l++] = h4;
        newbyte[l++] = h5;
        newbyte[l++] = h6;
        newbyte[l++] = h7;
        newbyte[l++] = h8;
        return newbyte;
    }

    // 单元摘要计算函数
    private void encrypt() {
        for (int i = 16; i <= 79; i++) {
            tmpData[i] = f4(tmpData[i - 3] ^ tmpData[i - 8] ^ tmpData[i - 14]
                    ^ tmpData[i - 16], 1);
        }
        int[] tmpabcde = new int[5];
        for (int i1 = 0; i1 < tmpabcde.length; i1++) {
            tmpabcde[i1] = digestInt[i1];
        }
        for (int j = 0; j <= 19; j++) {
            int tmp = f4(tmpabcde[0], 5)
                    + f1(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4]
                    + tmpData[j] + 0x5a827999;
            tmpabcde[4] = tmpabcde[3];
            tmpabcde[3] = tmpabcde[2];
            tmpabcde[2] = f4(tmpabcde[1], 30);
            tmpabcde[1] = tmpabcde[0];
            tmpabcde[0] = tmp;
        }
        for (int k = 20; k <= 39; k++) {
            int tmp = f4(tmpabcde[0], 5)
                    + f2(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4]
                    + tmpData[k] + 0x6ed9eba1;
            tmpabcde[4] = tmpabcde[3];
            tmpabcde[3] = tmpabcde[2];
            tmpabcde[2] = f4(tmpabcde[1], 30);
            tmpabcde[1] = tmpabcde[0];
            tmpabcde[0] = tmp;
        }
        for (int l = 40; l <= 59; l++) {
            int tmp = f4(tmpabcde[0], 5)
                    + f3(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4]
                    + tmpData[l] + 0x8f1bbcdc;
            tmpabcde[4] = tmpabcde[3];
            tmpabcde[3] = tmpabcde[2];
            tmpabcde[2] = f4(tmpabcde[1], 30);
            tmpabcde[1] = tmpabcde[0];
            tmpabcde[0] = tmp;
        }
        for (int m = 60; m <= 79; m++) {
            int tmp = f4(tmpabcde[0], 5)
                    + f2(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4]
                    + tmpData[m] + 0xca62c1d6;
            tmpabcde[4] = tmpabcde[3];
            tmpabcde[3] = tmpabcde[2];
            tmpabcde[2] = f4(tmpabcde[1], 30);
            tmpabcde[1] = tmpabcde[0];
            tmpabcde[0] = tmp;
        }
        for (int i2 = 0; i2 < tmpabcde.length; i2++) {
            digestInt[i2] = digestInt[i2] + tmpabcde[i2];
        }
        for (int n = 0; n < tmpData.length; n++) {
            tmpData[n] = 0;
        }
    }

    // 将字节转换为十六进制字符串
    private static String byteToHexString(byte ib) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
                'b', 'c', 'd', 'e', 'f'};
        char[] ob = new char[2];
        ob[0] = Digit[(ib >>> 4) & 0X0F];
        ob[1] = Digit[ib & 0X0F];
        String s = new String(ob);
        return s;
    }

    private int f1(int x, int y, int z) {
        return (x & y) | (~x & z);
    }

    private int f2(int x, int y, int z) {
        return x ^ y ^ z;
    }

    private int f3(int x, int y, int z) {
        return (x & y) | (x & z) | (y & z);
    }

    private int f4(int x, int y) {
        return (x << y) | x >>> (32 - y);
    }


    public static boolean checkByRegex(Object object, String regex) {
        if (checkStringValue(object)) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(object.toString());
            return matcher.matches();
        }
        return false;
    }

    public static boolean isIMEI(String str) {
        return checkByRegex(str, "^\\d{14,15}$");
    }

    public static boolean checkStringValue(Object object) {
        return (object instanceof String) && !object.toString().isEmpty();
    }
}
