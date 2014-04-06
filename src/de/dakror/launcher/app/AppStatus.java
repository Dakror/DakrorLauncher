package de.dakror.launcher.app;

/**
 * @author Dakror
 */
public enum AppStatus
{
	OK("Auf dem neuesten Stand"),
	UPDATE("Akualisierungen sind verfügbar"),
	MISSING("Anwendungsdateien sind fehlerhaft"),
	NOT_INSTALLED("Noch nicht installiert")
	
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
