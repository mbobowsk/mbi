/**
 * Klasa opakowująca współrzędne w tablicy.
 */

public class Tuple {
	public int x,y,z;
	
	// Dwa wymiary
	Tuple(int x, int y) {
		this.x = x;
		this.y = y;
		z = -1;
	}
	
	// Trzy wymiary
	Tuple(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public String toString() {
		return new String(x+" "+y+" "+z);
	}
	
	public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Tuple)) {
            return false;
        }
        Tuple other = (Tuple) obj;
        return ( x == other.x && y == other.y && z == other.z );
    }
	
	public int hashCode() {
        return x+y+z;
    }
}
