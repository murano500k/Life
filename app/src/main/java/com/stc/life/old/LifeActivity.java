/*
package com.stc.life.old;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.stc.life.R;
import com.stc.life.prefs.SettingsActivity;
import com.stc.life.old.model.WorldView;

import java.util.ArrayList;

import static com.stc.life.Const.EXTRA_AUTO_PLAY;

public class LifeActivity extends AppCompatActivity implements LifeCallback{
	private static final int REQUEST_CHANGE_SETTINGS = 6432;
	WorldView world;
	boolean autoPlaying;
	Runnable mTickListener;
	FloatingActionButton fabPlay;
	private Handler mHandler = new Handler(Looper.getMainLooper());
	private static final String TAG = "MainActivity";
	private FloatingActionButton fabReset;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_life);
		Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
		toolbar.showOverflowMenu();
		setSupportActionBar(toolbar);
		fabPlay = (FloatingActionButton) findViewById(R.id.fab_play);
		fabPlay.setImageResource(android.R.drawable.ic_media_play);

		fabPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//world.iterate();
				toggleAutoPlay(view);
			}
		});
		fabReset=(FloatingActionButton) findViewById(R.id.fab_reset);
		fabReset.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				world.reset();
			}
		});
		init(savedInstanceState);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==REQUEST_CHANGE_SETTINGS) world.reset();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.settings :
				startActivityForResult(new Intent(this, SettingsActivity.class), REQUEST_CHANGE_SETTINGS);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}

	}


	@Override
	protected void onSaveInstanceState(Bundle outState) {
		//if(world!=null) outState.putIntegerArrayList(EXTRA_CELL_STATES, world.getState());
		outState.putInt("EXTRA_AUTO_PLAY", autoPlaying ? 1 : 0);
		super.onSaveInstanceState(outState);

	}

	private void toggleAutoPlay(View v) {
		int icon = android.R.drawable.ic_media_play;
		autoPlaying=!autoPlaying;
		Log.d(TAG, "toggleAutoPlay: "+autoPlaying);
		if(autoPlaying){
			icon=android.R.drawable.ic_media_pause;
			start();
		}else {
			stop();
		}
		fabPlay.setImageResource(icon);
	}

	private void start() {
		Log.d(TAG, "start: ");
		if(mTickListener!=null) mHandler.removeCallbacks(mTickListener);
		if (mTickListener == null) {
			mTickListener = new Runnable() {
				@Override
				public void run() {
					world.iterate();
				}
			};
		}
		mTickListener.run();
	}
	private void stop(){
		Log.d(TAG, "stop: ");
		if(mTickListener!=null) mHandler.removeCallbacks(mTickListener);
		mTickListener=null;
	}


	private void init(Bundle savedState){
		world=(WorldView)findViewById(R.id.world_view);
		ArrayList<Integer> savedCellStates=null;
		autoPlaying=false;
		if(savedState!=null){
			autoPlaying=savedState.getInt(EXTRA_AUTO_PLAY, 0)==1;
		}
		world.setCallback(this);
		if (mTickListener == null) {
			mTickListener = new Runnable() {
				@Override
				public void run() {
					world.iterate();
				}
			};
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		if(autoPlaying)toggleAutoPlay(fabPlay);
	}

	@Override
	public void worldIsDrawn() {
		//Log.d(TAG, "callback. auto: "+autoPlaying);
		if (autoPlaying) {
			mHandler.postDelayed(mTickListener,SettingsActivity.getSleepTime(this));
		}
	}
}
*/
