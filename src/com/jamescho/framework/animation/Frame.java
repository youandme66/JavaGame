package com.jamescho.framework.animation;

import java.awt.Image;//ͼ��ͼ���������ĳ���

public class Frame {//��׼�����ݲ�model
	private Image image;//ͼ��ͼ��
	private double duration;//������ʱ��

	public Frame(Image image, double duration) {
		this.image = image;
		this.duration = duration;
	}

	public double getDuration() {
		return duration;
	}

	public Image getImage() {
		return image;
	}
}