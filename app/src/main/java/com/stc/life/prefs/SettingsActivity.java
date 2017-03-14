package com.stc.life.prefs;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import com.rarepebble.colorpicker.ColorPreference;
import com.stc.life.R;

import static com.stc.life.Const.DEFAULT_CELL_SIZE;
import static com.stc.life.Const.DEFAULT_SEED;
import static com.stc.life.Const.DEFAULT_SLEEP_TIME;

public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
	private static final String TAG = "SettingsActivity";
	public static final String KEY_PREF_CELL_COLOR = "pref_CellColor";
	public static final String KEY_PREF_BG_COLOR = "pref_BgColor";
	public static final String KEY_PREF_SLEEP_TIME = "pref_SleepTime";
	public static final String KEY_PREF_CELL_SIZE = "pref_CellSize";
	public static final String KEY_PREF_SEED = "pref_Seed";








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
			colorPreference.setColor(getCellColor(this));
		}else if(key.equals(KEY_PREF_BG_COLOR)) {
			ColorPreference colorPreference = (ColorPreference) pref;
			colorPreference.setColor(getBgColor(this));
		}else if(key.equals(KEY_PREF_SLEEP_TIME)){
			String val=sharedPreferences.getString(pref.getKey(),null);
			if(val!=null) pref.setSummary(val);
		}else if(key.equals(KEY_PREF_SEED)){
			pref.setSummary(String.valueOf(getSeed(this)));
		} if(key.equals(KEY_PREF_CELL_SIZE)){
			pref.setSummary(String.valueOf(getCellSize(this)));
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
		return PreferenceManager.getDefaultSharedPreferences(context).getInt(KEY_PREF_CELL_COLOR, R.color.default_cell_color);
	}
	public static int getBgColor(Context context){
		return PreferenceManager.getDefaultSharedPreferences(context).getInt(KEY_PREF_BG_COLOR, R.color.default_bg_color);
	}
	public static int getCellSize(Context context){
		return PreferenceManager.getDefaultSharedPreferences(context).getInt(KEY_PREF_CELL_SIZE,
								DEFAULT_CELL_SIZE
		);
	}
	public static int getSleepTime(Context context){
		return Integer.parseInt(
				PreferenceManager.getDefaultSharedPreferences(context).getString(KEY_PREF_SLEEP_TIME,
						DEFAULT_SLEEP_TIME.toString()
		));
	}
	public static int getSeed(Context context){
		return PreferenceManager.getDefaultSharedPreferences(context).getInt(KEY_PREF_SEED,
				DEFAULT_SEED
		);
	}

}
