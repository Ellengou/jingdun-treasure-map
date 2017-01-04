
package com.jd.core.utils;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

//TODO : remove redundant encoding method in utils package
public class IDUtils {
	
	private final static String CODE = "9214035678";
	
	public static Long decode(String eid){
		try{
			Long.parseLong(eid);
		}catch(NumberFormatException e){
			return null;
		}
		
		try{
			eid = eid.substring(2, eid.length());
			String result = "";
			for(int i=0;i<eid.length();i++){
				result+=CODE.indexOf(Integer.valueOf(eid.charAt(i)));
			}
			Long v = Long.valueOf(result);
			return (v-183729)/37;
		}catch(Exception e){
			return null;
		}
		
	}

	public static String encode(Long id){
		String eid = String.valueOf(id*37+183729);
		StringBuilder builder = new StringBuilder();
		for(int i=0;i<eid.length();i++){
			builder.append(CODE.charAt(Integer.valueOf(String.valueOf(eid.charAt(i)))));
		}
		return "10"+builder.toString();
	}

	//获取UUID
	public static String getUUIDFromGenerators(){
		TimeBasedGenerator gen = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
		String uuid = gen.generate().toString();
		uuid = uuid.replaceAll("-", "");
		return uuid;
	}
	
/*	public static  void  main(String[] args){
		System.out.println(IDUtils.encode(100L));
		System.out.println(IDUtils.decode("10276018"));
		System.out.println(EncryptionUtils.generateRandomCharAndNumber(505));
	}*/

}
