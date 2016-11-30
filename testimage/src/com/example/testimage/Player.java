package com.example.testimage;

import java.io.Serializable;

public class Player implements Serializable {
	public static final int male = 0;
	public static final int female = 1;

	public String name;

	public boolean computer;

	public Integer sex;

	// 消息区域的x坐标
	public int msgX;
	// 消息区域的y坐标
	public int msgY;
	
	public Player(String name, Integer sex) {
		this.name = name;
		this.sex = sex;
	}
}
