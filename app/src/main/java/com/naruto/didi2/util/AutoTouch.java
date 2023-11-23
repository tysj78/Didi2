package com.naruto.didi2.util;

import android.app.Activity;

import java.io.IOException;

public class AutoTouch {
    public int width = 0;
    public int height = 0;
    private ProcessBuilder processBuilder;

    /**
     * 传入在屏幕中的比例位置，坐标左上角为基准
     *
     * @param act    传入Activity对象
     * @param ratioX 需要点击的x坐标在屏幕中的比例位置
     * @param ratioY 需要点击的y坐标在屏幕中的比例位置
     */
    public void autoClickRatio(Activity act, final double ratioX, final double ratioY) {
        width = act.getWindowManager().getDefaultDisplay().getWidth();
        height = act.getWindowManager().getDefaultDisplay().getHeight();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 线程睡眠0.3s
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 生成点击坐标
                int x = (int) (width * ratioX);
                int y = (int) (height * ratioY);

                // 利用ProcessBuilder执行shell命令
                String[] order = {"input", "tap", "" + x, "" + y};
                try {
                    new ProcessBuilder(order).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 传入在屏幕中的坐标，坐标左上角为基准
     *
     * @param type 操作类型 1点击 2滑动
     * @param x    需要点击的x坐标
     * @param y    需要点击的x坐标
     */
    public void autoClickPos(final int type, final double x, final double y, final double endX, final double endY) {
//        width = act.getWindowManager().getDefaultDisplay().getWidth();
//        height = act.getWindowManager().getDefaultDisplay().getHeight();
        String[] order = getCmd(type, x, y, endX, endY);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                // 线程睡眠0.3s
//                try {
//                    Thread.sleep(300);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                // 利用ProcessBuilder执行shell命令
//                String[] order = getCmd(type, x, y, endX, endY);
//
//                try {
//                    processBuilder = new ProcessBuilder();
//                    processBuilder.command(order);
//                    processBuilder.start();
//                    LogUtils.e("模拟操作执行成功");
//                } catch (Exception e) {
//                    LogUtils.e("模拟操作执行失败：" + e.toString());
//                }
//            }
//        }).start();
        try {
            processBuilder = new ProcessBuilder();
            processBuilder.command(order);
            processBuilder.start();
            LogUtils.e("模拟操作执行成功");
        } catch (Exception e) {
            LogUtils.e("模拟操作执行失败：" + e.toString());
        }
    }

    public void autoClickPo(final int type, final double x, final double y, final double endX, final double endY) {
//        width = act.getWindowManager().getDefaultDisplay().getWidth();
//        height = act.getWindowManager().getDefaultDisplay().getHeight();
        String[] order1 = getCmd(type, x, y, endX, endY);
        String[] order2 = getCmd(type, x, y-50, endX, endY-100);
        String[] order3 = getCmd(type, x, endY-100, endX, endY-150);
        String[] order4 = getCmd(type, x, endY-150, endX, endY-200);
        String[] order5 = getCmd(type, x, endY-200, endX, endY-250);
        String[] order6 = getCmd(type, x, endY-250, endX, endY-300);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                // 线程睡眠0.3s
//                try {
//                    Thread.sleep(300);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                // 利用ProcessBuilder执行shell命令
//                String[] order = getCmd(type, x, y, endX, endY);
//
//                try {
//                    processBuilder = new ProcessBuilder();
//                    processBuilder.command(order);
//                    processBuilder.start();
//                    LogUtils.e("模拟操作执行成功");
//                } catch (Exception e) {
//                    LogUtils.e("模拟操作执行失败：" + e.toString());
//                }
//            }
//        }).start();
        try {
            processBuilder = new ProcessBuilder();
            processBuilder.command(order1);
//            processBuilder.command(order2);
//            processBuilder.command(order3);
//            processBuilder.command(order4);
//            processBuilder.command(order5);
//            processBuilder.command(order6);
            processBuilder.start();
            LogUtils.e("模拟操作执行成功");
        } catch (Exception e) {
            LogUtils.e("模拟操作执行失败：" + e.toString());
        }
    }
    private String[] getCmd(int type, double x, double y, double endX, double endY) {
        switch (type) {
            case 1:
                //点击
                return new String[]{"input", "tap", "" + x, "" + y};
            case 2:
                //滑动 模拟滑动input swipe startX startY endX endY duration(ms)
                return new String[]{"input", "swipe", "" + x, "" + y, "" + endX, "" + endY, "200"};
        }
        return new String[]{};
    }
}