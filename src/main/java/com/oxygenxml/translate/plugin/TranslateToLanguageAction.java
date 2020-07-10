package com.oxygenxml.translate.plugin;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.net.URL;

import javax.swing.AbstractAction;

import ro.sync.exml.workspace.api.PluginWorkspace;
import ro.sync.exml.workspace.api.editor.page.WSTextBasedEditorPage;
/**
 * 
 * @author Badoi Mircea
 *
 */
@SuppressWarnings("serial")
public class TranslateToLanguageAction extends AbstractAction {
	
	private PluginWorkspace pluginWorkspaceAccess;
	private String language;
	private WSTextBasedEditorPage textPage;


	/**
	 * Constructor
	 * @param pluginWorkspaceAccess
	 * @param language
	 */
	public TranslateToLanguageAction(PluginWorkspace pluginWorkspaceAccess, WSTextBasedEditorPage textPage, String language,String actionName) {
		super(actionName);
		this.pluginWorkspaceAccess = pluginWorkspaceAccess;
		this.textPage = textPage;
		this.language = language;
	}
	
	/**
	 * Swing action
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		browse(textPage.getSelectedText());
	}
	
	/**
	 * Browse selected text
	 * @param selectedText
	 */
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
