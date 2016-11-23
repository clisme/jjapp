package com.example.testimage;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;

public class BackUI extends Activity {
	
	private SoundPool soundPool;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.back_layout);
		soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
		soundPool.load(this, R.raw.button34, 1);
	}
	
	public void submit(View v) {
		playAudio();
		MyToast.myTextSubmit(BackUI.this, "谢谢您提出的建议", 0).show();
	}
	
	public void playAudio() {
		soundPool.play(1, 1, 1, 0, 0, 1);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
