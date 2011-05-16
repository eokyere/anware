package net.hutspace.anware;

import android.content.Context;
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
		return PreferenceManager.getDefaultSharedPreferences(context)
			.getBoolean("computer", false);
	}
}
