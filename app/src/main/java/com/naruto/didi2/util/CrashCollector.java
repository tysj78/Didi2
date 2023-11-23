package com.naruto.didi2.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 运行时异常收集器
 * （用户未抓取，未做处理的异常会交由此对象做处理）
 * @author Administrator
 *
 */
public class CrashCollector implements UncaughtExceptionHandler {

	public static final String TAG = "CrashCollector";
	// CrashCollector实例   
	private static CrashCollector INSTANCE = new CrashCollector();
	// 系统默认的UncaughtException处理类 
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	// 程序的Context对象
	private Context mContext;
	// 用来存储设备信息和异常信息
	private Map<String, String> infos = new HashMap<String, String>();
	// 用于格式化日期,作为日志文件名的一部分
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
	//用户表示（可以是手机IMEI，登录用户名）
	private String nameString = "username";
	/** 保证只有一个CrashHandler实例 */
	private CrashCollector() {
	}
	SoftWareInfo softinfo = null;
	HardWareInfo hardwareinfo = null;
	String Exceptioninfo = "";
	/** 获取CrashHandler实例 ,单例模式 */
	public static CrashCollector getInstance() {
		return INSTANCE;
	}
	/**
	 * 初始化
	 *
	 * @param context
	 */
	public void init(Context context) {
		mContext = context;
		// 获取系统默认的UncaughtException处理器
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		// 设置该CrashHandler为程序的默认处理器
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
	/**
	 * 当UncaughtException发生时会转入该函数来处理
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		// TODO Auto-generated method stub
		if(nameString == null || "".equals(nameString)){
			nameString = "usernologin";
		}
		if (!handleException(ex) && mDefaultHandler != null) {
			// 如果用户没有处理则让系统默认的异常处理器来处理
			if(ex != null){
				String string = ex.toString();
				if(string.contains("com.olivephone.office")){ //第三方office库的崩溃异常不处理
					LogUtils.e("office crash-" + string);
					return;
				}
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				Log.e( "error : ", e.toString());
			}
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
//			try {
//				Thread.sleep(3000);
//			} catch (InterruptedException e) {
//				Log.e( "error : ", e.toString());
//			}


			boolean debug = isApkDebug(mContext);
			LogUtils.e("debug:" + debug);
			if(debug){
				// 退出程序
//				android.os.Process.killProcess(android.os.Process.myPid());
//				System.exit(1);

				LogUtils.e("退出程序 "+Thread.currentThread().getName());
			}

		}
	}
	/**
	 * 设置当前用户信息（用户名还是其它的自己定，注意保密性）
	 * @param str
	 */
	public void setCurrentUser(String str){
		this.nameString = str;
	}
	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
	 *
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false.
	 */
	private boolean handleException(Throwable ex) {
		// TODO Auto-generated method stub
		if (ex == null) {
			return false;
		}
		// 使用Toast来显示异常信息
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				Toast.makeText(mContext, "程序出现异常,正在收集日志", Toast.LENGTH_LONG).show();
				Looper.loop();
			}
		}.start();

		collectHardWareInfo(mContext);
		collectSoftWareInfo(mContext);
		collectExceptionInfo(mContext,ex);

		try {
			saveInfosToFile(mContext);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogUtils.e(e.toString()	);
		}
		return true;
	}
	/**
	 * 收集手机硬件信息
	 * @param con
	 */
	private void collectHardWareInfo(Context con) {
		// TODO Auto-generated method stub
		if(hardwareinfo != null){
			return;
		}
		hardwareinfo = new HardWareInfo();
		hardwareinfo.BOARD = Build.BOARD;
		hardwareinfo.BRAND = Build.BRAND;
		hardwareinfo.CODENAME = Build.VERSION.CODENAME;
		hardwareinfo.CPU_ABI = Build.CPU_ABI;
		hardwareinfo.CPU_ABI2 = Build.CPU_ABI2;
		hardwareinfo.DEVICE = Build.DEVICE;
		hardwareinfo.DISPLAY = Build.DISPLAY;
		hardwareinfo.FINGERPRINT = Build.FINGERPRINT;
		hardwareinfo.HARDWARE = Build.HARDWARE;
		hardwareinfo.HOST = Build.HOST;
		hardwareinfo.ID = Build.ID;
		hardwareinfo.INCREMENTAL = Build.VERSION.INCREMENTAL;
		hardwareinfo.MANUFACTURER = Build.MANUFACTURER;
		hardwareinfo.MODEL = Build.MODEL;
		hardwareinfo.PRODUCT = Build.PRODUCT;
		hardwareinfo.RELEASE = Build.VERSION.RELEASE;
		hardwareinfo.SDK = Build.VERSION.SDK;
		hardwareinfo.SDK_INT = String.valueOf(Build.VERSION.SDK_INT);
		hardwareinfo.TAGS = Build.TAGS;
		hardwareinfo.TIME = formatter.format(new Date(Build.TIME));
		hardwareinfo.TYPE = Build.TYPE;
		hardwareinfo.USER = Build.USER;
	}
	/**
	 * 收集手机软件信息
	 * @param con
	 */
	private void collectSoftWareInfo(Context con) {
		// TODO Auto-generated method stub
		if(softinfo != null){
			return;
		}
		softinfo = new SoftWareInfo();
		try {
			PackageManager pm = con.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(con.getPackageName(),
					PackageManager.GET_ACTIVITIES);
			if(pi != null){
				softinfo.SOFTNAME = pi.applicationInfo.name;
				softinfo.VERSIONNAME = pi.versionName;
				softinfo.VERSIONCODE = String.valueOf(pi.versionCode);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	/**
	 * 收集异常信息
	 * @param con
	 * @param ex
	 * @throws Exception
	 * @throws Exception
	 */
	private void collectExceptionInfo(Context con, Throwable ex){
		// TODO Auto-generated method stub
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		Exceptioninfo = writer.toString();
	}
	/**
	 * 保存错误日志到文件
	 * @param con
	 * @throws Exception
	 */
	private void saveInfosToFile(Context con) throws Exception {
		// TODO Auto-generated method stub
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			String path = Environment.getExternalStorageDirectory().getPath() +  File.separator + "didi2_crashFolder";
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File file = new File(dir, "Crash.txt");
			if(!file.exists()){
				file.createNewFile();
				writeToTxtFile(file);
			}else{
				writeToTxtFile(file);
			}
		}
	}

	/**
	 * 将日志信息写入txt文件
	 * @param file
	 */
	private void writeToTxtFile(File file) {
		// TODO Auto-generated method stub
		//生成文件夹之后，再生成文件，不然会出错

		String strFilePath = file.getAbsolutePath();
		// 每次写入时，都换行写
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(  "/*****************************"
				+ "日期：" + new Date().toLocaleString()
				+ "****************************************/\n");
		stringBuffer.append("用户：" + nameString + "\n");
		stringBuffer.append("硬件：" + hardwareinfo.toString() + "\n");
		stringBuffer.append("软件：" + softinfo.toString() + "\n");
		stringBuffer.append("错误日志：" + Exceptioninfo + "\r\n");
		try {
			RandomAccessFile raf = new RandomAccessFile(file, "rwd");
			raf.seek(file.length());
			raf.write(stringBuffer.toString().getBytes("UTF-8"));
			raf.close();
		} catch (Exception e) {
			Log.e( "error : ",e.getMessage());
		}
	}
	/**
	 * 设备硬件参数
	 * @author Administrator
	 *
	 */
	public class HardWareInfo implements Serializable{

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		public String BOARD = "";//主板信息
		public String HARDWARE = "";// USER
		public String BRAND = "";//android系统定制商
		public String PRODUCT = ""; // 手机制造商 
		public String CPU_ABI = "";//CPU指令集
		public String CPU_ABI2 = "";//CPU指令集2
		public String DEVICE = "";// 设备参数  
		public String DISPLAY = "";// 显示屏参数
		public String FINGERPRINT  = "";//  硬件名称
		public String HOST = "";// HOST
		public String ID = ""; // 修订版本列表 
		public String MANUFACTURER = "";// 硬件制造商
		public String MODEL = "";// 版本
		public String TAGS = "";// build描述标签
		public String TIME = "";// TIME
		public String TYPE = "";// buildTYPE
		public String USER = "";// USER

		public String SDK = "";// SDK
		public String CODENAME = "";// CODENAME
		public String INCREMENTAL = "";// INCREMENTAL
		public String RELEASE = "";// RELEASE
		public String SDK_INT = "";// SDK_INT
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "BOARD:_" + BOARD + "\n" +
					"HARDWARE:_" + HARDWARE + "\n" +
					"PRODUCT:_" + PRODUCT + "\n" +
					"CPU_ABI:_" + CPU_ABI + "\n" +
					"CPU_ABI2:_" + CPU_ABI2 + "\n" +
					"DEVICE:_" + DEVICE + "\n" +
					"DISPLAY:_" + DISPLAY + "\n" +
					"FINGERPRINT:_" + FINGERPRINT + "\n" +
					"HOST:_" + HOST + "\n" +
					"ID:_" + ID + "\n" +
					"MANUFACTURER:_" + MANUFACTURER + "\n" +
					"MODEL:_" + MODEL + "\n" +
					"TAGS:_" + TAGS + "\n" +
					"TIME:_" + TIME + "\n" +
					"TYPE:_" + TYPE + "\n" +
					"USER:_" + USER + "\n" +
					"SDK:_" + SDK + "\n" +
					"CODENAME:_" + CODENAME + "\n" +
					"INCREMENTAL:_" + INCREMENTAL + "\n" +
					"RELEASE:_" + RELEASE + "\n" +
					"SDK_INT:_" + SDK_INT ;
		}


	}
	/**
	 * 软件信息
	 * @author Administrator
	 *
	 */
	public class SoftWareInfo implements Serializable{

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		public String SOFTNAME = "";//appname
		public String VERSIONNAME = "";//versionname
		public String VERSIONCODE = "";//versioncode
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "SOFTNAME:_" + SOFTNAME + "\n" +
					"VERSIONNAME:_" + VERSIONNAME + "\n" +
					"VERSIONCODE:_" + VERSIONCODE + "\n";
		}


	}

	/**
	 * 判断当前应用是debug版本还是release版本
	 *
	 * @param context
	 * @return
	 */
	public boolean isApkDebug(Context context) {
		try {
			ApplicationInfo info = context.getApplicationInfo();
			return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
		} catch (Exception e) {
			return false;
		}
	}

}
