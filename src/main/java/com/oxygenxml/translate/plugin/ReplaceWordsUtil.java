package com.oxygenxml.translate.plugin;

import java.util.ArrayList;

import ro.sync.ecss.extensions.api.AuthorDocumentController;
import ro.sync.ecss.extensions.api.content.TextContentIterator;
import ro.sync.ecss.extensions.api.content.TextContext;

public class ReplaceWordsUtil {

	private ReplaceWordsUtil() {
		// empty to avoid instantiation
	}

	public static void replaceText(AuthorDocumentController controller, int selectionStart, int selectionEnd,
			String replacementText, String pattern, ArrayList<Integer> finalWhiteSpace,
			ArrayList<Integer> beginingWhiteSpace) {
		TextContentIterator documentIterator = controller.getTextContentIterator(selectionStart, selectionEnd);
		String[] translatedContent = replacementText.split(pattern.trim());
		try {
			controller.beginCompoundEdit();
			for (int i = 0; documentIterator.hasNext() && i < translatedContent.length; i++) {
				TextContext next = documentIterator.next();
				String correctedTransletedContent = translatedContent[i].trim();
				if (finalWhiteSpace.get(i) == 1 && beginingWhiteSpace.get(i) == 1) {
					next.replaceText(" " + correctedTransletedContent + " ");
				}
				if (finalWhiteSpace.get(i) == 1 && beginingWhiteSpace.get(i) == 0) {
					next.replaceText(correctedTransletedContent + " ");
				}
				if (finalWhiteSpace.get(i) == 0 && beginingWhiteSpace.get(i) == 1) {
					next.replaceText(" " + correctedTransletedContent);
				}
				if (finalWhiteSpace.get(i) == 0 && beginingWhiteSpace.get(i) == 0) {
					next.replaceText(correctedTransletedContent);
				}
			}
		} finally {
			controller.endCompoundEdit();
		}
	}

}
