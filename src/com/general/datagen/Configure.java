package com.general.datagen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.util.ReflectionUtils;

import com.general.datagen.column.ColumnGenerator;

public class Configure {
	private static Configure instance = null;
	public int recordNumber = 0;
	public String workDir = ".";
	public String outputPath = "output";
	public String split = "|";
	public boolean verb = false;
	public boolean overwrite = true;
	public long fileSize = 1 * 1024 * 1024 * 1024;
	public String hostname = "localhost";
	public String compressCodec = "";
	public ArrayList<String> colNames = new ArrayList<String>();
	public HashMap<String, ColumnGenerator> colGenClass = new HashMap<String, ColumnGenerator>();
	public HashMap<String, String[]> colGenArgs = new HashMap<String, String[]>();
	public Configuration hadoopConf = null;
	public CompressionCodec codec = null;
	private static Byte lock = new Byte((byte)0);
	
	public final static String BasePath = "";
	private final static String[] HadoopConfResource = new String[] {
		BasePath + "/etc/hadoop/conf/core-site.xml",
		BasePath + "/etc/hadoop/conf/mapred-site.xml",
		BasePath + "/etc/hadoop/conf/hdfs-site.xml"
	};
	private final static String[] HBaseConfResource = new String[] {
		BasePath + "/etc/hbase/conf/hbase-site.xml"
	};
	
	public HashMap<String, String> confMap = null;
	
	public Configure(int recordNumber, HashMap<String, String> argsMap) {
		this.recordNumber = recordNumber;
		this.workDir = (argsMap.get("WorkDir") == null ? workDir : argsMap.get("WorkDir"));
		this.verb = (argsMap.get("Verb") == null ? 
				verb : argsMap.get("Verb").equalsIgnoreCase("true"));
		HashMap<String, String> confMap = new HashMap<String, String>();
		
		try {
			hadoopConf = new Configuration(true);
			for (String r : HadoopConfResource) {
				File f = new File(r);
				if (!f.exists()) {
					continue;
				}
				try {
					hadoopConf.addResource(f.toURI().toURL());
				}
				catch (MalformedURLException e) {
				}
			}
			for (String r : HBaseConfResource) {
				File f = new File(r);
				if (!f.exists()) {
					continue;
				}
				try {
					hadoopConf.addResource(f.toURI().toURL());
				}
				catch (MalformedURLException e) {
				}
			}
			hadoopConf.reloadConfiguration();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			hostname = InetAddress.getLocalHost().getHostName();
			hostname = hostname.split("\\.")[0];
		}
		catch (Exception e) {
		}
		
		this.confMap = confMap;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(workDir + "/" + "conf.properties"));
			String line = null;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.startsWith("#")) {
					continue;
				}
				String[] tokens = line.split("=");
				if (tokens.length != 2) {
					continue;
				}
				confMap.put(tokens[0], tokens[1]);
			}
			outputPath = (!confMap.containsKey("OutputPath") ? 
					outputPath : confMap.get("OutputPath"));
			fileSize = (!confMap.containsKey("FileSize") ? 
					fileSize : Long.valueOf(confMap.get("FileSize")));
			compressCodec = (!confMap.containsKey("CompressCodec") ? 
					compressCodec : confMap.get("CompressCodec"));
			if (!compressCodec.isEmpty()) {
				codec = (CompressionCodec) ReflectionUtils.newInstance(Class
						.forName(compressCodec), hadoopConf);
			}
			split = (!confMap.containsKey("Split") ? 
					split : confMap.get("Split"));
			overwrite = (!confMap.containsKey("Overwrite") ? 
					overwrite : Boolean.valueOf(confMap.get("Overwrite")));
			
			br.close();
			
			br = new BufferedReader(new FileReader(workDir + "/" + "table.properties"));
			line = null;
			confMap = new HashMap<String, String>();
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.startsWith("#")) {
					continue;
				}
				String[] tokens = line.split("=");
				if (tokens.length != 2) {
					continue;
				}
				confMap.put(tokens[0], tokens[1]);
			}
			br.close();
			String split = ",";
			if (!confMap.containsKey("Columns")) {
				ExceptionHandler.handle("Can't find properties \"Columns\"");
			}
			if (confMap.containsKey("Split")) {
				split = confMap.get("Split");
			}
			String[] columns = confMap.get("Columns").trim().split(",");
			for (String column : columns) {
				this.colNames.add(column);
				if (!confMap.containsKey(column)) {
					ExceptionHandler.handle("Can't find properties for column \"" 
							+ column + 
							"\"");
				}
				String[] vals = confMap.get(column).trim().split(split);
				if (vals.length < 1) {
					ExceptionHandler.handle("Illegal arguments for column \"" 
							+ column + 
							"\"");
				}
				colGenClass.put(column, 
						(ColumnGenerator)Class.forName(vals[0]).newInstance());
				ArrayList<String> list = new ArrayList<String>();
				if (verb) {
					System.out.print("Read column config : " + column + " "
							+ vals[0] + "    ");
				}
				for (int i = 1; i < vals.length; i++) {
					list.add(vals[i]);
					if (verb) {
						System.out.print(vals[i] + " ");
					}
				}
				System.out.println();
				colGenArgs.put(column, list.toArray(new String[list.size()]));
			}
		}
		catch(Exception e) {
			ExceptionHandler.handle(e);
		}
		finally {
			try {
				if (br != null) {
					br.close();
				}
			}
			catch(Exception e) { 
				
			}
		}
	}
	
	public static Configure newInstance(int recordNumber, 
			HashMap<String, String> argsMap) {
		synchronized(lock) {
			if (instance == null) {
				instance = new Configure(recordNumber, argsMap);
			}
			return instance;
		}
	}
	
	public static Configure getInstance() {
		return instance;
	}
}
