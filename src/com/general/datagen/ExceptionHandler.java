package com.general.datagen;

public class ExceptionHandler {
	public static void handle(Exception e) {
		e.printStackTrace();
		System.exit(1);
	}
	
	public static void handle(String errMsg) {
		System.err.println(errMsg);
		System.exit(1);
	}
}
