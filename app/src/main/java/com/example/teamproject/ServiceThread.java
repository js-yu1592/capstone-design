package com.example.teamproject;
import android.os.Handler;


public class ServiceThread extends Thread {
    Handler handler;
    boolean isRun = true;

    public ServiceThread(Handler handler) {
        this.handler = handler;
    }

    public void stopForever() {
        synchronized (this) {
            this.isRun = false;
        }
    }

    public void run() {
        //반복적으로 수행할 작업을 한다.
        handler.sendEmptyMessage(0);//쓰레드에 있는 핸들러에게 메세지를 보냄
        try {
            //Thread.sleep(10000); //10초씩 쉰다.
        } catch (Exception e) {
        }
    }
}
