package de.dakror.launcher.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import de.dakror.gamesetup.util.swing.JHintTextField;
import de.dakror.launcher.DakrorLauncher;
import de.dakror.launcher.ui.AreaBorder;
import de.dakror.launcher.util.Assistant;

/**
 * @author Dakror
 */
public class LoginPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	public LoginPanel()
	{
		setSize(400, 250);
		setBorder(new AreaBorder(Color.gray));
		setBackground(new Color(0, 0, 0, 0.7f));
		
		JLabel l = new JLabel("Login");
		l.setForeground(Color.white);
		l.setFont(DakrorLauncher.font.deriveFont(Font.PLAIN, 30f));
		add(l);
		
		JHintTextField usr = new JHintTextField("Benutzername");
		usr.setBorder(BorderFactory.createLineBorder(Color.gray));
		usr.foreGround = Color.white;
		usr.setFont(DakrorLauncher.font.deriveFont(Font.PLAIN, 22f));
		usr.setColumns(20);
		usr.setBackground(Color.black);
		usr.setCaretColor(Color.white);
		add(usr);
		
		final JPasswordField pwd = new JPasswordField("Passwort");
		pwd.setBorder(BorderFactory.createLineBorder(Color.gray));
		pwd.setColumns(20);
		pwd.setFont(DakrorLauncher.font.deriveFont(Font.PLAIN, 22f));
		pwd.setForeground(Color.white);
		pwd.setBackground(Color.black);
		pwd.setCaretColor(Color.white);
		pwd.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusGained(FocusEvent e)
			{
				if (new String(pwd.getPassword()).equals("Passwort")) pwd.setText("");
			}
		});
		add(pwd);
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.setClip(Assistant.getClipArea(0, 0, getWidth() + 1, getHeight() + 1));
		super.paint(g);
	}
}
