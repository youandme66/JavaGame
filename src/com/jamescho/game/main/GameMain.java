package com.jamescho.game.main;

import javax.swing.JFrame;//��������

public class GameMain {//����һ������
	public static final String GAME_TITLE = "Ellio (Chapter 6)";
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 450;
	public static Game sGame;

	public static void main(String[] args) {
		JFrame frame = new JFrame(GAME_TITLE);//JFrame ����һ�� JRootPane ��Ϊ��Ψһ��������
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�����û��ڴ˴����Ϸ��� "close" ʱĬ��ִ�еĲ�����
		frame.setResizable(false); // ��ֹ�û����ô��ڴ�С
		sGame = new Game(GAME_WIDTH, GAME_HEIGHT);
		frame.add(sGame);
		frame.pack();//�����˴��ڵĴ�С�����ʺ������������ѡ��С�Ͳ���
		frame.setVisible(true);//����
		frame.setIconImage(Resources.iconimage);
	}

}