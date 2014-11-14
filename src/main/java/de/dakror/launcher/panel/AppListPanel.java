package de.dakror.launcher.panel;

import java.awt.FlowLayout;

import javax.swing.JPanel;

/**
 * @author Dakror
 */
public class AppListPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public AppListPanel() {
		setSize(1200, 900);
		setLayout(new FlowLayout(FlowLayout.CENTER, 15, 50));
		setOpaque(false);
	}
}
