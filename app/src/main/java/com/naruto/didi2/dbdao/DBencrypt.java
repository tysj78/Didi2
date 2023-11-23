package com.naruto.didi2.dbdao;

import android.content.Context;

import com.naruto.didi2.util.LogUtils;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;

/**
 * 数据库文件加密类 class
 *
 * @author yangyong
 * @date 2020/12/7/0007
 */

public class DBencrypt {
    public static DBencrypt dBencrypt;
//    private Boolean isOpen = true;

    public static DBencrypt getInstences() {
        if (dBencrypt == null) {
            synchronized (DBencrypt.class) {
                if (dBencrypt == null) {
                    dBencrypt = new DBencrypt();
                }
            }
        }
        return dBencrypt;
    }

    /**
     * 如果有旧表 先加密数据库
     * 思路：将旧数据库文件加密后存到一个缓存目录下，再将旧数据库替换为加密数据库
     *
     * @param context
     * @param passphrase
     */
    public void encrypt(Context context, String passphrase, String dbname) {
//        File file = new File("/data/data/" + context.getPackageName() + "/databases/"+dbname);
        File file = context.getDatabasePath(dbname);
        if (file.exists()) {
//            if (isOpen) {
            try {
                LogUtils.e("开始加密旧数据库..");
                File newFile = File.createTempFile("sqlcipherutils", "tmp", context.getCacheDir());

                //以空密码打开数据库文件，如果为普通数据可以正常获取，如果为加密数据库则会报file is not a database
                net.sqlcipher.database.SQLiteDatabase db = net.sqlcipher.database.SQLiteDatabase.openDatabase(
                        file.getAbsolutePath(), "", null, SQLiteDatabase.OPEN_READWRITE);

                db.rawExecSQL(String.format("ATTACH DATABASE '%s' AS encrypted KEY '%s';",
                        newFile.getAbsolutePath(), passphrase));
                db.rawExecSQL("SELECT sqlcipher_export('encrypted')");
                db.rawExecSQL("DETACH DATABASE encrypted;");

                int version = db.getVersion();
                db.close();

                db = net.sqlcipher.database.SQLiteDatabase.openDatabase(newFile.getAbsolutePath(),
                        passphrase, null,
                        SQLiteDatabase.OPEN_READWRITE);

                db.setVersion(version);
                db.close();
                //移动文件，将缓存文件移动到应用database目录并重命名
                boolean delete = file.delete();
                boolean b = newFile.renameTo(file);
                LogUtils.e("加密后数据库文件位置：" + delete + b + "==" + newFile.getAbsolutePath() + "==" + file.getAbsolutePath());
//                    isOpen = false;
            } catch (Exception e) {
//                    isOpen = false;
                LogUtils.e("加密失败：" + e.toString());
            }
//            }
        }
    }
}
