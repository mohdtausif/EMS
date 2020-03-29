package com.asgnmt.ems.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	public static <T> String getJsonStrFromObject(T obj)  throws Exception
	{
		return new ObjectMapper().writeValueAsString(obj);
	}
	
	public static <T> T getObjectFromJsonStr(String jsonStr, Class<T> classType) throws Exception
	{
		return new ObjectMapper().readValue(jsonStr, classType);
	}
}
