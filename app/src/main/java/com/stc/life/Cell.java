package com.stc.life;

import android.graphics.Rect;

/**
 * Created by artem on 3/13/17.
 */

public class Cell {
	private final int row, column;
	private final Rect rect;
	private boolean isAlive;

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean alive) {
		isAlive = alive;
	}

	public Cell(int row, int column, Rect rect) {
		this.row = row;
		this.column = column;
		this.rect = rect;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public Rect getRect() {
		return rect;
	}

	@Override
	public String toString() {
		return "Cell{" +
				"row=" + row +
				", column=" + column +
				", rect=" + rect +
				", isAlive=" + isAlive +
				'}';
	}
}
