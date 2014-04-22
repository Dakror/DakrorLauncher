package de.dakror.launcher.app;

/**
 * @author Dakror
 */
public enum AppStatus
{
	OK("Auf dem neuesten Stand"),
	UPDATE("Aktualisieren"),
	MISSING("Reperatur notwendig!"),
	NOT_INSTALLED("Installieren")
	
	;
	
	private String description;
	
	private AppStatus(String desc)
	{
		description = desc;
	}
	
	public String getDescription()
	{
		return description;
	}
}
