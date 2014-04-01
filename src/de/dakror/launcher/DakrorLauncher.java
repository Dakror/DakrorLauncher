package de.dakror.launcher;

import static aurelienribon.slidinglayout.SLSide.*;
import static de.dakror.launcher.settings.UIStateChange.UIState.*;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
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

import de.dakror.gamesetup.util.swing.TexturedPanel;
import de.dakror.launcher.app.App;
import de.dakror.launcher.app.AppStatus;
import de.dakror.launcher.panel.AppDetailPanel;
import de.dakror.launcher.panel.AppListPanel;
import de.dakror.launcher.panel.AppPanel;
import de.dakror.launcher.panel.LoginPanel;
import de.dakror.launcher.panel.StatusPanel;
import de.dakror.launcher.panel.TitlePanel;
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
	
	LoginPanel loginPanel = new LoginPanel();
	TitlePanel titlePanel = new TitlePanel();
	StatusPanel statusPanel = new StatusPanel();
	
	public AppDetailPanel appDetailPanel = new AppDetailPanel();
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
		setFont(new Font("SANDBOX", Font.PLAIN, 17));
		
		setIconImage(Game.getImage("dakror6.png"));
		
		initComponents();
		initSL();
		setVisible(true);
		
	}
	
	public void initComponents()
	{
		state = LOGIN;
		
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
		
		configs.put(LOGIN, new SLConfig(slPanel).gap(0, 0).row(250).row(1f).row(1f).row(1f).row(1f).col(1f).place(0, 0, banner).beginGrid(2, 0).row(1f).row(loginPanel.getHeight()).row(1f).col(1f).col(loginPanel.getWidth()).col(1f).place(1, 1, loginPanel).endGrid());
		configs.put(MAIN, new SLConfig(slPanel).gap(0, 0).row(90).col(1f).place(0, 0, titlePanel).row(1f).place(1, 0, appListPane).row(32).place(2, 0, statusPanel));
		configs.put(APP_DETAIL, new SLConfig(slPanel).gap(0, 0).row(90).col(1f).place(0, 0, titlePanel).row(1f).place(1, 0, appDetailPanel).row(32).place(2, 0, statusPanel));
		
		slPanel.setTweenManager(SLAnimator.createTweenManager());
		slPanel.initialize(configs.get(state));
	}
	
	public void initSL()
	{
		frames.put(new UIStateChange(LOGIN, MAIN), new SLKeyframe(configs.get(MAIN), 0.6f).setEndSideForOldCmps(LEFT).setDelay(0.2f, appListPane, statusPanel).setDelay(0.5f, titlePanel).setStartSide(TOP, titlePanel).setStartSide(BOTTOM, appListPane, statusPanel));
		
		frames.put(new UIStateChange(MAIN, LOGIN), new SLKeyframe(configs.get(LOGIN), 0.6f).setEndSide(TOP, titlePanel).setEndSide(BOTTOM, appListPane).setDelay(0.2f, loginPanel).setStartSideForNewCmps(LEFT));
		frames.put(new UIStateChange(MAIN, APP_DETAIL), new SLKeyframe(configs.get(APP_DETAIL), 0.6f).setEndSide(TOP, appListPane).setDelay(0.2f, appDetailPanel).setStartSideForNewCmps(BOTTOM));
		
		frames.put(new UIStateChange(APP_DETAIL, MAIN), new SLKeyframe(configs.get(MAIN), 0.6f).setEndSide(BOTTOM, appDetailPanel).setDelay(0.2f, appListPane).setStartSideForNewCmps(TOP));
		frames.put(new UIStateChange(APP_DETAIL, LOGIN), new SLKeyframe(configs.get(LOGIN), 0.6f).setEndSide(TOP, titlePanel).setEndSide(BOTTOM, appDetailPanel).setDelay(0.2f, loginPanel).setStartSideForNewCmps(LEFT));
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
