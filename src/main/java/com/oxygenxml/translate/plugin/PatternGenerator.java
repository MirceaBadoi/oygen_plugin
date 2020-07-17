package com.oxygenxml.translate.plugin;

import java.util.Random;

public class PatternGenerator {
	
	private PatternGenerator() {};

	private static Random randomInteger = new Random();
	public static String randomPatternDelimiter(Integer lowerLimit, Integer upperLimit) {
		int resultRandomInteger = randomInteger.nextInt(upperLimit - lowerLimit) + lowerLimit;
		String result = " _" + Integer.valueOf(resultRandomInteger) + "_\n";
		return result;
	}
}
