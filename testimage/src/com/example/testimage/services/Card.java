package com.example.testimage.services;

import android.graphics.Bitmap;
import android.graphics.Rect;
/*
 * QQ:1650941960
 * by:hui
 * */
public class Card {
	/**
	 * 牌的横坐标
	 */
	int x = 0;  
	/**
	 * 牌的纵坐标
	 */
	int y = 0;
	/**
	 * 牌的宽度
	 */
	int width;
	/**
	 * 牌的高度
	 */
	int height;
	/**
	 * 牌的图片
	 */
	Bitmap bitmap; 
	/**
	 * 牌的名字
	 */
	String name;
	/**
	 * 是否是背面
	 */
	boolean rear = true;
	/**
	 * 是否被点击
	 */
	boolean clicked = false;
	
	public Card(int width, int height, Bitmap bitmap){
		this.width = width;
		this.height = height;
		this.bitmap = bitmap;
	}
	/**
	 * 设置牌的位置
	 * @param x 牌的横坐标
	 * @param y 牌的纵坐标
	 */
	public void setLocation(int x, int y){
		this.x = x;
		this.y = y;
	}
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 获取从(0,0)牌的矩形
	 * @return
	 */
	public Rect getSRC(){
		return new Rect(0, 0, width, height);
	}
	/**
	 * 获取指定点的牌的矩形
	 * @return
	 */
	public Rect getDST(){
		return new Rect(x, y, x + width, y + height);
	}
}
