package com.general.datagen.column;

import java.util.HashMap;

public class CorConcatGenerator extends ColumnGenerator {

	@Override
	public String generate(Object context) {
		// TODO Auto-generated method stub
		String val = "";
		int min = 0;
		int length = 0;
		StringBuffer sb = new StringBuffer();
		try {
			String curr = null;
			for(String col:arguments) {
				curr = ((HashMap<String, String>) context).get(col);
				
				if(curr!=null)
					sb.append(curr);
				else
					sb.append(col);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		finally {
			val = sb.toString();
		}
		return val;
	}

}
