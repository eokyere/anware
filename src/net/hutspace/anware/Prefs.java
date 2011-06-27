package net.hutspace.anware;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class Prefs extends PreferenceActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
	}
	
//	public static boolean againstComputer(Context context) {
//		return PreferenceManager.getDefaultSharedPreferences(context)
//			.getBoolean("computer", false);
//	}
	
	public static int animationSpeed(Context context) {
		final SharedPreferences prefs = PreferenceManager.
			getDefaultSharedPreferences(context);
		return 1000/fps(Integer.parseInt(prefs.getString("animationSpeed", "1")));
	}
	
	private static int fps(int key) {
		switch (key) {
			case 0:
				return 10;
			case 2:
				return 100;
			default:
				return 50;
		}
	}
}
