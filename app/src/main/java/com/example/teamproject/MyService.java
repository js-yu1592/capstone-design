package com.example.teamproject;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.RequiresApi;


public class MyService extends Service {
    NotificationManager Notifi_M;
    ServiceThread thread;
    Notification Notifi ;
    NotificationManager notificationManager;

    PendingIntent intent;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notifi_M = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        myServiceHandler handler = new myServiceHandler();
        thread = new ServiceThread(handler);
        thread.start();
        return START_STICKY;
    }

    //서비스가 종료될 때 할 작업

    public void onDestroy() {
        thread.stopForever();
        thread = null;//쓰레기 값을 만들어서 빠르게 회수하라고 null을 넣어줌.
    }

    class myServiceHandler extends Handler {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void handleMessage(android.os.Message msg) {
            intent = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(), MainActivity.class),

                    PendingIntent.FLAG_UPDATE_CURRENT);
            createNotificationChannel();

//                Notification.Builder builder = new Notification.Builder(getApplicationContext(),NotificationChannel)
            Notification noti=new Notification.Builder(getApplicationContext(),"channel_id")
                    .setSmallIcon(R.drawable.ic_launcher_background) // 아이콘 설정하지 않으면 오류남

                    .setDefaults(Notification.DEFAULT_ALL)

                    .setContentTitle("입질 감지") // 제목 설정

                    .setContentText("입질이 감지되었습니다. 낚시대를 확인해주세요!") // 내용 설정

                    //.setTicker("한줄 출력") // 상태바에 표시될 한줄 출력

                    .setAutoCancel(true)

                    .setContentIntent(intent).build();

            noti.flags=Notification.FLAG_AUTO_CANCEL;

            notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

            notificationManager.notify(0, noti);

            //토스트 띄우기
            Toast.makeText(MyService.this, "뜸?", Toast.LENGTH_LONG).show();

        }
    };
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel notificationChannel = new NotificationChannel("channel_id", "알림 테스트", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("알림 테스트");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 100, 200});
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            notificationManager.createNotificationChannel(notificationChannel);
        }


    }
}

