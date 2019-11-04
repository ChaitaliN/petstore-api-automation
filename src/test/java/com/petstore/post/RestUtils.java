package com.petstore.post;

import org.apache.commons.lang3.RandomStringUtils;

public class RestUtils {
	
	public static String getId()
	{
		String generatedId=RandomStringUtils.randomNumeric(3);
		return generatedId;
	}
	
	public static String getName()
	{
		String generatedName=RandomStringUtils.randomAlphabetic(4);
		return generatedName;
	}
	
}
