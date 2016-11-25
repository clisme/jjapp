package com.example.testimage;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;

public class StartGame extends Activity {
	
//	private SoundPool soundPool;
	private MediaPlayer player = new MediaPlayer();
	private int soundCount;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.start_game);
//		soundPool = new SoundPool(100,AudioManager.STREAM_MUSIC,5);
//		soundPool.load(this, R.raw.cleancard, 10);
//		soundPool.play(1, 1, 1, 1, -1, 1);
		try {
			AssetFileDescriptor afd = getResources().getAssets().openFd("cleancard.ogg");
			player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
			player.prepare();
//			player.setLooping(true);
			player.start();
			player.setOnCompletionListener(new OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer mp) {
					try {
						while(soundCount != 2) {
							player.start();
							soundCount++;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
//		player.pause();
		//player.release();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}
}
