package com.yangyong.didi2;

import android.util.Log;

import com.yangyong.didi2.bean.CallLogInfo;
import com.yangyong.didi2.bean.CallLogInfos;
import com.yangyong.didi2.util.EncodingDetect;
import com.yangyong.didi2.util.LogUtils;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.helper.StringUtil;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Handler;

import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import okhttp3.FormBody;

import static com.baidu.location.h.k.i;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private volatile static boolean flag = false;
    private ArrayList<Integer> lists;
    private boolean isTrue;
    private String str;

    @Test
    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);
//        System.out.println("aaaaaaaaaaa");
//        ThreadA threadA = new ThreadA();
//        ThreadB threadB = new ThreadB();
//        new Thread(threadA, "threadA").start();
//        Thread.sleep(3);//为了保证threadA比threadB先启动，sleep一下
//        new Thread(threadB, "threadB").start();
//        getString("D:/dj/djwq.txt");
//        getHex();

//        String macstr="";
//        macstr = macstr.toLowerCase().replaceAll("-", ":");
//        System.out.println("转换后的地址："+macstr );
//        String fileEncode= EncodingDetect.getJavaEncode("D:/dj/红楼梦.txt");
//        String fileContent= FileUtils.readFileToString(new File("D:/dj/红楼梦.txt"),fileEncode);
//
//
//        System.out.println("getString文档编码: " + fileEncode);
//        System.out.println("getString文档转码后内容: " + fileContent);

//        jiaoji();
//        checkRepetition(new ArrayList<CallLogInfos>());
//        format();

//        rxTest();
//        jisuan();
//        getGapTime(36555555);
//        try {
//            exTest();
//        } catch (Exception e) {
//            System.out.println(e.toString());
//        }
//        Map<String, String> parm = new HashMap<>();
//        parm.put("name","小米");
//        parm.put("sex","男");
//        parm.put("weight","50kg");
//        parmTest(parm);

//        System.out.println("获取内r: " + Const.name);
//        ArrayList<String> strings = new ArrayList<>();
//        strings.add("111");
//        for (int i = 0; i < strings.size(); i++) {
//            String p=null;
//            System.out.println(p);
//        }
        //=============================
//        int a=5;
//        int b=6;
//        if (a==1) {
//            System.out.println(a);
//        }else if (a==2) {
//            System.out.println(a);
//        }else if (a==5) {
//            System.out.println(a);
//        }else if (b==6) {
//            System.out.println(b);
//        }
        //=============================
//        for (int i=0;i<21;i++){
//            long l = System.currentTimeMillis();
//        }

//        Const aConst = new Const();
//        System.out.println(aConst.au);
//        List<Const> list = new ArrayList<Const>();
//        list.add(new Const("22", "男"));
//        list.add(new Const("23", "男"));
//        list.add(new Const("25", "男"));


//        Const aConst2 =new Const("","");
//        aConst2=list.get(1);
//        aConst2.age = "21";

//        for (Const a : list) {
//            System.out.println(a.toString());
//        }
//        reust();
//        lists = new ArrayList<>();
//        uum(1);
//        uum(3);
//        uum(4);
//        uum(5);
//        Integer num=3;
//        lists.remove(num);

//        uum(6);
//        for (Integer o : lists) {
//            System.out.println(o);
//        }

//        String aname="更多";
//        String bname="更多1";
//        if (bname.contains(aname)) {
//            System.out.println("检索到"+bname);
//        }
//        String name = "更多3";
//        System.out.println(name.substring(name.length() - 1));//输出d
//        boolean b = Const.contain2("Af", null);
//        System.out.println(b);

//        test1();
//        testLinkedQueue();
//        get10();
//        test6();
//        test7();
//        test9();
//        test10();
//        test11();
//        test12();
//        test13();
//        test14();
//        test15();
//        test16();
//        System.out.println(55);
//        test16();
//        System.out.println(59);
//        ArrayList<String> str = new ArrayList<>();
//        test17(str);
//        System.out.println(str.get(0));
//        System.out.println(str);
//        test19();
//        test21();
//        test22();
//        test25();
//        test26();
//        test27();
//        test28();
//        test29();
        test30();
    }

    private void test30() {
        ArrayList<String> list = new ArrayList<>();
        list.add("pk1");
        list.add("pk3");
        list.add("pk5");
        list.add("pk0");
        list.add("pk2");
        boolean pk6 = list.contains("pk2");
        System.out.println(pk6);
    }

    private void test29() {
        String str = null;
        if (str.contains("key"))
            System.out.println("suc");

    }

    private void test28() {
        final CountDownLatch latch = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.out.println(Thread.currentThread().getId() + " end");
                            latch.countDown();
                        }
                    }
            ).start();
        }

        System.out.println("all over");
    }

    private void test27() {
//        String str = "123456";
        String[] strings = new String[0];
        System.out.println(strings.length);
    }

    private void test26() {
//        String str ="emm_suninfo_com_encrypt_";
        String str = "123456";
        String miStr = "ZW1tX3N1bmluZm9fY29tX2VuY3J5cHRf";
//        int i = str.hashCode();
        String s = Base64.getEncoder().encodeToString(str.getBytes());
//        byte[] decode = Base64.getDecoder().decode(miStr);
//        String s = null;
//        try {
//            s = new String(decode,"utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        System.out.println(s);
    }

    private void test25() {
        for (int i = 0; i < 10; i++) {
            //0-59
//            int r = (int) (Math.random() * 60);
            SecureRandom random = new SecureRandom();
            int r = random.nextInt(60);
            System.out.println(r);
        }
//        Random random = new Random();
//        int i = random.nextInt();
    }

    private void test22() {
        //http://wwww.baidu.com https://wwww.baidu.com http://baidu.com https://baidu.com
        String url = "http://baidu.com";
        String substring = "";
        if (url.startsWith("https://www")) {
            substring = url.substring(8);
        } else if (url.startsWith("http://www")) {
            substring = url.substring(7);
        } else if (url.startsWith("https://")) {
            substring = "www." + url.substring(8);
        } else if (url.startsWith("http://")) {
            substring = "www." + url.substring(7);
        }
        System.out.println(substring);
    }


    private void test21() {
        String mac = "40:e2:30:05:82:f7";
        String replace = mac.replace(":", "-");
        System.out.println(replace);
    }

    private void test19() {
        String emmIp = "";
        String emm_url = "https://192.168.220.58:443/rest";
        try {
            if (emm_url.startsWith("https://")) {
                String[] split = emm_url.split(":");
                if (split.length > 2) {
                    //https://192.168.220.58:443/rest
                    emmIp = split[1].substring(2);
                } else {
                    //https://192.168.220.58/rest
                    String substring = split[1].substring(2);
                    int indexOf = substring.lastIndexOf("/");
                    emmIp = substring.substring(0, indexOf);
                }
            }
            System.out.println(emmIp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void test17(ArrayList<String> str) {
        StringBuilder stringBuilder = new StringBuilder();
        String gon = stringBuilder.append(" ").append("王府井").append("\r\n").append("经度116.333000").append(" 纬度35.241515")
                .append(" 半径100").toString();

        str.add(gon);
//        System.out.println(gon);
    }

    private void test16() {
        String s = "20200803";
        StringBuilder stringBuilder = new StringBuilder();
        String year = s.substring(0, 4);
        String month = s.substring(4, 6);
        String day = s.substring(6, 8);
        StringBuilder date = stringBuilder.append(year).append("-").append(month).append("-").append(day);
        System.out.println(date);
        if (true) {
            return;
        }
        System.out.println(57);
    }

    private void test15() {
        System.out.println(isTrue);
    }

    private void test14() {
        String s = "https://192.168.220.77:443/rest";
        if (s.isEmpty()) {
            return;
        }
        if (s.startsWith("https://")) {
            String[] split = s.split(":");
            String substring = split[1].substring(2);
            System.out.println(substring);
        }


    }

    private void test13() {
        String s = "https://baidu.com";
        if (s.isEmpty()) {
            return;
        }
        if (s.startsWith("http://www.")) {
            String substring = s.substring(11);
            System.out.println(substring);
        } else if (s.startsWith("https://www.")) {
            String substring = s.substring(12);
            System.out.println(substring);
        } else if (s.startsWith("https://") || s.startsWith("http://")) {
            String substring = s.substring(8);
            System.out.println(substring);
        }

    }

    private void test12() {
        ArrayList<String> list = new ArrayList<>();
        list.add("com.mobilewise.mobileware");
        list.add("com.yangyong.didi2");
        if (list.contains("com.mobilewise.mobilewar")) {
            System.out.println(1);
        }
    }

    private void test11() {
        int sun = 2;

        if (sun == 5) {
            System.out.println(5);
        } else if (sun > 2) {
            System.out.println(2);
        } else if (sun > 1) {
            System.out.println(1);
            if (true) {
                return;
            }
            System.out.println(1);
        } else if (sun > 0) {
            System.out.println(0);
        }
    }

    private void test10() {
        String json = "{\"type\":\"0\",\"use\":\"true\",\"blueToothStr\":[\"12:34:56:23:65:47\",\"23:65:47:89:89:87\"]}";
        try {
            JSONObject object = new JSONObject(json);
            String use = object.getString("use");
            System.out.println(use);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void test9() {
        float i = 540f / 1080f;
        System.out.println(i);
    }

    private void test7() {
        float rate = .0f;
        System.out.println(rate);
    }

    private void test6() {
        ArrayList<Const> objects = new ArrayList<>();
        Const aConst1 = new Const("1", "2");
        Const aConst2 = new Const("1", "2");
        Const aConst3 = new Const("1", "2");
        objects.add(aConst1);
        objects.add(aConst2);
        objects.add(aConst3);

        setValue(objects);


        for (Const co : objects) {
            System.out.println(co.toString());
        }
    }

    private void setValue(ArrayList<Const> objects) {
        for (Const co : objects) {
            co.age = "";
            co.sex = "";
            break;
        }
    }

    private void parmTest(Map<String, String> params) {
        //3.x版本post请求换成FormBody 封装键值对参数

        FormBody.Builder builder = new FormBody.Builder();
        //遍历集合
        for (String key : params.keySet()) {
            builder.add(key, params.get(key));
        }
        FormBody build = builder.build();
        String s2 = builder.toString();
        System.out.println(s2);

    }

    private void exTest() throws IOException {
        throw new NullPointerException("这是自定义的空指针");
    }

    private void jisuan() {
        float size = 55555555;
        float i = (float) Math.round(size / 1024 / 1024 * 100) / 100;

        System.out.println(i);
    }

    private void rxTest() {
//        Observable<String> observable = Observable.just("hello");
//        Consumer<String> consumer = new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                System.out.println(s);
//            }
//        };
//        observable.subscribe(consumer);

        Observable<String> observable = Observable.just("hello");
        Action onCompleteAction = new Action() {
            @Override
            public void run() throws Exception {
//                Log.i("kaelpu", "complete");
                System.out.println("complete");
            }
        };
        Consumer<String> onNextConsumer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
//                Log.i("kaelpu", s);
                System.out.println("onNextConsumer:" + s);
            }
        };
        Consumer<Throwable> onErrorConsumer = new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
//                Log.i("kaelpu", "error");
                System.out.println("onErrorConsumer:" + throwable.getMessage());
            }
        };
        observable.subscribe(onNextConsumer, onErrorConsumer, onCompleteAction);
    }


    public String getGapTime(long time) {
        long hours = time / (1000 * 60 * 60);
        long minutes = (time - hours * (1000 * 60 * 60)) / (1000 * 60);
        long seconds = ((time - hours * (1000 * 60 * 60)) - (minutes * 60 * 1000)) / 1000;
        StringBuilder diffTime = new StringBuilder();
        if (hours == 0) {
            diffTime.append(minutes).append("分").append(seconds).append("秒");
        } else {
            diffTime.append(hours).append("时").append(minutes).append("分").append(seconds).append("秒");
        }
        System.out.println(diffTime);
        return diffTime.toString();
    }


    class ThreadA extends Thread {
        public void run() {
            while (true) {
                System.out.println("循环中");
                if (flag) {
                    System.out.println(Thread.currentThread().getName() + " : flag is " + flag);
                    break;
                }
            }
        }

    }

    class ThreadB extends Thread {
        public void run() {
            flag = true;
            System.out.println(Thread.currentThread().getName() + " : flag is " + flag);
        }
    }


    public String getString(String str_filepath) {// 转码
        File file = new File(str_filepath);
        BufferedReader reader;
        StringBuilder text = new StringBuilder("");
        String code = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream in = new BufferedInputStream(fis);
            in.mark(4);
            byte[] first3bytes = new byte[3];
            in.read(first3bytes);//找到文档的前三个字节并自动判断文档类型。
            in.reset();
            System.out.println("getString:=== " + first3bytes[0] + first3bytes[1] + first3bytes[2]);
//            code = getCharset(first3bytes);
//            reader = new BufferedReader(new InputStreamReader(in, code));

            if (first3bytes[0] == (byte) 0xEF && first3bytes[1] == (byte) 0xBB
                    && first3bytes[2] == (byte) 0xBF) {// utf-8
                code = "utf-8";
                reader = new BufferedReader(new InputStreamReader(in, code));
            } else if (first3bytes[0] == (byte) 0xFF
                    && first3bytes[1] == (byte) 0xFE) {
                code = "unicode";
                reader = new BufferedReader(
                        new InputStreamReader(in, code));
            } else if (first3bytes[0] == (byte) 0xFE
                    && first3bytes[1] == (byte) 0xFF) {
                code = "utf-16be";
                reader = new BufferedReader(new InputStreamReader(in,
                        code));
            } else if (first3bytes[0] == (byte) 0xFF
                    && first3bytes[1] == (byte) 0xFF) {
                code = "utf-16le";
                reader = new BufferedReader(new InputStreamReader(in,
                        code));
            } else {
                code = "GB18030";
//                code = "utf-8";
                //InputStreamReader isr=new InputStreamReader(in);
                //reader = new BufferedReader(isr);
                reader = new BufferedReader(new InputStreamReader(in,
                        code));
            }
//            String str = "文档编码：" + code + "\n" + reader.readLine();
            System.out.println("getString文档编码: " + code);
            String str = reader.readLine();
            while (str != null) {
                text.append(str).append("\n");
                //text = text + str+ "\n";
                str = reader.readLine();
            }
            //text=EncodingUtils.getString(text.getBytes(isr.getEncoding()), code);
            //text=EncodingUtils.getString(text.getBytes(code), "utf-8");
            reader.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String s = text.toString();
        System.out.println("getString====: " + s);
        return text.toString();
    }

    void getHex() {
        FileInputStream is = null;
        try {
            is = new FileInputStream("D:/dj/机密文件u8a.txt");
            System.out.print("文件的前3个字符(HEX):");
            for (int i = 0; i < 3; i++) {
                int ch = is.read();
                System.out.print(Integer.toHexString(ch));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void jiaoji() {
        ArrayList<String> object = new ArrayList<>();
        object.add("真");
        object.add("新");
        object.add("镇");
        object.add("小");
        ArrayList<String> objects = new ArrayList<>();
        objects.add("真");
        objects.add("小");
        objects.add("智");
        boolean b = objects.retainAll(object);
        System.out.println(b);

        for (String s : objects) {
            System.out.println(s);
        }
    }

    /**
     * 通话记录去重
     */
    public List<CallLogInfos> checkRepetition(List<CallLogInfos> current) {
        current.add(new CallLogInfos("1", "156", "河南", "12", "1", "1545"));
        current.add(new CallLogInfos("2", "156", "河南", "12", "1", "1545"));
        current.add(new CallLogInfos("3", "156", "河南", "12", "1", "1545"));
        current.add(new CallLogInfos("4", "156", "河南", "12", "1", "1545"));
        current.add(new CallLogInfos("5", "156", "河南", "12", "1", "1545"));
        current.add(new CallLogInfos("6", "156", "河南", "12", "1", "1545"));
        current.add(new CallLogInfos("7", "156", "河南", "12", "1", "1545"));
        current.add(new CallLogInfos("8", "156", "河南", "12", "1", "1545"));
        current.add(new CallLogInfos("9", "156", "河南", "12", "1", "1545"));
        current.add(new CallLogInfos("11", "156", "河南", "12", "1", "1545"));
        current.add(new CallLogInfos("15", "156", "河南", "12", "1", "1545"));
        current.add(new CallLogInfos("13", "156", "河南", "12", "1", "1545"));
        current.add(new CallLogInfos("12", "156", "河南", "12", "1", "1545"));
        current.add(new CallLogInfos("0", "156", "河南", "12", "1", "1545"));
        List<CallLogInfos> tempInfo = new ArrayList<>();

        List<CallLogInfos> cacheInfo = new ArrayList<>();//内存中的上传记录  交集
        cacheInfo.add(new CallLogInfos("5", "156", "河南", "12", "1", "1545"));
        cacheInfo.add(new CallLogInfos("3", "156", "河南", "12", "1", "1545"));
        cacheInfo.add(new CallLogInfos("1", "156", "河南", "12", "1", "1545"));
        cacheInfo.add(new CallLogInfos("13", "156", "河南", "12", "1", "1545"));
        cacheInfo.add(new CallLogInfos("12", "156", "河南", "12", "1", "1545"));
//        for (CallLogInfos logInfo : current) {
//            for (CallLogInfos cInfo : cacheInfo) {
//                if (logInfo.getContact().equals(cInfo.getContact())) {
//                    current.remove(logInfo);
//                    break;
//                }
//            }
//        }

        //去除交集部分
        Iterator<CallLogInfos> iterator1 = current.iterator();
        while (iterator1.hasNext()) {
            CallLogInfos next = iterator1.next();
            for (CallLogInfos cInfo : cacheInfo) {
                if (next.getContact().equals(cInfo.getContact())) {
                    iterator1.remove();
                    break;
                }
            }

        }

        for (CallLogInfos infos : current) {
            System.out.println(infos.toString());
        }
        return tempInfo;
    }


    void reust() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 5; i++) {
                            try {
                                System.out.println(i + "============1");
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                System.out.println(e.toString());
//                                e.printStackTrace();
                            }
                        }
//                        for (int i=0;i<2;i++){
//                            try {
//                                System.out.println(i+"============2");
//                                Thread.sleep(1000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
                        System.out.println("循环结束=======");
                    }
                }
        ).start();

    }


    int uum(int num) {
        if (num > 5) {
            for (int a = 1; a < 6; a++) {
                if (!lists.contains(a)) {
                    num = a;
                    lists.add(num);
                    break;
                }
            }
        } else {
            lists.add(num);
        }
        return num;
    }

    void format() {
        String number = "1";
        String s = number.replaceAll("\\s*", "");
        String replace = s.replace("+86", "");
        System.out.print(replace);
    }

    void test1() {
        int sum = 0;
        for (int i = 0; i < 255; i++) {
            for (int j = 0; j < 255; j++) {
                for (int k = 0; k < 255; k++) {
                    System.out.println(sum++);
                }
            }
        }
    }

    void testLinkedQueue() {
        LinkedBlockingDeque<Integer> blockingDeque = new LinkedBlockingDeque<>(15);
        for (int i = 0; i < 5; i++) {
            blockingDeque.add(i);
        }
        System.out.println("add结束，当前队列大小：" + blockingDeque.size());
        for (int i = 0; i < 5; i++) {
            blockingDeque.offer(i);
        }
        System.out.println("offer结束，当前队列大小：" + blockingDeque.size());
        for (int i = 0; i < 20; i++) {
            try {
                blockingDeque.put(i);
            } catch (InterruptedException e) {
                System.out.println("put异常：" + e.toString());
            }
            System.out.println("put入队，当前队列大小：" + blockingDeque.size());
        }
        System.out.println("blockingDeque入队完成，当前队列大小：" + blockingDeque.size());
    }

    public static int MSG_ERROR = 0x0011;
    public static int MSG_ERRO = 0x0022;
    public static int MSG_ERRO1 = 0x00f1;

    void get10() {
        System.out.println("MSG_ERROR：" + MSG_ERROR);
        System.out.println("MSG_ERROR：" + MSG_ERRO);
        System.out.println("MSG_ERROR：" + MSG_ERRO1);
    }
}