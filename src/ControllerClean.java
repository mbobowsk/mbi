import java.util.HashSet;


public class ControllerClean {

	public ControllerClean() {}

	public Model model = new Model();
	// Container for result path
	public HashSet<Tuple> result = new HashSet<Tuple>();

	public void findSimilarity(String first, String second, String third, int offsetX, int offsetY, int offsetZ) {
		// Build complete matrix
		if (first.length() < 12 || second.length() < 12 || third.length() < 12) {
			int cols = first.length() + 1;
			int rows = second.length() + 1;
			int depth = third.length() + 1;
			Cell matrix[][][] = new Cell[cols][rows][depth];

			// Initialize matrix
			for (int i = 0; i < cols; i++) {
				matrix[i][0][0] = new Cell(i*model.c);
			}
			for (int i = 0; i < rows; i++) {
				matrix[0][i][0] = new Cell(i*model.c);
			}
			for (int i = 0; i < depth; i++) {
				matrix[0][0][i] = new Cell(i*model.c);
			}

			for ( int i = 1; i < rows; i++ ) {
				for ( int j = 1; j < depth; j++ ) {
					int cross = matrix[0][i-1][j-1].getScore() + match('-',second.charAt(i-1),third.charAt(j-1));
					int gapVertical = matrix[0][i-1][j].getScore() + model.gap;
					int gapHorizontal = matrix[0][i][j-1].getScore() + model.gap;
					int max = Math.max(Math.max(cross,gapVertical),gapHorizontal);
					matrix[0][i][j] = new Cell(max);
				}
			}


			for (int i = 1; i < cols; i++) {
				for (int j = 0; j < rows; j++) {
					for (int k = 0; k < depth; k++) {

						if (j==0 && k==0) { // already initialized
							continue;
						}
						else if (j==0) { // top wall
							int cross = matrix[i-1][j][k-1].getScore() + match(first.charAt(i-1),third.charAt(k-1));
							int right = matrix[i][j][k-1].getScore() + match('-', '-', third.charAt(k-1));
							int left = matrix[i-1][j][k].getScore() + match(first.charAt(i-1), '-', '-');
							int max = Math.max(Math.max(cross,right),left);
							if (max==cross) {
								matrix[i][j][k] = new Cell(max, Directions.DIRECTION_CROSS);
							}
							else if (max==right) {
								matrix[i][j][k] = new Cell(max, Directions.DIRECTION_RIGHT);
							}
							else { //left
								matrix[i][j][k] = new Cell(max, Directions.DIRECTION_LEFT);
							}
						}
						else if (k==0) { // front wall
							int left = matrix[i-1][j][k].getScore() + match(first.charAt(i-1), '-', '-');
							int leftCross = matrix[i-1][j-1][k].getScore() + match(first.charAt(i-1), second.charAt(j-1), '-');
							int up = matrix[i][j-1][k].getScore() + match('-', second.charAt(j-1), '-');
							int max = Math.max(Math.max(leftCross,left),up);
							if (max==leftCross) {
								matrix[i][j][k] = new Cell(max, Directions.DIRECTION_RIGHTCROSS);
							}
							else if (max==left) {
								matrix[i][j][k] = new Cell(max, Directions.DIRECTION_RIGHT);
							}
							else { //up
								matrix[i][j][k] = new Cell(max, Directions.DIRECTION_UP);
							}
						} else {

							// DIRECTION_CROSS
							int cross = matrix[i-1][j][k-1].getScore() + match(first.charAt(i-1), '-', third.charAt(k-1));
							// DIRECTION_LEFT
							int left = matrix[i-1][j][k].getScore() + match(first.charAt(i-1), '-', '-');
							// DIRECTION_RIGHT
							int right = matrix[i][j][k-1].getScore() + match('-', '-', third.charAt(k-1));
							// DIRECTION_CROSSCROSS
							int crossCross = matrix[i-1][j-1][k-1].getScore() + match(first.charAt(i-1), second.charAt(j-1), third.charAt(k-1));
							// DIRECTION_LEFTCROSS
							int leftCross = matrix[i-1][j-1][k].getScore() + match(first.charAt(i-1), second.charAt(j-1), '-');
							// DIRECTION_RIGHTCROSS
							int rightCross = matrix[i][j-1][k-1].getScore() + match('-', second.charAt(j-1), third.charAt(k-1));
							// DIRECTION_UP
							int up = matrix[i][j-1][k].getScore() + match('-', second.charAt(j-1), '-');

							// Find max
							int [] array = {crossCross, cross, left, right, leftCross, rightCross, up};
							int max = array[0];
							for ( int m = 1; m < array.length; m++ ) {
								max = max > array[m] ? max : array[m];
							}

							if (cross == max) {
								matrix[i][j][k] = new Cell(max, Directions.DIRECTION_CROSS);
							} else if (left == max) {
								matrix[i][j][k] = new Cell(max, Directions.DIRECTION_LEFT);
							} else if (right == max) {
								matrix[i][j][k] = new Cell(max, Directions.DIRECTION_RIGHT);
							} else if (crossCross == max) {
								matrix[i][j][k] = new Cell(max, Directions.DIRECTION_CROSSCROSS);
							} else if (leftCross == max) {
								matrix[i][j][k] = new Cell(max, Directions.DIRECTION_LEFTCROSS);
							} else if (rightCross == max) {
								matrix[i][j][k] = new Cell(max, Directions.DIRECTION_RIGHTCROSS);
							} else if (up == max) {
								matrix[i][j][k] = new Cell(max, Directions.DIRECTION_UP);
							}
						}
					}
				}
			}

			// Go back the result line
			int column = cols-1;
			int row = rows-1;
			int dep = depth-1;
			while (column != 0 && row != 0 && dep != 0) {
				result.add(new Tuple(column+offsetX, row+offsetY, dep+offsetZ));
				switch(matrix[column][row][dep].getDirection()) {
				case DIRECTION_CROSS:
					column--;
					dep--;
					break;
				case DIRECTION_LEFT:
					column--;
					break;
				case DIRECTION_RIGHT:
					dep--;
					break;
				case DIRECTION_CROSSCROSS:
					column--;
					row--;
					dep--;
					break;
				case DIRECTION_RIGHTCROSS:
					row--;
					dep--;
					break;
				case DIRECTION_LEFTCROSS:
					column--;
					row--;
					break;
				case DIRECTION_UP:
					row--;
					break;
				default:
					break;
				}
			}
		} else {
			// Divide problem

		}


		return;
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
		int dep = charToIndex(c);
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
		ControllerClean c = new ControllerClean();
		c.findSimilarity("GAAA", "GCAAA", "GCAAA", 0, 0, 0);
		for ( Tuple t : c.result ) {
			System.out.println(t);
		}
	}

}
