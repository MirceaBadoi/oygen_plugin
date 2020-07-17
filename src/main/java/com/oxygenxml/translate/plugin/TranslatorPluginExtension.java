package com.oxygenxml.translate.plugin;

import javax.swing.JMenu;


import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import ro.sync.ecss.extensions.api.AuthorAccess;
import ro.sync.exml.plugin.workspace.WorkspaceAccessPluginExtension;
import ro.sync.exml.workspace.api.editor.WSEditor;
import ro.sync.exml.workspace.api.editor.page.WSEditorPage;
import ro.sync.exml.workspace.api.editor.page.WSTextBasedEditorPage;
import ro.sync.exml.workspace.api.editor.page.text.WSTextEditorPage;
import ro.sync.exml.workspace.api.standalone.StandalonePluginWorkspace;
import ro.sync.exml.workspace.api.standalone.ViewComponentCustomizer;
import ro.sync.exml.workspace.api.standalone.ViewInfo;
import ro.sync.exml.workspace.api.standalone.actions.MenusAndToolbarsContributorCustomizer;

/**
 * Plugin extension - workspace access extension.
 */
public class TranslatorPluginExtension implements WorkspaceAccessPluginExtension {

	/**
	 * The custom messages area.
	 */
	private JTextArea customMessagesArea;
	private OxygenTranslator message = new OxygenTranslator();

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

				decidePluginStatusContextualMenu(popup, pluginWorkspaceAccess);
			}

			/**
			 * Customize the text popup menu
			 */

			@Override
			public void customizeTextPopUpMenu(JPopupMenu popup, WSTextEditorPage textPage) {

				decidePluginStatusContextualMenu(popup, pluginWorkspaceAccess);
			}
		});

		pluginWorkspaceAccess.addViewComponentCustomizer(new ViewComponentCustomizer() {
			/**
			 * @see ro.sync.exml.workspace.api.standalone.ViewComponentCustomizer#customizeView(ro.sync.exml.workspace.api.standalone.ViewInfo)
			 */
			public void customizeView(ViewInfo viewInfo) {
				if (

				"TranslatorWorkspace".equals(viewInfo.getViewID())) {
					customMessagesArea = new JTextArea("Messages:");
					viewInfo.setComponent(new JScrollPane(customMessagesArea));
					viewInfo.setTitle("Translator");

				}
			}
		});
	}

	public boolean applicationClosing() {
		// You can reject the application closing here

		return true;
	}

	/**
	 * Create menu with supported languages
	 * 
	 * @param popup
	 * @param pluginWorkspaceAccess
	 * @param wsTextBasedEditorPage
	 */

	public void createMenuLanguages(JPopupMenu popup, StandalonePluginWorkspace pluginWorkspaceAccess,
			WSTextBasedEditorPage wsTextBasedEditorPage) {

		int numberOfLanguagesInMenu = 6;// number of languages on menu page
		String[] languageArray = Languages.getPrioritizedLanguageISOCodes(
				pluginWorkspaceAccess.getOptionsStorage(),
				Languages.LANGUAGES.keySet());

		JMenu newMenu = new JMenu(message.getTranslation(Tags.TRANSLATE_TO));
		JMenu indexMenu = newMenu;

		 //Go through all languages
		for (int index = 0; index < languageArray.length; index++) {
			TranslateToLanguageAction translator = new TranslateToLanguageAction(pluginWorkspaceAccess,
					wsTextBasedEditorPage, languageArray[index],
					message.getTranslation(Languages.getLanguageName(languageArray[index])));

			JMenuItem menuItem = new JMenuItem(translator);
			indexMenu.add(menuItem);

			 // make the menu have 7 items, including others item also do not add others item
			 //if the menu have less then 6 languages added
			if ((index + 1) % numberOfLanguagesInMenu == 0 && index != 0) {
				JMenu others = new JMenu(message.getTranslation(Tags.OTHERS_SUBMENU));
				indexMenu.add(others);
				indexMenu = others;
			}

		}

		popup.add(newMenu);

	}

	/**
	 * Make the language menu available if any text in selected and disable it if
	 * not
	 * 
	 * @param popup
	 * @param pluginWorkspaceAccess
	 */
	public void decidePluginStatusContextualMenu(JPopupMenu popup, StandalonePluginWorkspace pluginWorkspaceAccess) {

		WSEditor editorAccess = pluginWorkspaceAccess
				.getCurrentEditorAccess(StandalonePluginWorkspace.MAIN_EDITING_AREA);

		WSEditorPage currentPage = editorAccess.getCurrentPage();
		if (currentPage instanceof WSTextBasedEditorPage) {
			WSTextBasedEditorPage wsTextBasedEditorPage = (WSTextBasedEditorPage) currentPage;
			if (wsTextBasedEditorPage.hasSelection()) {

				createMenuLanguages(popup, pluginWorkspaceAccess, wsTextBasedEditorPage);

			}
		}
	}

}