package com.stc.life;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.stc.life.mvp.ControlsView;
import com.stc.life.mvp.Presenter;
import com.stc.life.mvp.View;
import com.stc.life.prefs.SettingsActivity;

import java.util.List;

/**
 * Created by artem on 3/13/17.
 */

public class LifeView extends android.view.View implements View {
	private static final String TAG = "LifeView";
	private int w, h;
	private Presenter presenter;
	private Paint paintCell;
	private boolean autoPlay;
	private List<Cell> cells;
	private ControlsView controls;

	public LifeView(Context context) {
		super(context);
	}
	public LifeView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}
	public LifeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		Log.d(TAG, "onMeasure: w="+w+"h="+h+" isInit="+autoPlay);
		w = MeasureSpec.getSize(widthMeasureSpec);
		h = MeasureSpec.getSize(heightMeasureSpec);
		setAutoPlay(false);
		this.setMeasuredDimension(w, h);
	}

	@Override
	public void setControls(ControlsView controls) {
		this.controls=controls;
	}

	@Override
	public void initPresenter() {
		cells=null;
		setAutoPlay(false);
		new LifePresenter(LifeView.this,
				SettingsActivity.getSeed(getContext()),
				SettingsActivity.getSleepTime(getContext())
		);
		int color=SettingsActivity.getCellColor(getContext());
		Log.w(TAG, "initPresenter color="+color);
		paintCell =new Paint();
		paintCell.setColor(color);
		paintCell.setTextSize(100);
		setBackgroundColor(SettingsActivity.getBgColor(getContext()));
		setAutoPlay(false);
		presenter.observeCells(getRowCount(),getColumnCount(),getCellSize());

	}
	@Override
	protected void onDraw(final Canvas canvas) {
		super.onDraw(canvas);
		if(presenter==null)initPresenter();
		else {
			if(autoPlay) presenter.observeCells(getRowCount(),getColumnCount(),getCellSize());
			if(cells!=null && cells.size()>0){
				for(Cell cell: cells) canvas.drawRect(cell.getRect(), paintCell);
			}else {
				Log.e(TAG, "onDraw: no cells" );
				Paint p=new Paint();
				p.setColor(getResources().getColor(R.color.disabled_gray));
				canvas.drawText("Click play button to start",100,100,p);
			}
		}



	}

	@Override
	public void drawCells(List<Cell> cells) {
		if(cells.equals(this.cells)) {
			Log.d(TAG, "drawCells: finish");
			setAutoPlay(false);
		}
		this.cells=cells;
		invalidate();
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
	}

	@Override
	public boolean isAutoPlay() {
		return autoPlay;
	}

	@Override
	public void setAutoPlay(boolean autoPlay) {
		this.autoPlay=autoPlay;
		if (controls != null) controls.updateControls(autoPlay);
		if(autoPlay) presenter.observeCells(getRowCount(),getColumnCount(),getCellSize());
	}

	@Override
	public Presenter getPresenter() {
		return presenter;
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction()==MotionEvent.ACTION_DOWN) {
			presenter.cellIsClicked(getRowCount(),getColumnCount(),getCellSize(),(int)event.getX(), (int)event.getY());
			return true;
		}
		return false;
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter=presenter;
	}




	@Override
	public int getRowCount() {
		return h/SettingsActivity.getCellSize(getContext());
	}

	@Override
	public int getColumnCount() {
		return w/SettingsActivity.getCellSize(getContext());
	}

	@Override
	public int getCellSize() {
		return SettingsActivity.getCellSize(getContext());
	}






}
