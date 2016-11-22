package com.example.testimage;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class IntentActivity extends Activity {
	private Button bt1;
	private Button bt_help;
	private Button bt_about;
	private Button bt_exit;
	private boolean bt1Flag = false;
	private boolean btHelpFlag = false;
	private boolean btAboutFlag = false;
	private boolean btExitFlag = false;
	private SoundPool soundPool;
	private CustomDialog exitDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.intent_layout);
		bt1 = (Button) findViewById(R.id.bt1);
		bt_help = (Button) findViewById(R.id.bt_help);
		bt_about = (Button) findViewById(R.id.bt_about);
		bt_exit = (Button) findViewById(R.id.bt_exit);
		soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
		soundPool.load(this, R.raw.button34, 1);
		soundPool.load(this, R.raw.exitdialog, 2);
	}
	
	public void bt_click(View v) {
		switch (v.getId()) {
		case R.id.bt1:
			playAudio();
			Toast.makeText(this, "bt1 click", 1).show();
			break;
		case R.id.bt_help:
			playAudio();
//			Toast.makeText(this, "bt_help click", 1).show();
			showHelpDialog();
			break;
		case R.id.bt_about:
			playAudio();
//			Intent intent = new Intent(this, AboutActivity.class);
//			startActivity(intent);
			showAboutDialog();
			break;
		case R.id.bt_exit:
//			playAudio();
			showExitDialog();
			break;

		default:
			break;
		}
	}
	
//	public void back(View view) {
//		Intent intent = new Intent(this, BackUI.class);
//		startActivity(intent);
//	}

	private void showHelpDialog() {
		AlertDialog dialog = new AlertDialog.Builder(this).create();
		dialog.show();
		Window window = dialog.getWindow();
		window.setContentView(R.layout.help_layout);
		window.setLayout(1920, 900);
		window.setWindowAnimations(R.style.AnimBottom);
		Button bt_load = (Button) window.findViewById(R.id.bt_load);
		bt_load.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IntentActivity.this, GameRex.class);
				startActivity(intent);
			}
		});
		Button bt_back = (Button) window.findViewById(R.id.bt_back);
		bt_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IntentActivity.this, BackUI.class);
				startActivity(intent);
			}
		});
	/*	Button bt_load = (Button) window.findViewById(R.id.bt_load);
		final WebView wb = (WebView) window.findViewById(R.id.wb);
		bt_load.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				wb.getSettings().setJavaScriptEnabled(true);
				wb.setWebViewClient(new WebViewClient() {

					@Override
					public boolean shouldOverrideUrlLoading(WebView view,
							String url) {
						wb.loadUrl(url);
						return true;
					}
					
				});
				wb.loadUrl("file:///assets/help.html");
			}
		});
		
		*/
		
//		TextView tv_href = (TextView) window.findViewById(R.id.tv_href);
//		tv_href.setText(Html.fromHtml("<a href=\"help.html\">点击跳转</a>"));
//		tv_href.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(Intent.ACTION_VIEW);
//				File file = new File(Environment.getExternalStorageDirectory(), "help.html");
//				intent.setData(Uri.parse("content://com.android.htmlfileprovider/sdcard/help.html"));
//				intent.setClassName("com.android.browser","com.android.browser.BrowserActivity");
//				startActivity(intent);
//			}
//		});
	}

	private void showAboutDialog() {
		AlertDialog dialog = new AlertDialog.Builder(this).create();
		dialog.show();
		Window window = dialog.getWindow();
		window.setContentView(R.layout.about_layout);
		window.setLayout(890, 800);
		window.setWindowAnimations(R.style.AnimBottom);
		Button bt_update =  (Button) window.findViewById(R.id.bt_update);
		bt_update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				playAudio();
//				Toast.makeText(IntentActivity.this, "当前已经是最新版本", 0).show();
//				customToast("当前已经是最新版本", Toast.LENGTH_LONG);
//				new MyToast(IntentActivity.this, "当前已经是最新版本");
				MyToast.myText(IntentActivity.this, "当前已经是最新版本", 0).show();
			}
		});
	}

	public void showExitDialog() {
		
		exitDialog = new CustomDialog(this);	
		exitDialog.setOnPositiveListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(IntentActivity.this, "确定", 0).show();
//					exitDialog.dismiss();
				finish();
			}
		});
		exitDialog.setOnNegativeListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(IntentActivity.this, "取消", 0).show();
				exitDialog.dismiss();
			}
		});
		playExitAudio();
		exitDialog.show();
	}

	public void playAudio() {
		soundPool.play(1, 1, 1, 0, 0, 1);
	}
	
	public void playExitAudio() {
		soundPool.play(2, 1, 1, 0, 1, 1);
	}
	@Override
	public void onBackPressed() {
		showExitDialog();
	}
}
