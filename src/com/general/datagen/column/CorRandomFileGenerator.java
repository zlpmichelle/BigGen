package com.general.datagen.column;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class CorRandomFileGenerator extends ColumnGenerator {
	String fileName = "";
	BufferedReader br = null;
	ArrayList<String> list = null;
	
	@Override
	public String generate(Object context) {
		// TODO Auto-generated method stub
		String val = "";
		String col = "";
		int hash = 0;
		try {
			if (arguments.length == 2) {
				col = arguments[0];
			}
			String depend = ((HashMap<String, String>) context).get(col);
			
			ArrayList<String> curList = getInstance();
			
			hash = Math.abs(depend.hashCode()) % curList.size();
			//System.out.println(hash);
			val = curList.get(hash);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return val;
	}
	
	private ArrayList<String> getInstance(){
		try {
			if (list == null) {
				if (arguments.length == 2) {
					fileName = arguments[1];
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
