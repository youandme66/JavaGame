package com.jamescho.game.state;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.jamescho.game.main.GameMain;

public abstract class State {//状态的超类

	public abstract void init();//初始化

	public abstract void update(float delta);//更新数据

	public abstract void render(Graphics g);//加载画面

	public abstract void onClick(MouseEvent e);//点击

	public abstract void onKeyPress(KeyEvent e);//键盘按下

	public abstract void onKeyRelease(KeyEvent e);//键盘释放

	public void setCurrentState(State newState) {//设置当前状态
		GameMain.sGame.setCurrentState(newState);
	}
}
