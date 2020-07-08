package com.oxygenxml.translate.plugin;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Languages {
	private static LinkedHashMap<String, String> lang = new LinkedHashMap<>();
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
	
	
	public static String getLanguageName(String id) {
		
		return lang.get(id);

	}
	
	public static String[] getLanguges() {
		return new ArrayList<String>(lang.keySet()).toArray(new String[0]);
	}

}
