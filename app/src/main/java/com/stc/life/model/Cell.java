package com.stc.life.model;

import android.graphics.Rect;

/**
 * Created by artem on 3/10/17.
 */

public class Cell {
	private static final String TAG = "Cell";
	private Position position;
	private Rect rect;
	private boolean isAlive;

	public Cell(Position position, Rect rect) {
		this.position = position;
		this.rect = rect;
		isAlive=false;
	}

	public void setAlive(boolean alive) {
		isAlive = alive;
	}

	public Position getPosition() {
		return position;
	}

	public Rect getRect() {
		return rect;
	}

	public boolean isAlive() {
		return isAlive;
	}

	@Override
	public String toString() {
		return "Cell{" +
				" index=" + position.getIndex()+
				", row=" + position.getRow() +
				", column=" + position.getColumn() +
				"\nrect=" + rect.width()+" x "+rect.height() +
				"\nl:"+rect.left+" r:"+rect.right+" t:"+rect.top+" b:"+rect.bottom+
				"\nisAlive=" + isAlive +
				'}';
	}
}
