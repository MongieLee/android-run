package cn.mgl.run;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import cn.mgl.run.service.MyService;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button btn;


    Button bindBtn;
    Button cancelService;
    MyService.DownloadBinder downloadBinder;

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: " + name);
            downloadBinder = (MyService.DownloadBinder) service;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.ceshi);
        Button qidong = findViewById(R.id.qidong);
        qidong.setOnClickListener(v -> {
            Intent intent = new Intent(this, MyService.class);
            startService(intent);
        });

        NotificationChannel notificationChannel = new NotificationChannel("caonima", "caonima", NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.setDescription("ehhehehe");
        NotificationManager sm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        sm.createNotificationChannel(notificationChannel);

        bindBtn = findViewById(R.id.bind);
        cancelService = findViewById(R.id.cancelBind);
        bindBtn.setOnClickListener(r -> {
            Intent intent = new Intent(this, MyService.class);
            bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        });

        cancelService.setOnClickListener(q -> unbindService(serviceConnection));

        Button tingzhi = findViewById(R.id.tingzhi);
        tingzhi.setOnClickListener(v -> {
            Intent intent = new Intent(this, MyService.class);
            stopService(intent);
        });

        btn = findViewById(R.id.fuckbtn);
//        btn.setOnClickListener(v -> {
//            new Thread(() -> {
//                textView.setText("zhunbeibaocuo");
//            }).start();
//        });
//        安卓不允许在子线程更新主线程UI，这样调用会报错
        btn.setOnClickListener(v -> {
            new Thread(() -> {
                System.out.println("真的运行了吗");
                Message message = new Message();
                message.what = 123;
                mHandler.sendMessage(message);
            }).start();
        });
    }

    final private String TAG = "MAINACTIVITY";

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            int what = msg.what;
            Log.d(TAG, "handleMessage: " + what);
            if (what == 123) {
                textView.setText("哈哈哈哈哈哈");
            }
        }
    };
}