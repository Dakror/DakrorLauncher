package de.dakror.launcher.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import de.dakror.gamesetup.util.Helper;
import de.dakror.launcher.DakrorLauncher;
import de.dakror.launcher.Game;
import de.dakror.launcher.settings.UIStateChange.UIState;

/**
 * @author Dakror
 */
public class TitlePanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	public JLabel userName;
	
	JButton userIcon;
	
	public TitlePanel()
	{
		setSize(1200, 90);
		setLayout(new BorderLayout(0, 0));
		
		Box horizontalBox = Box.createHorizontalBox();
		add(horizontalBox, BorderLayout.EAST);
		
		Box verticalBox = Box.createVerticalBox();
		horizontalBox.add(verticalBox);
		
		userName = new JLabel(DakrorLauncher.username);
		userName.setFont(userName.getFont().deriveFont(22f));
		verticalBox.add(userName);
		userName.setVerticalTextPosition(SwingConstants.TOP);
		userName.setVerticalAlignment(SwingConstants.TOP);
		userName.setAlignmentY(Component.TOP_ALIGNMENT);
		
		Component verticalStrut = Box.createVerticalStrut(50);
		verticalBox.add(verticalStrut);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(5);
		horizontalBox.add(horizontalStrut_1);
		
		userIcon = new JButton(new AbstractAction("")
		{
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String link = JOptionPane.showInputDialog(DakrorLauncher.currentLauncher, "Bitte gib den Link zum gewünschten Bild an.", "Profilbild ändern", JOptionPane.QUESTION_MESSAGE);
				if (link == null) return;
				
				link = link.trim();
				
				if (!link.startsWith("http")) JOptionPane.showMessageDialog(DakrorLauncher.currentLauncher, "Dies ist kein gültiger Link!", "Ungültiger Link!", JOptionPane.ERROR_MESSAGE);
				else
				{
					try
					{
						if (Helper.getURLContent(new URL("http://dakror.de/mp-api/logo?id=" + DakrorLauncher.userId + "&password=" + DakrorLauncher.pwdMd5 + "&newlogo=" + link)).contains("true")) loadLogo();
						else JOptionPane.showMessageDialog(DakrorLauncher.currentLauncher, "Profilbild konnte nicht geändert werden!", "Fehler!", JOptionPane.ERROR_MESSAGE);
					}
					catch (MalformedURLException e1)
					{
						e1.printStackTrace();
					}
				}
			}
		});
		horizontalBox.add(userIcon);
		userIcon.setOpaque(false);
		userIcon.setBorder(new LineBorder(new Color(0, 0, 0)));
		userIcon.setFocusPainted(false);
		userIcon.setContentAreaFilled(false);
		userIcon.setPreferredSize(new Dimension(80, 80));
		
		userIcon.setIcon(new ImageIcon(Game.getImage("Monster.png").getScaledInstance(userIcon.getPreferredSize().width, userIcon.getPreferredSize().height, Image.SCALE_SMOOTH)));
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(5);
		horizontalBox.add(horizontalStrut_2);
		
		Box horizontalBox_1 = Box.createHorizontalBox();
		add(horizontalBox_1, BorderLayout.WEST);
		
		Component horizontalStrut = Box.createHorizontalStrut(5);
		horizontalBox_1.add(horizontalStrut);
		
		Box verticalBox_1 = Box.createVerticalBox();
		horizontalBox_1.add(verticalBox_1);
		
		JButton logout = new JButton("");
		logout.setToolTipText("Abmelden");
		logout.setFocusPainted(false);
		logout.setContentAreaFilled(false);
		verticalBox_1.add(logout);
		logout.setMaximumSize(new Dimension(40, 40));
		logout.setMinimumSize(new Dimension(40, 40));
		logout.setSize(new Dimension(40, 40));
		logout.setPreferredSize(new Dimension(40, 40));
		logout.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				DakrorLauncher.userId = 0;
				DakrorLauncher.setUsername(null);
				DakrorLauncher.currentLauncher.slideTo(UIState.LOGIN);
			}
		});
		
		logout.setIcon(new ImageIcon(Game.getImage("Logout.png").getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
	}
	
	public void loadLogo()
	{
		new Thread()
		{
			@Override
			public void run()
			{
				try
				{
					userIcon.setIcon(new ImageIcon(ImageIO.read(new URL(Helper.getURLContent(new URL("http://dakror.de/mp-api/logo?id=" + DakrorLauncher.userId)))).getScaledInstance(userIcon.getPreferredSize().width, userIcon.getPreferredSize().height, Image.SCALE_SMOOTH)));
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}.start();
	}
}
