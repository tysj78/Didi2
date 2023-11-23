// IMyAidlInterface.aidl
package com.naruto.aotosize;

// Declare any non-default types here with import statements

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return appwidget_info in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);

     int addSum(int a,int b);
      String getMesg();
}
