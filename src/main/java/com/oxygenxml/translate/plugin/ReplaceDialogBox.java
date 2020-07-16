package com.oxygenxml.translate.plugin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
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

@SuppressWarnings("serial")
public class ReplaceDialogBox extends OKCancelDialog {

	private static final WSOptionsStorage OPTIONS_STORAGE = PluginWorkspaceProvider.getPluginWorkspace()
			.getOptionsStorage();
	private JTextArea textInput;

	public ReplaceDialogBox() {
		super(null, "Replace Content With Translation", true);
		setOkButtonText("Replace");
		setLayout(new BorderLayout());
		textInput = new JTextArea();
		loadSavedLocationBox();
		textInput.setWrapStyleWord(true);
		textInput.setLineWrap(true);

		JLabel instruction = new JLabel("Replace selected content with: ");
		add(instruction, BorderLayout.NORTH);

		DefaultCaret caret = (DefaultCaret) textInput.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		JScrollPane scrollPane = new JScrollPane(textInput);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(400, 200));
		Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
		scrollPane
				.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 0)));
		add(scrollPane, BorderLayout.CENTER);
		popUpMenuDialogBox();
		pack();
	}

	private void popUpMenuDialogBox() {
		textInput.addMouseListener(new MouseListenerAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					JPopupMenu popup = createDialogBoxPopupMenu();
					popup.show(e.getComponent(), e.getX(), e.getY());
				}

			}

		});
	}

	private JPopupMenu createDialogBoxPopupMenu() {
		String cutAction = "cut";
		String pasteAction = "paste";
		String copyAction = "copy";
		String selectAllAction = "select all";
		
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

	private void loadSavedLocationBox() {
		if (OPTIONS_STORAGE.getOption("XSize", null) != null) {
			Integer x = Integer.valueOf(OPTIONS_STORAGE.getOption("XSize", null));
			Integer y = Integer.valueOf(OPTIONS_STORAGE.getOption("YSize", null));
			setLocation(x, y);
		} else {
			setLocationRelativeTo(null);
		}

	}

	public void saveLocationBox() {
		OPTIONS_STORAGE.setOption("XSize", Integer.toString(this.getX()));
		OPTIONS_STORAGE.setOption("YSize", Integer.toString(this.getY()));

	}

	public String getText() {
		return textInput.getText();
	}

}
