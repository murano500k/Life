package com.stc.life.mvp;

import com.stc.life.Cell;

import java.util.List;

/**
 * Created by artem on 3/13/17.
 */

public interface View {
	void setPresenter(Presenter presenter);


	boolean isAutoPlay();

	void setAutoPlay(boolean autoPlay);

	Presenter getPresenter();
	void setControls(ControlsView controls);

	void initPresenter();

	int getRowCount();
	int getColumnCount();

	int getCellSize();

	void drawCells(List<Cell> cells);

}
