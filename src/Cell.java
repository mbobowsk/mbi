/**
 * Cell from matrix.
 * Contains heuristic score and 2-dimensional index of its origin.
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
	
	Cell(int score, int index, int index2) {
		this.score = score;
		this.index = index;
		this.index2 = index2;
	}
	
	private int score;
	private int index = -1;
	private int index2 = -1;
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getIndex() {
		return index;
	}
	
	public int getIndex2() {
		return index2;
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
