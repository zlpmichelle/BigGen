package com.general.datagen;

import java.util.HashMap;

public class Gen {
	
	private static HashMap<String, String> argsMap = new HashMap<String, String>();
	public static int RecordNumber = 0;
	
	
	private static void help() {
		System.err.println("<RecordNumber=Num> [WorkDir=Path] [Verb=true]");
		System.exit(255);
	}
	
	public static void main(String[] args) {
		if (args.length < 1) {
			help();
		}
		for (String arg : args) {
			String[] tokens = arg.split("=");
			if (tokens.length != 2) {
				continue;
			}
			argsMap.put(tokens[0], tokens[1]);
		}
		try {
			RecordNumber = Integer.valueOf(argsMap.get("RecordNumber"));
		}
		catch(Exception e) {
			e.printStackTrace();
			help();
		}
		if (RecordNumber <= 0) {
			help();
		}
		GenFiles.newInstance(RecordNumber, argsMap).genFiles();
	}
}
