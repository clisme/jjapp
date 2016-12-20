package com.example.testimage.services;

import java.util.List;
import java.util.Random;

import android.R.integer;
import android.util.Log;
import android.view.MotionEvent;

public class EventAction {
	/*
	 * QQ:1650941960
	 * by:hui
	 * */
	MotionEvent event;
	MyView view;

	public EventAction(MyView view, MotionEvent event) {
		this.event = event;
		this.view = view;
	}
	/**
	 * 操作按钮事件
	 */
	public void getButton(){
		// 如果按钮显示状态
		if(!view.hideButton){
			// 获取手指点击的坐标(x,y)
			float x = event.getX(), y = event.getY();
			//左边按钮
			if((x > view.screen_width/2 - 3*view.cardWidth) && (y > view.screen_height - view.cardHeight*5/2) &&
					(x < view.screen_width/2 - view.cardWidth) && (y < view.screen_height - view.cardHeight*11/6)) {
				// 如果左边按钮是“抢地主”
				if(view.buttonText[0].equals("抢地主")) {
					// 将地主牌都翻过来
					for(Card card : view.dizhuList) {
						card.rear = false;
					}
					// 更新界面
					view.update();
					// 等待其他玩家抢地主
					view.setTimer(5, 1);
					// 将地主牌添加到玩家集合中
					view.playerList[1].addAll(view.dizhuList);
					// 并清空地主牌的集合
					view.dizhuList.clear();
					// 对玩家牌排序
					Common.setOrder(view.playerList[1]);
					// 重新设置其位置
					Common.rePosition(view, view.playerList[1], 1);
					// 给地主设置一个标记
					view.dizhuFlag = 1;
					Common.dizhuFlag = view.dizhuFlag;
					// 更新界面
					view.update();
					// 记录当前玩家的标记
					view.turn = 1;
				}
				// 如果左边按钮是“出牌”
				if(view.buttonText[0].equals("出牌")) {
					//选出最好的出牌(跟牌和主动出牌)
					List<Card> oppo = null;
					if(view.outList[0].size() <= 0 && view.outList[2].size() <= 0) {
						oppo = null;
					}
					else {
						oppo = (view.outList[0].size()>0) ? view.outList[0] : view.outList[2];
					}
					List<Card> mybest=Common.getMyBestCards(view.playerList[1], oppo);
					//Common.getBestAI(view.playerList[1],null);
					if(mybest==null)
						return;
					synchronized (view) {
						//加入outlist
						view.outList[1].clear();
						view.outList[1].addAll(mybest);
						//退出playerlist
						view.playerList[1].removeAll(mybest);
					}
					Common.rePosition(view, view.playerList[1], 1);
					view.flag[1]=1;
					view.message[1]="";
					view.nextTurn();
					view.update();
				}
				view.hideButton = !view.hideButton;
			}
			//右边
			if(x>view.screen_width/2+view.cardWidth&& y>view.screen_height-view.cardHeight*5/2&&
					x<view.screen_width/2+3*view.cardWidth&&y<view.screen_height-view.cardHeight*11/6)
			{
				//不抢
				if(view.buttonText[1].equals("不抢"))
				{
					view.dizhuFlag=Common.getBestDizhuFlag();
					//view.dizhuFlag=0;
					Common.dizhuFlag=view.dizhuFlag;
					for(Card card:view.dizhuList)
					{
						card.rear=false;//翻开
					}
					view.update();
					view.Sleep(3000);
					for(Card card:view.dizhuList)
					{
						card.rear=true;//关上
					}
					view.playerList[view.dizhuFlag].addAll(view.dizhuList);
					view.dizhuList.clear();
					Common.setOrder(view.playerList[view.dizhuFlag]);
					Common.rePosition(view, view.playerList[view.dizhuFlag], view.dizhuFlag);
					view.update();
					view.turn=view.dizhuFlag;
					view.hideButton=true;
				}
				//不出
				if(view.buttonText[1].equals("不要")){
					if(view.outList[0].size()==0&&view.outList[2].size()==0)
					{
						Log.i("mylog", "不能不不要");
						return;
					}
					Log.i("mylog", "不要");
					view.message[1]="不要";
					view.hideButton=true;
					view.nextTurn();
					view.flag[1]=0;
					view.update();
				}
				
			}
		}
	}
	/**
	 * 获取点击的是哪张牌
	 * @return 返回牌
	 */
	public Card getCard() {
		Card card = null;
		float x = event.getX();// 触摸x坐标
		float y = event.getY();// 触摸y坐标
		float xoffset = view.cardWidth * 4 / 5f;
		float yoffset = view.cardHeight;
		if (y < view.screen_height - 4 * view.cardHeight / 3)
			return null;
		// 拿到玩家的牌，遍历集合中的每一张牌
		for (Card card2 : view.playerList[1]) {
			// 牌被点击状态
			if (card2.clicked) {
				// 查询符合范围的
				if ((x - card2.x > 0) && (y - card2.y > 0) && 
						(((x - card2.x < xoffset) && (y - card2.y < yoffset)) || ((x - card2.x < card2.width) && (y - card2.y < card2.height / 3)))) {
					return card2;
				}
			} else { // 牌没有被点击的状态
				// 查询符合范围的
				if ((x - card2.x > 0) && (x - card2.x < xoffset) && (y - card2.y > 0) && (y - card2.y < yoffset)) {
					return card2;
				}
			}
		}

		return card;
	}
}
