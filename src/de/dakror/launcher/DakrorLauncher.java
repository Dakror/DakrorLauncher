package de.dakror.launcher;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

import aurelienribon.slidinglayout.SLAnimator;
import aurelienribon.slidinglayout.SLConfig;
import aurelienribon.slidinglayout.SLPanel;

import com.jtattoo.plaf.acryl.AcrylLookAndFeel;

import de.dakror.gamesetup.util.swing.TexturedPanel;
import de.dakror.launcher.panel.LoginPanel;
import de.dakror.launcher.util.Assistant;

/**
 * @author Dakror
 */
public class DakrorLauncher extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	public static Font font;
	
	LoginPanel loginPanel = new LoginPanel();
	
	SLPanel slPanel = new SLPanel();
	
	SLConfig loginConfig;
	
	public DakrorLauncher()
	{
		super("Dakror Launcher");
		setSize(1280, 720);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setUndecorated(true);
		setShape(Assistant.getClipArea(0, 0, getWidth(), getHeight()));
		setFont(font);
		try
		{
			setIconImage(ImageIO.read(getClass().getResource("/img/dakror6.png")));
			
			initComponents();
			setVisible(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void initComponents() throws IOException
	{
		TexturedPanel cp = new TexturedPanel(ImageIO.read(getClass().getResource("/img/background.png")));
		cp.setLayout(new BorderLayout());
		cp.add(slPanel, BorderLayout.CENTER);
		
		setContentPane(cp);
		
		JLabel banner = new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("/img/dakrorTrans.png")).getScaledInstance(1000, 250, Image.SCALE_DEFAULT)));
		loginConfig = new SLConfig(slPanel).gap(0, 0).row(250).row(1f).row(1f).row(1f).row(1f).col(1f).place(0, 0, banner).beginGrid(2, 0).row(1f).row(loginPanel.getHeight()).row(1f).col(1f).col(loginPanel.getWidth()).col(1f).place(1, 1, loginPanel).endGrid();
		
		slPanel.setTweenManager(SLAnimator.createTweenManager());
		slPanel.initialize(loginConfig);
	}
	
	public static void main(String[] args)
	{
		SLAnimator.start();
		Properties p = new Properties();
		p.put("logoString", "");
		AcrylLookAndFeel.setTheme(p);
		
		try
		{
			UIManager.setLookAndFeel(new AcrylLookAndFeel());
			font = Font.createFont(Font.TRUETYPE_FONT, DakrorLauncher.class.getResourceAsStream("/SANDBOXB.ttf"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		new DakrorLauncher();
	}
}
