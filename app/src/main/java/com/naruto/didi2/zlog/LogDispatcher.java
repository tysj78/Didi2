package com.naruto.didi2.zlog;

import android.os.Process;
import android.util.Log;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 消费线程，取出队列日志，存文件
 */

class LogDispatcher extends Thread {

    private static final String TAG = "NoticeDispatcher";

    private final int MAX_LOG_SIZE = 1024 * 1024;

    private static final int BUFFER_LEN = 8192;

    private static int logCount = 0;
    /**
     * 存储日志的队列
     */
    private LinkedBlockingQueue<LogBean> mLogQueue;
    private String mLogDir;


    LogDispatcher(LinkedBlockingQueue<LogBean> logQueue, String logDir) {
        this.mLogQueue = logQueue;
        this.mLogDir = logDir;
    }

    @Override
    public void run() {
//        boolean isClearedExpiredLogFile = false;

        try {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            while (true) {
//                if (!isClearedExpiredLogFile) {
//                    clearExpiredLogFile();
//                    isClearedExpiredLogFile = true;
//                }

                try {
                    LogBean logBean;
                    logBean = mLogQueue.take();
                    saveTextToFile(getLogFilePath(logBean.getLogType()), logBean.getLogText());
                    logCount++;
                    if (logCount > 50) {
                        Log.e("yy", "消费线程运行中: " );
//                        Log.e(TAG, "大于3000条");
//                        //每隔一天压缩一次log
//                        if (!diffDate()) {
//                            zipFile();
//                        }
                        logCount = 0;
                    }
                } catch (InterruptedException e) {
                    Log.e("run: ", e.toString());
                }
            }
        } catch (Exception e) {
            Log.e("run: ", e.toString());
        }
    }

//    private boolean diffDate() {
//        try {
//            String currentDate = getCurrentDate();
//            String logZipDate = SharedPreferencesUtils.getIntance().getLogZipDate(SharedPreferencesUtils.LOGZIPDATE);
//            if (currentDate.equals(logZipDate)) {
//                return true;
//            }
//        } catch (Exception e) {
//            Log.e(TAG, "diffDate出现异常===: " + e.fillInStackTrace());
//        }
//        return false;
//    }

    /**
     * 获取文件夹大小
     *
     * @param file File实例
     * @return long
     */
    private long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) size = size + getFolderSize(fileList[i]);
                else size = size + fileList[i].length();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }


    /**
     * 普通日志文件名： log1.txt；
     * 错误日志文件名：errorLog1.txt;
     * 每个日志文件最大为 1Mb，超过 1Mb 文件名累加 1.
     *
     * @param logType 日志类型
     * @return 文件绝对路径
     */
    private String getLogFilePath(LogType logType) {
        String returnFileName = "";
        try {
//            switch (logType) {
//                case ERROR: {
//                    returnFileName = getLastLogFileName(mLogDir, "errorLog");
//                    break;
//                }
//                case INFO:
//                case WTF:
//                case DEBUG: {
//                    returnFileName = getLastLogFileName(mLogDir, "log");
//                    break;
//                }
//                case CRASH: {
//                    returnFileName = getLastLogFileName(mLogDir, "crash");
//                    break;
//                }
//            }
            returnFileName = getLastLogFileName(mLogDir, getCurrentDate());
//            Log.e(TAG, "开始储存的log: " + returnFileName);
        } catch (Exception e) {
            Log.e("getLogFile: ", e.toString());
        }
        return returnFileName;
    }

    /**
     * 根据目录路径及文件前缀获取最后一个该日志文件；
     * 比如当前文件夹下有log1.txt, log2.txt, log3.txt,则返回 log3.txt;
     * 当文件个数为9，且最后一个文件大于1M，则清除Log文件
     *
     * @param dir     文件路径
     * @param logName 文件名开头，如 log
     * @return 文件名
     */
    /*private String getLastLogFileName(String dir, String logName) {
        String returnFileName = String.format("%s/%s1.txt", dir, logName);
        File file = new File(dir);
        if (file.exists()) {
            String[] fileArray = file.list();
            if (fileArray != null && fileArray.length > 0) {
                List<String> logList = new ArrayList<>();
                for (String s : fileArray) {
                    if (s.startsWith(logName) && s.length() == logName.length() + 5) {
                        logList.add(s);
                    }
                }
                if (!logList.isEmpty()) {
                    Collections.sort(logList);
                    String lastFileName = logList.get(logList.size() - 1);
                    if (new File(String.format("%s/%s", dir, lastFileName)).length() > MAX_LOG_SIZE) {
                        if (lastFileName.contains(".")) {
                            int LastLogCount = Integer.valueOf(lastFileName.split("\\.")[0].replaceAll(logName, "").trim());
                            if (LastLogCount > 8) {
                                try {
                                    for (String itemFileName : logList) {
                                        File itemFile = new File(String.format("%s/%s", dir, itemFileName));
                                        itemFile.delete();
                                    }
                                } catch (Exception e) {
                                    Log.e("getLastLogFileName: ", e.toString());
                                }
                                returnFileName = String.format("%s/%s1.txt", dir, logName);
                            } else {
                                LastLogCount++;
                                returnFileName = String.format("%s/%s%s.txt", dir, logName, LastLogCount);
                            }
                        }
                    } else {
                        returnFileName = String.format("%s/%s", dir, lastFileName);
                    }
                }
            }
        }
        return returnFileName;
    }*/
    private String getLastLogFileName(String dir, String logName) {
        String returnFileName = String.format("%s/%s.txt", dir, logName);
//        File file = new File(dir);
//        if (file.exists()) {
//            String[] fileArray = file.list();
//            if (fileArray != null && fileArray.length > 0) {
//                List<String> logList = new ArrayList<>();
//                for (String s : fileArray) {
//                    if (s.startsWith(logName) && s.length() == logName.length() + 5) {
//                        logList.add(s);
//                    }
//                }
//                if (!logList.isEmpty()) {
//                    Collections.sort(logList);
//                    String lastFileName = logList.get(logList.size() - 1);
//                    if (new File(String.format("%s/%s", dir, lastFileName)).length() > MAX_LOG_SIZE) {
//                        if (lastFileName.contains(".")) {
//                            int LastLogCount = Integer.valueOf(lastFileName.split("\\.")[0].replaceAll(logName, "").trim());
//                            if (LastLogCount > 8) {
//                                try {
//                                    for (String itemFileName : logList) {
//                                        File itemFile = new File(String.format("%s/%s", dir, itemFileName));
//                                        itemFile.delete();
//                                    }
//                                } catch (Exception e) {
//                                    Log.e("getLastLogFileName: ", e.toString());
//                                }
//                                returnFileName = String.format("%s/%s1.txt", dir, logName);
//                            } else {
//                                LastLogCount++;
//                                returnFileName = String.format("%s/%s%s.txt", dir, logName, LastLogCount);
//                            }
//                        }
//                    } else {
//                        returnFileName = String.format("%s/%s", dir, lastFileName);
//                    }
//                }
//            }
//        }
        return returnFileName;
    }

    /**
     * 将文本追加到到文件末尾;
     * 文件不存在会创建文件；
     * 父目录不存在会创建父目录，
     * 会判断三级以内的目录是否存在，不存在则创建
     *
     * @param filePath 文件绝对路径（包含文件名）
     * @param text     需要保存的文本
     */
    private void saveTextToFile(String filePath, String text) {
        boolean isNeedClear = false;

        try {
            File file = new File(filePath);
            if (!new File(file.getParent()).exists()) {
                File parentFile1 = new File(file.getParent());
                if (!parentFile1.exists()) {
                    File parentFile2 = new File(parentFile1.getParent());
                    if (!parentFile2.exists()) {
                        parentFile2.mkdir();
                    }
                    parentFile1.mkdir();
                }
                new File(file.getParent()).mkdir();
            }
            if (!file.exists()) {
                file.createNewFile();

                isNeedClear = true;
            }
            FileWriter writer = new FileWriter(file, true);
            writer.write(text);
            writer.close();
        } catch (Exception e) {
            Log.e("saveTextToFile: ", e.toString());
        }

        if (isNeedClear == true) {
            clearExpiredLogFile();
        }
    }

    //获取当前日期
    private String getCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    private void clearExpiredLogFile() {
        //获取目录下所有文件
        List<File> allFile = getDirAllFile(new File(mLogDir));
        int size = allFile.size();
        if (size >= 8) {
            for (int i = size - 8; i >= 0; i--) {
                File file = allFile.get(i);
                deleteFile(file);
            }
        }
    }


    //删除文件夹下所有文件
    private static void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                deleteFile(f);
            }
        } else if (file.exists()) {
            file.delete();
        }
    }

    //获取指定目录下一级文件
    private static List<File> getDirAllFile(File file) {
        List<File> fileList = new ArrayList<>();
        File[] fileArray = file.listFiles();
        if (fileArray == null)
            return fileList;
        for (File f : fileArray) {
            String filename = f.getName();

            if (!f.getName().equals("Crash.txt")) {
                fileList.add(f);
            }
        }
        fileSortByTime(fileList);
        return fileList;
    }

    //对文件进行时间排序
    private static void fileSortByTime(List<File> fileList) {
        Collections.sort(fileList, new Comparator<File>() {
            public int compare(File p1, File p2) {
                if (p1.getName().compareTo(p2.getName()) < 0 ) {
                    return -1;
                }
                return 1;
            }
        });
    }

//    private void zipFile() {
//        ZLog.closeSaveToFile();
//        File srcFile = new File(Environment.getExternalStorageDirectory().getPath() + "/emm_crashFolder");
//        File tagFile = new File(Environment.getExternalStorageDirectory().getPath() + "/emm_crashZip/emmlog.zip");
//        File tagPath = new File(Environment.getExternalStorageDirectory().getPath() + "/emm_crashZip");
//
//
//        try {
//            Log.i("yy", "开始压缩");
//            if (!tagPath.exists()) {
//                tagPath.mkdirs();
//            }
//            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(tagFile));
//            boolean zipResult = zipFile1(srcFile, "", zos, "emmlogs");
//            //压缩完成开始清理7天前的log,更新压缩日期
//            if (zipResult) {
////                deleteFile(srcFile);
//                String currentDate = getCurrentDate();
//                SharedPreferencesUtils.getIntance().saveLogZipDate(SharedPreferencesUtils.LOGZIPDATE, currentDate);
//            }
//        } catch (Exception e) {
//            Log.e(TAG, "出现异常===: " + e.toString());
//        }
//        ZLog.openSaveToFile();
//    }

    /**
     * 压缩文件
     *
     * @param srcFile  待压缩的源文件
     * @param rootPath 源文件的根路径
     * @param zos      Zip输出流
     * @param comment  备注
     * @return 压缩成功返回true
     * @throws IOException
     */
    private boolean zipFile1(final File srcFile, String rootPath, final ZipOutputStream zos, final String comment) throws IOException {
        rootPath = rootPath + (isSpace(rootPath) ? "" : File.separator) + srcFile.getName();
        Log.e("yy", "rootPath: " + rootPath);
        if (srcFile.isDirectory()) {
            File[] fileList = srcFile.listFiles();
            if (fileList == null || fileList.length <= 0) {
                ZipEntry entry = new ZipEntry(rootPath + '/');
                entry.setComment(comment);
                zos.putNextEntry(entry);
                zos.closeEntry();
            } else {
                for (File file : fileList) {
                    if (!zipFile1(file, rootPath, zos, comment)) return false;
                }
            }
        } else {
            InputStream is = null;
            try {
                is = new BufferedInputStream(new FileInputStream(srcFile));
                ZipEntry entry = new ZipEntry(rootPath);
                entry.setComment(comment);
                zos.putNextEntry(entry);
                byte buffer[] = new byte[BUFFER_LEN];
                int len;
                while ((len = is.read(buffer, 0, BUFFER_LEN)) != -1) {
                    zos.write(buffer, 0, len);
                }
                Log.i("yy", "压缩完成");
            } finally {
//                CloseUtils.closeIO(is);
                if (is != null)
                    is.close();
                if (zos != null)
                    zos.closeEntry();
            }
        }
        return true;
    }

    private static boolean isSpace(final String s) {
        if (s == null) {
            return true;
        }
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
