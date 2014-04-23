package de.dakror.launcher.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

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
	
	public TitlePanel()
	{
		setSize(1200, 90);
		setLayout(new BorderLayout(0, 0));
		
		Box horizontalBox = Box.createHorizontalBox();
		add(horizontalBox, BorderLayout.EAST);
		
		Box verticalBox = Box.createVerticalBox();
		horizontalBox.add(verticalBox);
		
		userName = new JLabel(DakrorLauncher.username);
		userName.setFont(new Font("SANDBOX", Font.PLAIN, 22));
		verticalBox.add(userName);
		userName.setVerticalTextPosition(SwingConstants.TOP);
		userName.setVerticalAlignment(SwingConstants.TOP);
		userName.setAlignmentY(Component.TOP_ALIGNMENT);
		
		Component verticalStrut = Box.createVerticalStrut(50);
		verticalBox.add(verticalStrut);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(5);
		horizontalBox.add(horizontalStrut_1);
		
		JButton userIcon = new JButton("");
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
}
