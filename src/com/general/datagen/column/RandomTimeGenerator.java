package com.general.datagen.column;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class RandomTimeGenerator extends ColumnGenerator{
	private SimpleDateFormat df;
	Random rand = new Random();
	long minDate = 0;
	long maxDate = 0;
	
	
	@Override
	public void init(String[] args) {
		super.init(args);
		df = new SimpleDateFormat(arguments[2]);
		
		try {
			if (arguments.length == 3) {
				minDate = df.parse(arguments[0]).getTime();
				maxDate = df.parse(arguments[1]).getTime();
			}
		}
			catch(Exception e) { 
				e.printStackTrace();
			}
			
	}
	
	public String generate(Object context) {
	try {
			if (maxDate == minDate) {
				return df.format(new Date(minDate));
			}
			long random = rand.nextLong();
			return df.format(Math.abs(random%(maxDate - minDate)) + minDate);
			}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
		
}
