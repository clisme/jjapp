package com.example.testimage.services;

import android.graphics.Bitmap;
import android.graphics.Rect;
/*
 * QQ:1650941960
 * by:hui
 * */
public class Card {
	/**
	 * �Ƶĺ�����
	 */
	int x = 0;  
	/**
	 * �Ƶ�������
	 */
	int y = 0;
	/**
	 * �ƵĿ��
	 */
	int width;
	/**
	 * �Ƶĸ߶�
	 */
	int height;
	/**
	 * �Ƶ�ͼƬ
	 */
	Bitmap bitmap; 
	/**
	 * �Ƶ�����
	 */
	String name;
	/**
	 * �Ƿ��Ǳ���
	 */
	boolean rear = true;
	/**
	 * �Ƿ񱻵��
	 */
	boolean clicked = false;
	
	public Card(int width, int height, Bitmap bitmap){
		this.width = width;
		this.height = height;
		this.bitmap = bitmap;
	}
	/**
	 * �����Ƶ�λ��
	 * @param x �Ƶĺ�����
	 * @param y �Ƶ�������
	 */
	public void setLocation(int x, int y){
		this.x = x;
		this.y = y;
	}
	public void setName(String name){
		this.name = name;
	}
	/**
	 * ��ȡ��(0,0)�Ƶľ���
	 * @return
	 */
	public Rect getSRC(){
		return new Rect(0, 0, width, height);
	}
	/**
	 * ��ȡָ������Ƶľ���
	 * @return
	 */
	public Rect getDST(){
		return new Rect(x, y, x + width, y + height);
	}
}
