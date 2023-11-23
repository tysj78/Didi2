package com.naruto.didi2.hook;

import android.os.Environment;
import android.util.Log;


import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wzl on 7/9/19.
 */

public class MsmLog {

    private static Boolean debug = true;
    private static String logfile;
    private static boolean initLogFile = false;
    private final static String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath();

    static {
        //external start log
        try {
            String extlogpath = Os.getSdard()+"/msmlog/log";
            File el = new File(extlogpath);
            if(el.exists())
            {
                initDebugInfo(extlogpath);
            }
        }catch (Exception e){
            ;
        }
    }

    private static String getTag(){
        String tag = "";
        try{
            StackTraceElement[] elements = new Throwable().getStackTrace();
            String className = elements[2].getClassName();
            tag = className.substring(className.lastIndexOf(".")+1);
            if(tag.length() > 23)
                tag = tag.substring(0, 22);
        }catch (Exception e){
            ;
        }

        return tag;
    }

    public static void print(String log){
        if(debug && log != null) {
            Log.w("MsmLog", getTag() + ", " + log);
            output2file(getTag() + ", " + log);
        }
    }

    public static void print(Exception e){
        print(Log.getStackTraceString(e));
    }

    public static void print(String log, Throwable tr){
        if(debug && log != null) {
            Log.w("MsmLog", log, tr);
            output2file(getTag() + ", " + log);
        }
    }

    public static void setStatus(Boolean p)
    {
        Log.w("MsmLog", "msm log status "+p);
        debug = p;
    }

    public static boolean debug()
    {
        return debug;
    }

    private static synchronized void output2file(String content)
    {
        try {
            if(!initLogFile)
            {
                String msmlog = sdcard+"/msmlog";
                File fm = new File(msmlog);
                if(!fm.exists()){
                    fm.mkdir();
                }

                SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
                sdf.applyPattern("yyyy-MM-dd");
                logfile = msmlog+"/"+ GlobalData.packageName+"_"+sdf.format(new Date())+".log";

                File fl = new File(logfile);
                if(!fl.exists())
                    fl.createNewFile();

                initLogFile = true;
            }

            appandFile(logfile, Os.getCurrentTime() + " " + content + "\n");
        }catch (Exception e){
            ;
        }
    }

    public static void appandFile(String fileName, String content){
        try {
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            ;//e.printStackTrace();
        }
    }
    public static String getCurrentCallStack(){
        String stack = "";
        try{
            stack = Log.getStackTraceString(new Throwable());
        }catch (Exception e){
            ;
        }

        return  stack;
    }

    public static String readFileExclusive(String srcFileName)
    {
        if(MsmLog.debug())
            MsmLog.print("readFileExclusive " + srcFileName);
        StringBuffer strBuf = new StringBuffer();
        try {
            do {
                File inputFile = new File(srcFileName);
                if(!inputFile.exists()){
                    MsmLog.print(srcFileName + " isn't exists");
                    break;
                }

                FileInputStream fis = null;
                fis = new FileInputStream(inputFile);

                byte[] buffer = new byte[1024*1024*5];//500k
                int len = 0;
                while((len=fis.read(buffer)) != -1){
                    strBuf.append(new String(subArray(buffer, len)));
                }

                fis.close();
                inputFile = null;
            }while(false);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        MsmLog.print("readFileExclusive "+ strBuf.toString());
        return strBuf.toString();
    }

    private static byte[] subArray(byte[] a, int len)
    {
        if(len>= a.length)
            return a;

        byte[] resA = new byte[len];
        System.arraycopy(a, 0, resA, 0, len);

        return resA;
    }

    public static void initDebugInfo(String policyfilePath) {
        try {
            String jsonCfg = readFileExclusive(policyfilePath);
            if(jsonCfg.length() != 0)
            {
                JSONObject json = new JSONObject(jsonCfg);
                Object value = json.get("S_MSM_LOG");

                if (value!=null && value instanceof Integer) {
                    if ((Integer)value == 1) {
                        MsmLog.setStatus(true);
                    }else
                    {
                        MsmLog.setStatus(false);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}