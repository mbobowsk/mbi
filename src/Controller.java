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
		if ( first.length() == 0 || second.length() == 0 ) {
			result.add(new Tuple(offsetX, offsetY));
			return;
		}
		
		if (first.length() > 2 && second.length() > 2){

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

			// Podział stringów wg x i y
			String sub1L = first.substring(0, x);
			String sub1R = first.substring(x,first.length());
			String sub2L = second.substring(0, y);
			String sub2R = second.substring(y,second.length());
			findSimilarity(sub1L, sub2L, offsetX, offsetY);
			findSimilarity(sub1R, sub2R, offsetX+x, offsetY+y);
		} else {
			int rows = first.length() + 1;
			int cols = second.length() + 1;
			Cell matrix[][] = new Cell[rows][cols];

			// Initialize matrix
			for (int i = 0; i < rows; i++){
				matrix[i][0] = new Cell(i*model.c);
			}
			for (int i = 0; i < cols; i++){
				matrix[0][i] = new Cell(i*model.c);
			}

			for (int i = 1; i < rows; i++){
				for (int j = 1; j < cols; j++){
					int cross = matrix[i-1][j-1].getScore() + match(first.charAt(i-1),second.charAt(j-1));
					int gapVertical = matrix[i-1][j].getScore() + model.gap;
					int gapHorizontal = matrix[i][j-1].getScore() + model.gap;
					int max = Math.max(Math.max(cross,gapVertical),gapHorizontal);

					if (cross == max){
						matrix[i][j] = new Cell(max, Directions.DIRECTION_CROSS);
					} else if (gapVertical == max){
						matrix[i][j] = new Cell(max, Directions.DIRECTION_LEFT);
					} else if (gapHorizontal == max){
						matrix[i][j] = new Cell(max, Directions.DIRECTION_RIGHT);
					}
				}
			}

			int column = cols-1;
			int row = rows-1;
			while (column != 0 && row != 0){
				result.add(new Tuple(row + offsetX, column + offsetY));
				switch(matrix[row][column].getDirection()){
				case DIRECTION_CROSS:
					column--;
					row--;
					break;
				case DIRECTION_LEFT:
					row--;
					break;
				case DIRECTION_RIGHT:
					column--;
					break;
				default:
					break;
				}
			}

			// rob cos

		}
		return;
	}





	/******************** MAGIA *******************************/

	public void findSimilarity2D(String str1, String str2, String str3, int offsetX, int offsetY, int offsetZ) {
		String first, second;

		if ( str1.length() == 0 ) {
			if ( str2.length() == 1 && str3.length() == 1) {
				result.add(new Tuple(offsetX,offsetY,offsetZ));
				return;
			}
			first = str2;
			second = str3;
		}
		else if ( str2.length() == 0 ) {
			if ( str1.length() == 1 && str3.length() == 1) {
				result.add(new Tuple(offsetX,offsetY,offsetZ));
				return;
			}
			first = str1;
			second = str3;
		}
		else if ( str3.length() == 0 ) {
			if ( str1.length() == 1 && str2.length() == 1) {
				result.add(new Tuple(offsetX,offsetY,offsetZ));
				return;
			}
			first = str1;
			second = str2;


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

					// Środek tablicy - indeksy inicjalizowane kolejnymi j
					if ( i <= x ) {
						col2[j] = new Cell(max,j);
					}
					// Indeks "przechodzi" z poprzedniej kolumny
					else {
						int index;
						if ( cross == max )
							index = col1[j-1].getIndex();
						else if ( gapHorizontal == max )
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

			if ( first.length() == 1 || second.length() == 1 )
				return;
			// Podział stringów wg x i y
			String sub1L = first.substring(0, x);
			String sub1R = first.substring(x,first.length());
			String sub2L = second.substring(0, y);
			String sub2R = second.substring(y,second.length());

			if ( str1.length() == 0 ) {
				result.add(new Tuple(offsetX ,offsetY+x, offsetZ+y));
				findSimilarity2D(str1, sub1L, sub2L, offsetX, offsetY, offsetZ);
				findSimilarity2D(str1, sub1R, sub2R, offsetX, offsetY+x, offsetZ+y);
			}
			else if ( str2.length() == 0 ) {
				result.add(new Tuple(offsetX+x, offsetY, offsetZ+y));
				findSimilarity2D(sub1L, str2 ,sub2L, offsetX, offsetY, offsetZ);
				findSimilarity2D(sub1R, str2 ,sub2R, offsetX+x, offsetY, offsetZ+y);
			}
			else // str3 == 0 
			{
				result.add(new Tuple(offsetX+x, offsetY+y, offsetZ));
				findSimilarity2D(sub1L, sub2L, str3, offsetX, offsetY, offsetZ);
				findSimilarity2D(sub1R, sub2R, str3, offsetX+x, offsetY+y, offsetZ);
			}



		}
	}

	/******************** END MAGIA ****************************/





	// I wersja dla trzech wymiarów
	public void findSimilarity(String first, String second, String third, int offsetX, int offsetY, int offsetZ) {
		if ( first.length() == 1 && second.length() == 1 && third.length() == 1 ) {
			result.add(new Tuple(offsetX,offsetY,offsetZ));
			return;
		}

		if ( first.length() == 0 || second.length() == 0 || third.length() == 0 ) {
			findSimilarity2D(first,second,third,offsetX,offsetY,offsetZ);
			return;
		}

		// Długość, szerokość i głębokość tablicy
		int cols = first.length() + 1;
		int rows = second.length() + 1;
		int depth = third.length() + 1;

		// Połowa kolumn - miejsce od którego zaczynamy liczyć indeks
		int x = cols/2;

		// Plastry macierzy
		Cell col1[][] = new Cell[rows][depth];
		Cell col2[][] = new Cell[rows][depth];

		// Inicjalizacja pierwszego plastra
		for ( int i = 0; i < rows; i++ ) {
			col1[i][0] = new Cell(i*model.c);
		}

		for ( int i = 0; i < depth; i++ ) {
			col1[0][i] = new Cell(i*model.c);
		}

		// Obliczenie kolumny
		for ( int i = 1; i < rows; i++ ) {
			for ( int j = 1; j < depth; j++ ) {
				int cross = col1[i-1][j-1].getScore() + match(second.charAt(i-1),third.charAt(j-1));
				int gapVertical = col1[i-1][j].getScore() + model.gap;
				int gapHorizontal = col1[i][j-1].getScore() + model.gap;
				int max = Math.max(Math.max(cross,gapVertical),gapHorizontal);
				col1[i][j] = new Cell(max);
			}
		}


		// Dla kolejnych kolumn
		for ( int i = 1; i < cols; i++) {
			// Oblicz nową kolumnę
			col2 = new Cell[rows][depth];

			for ( int j = 0; j < rows; j++ ) {
				for ( int k = 0; k < depth; k++ ) {
					if ( j == 0 && k == 0 )
						col2[0][0] = new Cell(i*model.c, 0, 0);
					else if ( j == 0 && k != 0 ) {
						// sciana gorna
						int cross = col1[j][k-1].getScore() + match(first.charAt(i-1),third.charAt(k-1));
						int gapVertical = col2[j][k-1].getScore() + model.gap;
						int gapHorizontal = col1[j][k].getScore() + model.gap;
						int max = Math.max(Math.max(cross,gapVertical),gapHorizontal);

						if ( i <= x )
							col2[j][k] = new Cell(max,j,k);
						else {
							int index, index2;
							if ( cross == max ) {
								index = col1[j][k-1].getIndex();
								index2 = col1[j][k-1].getIndex2();
							}
							else if ( gapHorizontal == max ) {
								index = col1[j][k].getIndex();
								index2 = col1[j][k].getIndex2();
							}
							else {
								index = col2[j][k-1].getIndex();
								index2 = col2[j][k-1].getIndex2();
							}
							col2[j][k] = new Cell(max,index,index2);
						}				

					}
					else if ( j != 0 && k == 0 ) {
						// sciana przednia
						int cross = col1[j-1][k].getScore() + match(first.charAt(i-1),second.charAt(j-1));
						int gapVertical = col2[j-1][k].getScore() + model.gap;
						int gapHorizontal = col1[j][k].getScore() + model.gap;
						int max = Math.max(Math.max(cross,gapVertical),gapHorizontal);						

						if ( i <= x )
							col2[j][k] = new Cell(max,j,k);
						else {
							int index, index2;
							if ( cross == max ) {
								index = col1[j-1][k].getIndex();
								index2 = col1[j-1][k].getIndex2();
							}
							else if ( gapHorizontal == max ) {
								index = col1[j][k].getIndex();
								index2 = col1[j][k].getIndex2();
							}
							else {
								index = col2[j-1][k].getIndex();
								index2 = col2[j-1][k].getIndex2();
							}
							col2[j][k] = new Cell(max,index,index2);
						}	

					} else {
						// glebia 
						int cross = col1[j][k-1].getScore() + match(first.charAt(i-1),'-',third.charAt(k-1));
						int left = col1[j][k].getScore() + match(first.charAt(i-1),'-','-');
						int right = col2[j][k-1].getScore() + match('-','-',third.charAt(k-1));

						int crossCross = col1[j-1][k-1].getScore() + match(first.charAt(i-1),second.charAt(j-1),third.charAt(k-1));
						int leftCross = col1[j-1][k].getScore() + match(first.charAt(i-1),second.charAt(j-1),'-');
						int rightCross = col2[j-1][k-1].getScore() + match('-',second.charAt(j-1),third.charAt(k-1));

						int up = col2[j-1][k].getScore() + match('-',second.charAt(j-1),'-');


						int [] array = {crossCross, cross, left, right, leftCross, rightCross, up};
						int max = array[0];
						for ( int m = 1; m < array.length; m++ ) {
							max = max > array[m] ? max : array[m];
						}
						if ( i <= x )
							col2[j][k] = new Cell(max,j,k);
						else {
							if ( crossCross == max ) {
								col2[j][k] = new Cell(max, col1[j-1][k-1].getIndex(), col1[j-1][k-1].getIndex2());
							}
							else if ( cross == max ) {
								col2[j][k] = new Cell(max, col1[j][k-1].getIndex(), col1[j][k-1].getIndex2());
							}
							else if ( left == max ) {
								col2[j][k] = new Cell(max, col1[j][k].getIndex(), col1[j][k].getIndex2());
							}
							else if ( right == max ) {
								col2[j][k] = new Cell(max, col2[j][k-1].getIndex(), col2[j][k-1].getIndex2());
							}
							else if ( leftCross == max ) {
								col2[j][k] = new Cell(max, col1[j-1][k].getIndex(), col1[j-1][k].getIndex2());
							}
							else if ( rightCross == max ) {
								col2[j][k] = new Cell(max, col2[j-1][k-1].getIndex(), col2[j-1][k-1].getIndex2());
							}
							else { // up
								col2[j][k] = new Cell(max, col2[j-1][k].getIndex(), col2[j-1][k].getIndex2());
							}

						}
					}
				}
			}

			System.out.println("--pierwsza--");

			for ( int j = 0; j < rows; j++) {
				for ( int k = 0; k < depth; k++) {
					System.out.print(col1[j][k].getScore() + " ");
				}
				System.out.println();
			}
			System.out.println("--druga--");
			// Druga staje się pierwszą
			col1 = col2.clone();

			// Wypisywanie
			for ( int j = 0; j < rows; j++) {
				for ( int k = 0; k < depth; k++) {
					System.out.print(col1[j][k].getScore() + " ");
				}
				System.out.println();
			}
			System.out.println("--------------------------------");
		}


		int y = col2[rows-1][depth-1].getIndex();
		int z = col2[rows-1][depth-1].getIndex2();

		// Podział stringów wg x i y
		String sub1L = first.substring(0, x);
		String sub1R = first.substring(x,first.length());
		String sub2L = second.substring(0, y);
		String sub2R = second.substring(y,second.length());
		String sub3L = third.substring(0, z);
		String sub3R = third.substring(z,third.length());
		findSimilarity(sub1L, sub2L, sub3L, offsetX, offsetY, offsetZ);
		findSimilarity(sub1R, sub2R, sub3R, offsetX+x, offsetY+y, offsetZ+z);
	}



	// Zwraca karę/nagrodę za dopasowanie dwóch znaków
	private int match( char a, char b ) {
		int row = charToIndex(a);
		int col = charToIndex(b);
		return model.similarityMatrix[row][col];
	}

	// Zwraca karę/nagrodę za dopasowanie trzech znaków
	private int match( char a, char b, char c ) {
		int row = charToIndex(a);
		int col = charToIndex(b);
		int dep = charToIndex(b);
		return ( model.similarityMatrix[row][col] +
				model.similarityMatrix[row][dep] +
				model.similarityMatrix[dep][col] );
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
		else if ( a == 'T' ) {
			index = 3;
		}
		else
			index = 4;
		return index;
	}


	public static void main(String[] args) {
		Controller c = new Controller();
		/* Nie działa nawet przy warunku < 2
		c.findSimilarity("GTTACT","GAATTT","GACCCT",0,0,0);
		c.findSimilarity("GTTACT","GAATTT","G",0,0,0); */
		c.findSimilarity("GCC","GCAGT",0,0);
		for ( Tuple t : c.result ) {
			System.out.println(t);
		}
	}
}
