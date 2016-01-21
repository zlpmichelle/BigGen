package com.general.datagen.column;

public class FixValueColumnGenerator extends ColumnGenerator {
	public String generate(Object context) {
		String val = "";
		try {
			val = arguments[0];
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return val;
	}
}
