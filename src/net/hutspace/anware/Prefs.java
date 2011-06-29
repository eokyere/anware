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

	public static boolean againstComputer(Context context) {
		return prefs(context).getBoolean("againstComputer", false);
	}

	public static int animationSpeed(Context context) {
		return 1000 / fps(Integer.parseInt(prefs(context).getString(
				"animationSpeed", "1")));
	}
	
	public static boolean chooseStartingPlayer(Context context) {
		return prefs(context).getBoolean("chooseStartingPlyaerAtStart", false);
	}

	public static int difficulty(Context context) {
		return Integer
				.parseInt(prefs(context).getString("animationSpeed", "1"));
	}

	private static SharedPreferences prefs(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
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
