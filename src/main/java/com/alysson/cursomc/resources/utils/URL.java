package com.alysson.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {
	
	public static List<Integer> decodeIntList(String s){		
		List<Integer> list = new ArrayList<>();
		
		String[] vet = s.split(",");
		
		for(String x : vet) 
			list.add(Integer.parseInt(x));
		
		return list;
	}
	
	public static String decodeParam(String s){
		try {
			return URLDecoder.decode(s, "UTF-8");
		} 
		catch (UnsupportedEncodingException e) {
			return "";
		}
	}
}
