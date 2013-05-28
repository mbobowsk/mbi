/**
 * Container for constant data.
 */

public class Model {
	
	/*
	     A 	 G   C 	 T  -
	A 	10 	-1 	-3 	-4  c
	G 	-1 	7 	-5 	-3  c
	C 	-3 	-5 	 9 	 0  c
	T 	-4 	-3 	 0 	 8  c
	-    c   c   c   c  0*/
	public int similarityMatrix[][];
	public int gap;	
	public int c;
	
	Model() {
		gap = -5;
		c = -5;
		similarityMatrix = new int[][] {
				{10, -1, -3, -4, c},
				{-1,  7, -5,  3, c},
				{-3, -5,  9,  0, c},
				{-4, -3,  0,  8, c},
				{ c,  c,  c,  c, 0} };
	}
	
}
