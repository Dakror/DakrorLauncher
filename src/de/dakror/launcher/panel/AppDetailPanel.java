package de.dakror.launcher.panel;

import java.awt.BorderLayout;
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
	
	public AppDetailPanel()
	{
		init();
	}
	
	public void setApp(App app)
	{
		this.app = app;
		init();
	}
	
	public void init()
	{
		removeAll();
		setSize(1200, 900);
		setBackground(new Color(0, 0, 0, 0.5f));
		setLayout(new BorderLayout(0, 0));
	}
}
