package com.naruto.didi2.hook;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


/**
 * Created by uniking on 17-9-20.
 */

public class ControlManager {
    private static final ControlManager ourInstance = new ControlManager();

    public final  static int P_BIT_FORBID = 1;//0001
    public final  static int P_BIT_KILL = 2;//0010
    public final  static int P_BIT_ALARM = 4;//0100
    public final  static int P_BIT_WIPE = 8;//1000

    //policy actions
    public final static int P_ACT_ERROR = -1;//ffff策略未知
    public final static int P_ACT_PASS = 0;//000 不警告，不自杀，不生效
    public final static int P_ACT_FORBID = 1;//001 不警告，不自杀，生效，  水印生效， 禁用生效
    public final static int P_ACT_PASS_KILL = 2;//010 不警告，自杀，不生效
    public final static int P_ACT_FORBID_KILL = 3;//011 不警告，自杀，生效
    public final static int P_ACT_ALARM_PASS = 4;//100 警告，不自杀，不生效
    public final static int P_ACT_ALARM_FORBID = 5;//101 警告，不自杀，生效
    public final static int P_ACT_ALARM_KILL_PASS = 6;//110 警告，自杀，不生效
    public final static int P_ACT_ALARM_KILL_FORBI = 7;//111 警告，自杀，生效
    public final static int P_ACT_WIPE_ALARM_PASS = 12;//1100 擦数据，警告，不自杀，不生效
    public final static int P_ACT_WIPE_ALARM_FORBID = 13;//1101 擦数据，警告，不自杀，生效

    //policy content
    private HashMap<Integer, Integer> m_switch_list = new HashMap<Integer, Integer>();
    private HashMap<Integer, List<String>> m_roster_list = new HashMap<Integer, List<String>>();
    private WatermarkCfg watermarkCfg = new WatermarkCfg();
    private NetworkUseCfg networkUseCfg = new NetworkUseCfg();
    private ArrayList<String> m_src_path;
    private String signatureMD5="";
    private int m_patternlock_timeout = 0;


    public static ControlManager getInstance() {
        return ourInstance;
    }

    public int GetSwitch(CodeSet.FuncCode type)
    {
        synchronized (m_switch_list){
            if (m_switch_list.isEmpty()) {
                return P_ACT_ERROR;
            }

            Integer v = m_switch_list.get(type.ordinal());
            if(null == v)
                return P_ACT_PASS;

            return v.intValue();
        }
    }

    public void setSwitchList(Map<Integer, Integer> switch_list){
        synchronized (m_switch_list){
            for(Integer e : switch_list.keySet()){
                m_switch_list.put(e, switch_list.get(e));
            }
        }
    }

    public void setRosterList(HashMap<Integer, List<String>> roster_list) {
        synchronized (m_roster_list){
            for(Integer e : roster_list.keySet()){
                m_roster_list.put(e, roster_list.get(e));
            }
        }
    }

    public WatermarkCfg getWatermarkCfg() {
        return watermarkCfg;
    }

    public void updateWatermarkCfg(WatermarkCfg watermarkCfg) {
        this.watermarkCfg = watermarkCfg;

    }

    public NetworkUseCfg getNetworkUseCfg() {
        return networkUseCfg;
    }

    public void updateNetworkUseCfg(NetworkUseCfg networkUseCfg) {
        this.networkUseCfg = networkUseCfg;

    }

    public boolean IsInTheRoster(CodeSet.FuncCode type, String name)
    {
        synchronized (m_roster_list){
            List<String> pl = new ArrayList<String>();

            pl = m_roster_list.get(type.ordinal());
            if(pl==null || 0 == pl.size())
                return false;//没有名单，不启用名单策略

            for(String one : pl)
                try {
                    if (Pattern.matches(one, name))
                        return true;
                }catch (Exception e)
                {
                    e.printStackTrace();
                    return false;
                }

            return false;
        }
    }

    public List<String> getSrcPath(){
        synchronized (m_roster_list){
            return m_roster_list.get(CodeSet.FuncCode.F_SDK_SENSITIVE_INFO.ordinal());
        }
    }

    public void setSignatureMD5(String md5){
        signatureMD5 = md5;
    }
    public String getSignatureMD5(){
        return signatureMD5;
    }

    public List<String> getSensitiveData(){
        return m_roster_list.get(CodeSet.FuncCode.F_INPUT_SENSITIVE_DATA.ordinal());
    }

    public void setPattenlockTimeout(int timeout){
        m_patternlock_timeout = timeout;
        GlobalData.mPatternLockCountdown = timeout;
    }

    public int getPattenlockTimeout(){
        return m_patternlock_timeout;
    }
}