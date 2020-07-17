package com.oxygenxml.translate.plugin;

/**
 * Tags used for persistence and internationalization.
 *
 */
public class Tags {

	/**
	 * Private constructor.
	 */
	private Tags() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * The text of JMenu
	 */
	public static final String TRANSLATE_TO = "Translate_To";

	/**
	 * Text for the "Others" JMenu
	 */
	
	public static final String OTHERS_SUBMENU = "Others_Submenu";
	
	/**
	 * Text for JDialog translator helper to replace button
	 */
	public static final String REPLACE_BUTTON_TEXT = "ReplaceButtonText";
	
	/**
	 * Text for JDialog translator helper to replace label
	 */
	public static final String REPLACE_CONTENT_WITH = "ReplaceContentWith";
	
	/**
	 * Text for JDialog translator helper to replace title
	 */
	public static final String COPY_OPTION = "CopyOption";
	
	public static final String PASTE_OPTION = "PasteOption";
	
	public static final String CUT_OPTION = "CutOption";
	
	public static final String SELECTALL_OPTION = "SelectAllOption";
	
	public static final String REPLACE_CONTENT_WITH_TRANSLATION = "ReplaceContentWithTranslation";
}
