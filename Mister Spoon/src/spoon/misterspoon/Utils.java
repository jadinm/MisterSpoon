package spoon.misterspoon;


import android.app.Activity;
import android.content.Intent;

public class Utils extends Activity
{
	private static int sTheme;

	public final static int THEME_DEFAULT = R.style.AppTheme;
	public final static int THEME_DARK = R.style.Dark;

	/**
	 * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
	 */
	public static void changeToTheme(Activity activity, int theme)
	{
		sTheme = theme;
		activity.finish();
		activity.startActivity(new Intent(activity, activity.getClass()));

	}

	/** Set the theme of the activity, according to the configuration. */
	public static void onActivityCreateSetTheme(Activity activity)
	{
		switch (sTheme)
		{
		default:
		case THEME_DEFAULT:
			activity.setTheme(R.style.AppTheme);
			break;
		case THEME_DARK:
			activity.setTheme(R.style.Dark);
			break;
		}
	}
}
