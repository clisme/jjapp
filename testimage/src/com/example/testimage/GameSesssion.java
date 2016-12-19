package com.example.testimage;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class GameSesssion extends Activity {
	
	private SoundPool soundPool;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.session_layout);
		ImageView iv_session = (ImageView) findViewById(R.id.iv_session);
		TextView tv_session = (TextView) findViewById(R.id.tv_session);
		soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
		soundPool.load(this, R.raw.button34, 1);
		AnimationDrawable ad = (AnimationDrawable) iv_session.getDrawable();
		ad.start();
		
		tv_session.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				soundPool.play(1, 1, 1, 0, 0, 1);
				Timer timer = new Timer();
				TimerTask tk = new TimerTask() {

					@Override
					public void run() {
						Intent intent = new Intent(GameSesssion.this, IntentActivity.class);
						startActivity(intent);
						finish();
					}
				};
				timer.schedule(tk, 200);
			}
		});
	}
}
