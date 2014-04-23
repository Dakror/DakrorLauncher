package de.dakror.launcher.app;

import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import de.dakror.gamesetup.util.Helper;

/**
 * @author Dakror
 */
public class AppLoader
{
	public static ArrayList<App> apps = new ArrayList<>();
	
	public static void getApps()
	{
		try
		{
			apps.clear();
			JSONArray a = new JSONArray(Helper.getURLContent(new URL("http://dakror.de/app/get")));
			for (int i = 0; i < a.length(); i++)
			{
				JSONObject o = a.getJSONObject(i);
				apps.add(new App(o.getInt("ID"), o.getString("NAME"), o.getString("IMAGE"), o.getString("DESCRIPTION"), o.getLong("VERSION")));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
