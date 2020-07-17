package com.oxygenxml.translate.plugin;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.AbstractAction;
<<<<<<< HEAD

import ro.sync.ecss.extensions.api.AuthorDocumentController;
import ro.sync.ecss.extensions.api.content.TextContentIterator;
import ro.sync.ecss.extensions.api.content.TextContext;
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
=======
import ro.sync.exml.workspace.api.PluginWorkspace;
import ro.sync.exml.workspace.api.editor.WSEditor;
import ro.sync.exml.workspace.api.editor.page.WSEditorPage;
import ro.sync.exml.workspace.api.editor.page.WSTextBasedEditorPage;
import ro.sync.exml.workspace.api.standalone.StandalonePluginWorkspace;

>>>>>>> parent of 9d09272... update
@SuppressWarnings("serial")
public class TranslateToLanguageAction extends AbstractAction {

	private PluginWorkspace pluginWorkspaceAccess;
<<<<<<< HEAD
	private String languageCode;
	private WSTextBasedEditorPage textPage;
	public static final String PATTERN = " _674_1_\n";

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
		WSEditor editorAccess = textPage.getParentEditor();
		ArrayList<Integer> finalWhiteSpace = new ArrayList<Integer>();
		ArrayList<Integer> beginingWhiteSpace = new ArrayList<Integer>();
		if (EditorPageConstants.PAGE_AUTHOR.equals(editorAccess.getCurrentPageID())) {
			StringBuilder textSelectedTransformed = new StringBuilder();
			WSAuthorEditorPage authorAccess = (WSAuthorEditorPage) editorAccess.getCurrentPage();
			AuthorDocumentController controller = authorAccess.getDocumentController();
			TextContentIterator documentIterator = controller.getTextContentIterator(authorAccess.getSelectionStart(),
					authorAccess.getSelectionEnd());
			while (documentIterator.hasNext()) {
				TextContext next = documentIterator.next();
				String content = next.getText().toString();
				if (content.endsWith(" ")) {
					finalWhiteSpace.add(1);
				} else {
					finalWhiteSpace.add(0);
				}

				if (content.startsWith(" ")) {
					beginingWhiteSpace.add(1);
				} else {
					beginingWhiteSpace.add(0);
				}
				textSelectedTransformed.append(content).append(PATTERN);
			}
			browse(textSelectedTransformed.toString());
		} else {
			browse(textPage.getSelectedText());
		}

		Languages.increasePriorityLanguage(pluginWorkspaceAccess.getOptionsStorage(), languageCode);
		ReplaceDialogBox replaceDialog = new ReplaceDialogBox();
		replaceDialog.setVisible(true);
		if (replaceDialog.getResult() == OKCancelDialog.RESULT_OK) {
			if (EditorPageConstants.PAGE_AUTHOR.equals(editorAccess.getCurrentPageID())) {
				WSAuthorEditorPage authorPage = (WSAuthorEditorPage) editorAccess.getCurrentPage();
				AuthorDocumentController controller = authorPage.getDocumentController();

				ReplaceWordsUtil.replaceText(controller, authorPage.getSelectionStart(), authorPage.getSelectionEnd(),
						replaceDialog.getText(), PATTERN, finalWhiteSpace, beginingWhiteSpace);

			}
		} else {
			replaceDialog.saveLocationBox();
		}
	}

	/**
	 * Browse selected text
	 * 
	 * @param selectedText
	 */
=======
	private String language;


	/**
	 * 
	 * @param pluginWorkspaceAccess
	 * @param language
	 */
	public TranslateToLanguageAction(PluginWorkspace pluginWorkspaceAccess, String language) {
		this.pluginWorkspaceAccess = pluginWorkspaceAccess;
		this.language = language;
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {

		// Get the current opened XML document
		WSEditor editorAccess = pluginWorkspaceAccess
				.getCurrentEditorAccess(StandalonePluginWorkspace.MAIN_EDITING_AREA);

		if (editorAccess != null) {
			WSEditorPage currentPage = editorAccess.getCurrentPage();
			if (currentPage instanceof WSTextBasedEditorPage) {
				WSTextBasedEditorPage wsTextBasedEditorPage = (WSTextBasedEditorPage)currentPage; 
				if (wsTextBasedEditorPage.hasSelection()) {

					browse(wsTextBasedEditorPage.getSelectedText());

				}
			} 
			
		}
	}
	
>>>>>>> parent of 9d09272... update
	private void browse(String selectedText) {

		try {
			String toBrowse = "https://translate.google.com/#view=home&op=translate&sl=auto&tl=" + languageCode
					+ "&text=" + pluginWorkspaceAccess.getUtilAccess().correctURL(selectedText);

			Desktop.getDesktop().browse(new URL(toBrowse).toURI());
		} catch (Exception ex) {
		}
	}
	
	
}
