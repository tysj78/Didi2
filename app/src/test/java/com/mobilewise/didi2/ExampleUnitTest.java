package com.mobilewise.didi2;

import com.naruto.didi2.bean.CallLogInfos;
import com.naruto.didi2.util.LogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import okhttp3.FormBody;

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
    private AtomicInteger mWriteCounter = new AtomicInteger();//自增长类
    private int aa = 30;

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
//        test30();
//        test31();
//        test33();
//        test35();
//        test36();
//        test37();
//        test38();
//        test39();
//        test41();
//        test42();
//        test43();
//        test45();
//        test46();
//        String checkUrl = checkUrl("www.baidu.com", true);
//        String checkUrl = checkUrl("baidu.com", true);
//        String checkUrl = checkUrl("http://baidu.com", true);
//        String checkUrl = checkUrl("https://baidu.com", true);
//        String checkUrl = checkUrl("http://www.baidu.com", true);
//        String checkUrl = checkUrl("https://www.baidu.com", true);
//        String checkUrl = checkUrl("about:start", true);
//        System.out.println(checkUrl);
//        test49();
//        test50();
//        test51();
//        test52();
//        test53();
//        test55();

        //集合判空
//        test56();

        //除法
//        test57();
        //随机数
//        test58();
        //删除集合后几条数据
//        test59(0);
//        test60();
//        test61();
//        test62("oppo");
//        List<String> list = test63();
//        list.add("22");
//        System.out.println("");
//        test65();
//        test66();
//        test67();
//        test68();
//        test69();
//        test70();
//        test71();
//        test72();
//        test73();
//        test75();
//        test76();
//        test77();
//        test79();
//        test80();
//        test81();
//        test82();
//        test83();
//        test86();
//        test87();
//        test89();
        String c = test90();
        System.out.println(c);

    }

    private String test90() {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update("abcd12345".getBytes());
            byte[] digest = md.digest();

            // Convert the byte array to a hex string
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();

        } catch (Exception e) {
            LogUtils.e("获取md5异常");
            return null;
        }
    }

    private void test89() {
        String u1 = "https://msp.suninfo.com:10447/vue/web/login";
        String u2 = "https://192.168.220.58:10443/vue/web/login";

        String input = "This is an example text with an IP address: 192.168.1.1 and another one: 10.0.0.2";

        Pattern ipPattern = Pattern.compile("((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))");
        Matcher matcher = ipPattern.matcher(u2);

        while (matcher.find()) {
            System.out.println("Found IP address: " + matcher.group());
        }
    }

    private void test87() {
        ArrayList<String> list = new ArrayList<>();
        list.add("192.168.220.58");
        list.add("192.168.220.59");
        list.add("192.168.220.60");
        list.add("192.168.220.58");
        list.add("192.168.220.59");
        list.add("192.168.220.60");
        list.add("192.168.220.58");
        list.add("192.168.220.59");
        list.add("192.168.220.60");
        list.add("192.168.220.58");
        list.add("192.168.220.59");
        list.add("192.168.220.60");

        String[] strings = list.toArray(new String[list.size()]);
        System.out.println(Arrays.toString(strings));
    }

    private void test86() {
//        List<String> list = new ArrayList<>();
//        list.add("1");
//        list.add("2");
//        list.add(null);
//
//        ArrayList<String> newlist= (ArrayList<String>) list;
//
//
//        String cc="李六,123,56,2936,7";
//        String[] split = cc.split(",");
//        String name = split[0];
//        for (int i = 1; i < split.length; i++){
//            System.out.println(name+","+split[i]);
//        }

        String aa = "862168050018194/A00000BF54FED8";
        String[] split = aa.split("/");
        System.out.println(Arrays.toString(split));

    }

    private void test83() {
        //多的，取本地关于服务器的补集
//        ArrayList<String> a = new ArrayList<>();
//        a.add("李一,13515619582,13515619534");
//        a.add("李二,13515619583");
//        a.add("李三,13515619581,13515619587");
//        ArrayList<String> b = new ArrayList<>();
//        b.add("李二,13515619583");
//        b.add("李三,13515619581,13515619589");
//        b.add("李五,13515619523,13515569587");
//        Collection<String> c = new ArrayList<>(a);
//        b.removeAll(c);
//
//
//        System.out.println(b);


        //少的，取服务器关于本地的补集
        ArrayList<String> a1 = new ArrayList<>();
        ArrayList<String> tempa1 = new ArrayList<>();

        a1.add("李一,13515619582,13515619534");
        a1.add("李二,13515619583");
        a1.add("李三,13515619581,13515619587");
        tempa1.addAll(a1);


        ArrayList<String> b1 = new ArrayList<>();
        ArrayList<String> tempb1 = new ArrayList<>();


        b1.add("李二,13515619583");
        b1.add("李三,13515619581,13515619589");
        b1.add("李五,13515619523,13515569587");

        tempb1.addAll(b1);

        Collection<String> c1 = new ArrayList<>(b1);
        tempa1.removeAll(c1);


        Collection<String> d1 = new ArrayList<>(a1);
        tempb1.removeAll(d1);

        System.out.println(tempa1);//本地少的
        System.out.println(tempb1);//服务器多的


        for (String contact : tempa1) {
            String name = contact.split(",")[0];
            System.out.println("删除联系人：" + name);
        }

        for (String contact : tempb1) {
            String[] contacts = contact.split(",");
            String name = contacts[0];
            for (int i = 1; i < contacts.length; i++) {
                System.out.println("添加联系人：" + name + contacts[i]);
            }

        }

    }

    private void test82() {
        String aa = "https://192.168.220.56:10443/rest";
        if (aa.endsWith("/rest")) {
            String substring = aa.substring(0, aa.length() - 5);
            System.out.println(substring);
        }
    }

    private void test81() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 106; i++) {
            list.add(i + "");
        }

        int inDex = 30;
        for (int i = 0; i < list.size(); i += 30) {
            if (i + 30 > list.size()) {
                inDex = list.size() - i;
            }
            List<String> nowList = list.subList(i, i + inDex);
            Object[] objects = nowList.toArray();

            System.out.println("取出集合数据：" + nowList.size() + Arrays.toString(objects));
        }
    }


    private void test80() {
        String aa = "8f:5e:19:88:20:64:ca:5e:c7:4b:83:f4:86:38:85:0a:84:60:a0:ee";
        String response = aa.replaceAll(":", "");
        System.out.println(response);
        int a = 0x0e;
        if (a <= 0xf) {
            System.out.print(Integer.toHexString(a) + " ");
        }
        System.out.print(a + " ");

   /*     (b <= 0xf) while ((b = in.read()) != -1) {
            if (b <= 0xf) {
                //单位数前面补0 System.out.print("0");
            }
            // System.out.print(Integer.toHexString(b)+" ");
            if (i++ % 10 == 0) {
                System.out.println();
            }
        }*/
    }

    private void test79() {
        String rgbstr = "[" + 255 + "," + 0 + "," + 0 + "]";
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder append = stringBuilder.append("[").append(255).append(",").append(0).append(",").append(0).append("]");

        System.out.println(rgbstr);
        System.out.println(append.toString());

    }

    private void test77() {
        ArrayList<AppInstall> list = new ArrayList<>();
        ArrayList<AppInstall.ListBean> listB = new ArrayList<>();
        AppInstall.ListBean listBean = new AppInstall.ListBean();
        listBean.setAppPackageName("com.wandoujia.phoenix2");
        listBean.setCertificateHash("BE:13:53:53:43:7D:70:4F:3A:37:E2:B4:13:D0:40:A5:DD:FF:4F:19");
        listB.add(listBean);

        AppInstall appInstall = new AppInstall();
        appInstall.setState("0");
        appInstall.setList(listB);
        list.add(appInstall);

        AppInstall[] listBeans = list.toArray(new AppInstall[list.size()]);
        System.out.println(Arrays.toString(listBeans));
    }

    private void test76() {
        String ssid = "\"Good_I_Deer\"";
        String substring = ssid.substring(1, ssid.length() - 1);
        System.out.println(substring);
    }

    private void test75() {
        System.out.println(17 & 1);
        System.out.println(21 & 1);
    }

    private void test73() {
//        System.out.println("aa");
        changeto("china");
    }


    private void changeto(String str) {
        char ch[] = str.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            ch[i] = (char) ((int) ch[i] + 4);
        }
        String str1 = new String(ch);

        System.out.print("加密后的字符串为：" + str1);

    }

    private void test72() {
        try {
            String mac = "94:0E:6B:54:2C:4C";
            String mac11 = "94-0E-6B-54-2C-4C";
            String mactemp = "94:0e:6b:54:2c:4c";
            String s11 = mac.replaceAll("-", ":");
            String s22 = mac11.replaceAll("-", ":");

            if (s22.equalsIgnoreCase(mactemp)) {
                System.out.println(true);
            }
            System.out.println(s11);
            System.out.println(s22);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void test71() {
        ArrayList<Const> list = new ArrayList<>();
        Const aConst = new Const();
        aConst.age = "19";
        list.add(aConst);
        Object o = list;
        List<Const> constList = (List<Const>) o;
        List<Const> constList1 = castList(o, Const.class);
        System.out.println(constList.get(0).age + "=" + constList1.get(0).age);

        Object o1 = aConst;
        Const c1 = (Const) o1;
        System.out.println(c1.age);


        Const<String> c2 = new Const<>("jiejie");
        Const<Integer> c3 = new Const<>(56);
        String mT = c2.mT;
        Integer mT1 = c3.mT;
        System.out.println(mT);
        System.out.println(mT1);
        String call = getCall("26630", String.class);
        System.out.println(call);
    }

    private <T, A> A getCall(Object number, Class<A> c) {
        return c.cast(number);
    }


    private <T> List<T> castList(Object obj, Class<T> clazz) {
        try {
            List<T> result = new ArrayList<T>();
            if (obj instanceof List<?>) {
                for (Object o : (List<?>) obj) {
                    result.add(clazz.cast(o));
                }
                return result;
            }
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
        return null;
    }


    private void test70() {
//        double parseDouble = Double.parseDouble("0.00");
//        double q = parseDouble - 0.009999999999999998;
//        String powerPercent = getPowerPercent(0.009999999999999998d, 4500d);
//        String retain = retain(powerPercent);

        String[] deviceState = new String[]{"0.25698%", "52.38858843%", "61.9567489%"};

        String cpu = retain(deviceState[0].substring(0, deviceState[0].length() - 1));
        String ram = retain(deviceState[1].substring(0, deviceState[1].length() - 1));
        String rom = retain(deviceState[2].substring(0, deviceState[2].length() - 1));

//        String cpu = retain(String.valueOf(Double.parseDouble(deviceState[0]) * 100));
//        String ram = retain(String.valueOf(Double.parseDouble(deviceState[1]) * 100));
//        String rom = retain(String.valueOf(Double.parseDouble(deviceState[2]) * 100));

        //        System.out.println(cpu + "=" + ram + "=" + rom);
        double a = 0.011654941666666D;

        double b = 4500D;
        String useDataLink = "0";
        String wifistr = "0";
        String errorcount = "0";
        String battery = "0.00%";
        String batteryTemp = "0.00%";
        String[] runinfo = new String[]{useDataLink, wifistr, errorcount, battery, batteryTemp};
        String powerPercent = getPowerPercent(a, b);

        System.out.println(powerPercent);
        System.out.println(runinfo[0]);
        System.out.println(runinfo[1]);
        System.out.println(runinfo[2]);
        System.out.println(runinfo[3]);
        System.out.println(runinfo[4]);

        double aa = 0.50D;
        System.out.println(aa);

        String powerPercentB = subtractPower(0.2, 0.1);
        System.out.println(powerPercentB);
    }

    public String getPowerPercent(double usePower, double sum) {
        try {
            if (usePower == 0) {
                return "0.00";
            }
            NumberFormat instance = NumberFormat.getInstance();
            instance.setMaximumFractionDigits(2);
            String format = instance.format(usePower / sum * 100);

            //不够小数点后两位的进行补全
            return String.format("%.2f", Double.parseDouble(format));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return "0.00";
    }

    public String subtractPower(double usePower, double sum) {
        try {
            NumberFormat instance = NumberFormat.getInstance();
            instance.setMaximumFractionDigits(2);
            String format = instance.format(usePower - sum);

            //不够小数点后两位的进行补全
            return String.format("%.2f", Double.parseDouble(format));

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return "0.00";
    }

    public String retain(String aa) {
        try {
            return String.format("%.2f", Double.parseDouble(aa)) + "%";
        } catch (Exception e) {
        }
        return "0.00%";
    }

    private void test69() {
//        String l1 = "116.271239D";
//        String l2 = "39.94866D";
//        double parseDouble1 = Double.parseDouble(l1);
//        double parseDouble2 = Double.parseDouble(l2);
//        System.out.println("parseDouble1:" + parseDouble1 + parseDouble2);


        String location = "{\"longitude\":116.271362,\"latitude\":39.948788,\"height\":0}";
        //酷派截取方式

//        String[] split = location.split("=");
//        String aa = split[1];
//        String bb = split[2];
//        String longitude = aa.substring(0, aa.length() - 12);
//
//        String latitude = bb.substring(1, bb.length() - 10);
        try {
            JSONObject jsonObject = new JSONObject(location);
            double longitude = jsonObject.getDouble("longitude");
            double latitude = jsonObject.getDouble("latitude");
            System.out.println("parseDouble1:" + longitude + latitude);
        } catch (Exception e) {
            System.out.println("" + e.toString());
        }

//        System.out.println("parseDouble1:" + longitude + latitude);
    }

    private void test68() {
        ArrayList<String> list = new ArrayList<>();
        list.add("com.cc");
        list.add("com.dd");

        list.remove("com.mobilewise.mobileware");
        System.out.println(list);
    }

    private void test67() {
        double parseDouble = Double.parseDouble("0.00");
        if (parseDouble == 0) {
            parseDouble = 0;
            System.out.println(parseDouble);
        }

    }

    private void test66() {
        ArrayList<String> list = new ArrayList<>();
        list.add("aa");
        list.add("bb");
        list.add("bb");
        list.add("aa");
        list.add("dd");
        list.add("bb");
        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.get(i).equals("aa") || list.get(i).equals("dd")) {
                continue;
            }
            list.remove(i);
        }
        System.out.println(list);
    }

    private void test65() {
        long i = 30;
        long m = 70;

        String location = "{\"longitude\"=116.24949\",\"latitude\"=\"39.951489\",\"height\"=\"0.0\"}";

        String[] split = location.split("=");
        String aa = split[1];
        String bb = split[2];
        String longt = aa.substring(0, aa.length() - 12);

        String langt = bb.substring(1, bb.length() - 10);

        String simOperator = "56010";
        String mcc = simOperator.substring(0, 3);
        String mnc = simOperator.substring(3, simOperator.length());

        System.out.println(Arrays.toString(split));
        System.out.println(aa + "&" + bb);
        System.out.println(mcc + "&" + mnc);

    }

    private List<String> test63() {
        if (false) {
            List<String> s1 = new ArrayList<>();
            s1.add("11");
            return s1;
        } else {
            return new ArrayList<>();
        }
    }

    private void test62(String aa) {
//        https://msp.suninfo.co
        String tempaa = "https://msp.suninfo.com/rest/mokbile/android/uploadSoftwareInfo";
        int i = tempaa.indexOf("/rest");
        String substring = tempaa.substring(i);

        System.out.println(i + "==" + substring);
    }


    private void test61() {
        String a = "";
        try {
            String substring = a.substring(0, a.length() - 1);
            System.out.println(substring);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private void test60() {
        Const aConst1 = new Const("12", "30");
        Const aConst2 = new Const("55", "62");
        Const aConst3 = new Const("136", "0");
        Const aConst4 = new Const("0", "0");
        Const aConst5 = new Const("0", "301");


        List<Const> objects = new ArrayList<>();
        objects.add(aConst1);
        objects.add(aConst2);
        objects.add(aConst3);
        objects.add(aConst4);
        objects.add(aConst5);


        List<Const> objects1 = new ArrayList<>();

        for (int i = 0; i < objects.size(); i++) {
            Const aConst = objects.get(i);
            if ("0".equals(aConst.age) && "0".equals(aConst.sex)) {
                continue;
            }

            if (("0".equals(aConst.age) && !"0".equals(aConst.sex)) || (!"0".equals(aConst.age) && "0".equals(aConst.sex))) {
                continue;
            }
            objects1.add(aConst);
        }

        System.out.println("list:" + objects1);
    }

    private void test59(int count) {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10");
        list.subList(list.size() - count, list.size()).clear();
//        list.add("new");
        System.out.println("list:" + list);
    }

    private void test58() {
        int max = 7, min = 3;
        int ran2 = (int) (Math.random() * (max - min) + min);
        System.out.println("随机数: " + ran2);
    }

    private void test57() {
        long record = 21519947;
        long allLength = 41336093;
        long dd = ((record * 100) / allLength);

        System.out.println("获取内r: " + dd);
    }

    private void test56() {
        List<String> objects = new ArrayList<>();
        if (objects != null && objects.isEmpty()) {
            if (!objects.contains("com.mobilewise.mobileware")) {
                objects.add("com.mobilewise.mobileware");
                objects.add("com.qq.tt");
            }
            System.out.println(objects.toString());
            if (objects.contains("com.mobilewise.mobileware")) {
                objects.remove("com.mobilewise.mobileware");
            }
            System.out.println(objects.toString());
        }
    }

    private void test55() {
        List<String> objects = new ArrayList<>();
        objects.add("com.aa.h");
        System.out.println(objects.toString());
    }

    private void test53() {
        String aa = "{\"code\":0,\"messages\":\"[{\\\"fromChannelAccount\\\":\\\"system\\\",\\\"msgContent\\\":\\\"{command:{requesttype:AndroidCollection, config:{\\\\\\\"collect_content\\\\\\\":[\\\\\\\"dev_basic_info\\\\\\\",\\\\\\\"dev_action_info\\\\\\\"],\\\\\\\"use\\\\\\\":1,\\\\\\\"collect_period\\\\\\\":6}}}\\\",\\\"sendTime\\\":1634789663000,\\\"toAccount\\\":\\\"5ab637a321e1233481e481186a104187193f03fa\\\",\\\"type\\\":1},{\\\"fromChannelAccount\\\":\\\"system\\\",\\\"msgContent\\\":\\\"{command:{requesttype:AndroidEnvironment, config:{\\\\\\\"JsonTypeHandler\\\\\\\":\\\\\\\"not json string.\\\\\\\",\\\\\\\"S_WATER_MARK_CFG\\\\\\\":{\\\\\\\"view_log\\\\\\\":0,\\\\\\\"alpha\\\\\\\":230,\\\\\\\"view_regex\\\\\\\":\\\\\\\"[^\\\\\\\\\\\\\\\\s]+((id/content|word:id/FullScreenPanesContainer|BindableView|PDFView|wps.moffice_eng:id/content_lay|wps.moffice_eng:id/infoflow_horizonal|PDFFrameLayout|PptRootFrameLayout|RootFrameLayout|MuPDFReaderView|ReaderTextPageView|BookView|WebView|ConstraintLayout|AppCompatEditText|WordCanvasView|ExcelCanvasView|PPTCanvasView))$\\\\\\\",\\\\\\\"fontSize\\\\\\\":110,\\\\\\\"style\\\\\\\":0,\\\\\\\"rgb\\\\\\\":[216,30,6],\\\\\\\"content\\\\\\\":\\\\\\\"\\\\\\\",\\\\\\\"bind_time\\\\\\\":0,\\\\\\\"switch\\\\\\\":29},\\\\\\\"S_SCREENSHOT\\\\\\\":1,\\\\\\\"S_CHECK_INJECT_STATUS\\\\\\\":{\\\\\\\"list\\\\\\\":[],\\\\\\\"switch\\\\\\\":21},\\\\\\\"S_CHECK_FRAME_ATTACH\\\\\\\":{\\\\\\\"list\\\\\\\":[],\\\\\\\"switch\\\\\\\":21},\\\\\\\"S_CHECK_DEBUG_STATUS\\\\\\\":21,\\\\\\\"S_CHECK_LOCATION_FRAUD\\\\\\\":21,\\\\\\\"S_CHECK_DOMAIN\\\\\\\":{\\\\\\\"list\\\\\\\":[],\\\\\\\"switch\\\\\\\":21},\\\\\\\"S_CHECK_PLUGIN\\\\\\\":{\\\\\\\"list\\\\\\\":[],\\\\\\\"switch\\\\\\\":21},\\\\\\\"S_CHECK_SIGNATURE\\\\\\\":{\\\\\\\"list\\\\\\\":[],\\\\\\\"switch\\\\\\\":21},\\\\\\\"S_CHECK_ABNORMAL_CA\\\\\\\":{\\\\\\\"list\\\\\\\":[],\\\\\\\"switch\\\\\\\":21},\\\\\\\"S_CHECK_WIFI_PROXY\\\\\\\":21,\\\\\\\"S_CHECK_MULTI_APK\\\\\\\":21,\\\\\\\"S_CHECK_FREQUENCY_ACCOUNT\\\\\\\":{\\\\\\\"period\\\\\\\":0,\\\\\\\"switch\\\\\\\":21,\\\\\\\"frequency\\\\\\\":0},\\\\\\\"S_CHECK_FREQUENCY_IP\\\\\\\":{\\\\\\\"period\\\\\\\":0,\\\\\\\"switch\\\\\\\":21,\\\\\\\"frequency\\\\\\\":0},\\\\\\\"S_CHECK_FREQUENCY_LOCATION\\\\\\\":{\\\\\\\"period\\\\\\\":0,\\\\\\\"switch\\\\\\\":21,\\\\\\\"frequency\\\\\\\":0},\\\\\\\"S_CHECK_FREQUENCY_RESTART\\\\\\\":{\\\\\\\"period\\\\\\\":0,\\\\\\\"switch\\\\\\\":21,\\\\\\\"frequency\\\\\\\":0},\\\\\\\"S_FRAME_ATTACH\\\\\\\":{\\\\\\\"list\\\\\\\":[],\\\\\\\"switch\\\\\\\":21},\\\\\\\"S_CHECK_DANGER_APPS\\\\\\\":{\\\\\\\"list\\\\\\\":[],\\\\\\\"switch\\\\\\\":21},\\\\\\\"S_CHECK_ROOT_STATUS\\\\\\\":21,\\\\\\\"S_ADB_ENABLED\\\\\\\":21,\\\\\\\"S_CHECK_VM_STATUS\\\\\\\":21,\\\\\\\"S_ALLOW_MOCK_LOCATION\\\\\\\":21,\\\\\\\"S_CHECK_SYS_USER_CA\\\\\\\":{\\\\\\\"list\\\\\\\":[],\\\\\\\"switch\\\\\\\":21},\\\\\\\"S_CHECK_CUSTOM_ROM\\\\\\\":21,\\\\\\\"S_CRASH_MONITOR\\\\\\\":1}}}\\\",\\\"sendTime\\\":1634789663000,\\\"toAccount\\\":\\\"5ab637a321e1233481e481186a104187193f03fa\\\",\\\"type\\\":1},{\\\"fromChannelAccount\\\":\\\"system\\\",\\\"msgContent\\\":\\\"{command:{requesttype:AndroidSafePolicy, config:{\\\\\\\"S_RUN_DEFAULT\\\\\\\":0,\\\\\\\"S_RUN_ALLOW_UNINSTALL\\\\\\\":1,\\\\\\\"S_RUN_ALWAYS\\\\\\\":0}}}\\\",\\\"sendTime\\\":1634789663000,\\\"toAccount\\\":\\\"5ab637a321e1233481e481186a104187193f03fa\\\",\\\"type\\\":1}]\"}";
        System.out.println(aa);
    }

    private void test52() {
        float aa = 10.1f;
        if (aa >= 11.0) {
            System.out.println("高版本");
        } else if (aa >= 8.2 && aa <= 10.1) {
            System.out.println("低版本");
        } else {
            System.out.println("当前华为设备不支持WIFI白名单策略");
        }
    }

    private void test51() {
        int UPLOAD_CHATRECORD = 0x901;
        System.out.println(UPLOAD_CHATRECORD);
    }

    private void test50() {
        int a = 60;
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        test51(list);
        System.out.println(list.size());
    }

    private void test51(List<String> a) {
        List<String> list = new ArrayList<>();
        list.addAll(a);
        list.add("3");
    }

    private int test49() {
        int r = 5;
        try {
            System.out.println("1");
            r = r + 1;
            int i = r / 0;

            return r;
        } catch (Exception e) {
            System.out.println(e.toString());
            return r;
        } finally {
            System.out.println("3");
        }
    }

    public String checkUrl(String url, boolean validUrl) {
        String convertUrl = "about:start";
//        if ((url != null) && (url.length() > 0)) {
//            if ((!url.startsWith("http://www.")) &&
//                    (!url.startsWith("https://www.")) &&
//                    (!url.startsWith("file://")) &&
//                    (!url.startsWith("http://")) &&
//                    (!url.startsWith("www.")) &&
//                    (!url.startsWith(Constants.URL_ABOUT_BLANK)) &&
//                    (!url.startsWith(Constants.URL_ABOUT_START))) {
//                if (validUrl) {
//                    convertUrl = "http://" + url;
//                } else {
//                    convertUrl = "http://www." + url;
//                }
//            } else {
//                convertUrl = url;
//            }
//        }
        if ((url != null) && (url.length() == 0)) {
            return convertUrl;
        }
        StringBuffer urlBuffer = new StringBuffer();

        if (url.startsWith("about:blank") || url.startsWith("about:start")) {
            convertUrl = url;
        } else if (url.startsWith("http://") && !url.startsWith("http://www.")) {
            convertUrl = urlBuffer.append(url).insert(7, "www.").toString();
        } else if (url.startsWith("https://") && !url.startsWith("https://www.")) {
            convertUrl = urlBuffer.append(url).insert(8, "www.").toString();
        } else if (url.startsWith("www.")) {
            convertUrl = "http://" + url;
        } else if (!url.startsWith("http://www.") && !url.startsWith("https://www.")) {
            convertUrl = "http://www." + url;
        } else {
            convertUrl = url;
        }


        return convertUrl;
    }

    private void test46() {
        JSONArray array = new JSONArray();
        array.put("检测到root文件");
        array.put("文件路径：/data/data/root");
        array.put("判定为设备root");

        System.out.println(array.toString());

    }

    private void test45() {
        JSONObject object = new JSONObject();
        try {
            object.put("name", "yang");
            object.put("MD5", "de1713cfa6727f48f86714014bdf943b");
            object.put("actionCode", "010701");

            System.out.print(object.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void test43() {
        int sum = 300;
        int num = 600;

        num += sum;
        System.out.println(num);
    }

    private void test42() {
        int code = 16;
        if ((code & 16) == 16) {
            System.out.print("16匹配");
        } else {
            System.out.print("16不匹配");
        }

        if ((code & 8) == 8) {
            System.out.print("8匹配");
        } else {
            System.out.print("8不匹配");
        }

        if ((code & 4) == 4) {
            System.out.print("4匹配");
        } else {
            System.out.print("4不匹配");
        }

        if ((code & 2) == 2) {
            System.out.print("2匹配");
        } else {
            System.out.print("2不匹配");
        }

        if ((code & 1) == 1) {
            System.out.print("1匹配");
        } else {
            System.out.print("1不匹配");
        }
    }

    private void test41() {
        byte[] b = new byte[]{'6', '3', '9', 'f', '4', 'c', '4', 'e', '5', '2', '4',
                'a', '8', 'c', '9', '0', 'f', 'b', 'd', '6', 'c', '6', 'f', '4', '2',
                'f', '1', '8', 'f', '3', 'b', '5',
                '2', '8', '4', '8', 'a', 'e', 'c', '0', 'f', 'c', 'c', '0', '1', 'b', '0', '3', 'f', 'f',
                '0', '4', 'c', '5', '5', '0', '3', 'c', '9', 'a', 'e', '6', '1', '1'};
        System.out.println(b.length);
//        for (int i = 0; i < bytes.length; i++) {
//            String format = String.format("%02x", new Object[]{new Integer(bytes[i] & 255)});
//            System.out.print(format);
//        }
        String a = a(b);
        System.out.print(a);

    }

    public static String a(byte[] var0) {
        StringBuilder var1 = new StringBuilder(var0.length * 2);
        byte[] var2 = var0;
        int var3 = var0.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            byte var5 = var2[var4];
//            var1.append(String.format("%02x", new Object[]{new Integer(var5 & 255)}));
            var1.append(String.format("%02x", new Object[]{new Integer(var5 & 255)}));
        }

        return var1.toString();
    }

    public static String copyA(byte[] var0) {
        StringBuilder var1 = new StringBuilder(65);
//        byte[] var2 = var0;
//        int var3 = var0.length;
//
//        for (int var4 = 0; var4 < var3; ++var4) {
//            byte var5 = var2[var4];
////            var1.append(String.format("%02x", new Object[]{new Integer(var5 & 255)}));
//            var1.append(String.format("%02x", new Object[]{new Integer(var5 & 255)}));
//        }

        var1.append("5");

        return var1.toString() + " " + var1.length();
    }

    private void test39() {
        List<String> appList = new ArrayList<>();
        appList.add("应用宝");
        appList.add("爱奇艺");
        appList.add("qq");
        appList.add("qq");
        appList.add("应用宝");

        Iterator<String> iterator = appList.iterator();
        while (iterator.hasNext()) {
            String app = iterator.next();
            if ("qq".equals(app) || "应用宝".equals(app)) {
                iterator.remove();
            }
        }

        for (String app : appList) {
            System.out.println(app);
        }
    }

    private void test38() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("当前线程id:" + Thread.currentThread().getId());
                        write(10);
                    }
                }
        ).start();
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
//                        mWriteCounter.incrementAndGet();
                        System.out.println("当前线程id:" + Thread.currentThread().getId());
                        write(5);
                    }
                }
        ).start();
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
//                        mWriteCounter.incrementAndGet();
                        System.out.println("当前线程id:" + Thread.currentThread().getId());
                        write(2);
                    }
                }
        ).start();
//        new Thread(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        mWriteCounter.incrementAndGet();
//                        LogUtils.e("当前线程id:" + Thread.currentThread().getId());
//
//                    }
//                }
//        ).start();
//        new Thread(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        mWriteCounter.incrementAndGet();
//                        LogUtils.e("当前线程id:" + Thread.currentThread().getId());
//
//                    }
//                }
//        ).start();
    }

    synchronized void write(int sum) {
        try {
//            if (mWriteCounter.incrementAndGet() == 1) {
            aa = aa - sum;
            Thread.sleep(1000);
            System.out.println("数据库写入完成");
//                mWriteCounter.decrementAndGet();
//            }
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    private void test37() {
        String aa = "https://192.168.220.92:8443/rest/client/loadfile.do?file=/b9a636a6-9fdb-492d-ab21-a7c9ccd78852/com.yangyong.didi2.apk&name=靖靖靖.apk";
        int indexOf = aa.indexOf("/rest");
        System.out.println(indexOf);
        String s1 = aa.substring(indexOf);
        System.out.println(s1);
    }

    private void test36() {
        Map<Integer, String> map = new HashMap<>();
        map.put(256, "emm");
        map.put(257, "emm北京");
        String val = map.get(257);
        System.out.println(val);
    }

    private void test35() {
        String str = "change user is not allowed, user is (tysj78).";
        int start = str.indexOf("(");
        int end = str.indexOf(")");
        String substring = str.substring(start + 1, end);

        System.out.println(substring);
    }

    private void test33() {
        for (int i = 0; i < 10; i++) {
//            int number = new Random().nextInt(100) + 1;

            if (i % 2 == 0) {
                System.out.println(i);
            }


        }
    }


    private void test31() {
//        String t1="55";
//        if (!t1.equals("25")&&!t1.equals("35")) {
//            System.out.println("不符合");
//        }
        char[] chars = "你好".toCharArray();
        String string = new String(chars);
        System.out.println(string);
        Arrays.fill(chars, ' ');
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