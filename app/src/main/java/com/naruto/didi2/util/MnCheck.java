package com.naruto.didi2.util;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.text.TextUtils;
import android.util.Log;

import com.naruto.didi2.bean.MnInfo;

/**
 * Created by yangyong on 2019/12/20/0020.
 */

public class MnCheck {
    private static final String TAG = "yy";

    /**
     * 当前手机是否为模拟器环境checkEmulator
     *
     * @param context
     * @return
     */
    public static MnInfo checkEmulator(Context context) {
        if (context == null)
            Log.e(TAG, "context must not be null");

        try {
            int suspectCount = 0;
            StringBuilder content = new StringBuilder();

            //1
            String baseBandVersion = getProperty("gsm.version.baseband");
            if (null == baseBandVersion || baseBandVersion.contains("1.0.0.0"))
                ++suspectCount;

            //2
            String buildFlavor = getProperty("ro.build.flavor");
            if (null == buildFlavor || buildFlavor.contains("vbox") || buildFlavor.contains("sdk_gphone"))
                ++suspectCount;

            //3
            String productBoard = getProperty("ro.product.board");
            if (null == productBoard || productBoard.contains("android") | productBoard.contains("goldfish"))
                ++suspectCount;

            //4
            String boardPlatform = getProperty("ro.board.platform");
            if (null == boardPlatform || boardPlatform.contains("android"))
                ++suspectCount;

            //5
            String hardWare = getProperty("ro.hardware");
            if (null == hardWare) ++suspectCount;
            else if (hardWare.toLowerCase().contains("ttvm")) suspectCount += 10;
            else if (hardWare.toLowerCase().contains("nox")) suspectCount += 10;

            //6
            String cameraFlash;
            String sensorNum = "sensorNum";
            boolean isSupportCameraFlash = context.getPackageManager().hasSystemFeature("android.hardware.camera.flash");
            if (!isSupportCameraFlash) ++suspectCount;
            cameraFlash = isSupportCameraFlash ? "support CameraFlash" : "unsupport CameraFlash";

            //7
            SensorManager sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            int sensorSize = sm.getSensorList(Sensor.TYPE_ALL).size();
            if (sensorSize < 7) ++suspectCount;
            sensorNum = sensorNum + sensorSize;


//        String userApps = CommandUtil.getSingleInstance().exec("pm list package -3");
//        String userAppNum = "userAppNum";
//        int userAppSize = getUserAppNum(userApps);
//        if (userAppSize < 5) ++suspectCount;
//        userAppNum = userAppNum + userAppSize;

            String filter = CommandUtil.getSingleInstance().exec("cat /proc/self/cgroup");
            if (null == filter) ++suspectCount;


            content.append("\n" + "baseBandVersion:" + baseBandVersion)
                    .append("\n" + "buildFlavor:" + buildFlavor)
                    .append("\n" + "productBoard:" + productBoard)
                    .append("\n" + "boardPlatform:" + boardPlatform)
                    .append("\n" + "hardWare:" + hardWare)
                    .append("\n" + "isSupportCameraFlash:" + isSupportCameraFlash)
                    .append("\n" + "sensorSize:" + sensorSize);
            return new MnInfo(suspectCount > 3, content.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static String getProperty(String propName) {
        String property = CommandUtil.getSingleInstance().getProperty(propName);
        return TextUtils.isEmpty(property) ? null : property;
    }
}
