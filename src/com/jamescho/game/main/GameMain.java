package com.jamescho.game.main;

import javax.swing.JFrame;//顶层容器

public class GameMain {//创建一个窗口
	public static final String GAME_TITLE = "Ellio (Chapter 6)";
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 450;
	public static Game sGame;

	public static void main(String[] args) {
		JFrame frame = new JFrame(GAME_TITLE);//JFrame 包含一个 JRootPane 作为其唯一的子容器
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置用户在此窗体上发起 "close" 时默认执行的操作。
		frame.setResizable(false); // 阻止用户重置窗口大小
		sGame = new Game(GAME_WIDTH, GAME_HEIGHT);
		frame.add(sGame);
		frame.pack();//调整此窗口的大小，以适合其子组件的首选大小和布局
		frame.setVisible(true);//设置
		frame.setIconImage(Resources.iconimage);
	}

}