package com.jamescho.game.model;

import java.awt.Rectangle;//ͨ������ռ��� Rectangle �������Ϸ��ĵ� (x,y)����Ⱥ͸߶ȿ��Զ����������

import com.jamescho.game.main.Resources;

public class Player {//�����״̬����
	private float x, y;//�������
	private int width, height, velY;//��ҿ�ȣ��߶ȣ�Y���ϵ���������
	private Rectangle rect, duckRect, ground;//ͨ������ռ��� Rectangle �������Ϸ��ĵ� (x,y)����Ⱥ͸߶ȿ��Զ����������

	private boolean isAlive;//�Ƿ����
	private boolean isDucked;//�Ƿ���
	private float duckDuration = .6f;//��ܳ���ʱ��

	private static final int JUMP_VELOCITY = -600;//��������
	private static final int ACCEL_GRAVITY = 1800;//������������

	public Player(float x, float y, int width, int height) {//������������
		this.x = x;//����x��y����
		this.y = y;
		this.width = width;//���ÿ��
		this.height = height;

		ground = new Rectangle(0, 405, 800, 45);//������
		rect = new Rectangle();//��������
		duckRect = new Rectangle();//������������
		isAlive = true;
		isDucked = false;
		updateRects();//��������
	}

	public void update(float delta) {
		if (duckDuration > 0 && isDucked) {//���ʱ�����0�Ҷ�ܣ���ܳ���ʱ���ĳ��ֵ
			duckDuration -= delta;
		} else {//�����Ϊδ���
			isDucked = false;
			duckDuration = .6f;
		}
		if (!isGrounded()) {
			velY += ACCEL_GRAVITY * delta;//��������
		} else {
			y = 406 - height;//�����y����
			velY = 0;//y���ϵ�����Ϊ0
		}
		y += velY * delta;//���ﲻ������
		updateRects();//��������
	}

	public void updateRects() {
		rect.setBounds((int) x + 10, (int) y, width - 20, height);
		duckRect.setBounds((int) x, (int) y + 20, width, height - 20);
	}

	public void jump() {//��
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
		return rect.intersects(ground);//ȷ���� rect �Ƿ���ground �ཻ��
	}

	public boolean isDucked() {
		return isDucked;//�Ƿ���
	}

	public float getX() {//��ȡx,y����,��ȣ��߶�
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

	public Rectangle getRect() {//��ȡ����
		return rect;
	}

	public Rectangle getDuckRect() {
		return duckRect;
	}

	public Rectangle getGround() {
		return ground;
	}

	public boolean isAlive() {//��ȡ�Ƿ��ǻ���
		return isAlive;
	}

	public float getDuckDuration() {//��ȡ��ܳ���ʱ��
		return duckDuration;
	}
}