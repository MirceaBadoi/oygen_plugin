package com.oxygenxml.translate.plugin;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Languages {
	private static LinkedHashMap<String, String> lang = new LinkedHashMap<>();
	
	/**
	 * Supported languages
	 */
	static {
		lang.put("en", "English");
		lang.put("fr", "French");
		lang.put("de", "German");
		lang.put("es", "Spanish");
		lang.put("zh-CN", "Chinese");
		lang.put("ja", "Japanese");
		lang.put("ro", "Romanian");
		lang.put("nl", "Dutch");
		lang.put("hu", "Hungarian");
		lang.put("da", "Danish");
		
	}
	
	/**
	 * Get the corresponding name for a given id 
	 * @param id
	 * @return String
	 */
	public static String getLanguageName(String id) {
		
		return lang.get(id);

	}
	
	/**
	 * Returns the id for every supported language
	 * @return String[]
	 */
	public static String[] getLanguges() {
		return new ArrayList<String>(lang.keySet()).toArray(new String[0]);
	}

}
