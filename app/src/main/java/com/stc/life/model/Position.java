package com.stc.life.model;

/**
 * Created by artem on 3/10/17.
 */

public class Position {
	private int row;
	private int column;
	private String[] neighbours;
	public Position( int row, int column) {
		this.row = row;
		this.column = column;
		this.neighbours=initNeighbours();
	}

	public String[] getNeighbours() {
		return neighbours;
	}

	private String[] initNeighbours() {
		String[] list=new String[10];
		list[0]=(row--)+"."+(column);
		list[1]=(row++)+"."+(column);
		list[2]=(row--)+"."+(column--);
		list[3]=(row--)+"."+(column++);
		list[4]=(row++)+"."+(column--);
		list[5]=(row++)+"."+(column++);
		list[6]=(row)+"."+(column--);
		list[7]=(row)+"."+(column++);
		return list;
	}

	public String getKey(){
		return row+"."+column;
	}



	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
}
