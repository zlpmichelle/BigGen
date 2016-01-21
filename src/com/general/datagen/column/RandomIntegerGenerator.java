package com.general.datagen.column;

import java.util.Random;

public class RandomIntegerGenerator extends ColumnGenerator{
	Random rand = new Random();

	int min = 0;
	int max = 10000;
	
	@Override
	public void init(String [] args) {
	super.init(args);

		if (arguments.length == 1) {
			max = Integer.valueOf(arguments[0]);
		}
		if (arguments.length == 2) {
			min = Integer.valueOf(arguments[0]);
			max = Integer.valueOf(arguments[1]);
		}
		
	
	
}

	@Override
	public String generate(Object context) {
		return String.valueOf(Math.abs(rand.nextInt()) % (max - min) + min);
	
	}
}
