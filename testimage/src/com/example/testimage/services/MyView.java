package com.example.testimage.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import com.example.testimage.R;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/*
 * QQ:1650941960
 * by:hui
 * 使用SurfaceView 采用双缓冲机制，显示界面
 * */
public class MyView extends SurfaceView implements SurfaceHolder.Callback,
		Runnable {
	
	private static final byte STARTWIDTH = 32;
	private static final byte ENDWIDTH = 0;
	private static final byte step = 1;
	private byte INITWIDTH = STARTWIDTH;
	private static final int color = Color.BLACK;
	/*
	 * 控制SurfaceView显示的数据
	 */
	SurfaceHolder surfaceHolder;
	/**
	 * 画布
	 */
	Canvas canvas;
	/**
	 * 控制界面的更新，
	 */
	Boolean repaint = false;
	/*
	 * 控制线程
	 */
	Boolean start;

	Thread gameThread, drawThread;
	/**
	 * 判断当前是否要牌
	 */
	int[] flag = new int[3];
	/**
	 * 屏幕的宽度、高度
	 */
	int screen_height;
	int screen_width;
	/**
	 * 54张牌的图片位图
	 */
	Bitmap[] cardBitmap = new Bitmap[54];
	/**
	 * 背景图片位图
	 */
	Bitmap bgBitmap;
	/**
	 * 牌的背面图片位图
	 */
	Bitmap cardBgBitmap;
	/**
	 * 地主的图片位图
	 */
	Bitmap dizhuBitmap;
	/**
	 * 牌的宽、高
	 */
	int cardWidth, cardHeight;
	/**
	 * 画笔
	 */
	Paint paint;
	/**
	 * 牌的对象
	 */
	Card[] card = new Card[54];
	/**
	 * 按钮的文本
	 */
	String[] buttonText = new String[2];
	/**
	 * 提示的文本
	 */
	String[] message = new String[3];
	/**
	 * 默认隐藏按钮
	 */
	boolean hideButton = true;
	/**
	 * 存放玩家牌的集合数组
	 */
	List<Card>[] playerList = new Vector[3];

	List<MediaPlayer> mediaPlayers = new ArrayList<MediaPlayer>();
	/**
	 * 存放地主牌的集合
	 */
	List<Card> dizhuList = new Vector<Card>();
	/**
	 * 谁是地主
	 */
	int dizhuFlag = -1;
	/**
	 * 轮流，当前玩家的标识
	 */
	int turn = -1;
	/**
	 * 存放已出牌的集合
	 */
	List<Card>[] outList = new Vector[3];

	MediaPlayer[] mp = new MediaPlayer[100];

	Handler handler;

	/**
	 * 
	 * @param context
	 *            上下文
	 * @param handler
	 *            在子类更新UI界面
	 */
	public MyView(Context context, Handler handler) {
		super(context);
		Common.view = this;
		this.handler = handler;
		// 通过 SurfaceView 获取到 SurfaceHolder
		surfaceHolder = this.getHolder();
		// 如果想让SurfaceView的生命周期生效,必须调用addCallback(Callback)
		surfaceHolder.addCallback(this);
	}

	/**
	 * 初始化图片
	 */
	public void InitBitMap() {
		for (int i = 0; i < 3; i++) {
			flag[i] = 0;
		}
		dizhuFlag = -1;
		turn = -1;
		int count = 0;
		for (int i = 1; i <= 4; i++) {
			for (int j = 3; j <= 15; j++) {
				// 根据名字找出ID
				String name = "a" + i + "_" + j;
				ApplicationInfo appInfo = getContext().getApplicationInfo();
				int id = getResources().getIdentifier(name, "drawable",
						appInfo.packageName);
				// 拿到每一张牌的图片
				cardBitmap[count] = BitmapFactory.decodeResource(
						getResources(), id);
				// 将图片封装成牌
				card[count] = new Card(cardBitmap[count].getWidth(),
						cardBitmap[count].getHeight(), cardBitmap[count]);
				// 设置牌的名字
				card[count].setName(name);
				count++;
			}
		}
		// 获取小王的图片
		cardBitmap[52] = BitmapFactory.decodeResource(getResources(),
				R.drawable.a5_16);
		// 将小王图片封装成牌
		card[52] = new Card(cardBitmap[52].getWidth(),
				cardBitmap[52].getHeight(), cardBitmap[52]);
		// 设置牌的名字
		card[52].setName("a5_16");

		// 获取大王的图片
		cardBitmap[53] = BitmapFactory.decodeResource(getResources(),
				R.drawable.a5_17);
		// 大王封装牌
		card[53] = new Card(cardBitmap[53].getWidth(),
				cardBitmap[53].getHeight(), cardBitmap[53]);
		// 牌的名字
		card[53].setName("a5_17");
		// 拿到大王的宽高
		cardWidth = card[53].width;
		cardHeight = card[53].height;
		// 地主图片
		dizhuBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_ddz);
		// 背景图片
		bgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gamebg);
		// 牌的背景图片
		cardBgBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.cardbg1);
		// 初始化按钮的文本
		for (int i = 0; i < 2; i++) {
			buttonText[i] = new String();
		}
		// 设置按钮的文本
		buttonText[0] = "抢";
		buttonText[1] = "不抢";
		/**
		 * 初始化提示文本消息 初始化已出牌的集合
		 */
		for (int i = 0; i < 3; i++) {
			message[i] = new String("");
			outList[i] = new Vector<Card>();
		}
		/**
		 * 初始化画笔的参数
		 */
		paint = new Paint();
		// 设置画笔的颜色
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

		for (int i = 0; i < mp.length; i++) {
			mp[i] = new MediaPlayer();
		}

		try {
			AssetFileDescriptor afd = getResources().getAssets().openFd("game_backMusic.mp3");
			mp[0].setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),
					afd.getLength());
			mp[0].prepare();
			mp[0].setLooping(true);
			mp[0].start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*public void playAudio(String name, boolean isloop) {
		try {
			AssetFileDescriptor afd = getResources().getAssets().openFd(name);
			for (int i = 0; i < mp.length; i++) {
				mp[i].setDataSource(afd.getFileDescriptor(),
						afd.getStartOffset(), afd.getLength());
				mp[i].prepare();
				mp[i].setLooping(isloop);
				mp[i].start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * 画背景
	 */
	public void drawBackground() {
		Rect src = new Rect(0, 0, bgBitmap.getWidth() * 3 / 4,
				bgBitmap.getHeight() * 2 / 3);
		Rect dst = new Rect(0, 0, screen_width, screen_height);
		canvas.drawBitmap(bgBitmap, src, dst, null);
	}

	/**
	 * 画玩家的牌
	 * 
	 * @param player
	 *            玩家的牌的标识
	 */
	public void drawPlayer(int player) {
		if (playerList[player] != null && playerList[player].size() > 0) {
			for (Card card : playerList[player]) {
				drawCard(card);
			}
		}
	}

	/**
	 * 画牌
	 * 
	 * @param card
	 *            玩家的牌
	 */
	public void drawCard(Card card) {
		Bitmap tempbitBitmap;
		// 如果牌为背面
		if (card.rear) {
			tempbitBitmap = cardBgBitmap;
		} else {
			tempbitBitmap = card.bitmap;
		}
		canvas.drawBitmap(tempbitBitmap, card.getSRC(), card.getDST(), null);
	}

	/**
	 * 洗牌
	 */
	public void washCards() {
		// 打乱顺序
		for (int i = 0; i < 100; i++) {
			Random random = new Random();
			int a = random.nextInt(54);
			int b = random.nextInt(54);
			Card k = card[a];
			card[a] = card[b];
			card[b] = k;
		}
	}

	/**
	 * 发牌
	 */
	public void handCards() {
		try {
			AssetFileDescriptor afd = getResources().getAssets().openFd("SendPoker.mp3");
			mp[1].setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),
					afd.getLength());
			mp[1].prepare();
			mp[1].setLooping(true);
			mp[1].start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 开始发牌
		int t = 0;
		// 初始化玩家牌的集合
		for (int i = 0; i < 3; i++) {
			playerList[i] = new Vector<Card>();
		}
		// 遍历所有的牌
		for (int i = 0; i < 54; i++) {
			if (i > 50)// 地主牌
			{
				// 放置地主牌
				card[i].setLocation(screen_width / 2 - (3 * i - 155)
						* cardWidth / 2, cardWidth * 3 / 2);
				// 把牌添加到地主集合中
				dizhuList.add(card[i]);
				update();
				continue;
			}
			switch ((t++) % 3) {
			case 0:
				// 左边玩家
				card[i].setLocation(cardWidth / 2 + cardHeight, cardHeight / 2
						+ i * cardHeight / 21);
				playerList[0].add(card[i]);
				break;
			case 1:
				// 我
				card[i].setLocation(screen_width / 2 - (9 - i / 3) * cardWidth
						* 2 / 3, screen_height - cardHeight * 3 / 2);
				// 设置让玩家看到自己的牌，默认是看不到牌的
				card[i].rear = false;
				playerList[1].add(card[i]);
				break;
			case 2:
				// 右边玩家
				card[i].setLocation(screen_width - 3 * cardWidth, cardHeight
						/ 2 + i * cardHeight / 21);
				playerList[2].add(card[i]);
				break;
			}
			update();
			Sleep(100);
		}
		// 重新排序
		for (int i = 0; i < 3; i++) {
			Common.setOrder(playerList[i]);
			Common.rePosition(this, playerList[i], i);
		}
		// 显示按钮
		hideButton = false;
		update();
	}

	/**
	 * 线程睡眠
	 * 
	 * @param i
	 */
	public void Sleep(long i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 按钮
	 */
	public void drawButton() {
		if (!hideButton) {
			// 画按钮的文本
			canvas.drawText(buttonText[0], screen_width / 2 - 2 * cardWidth,
					screen_height - cardWidth * 3, paint);
			canvas.drawText(buttonText[1], screen_width / 2 + 2 * cardWidth,
					screen_height - cardWidth * 3, paint);
			// 画按钮的矩形
			canvas.drawRect(new RectF(screen_width / 2 - 3 * cardWidth,
					screen_height - cardWidth * 8 / 2, screen_width / 2
							- cardWidth, screen_height - cardWidth * 5 / 2),
					paint);
			canvas.drawRect(new RectF(screen_width / 2 + cardWidth,
					screen_height - cardWidth * 8 / 2, screen_width / 2 + 3
							* cardWidth, screen_height - cardWidth * 5 / 2),
					paint);
		}

	}

	/**
	 * 画三个玩家显示的信息
	 */
	public void drawMessage() {
		// 自己的显示消息
		if (!message[1].equals("")) {
			canvas.drawText(message[1], screen_width / 2, screen_height
					- cardWidth * 3, paint);
		}
		// 左边玩家的显示消息
		if (!message[0].equals("")) {
			canvas.drawText(message[0], cardHeight * 3 + cardWidth,
					screen_height / 4, paint);
		}
		// 右边玩家的显示消息
		if (!message[2].equals("")) {
			canvas.drawText(message[2], screen_width - cardHeight * 4,
					screen_height / 4, paint);
		}
	}

	// 下一个玩家
	public void nextTurn() {
		turn = (turn + 1) % 3;
	}

	/**
	 * 画地主的头像
	 */
	public void drawDizhuIcon() {
		if (dizhuFlag >= 0) {
			float x = 0f, y = 0f;
			// -判断谁是地主，左边的玩家是地主
			if (dizhuFlag == 0) {
				x = cardWidth / 2f;
				y = dizhuBitmap.getHeight() * 5;
			}
			// 自己是地主
			if (dizhuFlag == 1) {
				x = cardWidth * 1.5f;
				y = screen_height - 2f * cardHeight;
			}
			// 右边的玩家是地主
			if (dizhuFlag == 2) {
				x = screen_width - cardWidth / 2f - dizhuBitmap.getWidth();
				y = dizhuBitmap.getHeight() * 5;
			}
			canvas.drawBitmap(dizhuBitmap, x, y, null);
		}
	}

	/**
	 * 画玩家已出的牌
	 */
	public void drawOutList() {
		int x = 0, y = 0;
		// 遍历自己牌的集合
		for (int i = 0, len = outList[1].size(); i < len; i++) {
			x = screen_width / 2 + (i - len / 2) * cardWidth / 3;
			y = screen_height - cardHeight * 3;
			canvas.drawBitmap(outList[1].get(i).bitmap, x, y, null);
		}
		// 遍历左边牌的集合
		for (int i = 0, len = outList[0].size(); i < len; i++) {
			x = 4 * cardWidth;
			y = screen_height / 2 + (i - len / 2 - 7) * cardHeight / 4;
			canvas.drawBitmap(outList[0].get(i).bitmap, x, y, null);
		}
		// 遍历右边牌的集合
		for (int i = 0, len = outList[2].size(); i < len; i++) {
			x = screen_width - cardWidth * 5;
			y = screen_height / 2 + (i - len / 2 - 7) * cardHeight / 4;
			canvas.drawBitmap(outList[2].get(i).bitmap, x, y, null);
		}
	}

	/**
	 * 当SurfaceView改变的时候调用
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		System.out.println("当SurfaceView改变的时候调用");
	}

	/**
	 * 当SurfaceView创建的时候调用
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		System.out.println("当SurfaceView创建的时候调用");
		// 开启线程
		start = true;
		// 获取到当前屏幕的宽、高
		screen_height = getHeight();
		screen_width = getWidth();
		// 初始化
		InitBitMap();
		// 洗牌
		washCards();
		// 开始游戏线程
		gameThread = new Thread(new Runnable() {
			@Override
			public void run() {
				// 开始发牌
				handCards();
				//mp[1].stop();
				//mp[1].release();

				// 等待地主选完
				while (start) {
					switch (turn) {
					case 0:
						player0();
						break;
					case 1:
						player1();
						break;
					case 2:
						player2();
						break;
					default:
						break;
					}
					win();
				}
			}
		});
		gameThread.start();
		// 开始绘图线程
		drawThread = new Thread(this);
		drawThread.start();
	}

	// player0
	public void player0() {
		// Log.i("mylog", "玩家0");
		List<Card> player0 = null;
		Common.currentFlag = 0;
		if (flag[1] == 0 && flag[2] == 0) {
			player0 = Common.getBestAI(playerList[0], null);

		} else if (flag[2] == 0) {
			Common.oppoerFlag = 1;
			player0 = Common.getBestAI(playerList[0], outList[1]);
		} else {
			Common.oppoerFlag = 2;
			player0 = Common.getBestAI(playerList[0], outList[2]);
		}
		message[0] = "";
		outList[0].clear();
		setTimer(3, 0);
		if (player0 != null) {
			outList[0].addAll(player0);
			playerList[0].removeAll(player0);
			Common.rePosition(this, playerList[0], 0);
			message[0] = "";
			flag[0] = 1;
		} else {
			message[0] = "不要";
			flag[0] = 0;
		}
		update();
		nextTurn();
	}

	// player2
	public void player2() {
		// Log.i("mylog", "玩家2");
		Common.currentFlag = 2;
		List<Card> player2 = null;
		if (flag[1] == 0 && flag[0] == 0) {
			player2 = Common.getBestAI(playerList[2], null);
		} else if (flag[1] == 0) {
			player2 = Common.getBestAI(playerList[2], outList[0]);
			Common.oppoerFlag = 0;
		} else {
			player2 = Common.getBestAI(playerList[2], outList[1]);
			Common.oppoerFlag = 1;
		}
		message[2] = "";
		outList[2].clear();
		setTimer(3, 2);
		if (player2 != null) {
			outList[2].addAll(player2);
			playerList[2].removeAll(player2);
			Common.rePosition(this, playerList[2], 2);
			message[2] = "";
			flag[2] = 1;
		} else {
			message[2] = "不要";
			flag[2] = 0;
		}
		update();
		nextTurn();
	}

	// player1
	public void player1() {
		Sleep(1000);
		// 开始写出牌的了
		buttonText[0] = "出牌";
		buttonText[1] = "不要";
		hideButton = false;
		outList[1].clear();
		update();
		// 倒计时
		int i = 28;
		while ((turn == 1) && (i-- > 0)) {
			// 计时器函数draw timer.画出计时画面
			message[1] = i + "";
			update();
			Sleep(1000);
		}
		hideButton = true;
		update();
		if (turn == 1 && i <= 0)// 说明用户没有任何操作
		{
			// 自动不要，或者选一张随便出
			message[1] = "不要";
			flag[1] = 0;
			nextTurn();
		}
		update();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		System.out.println("当SurfaceView销毁的时候调用");
		// 关闭线程
		start = false;

		for (int i = 0; i < mp.length; i++) {
			if (mp[i] != null) {
				mp[i].stop();
				mp[i].release();
			}
		}
	}

	/**
	 * 绘图线程
	 */
	@Override
	public void run() {
		while (start) {
			if (repaint) {
				onDraw();
				repaint = false;
				Sleep(5);
			}
		}
	}

	/**
	 * 画图
	 */
	public void onDraw() {
		// 加锁
		synchronized (surfaceHolder) {
			try {
				canvas = surfaceHolder.lockCanvas();
				// 画背景
				drawBackground();
				drawWindow();
				// 画玩家的牌
				for (int i = 0; i < 3; i++) {
					drawPlayer(i);
				}
				// 画地主牌
				for (int i = 0, len = dizhuList.size(); i < len; i++) {
					drawCard(dizhuList.get(i));
				}
				// 画按钮( 抢地主,不抢,出牌,不出)
				drawButton();
				// message部分 用3个String存
				drawMessage();
				// 画地主图标
				drawDizhuIcon();
				// 出牌界面(3个地方,用3个vector存)
				drawOutList();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (canvas != null)
					// 解锁画布并提交
					surfaceHolder.unlockCanvasAndPost(canvas);
			}
		}
	}
	/** 百叶窗效果宽度 **/
	public void drawWindow() {
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

	/**
	 * 更新界面
	 */
	public void update() {
		repaint = true;
	}

	/**
	 * 屏幕点击事件
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 只接受按下事件
		if (event.getAction() != MotionEvent.ACTION_UP)
			return true;
		// 点选牌
		EventAction eventAction = new EventAction(this, event);
		Card card = eventAction.getCard();
		if (card != null) {
			Log.i("mylog", card.name);
			// 如果牌被点击了
			if (card.clicked) {
				card.y += card.height / 3;
			}
			// 牌没有被点状态
			else {
				card.y -= card.height / 3;
			}
			// 把牌设置为没有被点击状态
			card.clicked = !card.clicked;
			update();// 重绘
		}
		// 按钮事件
		eventAction.getButton();
		return true;
	}

	/**
	 * 
	 * @param t
	 *            几秒钟
	 * @param flag
	 *            哪个玩家的提示信息
	 */
	public void setTimer(int t, int flag) {
		while (t-- > 0) {
			Sleep(1000);
			message[flag] = t + "";
			update();
		}
		// 清空玩家的提示信息
		message[flag] = "";
	}

	// 判断成功
	public void win() {
		int flag = -1;
		if (playerList[0].size() == 0)
			flag = 0;
		if (playerList[1].size() == 0)
			flag = 1;
		if (playerList[2].size() == 0)
			flag = 2;
		if (flag > -1) {
			for (int i = 0; i < 54; i++) {
				card[i].rear = false;
			}
			update();
			start = false;
			Message msg = new Message();
			msg.what = 0;
			Bundle builder = new Bundle();
			if (flag == 1)
				builder.putString("data", "恭喜你赢了");
			if (flag == dizhuFlag && flag != 1)
				builder.putString("data", "恭喜电脑" + flag + "赢了");
			if (flag != dizhuFlag && flag != 1)
				builder.putString("data", "恭喜你同伴赢了");
			for (int i = 0; i < 54; i++)
				card[i].rear = false;
			msg.setData(builder);
			handler.sendMessage(msg);
		}
	}
}
