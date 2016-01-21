package com.general.datagen.column;

public abstract class ColumnGenerator {
	public String[] arguments = null;
	
	public ColumnGenerator() {
		
	}
	
	public void init(String[] args) {
		arguments = args;
	}
	
	public abstract String generate(Object context);
}
