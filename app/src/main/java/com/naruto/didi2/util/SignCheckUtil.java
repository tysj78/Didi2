package com.naruto.didi2.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.SigningInfo;
import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class SignCheckUtil {

    private Context context;
    private String cer = null;
    private String type = "SHA1";
    private String sha1RealCer = "F6:6A:09:98:EB:6B:63:98:28:5F:39:66:36:A1:69:1E:E1:F1:15:23";
    private String md5RealCer = "3F:EE:BC:BB:D6:25:4D:AB:0B:8B:47:93:41:2D:5A:18";
    private static final String TAG = "sign";

    public SignCheckUtil(Context context) {
        this.context = context;
//        this.type = type;
    }


    /**
     * 获取应用的签名
     *
     * @return
     */
    @SuppressLint("WrongConstant")
    public String getCertificateSHA1Fingerprint() {
        String hexString = "";


        //获取包管理器
        PackageManager pm = context.getPackageManager();

        //获取当前要获取 SHA1 值的包名，也可以用其他的包名，但需要注意，
        //在用其他包名的前提是，此方法传递的参数 Context 应该是对应包的上下文。
        String packageName = context.getPackageName();

        //签名信息
        android.content.pm.Signature[] signatures = null;

        try {
            if (android.os.Build.VERSION.SDK_INT >= 28) {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNING_CERTIFICATES);
                SigningInfo signingInfo = null;
                signingInfo = packageInfo.signingInfo;
                signatures = signingInfo.getApkContentsSigners();
            } else {
                //获得包的所有内容信息类
                PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
                signatures = packageInfo.signatures;
            }


//        Signature[] signatures = packageInfo.signatures;
            byte[] cert = signatures[0].toByteArray();

            //将签名转换为字节数组流
            InputStream input = new ByteArrayInputStream(cert);

            //证书工厂类，这个类实现了出厂合格证算法的功能
            CertificateFactory cf = CertificateFactory.getInstance("X509");

            //X509 证书，X.509 是一种非常通用的证书格式
            X509Certificate c = null;
            c = (X509Certificate) cf.generateCertificate(input);


            //加密算法的类，这里的参数可以使 MD4,MD5 等加密算法
            MessageDigest md = MessageDigest.getInstance(type);

            //获得公钥
            byte[] publicKey = md.digest(c.getEncoded());

            //字节到十六进制的格式转换
            hexString = byte2HexFormatted(publicKey);
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
        return hexString.trim();
    }

    //这里是将获取到得编码进行16 进制转换
    private String byte2HexFormatted(byte[] arr) {

        StringBuilder str = new StringBuilder(arr.length * 2);

        for (int i = 0; i < arr.length; i++) {
            String h = Integer.toHexString(arr[i]);
            int l = h.length();
            if (l == 1)
                h = "0" + h;
            if (l > 2)
                h = h.substring(l - 2, l);
            str.append(h.toUpperCase());
            if (i < (arr.length - 1))
                str.append(':');
        }
        return str.toString();
    }


    /**
     * app签名校验
     * @param type "SHA1","MD5"
     * @return true 校验通过 false 签名被篡改
     */
    public boolean checkSignature(String type) {
        if (this.sha1RealCer != null || md5RealCer != null) {
            cer = getCertificateSHA1Fingerprint();
            LogUtils.e("当前签名：" + cer);
            if ((TextUtils.equals(type, "SHA1") && this.cer.equals(this.sha1RealCer)) || (TextUtils.equals(type, "MD5") && this.cer.equals(this.md5RealCer))) {
                return true;
            }
        }
        return false;
    }


    /**
     * app完整性校验
     * @param context
     * @param type "SHA-1","MD5"
     * @return true 通过校验 false app被篡改
     */
    public boolean checkIntegrity(Context context,String type) {
        if (this.sha1RealCer != null || md5RealCer != null) {
            cer = apkShaCheck(context,type);
            LogUtils.e("当前签名：" + cer);
            if ((TextUtils.equals(type, "SHA-1") && this.cer.equals(this.sha1RealCer)) || (TextUtils.equals(type, "MD5") && this.cer.equals(this.md5RealCer))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取当前appsha1或MD5值
     * @param context
     * @param type "SHA-1","MD5"
     * @return
     */
    public String apkShaCheck(Context context,String type) {
        MessageDigest msgDigest = null;
        String sha="";
        try {
            msgDigest = MessageDigest.getInstance(type);
            byte[] bytes = new byte[1024];
            int byteCount;
            File apkPath = new File(context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).sourceDir);
            FileInputStream fis = new FileInputStream(apkPath);
            while ((byteCount = fis.read(bytes)) > 0) {
                msgDigest.update(bytes, 0, byteCount);
            }
            BigInteger bi = new BigInteger(1, msgDigest.digest());
            sha = bi.toString(16).trim().toLowerCase();
            LogUtils.e("apk sha = " + sha);
            fis.close();
            // 这里添加从服务器中获取哈希值然后进行对比校验
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
        return sha;
    }

}
