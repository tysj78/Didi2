package com.naruto.didi2.util;

/**
 * Created by DELL on 2022/1/6.
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class MailSend {

    private Map<Integer, TimerTask> map = new HashMap<>();
    private Timer timer = new Timer();

    public void triger(String s, int j, long tses) throws InterruptedException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println(sdf.format(new Date()) + "-------" + s);
            }
        };
//        timer.scheduleAtFixedRate(task, 1000L, tses);
        timer.schedule(task, 1000L, tses);
        map.put(j, task);
    }

    public void stop(int sign) {
        TimerTask task = map.get(sign);
        if (task != null) {
            task.cancel();
            System.out.printf("结束任务[%s]", sign);
            map.remove(sign);
        }
    }

    public static void main(String[] args) throws Exception {
        MailSend mailSend = new MailSend();
        try (Scanner scan = new Scanner(System.in)) {
            while (true) {
                System.out.print("输入");
                String read = scan.nextLine();
                System.out.println("输入数据：" + read);
                if (read.equals("启动1")) {
                    mailSend.triger("sssssssssss", 5, 10 * 1000);
                } else if (read.equals("启动2")) {
                    mailSend.triger("ddddddddddd", 6, 15 * 1000);
                } else if (read.equals("结束1")) {
                    mailSend.stop(5);
                } else if (read.equals("结束2")) {
                    mailSend.stop(5);
                }
            }
        }
    }

    public static class Test01 {
        public static void main(String[] args) {
            final Timer timer = new Timer();
//            timer.scheduleAtFixedRate(new TimerTask() {
            timer.schedule(new TimerTask() {//分别注释这行和上面这行试一试效果
                int count = 1;

                @Override
                public void run() {
                    count++;
                    if (count == 10) {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            System.out.println("延迟5s");
                            e.printStackTrace();
                        }
                    }

                    if (count == 11) {
                        cancel();
                        System.out.println("结束定时任务");
                    }
                    SimpleDateFormat sf = new SimpleDateFormat(
                            "yyyy MM dd hh:mm:ss");
                    System.out.println("当前时间："
                            + sf.format(System.currentTimeMillis()) + "计划时间："
                            + sf.format(scheduledExecutionTime()));
                }
            }, 1000, 1000);
        }
    }

}
