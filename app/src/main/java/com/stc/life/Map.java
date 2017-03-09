package com.stc.life;

/**
 * Created by artem on 3/9/17.
 */

public class Map {
    private final int h;
    private final int w;
    boolean [][] values;
    private boolean updateCell(int x, int y){
        return false;

    }

    public boolean[][] getUpdatedMap(){
        for (int i = 0; i < w - 1; i++) {
            boolean[] row=values[i];
            for (int j = 0; j < h -1; j++) {
                values[i][j]=updateCell(i,j);
            }
        }
        return values;
    }

    public Map(boolean [] [] values) {
        this.values=values;
        w=values[0].length;
        h=values.length;
    }


}
