package com.example.testimage;

import java.io.Serializable;

public class Player implements Serializable {
	public static final int male = 0;
	public static final int female = 1;

	public String name;

	public boolean computer;

	public Integer sex;

	// ��Ϣ�����x����
	public int msgX;
	// ��Ϣ�����y����
	public int msgY;
	
	public Player(String name, Integer sex) {
		this.name = name;
		this.sex = sex;
	}
}
