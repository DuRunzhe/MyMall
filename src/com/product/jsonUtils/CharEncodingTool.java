package com.product.jsonUtils;

import java.io.UnsupportedEncodingException;

public class CharEncodingTool {

	public CharEncodingTool() {
		// TODO Auto-generated constructor stub
	}
	public static String changeEncode(String string,String fromCodesetName,String toCodeSetName){
		try {
			string=new String(string.getBytes(fromCodesetName),toCodeSetName);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return string;
		
	}
	public static String changeEncode(String string,String charsetName){
		try {
			string=new String(string.getBytes(),charsetName);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return string;
		
	}
	public static String changeEncode(String string){
		try {
			string=new String(string.getBytes(),"ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return string;
	}

}
