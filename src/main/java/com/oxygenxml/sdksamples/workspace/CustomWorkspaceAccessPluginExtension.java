package com.oxygenxml.sdksamples.workspace;

import java.awt.Container;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

import ro.sync.ecss.extensions.api.AuthorAccess;
import ro.sync.ecss.extensions.api.AuthorDocumentController;
import ro.sync.ecss.extensions.api.node.AuthorDocumentFragment;
import ro.sync.exml.editor.EditorPageConstants;
import ro.sync.exml.plugin.workspace.WorkspaceAccessPluginExtension;
import ro.sync.exml.workspace.api.editor.WSEditor;
import ro.sync.exml.workspace.api.editor.page.author.WSAuthorEditorPage;
import ro.sync.exml.workspace.api.editor.page.text.WSTextEditorPage;
import ro.sync.exml.workspace.api.listeners.WSEditorChangeListener;
import ro.sync.exml.workspace.api.standalone.StandalonePluginWorkspace;
import ro.sync.exml.workspace.api.standalone.ViewComponentCustomizer;
import ro.sync.exml.workspace.api.standalone.ViewInfo;
import ro.sync.exml.workspace.api.standalone.actions.MenusAndToolbarsContributorCustomizer;


/**
 * Plugin extension - workspace access extension.
 */
public class CustomWorkspaceAccessPluginExtension implements WorkspaceAccessPluginExtension  {
  /**
   * The custom messages area. A sample component added to your custom view.
   */
  private JTextArea customMessagesArea;

  /**
   * @see ro.sync.exml.plugin.workspace.WorkspaceAccessPluginExtension#applicationStarted(ro.sync.exml.workspace.api.standalone.StandalonePluginWorkspace)
   */
  public void applicationStarted(final StandalonePluginWorkspace pluginWorkspaceAccess) {
	  //You can set or read global options.
	  //The "ro.sync.exml.options.APIAccessibleOptionTags" contains all accessible keys.
	  //		  pluginWorkspaceAccess.setGlobalObjectProperty("can.edit.read.only.files", Boolean.FALSE);
	  // Check In action

	  //You can access the content inside each opened WSEditor depending on the current editing page (Text/Grid or Author).  
	  // A sample action which will be mounted on the  toolbar and contextual menu.
	final String baseRo="ro";
	final String baseEs="es";
	final String baseDe="de";
	final Action selectionSourceActionRo = createShowSelectionAction(pluginWorkspaceAccess,baseRo);
	final Action selectionSourceActionEs = createShowSelectionAction(pluginWorkspaceAccess,baseEs);
	final Action selectionSourceActionDe = createShowSelectionAction(pluginWorkspaceAccess,baseDe);
	//Mount the action on the contextual menus for the Text and Author modes.
	pluginWorkspaceAccess.addMenusAndToolbarsContributorCustomizer(new MenusAndToolbarsContributorCustomizer() {
				/**
				 * Customize the author popup menu.
				 */
				
				@Override
				public void customizeAuthorPopUpMenu(JPopupMenu popup,
						AuthorAccess authorAccess) {
					// Add our custom action
					JMenu sectionsMenu = new JMenu("Translate Selected");
					JMenuItem menuItem1 = new JMenuItem(selectionSourceActionRo);
					sectionsMenu.add(menuItem1);
					JMenuItem menuItem2 = new JMenuItem(selectionSourceActionEs);
					sectionsMenu.add(menuItem2);
					JMenuItem menuItem3 = new JMenuItem(selectionSourceActionDe);
					sectionsMenu.add(menuItem3);
					
					popup.add(sectionsMenu);
				}

				@Override
				public void customizeTextPopUpMenu(JPopupMenu popup,
						WSTextEditorPage textPage) {
					// Add our custom action
					JMenu sectionsMenu = new JMenu("Translate Selected");
					JMenuItem menuItem1 = new JMenuItem(selectionSourceActionRo);
					sectionsMenu.add(menuItem1);
					JMenuItem menuItem2 = new JMenuItem(selectionSourceActionEs);
					sectionsMenu.add(menuItem2);
					JMenuItem menuItem3 = new JMenuItem(selectionSourceActionDe);
					sectionsMenu.add(menuItem3);
					
					popup.add(sectionsMenu);
				}
			});


	  pluginWorkspaceAccess.addEditorChangeListener(
			  new WSEditorChangeListener() {
				  @Override
				  public boolean editorAboutToBeOpenedVeto(URL editorLocation) {
					  //You can reject here the opening of an URL if you want
					  return true;
				  }
				  @Override
				  public void editorOpened(URL editorLocation) {
					  checkActionsStatus(editorLocation);
				  }

				  // Check actions status
				  private void checkActionsStatus(URL editorLocation) {
					  WSEditor editorAccess = pluginWorkspaceAccess.getCurrentEditorAccess(StandalonePluginWorkspace.MAIN_EDITING_AREA);
					  if (editorAccess != null) {
						  selectionSourceActionRo.setEnabled(
								  EditorPageConstants.PAGE_AUTHOR.equals(editorAccess.getCurrentPageID())
								  || EditorPageConstants.PAGE_TEXT.equals(editorAccess.getCurrentPageID()));
					  }
				  }

				  @Override
				  public void editorClosed(URL editorLocation) {
					  //An edited XML document has been closed.
				  }

				  /**
				   * @see ro.sync.exml.workspace.api.listeners.WSEditorChangeListener#editorAboutToBeClosed(java.net.URL)
				   */
				  @Override
				  public boolean editorAboutToBeClosed(URL editorLocation) {
					  //You can veto the closing of an XML document.
					  //Allow close
					  return true;
				  }

				  /**
				   * The editor was relocated (Save as was called).
				   * 
				   * @see ro.sync.exml.workspace.api.listeners.WSEditorChangeListener#editorRelocated(java.net.URL, java.net.URL)
				   */
				  @Override
				  public void editorRelocated(URL previousEditorLocation, URL newEditorLocation) {
					  //
				  }

				  @Override
				  public void editorPageChanged(URL editorLocation) {
					  checkActionsStatus(editorLocation);
				  }

				  @Override
				  public void editorSelected(URL editorLocation) {
					  checkActionsStatus(editorLocation);
				  }

				  @Override
				  public void editorActivated(URL editorLocation) {
					  checkActionsStatus(editorLocation);
				  }
			  }, 
			  StandalonePluginWorkspace.MAIN_EDITING_AREA);


	  pluginWorkspaceAccess.addViewComponentCustomizer(new ViewComponentCustomizer() {
		  /**
		   * @see ro.sync.exml.workspace.api.standalone.ViewComponentCustomizer#customizeView(ro.sync.exml.workspace.api.standalone.ViewInfo)
		   */
		  public void customizeView(ViewInfo viewInfo) {
			  if(
					  //The view ID defined in the "plugin.xml"
					  "SampleWorkspaceAccessID".equals(viewInfo.getViewID())) {
				  customMessagesArea = new JTextArea("Messages:");
				  viewInfo.setComponent(new JScrollPane(customMessagesArea));
				  viewInfo.setTitle("Custom Messages");
				  //You can have images located inside the JAR library and use them...
//				  viewInfo.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/customMessage.png").toString()));
			  } 
		  }
	  }); 
  }

	/**
	 * Create the Swing action which shows the current selection.
	 * 
	 * @param pluginWorkspaceAccess The plugin workspace access.
	 * @return The "Show Selection" action
	 */
	@SuppressWarnings("serial")
	private AbstractAction createShowSelectionAction(
			final StandalonePluginWorkspace pluginWorkspaceAccess,String language) {
		return new AbstractAction("Translate To " + language.toUpperCase()) {
			  public void actionPerformed(ActionEvent actionevent) {
				  //Get the current opened XML document
				  WSEditor editorAccess = pluginWorkspaceAccess.getCurrentEditorAccess(StandalonePluginWorkspace.MAIN_EDITING_AREA);
				  // The action is available only in Author mode.
				  if(editorAccess != null){
					  if (EditorPageConstants.PAGE_AUTHOR.equals(editorAccess.getCurrentPageID())) {
						  WSAuthorEditorPage authorPageAccess = (WSAuthorEditorPage) editorAccess.getCurrentPage();
						  AuthorDocumentController controller = authorPageAccess.getDocumentController();
						  if (authorPageAccess.hasSelection()) {
							  AuthorDocumentFragment selectionFragment;
							  try {
								  // Create fragment from selection
								  selectionFragment = controller.createDocumentFragment(
										  authorPageAccess.getSelectionStart(),
										  authorPageAccess.getSelectionEnd() - 1
										  );
								  // Serialize
								  String serializeFragmentToXML = controller.serializeFragmentToXML(selectionFragment);
						
								  // Show fragment
								  try {
									    Desktop.getDesktop().browse(new URL("https://translate.google.com/#view=home&op=translate&sl=auto&tl=" + language.toLowerCase() + "&text=" + pluginWorkspaceAccess.getUtilAccess().correctURL((serializeFragmentToXML ))).toURI());
									} catch (Exception e) {}
							  } catch (BadLocationException e) {
								  pluginWorkspaceAccess.showErrorMessage("Show Selection Source operation failed: " + e.getMessage());
						
							  }
							  
						  } else {
							  // No selection
			
							  pluginWorkspaceAccess.showInformationMessage("No selection available.");
						  }
					  } else if (EditorPageConstants.PAGE_TEXT.equals(editorAccess.getCurrentPageID())) {
						  WSTextEditorPage textPage = (WSTextEditorPage) editorAccess.getCurrentPage();
						  if (textPage.hasSelection()) {
							  try {
								  Desktop.getDesktop().browse(new URL("https://translate.google.com/#view=home&op=translate&sl=auto&tl=" + language.toLowerCase() + "&text="+pluginWorkspaceAccess.getUtilAccess().correctURL(textPage.getSelectedText())).toURI());
								} catch (Exception e) {}
						  } else {
							  // No selection
							  pluginWorkspaceAccess.showInformationMessage("No selection available.");
						  }
					  }
				  }
			  }
		  };
	}
  
  /**
   * @see ro.sync.exml.plugin.workspace.WorkspaceAccessPluginExtension#applicationClosing()
   */
  public boolean applicationClosing() {
	  //You can reject the application closing here
    return true;
  }
  
}