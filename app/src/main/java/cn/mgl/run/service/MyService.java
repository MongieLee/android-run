package cn.mgl.run.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;

import cn.mgl.run.MainActivity;
import cn.mgl.run.R;

public class MyService extends Service {
    DownloadBinder binder = new DownloadBinder();

    public static class DownloadBinder extends Binder {
        private static final String TAG = "hahah";

        public void startDownload() {
            Log.d(TAG, "start downLoad");
        }

        public int getProgress() {
            Log.d(TAG, "getProgress: ");
            return 0;
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
//        stopSelf();
        return binder;
    }

    @Override
    public void onCreate() {
//        创建服务时调用
        super.onCreate();
        Log.d("sdfsdfds", "onCreate: ");
        Notification.Builder caonima = new Notification.Builder(this, "caonima").setSmallIcon(R.drawable.ic_launcher_foreground);
        caonima.setContentTitle("hahhaha");
        caonima.setContentText("sdjfklsdjkfljsdklf");
//        startForeground(1111, caonima.build());
//        sm.notify(1, caonima.build());
    }

    @Override
    public void onDestroy() {
//        每次服务启动时调用
        super.onDestroy();
        System.out.println("onDestroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 服务销毁时调用
        Log.d("sdfsdfds", "onStartCommand: ");
        AlarmManager sm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        long triggerAtTime = SystemClock.elapsedRealtime() + 2 * 1000;
        Intent intent2 = new Intent(this, FuckService.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent2, PendingIntent.FLAG_IMMUTABLE);
        sm.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);

    }
}
