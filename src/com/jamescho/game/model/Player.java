package com.jamescho.game.model;

import java.awt.Rectangle;//通过坐标空间中 Rectangle 对象左上方的点 (x,y)、宽度和高度可以定义这个区域。

import com.jamescho.game.main.Resources;

public class Player {//玩家由状态调度
	private float x, y;//玩家坐标
	private int width, height, velY;//玩家宽度，高度，Y轴上的下落速率
	private Rectangle rect, duckRect, ground;//通过坐标空间中 Rectangle 对象左上方的点 (x,y)、宽度和高度可以定义这个区域。

	private boolean isAlive;//是否活着
	private boolean isDucked;//是否躲避
	private float duckDuration = .6f;//躲避持续时间

	private static final int JUMP_VELOCITY = -600;//跳的速率
	private static final int ACCEL_GRAVITY = 1800;//万有引力加速

	public Player(float x, float y, int width, int height) {//设置人物属性
		this.x = x;//设置x，y坐标
		this.y = y;
		this.width = width;//设置宽高
		this.height = height;

		ground = new Rectangle(0, 405, 800, 45);//地区域
		rect = new Rectangle();//人物区域
		duckRect = new Rectangle();//定义三个区域
		isAlive = true;
		isDucked = false;
		updateRects();//更新区域
	}

	public void update(float delta) {
		if (duckDuration > 0 && isDucked) {//躲避时间大于0且躲避，躲避持续时间减某个值
			duckDuration -= delta;
		} else {//否则改为未躲避
			isDucked = false;
			duckDuration = .6f;
		}
		if (!isGrounded()) {
			velY += ACCEL_GRAVITY * delta;//增加速率
		} else {
			y = 406 - height;//人物的y坐标
			velY = 0;//y轴上的速率为0
		}
		y += velY * delta;//人物不断下落
		updateRects();//更新区域
	}

	public void updateRects() {
		rect.setBounds((int) x + 10, (int) y, width - 20, height);
		duckRect.setBounds((int) x, (int) y + 20, width, height - 20);
	}

	public void jump() {//跳
		if (isGrounded()) {
			Resources.onjump.play();
			isDucked = false;
			duckDuration = .6f;
			y -= 10;
			velY = JUMP_VELOCITY;
			updateRects();
		}
	}

	public void duck() {
		if (isGrounded()) {
			isDucked = true;
		}
	}

	public void pushBack(int dX) {
		Resources.hit.play();
		x -= dX;
		if (x < -width / 2) {
			isAlive = false;
		}
		rect.setBounds((int) x, (int) y, width, height);
	}

	public boolean isGrounded() {
		return rect.intersects(ground);//确定此 rect 是否与ground 相交。
	}

	public boolean isDucked() {
		return isDucked;//是否躲避
	}

	public float getX() {//获取x,y坐标,宽度，高度
		return x;
	}

	public float getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getVelY() {
		return velY;
	}

	public Rectangle getRect() {//获取区域
		return rect;
	}

	public Rectangle getDuckRect() {
		return duckRect;
	}

	public Rectangle getGround() {
		return ground;
	}

	public boolean isAlive() {//获取是否是活着
		return isAlive;
	}

	public float getDuckDuration() {//获取躲避持续时间
		return duckDuration;
	}
}