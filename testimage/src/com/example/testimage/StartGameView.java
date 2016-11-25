package com.example.testimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class StartGameView extends SurfaceView implements SurfaceHolder.Callback {
	
	private SurfaceHolder holder;
	private Paint paint;
	private static final byte STARTWIDTH = 32;
	private static final byte ENDWIDTH = 0;
	private static final byte step = 1;
	private byte INITWIDTH = STARTWIDTH;
	private static final int color = Color.BLACK;
	private StartGameThread sgt;
	private boolean flag;
	private Canvas canvas;
	private int screen_width;
	private int screen_height;
	private Bitmap gamebg;
	private SoundPool soundPool;

	/*public StartGameView(Context context) {
		super(context);
		holder = getHolder();
		holder.addCallback(this);

		init();
	}*/
	
	public StartGameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		holder = getHolder();
		holder.addCallback(this);

		init();
	}
	
	public void init() {
		paint = new Paint();
		paint.setAntiAlias(true);// 设置画笔无锯齿(如果不设置可以看到效果很差)
		paint.setStyle(Style.FILL);
		paint.setAntiAlias(true);
		paint.setTextSize(15.0f);
		paint.setTypeface(Typeface.DEFAULT);
		setKeepScreenOn(true);// 设置背景常亮
	}

	private class StartGameThread extends Thread {
		@Override
		public void run() {
			while (flag) {
				drawUI();
				SystemClock.sleep(1);
			}
		}
	}

	public void drawUI() {
		try {
			canvas = holder.lockCanvas();
			drawBackGroundImage();
			drawWindow();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(canvas != null) {
				holder.unlockCanvasAndPost(canvas);
			}
		}
	}
	
	public void drawBackGroundImage() {
		gamebg = BitmapFactory.decodeResource(getResources(), R.drawable.gamebg);
		Rect src = new Rect(0, 0, gamebg.getWidth(),
				gamebg.getHeight() * 3 / 5);
		Rect dst = new Rect(0, 0, screen_width, screen_height);
		canvas.drawBitmap(gamebg, src, dst, null);
	}

	/** 百叶窗效果宽度 **/
	public  void drawWindow() {
		if (INITWIDTH == ENDWIDTH)
			return;

		paint.setColor(color);
		for (int i = 0; i <= (screen_width / STARTWIDTH); i++) {
			for (int j = 0; j <= (screen_height / STARTWIDTH); j++) {
				canvas.drawRect(i * STARTWIDTH, j * STARTWIDTH, i * STARTWIDTH
						+ INITWIDTH, j * STARTWIDTH + INITWIDTH, paint);
			}
		}

		if (INITWIDTH > ENDWIDTH) {
			INITWIDTH -= step;
			if (INITWIDTH < ENDWIDTH) {
				INITWIDTH = ENDWIDTH;
			}
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		sgt = new StartGameThread();
		flag = true;
		screen_width = getWidth();
		screen_height = getHeight();
		sgt.start();
//		soundPool = new SoundPool(100,AudioManager.STREAM_MUSIC,5);
//		soundPool.load(getContext(), R.raw.cleancard, 10);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
//		soundPool.play(1, 1, 1, 1, -1, 1);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		flag = false;
	}

}
