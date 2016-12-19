package com.example.testimage;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.MeasureSpec;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.testimage.R.layout;
import com.example.testimage.UI.Rotate3d;

public class MainActivity extends Activity {

	// private Handler handler = new Handler() {
	//
	// @Override
	// public void handleMessage(Message msg) {
	// System.out.println("我收到消息了");
	// overridePendingTransition(android.R.anim.slide_in_left,
	// android.R.anim.slide_out_right);
	// }
	// };

	MediaPlayer mp = new MediaPlayer();
	private RelativeLayout logo_game;
	private RelativeLayout intent_menu;
	private SoundPool soundPool;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 隐藏标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 隐藏状态栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 锁定横屏
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_main);
		ImageView iv = (ImageView) findViewById(R.id.iv);
		soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
		soundPool.load(this, R.raw.button34, 1);
//		AnimationDrawable animationDrawable = (AnimationDrawable) iv.getDrawable();
//		animationDrawable.start();
		logo_game = (RelativeLayout) findViewById(R.id.logo_game);
		intent_menu = (RelativeLayout) findViewById(R.id.intent_menu);
		TextView tv_gogogo = (TextView) findViewById(R.id.tv_gogogo);
		tv_gogogo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				soundPool.play(1, 1, 1, 0, 0, 1);
				/*Timer timer = new Timer();
				TimerTask tk = new TimerTask() {

					@Override
					public void run() {
						
						// handler.sendMessage(new Message());
						// overridePendingTransition(android.R.anim.slide_in_left,
						// android.R.anim.slide_out_right);
						// LayoutInflater inflater =
						// LayoutInflater.from(MainActivity.this);
						// final View view1 =
						// inflater.inflate(R.layout.activity_main, null);
						// final View view2 =
						// inflater.inflate(R.layout.intent_layout, null);
						// final View v = View.inflate(MainActivity.this,
						// R.layout.activity_main, null);
						// final View v1 = View.inflate(MainActivity.this,
						// R.layout.intent_layout, null);
						// final ObjectAnimator visToInvis =
						// ObjectAnimator.ofFloat(v,"rotationX", 0f, 90f);
						// ObjectAnimator invisTovis =
						// ObjectAnimator.ofFloat(v1, "rotationX", -90f, 0f);
						//
						// visToInvis.addListener(new AnimatorListenerAdapter()
						// {
						//
						// @Override
						// public void onAnimationEnd(Animator animation) {
						// v.setVisibility(View.GONE);
						// visToInvis.start();
						// v1.setVisibility(View.VISIBLE);
						// }
						//
						// });

//						applyRotation();
						finish();
					}
				};
				timer.schedule(tk, 20);
			}*/
				/*try {
					AssetFileDescriptor afd = getResources().getAssets().openFd("btn.mp3");
					mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
					mp.prepare();
					mp.start();
				} catch (Exception e) {
					e.printStackTrace();
				}*/
				Intent intent = new Intent(MainActivity.this, GameSesssion.class);
				startActivity(intent);
				finish();
			}
		});
	}

//	public void applyRotation() {
//		// 得到中心点
//		float centerX = logo_game.getWidth() / 2.0f;
//		float centerY = logo_game.getHeight() / 2.0f;
//		// 根据参数创建一个新的三维动画,并且监听触发下一个动画
//		final Rotate3d rotation = new Rotate3d(0, 90, centerX, centerY, 310.0f,
//				true);
//		rotation.setDuration(300);// 设置动画持续时间
//		rotation.setInterpolator(new AccelerateInterpolator());// 设置动画变化速度
//		rotation.setAnimationListener(new DisplayNextView());// 显示下一个视图
//		logo_game.startAnimation(rotation);
//	}
//
//	private final class DisplayNextView implements Animation.AnimationListener {
//
//		public void onAnimationStart(Animation animation) {
//		}
//
//		public void onAnimationEnd(Animation animation) {
//			logo_game.post(new SwapViews());
//		}
//
//		public void onAnimationRepeat(Animation animation) {
//		}
//	}
//
//	private final class SwapViews implements Runnable {
//
//		public void run() {
//			// 首页页面以90~0度翻转
//			showView(intent_menu,logo_game, 90, 0);
//		}
//	}
//	
//	/**
//	 * 显示第二个视图动画
//	 * @param showView 要显示的视图
//	 * @param hiddenView 要隐藏的视图
//	 * @param start_jd 开始角度
//	 * @param end_jd 目标角度
//	 */
//	private void showView(RelativeLayout showView, RelativeLayout hiddenView, int start_jd, int end_jd) {
//		//同样以中心点进行翻转
//		float centerX = showView.getWidth() / 2.0f;
//		float centerY = showView.getHeight() / 2.0f;
//		hiddenView.setVisibility(View.GONE);
//		showView.setVisibility(View.VISIBLE);
//		Rotate3d rotation = new Rotate3d(start_jd, end_jd, centerX, centerY, 310.0f, false);
//		rotation.setDuration(300);//设置动画持续时间
//		rotation.setInterpolator(new DecelerateInterpolator());//设置动画变化速度
//		logo_game.startAnimation(rotation);
//	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mp.stop();
		mp.release();
	}
}
