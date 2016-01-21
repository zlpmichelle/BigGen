package com.general.datagen.column;

import java.util.Random;

public class RandomGaussianGenerator extends ColumnGenerator {

	@Override
	public String generate(Object context) {
		// TODO Auto-generated method stub
		int min = 0;
		int max = 1;
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
			
		}
		finally {
			Random r = new java.util.Random();
			double gauVal = (r.nextGaussian()+1)/2.0;
			val = String.valueOf((gauVal * (max - min) + min));
		}
		return val;
	}

}
