package com.jamescho.game.state;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.jamescho.game.main.GameMain;

public abstract class State {//״̬�ĳ���

	public abstract void init();//��ʼ��

	public abstract void update(float delta);//��������

	public abstract void render(Graphics g);//���ػ���

	public abstract void onClick(MouseEvent e);//���

	public abstract void onKeyPress(KeyEvent e);//���̰���

	public abstract void onKeyRelease(KeyEvent e);//�����ͷ�

	public void setCurrentState(State newState) {//���õ�ǰ״̬
		GameMain.sGame.setCurrentState(newState);
	}
}
