package de.dakror.launcher.panel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import de.dakror.launcher.DakrorLauncher;
import de.dakror.launcher.Game;
import de.dakror.launcher.app.App;
import de.dakror.launcher.app.AppStatus;
import de.dakror.launcher.settings.UIStateChange.UIState;
import de.dakror.launcher.util.Assistant;

/**
 * @author Dakror
 */
public class AppPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	App app;
	JLabel bg;
	
	public AppPanel(App app)
	{
		this.app = app;
		
		final Polygon p = new Polygon();
		p.addPoint(0, 99);
		p.addPoint(99, 99);
		p.addPoint(99, 0);
		
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
		desc.setBounds(1, 177, 248, 123);
		layeredPane.add(desc);
		
		JPanel descBg = new JPanel();
		descBg.setBounds(1, 177, 248, 123);
		descBg.setBackground(new Color(0, 0, 0, 0.6f));
		layeredPane.add(descBg);
		
		bg = new JLabel("")
		{
			private static final long serialVersionUID = 1L;
			
			@Override
			public void paint(Graphics g)
			{
				g.setClip(Assistant.getClipArea(0, 0, getWidth() + 1, 399));
				super.paint(g);
			}
		};
		bg.setIcon(new ImageIcon(new BufferedImage(248, 399, BufferedImage.TYPE_INT_RGB)));
		bg.setBounds(1, 1, 248, 399);
		layeredPane.add(bg);
		
		final JButton status = new JButton(new AbstractAction()
		{
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				DakrorLauncher.currentLauncher.appDetailPanel.setApp(AppPanel.this.app);
				DakrorLauncher.currentLauncher.slideTo(UIState.APP_DETAIL);
			}
		});
		if (app.getStatus() != AppStatus.NOT_INSTALLED) status.setIcon(new ImageIcon(Game.getImage("status/" + app.getStatus().name().toLowerCase() + ".png").getScaledInstance(99, 99, Image.SCALE_DEFAULT)));
		status.setContentAreaFilled(false);
		status.setBorderPainted(false);
		status.setFocusPainted(false);
		status.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseExited(MouseEvent e)
			{
				setCursor(Cursor.getDefaultCursor());
			}
		});
		status.addMouseMotionListener(new MouseMotionAdapter()
		{
			@Override
			public void mouseMoved(MouseEvent e)
			{
				if (p.contains(e.getPoint())) setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				else setCursor(Cursor.getDefaultCursor());
			}
		});
		status.setVisible(app.getStatus() != AppStatus.NOT_INSTALLED);
		status.setBounds(150, 300, 99, 99);
		layeredPane.add(status);
		
		new Thread()
		{
			@Override
			public void run()
			{
				BufferedImage bi = new BufferedImage(248, 399, BufferedImage.TYPE_INT_ARGB);
				Graphics2D g = (Graphics2D) bi.getGraphics();
				
				Image img = Game.getImage(AppPanel.this.app.getBgFile());
				
				g.drawImage(img, (250 - img.getWidth(null)) / 2, (400 - img.getHeight(null)) / 2, null);
				bg.setIcon(new ImageIcon(bi));
			}
		}.start();
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
			super.paint(g);
			g.setColor(Color.gray);
			((Graphics2D) g).setStroke(new BasicStroke(1));
			((Graphics2D) g).draw(Assistant.getClipArea(0, 0, getWidth(), getHeight()));
		}
	}
}
