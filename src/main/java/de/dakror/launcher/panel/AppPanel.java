/*******************************************************************************
 * Copyright 2015 Maximilian Stark | Dakror <mail@dakror.de>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/


package de.dakror.launcher.panel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
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
import java.io.File;
import java.io.IOException;

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
import de.dakror.launcher.DownloadManager;
import de.dakror.launcher.DownloadManager.Download;
import de.dakror.launcher.Game;
import de.dakror.launcher.app.App;
import de.dakror.launcher.app.AppStatus;
import de.dakror.launcher.settings.CFG;
import de.dakror.launcher.util.Assistant;

/**
 * @author Dakror
 */
public class AppPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static final String[] APP_STATES = { "in development", "released", "broken", "abandoned" };
	
	public App app;
	JLabel bg;
	JButton status;
	
	boolean statusHovered;
	
	MouseAdapter launch = new MouseAdapter() {
		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
				try {
					Runtime.getRuntime().exec(new String[] {
																				"java" + (System.getProperty("os.name").toLowerCase().contains("win") ? "w" : ""),
																				"-jar",
																				new File(CFG.DIR, DakrorLauncher.userId + "/apps/" + app.getName().replace(" ", "-") + "/" + app.getName().replace(" ", "-") + ".jar").getPath().replace(	"\\",
																																																																																																	"/"),
																				"-un=" + DakrorLauncher.username, "-upwd=" + DakrorLauncher.pwdMd5 });
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	};
	
	JLayeredPane layeredPane;
	
	public AppPanel(App app) {
		this.app = app;
		
		final Polygon p = new Polygon();
		p.addPoint(0, 99);
		p.addPoint(99, 99);
		p.addPoint(99, 0);
		
		setPreferredSize(new Dimension(250, 400));
		setOpaque(false);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		if (app.getStatus() == AppStatus.OK) addMouseListener(launch);
		
		layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(0, 1000));
		
		add(layeredPane);
		
		JPanel title = new JPanel() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void paint(Graphics g) {
				g.setClip(Assistant.getClipArea(0, 0, getWidth() + 1, 399));
				super.paint(g);
			}
		};;
		title.setBackground(new Color(0, 0, 0, 0.7f));
		title.setBounds(0, 0, 248, 40);
		layeredPane.add(title);
		title.setLayout(new BoxLayout(title, BoxLayout.Y_AXIS));
		
		Component verticalStrut = Box.createVerticalStrut(10);
		title.add(verticalStrut);
		
		JLabel name = new JLabel(app.getName());
		title.add(name);
		name.setForeground(Color.WHITE);
		name.setFont(name.getFont().deriveFont(19f));
		name.setAlignmentX(Component.CENTER_ALIGNMENT);
		name.setHorizontalAlignment(SwingConstants.CENTER);
		
		Component verticalStrut_1 = Box.createVerticalStrut(5);
		title.add(verticalStrut_1);
		
		JSeparator separator = new JSeparator();
		title.add(separator);
		
		final JTextPane desc = new JTextPane();
		desc.setOpaque(false);
		desc.setHighlighter(null);
		desc.setFont(name.getFont().deriveFont(13f));
		desc.setForeground(Color.WHITE);
		
		String add = "\n\n";
		String date = app.getDate() + "";
		if (app.getState() == 1 || app.getState() == 2) add += "Finish-date: " + date.substring(4) + "." + date.substring(0, 4) + "\n";
		add += "This game is " + APP_STATES[app.getState()];
		
		desc.setText(app.getDescription() + add);
		desc.setEditable(false);
		desc.setBounds(1, 155, 248, 145);
		layeredPane.add(desc);
		
		JPanel descBg = new JPanel();
		descBg.setBounds(1, 155, 248, 145);
		descBg.setBackground(new Color(0, 0, 0, 0.6f));
		layeredPane.add(descBg);
		
		bg = new JLabel("") {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void paint(Graphics g) {
				g.setClip(Assistant.getClipArea(0, 0, getWidth() + 1, 399));
				super.paint(g);
			}
		};
		bg.setBackground(new Color(0, 0, 0, 0.6f));
		bg.setBounds(1, 1, 248, 399);
		layeredPane.add(bg);
		
		status = new JButton(new AbstractAction() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (AppPanel.this.app.getStatus() != AppStatus.OK && AppPanel.this.app.getStatus() != AppStatus.DOWNLOADING) {
					DownloadManager.manager.addDownload(new Download(AppPanel.this.app));
					AppPanel.this.app.setStatus(AppStatus.DOWNLOADING);
				}
			}
			
		}) {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void paint(Graphics g) {
				
				if (AppPanel.this.app.getStatus() == AppStatus.NOT_INSTALLED) {
					if (statusHovered) super.paint(g);
				} else super.paint(g);
			}
		};
		
		status.setIcon(new ImageIcon(Game.getImage("status/" + app.getStatus().name().toLowerCase() + ".png").getScaledInstance(99, 99, Image.SCALE_DEFAULT)));
		status.setToolTipText(app.getStatus().getDescription());
		status.setContentAreaFilled(false);
		status.setBorderPainted(false);
		status.setFocusPainted(false);
		status.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(Cursor.getDefaultCursor());
				statusHovered = false;
				status.repaint();
			}
		});
		status.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (p.contains(e.getPoint())) setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				else setCursor(Cursor.getDefaultCursor());
				
				statusHovered = p.contains(e.getPoint());
				status.repaint();
			}
		});
		status.setVisible(true);
		status.setBounds(150, 300, 99, 99);
		layeredPane.add(status);
		
		if (app.getStatus() == AppStatus.OK) {
			for (Component c : layeredPane.getComponents()) {
				c.addMouseListener(launch);
				c.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		}
	}
	
	public void onLogin() {
		new Thread() {
			@Override
			public void run() {
				app.cacheBgFile();
				
				BufferedImage bi = new BufferedImage(248, 399, BufferedImage.TYPE_INT_ARGB);
				Graphics2D g = (Graphics2D) bi.getGraphics();
				Image img = Game.getImage(new File(CFG.DIR, DakrorLauncher.userId + "/apps/" + app.getName() + "/" + app.getBgFile()).getPath().replace("\\", "/"));
				
				int width = img.getWidth(null) / 4 * 3;
				int height = img.getHeight(null) / 4 * 3;
				
				g.drawImage(img, (250 - width) / 2, (400 - height) / 2, width, height, null);
				bg.setIcon(new ImageIcon(bi));
			}
		}.start();
		
		updateStatus();
	}
	
	public void updateStatus() {
		app.updateStatus();
		status.setIcon(new ImageIcon(Game.getImage("status/" + app.getStatus().name().toLowerCase() + ".png").getScaledInstance(99, 99, Image.SCALE_DEFAULT)));
		status.setToolTipText(app.getStatus().getDescription());
		
		if (app.getStatus() == AppStatus.OK) {
			for (Component c : layeredPane.getComponents()) {
				c.addMouseListener(launch);
				c.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		} else {
			for (Component c : layeredPane.getComponents()) {
				c.removeMouseListener(launch);
				c.setCursor(Cursor.getDefaultCursor());
			}
		}
		
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		if (app.getStatus() != AppStatus.NOT_INSTALLED) {
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
		} else {
			super.paint(g);
			g.setColor(Color.gray);
			((Graphics2D) g).setStroke(new BasicStroke(1));
			((Graphics2D) g).draw(Assistant.getClipArea(0, 0, getWidth(), getHeight()));
		}
	}
}
