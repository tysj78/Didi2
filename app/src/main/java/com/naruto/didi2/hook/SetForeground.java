package com.naruto.didi2.hook;

import android.widget.FrameLayout;

/**
 * Created by wzl on 9/9/20.
 */

public class SetForeground implements Runnable {
    FrameLayout m_frameLayout;
    ProxyWaterMark m_pVar;
    public SetForeground(FrameLayout frameLayout, ProxyWaterMark pVar){
        m_frameLayout = frameLayout;
        m_pVar = pVar;
    }
    @Override
    public void run() {
        m_frameLayout.setForeground(m_pVar);
        MsmLog.print("setForeground " + m_frameLayout.getClass().getName());
    }
}
