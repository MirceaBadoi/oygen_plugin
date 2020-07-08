package com.oxygenxml.translate.plugin;

/**
 * Interface used for internationalization.
 *
 */
public interface Translator {
	
	/**
	 * Get the translation from the given key;
	 * @param key the key.
	 * @return the translation.
	 */
	public String getTranslation(String key);
}