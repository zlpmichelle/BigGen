package com.general.datagen.column;

import java.util.HashMap;

public class CorSplitGenerator extends ColumnGenerator {

	int number = 0;
	String split = "";
	String col = null;
	
	public void init(String[]args) {
		super.init(args);
		try {
			if (arguments.length == 2) {
				col = arguments[0];
				split = arguments[1];
			}
			if (arguments.length == 3) {
				col = arguments[0];
				split = arguments[1];
				number = Integer.valueOf(arguments[2]);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public String generate(Object context) {

			String[] strSplit = ((HashMap<String, String>) context).get(col).split(split);
			return  strSplit[number];
	}

}
