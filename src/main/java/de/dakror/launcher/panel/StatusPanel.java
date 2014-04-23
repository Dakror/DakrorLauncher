package de.dakror.launcher.panel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * @author Dakror
 */
public class StatusPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	public JProgressBar progress;
	public JLabel info;
	
	public StatusPanel()
	{
		setSize(1200, 32);
		setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		progress = new JProgressBar();
		progress.setStringPainted(true);
		progress.setValue(0);
		progress.setPreferredSize(new Dimension(500, 22));
		progress.setVisible(false);
		add(progress);
		
		info = new JLabel("");
		info.setFont(new Font("SANDBOX", Font.PLAIN, 16));
		info.setVisible(false);
		add(info);
	}
	
	public void setComponentsVisible(boolean b)
	{
		for (Component c : getComponents())
			c.setVisible(b);
	}
}
