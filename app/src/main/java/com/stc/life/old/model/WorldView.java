/*
package com.stc.life.old.model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.stc.life.Cell;
import com.stc.life.prefs.SettingsActivity;
import com.stc.life.old.LifeCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.stc.life.Const.CREATION_RULE;
import static com.stc.life.Const.SURVIVAL_RULE;

*/
/**
 * Created by artem on 3/10/17.
 *//*


public class WorldView extends View {
	List<Cell> cells;
	public int cellSize, cellCount,columnCount,rowCount;
	private Paint paintLive;
	private static final String TAG = "WorldView";
	private int h, w;
	private List<Integer> cellStates;
	private int seed;
	private LifeCallback callback;

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
		cells = new ArrayList<>();
		this.cellSize= SettingsActivity.getCellSize(getContext());
		this.seed=SettingsActivity.getSeed(getContext());
		//paintLive.setColor(getResources().getColor(SettingsActivity.getCellColor(getContext())));
		paintLive.setColor(SettingsActivity.getCellColor(getContext()));
		//setBackgroundColor(getResources().getColor(SettingsActivity.getBgColor(getContext())));
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
		if(cells==null || cells.size()==0) {
			cellCount = initCells();
			if (cellStates == null || cellStates.size() == 0)
				cellStates = generate(cellCount, seed);
			createLife(cellStates);
			Log.d(TAG, "onMeasure: empty list");
		}
	}

	public void setCallback(LifeCallback callback) {
		this.callback=callback;
	}

	public void createLife( List<Integer> cellStates){
		int i=0;
		while (i<cellCount){
			boolean isLive = cellStates.get(i)==1;
			//Log.d(TAG, "createLife: "+isLive);
			cells.get(i).setAlive(isLive);
			i++;
		}
	}

	public int initCells(){
		//h=getHeight();
		//w=getWidth();
		columnCount=w/cellSize;
		rowCount=h/cellSize;
		int row=0, column=0, index=0;
		Cell cell=null;
		while(row<rowCount && column<columnCount){
			cell = createCell(index, row,column, cell);
			cells.add(index, cell);
			column++;
			if(column>=columnCount) {
				column=0;
				row++;
			}
			index++;
		}
		Log.d(TAG, index+" cells created");
		Log.d(TAG, "h="+h+" w="+w+" col="+columnCount+ " row="+rowCount);
		return index;
	}
	private List<Integer> generate(int cellCount, int seed) {
		List<Integer> list = new ArrayList<>(cellCount);
		Random random= new Random();
		int i=0;
		while(i<cellCount){
			boolean val=random.nextInt(seed)==0;
			//Log.d(TAG, "index "+i+" generate: "+val);
			list.add(i,  val ? 1 : 0);
			i++;
		}
		return list;
	}

	public void iterate(){
		Observable.fromCallable(new Callable<Long>() {

			@Override
			public Long call() throws Exception {

				for(Cell cell : cells){
					cell.setAlive(getNewState(cell));
				}
				return System.currentTimeMillis()/1000;
			}
		}).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.computation())
				.subscribe(new Observer<Long>() {
					@Override
					public void onSubscribe(Disposable d) {

						//Log.d(TAG, "compute step start:"+System.currentTimeMillis()/1000);
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

		for(Cell cell: cells){
			if(cell.isAlive()) {
				canvas.drawRect(cell.getRect(), paintLive);
			}
		}
		String timeText=System.currentTimeMillis()/1000+ "";
		canvas.drawText(timeText, 100,100, paintLive);
		callback.worldIsDrawn();
	}

	boolean getNewState(Cell cell){
		int liveNCount=0;
		int row=cell.getPosition().getRow();
		int col=cell.getPosition().getColumn();

		int rowMin=0;
		int rowMax=rowCount-1;
		int colMin=0;
		int colMax=columnCount-1;
		if(row>rowMin)   rowMin=row-1;
		if(row<rowMax)   rowMax=row+1;

		if(col>colMin)   colMin=col-1;
		if(col<colMax)   colMax=col+1;


		for (int x = rowMin; x <= rowMax; x++) {
			for(int y = colMin ; y <= colMax; y++){
				if(x==row && y==col) continue;
				int index=x*columnCount+y;
				if(index>0 && index<cells.size() && cells.get(index).isAlive())
					liveNCount++;
				}
			}

		if(cell.isAlive()){
			for(int i : SURVIVAL_RULE) {
				if(liveNCount==i) return true;
			}
		}else {
			for(int i : CREATION_RULE){
				if(liveNCount==i) return true;
			}
		}
		return false;
	}


	public ArrayList<Integer> getState() {
		ArrayList<Integer> states=new ArrayList<>(cellCount);
		for(Cell cell : cells){
			states.add(cell.getPosition().getIndex(), cell.isAlive() ? 1 : 0);
		}
		return states;
	}

	public void reset() {
		initVars();
		cellCount=initCells();
		createLife(generate(cellCount,seed));
		invalidate();
	}
}
*/
