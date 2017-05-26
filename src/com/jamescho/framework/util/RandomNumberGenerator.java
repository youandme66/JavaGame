package com.jamescho.framework.util;

import java.util.Random;//随机数生成

public class RandomNumberGenerator {

	private static Random rand = new Random();

	public static int getRandIntBetween(int lowerBound, int upperBound) {//根据上下限得到相应的随机函数
		return rand.nextInt(upperBound - lowerBound) + lowerBound;//生成之间的数
	}

	public static int getRandInt(int upperBound) {
		return rand.nextInt(upperBound);//生成0-上限的数
	}
}