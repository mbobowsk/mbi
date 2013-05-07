/**
 * Klasa przechowująca wszystkie dane statyczne potrzebne do działania algorytmu.
 */

public class Model {
	
	/* Przykładowa macierz kar i nagród z wykładu
	     A 	 G   C 	 T
	A 	10 	-1 	-3 	-4
	G 	-1 	7 	-5 	-3
	C 	-3 	-5 	 9 	 0
	T 	-4 	-3 	 0 	 8 */
	public int similarityMatrix[][] = new int[][] {
		{10,-1,-3,-4},
		{-1,7,-5,3},
		{-3,-5,9,0},
		{-4,-3,0,8} };
	
	// Kara za przerwę
	public int gap = -5;	

	// Kara za wiszący nukleotyd
	public int c = -5;
	
}
