package com.general.datagen.column;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class GaussianFileGenerator extends ColumnGenerator {
	String fileName = "";
	BufferedReader br = null;
	ArrayList<String> list = null;
	Random r = new java.util.Random();
	@Override
	public String generate(Object context) {
		try {
			ArrayList<String> curList = getInstance();
			 int size = curList.size();
			 double gauVal = r.nextGaussian() / 3;
			 int drift = (int)(size /2 * gauVal);
			 int mean = size/2;
			 int index = 0;
			 if (mean + drift < 0) {
				 index = 0;
			 }
			 else if (mean + drift >= size) {
				 index = size - 1;
			 }
			 else {
				 index = mean + drift;
			 }
			 
			return curList.get(index);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
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
		catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
