//package com.yangyong.didi2.hook;
//
//import android.content.Context;
//import android.os.Handler;
//import android.os.Looper;
//import android.widget.Toast;
//
//import com.msmsdk.callbacklayer.CodeSet.FuncCode;
//import com.msmsdk.checkstatus.StatusLooper;
//import com.msmsdk.checkstatus.utiles.SystemTools;
//import com.msmsdk.policy.AlarmManager;
//import com.msmsdk.utiles.FileOperate;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by wzl on 5/21/18.
// */
//
//public class SystemActionSubject implements ISystemActionSubject , IHookListener{
//    private Map<FuncCode, List<ISystemActionObserver>> observer_event_map = new HashMap<FuncCode, List<ISystemActionObserver>>();
//    public static Context m_context;
//    private static ClassLoader classloader;
//    private static SystemActionSubject self = null;
//    private static List<IHook> hooks = new ArrayList<IHook>();
//
//    private SystemActionSubject()
//    {
//        classloader = SystemActionSubject.class.getClassLoader();
//        //状态检测
//        StatusLooper.getInstance().updateHooks();
//        StatusLooper.getInstance().setListener(this);
//    };
//
//    private static SystemActionSubject getInstance()
//    {
//        if(null == self)
//            self = new SystemActionSubject();
//
//        return self;
//    }
//
//    public static SystemActionSubject getInstance(Context context)
//    {
//        if(context != null)
//            m_context = context;
//
//        if(null == self)
//            self = new SystemActionSubject();
//
//        return self;
//    }
//
//
//
//    private List<FuncCode> SortCodeList2FuncCodeList(ISystemActionObserver o)
//    {
//        List<FuncCode> fcl = new ArrayList<FuncCode>();
//        List<CodeSet.SortCode> sl = o.getActCode();
//        if(sl == null)
//            return fcl;
//
//        for (CodeSet.SortCode one : sl)
//        {
//            List<FuncCode> fcl_t = CodeSet.getInstance().sortCode2FuncCode(one);
//            for(FuncCode fc: fcl_t)
//                fcl.add(fc);
//        }
//
//        return fcl;
//    }
//
//    @Override
//    public void registerObserver(ISystemActionObserver o) {
//        List<FuncCode> events = SortCodeList2FuncCodeList(o);
//        for(FuncCode one : events)
//        {
//            List<ISystemActionObserver> observers = observer_event_map.get(one);
//            if(observers == null)
//            {
//                observers = new ArrayList<ISystemActionObserver>();
//                observers.add(o);
//                observer_event_map.put(one, observers);
//            }
//            else if(!observers.contains(o)) {
//                observers.add(o);
//                observer_event_map.put(one, observers);
//            }
//        }
//
//        StatusLooper.getInstance().resetStatus();
//    }
//
//    @Override
//    public void removeObserver(ISystemActionObserver o) {
//        List<FuncCode> events = SortCodeList2FuncCodeList(o);
//        for(FuncCode one : events)
//        {
//            List<ISystemActionObserver> observers = observer_event_map.get(one);
//            if(observers != null && observers.contains(o)) {
//                observers.remove(o);
//                observer_event_map.put(one, observers);
//            }
//        }
//    }
//
//    public void act_process(FuncCode target, UnityData ud){
//        if(ud.f_alarm){
//            try {
//                final String msg = AlarmManager.getInstance().getMsg(target, ud.f_act);
//                Handler mainHandler = new Handler(Looper.getMainLooper());
//                mainHandler.post(new Runnable(){
//                @Override
//                public void run(){
//                    Toast.makeText(GlobalData.mContext, msg, Toast.LENGTH_SHORT).show();
//                }
//                });
//            }catch (Exception e){
//                MsmLog.print(e);
//            }
//
//            if(ud.f_kill){
//                ud.f_kill = false;
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Thread.sleep(1000*3);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        SystemTools.kill();
//                    }
//                }).start();
//            }
//        }
//
//        if(ud.f_kill)
//            SystemTools.kill();
//
//        if(ud.f_wipe){
//            MsmLog.print("clean data data");
//            ArrayList<String> ignore = new ArrayList<>();
//            ignore.add(GlobalData.mDataDataDir+"/app_msm_policy/control");
//            ignore.add(GlobalData.mDataDataDir+"/app_msm_policy/monitor");
//            ignore.add(GlobalData.mDataDataDir+"/app_msm_policy/hooks");
//            ignore.add(GlobalData.mDataDataDir+"/lib");
//            FileOperate.deleteAllFiles(new File(GlobalData.mDataDataDir), ignore);
//        }
//    }
//
//    @Override
//    public void callBack(FuncCode target, UnityData ud) {
//        List<ISystemActionObserver> observers = observer_event_map.get(target);
//        if(observers == null) {
//            MsmLog.print("sub --- observers == null ---");
//            act_process(target, ud);
//            return;
//        }
//
//        ud.fc = target;
//        ud.sc = CodeSet.getInstance().funcCode2SortCode(target);
//
//        for (ISystemActionObserver one: observers)
//        {
//            //hook内不要占用太长时间，使用线程处理
//            one.update(ud);
//        }
//        act_process(target, ud);
//    }
//
//    public void addHooks(IHook e){
//        synchronized (hooks){
//            hooks.add(e);
//        }
//    }
//
//    public void updateHooks()
//    {
//        MsmLog.print("SystemActionSubject updateHooks");
//        synchronized (hooks){
//            int c = hooks.size();
//            for (int i=0; i< c ; ++i)
//            {
//                try {
//                    if(MsmLog.debug())
//                        MsmLog.print("SystemActionSubject updateHooks, " + hooks.get(i).description());
//                    hooks.get(i).setListener(this);
//                } catch (Exception e) {
//                    MsmLog.print(e);
//                }
//            }
//        }
//
//        //状态检测
//        StatusLooper.getInstance().updateHooks();
//        StatusLooper.getInstance().setListener(this);
//    }
//}
