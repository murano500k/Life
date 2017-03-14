package com.stc.life.mvp;

/**
 * Created by artem on 3/13/17.
 */

public interface Presenter {
	void cellIsClicked(int rowCount, int columnCount, int cellSize, int row, int column);

	void setModel(Model model);
	int getSeed();
	void observeCells(int rowCount, int columnCount, int cellSize);
}
