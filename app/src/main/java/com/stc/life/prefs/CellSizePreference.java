package com.stc.life.prefs;

import android.content.Context;
import android.util.AttributeSet;

public class CellSizePreference extends NumberPickerPreference {
	public CellSizePreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		MIN=1;
		MAX=200;
	}
}
