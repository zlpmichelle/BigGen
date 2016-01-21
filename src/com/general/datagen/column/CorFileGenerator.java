package com.general.datagen.column;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class CorFileGenerator extends ColumnGenerator {

	@Override
	public String generate(Object context) {
		// TODO Auto-generated method stub
		String col = "";
		String fileName = "";
		String ranVal = "";
		BufferedReader br = null;
		ArrayList<String> list = new ArrayList<String>();
		int hash = 0;
		try {
			if (arguments.length == 2) {
				fileName = arguments[0];
				col = arguments[1];
			}
			String depend = String.valueOf(((HashMap<String, String>) context).get(col));
			
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
			hash = depend.hashCode()%list.size();			
		}
		catch(Exception e) {
			
		}
		
		finally {
			ranVal = list.get(hash);
		}
		return ranVal;
	}

}
