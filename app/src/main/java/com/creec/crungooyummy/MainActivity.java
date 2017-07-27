package com.creec.crungooyummy;

import android.support.v7.app.AppCompatActivity;
import java.util.Timer;
import java.util.TimerTask;
import android.content.Intent;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                final Intent it = new Intent(MainActivity.this, YummyListActivity.class); //要转向的Activity
                startActivity(it); //执行
                MainActivity.this.finish();   //从栈中杀掉欢迎页这个Activity，这样当在持续点击back键时才不至于又回到了欢迎页
            }
        };
        timer.schedule(task, 1000 * 1); //1s后执行任务
    }
}
