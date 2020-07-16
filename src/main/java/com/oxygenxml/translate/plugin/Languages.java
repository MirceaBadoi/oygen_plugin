package com.oxygenxml.translate.plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ro.sync.exml.workspace.api.options.WSOptionsStorage;

public class Languages {
	public static final String ID_LANG = "com.oxygenxml.key.language.priority";
	public static final HashMap<String, String> LANGUAGES = new LinkedHashMap<>();
	
	private Languages() {
		//empty to avoid instantiation
	};

	/**
	 * Supported languages
	 */
	static {
		LANGUAGES.put("en", "English");
		LANGUAGES.put("fr", "French");
		LANGUAGES.put("de", "German");
		LANGUAGES.put("es", "Spanish");
		LANGUAGES.put("zh-CN", "Chinese");
		LANGUAGES.put("ja", "Japanese");
		LANGUAGES.put("ro", "Romanian");
		LANGUAGES.put("nl", "Dutch");
		LANGUAGES.put("hu", "Hungarian");
		LANGUAGES.put("da", "Danish");

	}

	/**
	 * Get the corresponding name for a given id
	 * 
	 * @param id
	 * @return String
	 */
	public static String getLanguageName(String id) {
		return LANGUAGES.get(id);
	}

	/**
	 * Returns the id for every supported language
	 * @param store
	 * @param languages
	 * @return
	 */
	public static String[] getLanguagesISOCodes(WSOptionsStorage store, Set<String> languages) {
		String[] langCodes = store.getStringArrayOption(ID_LANG, null);
		List<String> langs = new ArrayList<String>(languages);
		Map<String, Integer> priorities = new HashMap<String, Integer>();
		if(langCodes != null) {
			for (int i = 0; i < langCodes.length; i++) {
				priorities.put(langCodes[i], i);
			}
			
			Collections.sort(langs, new Comparator<String>() {
	
				@Override
				public int compare(String o1, String o2) {
					Integer p1 = priorities.get(o1);
					Integer p2 = priorities.get(o2);
	
					if (p1 != null && p2 != null) {
						int result = p1 - p2;
						return result >= 1 ? 1 : (result < 0 ? -1 : 0);
					} else if (p1 != null) {
						return -1;
					} else if (p2 != null) {
						return 1;
					} else {
						return 0;
					}
				}
	
			});
		}

		return langs.toArray(new String[0]);
	}
	/**
	 * Increases priority for the most used languages
	 * @param store
	 * @param id Language ISO
	 */
	public static void increasePriorityLanguage(WSOptionsStorage store, String id) {
		String[] langCodes = store.getStringArrayOption(ID_LANG, null);
		List<String> codes = new ArrayList<String>();
		if (langCodes != null) {
			codes.addAll(Arrays.asList(langCodes));
		}
		
		codes.remove(id);
		codes.add(0, id);
		store.setStringArrayOption(ID_LANG, codes.toArray(new String[0]));
	}
	

}
