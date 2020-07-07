package com.oxygenxml.translate.plugin;

public class Languages {
	
	private enum Language {
		Spanish, Romanian, English, German,French,Chinese,Japanese,Dutch,Hungarian

	}
	
	public String getLanguageName(String id) {
		
		String lang = "NOT SUPPORTED";
		switch (id) {

		case "ro":
			lang = Language.Romanian.toString();
			break;
		case "en":
			lang = Language.English.toString();
			break;
		case "de":
			lang = Language.German.toString();
			break;
		case "es":
			lang = Language.Spanish.toString();
			break;
		case "fr":
			lang = Language.French.toString();
			break;
		case "zh-CN":
			lang = Language.Chinese.toString();
			break;
		case "ja":
			lang = Language.Japanese.toString();
			break;
		case "nl":
			lang = Language.Dutch.toString();
			break;
		case "hu":
			lang = Language.Hungarian.toString();
			break;

		}
		
		return lang;

	}

}
