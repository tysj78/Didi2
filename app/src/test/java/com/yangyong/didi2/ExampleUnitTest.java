package com.yangyong.didi2;

import android.util.Log;

import com.yangyong.didi2.bean.CallLogInfo;
import com.yangyong.didi2.bean.CallLogInfos;
import com.yangyong.didi2.util.EncodingDetect;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Handler;

import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import okhttp3.FormBody;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private volatile static boolean flag = false;
    private ArrayList<Integer> lists;

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
        get10();
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