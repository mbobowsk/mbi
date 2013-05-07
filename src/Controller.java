import java.util.HashSet;
import java.util.Set;

public class Controller {
	
	Model model = new Model();
	// Kontener na punkty
	HashSet<Tuple> result = new HashSet<Tuple>();
	
	/*
	 * Funkcja przyjmuje dwa ciągi znaków, oblicza w czasie liniowym tablicę WH
	 * Zwraca indeks komórki ze środkowego rzędu przez którą przechodzi optymalne rozwiązanie
	 */
	public void findSimilarity(String first, String second, int offsetX, int offsetY) {
		if ( first.length() == 1 && second.length() == 1) {
			result.add(new Tuple(offsetX,offsetY));
			return;
		}
		
		// Długość i szerokość tablicy
		int cols = first.length() + 1;
		int rows = second.length() + 1;
		
		// Połowa kolumn - miejsce od którego zaczynamy liczyć indeks
		int x = cols/2;
		
		// Kolumny macierzy
		Cell col1[] = new Cell[rows];
		Cell col2[] = new Cell[rows];
		
		// Inicjalizacja pierwszej kolumny
		for ( int i = 0; i < rows; i++ ) {
			col1[i] = new Cell(i*model.c);
		}
		
		// Dla kolejnych kolumn
		for ( int i = 1; i < cols; i++) {
			// Oblicz nową kolumnę
			col2 = new Cell[rows];
			col2[0] = new Cell(i*model.c,0);
			for ( int j = 1; j < rows; j++) {
				int cross = col1[j-1].getScore() + match(first.charAt(i-1),second.charAt(j-1));
				int gapVertical = col2[j-1].getScore() + model.gap;
				int gapHorizontal = col1[j].getScore() + model.gap;
				int max = Math.max(Math.max(cross,gapVertical),gapHorizontal);
				
				// Nie interesują nas indeksy
				if ( i < x) {
					col2[j] = new Cell(max);
				}
				// Środek tablicy - indeksy inicjalizowane kolejnymi j
				else if ( i == x ) {
					col2[j] = new Cell(max,j);
				}
				// Indeks "przechodzi" z poprzedniej kolumny
				else {
					int index;
					if ( cross >= gapVertical && cross >= gapHorizontal )
						index = col1[j-1].getIndex();
					else if ( gapHorizontal >= gapVertical && gapHorizontal >= cross )
						index = col1[j].getIndex();
					else
						index = col2[j-1].getIndex();
					col2[j] = new Cell(max,index);
				}
			}			
			// Druga staje się pierwszą
			col1 = col2;
		}
		
		int y = col2[rows-1].getIndex();
		result.add(new Tuple(x+offsetX,y+offsetY));
		if ( first.length() == 1 || second.length() == 1 )
			return;
		// Podział stringów wg x i y
		String sub1L = first.substring(0, x);
		String sub1R = first.substring(x,first.length());
		String sub2L = second.substring(0, y);
		String sub2R = second.substring(y,second.length());
		findSimilarity(sub1L, sub2L, offsetX, offsetY);
		findSimilarity(sub1R, sub2R, offsetX+x, offsetY+y);
	}
	
	
	
	// Zwraca karę/nagrodę za dopasowanie dwóch znaków
	private int match( char a, char b ) {
		int row = charToIndex(a);
		int col = charToIndex(b);
		return model.similarityMatrix[row][col];
	}
	
	
	// Przyjmuje znak i zwraca odpowiadający mu indeks w macierzy kar i nagród
	private int charToIndex(char a) {
		int index;
		if ( a == 'A' ) {
			index = 0;
		}
		else if ( a == 'G' ) {
			index = 1;
		}
		else if ( a == 'C' ) {
			index = 2;
		}
		else {
			index = 3;
		}
		return index;
	}
	
	
	public static void main(String[] args) {
		Controller c = new Controller();
		c.findSimilarity("GAATTC","GATTA",0,0);
		for ( Tuple t : c.result ) {
			System.out.println(t);
		}
	}

}
