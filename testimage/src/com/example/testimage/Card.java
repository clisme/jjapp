package com.example.testimage;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Card {
	int x = 0;
	int y = 0;
	
	int width;
	int height;
	
	Bitmap cardBitmap;
	
	String name;
	
	boolean rear = true;
	
	boolean clicked = true;
	
	public Card(int width, int height, Bitmap cardBitmap) {
		this.width = width;
		this.height = height;
		this.cardBitmap = cardBitmap;
	}
	
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Rect getSRC(){
		return new Rect(0, 0, width, height);
	}
	
	public Rect getDST(){
		return new Rect(x, y, x + width, y + height);
	}
	
	public Rect getRotate() {
		return new Rect(y, x, x + height, y + width);
	}
}
