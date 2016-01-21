package com.general.datagen.column;

import java.util.Random;

public class GaussianLongGenerator extends ColumnGenerator {
	Random r = new java.util.Random();
	@Override
	public String generate(Object context) {
		// TODO Auto-generated method stub
		long min = 0;
		long max = 1;
		long index = 0;
		String val = "";
		try {
			if (arguments.length == 1) {
				max = Long.valueOf(arguments[0]);
			}
			if (arguments.length == 2) {
				min = Long.valueOf(arguments[0]);
				max = Long.valueOf(arguments[1]);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			
			double gauVal = r.nextGaussian() / 3;
			 long mean = (min+max) / 2;
			 long drift = (long)(mean * gauVal);			 
			 if (mean + drift < min) {
				 index = min;
			 }
			 else if (mean + drift >= max) {
				 index = max;
			 }
			 else {
				 index = mean + drift;
			 }
			
			val = String.valueOf(index);
		}
		return val;
	}

}
