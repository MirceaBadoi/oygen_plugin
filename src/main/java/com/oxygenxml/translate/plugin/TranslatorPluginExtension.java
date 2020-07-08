package com.oxygenxml.translate.plugin;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import ro.sync.ecss.extensions.api.AuthorAccess;
import ro.sync.exml.editor.EditorPageConstants;
import ro.sync.exml.plugin.workspace.WorkspaceAccessPluginExtension;
import ro.sync.exml.workspace.api.editor.WSEditor;
import ro.sync.exml.workspace.api.editor.page.author.WSAuthorEditorPage;
import ro.sync.exml.workspace.api.editor.page.text.WSTextEditorPage;
import ro.sync.exml.workspace.api.options.WSOptionsStorage;
import ro.sync.exml.workspace.api.standalone.StandalonePluginWorkspace;
import ro.sync.exml.workspace.api.standalone.ViewComponentCustomizer;
import ro.sync.exml.workspace.api.standalone.ViewInfo;
import ro.sync.exml.workspace.api.standalone.actions.MenusAndToolbarsContributorCustomizer;

/**
 * Plugin extension - workspace access extension.
 */
public class TranslatorPluginExtension implements WorkspaceAccessPluginExtension {
	
	/**
	 * The custom messages area. A sample component added to your custom view.
	 */
	private JTextArea customMessagesArea;

	private JMenu customMenuSection;
	OxygenTranslator message=new OxygenTranslator();
	/**
	 * @see ro.sync.exml.plugin.workspace.WorkspaceAccessPluginExtension#applicationStarted(ro.sync.exml.workspace.api.standalone.StandalonePluginWorkspace)
	 */
	public void applicationStarted(final StandalonePluginWorkspace pluginWorkspaceAccess) {

		pluginWorkspaceAccess.addMenusAndToolbarsContributorCustomizer(new MenusAndToolbarsContributorCustomizer() {
			/**
			 * Customize the author popup menu.
			 */

			@Override
			public void customizeAuthorPopUpMenu(JPopupMenu popup, AuthorAccess authorAccess) {
				// Add our custom action

				deceidePluginStatusContextualMenu(popup, pluginWorkspaceAccess);
			}

			@Override
			public void customizeTextPopUpMenu(JPopupMenu popup, WSTextEditorPage textPage) {
				deceidePluginStatusContextualMenu(popup, pluginWorkspaceAccess);
			}
		});

		pluginWorkspaceAccess.addViewComponentCustomizer(new ViewComponentCustomizer() {
			/**
			 * @see ro.sync.exml.workspace.api.standalone.ViewComponentCustomizer#customizeView(ro.sync.exml.workspace.api.standalone.ViewInfo)
			 */
			public void customizeView(ViewInfo viewInfo) {
				if (

				"SampleWorkspaceAccessID".equals(viewInfo.getViewID())) {
					customMessagesArea = new JTextArea("Messages:");
					viewInfo.setComponent(new JScrollPane(customMessagesArea));
					viewInfo.setTitle("Custom Messages");

				}
			}
		});
	}

	/**
	 * Create the Swing action which shows the current selection.
	 * 
	 * @param pluginWorkspaceAccess The plugin workspace access.
	 */
	@SuppressWarnings("serial")
	private AbstractAction createShowSelectionAction(final StandalonePluginWorkspace pluginWorkspaceAccess, String language) {
		
		return new AbstractAction(message.getTranslation(Languages.getLanguageName(language))) {
			public void actionPerformed(ActionEvent actionevent) {
				TranslateToLanguageAction n=new TranslateToLanguageAction(pluginWorkspaceAccess,language);
				n.actionPerformed(actionevent);
			}
		};
	}

	public boolean applicationClosing() {
		// You can reject the application closing here
		return true;
	}

	public void createMenuLanguages(JPopupMenu popup, StandalonePluginWorkspace pluginWorkspaceAccess) {
		String[] languageArray = Languages.getLanguges();
		int i=0;
		customMenuSection = new JMenu(message.getTranslation(Tags.MENU_TEXT));
		JMenu others = new JMenu(message.getTranslation(Tags.SUBMENU_TEXT));
		
		for (String s : languageArray) {
			if(i>5) {
			JMenuItem menuItem = new JMenuItem(createShowSelectionAction(pluginWorkspaceAccess, s));
			others.add(menuItem);
			}
			else {
				JMenuItem menuItem = new JMenuItem(createShowSelectionAction(pluginWorkspaceAccess, s));
				customMenuSection.add(menuItem);
			}
			i++;
		}

		
		customMenuSection.add(others);
		popup.add(customMenuSection);

	}

	public void deceidePluginStatusContextualMenu(JPopupMenu popup, StandalonePluginWorkspace pluginWorkspaceAccess) {

		WSEditor editorAccess = pluginWorkspaceAccess
				.getCurrentEditorAccess(StandalonePluginWorkspace.MAIN_EDITING_AREA);

		if (EditorPageConstants.PAGE_AUTHOR.equals(editorAccess.getCurrentPageID())) {

			WSAuthorEditorPage authorPageAccess = (WSAuthorEditorPage) editorAccess.getCurrentPage();

			if (authorPageAccess.hasSelection()) {
				createMenuLanguages(popup, pluginWorkspaceAccess);
			}
		}
		if (EditorPageConstants.PAGE_TEXT.equals(editorAccess.getCurrentPageID())) {

			WSTextEditorPage textPage = (WSTextEditorPage) editorAccess.getCurrentPage();

			if (textPage.hasSelection()) {
				createMenuLanguages(popup, pluginWorkspaceAccess);
			}
		}

	}

}