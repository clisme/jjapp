package com.example.testimage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	MediaPlayer mp = new MediaPlayer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		// Òþ²Ø±êÌâÀ¸
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Òþ²Ø×´Ì¬À¸
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// Ëø¶¨ºáÆÁ
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);
        ImageView iv = (ImageView) findViewById(R.id.iv);
        iv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Timer timer = new Timer();
				TimerTask tk = new TimerTask() {
					
					@Override
					public void run() {
						try {
							AssetFileDescriptor afd = getResources().getAssets().openFd("btn.mp3");
							mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),
									afd.getLength());
							mp.prepare();
							mp.start();
						} catch (Exception e) {
							e.printStackTrace();
						}
						Intent intent = new Intent(MainActivity.this, IntentActivity.class);
						startActivity(intent);
						finish();
					}
				};
				timer.schedule(tk, 500);
			}
		});
    }
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	mp.stop();
		mp.release();
    }
}
