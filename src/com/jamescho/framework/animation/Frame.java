package com.jamescho.framework.animation;

import java.awt.Image;//图形图像的所有类的超类

public class Frame {//标准的数据层model
	private Image image;//图形图像
	private double duration;//持续的时间

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