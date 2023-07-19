package cn.xdf.clientdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import cn.xdf.servicedemo.IXDFAidlInterface;
import cn.xdf.servicedemo.IXDFPlayerStatusListener;

public class MainActivity extends AppCompatActivity {

    private IXDFAidlInterface mIxdfAidlInterface;
    private ServiceConnection mServiceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_bind)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bindXDFService();
                    }
                });

        findViewById(R.id.btn_pause)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            mIxdfAidlInterface.setPause(" 小米音响");
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                });
        findViewById(R.id.btn_play)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            mIxdfAidlInterface.setPlay(" 小爱同学");
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }

                        try {
                            mIxdfAidlInterface.basicTypes(1, 2L, false,
                                    3.14f, 4.0d, "V5 小米");
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    private void bindXDFService() {
        Intent intent = new Intent();
        intent.setClassName("cn.xdf.servicedemo", "cn.xdf.servicedemo.XDFService");

        mServiceConnection = serviceConnect();
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mServiceConnection != null) {
            unbindService(mServiceConnection);
        }
    }


    private ServiceConnection serviceConnect() {
        if (mServiceConnection != null) {
            return mServiceConnection;
        }
        return new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.e("onServiceConnected", "服务绑定啦....");
                try {
                    mIxdfAidlInterface = IXDFAidlInterface.Stub.asInterface(iBinder);
                    mIxdfAidlInterface.setOnXDFPlayerStatusListener(new IXDFPlayerStatusListener.Stub() {

                        @Override
                        public void onPauseSuccess() throws RemoteException {
                            Log.e("客户端", "onPauseSuccess");
                        }

                        @Override
                        public void onPauseFailed(int errorCode) throws RemoteException {
                            Log.e("客户端", "onPauseFailed：" + errorCode);
                        }

                        @Override
                        public void onPlaySuccess() throws RemoteException {
                            Log.e("客户端", "onPlaySuccess");
                        }

                        @Override
                        public void onPlayFailed(int errorCode) throws RemoteException {
                            Log.e("客户端", "onPlayFailed：" + errorCode);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                Log.e("onServiceConnected", "服务解绑啦....");
            }
        };
    }

}