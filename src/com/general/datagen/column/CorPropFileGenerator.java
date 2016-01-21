package com.general.datagen.column;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


public class CorPropFileGenerator extends ColumnGenerator {
	String fileName = "";
	String split = ",";
	BufferedReader br = null;
	LinkedHashMap<String, Integer> proMap = null;
	
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
			
			hash = Math.abs(depend.hashCode()) % 100;
			//System.out.println(hash);
			LinkedHashMap<String, Integer> curProMap = getInstance();
			Iterator<Entry<String, Integer>> iter = curProMap.entrySet().iterator();
			while (iter.hasNext()) { 
			    Map.Entry entry = (Map.Entry) iter.next();
			    String key = (String)entry.getKey();
			    Integer value = (Integer)entry.getValue();
			    if(hash < value) {
			    	//System.out.println("key:"+key);
			    	//System.out.println("value:"+String.valueOf(value));
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
				if (arguments.length == 2) {
					fileName = arguments[1];
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
