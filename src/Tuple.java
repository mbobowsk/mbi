/**
 * Tuple for 3-dimensional coordinates.
 */

public class Tuple {
	public int x,y,z;
	
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
        return (x == other.x && y == other.y && z == other.z);
    }
	
	public int hashCode() {
        return x+y+z;
    }
}
