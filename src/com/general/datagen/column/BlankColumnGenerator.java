package com.general.datagen.column;

public class BlankColumnGenerator extends ColumnGenerator {
	public final String EMPTY = "";
	public String generate(Object context) {
		return EMPTY;
	}

}
