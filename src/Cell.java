/**
 * Klasa reprezentujące pojedynczy element tablicy.
 */

public class Cell {
	
	Cell(int score) {
		this.score = score;
	}
	
	Cell(int score, int index) {
		this.score = score;
		this.index = index;
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

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
