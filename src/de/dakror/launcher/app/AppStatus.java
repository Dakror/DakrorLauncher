package de.dakror.launcher.app;

/**
 * @author Dakror
 */
public enum AppStatus
{
	OK("", "Auf dem neuesten Stand"),
	UPDATE("Aktualisierung", "Aktualisieren"),
	MISSING("Reparatur", "Reparatur notwendig!"),
	NOT_INSTALLED("Installation", "Installieren")
	
	;
	
	private String name;
	private String description;
	
	private AppStatus(String name, String desc)
	{
		this.name = name;
		description = desc;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getDescription()
	{
		return description;
	}
}
