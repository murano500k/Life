package com.stc.life.mvp;

import com.stc.life.Cell;

import io.reactivex.Observable;

/**
 * Created by artem on 3/13/17.
 */

public interface Model {
	Observable<Cell[][]> initCellsObservable(final int rowCount, final int columnCount, final int cellSize);
	boolean cellIsClicked(int row, int column);

}
