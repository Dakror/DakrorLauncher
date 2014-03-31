package de.dakror.launcher;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;

import aurelienribon.slidinglayout.SLAnimator;
import aurelienribon.slidinglayout.SLConfig;
import aurelienribon.slidinglayout.SLKeyframe;
import aurelienribon.slidinglayout.SLPanel;
import aurelienribon.slidinglayout.SLSide;

import com.jtattoo.plaf.acryl.AcrylLookAndFeel;

import de.dakror.gamesetup.util.swing.TexturedPanel;
import de.dakror.launcher.app.App;
import de.dakror.launcher.app.AppStatus;
import de.dakror.launcher.panel.AppDetailPanel;
import de.dakror.launcher.panel.AppListPanel;
import de.dakror.launcher.panel.AppPanel;
import de.dakror.launcher.panel.LoginPanel;
import de.dakror.launcher.panel.StatusPanel;
import de.dakror.launcher.panel.TitlePanel;
import de.dakror.launcher.util.Assistant;

/**
 * @author Dakror
 */
public class DakrorLauncher extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	public static DakrorLauncher currentLauncher;
	public static SLPanel slPanel = new SLPanel();
	
	SLConfig loginConfig, mainConfig, appConfig;
	
	LoginPanel loginPanel = new LoginPanel();
	TitlePanel titlePanel = new TitlePanel();
	StatusPanel statusPanel = new StatusPanel();
	
	public AppDetailPanel appDetailPanel = new AppDetailPanel();
	public JScrollPane appListPane;
	
	public SLKeyframe login2main;
	public SLKeyframe main2login;
	public SLKeyframe main2app;
	
	public DakrorLauncher()
	{
		super("Dakror Launcher");
		currentLauncher = this;
		
		setSize(1280, 720);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setUndecorated(true);
		setShape(Assistant.getClipArea(0, 0, getWidth(), getHeight()));
		setFont(new Font("SANDBOX", Font.PLAIN, 17));
		
		setIconImage(Game.getImage("dakror6.png"));
		
		initComponents();
		initSL();
		setVisible(true);
		
	}
	
	public void initComponents()
	{
		TexturedPanel cp = new TexturedPanel(Game.getImage("background.png"));
		cp.setLayout(new BorderLayout());
		cp.add(slPanel, BorderLayout.CENTER);
		
		AppListPanel alp = new AppListPanel();
		
		alp.add(new AppPanel(new App(1, "Village Defense")));
		alp.add(new AppPanel(new App(1, "Village Defense").setStatus(AppStatus.MISSING)));
		alp.add(new AppPanel(new App(1, "Village Defense").setStatus(AppStatus.OK)));
		alp.add(new AppPanel(new App(1, "Village Defense").setStatus(AppStatus.NOT_INSTALLED)));
		alp.add(new AppPanel(new App(1, "Village Defense")));
		alp.add(new AppPanel(new App(1, "Village Defense")));
		
		appListPane = new JScrollPane(alp);
		appListPane.setBorder(BorderFactory.createEmptyBorder());
		appListPane.setOpaque(false);
		appListPane.getViewport().setOpaque(false);
		
		setContentPane(cp);
		
		JLabel banner = new JLabel(new ImageIcon(Game.getImage("dakrorTrans.png").getScaledInstance(1000, 250, Image.SCALE_DEFAULT)));
		
		loginConfig = new SLConfig(slPanel).gap(0, 0).row(250).row(1f).row(1f).row(1f).row(1f).col(1f).place(0, 0, banner).beginGrid(2, 0).row(1f).row(loginPanel.getHeight()).row(1f).col(1f).col(loginPanel.getWidth()).col(1f).place(1, 1, loginPanel).endGrid();
		mainConfig = new SLConfig(slPanel).gap(0, 0).row(90).col(1f).place(0, 0, titlePanel).row(1f).place(1, 0, appListPane).row(32).place(2, 0, statusPanel);
		appConfig = new SLConfig(slPanel).gap(0, 0).row(90).col(1f).place(0, 0, titlePanel).row(1f).place(1, 0, appDetailPanel).row(32).place(2, 0, statusPanel);
		
		slPanel.setTweenManager(SLAnimator.createTweenManager());
		slPanel.initialize(loginConfig);
	}
	
	public void initSL()
	{
		login2main = new SLKeyframe(mainConfig, 0.6f).setEndSideForOldCmps(SLSide.LEFT).setDelay(0.2f, appListPane, statusPanel).setDelay(0.5f, titlePanel).setStartSide(SLSide.TOP, titlePanel).setStartSide(SLSide.BOTTOM, appListPane, statusPanel);
		main2login = new SLKeyframe(loginConfig, 0.6f).setEndSide(SLSide.TOP, titlePanel).setEndSide(SLSide.BOTTOM, appListPane).setDelay(0.2f, loginPanel).setStartSideForNewCmps(SLSide.LEFT);
		main2app = new SLKeyframe(appConfig, 0.6f).setEndSide(SLSide.TOP, appListPane).setDelay(0.2f, appDetailPanel).setStartSideForNewCmps(SLSide.BOTTOM);
	}
	
	public static void main(String[] args)
	{
		SLAnimator.start();
		new Game();
		Properties p = new Properties();
		p.put("logoString", "");
		AcrylLookAndFeel.setTheme(p);
		
		try
		{
			ToolTipManager.sharedInstance().setInitialDelay(0);
			UIManager.setLookAndFeel(new AcrylLookAndFeel());
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.TRUETYPE_FONT, DakrorLauncher.class.getResourceAsStream("/SANDBOXB.ttf")));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		new DakrorLauncher();
	}
}
