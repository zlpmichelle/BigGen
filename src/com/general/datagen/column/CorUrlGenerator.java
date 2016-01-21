package com.general.datagen.column;

import java.util.HashMap;

public class CorUrlGenerator extends ColumnGenerator {
	String model = "";
	String col = "";
	String[] sepIp = null;
	String[] fullIp = null;
	int[] min = new int[4];
	int [] max = new int[4];
	int minPort = 1;
	int maxPort = 65536;
	
	@Override
	public void init(String[]args) {
		super.init(args);
			if(arguments.length==2) {
				col = arguments[0];
				model = arguments[1];
			}
			model = model.trim();
			fullIp = model.split(":");
			sepIp = fullIp[0].split("\\.");
			
			for(int i=0;i<sepIp.length;i++) {
				String[] token = (sepIp[i]).split("\\-");
				min[i] = 1;
				max[i] = 255;
				if(token.length==1 && !token[0].startsWith("*")) {
					min[i] = Integer.valueOf(token[0]);
					max[i] = min[i];
				}
				if(token.length==2) {
					min[i] = Integer.valueOf(token[0]);
					max[i] = Integer.valueOf(token[1]);
				}
			}
			if(fullIp.length==2) {
				minPort = 1;
				maxPort = 65535;
				String[] token = fullIp[1].split("\\-");
				if(token.length==2) {
					minPort = Integer.valueOf(token[0]);
					maxPort = Integer.valueOf(token[1]);
				}
				if(token.length==1 && !token[0].startsWith("*")) {
					minPort = Integer.valueOf(token[0]);
					maxPort = minPort;
				}
			}
	}
	
	
	@Override
	public String generate(Object context) {
		StringBuffer sb = new StringBuffer();
		String colVal = ((HashMap<String, String>) context).get(col);
		int hash = Math.abs(colVal.hashCode());
		for(int i=0;i<sepIp.length;i++) {
			if(max[i]!=min[i])
				sb.append(String.valueOf((int)(hash % (max[i] - min[i]) + min[i])));
			else
				sb.append(String.valueOf(max[i]));
			if(i!=3)
			sb.append(".");
			hash = hash >> 1;
		}
		if(fullIp.length==2) {
			sb.append(":");
			if(maxPort != minPort)
				sb.append((String.valueOf((int)(hash % (maxPort - minPort) + minPort))));
			else
				sb.append(String.valueOf(maxPort));
		}
		
		
		return sb.toString();
	}

}
