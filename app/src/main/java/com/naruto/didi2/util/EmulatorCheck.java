package com.naruto.didi2.util;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.text.TextUtils;

import com.naruto.didi2.bean.CheckResult;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

/**
 * Created by yangyong on 2019/12/17/0017.
 */

public class EmulatorCheck {
    private EmulatorCheck(){}

    private static class SingletonHolder{
        private SingletonHolder(){}
        private static final EmulatorCheck INSTANCE=new EmulatorCheck();
    }

    public static final EmulatorCheck getSingleInstance(){
        return SingletonHolder.INSTANCE;
    }

    public boolean readSysProperty(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        } else {
            //模拟器分值
            int suspectCount = 0;
            //取硬件信息
            CheckResult hardwareResult = this.checkFeaturesByHardware();
            switch (hardwareResult.result) {
                case 0:
                    ++suspectCount;
                default:
                    CheckResult flavorResult = this.checkFeaturesByFlavor();
                    switch (flavorResult.result) {
                        case 0:
                            ++suspectCount;
                        default:
                            CheckResult modelResult = this.checkFeaturesByModel();
                            switch (modelResult.result) {
                                case 0:
                                    ++suspectCount;
                                default:
                                    CheckResult manufacturerResult = this.checkFeaturesByManufacturer();
                                    switch (manufacturerResult.result) {
                                        case 0:
                                            ++suspectCount;
                                        default:
                                            CheckResult boardResult = this.checkFeaturesByBoard();
                                            switch (boardResult.result) {
                                                case 0:
                                                    ++suspectCount;
                                                default:
                                                    CheckResult platformResult = this.checkFeaturesByPlatform();
                                                    switch (platformResult.result) {
                                                        case 0:
                                                            ++suspectCount;
                                                        default:
                                                            CheckResult baseBandResult = this.checkFeaturesByBaseBand();
                                                            switch (baseBandResult.result) {
                                                                case 0:
                                                                    suspectCount += 2;
                                                                default:
                                                                    int sensorNumber = this.getSensorNumber(context);
                                                                    if (sensorNumber <= 7) {
                                                                        ++suspectCount;
                                                                    }

                                                                    int userAppNumber = this.getUserAppNumber();
                                                                    if (userAppNumber <= 5) {
                                                                        ++suspectCount;
                                                                    }

                                                                    boolean supportCameraFlash = this.supportCameraFlash(context);
                                                                    if (!supportCameraFlash) {
                                                                        ++suspectCount;
                                                                    }

                                                                    boolean supportCamera = this.supportCamera(context);
                                                                    if (!supportCamera) {
                                                                        ++suspectCount;
                                                                    }

                                                                    boolean supportBluetooth = this.supportBluetooth(context);
                                                                    if (!supportBluetooth) {
                                                                        ++suspectCount;
                                                                    }

                                                                    boolean hasLightSensor = this.hasLightSensor(context);
                                                                    if (!hasLightSensor) {
                                                                        ++suspectCount;
                                                                    }

                                                                    CheckResult cgroupResult = this.checkFeaturesByCgroup();
                                                                    if (cgroupResult.result == 0) {
                                                                        ++suspectCount;
                                                                    }
                                                            }
                                                            return suspectCount > 3;
                                                    }
                                            }
                                    }
                            }
                    }
            }
        }
    }

    private CheckResult checkFeaturesByHardware() {
        String hardware = this.getProperty("ro.hardware");
        if (null == hardware) {
            return new CheckResult(0, (String) null);
        } else {
            String tempValue = hardware.toLowerCase();
            byte var5 = -1;
            switch (tempValue.hashCode()) {
                case -1367724016:
                    if (tempValue.equals("cancro")) {
                        var5 = 2;
                    }
                    break;
                case -822798509:
                    if (tempValue.equals("vbox86")) {
                        var5 = 5;
                    }
                    break;
                case 109271:
                    if (tempValue.equals("nox")) {
                        var5 = 1;
                    }
                    break;
                case 3570999:
                    if (tempValue.equals("ttvm")) {
                        var5 = 0;
                    }
                    break;
                case 3613077:
                    if (tempValue.equals("vbox")) {
                        var5 = 4;
                    }
                    break;
                case 100361430:
                    if (tempValue.equals("intel")) {
                        var5 = 3;
                    }
                    break;
                case 937844646:
                    if (tempValue.equals("android_x86")) {
                        var5 = 6;
                    }
            }

            byte result;
            switch (var5) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    result = 1;
                    break;
                default:
                    result = 2;
            }

            return new CheckResult(result, hardware);
        }
    }

    private String getProperty(String propName) {
        String property = getProperty1(propName);
        return TextUtils.isEmpty(property) ? null : property;
    }

    public String getProperty1(String propName) {
        String value = null;

        try {
            Object roSecureObj = Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class}).invoke((Object)null, new Object[]{propName});
            if(roSecureObj != null) {
                value = (String)roSecureObj;
            }

            return value;
        } catch (Exception var8) {
            value = null;
            return value;
        } finally {
            ;
        }
    }

    private CheckResult checkFeaturesByFlavor() {
        String flavor = this.getProperty("ro.build.flavor");
        if (null == flavor) {
            return new CheckResult(0, (String) null);
        } else {
            String tempValue = flavor.toLowerCase();
            byte result;
            if (tempValue.contains("vbox")) {
                result = 1;
            } else if (tempValue.contains("sdk_gphone")) {
                result = 1;
            } else {
                result = 2;
            }

            return new CheckResult(result, flavor);
        }
    }

    private CheckResult checkFeaturesByModel() {
        String model = this.getProperty("ro.product.model");
        if (null == model) {
            return new CheckResult(0, (String) null);
        } else {
            String tempValue = model.toLowerCase();
            byte result;
            if (tempValue.contains("google_sdk")) {
                result = 1;
            } else if (tempValue.contains("emulator")) {
                result = 1;
            } else if (tempValue.contains("android sdk built for x86")) {
                result = 1;
            } else {
                result = 2;
            }

            return new CheckResult(result, model);
        }
    }

    private CheckResult checkFeaturesByManufacturer() {
        String manufacturer = this.getProperty("ro.product.manufacturer");
        if (null == manufacturer) {
            return new CheckResult(0, (String) null);
        } else {
            String tempValue = manufacturer.toLowerCase();
            byte result;
            if (tempValue.contains("genymotion")) {
                result = 1;
            } else if (tempValue.contains("netease")) {
                result = 1;
            } else {
                result = 2;
            }

            return new CheckResult(result, manufacturer);
        }
    }

    private CheckResult checkFeaturesByBoard() {
        String board = this.getProperty("ro.product.board");
        if (null == board) {
            return new CheckResult(0, (String) null);
        } else {
            String tempValue = board.toLowerCase();
            byte result;
            if (tempValue.contains("android")) {
                result = 1;
            } else if (tempValue.contains("goldfish")) {
                result = 1;
            } else {
                result = 2;
            }

            return new CheckResult(result, board);
        }
    }

    private CheckResult checkFeaturesByPlatform() {
        String platform = this.getProperty("ro.board.platform");
        if (null == platform) {
            return new CheckResult(0, (String) null);
        } else {
            String tempValue = platform.toLowerCase();
            byte result;
            if (tempValue.contains("android")) {
                result = 1;
            } else {
                result = 2;
            }

            return new CheckResult(result, platform);
        }
    }

    private CheckResult checkFeaturesByBaseBand() {
        String baseBandVersion = this.getProperty("gsm.version.baseband");
        if (null == baseBandVersion) {
            return new CheckResult(0, (String) null);
        } else {
            byte result;
            if (baseBandVersion.contains("1.0.0.0")) {
                result = 1;
            } else {
                result = 2;
            }

            return new CheckResult(result, baseBandVersion);
        }
    }


    private int getSensorNumber(Context context) {
        SensorManager sm = (SensorManager) context.getSystemService("sensor");
        return sm.getSensorList(-1).size();
    }

    private int getUserAppNumber() {
        String userApps = CommandUtil.getSingleInstance().exec("pm list package -3");
        return this.getUserAppNum(userApps);
    }

    private boolean supportCamera(Context context) {
        return context.getPackageManager().hasSystemFeature("android.hardware.camera");
    }

    private boolean supportCameraFlash(Context context) {
        return context.getPackageManager().hasSystemFeature("android.hardware.camera.flash");
    }

    private boolean supportBluetooth(Context context) {
        return context.getPackageManager().hasSystemFeature("android.hardware.bluetooth");
    }

    private boolean hasLightSensor(Context context) {
        SensorManager sensorManager = (SensorManager) context.getSystemService("sensor");
        Sensor sensor = sensorManager.getDefaultSensor(5);
        return null != sensor;
    }

    private CheckResult checkFeaturesByCgroup() {
        String filter = CommandUtil.getSingleInstance().exec("cat /proc/self/cgroup");
        return null == filter ? new CheckResult(0, (String) null) : new CheckResult(2, filter);
    }

    private int getUserAppNum(String userApps) {
        if (TextUtils.isEmpty(userApps)) {
            return 0;
        } else {
            String[] result = userApps.split("package:");
            return result.length;
        }
    }

    public String exec(String command) {
        BufferedOutputStream bufferedOutputStream = null;
        BufferedInputStream bufferedInputStream = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("sh");
            bufferedOutputStream = new BufferedOutputStream(process.getOutputStream());

            bufferedInputStream = new BufferedInputStream(process.getInputStream());
            bufferedOutputStream.write(command.getBytes());
            bufferedOutputStream.write('\n');
            bufferedOutputStream.flush();
            bufferedOutputStream.close();

            process.waitFor();

            String outputStr = getStrFromBufferInputSteam(bufferedInputStream);
            return outputStr;
        } catch (Exception e) {
            return null;
        } finally {
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (process != null) {
                process.destroy();
            }
        }
    }

    private static String getStrFromBufferInputSteam(BufferedInputStream bufferedInputStream) {
        if (null == bufferedInputStream) {
            return "";
        }
        int BUFFER_SIZE = 512;
        byte[] buffer = new byte[BUFFER_SIZE];
        StringBuilder result = new StringBuilder();
        try {
            while (true) {
                int read = bufferedInputStream.read(buffer);
                if (read > 0) {
                    result.append(new String(buffer, 0, read));
                }
                if (read < BUFFER_SIZE) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
