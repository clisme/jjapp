package com.example.testimage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.media.SoundPool;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class StartGameView extends SurfaceView implements
		SurfaceHolder.Callback, Runnable {

	private SurfaceHolder holder;
	private Paint paintRect;
	private static final byte STARTWIDTH = 32;
	private static final byte ENDWIDTH = 0;
	private static final byte step = 1;
	private byte INITWIDTH = STARTWIDTH;
	private static final int color = Color.BLACK;
	private StartGameThread sgt;
	private Thread drawThread;
	private boolean start;
	private Canvas canvas;
	private int screen_width;
	private int screen_height;
	private Bitmap gamebg;
	private SoundPool soundPool;
	Boolean repaint = false;

	Bitmap[] cardBitmaps = new Bitmap[54];
	Bitmap cardBgBitmap;
	int cardWidth;
	int cardHeight;

	Card[] cards = new Card[54];

	String[] buttonText = new String[2];

	String[] message = new String[4];

	boolean hideButton = true;

	List<Card>[] playerlists = new ArrayList[4];

	int turn = -1;

	List<Card>[] outList = new ArrayList[4];
	private Paint paint;

	/*
	 * public StartGameView(Context context) { super(context); holder =
	 * getHolder(); holder.addCallback(this);
	 * 
	 * init(); }
	 */

	public StartGameView(Context context, AttributeSet attrs) {
		super(context, attrs);

		holder = getHolder();
		holder.addCallback(this);

		init();
		
		
	}

	public void init() {
		paintRect = new Paint();
		paintRect.setAntiAlias(true);// 设置画笔无锯齿(如果不设置可以看到效果很差)
		paintRect.setStyle(Style.FILL);
		paintRect.setAntiAlias(true);
		paintRect.setTextSize(15.0f);
		paintRect.setTypeface(Typeface.DEFAULT);
		setKeepScreenOn(true);// 设置背景常亮
	}

	private class StartGameThread extends Thread {
		@Override
		public void run() {
			while (start) {
				drawUI();
				SystemClock.sleep(1);
			}
		}
	}

//	public void drawBitmap() {
//		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
//				R.drawable.cardbg2);
////		Card card = new Card(bitmap.getWidth(), bitmap.getHeight(), bitmap);
////		card.setLocation(70, -40);
////		Rect dst = card.getRotate();
////		Rect src = card.getSRC();
//		canvas.drawBitmap(bitmap, -40, 100, paintRect);
//	}

	public void drawUI() {
		try {
			canvas = holder.lockCanvas();
			drawBackGroundImage();
			drawWindow();
//			drawBitmap();
			for(int i = 0; i < 3; i++) {
				drawPlayer(i);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (canvas != null) {
				holder.unlockCanvasAndPost(canvas);
			}
		}
	}
	
	public void handCards() {
		int t = 0;
		
		for(int i = 0; i < 4; i++) {
			playerlists[i] = new ArrayList<Card>();
		}
		
		for(int i = 0; i < 52; i++) {
			switch ((t++) % 4) {
			case 0:
				cards[i].setLocation(-50, 5 * i + 100);
				playerlists[0].add(cards[i]);
				break;
			case 1:
				cards[i].setLocation(100 + i * 10, screen_height - cardHeight);
				cards[i].rear = false;
				playerlists[1].add(cards[i]);
				break;
			case 2:
				cards[i].setLocation(screen_width - 50, 5 * i + 100);
				playerlists[2].add(cards[i]);
				break;
			case 3:
				cards[i].setLocation(100 + i * 10, cardHeight - 50);
				playerlists[3].add(cards[i]);
				break;

			default:
				break;
			}
			update();
			Sleep(100);
		}
	}

	private void update() {
		repaint = true;
	}

	public void drawBackGroundImage() {
		gamebg = BitmapFactory.decodeResource(getResources(), R.drawable.gamebg);
		Rect src = new Rect(0, 0, gamebg.getWidth(), gamebg.getHeight() * 3 / 5);
		Rect dst = new Rect(0, 0, screen_width, screen_height);
		canvas.drawBitmap(gamebg, src, dst, null);
	}

	/** 百叶窗效果宽度 **/
	public void drawWindow() {
		if (INITWIDTH == ENDWIDTH)
			return;

		paintRect.setColor(color);
		for (int i = 0; i <= (screen_width / STARTWIDTH); i++) {
			for (int j = 0; j <= (screen_height / STARTWIDTH); j++) {
				canvas.drawRect(i * STARTWIDTH, j * STARTWIDTH, i * STARTWIDTH
						+ INITWIDTH, j * STARTWIDTH + INITWIDTH, paintRect);
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
		InitBitMap();
		washCards();
		sgt = new StartGameThread();
		drawThread = new Thread();
		start = true;
		screen_width = getWidth();
		screen_height = getHeight();
		sgt.start();
//		drawThread.start();
		// soundPool = new SoundPool(100,AudioManager.STREAM_MUSIC,5);
		// soundPool.load(getContext(), R.raw.cleancard, 10);
		
	}

	public void washCards() {
		for (int i = 0; i < 100; i++) {
			Random random = new Random();
			int a = random.nextInt(52);
			int b = random.nextInt(52);
			Card k = cards[a];
			cards[a] = cards[b];
			cards[b] = k;
		}
	}

	public void InitBitMap() {
		turn = -1;
		int count = 0;

		for (int i = 1; i <= 4; i++) {
			for (int j = 3; j <= 15; j++) {
				String name = "a" + i + "_" + j;
				ApplicationInfo info = getContext().getApplicationInfo();
				int id = getResources().getIdentifier(name, "drawable",
						info.packageName);
				System.out.println("id:" + Integer.toHexString(id));
				cardBitmaps[count] = BitmapFactory.decodeResource(
						getResources(), id);
				cards[count] = new Card(cardBitmaps[count].getWidth(),cardBitmaps[count].getHeight(), cardBitmaps[count]);
				cards[count++].setName(name);
			}
		}
		//cardBgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cardbg2);
		// 获取大王的图片
		cardBitmaps[53] = BitmapFactory.decodeResource(getResources(), R.drawable.a5_17);
		// 大王封装牌
		cards[53] = new Card(cardBitmaps[53].getWidth(),cardBitmaps[53].getHeight(), cardBitmaps[53]);
		// 牌的名字
		cards[53].setName("a5_17");
		// 拿到大王的宽高
		cardWidth = cards[53].width;
		cardHeight = cards[53].height;
		
		for (int i = 0; i < 2; i++) {
			buttonText[i] = new String();
		}
		
		for (int i = 0; i < 4; i++) {
			message[i] = new String("");
			outList[i] = new ArrayList<Card>();
		}
		
		paint = new Paint();
		paint.setColor(Color.WHITE);
		// 设置文字的大小
		paint.setTextSize(cardWidth * 2 / 3);
		// 设置抗锯齿
		paint.setAntiAlias(true);
		// 设置画笔为空心
		paint.setStyle(Style.STROKE);
		// 设置线宽
		paint.setStrokeWidth(1.0f);
		// 设置文字对齐方式
		paint.setTextAlign(Align.CENTER);
	}
	
	public void Sleep(long i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// soundPool.play(1, 1, 1, 1, -1, 1);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		start = false;
	}

	@Override
	public void run() {
		while (start) {
			handCards();
			if (repaint) {
				onDDraw();
				repaint = false;
				Sleep(33);
			}
		}
	}
	
	public void onDDraw() {
		// 加锁
		synchronized (holder) {
			try {
				canvas = holder.lockCanvas();
				// 画背景
				drawBackGroundImage();
				
				drawWindow();
				
				// 画玩家的牌
				for (int i = 0; i < 4; i++) {
					drawPlayer(i);
				}
//				// 画按钮( 抢地主,不抢,出牌,不出)
//				drawButton();
//				// message部分 用3个String存
//				drawMessage();
//				// 出牌界面(3个地方,用3个vector存)
//				drawOutList();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (canvas != null)
					// 解锁画布并提交
					holder.unlockCanvasAndPost(canvas);
			}
		}
	}
	
	public void drawPlayer(int player) {
		if (playerlists[player] != null && playerlists[player].size() > 0) {
			for (Card card : playerlists[player]) {
				drawCard(card);
			}
		}
	}
	
	public void drawCard(Card card) {
		Bitmap tempbitBitmap;
		// 如果牌为背面
		if (card.rear) {
			tempbitBitmap = cardBgBitmap;
		} else {
			tempbitBitmap = card.cardBitmap;
		}
		canvas.drawBitmap(tempbitBitmap, card.getSRC(), card.getDST(), null);
	}

}
