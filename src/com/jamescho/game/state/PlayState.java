package com.jamescho.game.state;

import java.awt.Color;//颜色管理器
import java.awt.Font;//创建字体
import java.awt.Graphics;//所有图形上下文的抽象基类
import java.awt.event.KeyEvent;//键盘事件
import java.awt.event.MouseEvent;//鼠标事件
import java.util.ArrayList;//数组列表(数据集合)

import com.jamescho.game.main.GameMain;
import com.jamescho.game.main.Resources;
import com.jamescho.game.model.Block;
import com.jamescho.game.model.Cloud;
import com.jamescho.game.model.Player;

public class PlayState extends State {//一个状态由Game调度
	private Player player;//游戏中模拟玩家
	private ArrayList<Block> blocks;//
	private Cloud cloud, cloud2;//

	private Font scoreFont;//表示字体
	private int playerScore = 0;//玩家分数

	private static final int BLOCK_HEIGHT = 50;//block高度
	private static final int BLOCK_WIDTH = 20;//block宽度
	private int blockSpeed = -200;//block速度

	private static final int PLAYER_WIDTH = 66;//玩家宽度
	private static final int PLAYER_HEIGHT = 92;//玩家高度

	@Override
	public void init() {
		player = new Player(160, GameMain.GAME_HEIGHT - 45 - PLAYER_HEIGHT,
				PLAYER_WIDTH, PLAYER_HEIGHT);//创建玩家的初始位置
		blocks = new ArrayList<Block>();//创建数组列表的block
		cloud = new Cloud(100, 100);//创建两片云彩
		cloud2 = new Cloud(500, 50);
		scoreFont = new Font("SansSerif", Font.BOLD, 25);//创建字体
		for (int i = 0; i < 5; i++) {
			Block b = new Block(i * 200, GameMain.GAME_HEIGHT - 95,
					BLOCK_WIDTH, BLOCK_HEIGHT);
			blocks.add(b);
		}//创建五个block
	}

	@Override
	public void update(float delta) {
		if (!player.isAlive()) {//
			setCurrentState(new GameOverState(playerScore / 100));
		}
		playerScore += 1;
		if (playerScore % 500 == 0 && blockSpeed > -280) {
			blockSpeed -= 10;
		}
		cloud.update(delta);
		cloud2.update(delta);
		Resources.runAnim.update(delta);
		player.update(delta);
		updateBlocks(delta); // 
	}

	private void updateBlocks(float delta) {
		for (Block b : blocks) {
			b.update(delta, blockSpeed);
			if (b.isVisible()) {
				if (player.isDucked()
						&& b.getRect().intersects(player.getDuckRect())) {
					b.onCollide(player);
				} else if (!player.isDucked()
						&& b.getRect().intersects(player.getRect())) {
					b.onCollide(player);
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Resources.skyBlue);
		g.fillRect(0, 0, GameMain.GAME_WIDTH, GameMain.GAME_HEIGHT);
		renderPlayer(g);
		renderBlocks(g);
		renderSun(g);
		renderClouds(g);
		g.drawImage(Resources.grass, 0, 405, null);
		renderScore(g);
	}

	private void renderScore(Graphics g) {//渲染分数
		g.setFont(scoreFont);
		g.setColor(Color.GRAY);
		g.drawString("" + playerScore / 100, 20, 30);
	}

	private void renderPlayer(Graphics g) {//加载人物
		if (player.isGrounded()) {
			if (player.isDucked()) {
				g.drawImage(Resources.duck, (int) player.getX(),
						(int) player.getY(), null);
			} else {
				Resources.runAnim.render(g, (int) player.getX(),
						(int) player.getY(), player.getWidth(),
						player.getHeight());
			}
		} else {
			g.drawImage(Resources.jump, (int) player.getX(),
					(int) player.getY(), player.getWidth(), player.getHeight(),
					null);
		}
	}

	private void renderBlocks(Graphics g) {//加载障碍物
		for (Block b : blocks) {
			if (b.isVisible()) {
				g.drawImage(Resources.block, (int) b.getX(), (int) b.getY(),
						BLOCK_WIDTH, BLOCK_HEIGHT, null);
			}
		}
	}

	private void renderSun(Graphics g) {//加载跑的动作
		g.setColor(Color.orange);
		g.fillOval(715, -85, 170, 170);
		g.setColor(Color.yellow);
		g.fillOval(725, -75, 150, 150);
	}

	private void renderClouds(Graphics g) {//画云
		g.drawImage(Resources.cloud1, (int) cloud.getX(), (int) cloud.getY(),
				100, 60, null);
		g.drawImage(Resources.cloud2, (int) cloud2.getX(), (int) cloud2.getY(),
				100, 60, null);
	}

	@Override
	public void onClick(MouseEvent e) {
	}

	@Override
	public void onKeyPress(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.jump();
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.duck();
		}
	}

	@Override
	public void onKeyRelease(KeyEvent e) {
	}
}