package com.general.datagen.column;

import java.util.HashMap;

public class CorSubstrGenerator extends ColumnGenerator {
	String col = "";
	String val = "";
	String colVal = "";
	int min = 0;
	int baselength = 0;

	
	@Override
	public void init(String[] args) {
	super.init(args);
		if (arguments.length == 1) {
			col = arguments[0];
		}
		if (arguments.length == 2) {
			col = arguments[0];
			min = Integer.valueOf(arguments[1]);
		}
		if (arguments.length == 3) {
			col = arguments[0];
			min = Integer.valueOf(arguments[1]);
			baselength = Integer.valueOf(arguments[2]);
		}
	}
	
	@Override
	public String generate(Object context) {
		int length = baselength;
		
try {
			colVal= ((HashMap<String, String>) context).get(col);
			if(min<0)
				min += colVal.length();
			if(length<0)
				length += colVal.length();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(length != 0)
				val = colVal.substring(min,length);
			else
				val = colVal.substring(min);
		}
		return val;
	}

}
