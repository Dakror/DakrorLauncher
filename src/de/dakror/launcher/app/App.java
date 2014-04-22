package de.dakror.launcher.app;

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
	
	public App(int id, String name)
	{
		this.id = id;
		this.name = name;
		status = AppStatus.NOT_INSTALLED;
		desc = "This is the app's description text. You give basic information about the game";
		bgFile = "villagedefense.png";
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
