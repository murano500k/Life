package com.stc.life.old.model;

/**
 * Created by artem on 3/10/17.
 */

public class Position {
	private int row;
	private int column;
	private int index;
	public Position(int index, int row, int column) {
		this.row = row;
		this.column = column;
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
}
