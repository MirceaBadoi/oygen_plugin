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
/**
 * Stores the supported languages 
 * @author Badoi Mircea
 *
 */
public class Languages {
	/**
	 * The language storage key
	 * Public for tests.
	 */
	public static final String LANG_STORAGE_KEY = "com.oxygenxml.key.language.priority";
	/**
	 * Maps an ISO language code to a language name
	 */
	public static final HashMap<String, String> LANGUAGES = new LinkedHashMap<>();
	
	/**
	 * Private constructor
	 */
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
	 * @param id Shorthand names like (ro,de,hu,fr,etc)
	 * @return String Full name for the corresponding id
	 */
	public static String getLanguageName(String id) {
		return LANGUAGES.get(id);
	}

	/**
	 * Returns the id for every supported language
	 * @param WSOptionsStorage for persistence
	 * @param Languages Shorthand languages in the correct priority order 
	 * @return
	 */
	public static String[] getPrioritizedLanguageISOCodes(WSOptionsStorage store, Set<String> languageISOCodes) {
		String[] langCodes = store.getStringArrayOption(LANG_STORAGE_KEY, null);//saved only clicked languages
		List<String> langIsoCodes = new ArrayList<String>(languageISOCodes);
		Map<String, Integer> codeToIndexMap = new HashMap<String, Integer>();
		if(langCodes != null) {
			for (int i = 0; i < langCodes.length; i++) {
				codeToIndexMap.put(langCodes[i], i);
			}
			
			//sort the id's by priority
			Collections.sort(langIsoCodes, new Comparator<String>() {
	
				@Override
				public int compare(String o1, String o2) {
					Integer p1 = codeToIndexMap.get(o1);
					Integer p2 = codeToIndexMap.get(o2);
	
					if (p1 != null && p2 != null) {
						return p1 - p2;
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

		return langIsoCodes.toArray(new String[0]);
	}
	
	/**
	 * Increases priority for the most used languages
	 * @param WSOptionsStorage for persistence
	 * @param id Language shorthand
	 */
	public static void increasePriorityLanguage(WSOptionsStorage store, String id) {
		String[] langCodes = store.getStringArrayOption(LANG_STORAGE_KEY, null);
		List<String> codes = new ArrayList<String>();
		if (langCodes != null) {
			codes.addAll(Arrays.asList(langCodes));
		}
		
		codes.remove(id);
		codes.add(0, id);
		store.setStringArrayOption(LANG_STORAGE_KEY, codes.toArray(new String[0]));
	}
	

}
