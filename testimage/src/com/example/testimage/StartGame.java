package com.example.testimage;

import java.io.IOException;

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
import android.view.View;
import android.view.View.OnClickListener;

public class StartGame extends Activity {

	// private SoundPool soundPool;
	private MediaPlayer player = new MediaPlayer();
	private MediaPlayer player1 = new MediaPlayer();
	private int soundCount;
	private CustomDialog exitGameDialog;
	private SoundPool pool;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.start_game);
		pool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
		pool.load(this, R.raw.exitgamedialog, 1);
		pool.load(this, R.raw.click_16, 2);
//		pool.load(this, R.raw.game_backmusic, 3);
		// soundPool = new SoundPool(100,AudioManager.STREAM_MUSIC,5);
		// soundPool.load(this, R.raw.cleancard, 10);
		// soundPool.play(1, 1, 1, 1, -1, 1);
		try {
			AssetFileDescriptor afd = getResources().getAssets().openFd("newcleancard.ogg");
			player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),afd.getLength());
			player.prepare();
			// player.setLooping(true);
			player.start();
			
			 /*player.setOnCompletionListener(new OnCompletionListener() {
			  
			 @Override 
			 public void onCompletion(MediaPlayer mp) { try {
			 while(soundCount != 2) { player.start(); soundCount++; } } catch
			 * (Exception e) { e.printStackTrace(); } } });*/
			
			player.setOnCompletionListener(new OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer mp) {
					try {
						AssetFileDescriptor assertSounds = getResources().getAssets().openFd("game_backmusic.mp3");
						player1.setDataSource(assertSounds.getFileDescriptor(), assertSounds.getStartOffset(), assertSounds.getLength());
						player1.prepare();
						player1.setLooping(true);
						player1.start();
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
		// player.pause();
		// player.release();
//		pool.play(3, 1, 1, 3, -1, 1);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
		player1.pause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	@Override
	public void onBackPressed() {
		showExitGameDialog();
	}

	public void showExitGameDialog() {

		exitGameDialog = new CustomDialog(this);
//		exitGameDialog.setAlert("温馨提示");
		exitGameDialog.setTitle("游戏正在进行中，要退出？");
		exitGameDialog.setBackgroundLayout(R.drawable.exitgame);
		exitGameDialog.setPtBtnBg(R.drawable.exit_button);
		exitGameDialog.setNvBtnBg(R.drawable.exit_button);
		exitGameDialog.setOnPositiveListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playBtnSounds();
				// Toast.makeText(IntentActivity.this, "确定", 0).show();
				// exitDialog.dismiss();
				finish();
			}
		});
		exitGameDialog.setOnNegativeListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playBtnSounds();
				// Toast.makeText(IntentActivity.this, "取消", 0).show();
				exitGameDialog.dismiss();
			}
		});
		playExitGameAudio();
		exitGameDialog.show();
	}
	
	public void playBtnSounds() {
		pool.play(2, 1, 1, 2, 0, 1);
	}

	public void playExitGameAudio() {
		pool.play(1, 1, 1, 1, 0, 1);
	}

}
