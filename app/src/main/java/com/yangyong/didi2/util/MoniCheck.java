//package com.yangyong.didi2.util;
//
//import android.content.Context;
//import android.hardware.Sensor;
//import android.hardware.SensorManager;
//
//import static java.security.Security.getProperty;
//
///**
// * Created by yangyong on 2019/11/25/0025.
// */
//
//public class MoniCheck {
//
//    public boolean readSysProperty() {
//        int suspectCount = 0;
//
//        String baseBandVersion = getProperty("gsm.version.baseband");
//        if (null == baseBandVersion || baseBandVersion.contains("1.0.0.0"))
//            ++suspectCount;//基带信息
//
//        String buildFlavor = getProperty("ro.build.flavor");
//        if (null == buildFlavor || buildFlavor.contains("vbox") || buildFlavor.contains("sdk_gphone"))
//            ++suspectCount;//渠道
//
//        String productBoard = getProperty("ro.product.board");
//        if (null == productBoard || productBoard.contains("android") | productBoard.contains("goldfish"))
//            ++suspectCount;//芯片
//
//        String boardPlatform = getProperty("ro.board.platform");
//        if (null == boardPlatform || boardPlatform.contains("android"))
//            ++suspectCount;//芯片平台
//
//        String hardWare = getProperty("ro.hardware");
//        if (null == hardWare) ++suspectCount;
//        else if (hardWare.toLowerCase().contains("ttvm")) suspectCount += 10;//天天
//        else if (hardWare.toLowerCase().contains("nox")) suspectCount += 10;//夜神
//
//        String cameraFlash = "";
//        String sensorNum = "sensorNum";
//        if (context != null) {
//            boolean isSupportCameraFlash = context.getPackageManager().hasSystemFeature("android.hardware.camera.flash");//是否支持闪光灯
//            if (!isSupportCameraFlash) ++suspectCount;
//            cameraFlash = isSupportCameraFlash ? "support CameraFlash" : "unsupport CameraFlash";
//
//            SensorManager sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
//            int sensorSize = sm.getSensorList(Sensor.TYPE_ALL).size();
//            if (sensorSize < 7) ++suspectCount;//传感器个数
//            sensorNum = sensorNum + sensorSize;
//        }
//
//        String userApps = CommandUtil.getSingleInstance().exec("pm list package -3");
//        String userAppNum = "userAppNum";
//        int userAppSize = getUserAppNums(userApps);
//        if (userAppSize < 5) ++suspectCount;//用户安装的app个数
//        userAppNum = userAppNum + userAppSize;
//
//        String filter = CommandUtil.getSingleInstance().exec("cat /proc/self/cgroup");
//        if (null == filter) ++suspectCount;//进程租
//
//        if (emulatorCheckCallback != null) {
//            StringBuffer stringBuffer = new StringBuffer("ceshi start|")
//                    .append(baseBandVersion).append("|")
//                    .append(buildFlavor).append("|")
//                    .append(productBoard).append("|")
//                    .append(boardPlatform).append("|")
//                    .append(hardWare).append("|")
//                    .append(cameraFlash).append("|")
//                    .append(sensorNum).append("|")
//                    .append(userAppNum).append("|")
//                    .append(filter).append("|end");
//            emulatorCheckCallback.findEmulator(stringBuffer.toString());
//            emulatorCheckCallback = null;
//        }
//        return suspectCount > 3;
//    }
//}
