package com.example.testimage.services;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.view.Window;
import android.view.WindowManager;

public class MyActivity extends Activity {
	/*
	 * QQ:361106306 by:小柒 转载此程序须保留版权,未经作者允许不能用作商业用途!
	 */
	MyView myView;
	String messString;
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				messString = msg.getData().getString("data");
				showDialog();
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/**
		 * RGBA_8888为android的一种32位颜色格式，
		 * R,G,B,A分别用八位表示，
		 * Android默认格式是PixelFormat.OPAQUE，其是不带Alpha值的。
		 * 设置之后可以看到图片的显示效果就和在PC上看到一样，不会出现带状的轮廓线了
		 */
		getWindow().setFormat(PixelFormat.RGBA_8888);
		// 隐藏标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 隐藏状态栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 锁定横屏
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		myView = new MyView(this, handler);
		setContentView(myView);
	}

	public void showDialog() {
		new AlertDialog.Builder(this)
				.setMessage(messString)
				.setPositiveButton("重新开始游戏",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								reGame();
							}
						}).setTitle("By:hui,QQ:1650941960").create().show();
	}

	// 重新开始游戏
	public void reGame() {
		myView = new MyView(this, handler);
		setContentView(myView);
	}

}
