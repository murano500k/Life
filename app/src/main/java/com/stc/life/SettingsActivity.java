package com.stc.life;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import com.rarepebble.colorpicker.ColorPreference;

public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
	private static final String TAG = "SettingsActivity";
	public static final String KEY_PREF_CELL_COLOR = "pref_CellColor";
	public static final String KEY_PREF_BG_COLOR = "pref_BgColor";
	public static final String KEY_PREF_SLEEP_TIME = "pref_SleepTime";
	public static final String KEY_PREF_CELL_SIZE = "pref_CellSize";
	public static final String KEY_PREF_SHOW_INFO = "pref_showInfo";
	public static final String KEY_PREF_SEED = "pref_Seed";




	public static final int DEFAULT_CELL_COLOR=R.color.colorPrimary;
	public static final int DEFAULT_BG_COLOR=R.color.colorAccent;
	public static final int DEFAULT_CELL_SIZE=8;
	public static final int DEFAULT_SLEEP_TIME=100;
	public static final int DEFAULT_SEED = 8;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupActionBar();
		addPreferencesFromResource(R.xml.pref_general);
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	private void setupActionBar() {
		android.app.ActionBar actionBar = getActionBar();
		if (actionBar != null) {
			// Show the Up button in the action bar.
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(this);
	}
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		Preference pref = findPreference(key);
		if(key.equals(KEY_PREF_CELL_COLOR)) {
			ColorPreference colorPreference = (ColorPreference) pref;
			colorPreference.setColor(sharedPreferences.getInt(key, DEFAULT_CELL_COLOR));
		}else if(key.equals(KEY_PREF_BG_COLOR)) {
			ColorPreference colorPreference = (ColorPreference) pref;
			colorPreference.setColor(sharedPreferences.getInt(key, DEFAULT_BG_COLOR));
		}else if(key.equals(KEY_PREF_SLEEP_TIME)){

		}
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			setResult(RESULT_OK);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public static int getCellColor(Context context){
		return PreferenceManager.getDefaultSharedPreferences(context).getInt(KEY_PREF_CELL_COLOR, DEFAULT_CELL_COLOR );
	}
	public static int getBgColor(Context context){
		return PreferenceManager.getDefaultSharedPreferences(context).getInt(KEY_PREF_BG_COLOR, DEFAULT_BG_COLOR );
	}
	public static int getCellSize(Context context){
		return Integer.parseInt(
				PreferenceManager.getDefaultSharedPreferences(context).getString(KEY_PREF_CELL_SIZE,
						String.valueOf(
								DEFAULT_CELL_SIZE
						))
		);
	}
	public static int getSleepTime(Context context){

		return Integer.parseInt(
				PreferenceManager.getDefaultSharedPreferences(context).getString(KEY_PREF_SLEEP_TIME,
						String.valueOf(
								DEFAULT_SLEEP_TIME
						))
		);
	}
	public static int getSeed(Context context){
		return Integer.parseInt(
				PreferenceManager.getDefaultSharedPreferences(context).getString(KEY_PREF_SEED,
						String.valueOf(
								DEFAULT_SEED
						))
		);
	}

}
