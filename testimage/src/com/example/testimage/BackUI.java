package com.example.testimage;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class BackUI extends Activity {

	private SoundPool soundPool;
	private EditText et_advice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.back_layout);
		soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
		soundPool.load(this, R.raw.button34, 1);
		et_advice = (EditText) findViewById(R.id.et_advice);
	}

	public void submit(View v) {
		playAudio();
		String str = et_advice.getText().toString();
		if (!TextUtils.isEmpty(str)) {
			MyToast.myTextSubmit(BackUI.this, "谢谢您提出的建议！", 0).show();
		} else {
			MyToast.myTextSubmit(BackUI.this, "您输入的内容为空！", 0).show();
		}
	}

	public void playAudio() {
		soundPool.play(1, 1, 1, 0, 0, 1);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
