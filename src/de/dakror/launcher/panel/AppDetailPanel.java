package de.dakror.launcher.panel;

import java.awt.Color;

import javax.swing.JPanel;

import de.dakror.launcher.app.App;

/**
 * @author Dakror
 */
public class AppDetailPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	App app;
	
	public AppDetailPanel(App app)
	{
		this.app = app;
		setSize(1200, 900);
		setBackground(new Color(0, 0, 0, 0.5f));
	}
}
