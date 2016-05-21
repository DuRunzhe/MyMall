package com.product.dbutil.product.util;

import java.util.UUID;

public class UUIDTools {

	public UUIDTools() {
		// TODO Auto-generated constructor stub
	}
	public static String getUUID(){
		UUID uuid=UUID.randomUUID();
		String result=uuid.toString().replaceAll("-", "").substring(0,6);
		long l=Long.parseLong(result,16);
		
		return l+"";
		
	}

}
