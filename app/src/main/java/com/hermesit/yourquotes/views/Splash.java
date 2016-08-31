package com.hermesit.yourquotes.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.hermesit.yourquotes.R;
import com.hermesit.yourquotes.views.MainActivity;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_layout);


        Runnable splashRun = new Runnable() {
            @Override
            public void run() {
                callMainActivity();
                finish();
            }
        };

        Handler myHandler = new Handler();
        myHandler.postDelayed(splashRun, 3000);
    }

    public void callMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}

