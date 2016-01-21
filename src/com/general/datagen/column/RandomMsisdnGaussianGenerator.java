package com.general.datagen.column;

import java.util.Random;

public class RandomMsisdnGaussianGenerator extends ColumnGenerator {
	Random r = new java.util.Random();
	long min = 0;
	long max = 1;
	
	@Override 
	public void init(String[] args) {
		super.init(args);
		// TODO Auto-generated method stub
				
		try {
			if (arguments.length == 1) {
				max = Long.parseLong(arguments[0]);
			}
			if (arguments.length == 2) {
				min = Long.parseLong(arguments[0]);
				max = Long.parseLong(arguments[1]);
			}
		}
		finally {
			
		}
	}
	
	@Override
	public String generate(Object context) {
		double gauVal = Math.abs(r.nextGaussian() / 3.0 + 0.5);
		String tmpval = String.valueOf((gauVal * (max - min) + min));
		return Md5Util.getMD5Str(tmpval);
	}

}
