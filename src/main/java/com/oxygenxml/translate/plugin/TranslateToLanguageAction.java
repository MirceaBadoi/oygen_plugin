package com.oxygenxml.translate.plugin;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.net.URL;

import javax.swing.AbstractAction;
import ro.sync.exml.workspace.api.PluginWorkspace;
import ro.sync.exml.workspace.api.editor.WSEditor;
import ro.sync.exml.workspace.api.editor.page.WSEditorPage;
import ro.sync.exml.workspace.api.editor.page.WSTextBasedEditorPage;
import ro.sync.exml.workspace.api.standalone.StandalonePluginWorkspace;

@SuppressWarnings("serial")
public class TranslateToLanguageAction extends AbstractAction {
	
	private PluginWorkspace pluginWorkspaceAccess;
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
	
	private void browse(String selectedText) {
		try {
			Desktop.getDesktop()
			.browse(new URL("https://translate.google.com/#view=home&op=translate&sl=auto&tl="
					+ language + "&text="
					+ pluginWorkspaceAccess.getUtilAccess().correctURL(selectedText))
					.toURI());
		} catch (Exception ex) {
		}
	}
}
