package com.general.datagen;

import java.io.BufferedOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.general.datagen.column.ColumnGenerator;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.compress.CompressionOutputStream;

public class GenFiles {
	private static GenFiles instance = null;
	private Configure conf = null;
	private static Byte lock = new Byte((byte)0);
	
	public GenFiles(int recordNumber, HashMap<String, String> argsMap) {
		conf = Configure.newInstance(recordNumber, argsMap);
	}
	
	public static GenFiles newInstance(int recordNumber, 
			HashMap<String, String> argsMap) {
		synchronized (lock) {
			if (instance == null) {
				instance = new GenFiles(recordNumber, argsMap);
			}
			return instance;
		}
	}
	
	public static GenFiles getInstance() {
		return instance;
	}
	
	private void initGenerators() {
		for (String col : conf.colNames) {
			ColumnGenerator cg = conf.colGenClass.get(col);
			cg.init(conf.colGenArgs.get(col));
		}
	}
	
	private String genLine() {
		StringBuffer sb = new StringBuffer();
		HashMap<String, String> context = new HashMap<String, String>();
		boolean isFirst = true;
		for (String col : conf.colNames) {
//			long start = System.currentTimeMillis();
			ColumnGenerator cg = conf.colGenClass.get(col);
			String className = cg.getClass().getName();
			String val = cg.generate(context);
			context.put(col, val);
			if (!isFirst) {
				sb.append(conf.split);
			}
			else {
				isFirst = false;
			}
			sb.append(val);
//			if (null == performance.get(className)) {
//				performance.put(className, System.currentTimeMillis() - start);
//			}
//			else {
//				performance.put(className, System.currentTimeMillis() - start + performance.get(className));
//			}
			
		}
		sb.append("\n");
		return sb.toString();
	}
	
	
	
	private int genFile(String filename, int currRecord) {
		Map<String, Long> performance = new HashMap<String, Long>();	
		int size = 0;
		int count = 0;
		boolean useZip = !conf.compressCodec.isEmpty();
		
		try {
			
			long start = System.currentTimeMillis();
			FileSystem fs = FileSystem.get(conf.hadoopConf);
			FSDataOutputStream fdos = fs.create(
					new Path(conf.outputPath + "/" + filename));
			initGenerators();
			BufferedOutputStream bufStream = new BufferedOutputStream(fdos, 32000000);
			CompressionOutputStream cos = null;
			if (useZip) {
				cos = conf.codec.createOutputStream(bufStream);
			}
			while (true) {
		//		long getLineStart = System.currentTimeMillis();
				String line = genLine();
//				if (null == performance.get("getline")) {
//					performance.put("getline", System.currentTimeMillis() - getLineStart);
//				}
//				else {
//					performance.put("getline", System.currentTimeMillis() - getLineStart + performance.get("getline"));
//				}
//				getLineStart = System.currentTimeMillis();
				byte[] bytes = line.getBytes("utf-8");
				if (useZip) {
					cos.write(bytes);
				}
				else {
					bufStream.write(bytes);
				}
				size += bytes.length;
				count++;
				if (count % 1000000 == 0) {
					System.out.println("record count: " + count + ", elapsed: " + (System.currentTimeMillis() - start));
					
				}
//				if (null == performance.get("writeBuffer")) {
//					performance.put("writeBuffer", System.currentTimeMillis() - getLineStart);
//				}
//				else {
//					performance.put("writeBuffer", System.currentTimeMillis() - getLineStart + performance.get("writeBuffer"));
//				}
				if (size >= conf.fileSize
						|| currRecord + count >= conf.recordNumber) {
					break;
				}
			}
			if (useZip) {
				cos.close();
			}
			else {
				bufStream.close();
			}
			if (null == performance.get("total")) {
				performance.put("total", System.currentTimeMillis() - start);
			}
			else {
				performance.put("total", System.currentTimeMillis() - start + performance.get("total"));
			}
		} catch (Exception e) {
			if (conf.verb) {
				e.printStackTrace();
			}
		}
		finally {
			for (String key : performance.keySet()) {
				System.out.println("performance " + "key: " + key + ": " + performance.get(key));
			}
		}
		return count;
	}
	
	public void genFiles() {
		int count = 0;
		int currRecord = 0;
		Random rand = new Random();
		int id = rand.nextInt();
		try {
			FileSystem fs = FileSystem.get(conf.hadoopConf);
			Path op = new Path(conf.outputPath);
			if (!fs.getFileStatus(op).isDir()) {
				fs.mkdirs(op);
			}
			if (conf.overwrite) {
				FileStatus[] fss = fs.listStatus(op);
				for (FileStatus fsi : fss) {
					if (!fsi.isDir()) {
						fs.delete(fsi.getPath(), true);
					}
				}
			}
		} catch (Exception e) {
			if (conf.verb) {
				e.printStackTrace();
			}
		}
		while (true) {
			String filename = "part-" + conf.hostname + "-" + id + "-" + count++;
			if (!conf.compressCodec.isEmpty()) {
				filename = filename + conf.codec.getDefaultExtension();
			}
			if (conf.verb) {
				System.out.println("Gen File \"" + filename + "\" start.");
			}
			currRecord += genFile(filename, currRecord);
			if (conf.verb) {
				System.out.println("Total process : " + currRecord);
			}
			if (currRecord >= conf.recordNumber) {
				break;
			}
		}
	}
}
