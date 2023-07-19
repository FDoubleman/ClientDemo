// IXDFPlayerStatusListener.aidl
package cn.xdf.servicedemo;

// Declare any non-default types here with import statements

interface IXDFPlayerStatusListener {
    void onPauseSuccess();
    void onPauseFailed(int errorCode);
    void onPlaySuccess();
    void onPlayFailed(int errorCode);
}