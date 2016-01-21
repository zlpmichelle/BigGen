package com.general.datagen.column;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;


public class PropFileGenerator extends ColumnGenerator {
	String fileName = "";
	String split = ",";
	BufferedReader br = null;
	LinkedHashMap<String, Integer> proMap = null;
	Random rand = new Random();
	
	@Override
	public String generate(Object context) {
		// TODO Auto-generated method stub
		String val = "";
		int hash = 0;
		try {
			hash = (int)(rand.nextDouble()*100) % 100;
			LinkedHashMap<String, Integer> curProMap = getInstance();
			Iterator<Entry<String, Integer>> iter = curProMap.entrySet().iterator();
			while (iter.hasNext()) { 
			    Map.Entry entry = (Map.Entry) iter.next();
			    String key = (String)entry.getKey();
			    Integer value = (Integer)entry.getValue();
			    if(hash < value) {
			    	val = key;
			    	break;
			    }
			} 
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return val;
	}
	
	private LinkedHashMap<String, Integer> getInstance(){
		try {
			if(proMap == null) {
				if (arguments.length == 1) {
					fileName = arguments[0];
				}
				proMap = new LinkedHashMap<String, Integer>();
				
				br = new BufferedReader(new FileReader(fileName));
				String line = null;
				boolean isFirst = true;
				int currPro = 0;
				while ((line = br.readLine()) != null) {
					line = line.trim();
					if (line.startsWith("#")) {
						continue;
					}
					if(isFirst) {
						isFirst=false;
						String[] splitToken = line.split("=");
						if(splitToken.length==2) {
							split = splitToken[1];
							break;
						}
					}
					String[] token = line.split(split);
					currPro += (int)(Double.valueOf(token[1])*100);
					proMap.put(token[0], currPro);
				}
				br.close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();	
		}
		return proMap;
	}

}
