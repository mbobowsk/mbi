/**
 * Klasa przechowująca wszystkie dane statyczne potrzebne do działania algorytmu.
 */

public class Model {
	
	/* Przykładowa macierz kar i nagród z wykładu
	     A 	 G   C 	 T  -
	A 	10 	-1 	-3 	-4  c
	G 	-1 	7 	-5 	-3  c
	C 	-3 	-5 	 9 	 0  c
	T 	-4 	-3 	 0 	 8  c
	-    c   c   c   c  0*/
	public int similarityMatrix[][];
	
	// Kara za przerwę
	public int gap;	
	
	// Kara za wiszący nukleotyd
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
