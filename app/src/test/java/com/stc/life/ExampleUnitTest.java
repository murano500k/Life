package com.stc.life;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void testLife() throws Exception {
	    boolean [][] arr=new boolean[5][10];

	    arr[2][1]=arr[2][2]=arr[2][3]=true;
		printMap(arr);


    }
    private void printMap(boolean[][]map){

	    System.out.println("\n###########################");
	    for (int i = 0; i < 5; i++) {
		    String row ="";
		    for (int j = 0; j < 10; j++) {
			    row+=(map[i][j] ? "x" : "o")+" ";
		    }
		    System.out.println(row);
	    }
	    System.out.println("###########################\n\n");

    }
}