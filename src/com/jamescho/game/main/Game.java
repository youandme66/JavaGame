package com.jamescho.game.main;

import java.awt.Color;//提供用于颜色空间的类
import java.awt.Dimension;//封装单个对象中组件的宽度和高度
import java.awt.Graphics;//所有图形上下文的抽象基类，允许应用程序在组件（已经在各种设备上实现）以及闭屏图像上进行绘制。
import java.awt.Image;//图形图像的所有类的超类。

import javax.swing.JPanel;//一般轻量级容器

import com.jamescho.framework.util.InputHandler;
import com.jamescho.game.state.LoadState;
import com.jamescho.game.state.State;

@SuppressWarnings("serial")
public class Game extends JPanel implements Runnable {//最厉害的管理器
	private int gameWidth;//游戏宽度
	private int gameHeight;//游戏高度
	private Image gameImage;//图片

	private Thread gameThread;//线程对象
	private volatile boolean running;//是否在运行
	private volatile State currentState;//当前游戏状态

	private InputHandler inputHandler;

	public Game(int gameWidth, int gameHeight) {
		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;
		setPreferredSize(new Dimension(gameWidth, gameHeight));//设置此组件的首选大小
		setBackground(Color.BLACK);//设置此组件的背景色。
		setFocusable(true);//Component 是否可以获得焦点
		requestFocus();//请求此 Component 获取输入焦点，并且此 Component 的顶层祖先成为获得焦点的 Window
	}

	public void setCurrentState(State newState) {
		System.gc();//运行垃圾回收器
		newState.init();
		currentState = newState;
		inputHandler.setCurrentState(currentState);//进入当前状态的事件监听
	}

	@Override
	public void addNotify() {//添加通知
		super.addNotify();
		initInput();
		setCurrentState(new LoadState());
		initGame();
	}

	private void initGame() {//初始化游戏
		running = true;
		gameThread = new Thread(this, "Game Thread");//本对象，线程名"Game Thread"
		gameThread.start();//启动线程
	}

	@Override
	public void run() {
		long updateDurationMillis = 0;//更新持续时间 
		long sleepDurationMillis = 0;//睡眠持续时间
		while (running) {
			long beforeUpdateRender = System.nanoTime();//返回最准确的可用系统计时器的当前值，以毫微秒为单位
			long deltaMillis = updateDurationMillis + sleepDurationMillis;

			updateAndRender(deltaMillis);//执行更新并且加载

			updateDurationMillis = (System.nanoTime() - beforeUpdateRender) / 1000000L;//更新持续秒数
			sleepDurationMillis = Math.max(2, 17 - updateDurationMillis);//取最大的睡眠持续时间

			try {
				Thread.sleep(sleepDurationMillis);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.exit(0);
	}

	private void updateAndRender(long deltaMillis) {//更新并且加载
		currentState.update(deltaMillis / 1000f);
		prepareGameImage();//准备一个初始画板
		currentState.render(gameImage.getGraphics());//加载，传递一个供绘制闭屏图像（off-screen image）使用的图形上下文。
		renderGameImage(getGraphics());//加载游戏画面，getGraphics返回此组件的图形上下文，该上下文允许您绘制组件
	}

	private void prepareGameImage() {
		if (gameImage == null) {
			gameImage = createImage(gameWidth, gameHeight);//创建一幅用于双缓冲的、可在屏幕外绘制的图像。
		}
		Graphics g = gameImage.getGraphics();
		g.clearRect(0, 0, gameWidth, gameHeight);//通过使用当前绘图表面的背景色进行填充来清除指定的矩形。
	}

	public void exit() {
		running = false;
	}

	private void renderGameImage(Graphics g) {//加载游戏图片
		if (gameImage != null) {
			g.drawImage(gameImage, 0, 0, null);//绘制指定图像中当前可用的图像。
		}
		g.dispose();//释放此图形的上下文以及它使用的所有系统资源。
	}

	private void initInput() {
		inputHandler = new InputHandler();
		addKeyListener(inputHandler);//添加指定的按键侦听器，以接收发自此组件的按键事件。
		addMouseListener(inputHandler);//添加指定的鼠标侦听器，以接收发自此组件的鼠标事件。
	}
}