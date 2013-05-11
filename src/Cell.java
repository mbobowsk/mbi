/**
 * Klasa reprezentujące pojedynczy element tablicy.
 */

public class Cell {
	
	private Directions direction = Directions.DIRECTION_NULL;
	
	Cell(int score) {
		this.score = score;
	}

	Cell(int score, Directions direction){
		this.score = score;
		this.direction = direction;
	}
	
	// Dwa wymiary
	Cell(int score, int index) {
		this.score = score;
		this.index = index;
	}
	
	// Trzy wymiary
	Cell(int score, int index, int index2) {
		this.score = score;
		this.index = index;
		this.index2 = index2;
	}
	
	// Heurystyczna ocena połączenia
	private int score;
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	// Indeks oznaczający komórkę źródłową
	int index = -1;
	int index2 = -1;

	public int getIndex() {
		return index;
	}
	
	public int getIndex2() {
		return index2;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public void setIndex(int index, int index2) {
		this.index = index;
		this.index2 = index2;
	}
	
	public Directions getDirection() {
		return direction;
	}
	
	public void setDirection(Directions direction) {
		this.direction = direction;
	}
}
