// IXDFAidlInterface.aidl
package cn.xdf.servicedemo;

import cn.xdf.servicedemo.IXDFPlayerStatusListener;
// Declare any non-default types here with import statements

interface IXDFAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    void setPause(String pause);

    void setPlay(String play);

    void setOnXDFPlayerStatusListener(in IXDFPlayerStatusListener listener);
}