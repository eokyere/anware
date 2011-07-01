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

	public static int sleepInterval(Context context) {
		return 1000 / fps(Integer.parseInt(prefs(context).getString(
				"animationSpeed", "1")));
	}
	
	private static SharedPreferences prefs(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

	private static int fps(int key) {
		switch (key) {
		case 0:
			return 1;
		case 2:
			return 5;
		default:
			return 2;
		}
	}
}
