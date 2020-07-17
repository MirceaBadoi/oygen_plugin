package com.oxygenxml.translate.plugin;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.AbstractAction;

import ro.sync.ecss.extensions.api.AuthorDocumentController;
import ro.sync.ecss.extensions.api.content.TextContentIterator;
import ro.sync.exml.editor.EditorPageConstants;
import ro.sync.exml.workspace.api.PluginWorkspace;
import ro.sync.exml.workspace.api.editor.WSEditor;
import ro.sync.exml.workspace.api.editor.page.WSTextBasedEditorPage;
import ro.sync.exml.workspace.api.editor.page.author.WSAuthorEditorPage;
import ro.sync.exml.workspace.api.standalone.ui.OKCancelDialog;

/**
 * 
 * @author Badoi Mircea
 *
 */
@SuppressWarnings("serial")
public class TranslateToLanguageAction extends AbstractAction {

	private PluginWorkspace pluginWorkspaceAccess;
	private String languageCode;
	private WSTextBasedEditorPage textPage;

	/**
	 * 
	 * @param pluginWorkspaceAccess Provides access to plugin workspace methods.
	 * @param textPage              The text page form Oxygen Editor.
	 * @param languageCode          language shorthand (ro, en, fr )
	 * @param languageName          language full name.
	 */
	public TranslateToLanguageAction(PluginWorkspace pluginWorkspaceAccess, WSTextBasedEditorPage textPage,
			String languageCode, String languageName) {
		super(languageName);
		this.pluginWorkspaceAccess = pluginWorkspaceAccess;
		this.textPage = textPage;
		this.languageCode = languageCode;
	}

	/**
	 * Swing action
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String pattern = PatternGenerator.randomPatternDelimiter(1000, 9999);
		WSEditor editorAccess = textPage.getParentEditor();
		if (EditorPageConstants.PAGE_AUTHOR.equals(editorAccess.getCurrentPageID())) {
			StringBuilder textSelectedTransformed = new StringBuilder();
			WSAuthorEditorPage authorPage = (WSAuthorEditorPage) editorAccess.getCurrentPage();
			AuthorDocumentController controller = authorPage.getDocumentController();
			TextContentIterator documentIterator = controller.getTextContentIterator(authorPage.getSelectionStart(),
					authorPage.getSelectionEnd());
			
			ArrayList<SegmentInfo> whiteSpaces = new ArrayList<SegmentInfo>();
				whiteSpaces = ReplaceWordsUtil.transformSelectedText(documentIterator, textSelectedTransformed, pattern);
			if(whiteSpaces.size() <= 1) {
				browse(textPage.getSelectedText());
			} else {
				
			browse(textSelectedTransformed.toString());
			ReplaceDialogBox replaceDialog = new ReplaceDialogBox();
			replaceDialog.setVisible(true);
			if (replaceDialog.getResult() == OKCancelDialog.RESULT_OK && !replaceDialog.getText().trim().equals("")) {
	
				ReplaceWordsUtil.replaceText(controller, authorPage.getSelectionStart(),
						authorPage.getSelectionEnd(), replaceDialog.getText(), pattern, whiteSpaces);
				}
			}
		} else {
			browse(textPage.getSelectedText());
		}

		Languages.increasePriorityLanguage(pluginWorkspaceAccess.getOptionsStorage(), languageCode);

	}

	/**
	 * Browse selected text
	 * 
	 * @param selectedText
	 */
	private void browse(String selectedText) {

		try {
			String toBrowse = "https://translate.google.com/#view=home&op=translate&sl=auto&tl=" + languageCode
					+ "&text=" + pluginWorkspaceAccess.getUtilAccess().correctURL(selectedText);

			Desktop.getDesktop().browse(new URL(toBrowse).toURI());
		} catch (Exception ex) {
		}
	}


}
