package de.dakror.launcher.panel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import de.dakror.launcher.Game;
import de.dakror.launcher.app.App;
import de.dakror.launcher.app.AppStatus;
import de.dakror.launcher.ui.AreaBorder;
import de.dakror.launcher.util.Assistant;

/**
 * @author Dakror
 */
public class AppPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	App app;
	
	public AppPanel(App app)
	{
		this.app = app;
		
		setPreferredSize(new Dimension(250, 400));
		setOpaque(false);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(0, 1000));
		add(layeredPane);
		
		JPanel title = new JPanel();
		title.setBackground(new Color(0, 0, 0, 0.7f));
		title.setBounds(0, 0, 248, 40);
		layeredPane.add(title);
		title.setLayout(new BoxLayout(title, BoxLayout.Y_AXIS));
		
		Component verticalStrut = Box.createVerticalStrut(10);
		title.add(verticalStrut);
		
		JLabel name = new JLabel(app.getName());
		title.add(name);
		name.setForeground(Color.WHITE);
		name.setFont(new Font("SANDBOX", Font.PLAIN, 19));
		name.setAlignmentX(Component.CENTER_ALIGNMENT);
		name.setHorizontalAlignment(SwingConstants.CENTER);
		
		Component verticalStrut_1 = Box.createVerticalStrut(5);
		title.add(verticalStrut_1);
		
		JSeparator separator = new JSeparator();
		title.add(separator);
		
		final JTextPane desc = new JTextPane();
		desc.setOpaque(false);
		desc.setHighlighter(null);
		desc.setFont(new Font("SANDBOX", Font.ITALIC, 12));
		desc.setForeground(Color.WHITE);
		desc.setText(app.getDescription());
		desc.setEditable(false);
		desc.setBounds(0, 177, 249, 123);
		layeredPane.add(desc);
		
		JPanel descBg = new JPanel();
		descBg.setBounds(0, 177, 249, 123);
		descBg.setBackground(new Color(0, 0, 0, 0.6f));
		layeredPane.add(descBg);
		
		JLabel bg = new JLabel("")
		{
			private static final long serialVersionUID = 1L;
			
			@Override
			public void paint(Graphics g)
			{
				g.setClip(Assistant.getClipArea(0, 0, getWidth() + 1, 399));
				Image img = Game.getImage(AppPanel.this.app.getBgFile());
				g.drawImage(img, (250 - img.getWidth(null)) / 2, (400 - img.getHeight(null)) / 2, null);
				super.paint(g);
			}
		};
		bg.setIcon(null);
		bg.setBounds(1, 1, 248, 399);
		layeredPane.add(bg);
		
		JPanel status = new JPanel()
		{
			private static final long serialVersionUID = 1L;
			
			@Override
			public void paint(Graphics g)
			{
				Image img = Game.getImage("status/" + AppPanel.this.app.getStatus().name().toLowerCase() + ".png");
				g.drawImage(img, 0, 0, 99, 99, null);
			}
		};
		status.setOpaque(false);
		status.setVisible(app.getStatus() != AppStatus.NOT_INSTALLED);
		status.setBounds(150, 300, 99, 99);
		layeredPane.add(status);
	}
	
	@Override
	public void paint(Graphics g)
	{
		if (app.getStatus() != AppStatus.NOT_INSTALLED)
		{
			int rad = 25;
			rad = getHeight() == 400 || getHeight() == 401 || getHeight() == 399 ? 50 : rad; // AppPanel
			Area a = new Area(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, rad, rad));
			a.add(new Area(new Rectangle(getWidth() - rad - 1, 0, rad, rad)));
			a.add(new Area(new Rectangle(0, getHeight() - rad - 1, rad, rad)));
			
			a.add(new Area(new Rectangle(getWidth() - rad - 1, getHeight() - rad - 1, rad, rad)));
			super.paint(g);
			
			g.setColor(Color.gray);
			((Graphics2D) g).setStroke(new BasicStroke(1));
			((Graphics2D) g).draw(a);
		}
		else
		{
			new AreaBorder(Color.gray).paintBorder(null, g, 0, 0, getWidth(), getHeight());
			super.paint(g);
		}
	}
}
