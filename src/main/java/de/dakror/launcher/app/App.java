package de.dakror.launcher.app;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

import de.dakror.gamesetup.util.Helper;
import de.dakror.launcher.DakrorLauncher;
import de.dakror.launcher.settings.CFG;

/**
 * @author Dakror
 */
public class App
{
	AppStatus status;
	String name;
	String desc;
	String bgFile;
	int id;
	long onlineVersion;
	
	public App(int id, String name, String bgFile, String desc, long onlineVersion)
	{
		this.id = id;
		this.name = name;
		this.bgFile = bgFile;
		this.desc = desc;
		this.onlineVersion = onlineVersion;
		
		updateStatus();
	}
	
	public void updateStatus()
	{
		File dir = new File(CFG.DIR, DakrorLauncher.userId + "/apps/" + name.replace(" ", "-"));
		File version = new File(dir, name.replace(" ", "-") + ".version");
		if (!dir.exists()) status = AppStatus.NOT_INSTALLED;
		else
		{
			try
			{
				long v = Long.parseLong(Helper.getFileContent(version).trim());
				if (v < onlineVersion) status = AppStatus.UPDATE;
				else status = AppStatus.OK;
			}
			catch (Exception e)
			{
				status = AppStatus.NOT_INSTALLED;
			}
		}
	}
	
	public void cacheBgFile()
	{
		try
		{
			File cache = new File(CFG.DIR, DakrorLauncher.userId + "/apps/" + name.replace(" ", "-"));
			cache.mkdirs();
			
			File f = new File(cache, bgFile);
			
			if (!f.exists() && DakrorLauncher.internet) Helper.copyInputStream(new URL("http://dakror.de/img/app/" + bgFile).openStream(), new FileOutputStream(f));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public AppStatus getStatus()
	{
		return status;
	}
	
	public App setStatus(AppStatus status)
	{
		this.status = status;
		return this;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getDescription()
	{
		return desc;
	}
	
	public String getBgFile()
	{
		return bgFile;
	}
	
	public int getId()
	{
		return id;
	}
}
