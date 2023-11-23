package com.naruto.didi2.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.naruto.didi2.R;


public class DeBugActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_de_bug);
        testMethod();
    }

    private void testMethod() {
        Object o=null;
        int a=1;
        int b=2;
        int d=a+b;
        Log.e("yy", "testMethod: "+o.toString()+d );
        for (int i=0;i<5;i++){
            Log.e("yy", "testMethod: "+i );
        }
    }
}
