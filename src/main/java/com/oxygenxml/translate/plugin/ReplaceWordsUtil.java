package com.oxygenxml.translate.plugin;

import java.util.ArrayList;

import ro.sync.ecss.extensions.api.AuthorDocumentController;
import ro.sync.ecss.extensions.api.content.TextContentIterator;
import ro.sync.ecss.extensions.api.content.TextContext;

/**
 * Util class for text replacement
 * @author Badoi Mircea
 *
 */
public class ReplaceWordsUtil {

	private ReplaceWordsUtil() {
		// empty to avoid instantiation
	}
	
	/**
	 * Replaces the selected text with the translation
	 * @param controller AuthorDocumentController
	 * @param selectionStart Selection start
	 * @param selectionEnd	Selection end
	 * @param replacementText Text to replace with
	 * @param pattern Pattern used as a delimiter for nodes
	 * @param whiteSpaces ArrayList in order to know whitespace
	 */
	public static void replaceText(AuthorDocumentController controller, int selectionStart, int selectionEnd,
			String replacementText, String pattern, ArrayList<SegmentInfo> whiteSpaces) {

		TextContentIterator documentIterator = controller.getTextContentIterator(selectionStart, selectionEnd);
		String[] translatedContent = replacementText.split(pattern.trim());
		try {
			controller.beginCompoundEdit();
			//iterate through nodes and replace text content with translation
			for (int i = 0; documentIterator.hasNext() && i < translatedContent.length; i++) {
				String whiteSpace = " ";
				TextContext next = documentIterator.next();
				String correctedTransletadContent = translatedContent[i].trim();
				if (whiteSpaces.get(i).isStartWithWhitespace()) {
					correctedTransletadContent = whiteSpace + correctedTransletadContent;
				}
				if (whiteSpaces.get(i).isEndWithWhitespace()) {
					correctedTransletadContent = correctedTransletadContent + whiteSpace;
				}
				//decide whitespace 
				next.replaceText(correctedTransletadContent);
			}
		} finally {
			controller.endCompoundEdit();
		}
	}
	
	/**
	 * Prepare the selected content for translation
	 * @param documentIterator
	 * @param textSelectedTransformed
	 * @param pattern
	 * @return
	 */
	public static ArrayList<SegmentInfo> transformSelectedText(TextContentIterator documentIterator,
			StringBuilder textSelectedTransformed, String pattern) {
		ArrayList<SegmentInfo> whiteSpaces = new ArrayList<SegmentInfo>();
		while (documentIterator.hasNext()) {
			TextContext next = documentIterator.next();
			String content = next.getText().toString();
			SegmentInfo pair = new SegmentInfo();
		
			if (content.startsWith(" ")) {
				pair.setStartWithWhitespace(true);
			}

			if (content.endsWith(" ")) {
				pair.setEndWithWhitespace(true);
			}

			whiteSpaces.add(pair);

			textSelectedTransformed.append(content).append(pattern);
		}
		return whiteSpaces;
	}

}
