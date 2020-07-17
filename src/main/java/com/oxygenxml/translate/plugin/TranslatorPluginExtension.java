package com.oxygenxml.translate.plugin;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenu;
<<<<<<< HEAD


=======
>>>>>>> parent of 9d09272... update
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
<<<<<<< HEAD
	 * The custom messages area.
	 */
	private JTextArea customMessagesArea;
	private OxygenTranslator message = new OxygenTranslator();

=======
	 * The custom messages area. A sample component added to your custom view.
	 */
	private JTextArea customMessagesArea;

	private JMenu customMenuSection;
	OxygenTranslator message=new OxygenTranslator();
>>>>>>> parent of 9d09272... update
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
<<<<<<< HEAD

			/**
			 * Customize the text popup menu
			 */
=======
>>>>>>> parent of 9d09272... update

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

				"TranslatorWorkspace".equals(viewInfo.getViewID())) {
					customMessagesArea = new JTextArea("Messages:");
					viewInfo.setComponent(new JScrollPane(customMessagesArea));
					viewInfo.setTitle("Translator");

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
<<<<<<< HEAD

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
		String[] languageArray = Languages.getLanguagesISOCodes(
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
=======

	public void createMenuLanguages(JPopupMenu popup, StandalonePluginWorkspace pluginWorkspaceAccess) {
		String[] languageArray = Languages.getLanguges();
		int i=0;
		customMenuSection = new JMenu(message.getTranslation(Tags.MENU_TEXT));
		JMenu others = new JMenu(message.getTranslation(Tags.SUBMENU_TEXT));
		
		for (String s : languageArray) {
			if(i>5) {
			JMenuItem menuItem = new JMenuItem(createShowSelectionAction(pluginWorkspaceAccess, s));
			others.add(menuItem);
>>>>>>> parent of 9d09272... update
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

<<<<<<< HEAD
	/**
	 * Make the language menu available if any text in selected and disable it if
	 * not
	 * 
	 * @param popup
	 * @param pluginWorkspaceAccess
	 */
	public void decidePluginStatusContextualMenu(JPopupMenu popup, StandalonePluginWorkspace pluginWorkspaceAccess) {
=======
	public void deceidePluginStatusContextualMenu(JPopupMenu popup, StandalonePluginWorkspace pluginWorkspaceAccess) {
>>>>>>> parent of 9d09272... update

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