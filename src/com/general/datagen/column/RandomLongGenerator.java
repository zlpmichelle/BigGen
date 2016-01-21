package com.general.datagen.column;

import java.util.Random;

public class RandomLongGenerator extends ColumnGenerator{
	Random rand = new Random();

	long min = 0;
	 long max = 10000;
	
	@Override
	public void init(String [] args) {
		super.init(args);
	
		if (arguments.length == 1) {
			max = Long.parseLong(arguments[0]);
		}
		if (arguments.length == 2) {
			min = Long.parseLong(arguments[0]);
			max = Long.parseLong(arguments[1]);
		}
	}
	
	public String generate(Object context) {
		return String.valueOf(Math.abs(rand.nextInt()) % (max - min) + min);
	}
}
