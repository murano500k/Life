package com.stc.life.prefs;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by artem on 3/14/17.
 */

public class SeedPreference extends NumberPickerPreference {
	public SeedPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		MIN=0;
		MAX=1000;
	}
}
