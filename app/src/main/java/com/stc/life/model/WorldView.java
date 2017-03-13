package com.stc.life.model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.stc.life.LifeCallback;
import com.stc.life.SettingsActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.stc.life.Const.CREATION_RULE;
import static com.stc.life.Const.SURVIVAL_RULE;

/**
 * Created by artem on 3/10/17.
 */

public class
WorldView extends View {
	public int cellSize;
	private Paint paintLive;
	private static final String TAG = "WorldView";
	private int h, w;
	private LifeCallback callback;
	private WorldModel model;

	public WorldView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		initVars();
	}

	public WorldView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initVars();
	}

	public WorldView(Context context) {
		super(context);
		initVars();

	}
	private void initVars(){
		paintLive=new Paint();
		this.cellSize= SettingsActivity.getCellSize(getContext());
		paintLive.setColor(SettingsActivity.getCellColor(getContext()));
		setBackgroundColor(SettingsActivity.getBgColor(getContext()));
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		Log.d(TAG, "onMeasure: w1="+widthMeasureSpec);
		Log.d(TAG, "onMeasure: h1="+heightMeasureSpec);
		w = MeasureSpec.getSize(widthMeasureSpec);
		h = MeasureSpec.getSize(heightMeasureSpec);
		Log.d(TAG, "onMeasure: w2="+w);
		Log.d(TAG, "onMeasure: h2="+h);
		this.setMeasuredDimension(w, h);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		callback.worldIsReady(w,h);

	}

	public void setCallback(LifeCallback callback) {
		this.callback=callback;
	}

	public void iterate(){
		Observable.fromCallable(new Callable<Long>() {

			@Override
			public Long call() throws Exception {
				model.iterate();
				return System.currentTimeMillis()/1000;
			}
		}).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.computation())
				.subscribe(new Observer<Long>() {
					@Override
					public void onSubscribe(Disposable d) {

					}
					@Override
					public void onNext(Long o) {
						Log.d(TAG, "compute step end:"+o);
					}
					@Override
					public void onError(Throwable e) {
						Log.e(TAG, "onError: ",e );
					}

					@Override
					public void onComplete() {
						invalidate();
					}
				});
	}

	@Override
	protected void onDraw(Canvas canvas) {

		for(Cell cell : model.getLiveCells()){
			Log.d(TAG, "onDraw: "+cell);
			canvas.drawRect(cell.getRect(), paintLive);
		}
		String timeText=System.currentTimeMillis()/1000+ "";
		canvas.drawText(timeText, 100,100, paintLive);
		callback.worldIsDrawn();
	}
	public void reset() {
		initVars();
		invalidate();
	}

	public void setModel(WorldModel model) {
		this.model = model;

	}
}
