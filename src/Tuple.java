/**
 * Klasa opakowująca współrzędne w tablicy.
 */

public class Tuple {
	public int x,y;
	
	Tuple(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return new String(x+" "+y);
	}
	
	public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Tuple)) {
            return false;
        }
        Tuple other = (Tuple) obj;
        return ( x == other.x && y == other.y );
    }
	
	public int hashCode() {
        return x+y;
    }
}
