package com.stc.life;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import com.stc.life.mvp.Model;
import com.stc.life.mvp.Presenter;

import java.util.Random;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by artem on 3/13/17.
 */

public class LifeModel implements Model {
	private static final String TAG = "LifeModel";
	final private Presenter presenter;
	private boolean autoPlay;
	private Cell[][] cells;


	public LifeModel(final int rowCount, final int columnCount, final Presenter presenter) {
		Log.d(TAG, "LifeModel: r="+rowCount+" c="+columnCount);
		this.presenter=presenter;
		presenter.setModel(this);
		autoPlay=false;
	}


	private Cell[][]getUpdatedCells(){
		Cell[][] newCells=cells;
		for (int row = 0; row < cells.length; row++) {
			for (int column = 0; column < cells[row].length; column++) {
				boolean isAlive = getNewState(row,column,cells[row][column].isAlive(),cells);
				newCells[row][column].setAlive(isAlive);
			}
		}
		cells=newCells;
		return cells;
	}


	private boolean getNewState(int row, int column, boolean cellState, final Cell[][]cells){
		boolean newState=false;
		int liveCount=0;
		int left=column-1;
		int right=column+1;
		int above=row+1;
		int below=row-1;
		if(left>0 && cells[row][left].isAlive()) liveCount++;
		if(right < cells[row].length && cells[row][right].isAlive()) liveCount++;
		if(below > 0 ){
			if(left > 0 && cells[below][left].isAlive()) liveCount++;
			if(right < cells[below].length && cells[below][right].isAlive()) liveCount++;
			if(cells[below][column].isAlive()) liveCount++;
		}
		if(above<cells.length){
			if(left>0 && cells[above][left].isAlive()) liveCount++;
			if(right<cells[above].length && cells[above][right].isAlive()) liveCount++;
			if(cells[above][column].isAlive()) liveCount++;
		}

		if(cellState){
			if(liveCount<=2 || liveCount>4) newState=false;
			else newState=true;
		}else {
			if(liveCount==3) newState=true;
		}
		return newState;
	}
	@Override
	public Observable<Cell[][]> initCellsObservable(final int rowCount, final int columnCount, final int cellSize){
		return Observable.fromCallable(new Callable<Cell[][]>() {
			@Override
			public Cell[][] call() throws Exception {
				if(cells==null) cells=initCells(rowCount, columnCount, cellSize);
				else cells=getUpdatedCells();
				return cells;
			}
		}).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread());
	}


	private Cell[][] initCells(int rowCount, int columnCount, int cellSize){
		Random random =new Random();
		Cell[][]cells = new Cell[rowCount][columnCount];
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				Cell cell = new Cell(row, column, createCellRect(row, column, cellSize));
				cell.setAlive(random.nextInt(presenter.getSeed())==0);
				cells[row][column] =cell;
			}
		}
		return cells;
	}
	private Rect createCellRect(int row, int column, int size ){
		Point point =new Point(row*size, column*size);
		return new Rect(point.y, point.x+size, point.y+size, point.x);
	}
	@Override
	public boolean cellIsClicked(int x, int y) {
		for(Cell[] cellsRow: cells){
			for(Cell cell: cellsRow)if(cell.getRect().contains(y,x)){
				Log.d(TAG, "cellIsClicked: "+cell);
				cell.setAlive(true);
				return true;
			}
		}
		return false;
	}



}

