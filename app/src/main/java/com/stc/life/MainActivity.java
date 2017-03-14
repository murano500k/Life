package com.stc.life;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.stc.life.mvp.ControlsView;
import com.stc.life.prefs.SettingsActivity;

public class MainActivity extends AppCompatActivity implements ControlsView {
	private static final String TAG = "MainActivity";
	private static final int REQUEST_SETTINGS = 3444;
	private LifeView lifeView;
	private ProgressBar progress;
	private FloatingActionButton fabPlay;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		//toolbar.setBackgroundColor(SettingsActivity.getBgColor(this));
		setSupportActionBar(toolbar);

		FloatingActionButton fabReset = (FloatingActionButton) findViewById(R.id.fab_reset);
		fabReset.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startNewGame();
			}
		});
		fabPlay = (FloatingActionButton) findViewById(R.id.fab_play);
		fabPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				lifeView.setAutoPlay(!lifeView.isAutoPlay());
			}
		});
		progress=(ProgressBar) findViewById(R.id.progress);
		lifeView =(LifeView) findViewById(R.id.life_view);
		lifeView.setControls(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==REQUEST_SETTINGS) startNewGame();
	}

	public void startNewGame(){
		Log.d(TAG, "startNewGame:");
		lifeView.initPresenter();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()==R.id.settings) {
			startActivityForResult(new Intent(this, SettingsActivity.class), REQUEST_SETTINGS);
			return true;
		}else return super.onOptionsItemSelected(item);
	}

	@Override
	public void updateControls(boolean autoPlay) {
		fabPlay.setImageResource(autoPlay ? android.R.drawable.ic_media_pause : android.R.drawable.ic_media_play);
	}
}
