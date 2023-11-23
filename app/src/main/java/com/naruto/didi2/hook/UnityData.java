package com.naruto.didi2.hook;

import android.location.Location;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Created by wzl on 5/21/18.
 */

public class UnityData {
    //code
    public CodeSet.SortCode sc;
    public CodeSet.FuncCode fc;

    public int f_act=0;

    //是否被禁用
    public boolean f_forbid = false;

    //是否自杀
    public boolean f_kill = false;

    //是否擦除数据
    public boolean f_wipe = false;

    //是否警告
    public boolean f_alarm = false;

    //函数参数
    public boolean p_b = false;
    public String p_s = null;
    public String p_s2 = null;
    public int p_i = 0;

    public List<String> p_ls = null;

    //函数返回结果
    public boolean r_b = false;
    public String r_s = null;
    public int r_i = 0;

    public List<String> r_ls = null;

    public Location location = null;

    public void print()
    {
        String msg="";
        msg = msg + "f_forbid="+f_forbid;
        msg = msg + " " + "p_b=" + p_b;
        msg = msg + " " + "p_s=" + p_s;
        msg = msg + " " + "p_i=" + p_i;

        if (p_ls != null)
        {
            msg += " plist:";
            for (String one: p_ls)
                msg += " " + one;
        }

        msg = msg + " " + "r_b=" + r_b;
        msg = msg + " " + "r_s=" + r_s;
        msg = msg + " " + "r_i=" + r_i;

        if (p_ls != null)
        {
            msg += " rlist:";
            for (String one: r_ls)
                msg += " " + one;
        }

        //Log.w("UnityData", msg);
    }

    public void outStream(ObjectInput is) {
        try {
            this.sc = (CodeSet.SortCode)is.readObject();
            this.fc = (CodeSet.FuncCode)is.readObject();
            this.f_forbid = ((Boolean)is.readObject()).booleanValue();
            this.p_b = ((Boolean)is.readObject()).booleanValue();
            this.p_s = (String)is.readObject();
            this.p_s2 = (String)is.readObject();
            this.p_i = (int)is.readObject();
            this.p_ls = ((List<String>)is.readObject());
            this.r_b = ((Boolean)is.readObject()).booleanValue();
            this.r_s = (String)is.readObject();
            this.r_i = (int)is.readObject();
            this.r_ls = ((List<String>)is.readObject());
        } catch (ClassNotFoundException var3) {
            var3.printStackTrace();
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }

    public void inStream(ObjectOutput os) {
        try {
            os.writeObject(this.sc);
            os.writeObject(this.fc);
            os.writeObject(this.f_forbid);
            os.writeObject(Boolean.valueOf(this.p_b));
            os.writeObject(this.p_s);
            os.writeObject(this.p_s2);
            os.writeObject(Integer.valueOf(this.p_i));
            os.writeObject(this.p_ls);
            os.writeObject(Boolean.valueOf(this.r_b));
            os.writeObject(this.r_s);
            os.writeObject(Integer.valueOf(this.r_i));
            os.writeObject(this.r_ls);
        } catch (IOException var3) {
            var3.printStackTrace();
        }
    }

    public Object inObj(byte[] data) {
        Object res = null;

        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            ObjectInput is = null;
            is = new ObjectInputStream(bis);
            this.outStream(is);
            is.close();
        } catch (FileNotFoundException var5) {
            var5.printStackTrace();
        } catch (IOException var6) {
            var6.printStackTrace();
        }

        return res;
    }

    public byte[] outObj() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutput os = null;
            os = new ObjectOutputStream(bos);
            this.inStream(os);
            os.close();
            return bos.toByteArray();
        } catch (IOException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public void setAct(int act){
        f_act = act;

        if((act & ControlManager.P_BIT_FORBID) != 0)
            f_forbid = true;
        if((act & ControlManager.P_BIT_KILL) != 0)
            f_kill = true;
        if((act & ControlManager.P_BIT_ALARM) != 0)
            f_alarm = true;
        if((act & ControlManager.P_BIT_WIPE) != 0)
            f_wipe = true;
    }

    public void setAct(CodeSet.FuncCode act){
        setAct(ControlManager.getInstance().GetSwitch(act));
    }
}
