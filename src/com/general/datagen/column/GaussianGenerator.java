package com.general.datagen.column;

import java.util.Random;

public class GaussianGenerator extends ColumnGenerator {
	Random r = new java.util.Random();
	@Override
	public String generate(Object context) {
		// TODO Auto-generated method stub
		int min = 0;
		int max = 1;
		int index = 0;
		String val = "";
		try {
			if (arguments.length == 1) {
				max = Integer.valueOf(arguments[0]);
			}
			if (arguments.length == 2) {
				min = Integer.valueOf(arguments[0]);
				max = Integer.valueOf(arguments[1]);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			
			double gauVal = r.nextGaussian();
			 int mean = (min+max) / 2;
			 int drift = (int)(mean * gauVal);			 
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
