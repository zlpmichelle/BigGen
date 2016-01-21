package com.general.datagen.column;

import java.util.HashMap;

public class CorIdentityGenerator extends ColumnGenerator {
	String col = "";
	

	public void init(String[] args) {
		super.init(args);
		try {
			if (arguments.length == 1) {
				col = arguments[0];
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String generate(Object context) {
		// TODO Auto-generated method stub

			String result = ((HashMap<String, String>) context).get(col);
			if (null != result) {
				return result;
			}
			return "";
	}

}
