package com.general.datagen.column;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class RandomFileGenerator extends ColumnGenerator {
	String fileName = "";
	BufferedReader br = null;
	ArrayList<String> list = null;
	
	Random rand = new Random();
	@Override
	public String generate(Object context) {
		String val = "";
		try {
			ArrayList<String> curList = getInstance();
			val = curList.get((int)(rand.nextDouble() * curList.size()));
		}
		catch(Exception e) {
			e.printStackTrace();	
		}
		return val;
	}
	
	private ArrayList<String> getInstance(){
		try {
			if (list == null) {
				if (arguments.length == 1) {
					fileName = arguments[0];
				}
				list = new ArrayList<String>();
				br = new BufferedReader(new FileReader(fileName));
				String line = null;
				while ((line = br.readLine()) != null) {
					line = line.trim();
					if (line.startsWith("#")) {
						continue;
					}
					else {
						list.add(line);
					}
				}
				br.close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
