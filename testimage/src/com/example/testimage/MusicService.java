package com.example.testimage;

import java.util.HashMap;
import java.util.Map;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.IBinder;

public class MusicService extends Service {
	private static Context context;
	
	private static Map<Integer,Integer> maleSound;
	private static Map<Integer,Integer> femaleSound;
	private static Map<Integer,Integer> specialSound;
	private static SoundPool cardSoundPool;
	
	public static void initSounds(Context ctx) {
		context = ctx;
		
		cardSoundPool = new SoundPool(100,AudioManager.STREAM_MUSIC,100);
		maleSound = new HashMap<Integer,Integer>();
		femaleSound = new HashMap<Integer,Integer>();
		specialSound = new HashMap<Integer,Integer>();
		
		//loadMaleSounds();
//		loadFemaleSounds();
//		loadSpecialSounds();
	}
	
	private static void loadMaleSound(int raw,int id){
		maleSound.put(id, cardSoundPool.load(context, raw, id));
	}
	
	/*private static void loadMaleSounds() {
		//加载"单牌"
		loadMaleSound(R.raw.m_1, 1);
		loadMaleSound(R.raw.m_2, 2);
		loadMaleSound(R.raw.m_3, 3);
		loadMaleSound(R.raw.m_4, 4);
		loadMaleSound(R.raw.m_5, 5);
		loadMaleSound(R.raw.m_6, 6);
		loadMaleSound(R.raw.m_7, 7);
		loadMaleSound(R.raw.m_8, 8);
		loadMaleSound(R.raw.m_9, 9);
		loadMaleSound(R.raw.m_10, 10);
		loadMaleSound(R.raw.m_11, 11);
		loadMaleSound(R.raw.m_12, 12);
		loadMaleSound(R.raw.m_13, 13);
		loadMaleSound(R.raw.m_14, 16);
		loadMaleSound(R.raw.m_15, 17);
		
		//加载"对子"
		loadMaleSound(R.raw.m_dui1, 18);
		loadMaleSound(R.raw.m_dui2, 19);
		loadMaleSound(R.raw.m_dui3, 20);
		loadMaleSound(R.raw.m_dui4, 21);
		loadMaleSound(R.raw.m_dui5, 22);
		loadMaleSound(R.raw.m_dui6, 23);
		loadMaleSound(R.raw.m_dui7, 24);
		loadMaleSound(R.raw.m_dui8, 25);
		loadMaleSound(R.raw.m_dui9, 26);
		loadMaleSound(R.raw.m_dui10, 27);
		loadMaleSound(R.raw.m_dui11, 28);
		loadMaleSound(R.raw.m_dui12, 29);
		loadMaleSound(R.raw.m_dui13, 30);
		
		//加载"不要"
		loadMaleSound(R.raw.m_buyao1, Card.BU_YAO1);
		loadMaleSound(R.raw.m_buyao2, Card.BU_YAO2);
		loadMaleSound(R.raw.m_buyao3, Card.BU_YAO3);
		loadMaleSound(R.raw.m_buyao4, Card.BU_YAO4);
		
		//加载"大你"
		loadMaleSound(R.raw.m_dani1, Card.DA_NI1);
		loadMaleSound(R.raw.m_dani2, Card.DA_NI2);
		loadMaleSound(R.raw.m_dani3, Card.DA_NI3);
		
	}*/
	
	/*private static void loadFemaleSounds() {
		//加载"单牌"
		loadFemaleSound(R.raw.f_1, 1);
		loadFemaleSound(R.raw.f_2, 2);
		loadFemaleSound(R.raw.f_3, 3);
		loadFemaleSound(R.raw.f_4, 4);
		loadFemaleSound(R.raw.f_5, 5);
		loadFemaleSound(R.raw.f_6, 6);
		loadFemaleSound(R.raw.f_7, 7);
		loadFemaleSound(R.raw.f_8, 8);
		loadFemaleSound(R.raw.f_9, 9);
		loadFemaleSound(R.raw.f_10, 10);
		loadFemaleSound(R.raw.f_11, 11);
		loadFemaleSound(R.raw.f_12, 12);
		loadFemaleSound(R.raw.f_13, 13);
		loadFemaleSound(R.raw.f_14, 16);
		loadFemaleSound(R.raw.f_15, 17);
		
		//加载"对子"
		loadFemaleSound(R.raw.f_dui1, 18);
		loadFemaleSound(R.raw.f_dui2, 19);
		loadFemaleSound(R.raw.f_dui3, 20);
		loadFemaleSound(R.raw.f_dui4, 21);
		loadFemaleSound(R.raw.f_dui5, 22);
		loadFemaleSound(R.raw.f_dui6, 23);
		loadFemaleSound(R.raw.f_dui7, 24);
		loadFemaleSound(R.raw.f_dui8, 25);
		loadFemaleSound(R.raw.f_dui9, 26);
		loadFemaleSound(R.raw.f_dui10, 27);
		loadFemaleSound(R.raw.f_dui11, 28);
		loadFemaleSound(R.raw.f_dui12, 29);
		loadFemaleSound(R.raw.f_dui13, 30);
		
		
		//加载"不要"
		loadFemaleSound(R.raw.f_buyao1, Card.BU_YAO1);
		loadFemaleSound(R.raw.f_buyao2, Card.BU_YAO2);
		loadFemaleSound(R.raw.f_buyao3, Card.BU_YAO3);
		loadFemaleSound(R.raw.f_buyao4, Card.BU_YAO4);
		
		//加载"大你"
		loadFemaleSound(R.raw.f_dani1, Card.DA_NI1);
		loadFemaleSound(R.raw.f_dani2, Card.DA_NI2);
		loadFemaleSound(R.raw.f_dani3, Card.DA_NI3);
		
	}
	
	private static void loadFemaleSound(int raw,int id){
		femaleSound.put(id, cardSoundPool.load(context, raw, id));
	}*/
	
	/*private static void loadSpecialSounds() {
		loadSpecialSound(R.raw.click, Card.CLICK);
		loadSpecialSound(R.raw.sendcard, Card.SEND_CARD);
		loadSpecialSound(R.raw.cleancard, Card.CLEAN_CARD);
	}*/
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
