package com.oxygenxml.translate.plugin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;

import ro.sync.exml.workspace.api.PluginWorkspaceProvider;
import ro.sync.exml.workspace.api.options.WSOptionsStorage;
import ro.sync.exml.workspace.api.standalone.ui.OKCancelDialog;
/**
 * DialogBox class for translation replacement
 * @author Badoi Mircea
 *
 */
@SuppressWarnings("serial")
public class ReplaceDialogBox extends OKCancelDialog {
	private static OxygenTranslator message = new OxygenTranslator();
	private final String yAxisLocation = "ReplaceDialogBox_YAxis";
	private final String xAxisLocation = "ReplaceDialogBox_XAxis";
	private final WSOptionsStorage OPTIONS_STORAGE = PluginWorkspaceProvider.getPluginWorkspace()
			.getOptionsStorage();
	private JTextArea textInput;
	/**
	 * Makes a JDialog for translator plug-in in order to replace the selected text with translation
	 */
	public ReplaceDialogBox() {
		super(null, message.getTranslation(Tags.TRANSLATE_TO), true);
		setOkButtonText(message.getTranslation(Tags.REPLACE_BUTTON_TEXT));
		setLayout(new BorderLayout());
		textInput = new JTextArea();
		loadSavedLocationBox();
		textInput.setWrapStyleWord(true);
		textInput.setLineWrap(true);

		JLabel instruction = new JLabel(message.getTranslation(Tags.REPLACE_CONTENT_WITH));
		add(instruction, BorderLayout.NORTH);

		DefaultCaret carret = (DefaultCaret) textInput.getCaret();
		carret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);//move down as text programmatically is being inserted
		JScrollPane scrollPane = new JScrollPane(textInput);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(400, 200));
		Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
		scrollPane
				.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 0)));
		add(scrollPane, BorderLayout.CENTER);
		installPopUpMenuDialogBox();//pop up menu with options
		pack(); 
	}
	/**
	 * Add pop up menu to JTextArea(right click mouse listener)
	 */
	private void installPopUpMenuDialogBox() {
		textInput.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					JPopupMenu popup = createDialogBoxPopupMenu();
					popup.show(e.getComponent(), e.getX(), e.getY());
				}

			}

		});
	}

	/**
	 * Create pop up menu and associate action to items
	 * @return
	 */
	private JPopupMenu createDialogBoxPopupMenu() {
		
		String cutAction = message.getTranslation(Tags.CUT_OPTION);
		String pasteAction = message.getTranslation(Tags.PASTE_OPTION);
		String copyAction = message.getTranslation(Tags.COPY_OPTION);
		String selectAllAction = message.getTranslation(Tags.SELECTALL_OPTION);
		
		JPopupMenu popup = new JPopupMenu();
		JMenuItem paste = new JMenuItem(new AbstractAction(pasteAction) {

			@Override
			public void actionPerformed(ActionEvent e) {
				textInput.paste();
			}
		});

		JMenuItem copy = new JMenuItem(new AbstractAction(copyAction) {

			@Override
			public void actionPerformed(ActionEvent e) {
				textInput.copy();
			}
		});
		
		JMenuItem cut = new JMenuItem(new AbstractAction(cutAction) {

			@Override
			public void actionPerformed(ActionEvent e) {
				textInput.cut();
			}
		});
		
		JMenuItem selectAll = new JMenuItem(new AbstractAction(selectAllAction) {

			@Override
			public void actionPerformed(ActionEvent e) {
				textInput.selectAll();
			}
		});
		
		popup.add(cut);
		popup.add(copy);
		popup.add(paste);
		popup.addSeparator();
		popup.add(selectAll);

		return popup;
	}
	/**
	 * Load saved location of the JDialog for persistence
	 */
	private void loadSavedLocationBox() {
		if (OPTIONS_STORAGE.getOption(xAxisLocation, null) != null) {
			Integer xAx = Integer.valueOf(OPTIONS_STORAGE.getOption(xAxisLocation, null));
			Integer yAx = Integer.valueOf(OPTIONS_STORAGE.getOption(yAxisLocation, null));
			setLocation(xAx, yAx);
		} else {
			setLocationRelativeTo(null);
		}

	}
	
	@Override
	public void setVisible(boolean visible) {
		if(! visible) {
			saveLocationBox();
		}
		super.setVisible(visible);
	}
	/**
	 * Save actual location of the JDialog for persistence
	 */
	private void saveLocationBox() {
		OPTIONS_STORAGE.setOption(xAxisLocation, Integer.toString(this.getX()));
		OPTIONS_STORAGE.setOption(yAxisLocation, Integer.toString(this.getY()));

	}

	public String getText() {
		return textInput.getText();
	}

}
