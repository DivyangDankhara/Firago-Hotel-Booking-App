package com.example.mobile_a1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class AreYouStillThere extends AppCompatActivity {

    private ProgressBar pb;
    private int LevelOfProgress=0;
    private Handler handle = new Handler();
    private boolean stopThread=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_are_you_still_there);
        pb = findViewById(R.id.ProgBar2);
        ConstraintLayout clayout = (ConstraintLayout) findViewById(R.id.ActiveLayout);
        clayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (LevelOfProgress < 100)
                {
                    Intent PrevPage = new Intent(AreYouStillThere.this,SecondActivity.class);
                    stopThread=true;
                    Thread.currentThread().interrupt();
                    //startActivity(PrevPage);
                    finish();
                }


            }

        });



        new Thread(new Runnable() {
            @Override
            public void run() {
                while (LevelOfProgress<100){
                    LevelOfProgress++;
                    SystemClock.sleep(50);
                    handle.post(new Runnable() {
                        @Override
                        public void run() {
                            pb.setProgress(LevelOfProgress);

                        }
                    });
                }
                if(LevelOfProgress==100)
                {
                    if (stopThread==true)
                    {
                        Thread.currentThread().interrupt();
                    }
                    else {
                        Intent userActive = new Intent(AreYouStillThere.this, MainActivity.class);
                        Thread.currentThread().interrupt();
                        startActivity(userActive);
                    }
                }
            }
        }).start();

    }


}
