package com.jamescho.framework.util;

import java.awt.event.KeyEvent;//�������¼�
import java.awt.event.KeyListener;//���ڽ��ռ����¼������������������ӿڡ�
import java.awt.event.MouseEvent;//��궯�����¼�
import java.awt.event.MouseListener;//���ڽ�������¼������¡��ͷš�������������뿪�����������ӿڡ�

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
		currentState.onKeyPress(e);//���̰��²���
	}

	@Override
	public void keyReleased(KeyEvent e) {
		currentState.onKeyRelease(e);//�����ͷŲ���
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Do Nothing
	}

}
