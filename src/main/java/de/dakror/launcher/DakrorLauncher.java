package de.dakror.launcher;

import static aurelienribon.slidinglayout.SLSide.*;
import static de.dakror.launcher.settings.UIStateChange.UIState.*;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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
import aurelienribon.slidinglayout.SLKeyframe.Callback;
import aurelienribon.slidinglayout.SLPanel;

import com.jtattoo.plaf.acryl.AcrylLookAndFeel;

import de.dakror.gamesetup.util.Helper;
import de.dakror.gamesetup.util.swing.TexturedPanel;
import de.dakror.launcher.app.App;
import de.dakror.launcher.app.AppLoader;
import de.dakror.launcher.panel.AppListPanel;
import de.dakror.launcher.panel.AppPanel;
import de.dakror.launcher.panel.LoginPanel;
import de.dakror.launcher.panel.StatusPanel;
import de.dakror.launcher.panel.TitlePanel;
import de.dakror.launcher.settings.CFG;
import de.dakror.launcher.settings.UIStateChange;
import de.dakror.launcher.settings.UIStateChange.UIState;
import de.dakror.launcher.util.Assistant;

/**
 * @author Dakror
 */
public class DakrorLauncher extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	public static DakrorLauncher currentLauncher;
	public static SLPanel slPanel = new SLPanel();
	
	public static int userId;
	public static String username;
	public static String pwdMd5;
	public TitlePanel titlePanel = new TitlePanel();
	
	LoginPanel loginPanel = new LoginPanel();
	StatusPanel statusPanel = new StatusPanel();
	
	public AppListPanel alp;
	
	public JScrollPane appListPane;
	
	public UIState state;
	
	HashMap<UIState, SLConfig> configs = new HashMap<>();
	HashMap<UIStateChange, SLKeyframe> frames = new HashMap<>();
	
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
		
		setIconImage(Game.getImage("dakror6.png"));
		
		AppLoader.getApps();
		new DownloadManager();
		
		initComponents();
		initSL();
		setVisible(true);
	}
	
	public static String getLastLogin()
	{
		File f = new File(CFG.DIR, "lastlogin");
		if (f.exists()) return Helper.getFileContent(f).trim();
		return "";
	}
	
	public static void setLastLogin()
	{
		try
		{
			File f = new File(CFG.DIR, "lastlogin");
			f.createNewFile();
			Helper.setFileContent(f, username);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void initComponents()
	{
		state = LOGIN;
		
		TexturedPanel cp = new TexturedPanel(Game.getImage("background.png"));
		cp.setLayout(new BorderLayout());
		cp.add(slPanel, BorderLayout.CENTER);
		
		alp = new AppListPanel();
		
		for (App app : AppLoader.apps)
			alp.add(new AppPanel(app));
		
		appListPane = new JScrollPane(alp);
		appListPane.setBorder(BorderFactory.createEmptyBorder());
		appListPane.setOpaque(false);
		appListPane.getViewport().setOpaque(false);
		
		setContentPane(cp);
		
		JLabel banner = new JLabel(new ImageIcon(Game.getImage("dakrorTrans.png").getScaledInstance(1000, 250, Image.SCALE_DEFAULT)));
		
		configs.put(LOGIN, new SLConfig(slPanel).gap(0, 0).row(250).row(1f).row(1f).row(1f).row(1f).col(1f).place(0, 0, banner).beginGrid(2, 0).row(1f).row(loginPanel.getHeight()).row(1f).col(1f).col(loginPanel.getWidth()).col(1f).place(1, 1, loginPanel).endGrid());
		configs.put(MAIN, new SLConfig(slPanel).gap(0, 0).row(90).col(1f).place(0, 0, titlePanel).row(1f).place(1, 0, appListPane).row(32).place(2, 0, statusPanel));
		
		slPanel.setTweenManager(SLAnimator.createTweenManager());
		slPanel.initialize(configs.get(state));
	}
	
	public void initSL()
	{
		frames.put(new UIStateChange(LOGIN, MAIN), new SLKeyframe(configs.get(MAIN), 0.6f).setEndSideForOldCmps(LEFT).setDelay(0.2f, appListPane, statusPanel).setDelay(0.5f, titlePanel).setStartSide(TOP, titlePanel).setStartSide(BOTTOM, appListPane, statusPanel));
		
		frames.put(new UIStateChange(MAIN, LOGIN), new SLKeyframe(configs.get(LOGIN), 0.6f).setEndSide(TOP, titlePanel).setEndSide(BOTTOM, appListPane).setDelay(0.2f, loginPanel).setStartSideForNewCmps(LEFT));
	}
	
	public void slideTo(final UIState newState)
	{
		slPanel.createTransition().push(frames.get(new UIStateChange(state, newState)).setCallback(new Callback()
		{
			@Override
			public void done()
			{
				state = newState;
			}
		})).play();
	}
	
	public static void setUsername(String s)
	{
		username = s;
		currentLauncher.titlePanel.userName.setText(s);
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
