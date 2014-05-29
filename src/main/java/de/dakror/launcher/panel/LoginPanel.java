package de.dakror.launcher.panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.security.MessageDigest;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

import de.dakror.gamesetup.util.Helper;
import de.dakror.gamesetup.util.swing.JHintTextField;
import de.dakror.launcher.DakrorLauncher;
import de.dakror.launcher.app.App;
import de.dakror.launcher.app.AppLoader;
import de.dakror.launcher.settings.UIStateChange.UIState;
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
		setSize(new Dimension(400, 250));
		setBorder(new AreaBorder(Color.gray));
		setBackground(new Color(0, 0, 0, 0.7f));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		Component horizontalStrut = Box.createHorizontalStrut(14);
		add(horizontalStrut);
		
		Box verticalBox = Box.createVerticalBox();
		add(verticalBox);
		
		JLabel l = new JLabel("Login");
		verticalBox.add(l);
		l.setAlignmentX(Component.CENTER_ALIGNMENT);
		l.setForeground(Color.white);
		l.setFont(l.getFont().deriveFont(30f));
		
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		verticalBox.add(verticalStrut_3);
		
		final JHintTextField usr = new JHintTextField("Benutzername");
		usr.setText(DakrorLauncher.getLastLogin()[0]);
		verticalBox.add(usr);
		usr.setBorder(BorderFactory.createLineBorder(Color.gray));
		usr.foreGround = Color.white;
		usr.setFont(usr.getFont().deriveFont(22f));
		usr.setColumns(20);
		usr.setBackground(Color.black);
		usr.setCaretColor(Color.white);
		
		Component verticalStrut_5 = Box.createVerticalStrut(5);
		verticalBox.add(verticalStrut_5);
		
		final JPasswordField pwd = new JPasswordField();
		verticalBox.add(pwd);
		pwd.setBorder(BorderFactory.createLineBorder(Color.gray));
		pwd.setColumns(20);
		pwd.setFont(pwd.getFont().deriveFont(22f));
		pwd.setForeground(Color.white);
		pwd.setBackground(Color.black);
		pwd.setCaretColor(Color.white);
		
		Component verticalStrut_4 = Box.createVerticalStrut(5);
		verticalBox.add(verticalStrut_4);
		
		JSeparator separator = new JSeparator();
		verticalBox.add(separator);
		
		Component verticalStrut_2 = Box.createVerticalStrut(5);
		verticalBox.add(verticalStrut_2);
		
		JLabel l_1 = new JLabel("Noch kein Konto? Registrieren!");
		verticalBox.add(l_1);
		l_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		l_1.setForeground(Color.white);
		l_1.setOpaque(false);
		l_1.setFont(l_1.getFont().deriveFont(16f));
		Assistant.addLinkBahaviour(l_1, "http://dakror.de/#register");
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		verticalBox.add(verticalStrut_1);
		
		final JButton login = new JButton("         Anmelden        ")
		{
			private static final long serialVersionUID = 1L;
			
			@Override
			public void paint(Graphics g)
			{
				g.setClip(Assistant.getClipArea(0, 0, getWidth() + 1, getHeight() + 1));
				super.paint(g);
			}
		};
		verticalBox.add(login);
		login.setAlignmentX(Component.CENTER_ALIGNMENT);
		login.setHorizontalAlignment(SwingConstants.LEFT);
		login.setEnabled(!DakrorLauncher.internet);
		login.setFocusPainted(false);
		login.setBorder(new AreaBorder(Color.gray));
		login.setFont(login.getFont().deriveFont(35f));
		
		Component verticalStrut = Box.createVerticalStrut(20);
		verticalBox.add(verticalStrut);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		add(horizontalStrut_1);
		login.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					if (!DakrorLauncher.internet)
					{
						if (usr.getText().equals(DakrorLauncher.getLastLogin()[0]))
						{
							DakrorLauncher.userId = Integer.parseInt(DakrorLauncher.getLastLogin()[1]);
							DakrorLauncher.setUsername(DakrorLauncher.getLastLogin()[0]);
							DakrorLauncher.pwdMd5 = "";
							
							for (App app : AppLoader.apps)
								app.updateStatus();
							for (Component c : DakrorLauncher.currentLauncher.alp.getComponents())
								if (c instanceof AppPanel) ((AppPanel) c).onLogin();
							
							DakrorLauncher.currentLauncher.titlePanel.loadLogo();
							DakrorLauncher.currentLauncher.slideTo(UIState.MAIN);
							
							return;
						}
					}
					
					String pw = new String(HexBin.encode(MessageDigest.getInstance("MD5").digest(new String(pwd.getPassword()).getBytes()))).toLowerCase();
					String s = Helper.getURLContent(new URL("http://dakror.de/mp-api/login_noip.php?username=" + usr.getText() + "&password=" + pw)).trim();
					
					if (s.contains("true"))
					{
						String[] p = s.split(":");
						DakrorLauncher.userId = Integer.parseInt(p[1]);
						DakrorLauncher.setUsername(p[2]);
						DakrorLauncher.pwdMd5 = pw;
						DakrorLauncher.setLastLogin();
						pwd.setText("");
						usr.setText(DakrorLauncher.getLastLogin()[0]);
						login.setEnabled(false);
						
						for (App app : AppLoader.apps)
							app.updateStatus();
						for (Component c : DakrorLauncher.currentLauncher.alp.getComponents())
							if (c instanceof AppPanel) ((AppPanel) c).onLogin();
						
						DakrorLauncher.currentLauncher.titlePanel.loadLogo();
						DakrorLauncher.currentLauncher.slideTo(UIState.MAIN);
					}
					else
					{
						pwd.setText("");
						login.setEnabled(false);
						JOptionPane.showMessageDialog(DakrorLauncher.currentLauncher, "Login inkorrekt!", "Login inkorrekt!", JOptionPane.ERROR_MESSAGE);
					}
				}
				catch (Exception e1)
				{
					e1.printStackTrace();
				}
				
			}
		});
		
		KeyAdapter ka = new KeyAdapter()
		{
			@Override
			public void keyReleased(KeyEvent e)
			{
				login.setEnabled(usr.getText().trim().length() > 0 && new String(pwd.getPassword()).trim().length() > 0);
			}
		};
		usr.addKeyListener(ka);
		pwd.addKeyListener(ka);
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.setClip(Assistant.getClipArea(0, 0, getWidth() + 1, getHeight() + 1));
		super.paint(g);
	}
}
