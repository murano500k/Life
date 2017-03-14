package com.stc.life;


import android.util.Log;

import com.stc.life.mvp.Model;
import com.stc.life.mvp.Presenter;
import com.stc.life.mvp.View;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;



public class LifePresenter implements Presenter {
	private static final String TAG = "LifePresenter";
	private final int seed, sleep;
	private View view;
	private Model model;


	public LifePresenter(View v, int seed, int sleep) {
		Log.d(TAG, "LifePresenter: seed="+seed+ ", sleep="+sleep);
		this.view=v;
		this.sleep=sleep;
		this.seed=seed;
		view.setPresenter(this);
		new LifeModel(view.getRowCount(), view.getColumnCount(), this);
	}


	@Override
	public void cellIsClicked(int rowCount, int columnCount, int cellSize, int x, int y) {
		Log.d(TAG, "cellIsClicked: "+x+","+y);
		if(model.cellIsClicked(x,y)) observeCells(rowCount, columnCount,cellSize);
	}

	@Override
	public void setModel(Model model) {
		this.model=model;
	}


	@Override
	public void observeCells(int rowCount, int columnCount, int cellSize) {
		Log.d(TAG, "observeCells: "+rowCount+" "+columnCount+" "+cellSize);
		Observable<Cell[][]> initCellsObservable=model.initCellsObservable(rowCount,columnCount,cellSize);
		fromArraysToItems(initCellsObservable
				.delay(sleep, TimeUnit.MILLISECONDS)
				.subscribeOn(Schedulers.computation())
				.observeOn(AndroidSchedulers.mainThread()))
				.toList()
				.subscribe(new Consumer<List<Cell>>() {
			@Override
			public void accept(@NonNull List<Cell> cells) throws Exception {
				view.drawCells(cells);
			}
		});
	}

	private Observable<Cell>fromArraysToItems(Observable<Cell[][]> cellsObservable){
		return cellsObservable.flatMap(new Function<Cell[][], ObservableSource<Cell[]>>() {
			@Override
			public ObservableSource<Cell[]> apply(@NonNull Cell[][] cells) throws Exception {
				return Observable.fromArray(cells);
			}
		}).flatMap(new Function<Cell[], ObservableSource<Cell>>() {
			@Override
			public ObservableSource<Cell> apply(@NonNull Cell[] cells) throws Exception {
				return Observable.fromArray(cells);
			}
		}).filter(new Predicate<Cell>() {
			@Override
			public boolean test(@NonNull Cell cell) throws Exception {
				return cell != null && cell.isAlive();
			}
		});
	}

	@Override
	public int getSeed() {
		return seed;
	}




}
