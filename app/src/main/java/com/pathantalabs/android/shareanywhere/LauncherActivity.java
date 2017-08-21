package com.pathantalabs.android.shareanywhere;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class LauncherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_launcher);
        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally{
                    Intent intent = new Intent(LauncherActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        timerThread.start();



    }
}
