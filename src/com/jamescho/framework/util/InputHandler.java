package com.jamescho.framework.util;

import java.awt.event.KeyEvent;//键击的事件
import java.awt.event.KeyListener;//用于接收键盘事件（击键）的侦听器接口。
import java.awt.event.MouseEvent;//鼠标动作的事件
import java.awt.event.MouseListener;//用于接收鼠标事件（按下、释放、单击、进入或离开）的侦听器接口。

import com.jamescho.game.state.State;

public class InputHandler implements KeyListener, MouseListener {

	private State currentState;

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		currentState.onClick(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Do Nothing
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Do Nothing
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// Do Nothing
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Do Nothing
	}

	@Override
	public void keyPressed(KeyEvent e) {
		currentState.onKeyPress(e);//键盘按下操作
	}

	@Override
	public void keyReleased(KeyEvent e) {
		currentState.onKeyRelease(e);//键盘释放操作
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Do Nothing
	}

}
