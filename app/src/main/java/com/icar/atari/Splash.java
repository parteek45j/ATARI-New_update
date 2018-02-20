package com.icar.atari;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class Splash extends AppCompatActivity {
    SharedPreferences preferences;
    boolean loggedIn=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        ActionBar a=getSupportActionBar();
//        a.hide();
        preferences=getSharedPreferences(Util.loginPrefs,MODE_PRIVATE);
        loggedIn=preferences.getBoolean(Util.booleanLogin,false);
        if (loggedIn){
            handler.sendEmptyMessageDelayed(101,5000);
        }else {
            handler.sendEmptyMessageDelayed(102,3000);
        }
    }
     Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==101){
                startActivity(new Intent(Splash.this,HomeActivity.class));
                finish();
            }else if (msg.what==102){
                startActivity(new Intent(Splash.this,LoginActivity.class));
                finish();
            }
        }
    };
}
