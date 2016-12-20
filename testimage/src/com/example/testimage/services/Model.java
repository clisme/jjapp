package com.example.testimage.services;

import java.util.Vector;
import java.util.List;

/*
 * QQ:1650941960
 * by:hui
 * 
 * 
 * */
public class Model {
	int count;//手数
	int value;//权值
	//一组牌
	List<String> a1 = new Vector<String>(); //单张
	List<String> a2 = new Vector<String>(); //对子
	List<String> a3 = new Vector<String>(); //3带
	List<String> a123 = new Vector<String>(); //顺子
	List<String> a112233 = new Vector<String>(); //连队
	List<String> a111222 = new Vector<String>(); //飞机
	List<String> a4 = new Vector<String>(); //炸弹
}
