package com.naruto.didi2.util;

import android.content.Context;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by yangyong on 2019/8/29/0029.
 */

public class HookSetOnClickListenerHelper {
    /**
     * hook的核心代码
     * 这个方法的唯一目的：用自己的点击事件，替换掉 View原来的点击事件
     *
     * @param v hook的范围仅限于这个view
     */
    public static void hook(Context context, final View v) {//
        try {
            // 反射执行View类的getListenerInfo()方法，拿到v的mListenerInfo对象，这个对象就是点击事件的持有者
            Method method = View.class.getDeclaredMethod("getListenerInfo");
            method.setAccessible(true);//由于getListenerInfo()方法并不是public的，所以要加这个代码来保证访问权限
            Object mListenerInfo = method.invoke(v);//这里拿到的就是mListenerInfo对象，也就是点击事件的持有者

            //要从这里面拿到当前的点击事件对象
            Class<?> listenerInfoClz = Class.forName("android.view.View$ListenerInfo");// 这是内部类的表示方法
            Field field = listenerInfoClz.getDeclaredField("mOnClickListener");
            field.setAccessible(true);


            final View.OnClickListener onClickListenerInstance = (View.OnClickListener) field.get(mListenerInfo);//取得真实的mOnClickListener对象
            // 用自定义的 OnClickListener 替换原始的 OnClickListener
            //2. 创建我们自己的点击事件代理类
            //   方式1：自己创建代理类
//            ProxyOnClickListener proxyOnClickListener = new ProxyOnClickListener(onClickListenerInstance);
            //   方式2：由于View.OnClickListener是一个接口，所以可以直接用动态代理模式
            // Proxy.newProxyInstance的3个参数依次分别是：
            // 本地的类加载器;
            // 代理类的对象所继承的接口（用Class数组表示，支持多个接口）
            // 代理类的实际逻辑，封装在new出来的InvocationHandler内
            Object proxyOnClickListener = Proxy.newProxyInstance(context.getClass().getClassLoader(), new Class[]{View.OnClickListener.class}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    Log.d("yy", "点击事件被hook到了");//加入自己的逻辑
                    return method.invoke(onClickListenerInstance, args);//执行被代理的对象的逻辑
//                    return null;//执行被代理的对象的逻辑
                }
            });
            //3. 用我们自己的点击事件代理类，设置到"持有者"中
            field.set(mListenerInfo, proxyOnClickListener);
            //完成
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 还真是这样,自定义代理类
    static class ProxyOnClickListener implements View.OnClickListener {
        View.OnClickListener oriLis;

        ProxyOnClickListener(View.OnClickListener oriLis) {
            this.oriLis = oriLis;
        }

        @Override
        public void onClick(View v) {
            Log.d("yy", "hook点击前");
//            if (oriLis != null) {
//                oriLis.onClick(v);
//            }
            Log.d("yy", "hook点击后");
        }
    }

    public static void recordHook(){
    }
}
