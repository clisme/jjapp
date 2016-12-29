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
 * ʹ��SurfaceView ����˫������ƣ���ʾ����
 * */
public class MyView extends SurfaceView implements SurfaceHolder.Callback,
		Runnable {
	
	private static final byte STARTWIDTH = 32;
	private static final byte ENDWIDTH = 0;
	private static final byte step = 1;
	private byte INITWIDTH = STARTWIDTH;
	private static final int color = Color.BLACK;
	/*
	 * ����SurfaceView��ʾ������
	 */
	SurfaceHolder surfaceHolder;
	/**
	 * ����
	 */
	Canvas canvas;
	/**
	 * ���ƽ���ĸ��£�
	 */
	Boolean repaint = false;
	/*
	 * �����߳�
	 */
	Boolean start;

	Thread gameThread, drawThread;
	/**
	 * �жϵ�ǰ�Ƿ�Ҫ��
	 */
	int[] flag = new int[3];
	/**
	 * ��Ļ�Ŀ�ȡ��߶�
	 */
	int screen_height;
	int screen_width;
	/**
	 * 54���Ƶ�ͼƬλͼ
	 */
	Bitmap[] cardBitmap = new Bitmap[54];
	/**
	 * ����ͼƬλͼ
	 */
	Bitmap bgBitmap;
	/**
	 * �Ƶı���ͼƬλͼ
	 */
	Bitmap cardBgBitmap;
	/**
	 * ������ͼƬλͼ
	 */
	Bitmap dizhuBitmap;
	/**
	 * �ƵĿ���
	 */
	int cardWidth, cardHeight;
	/**
	 * ����
	 */
	Paint paint;
	/**
	 * �ƵĶ���
	 */
	Card[] card = new Card[54];
	/**
	 * ��ť���ı�
	 */
	String[] buttonText = new String[2];
	/**
	 * ��ʾ���ı�
	 */
	String[] message = new String[3];
	/**
	 * Ĭ�����ذ�ť
	 */
	boolean hideButton = true;
	/**
	 * �������Ƶļ�������
	 */
	List<Card>[] playerList = new Vector[3];

	List<MediaPlayer> mediaPlayers = new ArrayList<MediaPlayer>();
	/**
	 * ��ŵ����Ƶļ���
	 */
	List<Card> dizhuList = new Vector<Card>();
	/**
	 * ˭�ǵ���
	 */
	int dizhuFlag = -1;
	/**
	 * ��������ǰ��ҵı�ʶ
	 */
	int turn = -1;
	/**
	 * ����ѳ��Ƶļ���
	 */
	List<Card>[] outList = new Vector[3];

	MediaPlayer[] mp = new MediaPlayer[100];

	Handler handler;

	/**
	 * 
	 * @param context
	 *            ������
	 * @param handler
	 *            ���������UI����
	 */
	public MyView(Context context, Handler handler) {
		super(context);
		Common.view = this;
		this.handler = handler;
		// ͨ�� SurfaceView ��ȡ�� SurfaceHolder
		surfaceHolder = this.getHolder();
		// �������SurfaceView������������Ч,�������addCallback(Callback)
		surfaceHolder.addCallback(this);
	}

	/**
	 * ��ʼ��ͼƬ
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
				// ���������ҳ�ID
				String name = "a" + i + "_" + j;
				ApplicationInfo appInfo = getContext().getApplicationInfo();
				int id = getResources().getIdentifier(name, "drawable",
						appInfo.packageName);
				// �õ�ÿһ���Ƶ�ͼƬ
				cardBitmap[count] = BitmapFactory.decodeResource(
						getResources(), id);
				// ��ͼƬ��װ����
				card[count] = new Card(cardBitmap[count].getWidth(),
						cardBitmap[count].getHeight(), cardBitmap[count]);
				// �����Ƶ�����
				card[count].setName(name);
				count++;
			}
		}
		// ��ȡС����ͼƬ
		cardBitmap[52] = BitmapFactory.decodeResource(getResources(),
				R.drawable.a5_16);
		// ��С��ͼƬ��װ����
		card[52] = new Card(cardBitmap[52].getWidth(),
				cardBitmap[52].getHeight(), cardBitmap[52]);
		// �����Ƶ�����
		card[52].setName("a5_16");

		// ��ȡ������ͼƬ
		cardBitmap[53] = BitmapFactory.decodeResource(getResources(),
				R.drawable.a5_17);
		// ������װ��
		card[53] = new Card(cardBitmap[53].getWidth(),
				cardBitmap[53].getHeight(), cardBitmap[53]);
		// �Ƶ�����
		card[53].setName("a5_17");
		// �õ������Ŀ��
		cardWidth = card[53].width;
		cardHeight = card[53].height;
		// ����ͼƬ
		dizhuBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_ddz);
		// ����ͼƬ
		bgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gamebg);
		// �Ƶı���ͼƬ
		cardBgBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.cardbg1);
		// ��ʼ����ť���ı�
		for (int i = 0; i < 2; i++) {
			buttonText[i] = new String();
		}
		// ���ð�ť���ı�
		buttonText[0] = "��";
		buttonText[1] = "����";
		/**
		 * ��ʼ����ʾ�ı���Ϣ ��ʼ���ѳ��Ƶļ���
		 */
		for (int i = 0; i < 3; i++) {
			message[i] = new String("");
			outList[i] = new Vector<Card>();
		}
		/**
		 * ��ʼ�����ʵĲ���
		 */
		paint = new Paint();
		// ���û��ʵ���ɫ
		paint.setColor(Color.WHITE);
		// �������ֵĴ�С
		paint.setTextSize(cardWidth * 2 / 3);
		// ���ÿ����
		paint.setAntiAlias(true);
		// ���û���Ϊ����
		paint.setStyle(Style.STROKE);
		// �����߿�
		paint.setStrokeWidth(1.0f);
		// �������ֶ��뷽ʽ
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
	 * ������
	 */
	public void drawBackground() {
		Rect src = new Rect(0, 0, bgBitmap.getWidth() * 3 / 4,
				bgBitmap.getHeight() * 2 / 3);
		Rect dst = new Rect(0, 0, screen_width, screen_height);
		canvas.drawBitmap(bgBitmap, src, dst, null);
	}

	/**
	 * ����ҵ���
	 * 
	 * @param player
	 *            ��ҵ��Ƶı�ʶ
	 */
	public void drawPlayer(int player) {
		if (playerList[player] != null && playerList[player].size() > 0) {
			for (Card card : playerList[player]) {
				drawCard(card);
			}
		}
	}

	/**
	 * ����
	 * 
	 * @param card
	 *            ��ҵ���
	 */
	public void drawCard(Card card) {
		Bitmap tempbitBitmap;
		// �����Ϊ����
		if (card.rear) {
			tempbitBitmap = cardBgBitmap;
		} else {
			tempbitBitmap = card.bitmap;
		}
		canvas.drawBitmap(tempbitBitmap, card.getSRC(), card.getDST(), null);
	}

	/**
	 * ϴ��
	 */
	public void washCards() {
		// ����˳��
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
	 * ����
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
		
		// ��ʼ����
		int t = 0;
		// ��ʼ������Ƶļ���
		for (int i = 0; i < 3; i++) {
			playerList[i] = new Vector<Card>();
		}
		// �������е���
		for (int i = 0; i < 54; i++) {
			if (i > 50)// ������
			{
				// ���õ�����
				card[i].setLocation(screen_width / 2 - (3 * i - 155)
						* cardWidth / 2, cardWidth * 3 / 2);
				// ������ӵ�����������
				dizhuList.add(card[i]);
				update();
				continue;
			}
			switch ((t++) % 3) {
			case 0:
				// ������
				card[i].setLocation(cardWidth / 2 + cardHeight, cardHeight / 2
						+ i * cardHeight / 21);
				playerList[0].add(card[i]);
				break;
			case 1:
				// ��
				card[i].setLocation(screen_width / 2 - (9 - i / 3) * cardWidth
						* 2 / 3, screen_height - cardHeight * 3 / 2);
				// ��������ҿ����Լ����ƣ�Ĭ���ǿ������Ƶ�
				card[i].rear = false;
				playerList[1].add(card[i]);
				break;
			case 2:
				// �ұ����
				card[i].setLocation(screen_width - 3 * cardWidth, cardHeight
						/ 2 + i * cardHeight / 21);
				playerList[2].add(card[i]);
				break;
			}
			update();
			Sleep(100);
		}
		// ��������
		for (int i = 0; i < 3; i++) {
			Common.setOrder(playerList[i]);
			Common.rePosition(this, playerList[i], i);
		}
		// ��ʾ��ť
		hideButton = false;
		update();
	}

	/**
	 * �߳�˯��
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
	 * ��ť
	 */
	public void drawButton() {
		if (!hideButton) {
			// ����ť���ı�
			canvas.drawText(buttonText[0], screen_width / 2 - 2 * cardWidth,
					screen_height - cardWidth * 3, paint);
			canvas.drawText(buttonText[1], screen_width / 2 + 2 * cardWidth,
					screen_height - cardWidth * 3, paint);
			// ����ť�ľ���
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
	 * �����������ʾ����Ϣ
	 */
	public void drawMessage() {
		// �Լ�����ʾ��Ϣ
		if (!message[1].equals("")) {
			canvas.drawText(message[1], screen_width / 2, screen_height
					- cardWidth * 3, paint);
		}
		// �����ҵ���ʾ��Ϣ
		if (!message[0].equals("")) {
			canvas.drawText(message[0], cardHeight * 3 + cardWidth,
					screen_height / 4, paint);
		}
		// �ұ���ҵ���ʾ��Ϣ
		if (!message[2].equals("")) {
			canvas.drawText(message[2], screen_width - cardHeight * 4,
					screen_height / 4, paint);
		}
	}

	// ��һ�����
	public void nextTurn() {
		turn = (turn + 1) % 3;
	}

	/**
	 * ��������ͷ��
	 */
	public void drawDizhuIcon() {
		if (dizhuFlag >= 0) {
			float x = 0f, y = 0f;
			// -�ж�˭�ǵ�������ߵ�����ǵ���
			if (dizhuFlag == 0) {
				x = cardWidth / 2f;
				y = dizhuBitmap.getHeight() * 5;
			}
			// �Լ��ǵ���
			if (dizhuFlag == 1) {
				x = cardWidth * 1.5f;
				y = screen_height - 2f * cardHeight;
			}
			// �ұߵ�����ǵ���
			if (dizhuFlag == 2) {
				x = screen_width - cardWidth / 2f - dizhuBitmap.getWidth();
				y = dizhuBitmap.getHeight() * 5;
			}
			canvas.drawBitmap(dizhuBitmap, x, y, null);
		}
	}

	/**
	 * ������ѳ�����
	 */
	public void drawOutList() {
		int x = 0, y = 0;
		// �����Լ��Ƶļ���
		for (int i = 0, len = outList[1].size(); i < len; i++) {
			x = screen_width / 2 + (i - len / 2) * cardWidth / 3;
			y = screen_height - cardHeight * 3;
			canvas.drawBitmap(outList[1].get(i).bitmap, x, y, null);
		}
		// ��������Ƶļ���
		for (int i = 0, len = outList[0].size(); i < len; i++) {
			x = 4 * cardWidth;
			y = screen_height / 2 + (i - len / 2 - 7) * cardHeight / 4;
			canvas.drawBitmap(outList[0].get(i).bitmap, x, y, null);
		}
		// �����ұ��Ƶļ���
		for (int i = 0, len = outList[2].size(); i < len; i++) {
			x = screen_width - cardWidth * 5;
			y = screen_height / 2 + (i - len / 2 - 7) * cardHeight / 4;
			canvas.drawBitmap(outList[2].get(i).bitmap, x, y, null);
		}
	}

	/**
	 * ��SurfaceView�ı��ʱ�����
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		System.out.println("��SurfaceView�ı��ʱ�����");
	}

	/**
	 * ��SurfaceView������ʱ�����
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		System.out.println("��SurfaceView������ʱ�����");
		// �����߳�
		start = true;
		// ��ȡ����ǰ��Ļ�Ŀ���
		screen_height = getHeight();
		screen_width = getWidth();
		// ��ʼ��
		InitBitMap();
		// ϴ��
		washCards();
		// ��ʼ��Ϸ�߳�
		gameThread = new Thread(new Runnable() {
			@Override
			public void run() {
				// ��ʼ����
				handCards();
				//mp[1].stop();
				//mp[1].release();

				// �ȴ�����ѡ��
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
		// ��ʼ��ͼ�߳�
		drawThread = new Thread(this);
		drawThread.start();
	}

	// player0
	public void player0() {
		// Log.i("mylog", "���0");
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
			message[0] = "��Ҫ";
			flag[0] = 0;
		}
		update();
		nextTurn();
	}

	// player2
	public void player2() {
		// Log.i("mylog", "���2");
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
			message[2] = "��Ҫ";
			flag[2] = 0;
		}
		update();
		nextTurn();
	}

	// player1
	public void player1() {
		Sleep(1000);
		// ��ʼд���Ƶ���
		buttonText[0] = "����";
		buttonText[1] = "��Ҫ";
		hideButton = false;
		outList[1].clear();
		update();
		// ����ʱ
		int i = 28;
		while ((turn == 1) && (i-- > 0)) {
			// ��ʱ������draw timer.������ʱ����
			message[1] = i + "";
			update();
			Sleep(1000);
		}
		hideButton = true;
		update();
		if (turn == 1 && i <= 0)// ˵���û�û���κβ���
		{
			// �Զ���Ҫ������ѡһ������
			message[1] = "��Ҫ";
			flag[1] = 0;
			nextTurn();
		}
		update();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		System.out.println("��SurfaceView���ٵ�ʱ�����");
		// �ر��߳�
		start = false;

		for (int i = 0; i < mp.length; i++) {
			if (mp[i] != null) {
				mp[i].stop();
				mp[i].release();
			}
		}
	}

	/**
	 * ��ͼ�߳�
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
	 * ��ͼ
	 */
	public void onDraw() {
		// ����
		synchronized (surfaceHolder) {
			try {
				canvas = surfaceHolder.lockCanvas();
				// ������
				drawBackground();
				drawWindow();
				// ����ҵ���
				for (int i = 0; i < 3; i++) {
					drawPlayer(i);
				}
				// ��������
				for (int i = 0, len = dizhuList.size(); i < len; i++) {
					drawCard(dizhuList.get(i));
				}
				// ����ť( ������,����,����,����)
				drawButton();
				// message���� ��3��String��
				drawMessage();
				// ������ͼ��
				drawDizhuIcon();
				// ���ƽ���(3���ط�,��3��vector��)
				drawOutList();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (canvas != null)
					// �����������ύ
					surfaceHolder.unlockCanvasAndPost(canvas);
			}
		}
	}
	/** ��Ҷ��Ч����� **/
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
	 * ���½���
	 */
	public void update() {
		repaint = true;
	}

	/**
	 * ��Ļ����¼�
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// ֻ���ܰ����¼�
		if (event.getAction() != MotionEvent.ACTION_UP)
			return true;
		// ��ѡ��
		EventAction eventAction = new EventAction(this, event);
		Card card = eventAction.getCard();
		if (card != null) {
			Log.i("mylog", card.name);
			// ����Ʊ������
			if (card.clicked) {
				card.y += card.height / 3;
			}
			// ��û�б���״̬
			else {
				card.y -= card.height / 3;
			}
			// ��������Ϊû�б����״̬
			card.clicked = !card.clicked;
			update();// �ػ�
		}
		// ��ť�¼�
		eventAction.getButton();
		return true;
	}

	/**
	 * 
	 * @param t
	 *            ������
	 * @param flag
	 *            �ĸ���ҵ���ʾ��Ϣ
	 */
	public void setTimer(int t, int flag) {
		while (t-- > 0) {
			Sleep(1000);
			message[flag] = t + "";
			update();
		}
		// �����ҵ���ʾ��Ϣ
		message[flag] = "";
	}

	// �жϳɹ�
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
				builder.putString("data", "��ϲ��Ӯ��");
			if (flag == dizhuFlag && flag != 1)
				builder.putString("data", "��ϲ����" + flag + "Ӯ��");
			if (flag != dizhuFlag && flag != 1)
				builder.putString("data", "��ϲ��ͬ��Ӯ��");
			for (int i = 0; i < 54; i++)
				card[i].rear = false;
			msg.setData(builder);
			handler.sendMessage(msg);
		}
	}
}
